@echo off
setlocal
chcp 65001 > nul

if not exist "target\FastLanguageDetector-0.1.0.jar" (
    echo [FastLanguageDetector] Building core library...
    call mvn clean package -DskipTests -q
)

if not exist "examples\Demo\target\classes\fastlanguagedetector\demo\Demo.class" (
    echo [FastLanguageDetector] Compiling examples/Demo...
    call mvn -f examples\Demo\pom.xml compile -q
)

set CP=examples\Demo\target\classes;target\FastLanguageDetector-0.1.0.jar;target\classes

java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "%CP%" fastlanguagedetector.demo.Demo %*
