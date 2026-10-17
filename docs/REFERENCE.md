# FastLanguageDetector API Reference Manual

`FastLanguageDetector` provides an ultra-fast, zero-dependency, deterministic offline language identification engine for Java 17+. It identifies text across **37 European languages** and **6 major global scripts** with sub-microsecond execution latency (~570,000+ ops/sec) and zero regex overhead.

---

## 1. Class: `fastlanguagedetector.FastLanguageDetector`

Central thread-safe, stateless facade for text language detection and script classification.

### Static Convenience Methods

| Method / Signature | Return Type | Description |
|:---|:---|:---|
| `FastLanguageDetector.detect(CharSequence text)` | `Language` | Detects the most probable `Language` enum for the given text. Falls back to English on unresolvable input. |
| `FastLanguageDetector.detectLocale(CharSequence text)` | `String` | Detects language and immediately returns the standard BCP-47 locale tag (e.g. `"de-DE"`, `"fr-FR"`). |
| `FastLanguageDetector.detectWithConfidence(CharSequence text)` | `DetectionResult` | Performs full analysis and returns a rich `DetectionResult` containing language, confidence (0.0–1.0), script, and provenance rule. |
| `FastLanguageDetector.standard()` | `FastLanguageDetector` | Returns the shared immutable singleton instance. |

### Instance Methods & Custom Fallbacks

| Method / Signature | Return Type | Description |
|:---|:---|:---|
| `new FastLanguageDetector()` | `FastLanguageDetector` | Creates detector instance with default fallback `Language.ENGLISH`. |
| `new FastLanguageDetector(Language fallback)` | `FastLanguageDetector` | Creates detector instance with a custom fallback language when input cannot be resolved. |
| `detectLanguage(CharSequence text)` | `Language` | Detects `Language` using configured fallback. |
| `analyze(CharSequence text)` | `DetectionResult` | Analyzes text using configured fallback. |
| `analyze(CharSequence text, Language fallback)` | `DetectionResult` | Analyzes text with an explicit ad-hoc fallback language. |

---

## 2. Record: `fastlanguagedetector.DetectionResult`

Immutable record holding comprehensive classification metrics.

### Components & Fields

| Component | Type | Description |
|:---|:---|:---|
| `language()` | `Language` | Detected language enumeration value. |
| `confidence()` | `double` | Normalized confidence score in the range `[0.0, 1.0]`. |
| `script()` | `Language.Script` | Primary Unicode script determined during L1 analysis. |
| `matchedRule()` | `String` | Detection provenance indicator (e.g. `Script:CHINESE`, `LexicalProfile:de`, `DefaultLatinFallback`). |

### Helper Methods

| Method | Return Type | Description |
|:---|:---|:---|
| `isConfident()` | `boolean` | Returns `true` if `confidence >= 0.50`. |
| `getCode()` | `String` | Returns ISO 639-1 two-letter code (e.g. `"de"`, `"en"`). |
| `getLocale()` | `String` | Returns BCP-47 locale tag (e.g. `"de-DE"`, `"fr-FR"`). |

---

## 3. Enum: `fastlanguagedetector.Language`

Enumeration of all supported languages with ISO 639-1 codes, BCP-47 locale identifiers, and associated Unicode scripts.

### Enum Values (43 Total)

#### Germanic (European)
- `GERMAN` (`de`, `de-DE`, `Script.LATIN`)
- `ENGLISH` (`en`, `en-US`, `Script.LATIN`)
- `DUTCH` (`nl`, `nl-NL`, `Script.LATIN`)
- `SWEDISH` (`sv`, `sv-SE`, `Script.LATIN`)
- `DANISH` (`da`, `da-DK`, `Script.LATIN`)
- `NORWEGIAN` (`no`, `no-NO`, `Script.LATIN`)
- `ICELANDIC` (`is`, `is-IS`, `Script.LATIN`)

#### Romance (European)
- `FRENCH` (`fr`, `fr-FR`, `Script.LATIN`)
- `SPANISH` (`es`, `es-ES`, `Script.LATIN`)
- `ITALIAN` (`it`, `it-IT`, `Script.LATIN`)
- `PORTUGUESE` (`pt`, `pt-PT`, `Script.LATIN`)
- `ROMANIAN` (`ro`, `ro-RO`, `Script.LATIN`)
- `CATALAN` (`ca`, `ca-ES`, `Script.LATIN`)
- `GALICIAN` (`gl`, `gl-ES`, `Script.LATIN`)

