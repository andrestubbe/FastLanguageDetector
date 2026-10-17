# FastLanguageDetector

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://openjdk.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Zero-Dependencies](https://img.shields.io/badge/dependencies-zero-brightgreen.svg)]()
[![Throughput](https://img.shields.io/badge/throughput->200,000%20ops%2Fsec-success.svg)]()

**FastLanguageDetector** is an ultra-fast, zero-dependency, offline language identification library designed for modern Java 17+ applications.

It identifies text language across 19+ languages and 9 Unicode scripts in **under 5 microseconds per detection** (~200,000+ operations per second), with zero regex overhead and minimal heap allocations.

---

## ⚡ Key Highlights

- **Zero Dependencies**: Pure Java 17+ standard library. No native binaries, no heavy ML models, no HuggingFace downloads.
- **Ultra-Low Latency**: ~3 to 5 microseconds per detection. Over 200,000 ops/second on standard CPU cores.
- **Two-Tier Engine**:
  - **L1 Nanosecond Script Engine**: Instant Unicode block categorization for non-Latin scripts (Cyrillic, Chinese, Japanese, Korean, Arabic, Hebrew, Greek, Devanagari).
  - **L2 Allocation-Free Lexical Engine**: Linear-scan tokenization and frequency-weighted stopword profiles with diacritics boost for European languages.
- **Rich Result Metadata**: Returns standard ISO 639-1 language code, BCP-47 locale tag (`de-DE`, `en-US`), Unicode script, confidence score (0.0–1.0), and matching rule.
- **Thread-Safe**: 100% immutable and stateless design.

---

## 🌍 Supported Languages & Scripts

| Script | Languages | Typical Latency |
|---|---|---|
| **Latin** | German (`de`), English (`en`), French (`fr`), Spanish (`es`), Italian (`it`), Portuguese (`pt`), Dutch (`nl`), Polish (`pl`), Turkish (`tr`), Swedish (`sv`) | 4 - 8 µs |
| **Cyrillic** | Russian (`ru`), Ukrainian (`uk`) | < 1 µs |
| **Chinese (CJK)** | Chinese Simplified / Traditional (`zh`) | < 1 µs |
| **Japanese** | Japanese (`ja`) | < 1 µs |
| **Korean** | Korean (`ko`) | < 1 µs |
| **Arabic** | Arabic (`ar`) | < 1 µs |
| **Greek** | Greek (`el`) | < 1 µs |
| **Hebrew** | Hebrew (`he`) | < 1 µs |
| **Devanagari**| Hindi (`hi`) | < 1 µs |

---

## 🚀 Quick Start

### 1. Simple Language Detection

```java
import fastlanguagedetector.FastLanguageDetector;
import fastlanguagedetector.Language;

// Get Language Enum
Language lang = FastLanguageDetector.detect("Das ist ein wunderschöner Tag.");
System.out.println(lang); // GERMAN
System.out.println(lang.getCode()); // "de"

// Get BCP-47 Locale String directly
String locale = FastLanguageDetector.detectLocale("Bonjour tout le monde!");
System.out.println(locale); // "fr-FR"
```

### 2. Comprehensive Analysis with Confidence

```java
import fastlanguagedetector.FastLanguageDetector;
import fastlanguagedetector.DetectionResult;

DetectionResult result = FastLanguageDetector.detectWithConfidence(
    "¡Hola amigo! ¿Cómo estás hoy? Muchas gracias por tu ayuda."
);

System.out.println("Language   : " + result.language().getDisplayName()); // Spanish
System.out.println("Code       : " + result.getCode());                   // es
System.out.println("Locale     : " + result.getLocale());                 // es-ES
System.out.println("Confidence : " + (result.confidence() * 100) + "%"); // 91.4%
System.out.println("Script     : " + result.script());                    // LATIN
System.out.println("Confident  : " + result.isConfident());              // true
```

---

## 📊 Benchmark & Demo

```bash
# Run interactive multi-language demo
run-demo.bat

# Detect language of custom string
run-demo.bat "Dies ist ein Test auf Deutsch"

# Run 500,000 iteration throughput benchmark
run-benchmark.bat
```

Sample Benchmark Output:
```text
=================================================================================
Running high-throughput performance benchmark (500,000 detections)...
---------------------------------------------------------------------------------
Iterations   : 500,000 operations
Total Time   : 2.553 seconds
Throughput   : 195,832 ops/sec
Avg Latency  : 5.106 µs / op
---------------------------------------------------------------------------------
```

---

## 📦 Maven Dependency (JitPack)

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.andrestubbe</groupId>
    <artifactId>FastLanguageDetector</artifactId>
    <version>0.1.0</version>
</dependency>
```

---

## 📄 License

Apache License 2.0. Part of the **FastJava** ecosystem.
