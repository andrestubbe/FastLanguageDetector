# FastLanguageDetector

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://openjdk.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Zero-Dependencies](https://img.shields.io/badge/dependencies-zero-brightgreen.svg)]()
[![Throughput](https://img.shields.io/badge/throughput->570,000%20ops%2Fsec-success.svg)]()
[![Latency](https://img.shields.io/badge/avg%20latency-1.7%20%C2%B5s-blue.svg)]()

**FastLanguageDetector** is an ultra-fast, zero-dependency, offline language identification library designed for modern Java 17+ applications.

It identifies text language across **37 European languages** and **6 major global scripts** in **under 2 microseconds per detection** (~570,000+ operations per second), with zero regex overhead, an inverted lexical index, and single-pass diacritics analysis.

---

## ⚡ Highlights

- **Zero Dependencies**: Pure Java 17+ standard library. No native binaries, no heavy PyTorch/ONNX models, zero network calls.
- **Ultra-High Throughput**: **> 570,000 detections/sec** on standard CPU cores (average latency ~1.7 µs).
- **Comprehensive European Coverage**: All official EU languages, EEA languages, and key regional European languages (Celtic, Baltic, Slavic Latin/Cyrillic, Romance, Germanic, Finno-Ugric).
- **Two-Tier Engine**:
  - **L1 Nanosecond Script Engine**: Instant Unicode block categorization for non-Latin scripts (Cyrillic, Chinese, Japanese, Korean, Arabic, Hebrew, Greek, Devanagari) with unique national character switches.
  - **L2 Inverted Lexical & Single-Pass Diacritics Engine**: $O(1)$ word matching via an inverted stopword index combined with a single-pass lookupswitch for European diacritics.
- **Rich Result Metadata**: Returns ISO 639-1 code, BCP-47 locale tag (`de-DE`, `fr-FR`), Unicode script, confidence score (0.0–1.0), and rule provenance.
- **Thread-Safe & Immutable**: 100% stateless design for concurrent execution.

---

## 🌍 Supported Languages (43 Total)

### European Languages (37)

| Region / Family | Languages | ISO / BCP-47 |
|---|---|---|
| **Germanic** | German, English, Dutch, Swedish, Danish, Norwegian, Icelandic | `de` (`de-DE`), `en` (`en-US`), `nl` (`nl-NL`), `sv` (`sv-SE`), `da` (`da-DK`), `no` (`no-NO`), `is` (`is-IS`) |
| **Romance** | French, Spanish, Italian, Portuguese, Romanian, Catalan, Galician | `fr` (`fr-FR`), `es` (`es-ES`), `it` (`it-IT`), `pt` (`pt-PT`), `ro` (`ro-RO`), `ca` (`ca-ES`), `gl` (`gl-ES`) |
| **Slavic (Latin)** | Polish, Czech, Slovak, Croatian, Slovenian | `pl` (`pl-PL`), `cs` (`cs-CZ`), `sk` (`sk-SK`), `hr` (`hr-HR`), `sl` (`sl-SI`) |
| **Slavic (Cyrillic)** | Russian, Ukrainian, Bulgarian, Belarusian, Macedonian, Serbian | `ru` (`ru-RU`), `uk` (`uk-UA`), `bg` (`bg-BG`), `be` (`be-BY`), `mk` (`mk-MK`), `sr` (`sr-RS`) |
| **Finno-Ugric & Baltic** | Finnish, Estonian, Hungarian, Lithuanian, Latvian | `fi` (`fi-FI`), `et` (`et-EE`), `hu` (`hu-HU`), `lt` (`lt-LT`), `lv` (`lv-LV`) |
| **Celtic & Mediterranean** | Irish, Welsh, Basque, Albanian, Maltese, Turkish, Greek | `ga` (`ga-IE`), `cy` (`cy-GB`), `eu` (`eu-ES`), `sq` (`sq-AL`), `mt` (`mt-MT`), `tr` (`tr-TR`), `el` (`el-GR`) |

### Global Non-European (6)

| Script | Languages | ISO / BCP-47 |
|---|---|---|
| **Chinese (CJK)** | Chinese Simplified / Traditional | `zh` (`zh-CN`) |
| **Japanese** | Japanese (Hiragana, Katakana, Kanji) | `ja` (`ja-JP`) |
| **Korean** | Korean (Hangul) | `ko` (`ko-KR`) |
| **Arabic** | Arabic | `ar` (`ar-SA`) |
| **Hebrew** | Hebrew | `he` (`he-IL`) |
| **Devanagari** | Hindi | `hi` (`hi-IN`) |

---

## 🚀 Quick Start

### 1. Simple Language Detection

```java
import fastlanguagedetector.FastLanguageDetector;
import fastlanguagedetector.Language;

// Detect Language enum
Language lang = FastLanguageDetector.detect("Das ist ein wunderschöner Tag.");
System.out.println(lang);           // GERMAN
System.out.println(lang.getCode()); // "de"

// Detect BCP-47 Locale tag directly
String locale = FastLanguageDetector.detectLocale("Bonjour tout le monde!");
System.out.println(locale);         // "fr-FR"
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
System.out.println("Rule       : " + result.matchedRule());               // LexicalProfile:es
System.out.println("Confident  : " + result.isConfident());              // true
```

---

## 📊 Performance Benchmark

Benchmark measured on 500,000 detections across the multi-language corpus:

```text
=========================================================================================
                         FastLanguageDetector - Demo & CLI                               
                     37 European Languages + 6 Global Scripts                            
=========================================================================================
Running high-throughput performance benchmark (500,000 detections)...
Measuring throughput on multi-language corpus (43 languages)...

-----------------------------------------------------------------------------------------
Corpus Size  : 43 languages / scripts
Iterations   : 500,000 operations
Total Time   : 0.870 seconds
Throughput   : 574,882 ops/sec
Avg Latency  : 1,739 ns / op (1.739 µs / op)
-----------------------------------------------------------------------------------------
```

### Running the Demo & Benchmark

```bash
# Run multi-language interactive demo
run-demo.bat

# Detect custom string
run-demo.bat "Cześć, jak się masz dzisiaj?"

# Run 500,000 iteration throughput benchmark
run-benchmark.bat
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
