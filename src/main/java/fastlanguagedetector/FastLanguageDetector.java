package fastlanguagedetector;

import fastlanguagedetector.core.ScriptDetector;
import fastlanguagedetector.core.StopwordProfiles;

import java.util.*;

/**
 * FastLanguageDetector - Ultra-Fast, Offline Language Identification.
 * Operates in sub-microseconds without external models or heavy dependencies.
 */
public final class FastLanguageDetector {

    private static final FastLanguageDetector INSTANCE = new FastLanguageDetector();
    private final Language defaultFallback;

    public FastLanguageDetector() {
        this(Language.ENGLISH);
    }

    public FastLanguageDetector(Language defaultFallback) {
        this.defaultFallback = (defaultFallback != null) ? defaultFallback : Language.ENGLISH;
    }

    public static FastLanguageDetector standard() {
        return INSTANCE;
    }

    /**
     * Detects the language of the given text, returning the Language enum.
     */
    public static Language detect(CharSequence text) {
        return INSTANCE.detectLanguage(text);
    }

    /**
     * Detects the language and returns standard BCP-47 locale tag (e.g., "de-DE", "en-US").
     */
    public static String detectLocale(CharSequence text) {
        return detect(text).getBcp47();
    }

    /**
     * Detects language and returns comprehensive analysis including confidence score.
     */
    public static DetectionResult detectWithConfidence(CharSequence text) {
        return INSTANCE.analyze(text, INSTANCE.defaultFallback);
    }

    public Language detectLanguage(CharSequence text) {
        return analyze(text, defaultFallback).language();
    }

    public DetectionResult analyze(CharSequence text) {
        return analyze(text, defaultFallback);
    }

    public DetectionResult analyze(CharSequence text, Language fallback) {
        if (text == null || text.length() == 0) {
            Language def = (fallback != null) ? fallback : defaultFallback;
            return new DetectionResult(def, 0.0, Language.Script.LATIN, "EmptyInputFallback");
        }

        // 1. Level 1: Unicode Script Analysis (< 0.001 ms)
        ScriptDetector.ScriptAnalysis script = ScriptDetector.analyze(text);
        if (script.primaryScript() != Language.Script.LATIN && script.detectedLanguage() != Language.UNKNOWN) {
            double conf = Math.min(1.0, 0.70 + (script.scriptCharCount() / (double) Math.max(1, script.totalCharCount())) * 0.30);
            return new DetectionResult(script.detectedLanguage(), conf, script.primaryScript(), "Script:" + script.primaryScript());
        }

        // 2. Level 2 & 3: Fast Lexical & Diacritics Profiling
        List<String> words = extractWords(text);
        int totalWords = words.size();

        double[] scores = new double[Language.values().length];

        for (int i = 0; i < totalWords; i++) {
            Language[] matches = StopwordProfiles.WORD_INDEX.get(words.get(i));
            if (matches != null) {
                for (int j = 0; j < matches.length; j++) {
                    scores[matches[j].ordinal()] += 1.0;
                }
            }
        }

        // Apply fast single-pass diacritics boost
        StopwordProfiles.applyDiacritics(text, scores);

        // Find highest scoring language
        int bestIdx = -1;
        double bestScore = 0.0;
        double secondScore = 0.0;

        for (int i = 0; i < scores.length; i++) {
            double s = scores[i];
            if (s > bestScore) {
                secondScore = bestScore;
                bestScore = s;
                bestIdx = i;
            } else if (s > secondScore) {
                secondScore = s;
            }
        }

        if (bestIdx >= 0 && bestScore > 0.0) {
            Language bestLang = Language.values()[bestIdx];
            double confidence = (totalWords > 0) ? Math.min(1.0, (bestScore / (bestScore + secondScore + 0.5))) : 0.75;
            return new DetectionResult(bestLang, Math.max(0.51, confidence), bestLang.getScript(), "LexicalProfile:" + bestLang.getCode());
        }

        // Fallback for short or ambiguous text
        if (script.primaryScript() == Language.Script.CYRILLIC) {
            Language def = (fallback != null) ? fallback : Language.RUSSIAN;
            return new DetectionResult(def, 0.40, Language.Script.CYRILLIC, "DefaultCyrillicFallback");
        }
        Language def = (fallback != null) ? fallback : defaultFallback;
        return new DetectionResult(def, 0.35, Language.Script.LATIN, "DefaultLatinFallback");
    }

    private static List<String> extractWords(CharSequence text) {
        int len = text.length();
        List<String> words = new ArrayList<>(Math.min(32, len / 4 + 1));
        StringBuilder sb = new StringBuilder(16);

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            } else if (sb.length() > 0) {
                words.add(sb.toString());
                sb.setLength(0);
            }
        }
        if (sb.length() > 0) {
            words.add(sb.toString());
        }
        return words;
    }
}
