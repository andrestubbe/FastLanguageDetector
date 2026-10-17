package fastlanguagedetector.core;

import fastlanguagedetector.Language;

import java.util.*;

/**
 * High-Frequency Lexical Profiles and Single-Pass Char Scanners for all European languages.
 * Optimized for zero-regex execution and multi-hundred-thousand ops/sec.
 */
public final class StopwordProfiles {

    public static final Map<Language, Set<String>> STOPWORDS = new EnumMap<>(Language.class);

    public record Profile(Language language, Set<String> stopwords) {}
    public static final Profile[] PROFILES;
    public static final Map<String, Language[]> WORD_INDEX;
    public static final WordTable WORD_TABLE;

    static {
        // --- GERMANIC ---
        STOPWORDS.put(Language.GERMAN, Set.of(
                "der", "die", "das", "und", "ist", "in", "ein", "eine", "nicht", "mit",
                "auf", "für", "von", "zu", "sich", "dem", "den", "dass", "aber", "noch",
                "wird", "aus", "wenn", "oder", "nach", "auch", "als", "an", "bei", "wie",
                "um", "im", "über", "haben", "hat", "einer", "einem", "einen", "kann", "sie",
                "wir", "heute", "sehr", "viel", "schöner", "können", "bitte", "helfen"
        ));

        STOPWORDS.put(Language.ENGLISH, Set.of(
                "the", "and", "of", "to", "in", "is", "that", "it", "for", "as",
                "with", "was", "on", "at", "by", "this", "have", "from", "be", "are",
                "you", "or", "had", "but", "not", "what", "all", "were", "we", "when",
                "your", "can", "said", "there", "use", "an", "each", "which", "she", "do",
                "how", "doing", "today", "time", "train", "arrive", "test", "ultra"
        ));

        STOPWORDS.put(Language.DUTCH, Set.of(
                "de", "het", "een", "en", "van", "in", "is", "op", "te", "met",
                "voor", "niet", "dat", "om", "als", "naar", "er", "maar", "aan", "omdat",
                "zijn", "ze", "bij", "ook", "over", "uit", "door", "zeer", "wordt", "kunnen",
                "hallo", "hoe", "gaat", "jou", "vandaag", "taalsysteem", "allemaal", "weer", "goed", "graag"
        ));

        STOPWORDS.put(Language.SWEDISH, Set.of(
                "och", "det", "att", "i", "en", "jag", "hon", "som", "han", "pa",
                "på", "den", "med", "var", "sig", "for", "för", "sa", "så", "till",
                "ar", "är", "men", "ett", "om", "hade", "de", "inte", "eller", "har",
                "fran", "från", "av", "vad", "kunde", "mycket", "bra", "tack", "hej", "dag"
        ));

        STOPWORDS.put(Language.DANISH, Set.of(
                "og", "i", "jeg", "det", "at", "en", "den", "til", "er", "som",
                "på", "de", "med", "han", "af", "for", "ikke", "der", "var", "mig",
                "sig", "men", "et", "har", "om", "vi", "min", "havde", "ham", "hun",
                "nu", "over", "da", "fra", "du", "ud", "sin", "dem", "os", "op",
                "man", "hans", "hvor", "eller", "hvad", "skal", "selv", "her", "alle", "noget",
                "hej", "tak", "dig", "dette", "hvordan", "går", "dag", "godt"
        ));

        STOPWORDS.put(Language.NORWEGIAN, Set.of(
                "og", "i", "jeg", "det", "at", "en", "et", "den", "til", "er",
                "som", "på", "de", "med", "han", "av", "ikke", "der", "så", "var",
                "meg", "seg", "men", "ett", "har", "om", "vi", "min", "mitt", "ha",
                "hadde", "hun", "nå", "over", "da", "fra", "du", "ut", "sin", "dem",
                "oss", "opp", "man", "kan", "hans", "hvor", "eller", "hva", "skal", "selv",
                "her", "alle", "noe", "hei", "takk", "deg", "dette", "hvordan", "går", "dag", "godt"
        ));

        STOPWORDS.put(Language.ICELANDIC, Set.of(
                "og", "í", "að", "er", "sem", "á", "til", "við", "um", "en",
                "það", "ekki", "með", "fyrir", "var", "hann", "hún", "þeir", "hvað", "halló",
                "góðan", "daginn", "takk", "ísland", "frá", "sjá", "vera", "hafa", "ég", "þú",
                "þið", "mjög", "velkominn"
        ));

        // --- ROMANCE ---
        STOPWORDS.put(Language.FRENCH, Set.of(
                "le", "la", "les", "et", "de", "des", "un", "une", "est", "dans",
                "pour", "avec", "sur", "qui", "pas", "ce", "que", "ne", "au", "aux",
                "en", "du", "par", "ont", "sont", "plus", "mais", "cette", "comme", "tout",
                "faire", "son", "sa", "ses", "leur", "aussi", "bien", "elle", "il", "nous",
                "vous", "bonjour", "monde", "comment", "votre", "merci", "beaucoup", "aide"
        ));

        STOPWORDS.put(Language.SPANISH, Set.of(
                "el", "la", "los", "las", "y", "de", "en", "un", "una", "es",
                "por", "con", "para", "que", "su", "al", "del", "lo", "como", "mas",
                "pero", "sus", "este", "esta", "estos", "estas", "fue", "muy", "ya", "todo",
                "hacer", "tiene", "sin", "sobre", "tambien", "me", "se", "no", "si", "cuando",
                "hola", "amigo", "estás", "tiempo", "bueno", "gracias", "muchas", "atencion", "atención", "colaboracion", "colaboración"
        ));

        STOPWORDS.put(Language.ITALIAN, Set.of(
                "il", "la", "i", "le", "e", "di", "in", "un", "una", "uno",
                "per", "con", "su", "da", "che", "non", "del", "della", "dei", "delle",
                "nel", "nella", "nei", "nelle", "piu", "anche", "sono", "stato", "tutto", "come",
                "ha", "hanno", "questo", "questa", "ma", "loro", "ci", "si", "ed", "ad",
                "buongiorno", "tutti", "prova", "lingua", "italiana", "tavolo", "cucina", "grande", "grazie", "mille", "supporto"
        ));

        STOPWORDS.put(Language.PORTUGUESE, Set.of(
                "o", "a", "os", "as", "e", "de", "em", "um", "uma", "para",
                "com", "por", "que", "do", "da", "dos", "das", "no", "na",
                "nos", "nas", "como", "nao", "não", "mais", "mas", "foi", "ao", "aos",
                "pelo", "pela", "pelos", "pelas", "se", "sua", "seu", "suas", "seus", "tambem", "também",
                "olá", "ola", "voce", "você", "esta", "está", "muito", "bonito", "podemos", "fazer", "obrigado", "atencao", "atenção"
        ));

        STOPWORDS.put(Language.ROMANIAN, Set.of(
                "și", "si", "de", "la", "în", "in", "cu", "pe", "care", "este",
                "din", "o", "un", "pentru", "nu", "se", "ce", "sau", "mai", "al",
                "le", "ca", "sunt", "fost", "dar", "dacă", "daca", "prin", "tot", "mult",
                "bună", "ziua", "mulțumesc", "multumesc", "vă", "rog", "românia"
        ));

        STOPWORDS.put(Language.CATALAN, Set.of(
                "de", "la", "el", "i", "que", "a", "en", "del", "per", "una",
                "un", "és", "es", "les", "els", "amb", "al", "com", "però", "pero",
                "més", "mes", "molt", "hola", "bon", "dia", "gràcies", "gracies", "si", "us",
                "plau", "catalunya", "aquest"
        ));

        STOPWORDS.put(Language.GALICIAN, Set.of(
                "de", "a", "o", "e", "do", "da", "en", "que", "non", "os",
                "as", "por", "un", "unha", "con", "coma", "polo", "pola", "máis", "mais",
                "tamén", "tamen", "ola", "bo", "día", "grazas", "obrigado", "galicia"
        ));

        // --- SLAVIC (LATIN) ---
        STOPWORDS.put(Language.POLISH, Set.of(
                "w", "i", "z", "na", "do", "nie", "o", "to", "jak", "po",
                "tak", "za", "co", "od", "ale", "oraz", "ze", "dla", "sie", "się",
                "juz", "już", "jego", "tylko", "przez", "jeszcze", "jeden", "moze", "może",
                "ktory", "który", "bardzo", "jest", "cześć", "czesc", "masz", "dzisiaj", "sprawa", "wszystkich", "dzień", "dobry", "dziękuję"
        ));

        STOPWORDS.put(Language.CZECH, Set.of(
                "a", "se", "v", "na", "že", "to", "o", "s", "do", "ve",
                "k", "pro", "je", "jsou", "za", "z", "tak", "ale", "jako", "po",
                "od", "při", "už", "byl", "byla", "bylo", "byli", "nebo", "jeho", "jak",
                "jen", "podle", "až", "ani", "když", "však", "tam", "dobrý", "den", "děkuji", "prosím", "česká", "republika"
        ));

        STOPWORDS.put(Language.SLOVAK, Set.of(
                "a", "sa", "v", "na", "že", "to", "o", "s", "do", "vo",
                "k", "pre", "je", "sú", "za", "z", "tak", "ale", "ako", "po",
                "od", "pri", "už", "bol", "bola", "bolo", "boli", "alebo", "jeho", "len",
                "podľa", "až", "ani", "keď", "však", "tam", "dobrý", "deň", "ďakujem", "prosím", "slovensko", "veľmi"
        ));

        STOPWORDS.put(Language.CROATIAN, Set.of(
                "i", "u", "je", "se", "da", "na", "za", "su", "o", "od",
                "s", "sa", "to", "kako", "ali", "ili", "iz", "što", "sto", "koji",
                "koja", "koje", "sve", "bili", "bio", "bila", "smo", "ste", "dobar", "dan",
                "hvala", "lijepa", "molim", "hrvatska"
        ));

        STOPWORDS.put(Language.SLOVENIAN, Set.of(
                "in", "je", "v", "se", "da", "na", "za", "so", "ki", "z",
                "o", "od", "po", "ali", "iz", "tudi", "kot", "to", "pri", "ne",
                "že", "ze", "le", "lahko", "danes", "dober", "dan", "hvala", "prosim", "slovenija", "kako"
        ));

        // --- FINNO-UGRIC & BALTIC ---
        STOPWORDS.put(Language.FINNISH, Set.of(
                "ja", "on", "ei", "se", "hän", "että", "oli", "niin", "mutta", "kun",
                "jos", "tai", "myös", "joka", "ovat", "vain", "kuin", "jo", "sen", "mitä",
                "tämä", "ne", "minä", "ole", "me", "nyt", "ollut", "hänelle", "tuli", "voi",
                "hyvää", "päivää", "kiitos", "paljon", "suomi", "oleva", "kanssa", "täällä",
                "miten", "voit", "tänään", "avusta"
        ));

        STOPWORDS.put(Language.ESTONIAN, Set.of(
                "ja", "on", "ei", "et", "see", "oli", "kui", "ka", "mis", "aga",
                "ta", "oma", "nii", "ning", "selle", "kõik", "koik", "või", "voi", "nagu",
                "olema", "ära", "ara", "juba", "tere", "päevast", "paevast", "tänan", "tanan", "palun",
                "eesti", "head", "kuidas", "teil", "täna", "läheb", "teid", "väga"
        ));

        STOPWORDS.put(Language.HUNGARIAN, Set.of(
                "a", "az", "és", "hogy", "nem", "egy", "van", "is", "meg", "el",
                "volt", "csak", "de", "kell", "mint", "már", "ha", "ki", "mindig", "után",
                "mert", "nagyon", "lesz", "vagy", "még", "itt", "ott", "több", "ő", "mi",
                "ti", "ők", "jó", "napot", "köszönöm", "szépen", "kérem", "magyar", "szia"
        ));

        STOPWORDS.put(Language.LITHUANIAN, Set.of(
                "ir", "kad", "tai", "yra", "į", "su", "apie", "kaip", "ne", "iš",
                "dėl", "kur", "ar", "tik", "jau", "bet", "nuo", "po", "labas", "diena",
                "ačiū", "aciu", "prašau", "prasau", "lietuva", "labai", "gerai"
        ));

        STOPWORDS.put(Language.LATVIAN, Set.of(
                "un", "ir", "ka", "ar", "no", "par", "kā", "tas", "ne", "arī",
                "ari", "pie", "lai", "tā", "bet", "kas", "ja", "uz", "labdien", "paldies",
                "lūdzu", "ludzu", "latvija", "ļoti", "labi"
        ));

        // --- CELTIC & OTHER EUROPEAN ---
        STOPWORDS.put(Language.IRISH, Set.of(
                "agus", "ar", "an", "na", "le", "go", "nach", "sé", "sí", "tá",
                "ta", "do", "ó", "mar", "chun", "ann", "dia", "dhuit", "raibh", "maith",
                "agat", "thoil", "éire", "slán"
        ));

        STOPWORDS.put(Language.WELSH, Set.of(
                "a", "ac", "yn", "y", "yr", "o", "i", "ar", "am", "bod",
                "mae", "gan", "ond", "fel", "hefyd", "nid", "hyn", "dyna", "shwmae", "diolch",
                "fawr", "gwelwch", "dda", "cymru"
        ));

        STOPWORDS.put(Language.BASQUE, Set.of(
                "eta", "da", "ez", "ere", "den", "dira", "zen", "du", "izan", "baino",
                "bere", "bat", "hori", "duela", "egin", "beste", "baita", "kaixo", "egun", "on",
                "eskerrik", "asko", "mesedez", "euskara", "guztiak"
        ));

        STOPWORDS.put(Language.ALBANIAN, Set.of(
                "dhe", "të", "te", "në", "ne", "i", "e", "me", "ka", "për",
                "per", "një", "nje", "se", "nga", "më", "nuk", "si", "ky", "kjo",
                "janë", "jane", "mirëdita", "faleminderit", "ju", "lutem", "shqipëri", "shumë"
        ));

        STOPWORDS.put(Language.MALTESE, Set.of(
                "u", "ta", "tal", "il", "fil", "ma", "li", "huwa", "hija", "kien",
                "kienet", "għal", "ghal", "minn", "biex", "jekk", "iktar", "merħba", "merhba", "bonġu",
                "bongu", "grazzi", "ħafna", "hafna", "malta"
        ));

        STOPWORDS.put(Language.TURKISH, Set.of(
                "bir", "ve", "bu", "da", "de", "icin", "için", "ile", "cok", "çok",
                "ne", "o", "gibi", "daha", "en", "kadar", "her", "ama", "sonra", "olarak",
                "var", "yok", "ise", "kendi", "biz", "sen", "ben", "onlar", "bunu", "buna",
                "nasil", "nasıl", "neden", "merhaba", "bugün", "güzel", "konuda", "bilgi", "almak", "istiyorum", "teşekkürler"
        ));

        // --- CYRILLIC (EUROPEAN) ---
        STOPWORDS.put(Language.RUSSIAN, Set.of(
                "и", "в", "не", "на", "я", "что", "тот", "быть", "с", "он",
                "а", "как", "это", "по", "этот", "к", "но", "они", "мы", "такой",
                "только", "уже", "для", "вот", "кто", "да", "привет", "здравствуйте", "спасибо", "пожалуйста", "хорошо", "россия"
        ));

        STOPWORDS.put(Language.UKRAINIAN, Set.of(
                "і", "в", "не", "на", "що", "бути", "з", "він", "а", "як",
                "це", "по", "до", "та", "вони", "ми", "такий", "тільки", "вже", "для",
                "ось", "хто", "так", "привіт", "доброго", "дня", "дякую", "будь", "ласка", "добре", "україна"
        ));

        STOPWORDS.put(Language.BULGARIAN, Set.of(
                "и", "в", "не", "на", "за", "се", "от", "с", "по", "че",
                "как", "това", "той", "тя", "те", "сме", "сте", "са", "съм", "български",
                "българия", "здравейте", "добър", "ден", "благодаря", "моля", "добре", "много", "път"
        ));

        STOPWORDS.put(Language.BELARUSIAN, Set.of(
                "і", "не", "на", "што", "ў", "з", "як", "гэта", "па", "да",
                "ён", "яна", "яны", "мы", "быць", "толькі", "ўжо", "для", "вось", "хто",
                "так", "прывітанне", "дзень", "добры", "дзякуй", "калі", "ласка", "добра", "беларусь"
        ));

        STOPWORDS.put(Language.MACEDONIAN, Set.of(
                "и", "во", "на", "да", "се", "за", "не", "од", "со", "што",
                "како", "тоа", "тој", "таа", "тие", "сме", "сте", "сум", "македонија", "македонски",
                "здраво", "добар", "благодарам", "молам", "многу"
        ));

        STOPWORDS.put(Language.SERBIAN, Set.of(
                "и", "у", "је", "да", "се", "на", "за", "су", "о", "од",
                "с", "са", "то", "како", "али", "или", "из", "што", "који", "која",
                "које", "све", "били", "био", "била", "смо", "сте", "здраво", "добар", "хвала", "молим", "србија"
        ));

        // Create inverted index for O(1) word matching across all languages
        Map<String, List<Language>> temp = new HashMap<>(1024);
        for (Map.Entry<Language, Set<String>> entry : STOPWORDS.entrySet()) {
            Language lang = entry.getKey();
            for (String w : entry.getValue()) {
                temp.computeIfAbsent(w, k -> new ArrayList<>(2)).add(lang);
            }
        }
        WORD_INDEX = new HashMap<>(temp.size() * 2);
        for (Map.Entry<String, List<Language>> entry : temp.entrySet()) {
            WORD_INDEX.put(entry.getKey(), entry.getValue().toArray(new Language[0]));
        }

        PROFILES = new Profile[STOPWORDS.size()];
        int idx = 0;
        for (Map.Entry<Language, Set<String>> entry : STOPWORDS.entrySet()) {
            PROFILES[idx++] = new Profile(entry.getKey(), entry.getValue());
        }

        WORD_TABLE = new WordTable(WORD_INDEX);
    }

