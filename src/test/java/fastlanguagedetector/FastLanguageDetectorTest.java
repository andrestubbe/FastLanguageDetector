package fastlanguagedetector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FastLanguageDetectorTest {

    @Test
    public void testGermanDetection() {
        assertEquals(Language.GERMAN, FastLanguageDetector.detect("Hallo, wie geht es dir heute?"));
        assertEquals(Language.GERMAN, FastLanguageDetector.detect("Das ist ein schöner Tag mit viel Sonnenschein."));
        assertEquals(Language.GERMAN, FastLanguageDetector.detect("Wir müssen die Arbeit heute noch fertigstellen."));
        assertEquals("de-DE", FastLanguageDetector.detectLocale("Können Sie mir bitte helfen?"));
    }

    @Test
    public void testEnglishDetection() {
        assertEquals(Language.ENGLISH, FastLanguageDetector.detect("Hello, how are you doing today?"));
        assertEquals(Language.ENGLISH, FastLanguageDetector.detect("This is an ultra-fast language detection test for Java."));
        assertEquals(Language.ENGLISH, FastLanguageDetector.detect("Artificial intelligence is transforming software engineering."));
        assertEquals("en-US", FastLanguageDetector.detectLocale("What time does the train arrive?"));
    }

    @Test
    public void testFrenchDetection() {
        assertEquals(Language.FRENCH, FastLanguageDetector.detect("Bonjour tout le monde, comment allez-vous aujourd'hui?"));
        assertEquals(Language.FRENCH, FastLanguageDetector.detect("C'est une très belle journée dans la ville de Paris."));
        assertEquals("fr-FR", FastLanguageDetector.detectLocale("Merci beaucoup pour votre aide précieuse."));
    }

    @Test
    public void testSpanishDetection() {
        assertEquals(Language.SPANISH, FastLanguageDetector.detect("Hola amigo, ¿cómo estás?"));
        assertEquals(Language.SPANISH, FastLanguageDetector.detect("El tiempo está muy bueno hoy en Madrid."));
        assertEquals("es-ES", FastLanguageDetector.detectLocale("Muchas gracias por su atención y colaboración."));
    }

    @Test
    public void testItalianDetection() {
        assertEquals(Language.ITALIAN, FastLanguageDetector.detect("Buongiorno a tutti, questa è una prova di lingua."));
        assertEquals(Language.ITALIAN, FastLanguageDetector.detect("Il gatto è sul tavolo nella cucina grande."));
        assertEquals("it-IT", FastLanguageDetector.detectLocale("Grazie mille per il vostro supporto costante."));
    }

    @Test
    public void testPortugueseDetection() {
        assertEquals(Language.PORTUGUESE, FastLanguageDetector.detect("Olá, como você está? O dia está muito bonito."));
        assertEquals(Language.PORTUGUESE, FastLanguageDetector.detect("Não podemos esquecer de fazer as tarefas de hoje."));
        assertEquals("pt-PT", FastLanguageDetector.detectLocale("Muito obrigado pela sua atenção."));
    }

    @Test
    public void testDutchDetection() {
        assertEquals(Language.DUTCH, FastLanguageDetector.detect("Hallo, hoe gaat het met jou vandaag?"));
        assertEquals(Language.DUTCH, FastLanguageDetector.detect("Dit is een test van het Nederlandse taalsysteem."));
    }

    @Test
    public void testPolishDetection() {
        assertEquals(Language.POLISH, FastLanguageDetector.detect("Cześć, jak się masz dzisiaj w Warszawie?"));
        assertEquals(Language.POLISH, FastLanguageDetector.detect("To jest bardzo ważna sprawa dla nas wszystkich."));
    }

    @Test
    public void testTurkishDetection() {
        assertEquals(Language.TURKISH, FastLanguageDetector.detect("Merhaba nasılsın bugün çok güzel bir gün?"));
        assertEquals(Language.TURKISH, FastLanguageDetector.detect("Bu konuda daha fazla bilgi almak istiyorum."));
    }

    @Test
    public void testEuropeanLanguages() {
        // Nordic
        assertEquals(Language.SWEDISH, FastLanguageDetector.detect("Hej, hur mår du idag? Det är mycket bra väder här."));
        assertEquals(Language.DANISH, FastLanguageDetector.detect("Hej, hvordan har du det i dag? Det er et godt vejr."));
        assertEquals(Language.NORWEGIAN, FastLanguageDetector.detect("Hei, hvordan går det med deg i dag? Dette er en fin dag."));
        assertEquals(Language.ICELANDIC, FastLanguageDetector.detect("Góðan daginn, hvernig hefur þú það í dag? Takk fyrir."));
        assertEquals(Language.FINNISH, FastLanguageDetector.detect("Hyvää päivää! Miten voit tänään? Kiitos paljon avusta."));

        // Slavic (Latin)
        assertEquals(Language.CZECH, FastLanguageDetector.detect("Dobrý den, jak se máte dnes v Praze? Děkuji za pomoc."));
        assertEquals(Language.SLOVAK, FastLanguageDetector.detect("Dobrý deň, ako sa máte dnes v Bratislave? Veľmi pekne ďakujem."));
        assertEquals(Language.CROATIAN, FastLanguageDetector.detect("Dobar dan, kako ste danas u Zagrebu? Hvala vam lijepa."));
        assertEquals(Language.SLOVENIAN, FastLanguageDetector.detect("Dober dan, kako ste danes v Ljubljani? Hvala lepa za pomoč."));

        // Baltic & Finno-Ugric
        assertEquals(Language.ESTONIAN, FastLanguageDetector.detect("Tere päevast! Kuidas teil täna läheb? Tänan teid väga."));
        assertEquals(Language.HUNGARIAN, FastLanguageDetector.detect("Jó napot kívánok! Hogy vagy ma? Nagyon szépen köszönöm a segítséget."));
        assertEquals(Language.LITHUANIAN, FastLanguageDetector.detect("Laba diena, kaip jums šiandien sekasi? Labai ačiū už pagalbą."));
        assertEquals(Language.LATVIAN, FastLanguageDetector.detect("Labdien! Kā jums šodien klājas? Liels paldies par palīdzību."));

        // Romance & Regional
        assertEquals(Language.ROMANIAN, FastLanguageDetector.detect("Bună ziua, ce mai faceți astăzi? Vă mulțumesc foarte mult."));
        assertEquals(Language.CATALAN, FastLanguageDetector.detect("Bon dia a tothom! Com esteu avui a Catalunya? Moltes gràcies per tot."));
        assertEquals(Language.GALICIAN, FastLanguageDetector.detect("Bo día a todos! Como estades hoxe en Galicia? Moitas grazas."));
        assertEquals(Language.BASQUE, FastLanguageDetector.detect("Egun on guztioi! Kaixo, nola zaude gaur? Eskerrik asko zure laguntzagatik."));

        // Celtic & Mediterranean
        assertEquals(Language.ALBANIAN, FastLanguageDetector.detect("Mirëdita, si jeni sot në Tiranë? Faleminderit shumë për ndihmën."));
        assertEquals(Language.MALTESE, FastLanguageDetector.detect("Bonġu lil kulħadd! Kif intom illum f'Malta? Grazzi ħafna."));
        assertEquals(Language.IRISH, FastLanguageDetector.detect("Dia dhuit! Conas atá tú inniu? Go raibh míle maith agat."));
        assertEquals(Language.WELSH, FastLanguageDetector.detect("Shwmae! Sut wyt ti heddiw yng Nghymru? Diolch yn fawr iawn."));

        // Cyrillic European
        assertEquals(Language.BULGARIAN, FastLanguageDetector.detect("Здравейте, как сте днес? Много благодаря за помощта в България."));
        assertEquals(Language.BELARUSIAN, FastLanguageDetector.detect("Дзень добры! Як вашы справы сёння? Дзякуй вялікі за дапамогу ў Беларусі."));
        assertEquals(Language.MACEDONIAN, FastLanguageDetector.detect("Здраво, како сте денес? Ви благодарам многу за помошта во Македонија."));
        assertEquals(Language.SERBIAN, FastLanguageDetector.detect("Здраво, како сте данас? Хвала вам пуно на помоћи у Србији."));
    }

    @Test
    public void testNonLatinScripts() {
        // Russian / Cyrillic
        assertEquals(Language.RUSSIAN, FastLanguageDetector.detect("Привет, как твои дела сегодня?"));
        // Ukrainian / Cyrillic
        assertEquals(Language.UKRAINIAN, FastLanguageDetector.detect("Доброго дня! Як ваші справи в Україні?"));
        // Chinese
        assertEquals(Language.CHINESE, FastLanguageDetector.detect("你好，今天的天气真不错。"));
        // Japanese
        assertEquals(Language.JAPANESE, FastLanguageDetector.detect("こんにちは、お元気ですか？"));
        // Korean
        assertEquals(Language.KOREAN, FastLanguageDetector.detect("안녕하세요, 오늘 날씨가 참 좋네요."));
        // Arabic
        assertEquals(Language.ARABIC, FastLanguageDetector.detect("مرحبا كيف حالك اليوم؟"));
        // Greek
        assertEquals(Language.GREEK, FastLanguageDetector.detect("Καλημέρα, πώς είστε σήμερα;"));
        // Hebrew
        assertEquals(Language.HEBREW, FastLanguageDetector.detect("שלום, מה שלומך היום?"));
        // Hindi
        assertEquals(Language.HINDI, FastLanguageDetector.detect("नमस्ते, आप आज कैसे हैं?"));
    }

    @Test
    public void testConfidenceAndDetectionResult() {
        DetectionResult de = FastLanguageDetector.detectWithConfidence("Dies ist ein deutscher Satz.");
        assertTrue(de.isConfident());
        assertEquals(Language.GERMAN, de.language());
        assertEquals("de-DE", de.getLocale());
        assertEquals("LexicalProfile:de", de.matchedRule());

        DetectionResult ru = FastLanguageDetector.detectWithConfidence("Привет мир");
        assertTrue(ru.isConfident());
        assertEquals(Language.RUSSIAN, ru.language());
        assertEquals(Language.Script.CYRILLIC, ru.script());
        assertEquals("LexicalProfile:ru", ru.matchedRule());

        DetectionResult zh = FastLanguageDetector.detectWithConfidence("你好世界");
        assertTrue(zh.isConfident());
        assertEquals(Language.CHINESE, zh.language());
        assertEquals(Language.Script.CHINESE, zh.script());
        assertEquals("Script:CHINESE", zh.matchedRule());

        DetectionResult empty = FastLanguageDetector.detectWithConfidence("");
        assertEquals(Language.ENGLISH, empty.language());
        assertEquals(FastLanguageDetector.RULE_EMPTY_INPUT, empty.matchedRule());

        assertEquals(Language.SPANISH, FastLanguageDetector.detect("", Language.SPANISH));
        assertEquals(Language.ITALIAN, FastLanguageDetector.detect(null, Language.ITALIAN));
        assertEquals(Language.PORTUGUESE, FastLanguageDetector.detect("xyz 123456 !!!", Language.PORTUGUESE));
    }

    @Test
    public void testThroughputPerformance() {
        String[] samples = {
                "Guten Tag, wie kann ich Ihnen heute behilflich sein?",
                "Hello, this is a fast performance benchmark for language identification.",
                "Bonjour monsieur, je voudrais un café s'il vous plaît.",
                "Hola, ¿dónde está la estación de tren más cercana?",
                "Привет, это тест скорости работы алгоритма."
        };

        // Warmup
        for (int i = 0; i < 50_000; i++) {
            FastLanguageDetector.detect(samples[i % samples.length]);
        }

        // Benchmark 200,000 iterations
        int iterations = 200_000;
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            FastLanguageDetector.detect(samples[i % samples.length]);
        }
        long durationNs = System.nanoTime() - start;
        double totalSec = durationNs / 1_000_000_000.0;
        double opsPerSec = iterations / totalSec;

        System.out.printf("[Performance] %d detections in %.3f s -> %.0f ops/sec (%.2f ns/op)%n",
                iterations, totalSec, opsPerSec, (double) durationNs / iterations);

        assertTrue(opsPerSec > 100_000, "Should easily achieve > 100,000 ops/sec, got " + opsPerSec);
    }
}
