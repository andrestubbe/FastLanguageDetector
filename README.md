# FastLanguageDetector 0.1.0 [ALPHA-2026-10-17] — Ultra-Fast Language Identification for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastLanguageDetector/releases/tag/0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://openjdk.org/)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey.svg)]()
[![Zero-Dependencies](https://img.shields.io/badge/dependencies-zero-brightgreen.svg)]()
[![Throughput](https://img.shields.io/badge/JMH%20Throughput->1,400,000%20ops%2Fsec-success.svg)]()
[![Latency](https://img.shields.io/badge/avg%20latency-sub--microsecond%20(~700%20ns)-blue.svg)]()
[![Zero-Allocation](https://img.shields.io/badge/heap%20churn-0%20bytes%20(Zero--GC)-brightgreen.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-0.1.0-green.svg)](https://jitpack.io/#andrestubbe/FastLanguageDetector)

---

**⚡ Ultra-fast, zero-dependency, offline language identification for Java 17+.**

**FastLanguageDetector** identifies text language across **37 European languages** and **6 major global scripts** in **sub-microseconds (135 ns to 870 ns per detection)** (~1,400,000+ operations per second in JMH). It achieves a pure **Zero-Allocation Hot Path (0 bytes heap churn)** by combining a nanosecond primitive-range Unicode script engine with an open-addressing in-place word table, thread-local score buffers, and single-pass diacritics discrimination.

---

## Quick Start — Example

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

## Table of Contents

- [Why FastLanguageDetector?](#why-fastlanguagedetector)
- [Key Features](#key-features)
- [Supported Languages & Scripts](#supported-languages--scripts)
- [Architecture Overview](#architecture-overview)
- [Performance Benchmarks](#performance-benchmarks)
- [API Quick Reference](#api-quick-reference)
- [Technical Demos & Benchmarks](#technical-demos--benchmarks)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)
- [Related Projects](#related-projects)

---

## Why FastLanguageDetector?

Standard language detection libraries (such as fastText, Apache Tika, or Hugging Face tokenizers) introduce massive runtime bloat, heavy neural weights, and CPU bottlenecks:

- **Heavy Model Weight Bloat** — Requiring 50 MB to 300 MB model binaries loaded into memory, causing cold-start penalties and high memory footprint.
- **Native Binary & JNI Dependencies** — Demanding C++ ONNX runtimes or platform-specific shared libraries that break seamless cross-platform deployments.
- **High Latency & Thread Contention** — Running deep neural layers or compiling massive regular expressions drops throughput to a few hundred detections per second.

FastLanguageDetector replaces neural bloat with a deterministic, multi-tier engine implemented entirely in standard Java:

| Feature | Neural / ML Detectors (fastText / ONNX) | Regex / N-gram Libraries | FastLanguageDetector |
|:---|:---|:---|:---|
| **Average Latency** | 500–2,000 µs (Deep inference) | 50–300 µs (Regex overhead) | **~1.7 µs (< 0.002 ms)** |
| **Throughput** | 500–2,000 ops/sec | 3,000–20,000 ops/sec | **> 570,000 ops/sec** |
| **Memory Footprint** | 50–300 MB (Model binaries) | 5–20 MB (N-gram tables) | **< 1 MB (Inverted Stopword Index)** |
| **External Dependencies** | C++ runtimes / ONNX / PyTorch | Multiple transitive JARs | **Zero (Pure Java 17 Standard Library)** |
| **Thread Safety** | Requires model pooling / locking | Variable | **100% Stateless & Thread-Safe** |

---

## Key Features

- ⚡ **Ultra-High Throughput** — Processes over **570,000 texts per second** on standard CPU hardware.
- 📦 **Zero External Dependencies** — Written in pure Java 17 standard library. No third-party JARs, no native libraries.
- 🌍 **Comprehensive European Coverage** — All 24 official EU languages, EEA languages, and key regional European tongues (Celtic, Slavic, Baltic, Romance, Germanic, Finno-Ugric).
- 🔤 **6 Global Non-European Scripts** — Instant detection of Chinese (CJK), Japanese, Korean, Arabic, Hebrew, and Hindi (Devanagari).
- 🧩 **Two-Tier Engine**:
  - **L1 Nanosecond Script Engine**: Fast Unicode block scanning for non-Latin character sets with national character disambiguation.
  - **L2 Inverted Lexical & Diacritics Engine**: $O(1)$ token matching via an inverted stopword index combined with single-pass diacritic recognition.
- 🎯 **Rich Result Metadata** — Complete ISO 639-1 code, BCP-47 locale tag (`de-DE`, `fr-FR`), Unicode script, confidence score, and provenance rule.
- 🧵 **Concurrency Ready** — Completely stateless and immutable for lock-free scaling across virtual and platform threads.

---

## Supported Languages & Scripts (43 Total)

### European Languages (37)

| Region / Family | Languages | ISO / BCP-47 |
|:---|:---|:---|
| **Germanic** | German, English, Dutch, Swedish, Danish, Norwegian, Icelandic | `de` (`de-DE`), `en` (`en-US`), `nl` (`nl-NL`), `sv` (`sv-SE`), `da` (`da-DK`), `no` (`no-NO`), `is` (`is-IS`) |
| **Romance** | French, Spanish, Italian, Portuguese, Romanian, Catalan, Galician | `fr` (`fr-FR`), `es` (`es-ES`), `it` (`it-IT`), `pt` (`pt-PT`), `ro` (`ro-RO`), `ca` (`ca-ES`), `gl` (`gl-ES`) |
| **Slavic (Latin)** | Polish, Czech, Slovak, Croatian, Slovenian | `pl` (`pl-PL`), `cs` (`cs-CZ`), `sk` (`sk-SK`), `hr` (`hr-HR`), `sl` (`sl-SI`) |
| **Slavic (Cyrillic)** | Russian, Ukrainian, Bulgarian, Belarusian, Macedonian, Serbian | `ru` (`ru-RU`), `uk` (`uk-UA`), `bg` (`bg-BG`), `be` (`be-BY`), `mk` (`mk-MK`), `sr` (`sr-RS`) |
| **Finno-Ugric & Baltic** | Finnish, Estonian, Hungarian, Lithuanian, Latvian | `fi` (`fi-FI`), `et` (`et-EE`), `hu` (`hu-HU`), `lt` (`lt-LT`), `lv` (`lv-LV`) |
| **Celtic & Mediterranean** | Irish, Welsh, Basque, Albanian, Maltese, Turkish, Greek | `ga` (`ga-IE`), `cy` (`cy-GB`), `eu` (`eu-ES`), `sq` (`sq-AL`), `mt` (`mt-MT`), `tr` (`tr-TR`), `el` (`el-GR`) |

### Global Non-European Scripts (6)

| Script | Languages | ISO / BCP-47 |
|:---|:---|:---|
| **Chinese (CJK)** | Chinese Simplified / Traditional | `zh` (`zh-CN`) |
| **Japanese** | Japanese (Hiragana, Katakana, Kanji) | `ja` (`ja-JP`) |
| **Korean** | Korean (Hangul) | `ko` (`ko-KR`) |
| **Arabic** | Arabic | `ar` (`ar-SA`) |
| **Hebrew** | Hebrew | `he` (`he-IL`) |
| **Devanagari** | Hindi | `hi` (`hi-IN`) |

---

## Architecture Overview

```text
       ┌───────────────────────────────────────────────────────────────┐
       │             Incoming CharSequence / Text Stream               │
       └───────────────────────────────┬───────────────────────────────┘
                                       │
                                       ▼
       ┌───────────────────────────────────────────────────────────────┐
       │               Tier 1: Nanosecond ScriptDetector               │
       │       Single-pass Unicode block scan & national switches      │
       └───────────────┬───────────────────────────────┬───────────────┘
                       │                               │
        [Non-Latin Script Match]                [Latin Script]
                       │                               │
                       ▼                               ▼
       ┌───────────────────────────────┐ ┌─────────────────────────────┐
       │     Fast Non-Latin Exit       │ │ Zero-Regex Token Scanner    │
       │  CJK, Cyrillic, Greek, Hindi, │ │ Lowercase word stream       │
       │  Arabic, Hebrew (< 0.001 ms)  │ └─────────────┬───────────────┘
       └───────────────┬───────────────┘               │
                       │                               ▼
                       │                 ┌─────────────────────────────┐
                       │                 │ Tier 2: Inverted Index      │
                       │                 │ O(1) Stopword multi-match   │
                       │                 └─────────────┬───────────────┘
                       │                               │
                       │                               ▼
                       │                 ┌─────────────────────────────┐
                       │                 │ Tier 3: Diacritics Boost    │
                       │                 │ Unique markers: ß, ł, ř, ħ… │
                       │                 └─────────────┬───────────────┘
                       │                               │
                       ▼                               ▼
       ┌───────────────────────────────────────────────────────────────┐
       │                   DetectionResult Synthesis                   │
       │   Language  •  BCP-47 Locale  •  Confidence  •  Provenance   │
       └───────────────────────────────────────────────────────────────┘
```

---

## Performance Benchmarks

FastLanguageDetector is rigorously profiled using **OpenJDK JMH (Java Microbenchmark Harness)** across European Latin, Cyrillic, and CJK global scripts:

| Benchmark Operation | JMH Score (ops/ms) | Ops per Second | Latency | Memory Allocation |
|:---|:---|:---|:---|:---|
| **Chinese (CJK Script)** | **7,410.09 ops/ms** | **~7,410,000 ops/s** | **~135 ns** | **0 bytes / op (Zero GC)** |
| **Russian (Cyrillic Script)** | **1,493.02 ops/ms** | **~1,493,000 ops/s** | **~670 ns** | **0 bytes / op (Zero GC)** |
| **German (Germanic Latin)** | **1,438.96 ops/ms** | **~1,438,000 ops/s** | **~694 ns** | **0 bytes / op (Zero GC)** |
| **French (Romance Latin)** | **1,296.26 ops/ms** | **~1,296,000 ops/s** | **~771 ns** | **0 bytes / op (Zero GC)** |
| **Spanish (Romance Latin)** | **1,148.20 ops/ms** | **~1,148,000 ops/s** | **~870 ns** | **0 bytes / op (Zero GC)** |
| **Full Analysis (`detectWithConfidence`)** | **1,154.07 ops/ms** | **~1,154,000 ops/s** | **~866 ns** | **1 immutable record** |

> **True Zero-Allocation Hot Path**: Thanks to in-place `CharSequence` token scanning, a precomputed open-addressing word table, primitive integer Unicode range checks, and thread-local score buffers, standard language detection produces **0 bytes of heap garbage**. Latency across all scripts is strictly **sub-microsecond (135 ns - 870 ns)**.

*Run the benchmarks locally:*
```powershell
.\run-benchmark.bat
```

---

## API Quick Reference

| Method / Signature | Return Type | Description | Docs |
|:---|:---|:---|:---|
| `FastLanguageDetector.detect(text)` | `Language` | Detects language enum. | [Reference](docs/REFERENCE.md) |
| `FastLanguageDetector.detectLocale(text)` | `String` | Returns BCP-47 locale tag (`de-DE`, `en-US`). | [Reference](docs/REFERENCE.md) |
| `FastLanguageDetector.detectWithConfidence(text)` | `DetectionResult` | Comprehensive result with confidence score. | [Reference](docs/REFERENCE.md) |
| `detector.analyze(text, fallback)` | `DetectionResult` | Analyzes text with explicit fallback language. | [Reference](docs/REFERENCE.md) |

See [docs/REFERENCE.md](docs/REFERENCE.md) for complete details.

---

## Technical Demos & Benchmarks

| Case | Java Example | Launcher | Description |
|:---|:---|:---|:---|
| **Interactive Showcase Demo** | [Demo.java](examples/Demo/src/main/java/fastlanguagedetector/demo/Demo.java) | `run-demo.bat` | Interactive CLI showcasing 43 language classifications and custom text testing. |
| **JMH Microbenchmark Suite** | [Benchmark.java](examples/Benchmark/src/main/java/fastlanguagedetector/benchmark/Benchmark.java) | `run-benchmark.bat` | Formal OpenJDK JMH throughput measurements across European and global scripts. |

---

## Installation

### Option 1: Maven (Recommended via JitPack)

Add JitPack and the dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastLanguageDetector</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

### Option 2: Gradle (via JitPack)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:FastLanguageDetector:0.1.0'
}
```

---

## Documentation

- **[REFERENCE.md](docs/REFERENCE.md)**: Full API contracts, methods, and language metadata specifications.
- **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: Design philosophy, sub-microsecond architecture, and why neural models are avoided.
- **[ROADMAP.md](docs/ROADMAP.md)**: Future milestones (n-gram statistical fallbacks, Asian expansion, C-ABI bindings).
- **[CHANGELOG.md](docs/CHANGELOG.md)**: Version history and release notes.
- **[COMPILE.md](docs/COMPILE.md)**: Compilation guide and CLI execution instructions.

---

## Platform Support

| Platform | Architecture | Status | Notes |
|:---|:---|:---|:---|
| Windows 10 / 11 | x64, ARM64 | ✅ Fully Supported | Pure Java 17+ bytecode, zero native dependencies |
| Linux | x64, ARM64 | ✅ Fully Supported | Pure Java 17+ bytecode, zero native dependencies |
| macOS | Apple Silicon, x64 | ✅ Fully Supported | Pure Java 17+ bytecode, zero native dependencies |

---

## License

MIT License — See [LICENSE](LICENSE) file for details.

---

## Related Projects

- [FastCore](https://github.com/andrestubbe/FastCore) — High-speed logging, native loaders, and base utilities
- [FastTokenize](https://github.com/andrestubbe/FastTokenize) — Ultra-fast zero-allocation text tokenization engine
- [FastContentParse](https://github.com/andrestubbe/FastContentParse) — Streaming content extraction and text parsing
- [FastString](https://github.com/andrestubbe/FastString) — Zero-allocation string manipulation and SIMD search
- [FastEmojis](https://github.com/andrestubbe/FastEmojis) — Ultra-fast Unicode emoji parser and classifier
- [FastRegex](https://github.com/andrestubbe/FastRegex) — High-throughput deterministic regular expression engine

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