#### Slavic Latin Script (European)
- `POLISH` (`pl`, `pl-PL`, `Script.LATIN`)
- `CZECH` (`cs`, `cs-CZ`, `Script.LATIN`)
- `SLOVAK` (`sk`, `sk-SK`, `Script.LATIN`)
- `CROATIAN` (`hr`, `hr-HR`, `Script.LATIN`)
- `SLOVENIAN` (`sl`, `sl-SI`, `Script.LATIN`)

#### Slavic Cyrillic Script (European)
- `RUSSIAN` (`ru`, `ru-RU`, `Script.CYRILLIC`)
- `UKRAINIAN` (`uk`, `uk-UA`, `Script.CYRILLIC`)
- `BULGARIAN` (`bg`, `bg-BG`, `Script.CYRILLIC`)
- `BELARUSIAN` (`be`, `be-BY`, `Script.CYRILLIC`)
- `MACEDONIAN` (`mk`, `mk-MK`, `Script.CYRILLIC`)
- `SERBIAN` (`sr`, `sr-RS`, `Script.CYRILLIC`)

#### Finno-Ugric & Baltic (European)
- `FINNISH` (`fi`, `fi-FI`, `Script.LATIN`)
- `ESTONIAN` (`et`, `et-EE`, `Script.LATIN`)
- `HUNGARIAN` (`hu`, `hu-HU`, `Script.LATIN`)
- `LITHUANIAN` (`lt`, `lt-LT`, `Script.LATIN`)
- `LATVIAN` (`lv`, `lv-LV`, `Script.LATIN`)

#### Celtic & Mediterranean (European)
- `IRISH` (`ga`, `ga-IE`, `Script.LATIN`)
- `WELSH` (`cy`, `cy-GB`, `Script.LATIN`)
- `BASQUE` (`eu`, `eu-ES`, `Script.LATIN`)
- `ALBANIAN` (`sq`, `sq-AL`, `Script.LATIN`)
- `MALTESE` (`mt`, `mt-MT`, `Script.LATIN`)
- `TURKISH` (`tr`, `tr-TR`, `Script.LATIN`)
- `GREEK` (`el`, `el-GR`, `Script.GREEK`)

#### Global Non-European Scripts
- `CHINESE` (`zh`, `zh-CN`, `Script.CHINESE`)
- `JAPANESE` (`ja`, `ja-JP`, `Script.JAPANESE`)
- `KOREAN` (`ko`, `ko-KR`, `Script.KOREAN`)
- `ARABIC` (`ar`, `ar-SA`, `Script.ARABIC`)
- `HEBREW` (`he`, `he-IL`, `Script.HEBREW`)
- `HINDI` (`hi`, `hi-IN`, `Script.DEVANAGARI`)

#### Fallback
- `UNKNOWN` (`und`, `und`, `Script.LATIN`)

### Enum Methods

| Method | Return Type | Description |
|:---|:---|:---|
| `getCode()` | `String` | Returns ISO 639-1 two-letter code. |
| `getBcp47()` | `String` | Returns BCP-47 locale tag (e.g. `en-US`, `de-DE`). |
| `getDisplayName()` | `String` | Human-readable English name of the language. |
| `getScript()` | `Language.Script` | Associated primary script. |
| `Language.fromCode(String code)` | `Language` | Resolves ISO or BCP-47 string back to `Language`, or `UNKNOWN`. |

---

## 4. Enum: `fastlanguagedetector.Language.Script`

Supported Unicode script groups:
- `LATIN`
- `CYRILLIC`
- `CHINESE`
- `JAPANESE`
- `KOREAN`
- `ARABIC`
- `GREEK`
- `HEBREW`
- `DEVANAGARI`

---

## 5. Performance & Memory Contract

- **Zero Heavy Dependencies**: Pure Java 17 standard library. No ONNX runtime, no PyTorch models, no C++ dynamic libraries.
- **Nanosecond Script Classification (L1)**: Character block detection operates in $O(N)$ single-pass scanning without regex compilation or memory allocation.
- **Inverted Lexical Index (L2)**: Stopwords are resolved via a shared pre-indexed `Map<String, Language[]>` providing $O(1)$ lookup per token.
- **Single-Pass Diacritics Switch (L3)**: High-frequency European character markers (`ß`, `ä`, `ö`, `ü`, `ñ`, `ç`, `ř`, `ł`, `đ`, `ą`, `ė`, `õ`, etc.) are evaluated directly without regex matching.
- **Thread Safety**: Fully thread-safe and re-entrant. All static and instance methods can be executed concurrently across virtual and platform threads.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster.*
