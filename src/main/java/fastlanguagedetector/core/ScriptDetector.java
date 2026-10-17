package fastlanguagedetector.core;

import fastlanguagedetector.Language;

/**
 * Ultra-fast Unicode Script and Block Detector.
 * Pure primitive integer range checks, zero allocations, zero UnicodeBlock overhead.
 */
public final class ScriptDetector {

    public record ScriptAnalysis(
            Language.Script primaryScript,
            Language detectedLanguage,
            int scriptCharCount,
            int totalCharCount
    ) {}

    public static ScriptAnalysis analyze(CharSequence text) {
        if (text == null || text.length() == 0) {
            return new ScriptAnalysis(Language.Script.LATIN, Language.UNKNOWN, 0, 0);
        }

        int cyrillic = 0;
        int ukrainianChars = 0;
        int russianChars = 0;
        int belarusianChars = 0;
        int macedonianChars = 0;
        int serbianChars = 0;
        int bulgarianHardSign = 0;

        int han = 0;
        int hiragana = 0;
        int katakana = 0;
        int hangul = 0;
        int arabic = 0;
        int greek = 0;
        int hebrew = 0;
        int devanagari = 0;
        int latin = 0;
        int totalAlpha = 0;

        int len = text.length();
        for (int i = 0; i < len; ) {
            int cp = Character.codePointAt(text, i);
            i += Character.charCount(cp);

            // Fast-path ASCII letters
            if (cp <= 0x007F) {
                if ((cp >= 'a' && cp <= 'z') || (cp >= 'A' && cp <= 'Z')) {
                    totalAlpha++;
                    latin++;
                }
                continue;
            }

            if (!Character.isLetter(cp)) continue;
            totalAlpha++;

            // Direct primitive range checks
            if (cp <= 0x024F || (cp >= 0x1E00 && cp <= 0x1EFF)) {
                latin++;
            } else if (cp >= 0x0400 && cp <= 0x052F) {
                cyrillic++;
                if (cp == 'ў' || cp == 'Ў') {
                    belarusianChars++;
                } else if (cp == 'ѓ' || cp == 'Ѓ' || cp == 'ќ' || cp == 'Ќ' || cp == 'ѕ' || cp == 'Ѕ') {
                    macedonianChars++;
                } else if (cp == 'ђ' || cp == 'Ђ' || cp == 'ћ' || cp == 'Ћ') {
                    serbianChars++;
                } else if (cp == 'ї' || cp == 'Ї' || cp == 'є' || cp == 'Є' || cp == 'ґ' || cp == 'Ґ') {
                    ukrainianChars += 3;
                } else if (cp == 'і' || cp == 'І') {
                    ukrainianChars++;
                } else if (cp == 'ы' || cp == 'Ы' || cp == 'э' || cp == 'Э' || cp == 'ё' || cp == 'Ё') {
                    russianChars++;
                } else if (cp == 'ъ' || cp == 'Ъ') {
                    bulgarianHardSign++;
                }
            } else if ((cp >= 0x4E00 && cp <= 0x9FFF) || (cp >= 0x3400 && cp <= 0x4DBF) || (cp >= 0xF900 && cp <= 0xFAFF)) {
                han++;
            } else if (cp >= 0x3040 && cp <= 0x309F) {
                hiragana++;
            } else if ((cp >= 0x30A0 && cp <= 0x30FF) || (cp >= 0x31F0 && cp <= 0x31FF)) {
                katakana++;
            } else if ((cp >= 0xAC00 && cp <= 0xD7AF) || (cp >= 0x1100 && cp <= 0x11FF) || (cp >= 0x3130 && cp <= 0x318F)) {
                hangul++;
            } else if ((cp >= 0x0600 && cp <= 0x06FF) || (cp >= 0x0750 && cp <= 0x077F) || (cp >= 0x08A0 && cp <= 0x08FF)) {
                arabic++;
            } else if ((cp >= 0x0370 && cp <= 0x03FF) || (cp >= 0x1F00 && cp <= 0x1FFF)) {
                greek++;
            } else if (cp >= 0x0590 && cp <= 0x05FF) {
                hebrew++;
            } else if (cp >= 0x0900 && cp <= 0x097F) {
                devanagari++;
            }
        }

        if (totalAlpha == 0) {
            return new ScriptAnalysis(Language.Script.LATIN, Language.UNKNOWN, 0, 0);
        }

        // Japanese check (Hiragana or Katakana presence indicates Japanese immediately)
        if (hiragana > 0 || katakana > 0) {
            return new ScriptAnalysis(Language.Script.JAPANESE, Language.JAPANESE, hiragana + katakana + han, totalAlpha);
        }
        if (hangul > 0) {
            return new ScriptAnalysis(Language.Script.KOREAN, Language.KOREAN, hangul, totalAlpha);
        }
        if (han > 0 && han >= totalAlpha * 0.30) {
            return new ScriptAnalysis(Language.Script.CHINESE, Language.CHINESE, han, totalAlpha);
        }
        if (cyrillic >= totalAlpha * 0.30) {
            if (belarusianChars > 0) {
                return new ScriptAnalysis(Language.Script.CYRILLIC, Language.BELARUSIAN, cyrillic, totalAlpha);
            }
            if (macedonianChars > 0) {
                return new ScriptAnalysis(Language.Script.CYRILLIC, Language.MACEDONIAN, cyrillic, totalAlpha);
            }
            if (serbianChars > 0) {
                return new ScriptAnalysis(Language.Script.CYRILLIC, Language.SERBIAN, cyrillic, totalAlpha);
            }
            if (ukrainianChars > 0 && ukrainianChars >= russianChars) {
                return new ScriptAnalysis(Language.Script.CYRILLIC, Language.UKRAINIAN, cyrillic, totalAlpha);
            }
            if (russianChars > 0) {
                return new ScriptAnalysis(Language.Script.CYRILLIC, Language.RUSSIAN, cyrillic, totalAlpha);
            }
            if (bulgarianHardSign > 0) {
                return new ScriptAnalysis(Language.Script.CYRILLIC, Language.BULGARIAN, cyrillic, totalAlpha);
            }
            // If common Cyrillic, let lexical matcher disambiguate or default
            return new ScriptAnalysis(Language.Script.CYRILLIC, Language.UNKNOWN, cyrillic, totalAlpha);
        }
        if (arabic >= totalAlpha * 0.30) {
            return new ScriptAnalysis(Language.Script.ARABIC, Language.ARABIC, arabic, totalAlpha);
        }
        if (greek >= totalAlpha * 0.30) {
            return new ScriptAnalysis(Language.Script.GREEK, Language.GREEK, greek, totalAlpha);
        }
        if (hebrew >= totalAlpha * 0.30) {
            return new ScriptAnalysis(Language.Script.HEBREW, Language.HEBREW, hebrew, totalAlpha);
        }
        if (devanagari >= totalAlpha * 0.30) {
            return new ScriptAnalysis(Language.Script.DEVANAGARI, Language.HINDI, devanagari, totalAlpha);
        }

        return new ScriptAnalysis(Language.Script.LATIN, Language.UNKNOWN, latin, totalAlpha);
    }
}
