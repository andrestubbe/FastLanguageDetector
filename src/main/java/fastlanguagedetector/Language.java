package fastlanguagedetector;

/**
 * Supported Languages in FastLanguageDetector.
 */
public enum Language {

    // Major Latin
    GERMAN("de", "de-DE", "German", Script.LATIN),
    ENGLISH("en", "en-US", "English", Script.LATIN),
    FRENCH("fr", "fr-FR", "French", Script.LATIN),
    SPANISH("es", "es-ES", "Spanish", Script.LATIN),
    ITALIAN("it", "it-IT", "Italian", Script.LATIN),
    PORTUGUESE("pt", "pt-PT", "Portuguese", Script.LATIN),
    DUTCH("nl", "nl-NL", "Dutch", Script.LATIN),
    POLISH("pl", "pl-PL", "Polish", Script.LATIN),
    TURKISH("tr", "tr-TR", "Turkish", Script.LATIN),
    SWEDISH("sv", "sv-SE", "Swedish", Script.LATIN),

    // Non-Latin Script
    RUSSIAN("ru", "ru-RU", "Russian", Script.CYRILLIC),
    UKRAINIAN("uk", "uk-UA", "Ukrainian", Script.CYRILLIC),
    CHINESE("zh", "zh-CN", "Chinese", Script.CHINESE),
    JAPANESE("ja", "ja-JP", "Japanese", Script.JAPANESE),
    KOREAN("ko", "ko-KR", "Korean", Script.KOREAN),
    ARABIC("ar", "ar-SA", "Arabic", Script.ARABIC),
    GREEK("el", "el-GR", "Greek", Script.GREEK),
    HEBREW("he", "he-IL", "Hebrew", Script.HEBREW),
    HINDI("hi", "hi-IN", "Hindi", Script.DEVANAGARI),

    // Fallback Unknown
    UNKNOWN("und", "und", "Unknown", Script.LATIN);

    private final String code;
    private final String bcp47;
    private final String displayName;
    private final Script script;

    Language(String code, String bcp47, String displayName, Script script) {
        this.code = code;
        this.bcp47 = bcp47;
        this.displayName = displayName;
        this.script = script;
    }

    public String getCode() {
        return code;
    }

    public String getBcp47() {
        return bcp47;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Script getScript() {
        return script;
    }

    public static Language fromCode(String code) {
        if (code == null) return UNKNOWN;
        String clean = code.trim().toLowerCase();
        for (Language l : values()) {
            if (l.code.equals(clean) || l.bcp47.equalsIgnoreCase(clean)) {
                return l;
            }
        }
        return UNKNOWN;
    }

    public enum Script {
        LATIN,
        CYRILLIC,
        CHINESE,
        JAPANESE,
        KOREAN,
        ARABIC,
        GREEK,
        HEBREW,
        DEVANAGARI
    }
}
