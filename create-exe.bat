@echo off
REM Windows에서 실행할 스크립트입니다.
REM Java 21 JDK가 설치되어 있어야 합니다.
REM 이 스크립트는 Java Runtime이 포함된 독립 실행 파일(.exe)을 생성합니다.

echo ============================================
echo AutoCAD CLI - Standalone Executable Builder
echo ============================================
echo.

echo [1/3] Building executable JAR...
call gradlew.bat clean bootJar
if %errorlevel% neq 0 (
    echo Error: JAR build failed!
    pause
    exit /b 1
)
echo JAR build completed successfully!
echo.

echo [2/3] Creating Windows installer with bundled Java Runtime...
echo This will create a standalone .exe that includes Java Runtime.
echo No Java installation will be required on target PC!
echo.

jpackage ^
  --input build/libs ^
  --name AutoCAD ^
  --main-jar java-autocad-0.0.1-SNAPSHOT.jar ^
  --main-class com.example.javaautocad.AutoCad.Application ^
  --type exe ^
  --win-console ^
  --app-version 1.0 ^
  --vendor "AutoCAD Application" ^
  --description "AutoCAD CLI Application - Standalone" ^
  --java-options "-Xmx512m"

if %errorlevel% neq 0 (
    echo.
    echo Error: jpackage failed!
    echo Make sure Java 21 JDK is installed.
    pause
    exit /b 1
)

echo.
echo [3/3] Success!
echo ============================================
echo Standalone executable created: AutoCAD-1.0.exe
echo.
echo This .exe file includes Java Runtime!
echo You can distribute it to any Windows PC without Java.
echo ============================================
pause
