@echo off
echo [1/5] Checking Python...
if not exist python-embedded\python.exe (
    echo Error: Run setup-python.bat first!
    pause
    exit /b 1
)

echo [2/5] Building JAR...
call gradlew.bat clean bootJar
if %errorlevel% neq 0 (
    echo Error: Build failed!
    pause
    exit /b 1
)

echo [3/5] Preparing files...
copy run.bat build\libs\
echo PYTHON_PATH=runtime/python/python.exe > build\libs\.env
echo SCRIPT_PATH=python/dxf_to_json.py >> build\libs\.env
echo OPENAI_API_KEY= >> build\libs\.env
echo Files copied successfully!

echo [4/5] Packaging Python...
if not exist build\libs\runtime mkdir build\libs\runtime
echo Copying Python runtime (this may take a moment)...
xcopy /E /I /Y python-embedded build\libs\runtime\python
if %errorlevel% neq 0 (
    echo Error: Failed to copy Python runtime!
    pause
    exit /b 1
)
echo Python runtime copied successfully!

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
  --java-options "-Xmx512m"

if %errorlevel% neq 0 (
    echo Error: jpackage failed! Check Java 21 JDK.
    pause
    exit /b 1
)

echo.
echo Done! AutoCAD-1.0.exe created (100-150MB)
echo Includes: Java + Python + ezdxf
echo.
echo IMPORTANT: After installation, run "run.bat" instead of "AutoCAD.exe"
echo Location: C:\Program Files\AutoCAD\app\run.bat
pause
