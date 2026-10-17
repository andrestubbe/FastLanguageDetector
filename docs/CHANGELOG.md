# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [0.1.0] - 2026-10-17

### Added
- Initial release of FastLanguageDetector ultra-fast offline language identification library.
- Comprehensive support for 37 European languages (all official EU, EEA, Baltic, Slavic, Romance, Germanic, and Celtic languages).
- Multi-script support for 6 global scripts: Chinese (CJK), Japanese, Korean, Arabic, Hebrew, and Hindi (Devanagari).
- Two-Tier detection architecture:
  - L1 Nanosecond Unicode Script Engine.
  - L2 Inverted Lexical Index & single-pass diacritics matcher.
- Rich `DetectionResult` record with `confidence`, ISO 639-1 code, BCP-47 locale, script, and provenance rule.
- Direct convenience methods: `FastLanguageDetector.detect()` and `FastLanguageDetector.detectLocale()`.
- High-throughput benchmark reaching **> 570,000 ops/sec** (~1.7 µs average latency).
- Interactive demo CLI with customizable text detection (`run-demo.bat`).
- Standardized documentation suite (`REFERENCE.md`, `PHILOSOPHY.md`, `ROADMAP.md`, `COMPILE.md`, `CHANGELOG.md`).
- Comprehensive JUnit 5 test suite validating all supported language corpora.
