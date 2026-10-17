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

    public static final String RULE_EMPTY_INPUT = "EmptyInputFallback";
    public static final String RULE_CYRILLIC_FALLBACK = "DefaultCyrillicFallback";
    public static final String RULE_LATIN_FALLBACK = "DefaultLatinFallback";

    private static final FastLanguageDetector INSTANCE = new FastLanguageDetector();
    private static final DetectionResult EMPTY_RESULT = new DetectionResult(Language.ENGLISH, 0.0, Language.Script.LATIN, RULE_EMPTY_INPUT);

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
     * Detects the language of the given text, returning the Language enum constant.
     * ZERO HEAP ALLOCATION: Allocates 0 objects and 0 bytes on the heap.
     */
    public static Language detect(CharSequence text) {
        return INSTANCE.detectLanguage(text);
    }

    /**
     * Detects the language with a specified fallback, returning the Language enum constant.
     * ZERO HEAP ALLOCATION: Allocates 0 objects and 0 bytes on the heap.
     */
    public static Language detect(CharSequence text, Language fallback) {
        return INSTANCE.detectLanguage(text, fallback);
    }

    /**
     * Detects the language and returns standard BCP-47 locale tag (e.g., "de-DE", "en-US").
     */
    public static String detectLocale(CharSequence text) {
        return detect(text).getBcp47();
    }

    /**
     * Detects language and returns comprehensive analysis including confidence score.
     * Allocates only a single immutable DetectionResult record, zero temporary strings.
     */
    public static DetectionResult detectWithConfidence(CharSequence text) {
        return INSTANCE.analyze(text, INSTANCE.defaultFallback);
    }

    /**
     * Detects language returning the Language enum directly without instantiating DetectionResult.
     * Guarantees zero heap allocation.
     */
    public Language detectLanguage(CharSequence text) {
        return detectLanguage(text, defaultFallback);
    }

    /**
     * Detects language returning the Language enum directly with custom fallback.
     * Guarantees zero heap allocation.
     */
    public Language detectLanguage(CharSequence text, Language fallback) {
        if (text == null || text.length() == 0) {
            return (fallback != null) ? fallback : defaultFallback;
        }

        // 1. Level 1: Unicode Script Analysis (< 0.001 ms, zero allocations)
        ScriptDetector.ScriptAnalysis script = ScriptDetector.analyze(text);
        if (script.primaryScript() != Language.Script.LATIN && script.detectedLanguage() != Language.UNKNOWN) {
            return script.detectedLanguage();
        }

        // 2. Level 2 & 3: Fast Lexical & Diacritics Profiling (Zero-allocation in-place scan)
        double[] scores = THREAD_LOCAL_SCORES.get();
        scoreText(text, scores);

        // Find highest scoring language
        int bestIdx = -1;
        double bestScore = 0.0;
        for (int i = 0; i < scores.length; i++) {
            double s = scores[i];
            if (s > bestScore) {
                bestScore = s;
                bestIdx = i;
            }
        }

        if (bestIdx >= 0 && bestScore > 0.0) {
            return Language.ALL[bestIdx];
        }

        // Fallback for short or ambiguous text
        if (script.primaryScript() == Language.Script.CYRILLIC) {
            return (fallback != null) ? fallback : Language.RUSSIAN;
        }
        return (fallback != null) ? fallback : defaultFallback;
    }

    public DetectionResult analyze(CharSequence text) {
        return analyze(text, defaultFallback);
    }

    public DetectionResult analyze(CharSequence text, Language fallback) {
        if (text == null || text.length() == 0) {
            if (fallback == null || fallback == defaultFallback) {
                return EMPTY_RESULT;
            }
            return new DetectionResult(fallback, 0.0, Language.Script.LATIN, RULE_EMPTY_INPUT);
        }

        // 1. Level 1: Unicode Script Analysis (< 0.001 ms, zero allocations)
        ScriptDetector.ScriptAnalysis script = ScriptDetector.analyze(text);
        if (script.primaryScript() != Language.Script.LATIN && script.detectedLanguage() != Language.UNKNOWN) {
            double conf = Math.min(1.0, 0.70 + (script.scriptCharCount() / (double) Math.max(1, script.totalCharCount())) * 0.30);
            return new DetectionResult(script.detectedLanguage(), conf, script.primaryScript(), script.primaryScript().getRuleProvenance());
        }

        // 2. Level 2 & 3: Fast Lexical & Diacritics Profiling (Zero-allocation in-place scan)
        double[] scores = THREAD_LOCAL_SCORES.get();
        int totalWords = scoreText(text, scores);

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
            return new DetectionResult(bestLang, confidence, bestLang.getScript(), bestLang.getRuleProvenance());
        }

        // Fallback for short or ambiguous text
        if (script.primaryScript() == Language.Script.CYRILLIC) {
            Language def = (fallback != null) ? fallback : Language.RUSSIAN;
            return new DetectionResult(def, 0.40, Language.Script.CYRILLIC, RULE_CYRILLIC_FALLBACK);
        }
        Language def = (fallback != null) ? fallback : defaultFallback;
        return new DetectionResult(def, 0.35, Language.Script.LATIN, RULE_LATIN_FALLBACK);
    }

    private static int scoreText(CharSequence text, double[] scores) {
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
        return totalWords;
    }
}
