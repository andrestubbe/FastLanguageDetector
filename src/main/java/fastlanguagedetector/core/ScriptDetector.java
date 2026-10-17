package fastlanguagedetector.core;

import fastlanguagedetector.Language;

import java.lang.Character.UnicodeBlock;

/**
 * Ultra-fast Unicode Script and Block Detector.
 * Runs in nanoseconds by inspecting Unicode code points directly.
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

            if (!Character.isLetter(cp)) continue;
            totalAlpha++;

            UnicodeBlock block = UnicodeBlock.of(cp);
            if (block == null) continue;

            if (block == UnicodeBlock.BASIC_LATIN ||
                block == UnicodeBlock.LATIN_1_SUPPLEMENT ||
                block == UnicodeBlock.LATIN_EXTENDED_A ||
                block == UnicodeBlock.LATIN_EXTENDED_B) {
                latin++;
            } else if (block == UnicodeBlock.CYRILLIC || block == UnicodeBlock.CYRILLIC_SUPPLEMENTARY) {
                cyrillic++;
                // Check unique Cyrillic national characters
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
            } else if (block == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
                       block == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                       block == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) {
                han++;
            } else if (block == UnicodeBlock.HIRAGANA) {
                hiragana++;
            } else if (block == UnicodeBlock.KATAKANA || block == UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS) {
                katakana++;
            } else if (block == UnicodeBlock.HANGUL_SYLLABLES ||
                       block == UnicodeBlock.HANGUL_JAMO ||
                       block == UnicodeBlock.HANGUL_COMPATIBILITY_JAMO) {
                hangul++;
            } else if (block == UnicodeBlock.ARABIC || block == UnicodeBlock.ARABIC_SUPPLEMENT) {
                arabic++;
            } else if (block == UnicodeBlock.GREEK || block == UnicodeBlock.GREEK_EXTENDED) {
                greek++;
            } else if (block == UnicodeBlock.HEBREW) {
                hebrew++;
            } else if (block == UnicodeBlock.DEVANAGARI || block == UnicodeBlock.DEVANAGARI_EXTENDED) {
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
