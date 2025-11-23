@echo off
echo [1/4] Checking Python...
if not exist python-embedded\python.exe (
    echo Error: Run setup-python.bat first!
    pause
    exit /b 1
)

echo [2/4] Building JAR...
call gradlew.bat clean bootJar
if %errorlevel% neq 0 (
    echo Error: Build failed!
    pause
    exit /b 1
)

echo [3/4] Preparing package...
if exist AutoCAD-Package rmdir /S /Q AutoCAD-Package
mkdir AutoCAD-Package
mkdir AutoCAD-Package\python

copy build\libs\java-autocad-0.0.1-SNAPSHOT.jar AutoCAD-Package\
copy run.bat AutoCAD-Package\
copy src\main\java\com\example\javaautocad\AutoCad\python\dxf_to_json.py AutoCAD-Package\python\

echo PYTHON_PATH=runtime/python/python.exe > AutoCAD-Package\.env
echo SCRIPT_PATH=python/dxf_to_json.py >> AutoCAD-Package\.env
echo OPENAI_API_KEY= >> AutoCAD-Package\.env

echo Copying Python runtime...
xcopy /E /I /Y python-embedded AutoCAD-Package\runtime\python

echo [4/4] Creating ZIP...
powershell -Command "Compress-Archive -Path 'AutoCAD-Package\*' -DestinationPath 'AutoCAD-Portable.zip' -Force"

echo.
echo Done! AutoCAD-Portable.zip created
echo Extract and run run.bat
pause
