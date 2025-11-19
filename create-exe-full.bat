@echo off
echo [1/5] Checking Python...
if not exist python-embedded\python.exe (
    echo Error: Run setup-python.bat first!
    pause
    exit /b 1
)

echo [2/5] Preparing files...
if not exist build\libs\python mkdir build\libs\python
copy src\main\java\com\example\javaautocad\AutoCad\python\dxf_to_json.py build\libs\python\ >nul
if exist .env copy .env build\libs\ >nul

echo [3/5] Building JAR...
call gradlew.bat clean bootJar
if %errorlevel% neq 0 (
    echo Error: Build failed!
    pause
    exit /b 1
)

echo [4/5] Packaging Python...
if not exist build\libs\runtime mkdir build\libs\runtime
xcopy /E /I /Y /Q python-embedded build\libs\runtime\python >nul

echo [5/5] Creating .exe (5-10 min)...

jpackage ^
  --input build/libs ^
  --name AutoCAD ^
  --main-jar java-autocad-0.0.1-SNAPSHOT.jar ^
  --main-class com.example.javaautocad.AutoCad.Application ^
  --type exe ^
  --win-console ^
  --app-version 1.0 ^
  --vendor "AutoCAD Application" ^
  --description "AutoCAD CLI - Standalone (Java + Python included)" ^
  --java-options "-Xmx512m" ^
  --resource-dir build/libs

if %errorlevel% neq 0 (
    echo Error: jpackage failed! Check Java 21 JDK.
    pause
    exit /b 1
)

echo.
echo Done! AutoCAD-1.0.exe created (100-150MB)
echo Includes: Java + Python + ezdxf
pause