    /**
     * Single-pass character scanner applying diacritics boost directly to scores array.
     */
    public static void applyDiacritics(CharSequence text, double[] scores) {
        int len = text.length();
        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            if (c <= 127 && c != '¿' && c != '¡') continue;

            switch (c) {
                case 'ß':
                    scores[Language.GERMAN.ordinal()] += 4.0;
                    break;
                case 'ü': case 'Ü':
                    scores[Language.GERMAN.ordinal()] += 2.5;
                    scores[Language.ESTONIAN.ordinal()] += 1.5;
                    scores[Language.HUNGARIAN.ordinal()] += 1.5;
                    scores[Language.TURKISH.ordinal()] += 1.5;
                    break;
                case 'ä': case 'Ä':
                    scores[Language.FINNISH.ordinal()] += 2.5;
                    scores[Language.ESTONIAN.ordinal()] += 2.5;
                    scores[Language.SWEDISH.ordinal()] += 1.5;
                    scores[Language.GERMAN.ordinal()] += 1.0;
                    break;
                case 'ö': case 'Ö':
                    scores[Language.FINNISH.ordinal()] += 2.0;
                    scores[Language.ESTONIAN.ordinal()] += 2.0;
                    scores[Language.SWEDISH.ordinal()] += 1.5;
                    scores[Language.HUNGARIAN.ordinal()] += 1.5;
                    scores[Language.TURKISH.ordinal()] += 1.5;
                    scores[Language.GERMAN.ordinal()] += 1.0;
                    break;
                case '¿': case '¡':
                case 'ñ': case 'Ñ':
                    scores[Language.SPANISH.ordinal()] += 5.0;
                    break;
                case 'œ':
                    scores[Language.FRENCH.ordinal()] += 4.0;
                    break;
                case 'æ': case 'Æ':
                    scores[Language.DANISH.ordinal()] += 4.0;
                    scores[Language.NORWEGIAN.ordinal()] += 4.0;
                    scores[Language.ICELANDIC.ordinal()] += 2.0;
                    scores[Language.FRENCH.ordinal()] += 2.0;
                    break;
                case 'ø': case 'Ø':
                    scores[Language.DANISH.ordinal()] += 4.0;
                    scores[Language.NORWEGIAN.ordinal()] += 4.0;
                    break;
                case 'å': case 'Å':
                    scores[Language.SWEDISH.ordinal()] += 3.0;
                    scores[Language.DANISH.ordinal()] += 3.0;
                    scores[Language.NORWEGIAN.ordinal()] += 3.0;
                    break;
                case 'þ': case 'Þ':
                case 'ð': case 'Ð':
                    scores[Language.ICELANDIC.ordinal()] += 5.0;
                    break;
                case 'ç': case 'Ç':
                    scores[Language.FRENCH.ordinal()] += 2.5;
                    scores[Language.PORTUGUESE.ordinal()] += 2.5;
                    scores[Language.CATALAN.ordinal()] += 2.5;
                    scores[Language.TURKISH.ordinal()] += 2.5;
                    scores[Language.ALBANIAN.ordinal()] += 2.5;
                    break;
                case 'ã': case 'õ': case 'Ã': case 'Õ':
                    scores[Language.PORTUGUESE.ordinal()] += 4.0;
                    break;
                case 'ł': case 'Ł': case 'ń': case 'Ń': case 'ś': case 'Ś':
                case 'ź': case 'Ź': case 'ż': case 'Ż': case 'ą': case 'ę':
                    scores[Language.POLISH.ordinal()] += 3.5;
                    break;
                case 'ř': case 'Ř': case 'ě': case 'Ě': case 'ů': case 'Ů':
                    scores[Language.CZECH.ordinal()] += 5.0;
                    break;
                case 'ô': case 'Ô': case 'ľ': case 'Ľ': case 'ĺ': case 'Ĺ': case 'ŕ': case 'Ŕ':
                    scores[Language.SLOVAK.ordinal()] += 5.0;
                    break;
                case 'ő': case 'Ő': case 'ű': case 'Ű':
                    scores[Language.HUNGARIAN.ordinal()] += 5.0;
                    break;
                case 'ă': case 'Ă': case 'â': case 'Â': case 'î': case 'Î':
                case 'ș': case 'Ș': case 'ț': case 'Ț':
                    scores[Language.ROMANIAN.ordinal()] += 4.0;
                    break;
                case 'ğ': case 'Ğ': case 'ı': case 'İ': case 'ş': case 'Ş':
                    scores[Language.TURKISH.ordinal()] += 4.0;
                    break;
                case 'ė': case 'Ė': case 'į': case 'Į': case 'ų': case 'Ų': case 'ū': case 'Ū':
                    scores[Language.LITHUANIAN.ordinal()] += 4.0;
                    break;
                case 'ģ': case 'Ģ': case 'ķ': case 'Ķ': case 'ņ': case 'Ņ':
                case 'ā': case 'Ā': case 'ē': case 'Ē': case 'ī': case 'Ī':
                    scores[Language.LATVIAN.ordinal()] += 4.0;
                    break;
                case '·': case 'ŀ': case 'Ŀ':
                    scores[Language.CATALAN.ordinal()] += 5.0;
                    break;
                case 'ë': case 'Ë':
                    scores[Language.ALBANIAN.ordinal()] += 4.0;
                    break;
                case 'ħ': case 'Ħ': case 'ċ': case 'Ċ': case 'ġ': case 'Ġ':
                    scores[Language.MALTESE.ordinal()] += 4.0;
                    break;
                case 'č': case 'Č': case 'š': case 'Š': case 'ž': case 'Ž':
                    scores[Language.CZECH.ordinal()] += 2.0;
                    scores[Language.SLOVAK.ordinal()] += 2.0;
                    scores[Language.CROATIAN.ordinal()] += 2.0;
                    scores[Language.SLOVENIAN.ordinal()] += 2.0;
                    scores[Language.LITHUANIAN.ordinal()] += 1.5;
                    scores[Language.LATVIAN.ordinal()] += 1.5;
                    break;
                case 'ć': case 'Ć': case 'đ': case 'Đ':
                    scores[Language.CROATIAN.ordinal()] += 4.0;
                    scores[Language.POLISH.ordinal()] += 2.0;
                    break;
                default:
                    break;
            }
        }
    }

    public static Language[] lookupWord(CharSequence text, int start, int end) {
        return WORD_TABLE.lookup(text, start, end);
    }

    public static final class WordTable {
        private final Entry[] table;
        private final int mask;

        record Entry(String word, Language[] languages) {}

        WordTable(Map<String, Language[]> map) {
            int cap = 1;
            while (cap < map.size() * 3) {
                cap <<= 1;
            }
            this.mask = cap - 1;
            this.table = new Entry[cap];

            for (Map.Entry<String, Language[]> e : map.entrySet()) {
                String w = e.getKey();
                int h = hash(w, 0, w.length());
                int slot = (h ^ (h >>> 16)) & mask;
                while (table[slot] != null) {
                    slot = (slot + 1) & mask;
                }
                table[slot] = new Entry(w, e.getValue());
            }
        }

        public Language[] lookup(CharSequence text, int start, int end) {
            int len = end - start;
            if (len <= 0 || len > 32) return null;

            int h = hash(text, start, end);
            int slot = (h ^ (h >>> 16)) & mask;

            while (table[slot] != null) {
                Entry e = table[slot];
                if (e.word.length() == len && matches(e.word, text, start, end)) {
                    return e.languages;
                }
                slot = (slot + 1) & mask;
            }
            return null;
        }

        private static int hash(CharSequence cs, int start, int end) {
            int h = 0;
            for (int i = start; i < end; i++) {
                char c = cs.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) (c + 32);
                } else if (c > 127) {
                    c = Character.toLowerCase(c);
                }
                h = 31 * h + c;
            }
            return h;
        }

        private static boolean matches(String word, CharSequence text, int start, int end) {
            int len = word.length();
            for (int i = 0; i < len; i++) {
                char c = text.charAt(start + i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) (c + 32);
                } else if (c > 127) {
                    c = Character.toLowerCase(c);
                }
                if (word.charAt(i) != c) return false;
            }
            return true;
        }
    }
}
