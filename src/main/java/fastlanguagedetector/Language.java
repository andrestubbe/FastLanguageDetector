package fastlanguagedetector;

/**
 * Supported Languages in FastLanguageDetector.
 * Covers all official European languages, key regional European languages, and major global languages.
 */
public enum Language {

    // Germanic (European)
    GERMAN("de", "de-DE", "German", Script.LATIN),
    ENGLISH("en", "en-US", "English", Script.LATIN),
    DUTCH("nl", "nl-NL", "Dutch", Script.LATIN),
    SWEDISH("sv", "sv-SE", "Swedish", Script.LATIN),
    DANISH("da", "da-DK", "Danish", Script.LATIN),
    NORWEGIAN("no", "no-NO", "Norwegian", Script.LATIN),
    ICELANDIC("is", "is-IS", "Icelandic", Script.LATIN),

    // Romance (European)
    FRENCH("fr", "fr-FR", "French", Script.LATIN),
    SPANISH("es", "es-ES", "Spanish", Script.LATIN),
    ITALIAN("it", "it-IT", "Italian", Script.LATIN),
    PORTUGUESE("pt", "pt-PT", "Portuguese", Script.LATIN),
    ROMANIAN("ro", "ro-RO", "Romanian", Script.LATIN),
    CATALAN("ca", "ca-ES", "Catalan", Script.LATIN),
    GALICIAN("gl", "gl-ES", "Galician", Script.LATIN),

    // Slavic (Latin Script - European)
    POLISH("pl", "pl-PL", "Polish", Script.LATIN),
    CZECH("cs", "cs-CZ", "Czech", Script.LATIN),
    SLOVAK("sk", "sk-SK", "Slovak", Script.LATIN),
    CROATIAN("hr", "hr-HR", "Croatian", Script.LATIN),
    SLOVENIAN("sl", "sl-SI", "Slovenian", Script.LATIN),

    // Slavic (Cyrillic Script - European)
    RUSSIAN("ru", "ru-RU", "Russian", Script.CYRILLIC),
    UKRAINIAN("uk", "uk-UA", "Ukrainian", Script.CYRILLIC),
    BULGARIAN("bg", "bg-BG", "Bulgarian", Script.CYRILLIC),
    BELARUSIAN("be", "be-BY", "Belarusian", Script.CYRILLIC),
    MACEDONIAN("mk", "mk-MK", "Macedonian", Script.CYRILLIC),
    SERBIAN("sr", "sr-RS", "Serbian", Script.CYRILLIC),

    // Finno-Ugric / Baltic (European)
    FINNISH("fi", "fi-FI", "Finnish", Script.LATIN),
    ESTONIAN("et", "et-EE", "Estonian", Script.LATIN),
    HUNGARIAN("hu", "hu-HU", "Hungarian", Script.LATIN),
    LITHUANIAN("lt", "lt-LT", "Lithuanian", Script.LATIN),
    LATVIAN("lv", "lv-LV", "Latvian", Script.LATIN),

    // Celtic & Other European
    IRISH("ga", "ga-IE", "Irish", Script.LATIN),
    WELSH("cy", "cy-GB", "Welsh", Script.LATIN),
    BASQUE("eu", "eu-ES", "Basque", Script.LATIN),
    ALBANIAN("sq", "sq-AL", "Albanian", Script.LATIN),
    MALTESE("mt", "mt-MT", "Maltese", Script.LATIN),
    TURKISH("tr", "tr-TR", "Turkish", Script.LATIN),
    GREEK("el", "el-GR", "Greek", Script.GREEK),

    // Global Non-European
    CHINESE("zh", "zh-CN", "Chinese", Script.CHINESE),
    JAPANESE("ja", "ja-JP", "Japanese", Script.JAPANESE),
    KOREAN("ko", "ko-KR", "Korean", Script.KOREAN),
    ARABIC("ar", "ar-SA", "Arabic", Script.ARABIC),
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
