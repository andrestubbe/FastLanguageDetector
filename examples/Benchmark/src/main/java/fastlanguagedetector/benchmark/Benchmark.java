package fastlanguagedetector.benchmark;

import fastlanguagedetector.DetectionResult;
import fastlanguagedetector.FastLanguageDetector;
import fastlanguagedetector.Language;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * Standard OpenJDK JMH Microbenchmark Suite for FastLanguageDetector.
 *
 * Measures throughput across European Latin, Cyrillic, and CJK global scripts.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class Benchmark {

    private String germanText;
    private String frenchText;
    private String spanishText;
    private String russianText;
    private String chineseText;

    @Setup
    public void setup() {
        germanText = "Das ist ein wunderschöner Tag in Deutschland und wir programmieren Hochleistungs-Java.";
        frenchText = "Bonjour tout le monde! Comment allez-vous aujourd'hui? C'est une magnifique journée.";
        spanishText = "¡Hola amigo! ¿Cómo estás hoy? Muchas gracias por tu ayuda en el desarrollo del software.";
        russianText = "Привет, как твои дела? Это тест русского языка на экстремально высокой скорости.";
        chineseText = "你好世界，这是一个超高性能的语言检测系统。";
    }

    @org.openjdk.jmh.annotations.Benchmark
    public Language benchmarkDetectGerman() {
        return FastLanguageDetector.detect(germanText);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public Language benchmarkDetectFrench() {
        return FastLanguageDetector.detect(frenchText);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public Language benchmarkDetectSpanish() {
        return FastLanguageDetector.detect(spanishText);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public Language benchmarkDetectRussianCyrillic() {
        return FastLanguageDetector.detect(russianText);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public Language benchmarkDetectChineseScript() {
        return FastLanguageDetector.detect(chineseText);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public DetectionResult benchmarkAnalyzeWithConfidence() {
        return FastLanguageDetector.detectWithConfidence(germanText);
    }
}
