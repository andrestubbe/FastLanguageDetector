# The Philosophy of FastLanguageDetector

> [!IMPORTANT]
> **"Sub-microsecond language identification. Pure Java standard library. Zero neural bloat."**

FastLanguageDetector was created to eliminate the unacceptable overhead of modern NLP language detectors in high-throughput data processing pipelines, web scrapers, message queues, and local desktop applications.

---

## The Problem with Traditional Language Detection

Modern language identification libraries commonly fall into two undesirable categories:

1. **Heavy Neural & Machine Learning Models (fastText, HuggingFace, ONNX)**:
   - Require 50 MB to 300 MB model files loaded into RAM.
   - Introduce native C++ binaries, JNI boundaries, or Python sidecars.
   - High cold-start penalty (>500 ms) and throughput bottleneck (often 200–2,000 ops/sec).
2. **Naive Regex & N-gram Libraries**:
   - Compiling and executing large regular expressions causes severe CPU contention.
   - High allocation churn (`String.split()`, temporary Matcher objects) generates massive garbage collection pressure.

---

## Core Tenets

### 1. Zero External Dependencies
FastLanguageDetector requires **only standard Java 17+**. No external neural engines, no native `.dll`/`.so` dependencies, and zero third-party JARs required at runtime.

### 2. Multi-Tiered Evaluation Hierarchy
Language identification follows an optimized tiered execution path:
- **Tier 1 — Nanosecond Unicode Script Analysis**: Non-Latin scripts (CJK, Arabic, Hebrew, Devanagari, Greek, Cyrillic) are identified in a single pass across code points in nanoseconds.
- **Tier 2 — Inverted Stopword Index**: Distinctive function words (prepositions, articles, pronouns) are indexed into an inverted lexical map with $O(1)$ lookup per word.
- **Tier 3 — Single-Pass Diacritic Discrimination**: Unique national orthographic markers (e.g. German `ß`, Polish `ł`, Czech `ř`, Maltese `ħ`, Romanian `ș`/`ț`) boost specific language scores without regex overhead.

### 3. Ultra-Low Latency & High Throughput
Achieves **> 570,000 operations per second** on standard desktop CPU cores, with an average execution latency of **~1.7 microseconds**.

### 4. Stateless & Concurrency-First
The engine contains no mutable internal state. A single shared instance (`FastLanguageDetector.standard()`) can be safely invoked concurrently by thousands of virtual threads (`Thread.startVirtualThread`) or parallel worker pools with zero lock contention.

### 5. Part of the FastJava Ecosystem
Adheres strictly to the standardized FastJava architectural blueprint:
- Microsecond deterministic execution deadlines.
- Clear separation of concerns and uniform documentation (`REFERENCE.md`, `ROADMAP.md`, `PHILOSOPHY.md`, `CHANGELOG.md`, `COMPILE.md`).
- Interoperability with FastJava text and token engines (`FastTokenize`, `FastString`, `FastContentParse`).

---

**⚡ FastLanguageDetector — High-performance language identification for modern Java.**
