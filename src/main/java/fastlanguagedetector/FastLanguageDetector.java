package fastlanguagedetector;

import fastlanguagedetector.core.ScriptDetector;
import fastlanguagedetector.core.StopwordProfiles;

import java.util.Arrays;

/**
 * FastLanguageDetector - Ultra-Fast, Offline Language Identification.
 * Pure FastJava architecture: zero external dependencies, zero regex,
 * zero heap allocations in the hot path, and sub-microsecond latency.
 */
public final class FastLanguageDetector {

    private static final FastLanguageDetector INSTANCE = new FastLanguageDetector();
    private static final DetectionResult EMPTY_RESULT = new DetectionResult(Language.ENGLISH, 0.0, Language.Script.LATIN, "EmptyInputFallback");

    // ThreadLocal reusable scoring array to eliminate heap churn
    private static final ThreadLocal<double[]> THREAD_LOCAL_SCORES = ThreadLocal.withInitial(() -> new double[Language.COUNT]);

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
            if (fallback == null || fallback == defaultFallback) {
                return EMPTY_RESULT;
            }
            return new DetectionResult(fallback, 0.0, Language.Script.LATIN, "EmptyInputFallback");
        }

        // 1. Level 1: Unicode Script Analysis (< 0.001 ms, zero allocations)
        ScriptDetector.ScriptAnalysis script = ScriptDetector.analyze(text);
        if (script.primaryScript() != Language.Script.LATIN && script.detectedLanguage() != Language.UNKNOWN) {
            double conf = Math.min(1.0, 0.70 + (script.scriptCharCount() / (double) Math.max(1, script.totalCharCount())) * 0.30);
            return new DetectionResult(script.detectedLanguage(), conf, script.primaryScript(), "Script:" + script.primaryScript());
        }

        // 2. Level 2 & 3: Fast Lexical & Diacritics Profiling (Zero-allocation in-place scan)
        double[] scores = THREAD_LOCAL_SCORES.get();
        Arrays.fill(scores, 0.0);

        int len = text.length();
        int wordStart = -1;
        int totalWords = 0;

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            boolean isLetter = (c <= 127)
                    ? ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
                    : Character.isLetter(c);

            if (isLetter) {
                if (wordStart < 0) {
                    wordStart = i;
                }
            } else if (wordStart >= 0) {
                totalWords++;
                Language[] matches = StopwordProfiles.lookupWord(text, wordStart, i);
                if (matches != null) {
                    for (int m = 0; m < matches.length; m++) {
                        scores[matches[m].ordinal()] += 1.0;
                    }
                }
                wordStart = -1;
            }
        }
        if (wordStart >= 0) {
            totalWords++;
            Language[] matches = StopwordProfiles.lookupWord(text, wordStart, len);
            if (matches != null) {
                for (int m = 0; m < matches.length; m++) {
                    scores[matches[m].ordinal()] += 1.0;
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
            Language bestLang = Language.ALL[bestIdx];
            double confidence = (totalWords > 0)
                    ? Math.min(1.0, (bestScore / (bestScore + secondScore + 0.5)))
                    : 0.75;
            return new DetectionResult(bestLang, confidence, bestLang.getScript(), "LexicalProfile:" + bestLang.getCode());
        }

        // Fallback for short or ambiguous text
        if (script.primaryScript() == Language.Script.CYRILLIC) {
            Language def = (fallback != null) ? fallback : Language.RUSSIAN;
            return new DetectionResult(def, 0.40, Language.Script.CYRILLIC, "DefaultCyrillicFallback");
        }
        Language def = (fallback != null) ? fallback : defaultFallback;
        return new DetectionResult(def, 0.35, Language.Script.LATIN, "DefaultLatinFallback");
    }
}
