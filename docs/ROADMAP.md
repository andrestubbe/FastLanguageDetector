# FastLanguageDetector Technical Roadmap

This roadmap outlines planned features, algorithm improvements, and language expansions for FastLanguageDetector.

---

## Milestone 1: European Core & Multi-Script Primitives (v0.1.0) — ✅ Complete
- [x] Pure Java 17+ implementation with zero external runtime dependencies.
- [x] Tier 1 Unicode script classifier with direct primitive range checks and reusable `ThreadLocal<ScriptAnalysis>` (Cyrillic, Greek, Arabic, Hebrew, CJK, Devanagari).
- [x] Tier 2 `FastWordTable` open-addressing table with zero-allocation in-place `CharSequence` slice scanner covering all 37 European languages and global profiles.
- [x] Tier 3 Single-pass diacritics switch for European national alphabets with `ThreadLocal` score buffers.
- [x] True 0-Allocation hot path: `FastLanguageDetector.detect(text)` generates **0 bytes / 0 objects** heap churn.
- [x] Pre-cached rule provenance strings eliminating dynamic String concatenation churn.
- [x] Immutable `DetectionResult` record with confidence scoring, BCP-47 locale, and matched rule provenance.
- [x] OpenJDK JMH Microbenchmark suite achieving 1.2M to 7.4M ops/sec (135–800 ns latency).
- [x] Interactive multi-language showcase demo CLI (`run-demo.bat`).
- [x] Full JUnit 5 test suite covering all supported language families (100% pass rate).

---

## Milestone 2: Character N-Gram Fallbacks & Short Text Enhancement (v0.2.0) — 📋 Planned
- [ ] Compact 3-gram and 4-gram statistical profile fallback for ultra-short texts (1–3 words without stopwords).
- [ ] Pre-hashed byte array profiles for zero-string-allocation token matching.
- [ ] Configurable language whitelist/blacklist filtering to restrict detection to a subset of expected languages.
- [ ] Multi-language mixed text chunk segmentation (detecting code-switching between sentences).

---

## Milestone 3: Global Language Expansion (v0.3.0) — 📋 Planned
- [ ] Expansion to major Asian languages (Vietnamese, Thai, Indonesian, Tagalog, Malay).
- [ ] Cyrillic regional sub-script disambiguation improvements (Kazakh, Kyrgyz, Uzbek, Mongolian Cyrillic).
- [ ] Direct integration with `FastTokenize` and `FastContentParse` for zero-allocation stream detection.
- [ ] Panama FFM C-ABI export header for embedding FastLanguageDetector in native C/C++ applications.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster.*
