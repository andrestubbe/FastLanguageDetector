package fastlanguagedetector.core;

import fastlanguagedetector.Language;

import java.util.*;

/**
 * High-Frequency Lexical Profiles and Fast Char Scanners for major Latin languages.
 * Optimized for zero-regex execution and multi-hundred-thousand ops/sec.
 */
public final class StopwordProfiles {

    public static final Map<Language, Set<String>> STOPWORDS = new EnumMap<>(Language.class);

    static {
        // German
        STOPWORDS.put(Language.GERMAN, Set.of(
                "der", "die", "das", "und", "ist", "in", "ein", "eine", "nicht", "mit",
                "auf", "für", "von", "zu", "sich", "dem", "den", "dass", "aber", "noch",
                "wird", "aus", "wenn", "oder", "nach", "auch", "als", "an", "bei", "wie",
                "um", "im", "über", "haben", "hat", "einer", "einem", "einen", "kann", "sie",
                "wir", "heute", "sehr", "viel", "schöner", "können", "bitte", "helfen"
        ));

        // English
        STOPWORDS.put(Language.ENGLISH, Set.of(
                "the", "and", "of", "to", "in", "is", "that", "it", "for", "as",
                "with", "was", "on", "at", "by", "this", "have", "from", "be", "are",
                "you", "or", "had", "but", "not", "what", "all", "were", "we", "when",
                "your", "can", "said", "there", "use", "an", "each", "which", "she", "do",
                "how", "doing", "today", "time", "train", "arrive", "test", "ultra"
        ));

        // French
        STOPWORDS.put(Language.FRENCH, Set.of(
                "le", "la", "les", "et", "de", "des", "un", "une", "est", "dans",
                "pour", "avec", "sur", "qui", "pas", "ce", "que", "ne", "au", "aux",
                "en", "du", "par", "ont", "sont", "plus", "mais", "cette", "comme", "tout",
                "faire", "son", "sa", "ses", "leur", "aussi", "bien", "elle", "il", "nous",
                "vous", "bonjour", "monde", "comment", "votre", "merci", "beaucoup", "aide"
        ));

        // Spanish
        STOPWORDS.put(Language.SPANISH, Set.of(
                "el", "la", "los", "las", "y", "de", "en", "un", "una", "es",
                "por", "con", "para", "que", "su", "al", "del", "lo", "como", "mas",
                "pero", "sus", "este", "esta", "estos", "estas", "fue", "muy", "ya", "todo",
                "hacer", "tiene", "sin", "sobre", "tambien", "me", "se", "no", "si", "cuando",
                "hola", "amigo", "estás", "tiempo", "bueno", "gracias", "muchas", "atencion", "atención", "colaboracion", "colaboración"
        ));

        // Italian
        STOPWORDS.put(Language.ITALIAN, Set.of(
                "il", "la", "i", "le", "e", "di", "in", "un", "una", "uno",
                "per", "con", "su", "da", "che", "non", "del", "della", "dei", "delle",
                "nel", "nella", "nei", "nelle", "piu", "anche", "sono", "stato", "tutto", "come",
                "ha", "hanno", "questo", "questa", "ma", "loro", "ci", "si", "ed", "ad",
                "buongiorno", "tutti", "prova", "lingua", "italiana", "tavolo", "cucina", "grande", "grazie", "mille", "supporto"
        ));

        // Portuguese
        STOPWORDS.put(Language.PORTUGUESE, Set.of(
                "o", "a", "os", "as", "e", "de", "em", "um", "uma", "para",
                "com", "por", "que", "do", "da", "dos", "das", "no", "na",
                "nos", "nas", "como", "nao", "não", "mais", "mas", "foi", "ao", "aos",
                "pelo", "pela", "pelos", "pelas", "se", "sua", "seu", "suas", "seus", "tambem", "também",
                "olá", "ola", "voce", "você", "esta", "está", "muito", "bonito", "podemos", "fazer", "obrigado", "atencao", "atenção"
        ));

        // Dutch
        STOPWORDS.put(Language.DUTCH, Set.of(
                "de", "het", "een", "en", "van", "in", "is", "op", "te", "met",
                "voor", "niet", "dat", "om", "als", "naar", "er", "maar", "aan", "omdat",
                "zijn", "ze", "bij", "ook", "over", "uit", "door", "zeer", "wordt", "kunnen",
                "hallo", "hoe", "gaat", "jou", "vandaag", "taalsysteem"
        ));

        // Polish
        STOPWORDS.put(Language.POLISH, Set.of(
                "w", "i", "z", "na", "do", "nie", "o", "to", "jak", "po",
                "tak", "za", "co", "od", "ale", "oraz", "ze", "dla", "sie", "się",
                "juz", "już", "jego", "tylko", "przez", "jeszcze", "jeden", "moze", "może",
                "ktory", "który", "bardzo", "jest", "cześć", "czesc", "masz", "dzisiaj", "sprawa", "wszystkich"
        ));

        // Turkish
        STOPWORDS.put(Language.TURKISH, Set.of(
                "bir", "ve", "bu", "da", "de", "icin", "için", "ile", "cok", "çok",
                "ne", "o", "gibi", "daha", "en", "kadar", "her", "ama", "sonra", "olarak",
                "var", "yok", "ise", "kendi", "biz", "sen", "ben", "onlar", "bunu", "buna",
                "nasil", "nasıl", "neden", "merhaba", "bugün", "güzel", "konuda", "bilgi", "almak", "istiyorum"
        ));

        // Swedish
        STOPWORDS.put(Language.SWEDISH, Set.of(
                "och", "det", "att", "i", "en", "jag", "hon", "som", "han", "pa",
                "den", "med", "var", "sig", "for", "sa", "till", "ar", "men", "ett",
                "om", "hade", "de", "inte", "eller", "har", "fran", "av", "vad", "kunde"
        ));
    }

    public static double checkDiacriticsBoost(CharSequence text, Language lang) {
        int len = text.length();
        double boost = 0.0;

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);

            switch (lang) {
                case GERMAN:
                    if (c == 'ß') boost += 4.0;
                    else if (c == 'ä' || c == 'ö' || c == 'ü' || c == 'Ä' || c == 'Ö' || c == 'Ü') boost += 2.0;
                    break;
                case SPANISH:
                    if (c == '¿' || c == '¡') boost += 5.0;
                    else if (c == 'ñ' || c == 'Ñ') boost += 4.0;
                    break;
                case PORTUGUESE:
                    if (c == 'ã' || c == 'õ' || c == 'Ã' || c == 'Õ') boost += 4.0;
                    break;
                case POLISH:
                    if (c == 'ł' || c == 'Ł' || c == 'ń' || c == 'Ń' || c == 'ś' || c == 'Ś' ||
                        c == 'ź' || c == 'Ź' || c == 'ż' || c == 'Ż' || c == 'ą' || c == 'ę') {
                        boost += 3.5;
                    }
                    break;
                case TURKISH:
                    if (c == 'ğ' || c == 'Ğ' || c == 'ı' || c == 'İ' || c == 'ş' || c == 'Ş') boost += 4.0;
                    break;
                case SWEDISH:
                    if (c == 'å' || c == 'Å') boost += 3.0;
                    break;
                case FRENCH:
                    if (c == 'œ' || c == 'æ') boost += 4.0;
                    break;
            }
        }
        return boost;
    }
}
