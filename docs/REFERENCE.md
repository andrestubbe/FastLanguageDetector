# FastLanguageDetector API Reference Manual

`FastLanguageDetector` provides an ultra-fast, zero-dependency, deterministic offline language identification engine for Java 17+. It identifies text across **37 European languages** and **6 major global scripts** with sub-microsecond execution latency (1,200,000+ to 7,400,000+ ops/sec, 135–800 ns/op) and zero regex overhead.

---

## 1. Class: `fastlanguagedetector.FastLanguageDetector`

Central thread-safe, stateless facade for text language detection and script classification.

### Static Convenience Methods

| Method / Signature | Return Type | Heap Allocation | Description |
|:---|:---|:---|:---|
| `FastLanguageDetector.detect(CharSequence text)` | `Language` | **0 bytes (0 objects)** | Detects the most probable `Language` enum constant. Falls back to English on unresolvable input. |
| `FastLanguageDetector.detect(CharSequence text, Language fallback)` | `Language` | **0 bytes (0 objects)** | Detects the most probable `Language` enum constant with explicit custom fallback. |
| `FastLanguageDetector.detectLocale(CharSequence text)` | `String` | **0 bytes** | Detects language and returns pre-cached standard BCP-47 locale tag (e.g. `"de-DE"`, `"fr-FR"`). |
| `FastLanguageDetector.detectWithConfidence(CharSequence text)` | `DetectionResult` | 1 object (`DetectionResult`) | Performs full analysis and returns a rich `DetectionResult` containing language, confidence (0.0–1.0), script, and pre-cached provenance rule. |
| `FastLanguageDetector.standard()` | `FastLanguageDetector` | 0 bytes | Returns the shared immutable singleton instance. |

### Instance Methods & Custom Fallbacks

| Method / Signature | Return Type | Heap Allocation | Description |
|:---|:---|:---|:---|
| `new FastLanguageDetector()` | `FastLanguageDetector` | 1 instance | Creates detector instance with default fallback `Language.ENGLISH`. |
| `new FastLanguageDetector(Language fallback)` | `FastLanguageDetector` | 1 instance | Creates detector instance with a custom fallback language when input cannot be resolved. |
| `detectLanguage(CharSequence text)` | `Language` | **0 bytes** | Detects `Language` using configured fallback without object allocation. |
| `detectLanguage(CharSequence text, Language fallback)` | `Language` | **0 bytes** | Detects `Language` using ad-hoc fallback without object allocation. |
| `analyze(CharSequence text)` | `DetectionResult` | 1 object | Analyzes text using configured fallback. |
| `analyze(CharSequence text, Language fallback)` | `DetectionResult` | 1 object | Analyzes text with an explicit ad-hoc fallback language. |

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
- **Nanosecond Script Classification (L1)**: Character block detection operates in $O(N)$ single-pass scanning with direct primitive range checks (`cp >= 0x0400 && cp <= 0x052F`), zero `UnicodeBlock.of()` overhead, and reusable `ThreadLocal<ScriptAnalysis>` (0 allocations).
- **FastWordTable Open-Addressing Index (L2)**: Token resolution scans `CharSequence` slices in-place `(start, end)` directly against `FastWordTable` using power-of-two bitmask slotting and ASCII fast-path hashing. No `ArrayList`, no `StringBuilder`, and no `String` tokens are allocated.
- **Single-Pass Diacritics Switch (L3)**: High-frequency European character markers (`ß`, `ä`, `ö`, `ü`, `ñ`, `ç`, `ř`, `ł`, `đ`, `ą`, `ė`, `õ`, etc.) are evaluated directly via primitive `switch(char)` without regex matching.
- **ThreadLocal Score Buffer**: Thread-safe reusable `double[Language.COUNT]` score array eliminates per-request array allocation.
- **Zero-Allocation Detection Path**: `FastLanguageDetector.detect(text)` allocates **0 bytes / 0 objects** on the heap, executing at sub-microsecond speeds (1.2M to 7.4M ops/sec).
- **Pre-Cached Provenance Strings**: `DetectionResult.matchedRule()` returns interned static constants (`Script:<SCRIPT>`, `LexicalProfile:<CODE>`, `DefaultCyrillicFallback`), eliminating dynamic String concatenation churn.
- **Thread Safety**: Fully thread-safe, immutable, and re-entrant. All static and instance methods can be executed concurrently across platform and virtual threads.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster.*
