@echo off
setlocal

set ROOT=%~dp0
echo [INFO] ROOT directory: %ROOT%

cd /d "%ROOT%"
set JAVA_EXE=%ROOT%runtime\bin\java.exe
echo [INFO] Checking Java: %JAVA_EXE%
if not exist "%JAVA_EXE%" (
    echo [ERROR] Embedded Java missing: %JAVA_EXE%
    pause
    exit /b 1
)
echo [OK] Java found

set PYTHON_EXE=%ROOT%python\python.exe
set PYTHON_SCRIPT=%ROOT%python\dxf_to_json.py

echo [INFO] Checking Python: %PYTHON_EXE%
if not exist "%PYTHON_EXE%" (
    echo [ERROR] Embedded Python missing
    pause
    exit /b 1
)
echo [OK] Python found

echo [INFO] Checking Script: %PYTHON_SCRIPT%
if not exist "%PYTHON_SCRIPT%" (
    echo [ERROR] Python script missing
    pause
    exit /b 1
)
echo [OK] Script found

set JAR=%ROOT%app\java-autocad.jar
echo [INFO] Checking JAR: %JAR%
if not exist "%JAR%" (
    echo [ERROR] JAR file not found: %JAR%
    pause
    exit /b 1
)
echo [OK] JAR found

echo.
echo [INFO] Starting application...
echo.

"%JAVA_EXE%" -jar "%JAR%" %*

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Program exited with error code: %errorlevel%
)

pause
endlocal
