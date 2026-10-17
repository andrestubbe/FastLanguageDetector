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

        DetectionResult ru = FastLanguageDetector.detectWithConfidence("Привет мир");
        assertTrue(ru.isConfident());
        assertEquals(Language.RUSSIAN, ru.language());
        assertEquals(Language.Script.CYRILLIC, ru.script());
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
