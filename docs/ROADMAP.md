# FastLanguageDetector Technical Roadmap

This roadmap outlines planned features, algorithm improvements, and language expansions for FastLanguageDetector.

---

## Milestone 1: European Core & Multi-Script Primitives (v0.1.0) — ✅ Complete
- [x] Pure Java 17+ implementation with zero external runtime dependencies.
- [x] Tier 1 Unicode script classifier (Cyrillic, Greek, Arabic, Hebrew, CJK, Devanagari).
- [x] Tier 2 Inverted stopword index covering all official European languages (37 total).
- [x] Tier 3 Single-pass diacritics lookupswitch for European languages.
- [x] Immutable `DetectionResult` record with confidence scoring, BCP-47 locale, and matched rule provenance.
- [x] Throughput benchmark achieving > 570,000 ops/sec (~1.7 µs latency).
- [x] Interactive multi-language showcase demo CLI (`run-demo.bat`).
- [x] Full JUnit 5 test suite covering all supported language families.

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
