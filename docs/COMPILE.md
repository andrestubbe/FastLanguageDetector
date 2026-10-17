# Building FastLanguageDetector from Source

## Prerequisites

- **JDK 17+** (JDK 21 recommended) — [Adoptium Temurin](https://adoptium.net/)
- **Maven 3.9+** — [Apache Maven](https://maven.apache.org/download.cgi)

FastLanguageDetector is a pure, zero-dependency Java module that does not require C++ compilers or native toolchains.

---

## Build Commands

| Command | Purpose |
|:---|:---|
| `mvn clean compile` | Compiles core FastLanguageDetector Java sources. |
| `mvn test` | Runs the full JUnit 5 unit test suite across all language corpora. |
| `mvn clean package` | Builds the FastLanguageDetector JAR in `target/`. |
| `mvn clean install` | Installs FastLanguageDetector into your local `.m2` repository. |
| `.\run-demo.bat` | Runs interactive demo classifying sample texts across 43 languages. |
| `.\run-benchmark.bat` | Executes the 500,000 detection throughput benchmark suite. |

---

## Running the Interactive Demo & Benchmark

### 1. Interactive Demo
```powershell
# Runs interactive showcase with sample texts
.\run-demo.bat

# Detect language of custom argument
.\run-demo.bat "Bonjour tout le monde! Comment allez-vous aujourd'hui?"
```

### 2. High-Throughput Benchmark
FastLanguageDetector includes a benchmark suite measuring operations per second and latency across 500,000 iterations:

```powershell
.\run-benchmark.bat
```
