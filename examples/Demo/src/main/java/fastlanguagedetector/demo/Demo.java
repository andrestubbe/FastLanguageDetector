package fastlanguagedetector.demo;

import fastlanguagedetector.DetectionResult;
import fastlanguagedetector.FastLanguageDetector;

import java.util.List;

/**
 * FastLanguageDetector Demo & Interactive CLI.
 */
public class Demo {

    private static final List<String> SAMPLE_TEXTS = List.of(
            "Das ist ein wunderschöner Tag in Deutschland und wir programmieren Java.",
            "The quick brown fox jumps over the lazy dog in high performance computing.",
            "Bonjour tout le monde! Comment allez-vous aujourd'hui?",
            "¡Hola amigo! ¿Cómo estás hoy? Muchas gracias por tu ayuda.",
            "Buongiorno a tutti, questa è una prova del sistema di rilevamento lingua.",
            "Olá a todos! Como você está? Muito obrigado pela atenção e colaboração.",
            "Hallo allemaal, hoe gaat het met jou vandaag in Nederland?",
            "Cześć, jak się masz dzisiaj? To jest bardzo szybki system.",
            "Merhaba arkadaşlar, bugün hava çok güzel ve yazılım geliştiriyoruz.",
            "Привет, как твои дела? Это тест русского языка на высокой скорости.",
            "Доброго дня! Як ваші справи в Україні сегодня?",
            "こんにちは世界！これは日本語のテキストです。",
            "你好世界，这是一个超高性能的语言检测系统。",
            "안녕하세요 세계, 한국어 자연어 처리 벤치마크입니다.",
            "مرحبا بكم في نظام كشف اللغة فائق السرعة",
            "שלום עולם, זוהي מערכת זיהוי שפה מהירה מאוד",
            "Γειά σου κόσμε, αυτό είναι ένα σύστημα αναγνώρισης γλώσσας",
            "नमस्ते दुनिया, यह अल्ट्रा-फास्ट भाषा पहचान प्रणाली है"
    );

    public static void main(String[] args) {
        System.out.println("=================================================================================");
        System.out.println("                       FastLanguageDetector - Demo & CLI                         ");
        System.out.println("=================================================================================");

        if (args.length > 0 && ("--benchmark".equalsIgnoreCase(args[0]) || "-b".equalsIgnoreCase(args[0]))) {
            runBenchmark();
            return;
        }

        if (args.length > 0) {
            String input = String.join(" ", args);
            System.out.println("Input Text: \"" + input + "\"");
            System.out.println();
            long start = System.nanoTime();
            DetectionResult result = FastLanguageDetector.detectWithConfidence(input);
            long elapsedNs = System.nanoTime() - start;

            System.out.printf("Language   : %s (%s)%n", result.language().getDisplayName(), result.language().getCode());
            System.out.printf("Confidence : %.1f%%%n", result.confidence() * 100.0);
            System.out.printf("Script     : %s%n", result.script());
            System.out.printf("Rule       : %s%n", result.matchedRule());
            System.out.printf("Latency    : %.2f µs (%,d ns)%n", elapsedNs / 1000.0, elapsedNs);
            return;
        }

        System.out.println("Testing sample phrases across 18+ languages & scripts:\n");
        System.out.printf("%-40s | %-18s | %-8s | %-12s | %-10s%n", "TEXT PREVIEW", "DETECTED LANG", "CONF", "SCRIPT", "LATENCY");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        for (String sample : SAMPLE_TEXTS) {
            long start = System.nanoTime();
            DetectionResult result = FastLanguageDetector.detectWithConfidence(sample);
            long elapsedNs = System.nanoTime() - start;

            String preview = sample.length() > 38 ? sample.substring(0, 35) + "..." : sample;
            System.out.printf("%-40s | %-18s | %5.1f%% | %-12s | %6.2f µs%n",
                    preview,
                    result.language().getDisplayName() + " [" + result.language().getCode() + "]",
                    result.confidence() * 100.0,
                    result.script(),
                    elapsedNs / 1000.0
            );
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println("\nTip: Pass your own text as argument: run-demo.bat \"Your text here\"");
        System.out.println("     Run throughput benchmark:       run-demo.bat --benchmark");
    }

    private static void runBenchmark() {
        System.out.println("Running high-throughput performance benchmark (500,000 detections)...");
        System.out.println("Warming up JIT compiler...");

        // Warmup
        for (int i = 0; i < 50_000; i++) {
            for (String sample : SAMPLE_TEXTS) {
                FastLanguageDetector.detect(sample);
            }
        }

        System.out.println("Measuring throughput on multi-language corpus...");
        int iterations = 500_000;
        int sampleCount = SAMPLE_TEXTS.size();

        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            FastLanguageDetector.detect(SAMPLE_TEXTS.get(i % sampleCount));
        }
        long durationNs = System.nanoTime() - start;

        double durationSec = durationNs / 1_000_000_000.0;
        double opsPerSec = iterations / durationSec;
        double nsPerOp = (double) durationNs / iterations;

        System.out.println("\n---------------------------------------------------------------------------------");
        System.out.printf("Iterations   : %,d operations%n", iterations);
        System.out.printf("Total Time   : %.3f seconds%n", durationSec);
        System.out.printf("Throughput   : %,.0f ops/sec%n", opsPerSec);
        System.out.printf("Avg Latency  : %.2f ns / op (%.3f µs / op)%n", nsPerOp, nsPerOp / 1000.0);
        System.out.println("---------------------------------------------------------------------------------");
    }
}
