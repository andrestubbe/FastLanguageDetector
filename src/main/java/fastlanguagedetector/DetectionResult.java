package fastlanguagedetector;

/**
 * Result of language identification including language, confidence score, script, and matching strategy.
 */
public record DetectionResult(
        Language language,
        double confidence,
        Language.Script script,
        String matchedRule
) {
    public boolean isConfident() {
        return confidence >= 0.50;
    }

    public String getLocale() {
        return language.getBcp47();
    }

    public String getCode() {
        return language.getCode();
    }
}
