@echo off
echo ===== BAT FILE STARTED =====
pause
echo This line will show if pause works
pause

setlocal

set ROOT=%~dp0
echo [INFO] ROOT directory: %ROOT%
pause

cd /d "%ROOT%"
set JAVA_EXE=%ROOT%runtime\bin\java.exe
echo [INFO] Checking Java: %JAVA_EXE%
pause

if not exist "%JAVA_EXE%" (
    echo [ERROR] Embedded Java missing: %JAVA_EXE%
    pause
    exit /b 1
)
echo [OK] Java found
pause

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

REM .env 파일에서 API 키 읽기
set ENV_FILE=%ROOT%app\.env
echo [INFO] Checking .env file: %ENV_FILE%
set API_KEY=
if exist "%ENV_FILE%" (
    echo [OK] .env file found
    for /f "usebackq tokens=1,2 delims==" %%a in ("%ENV_FILE%") do (
        if "%%a"=="API_KEY" set API_KEY=%%b
    )
) else (
    echo [ERROR] .env file not found: %ENV_FILE%
    pause
    exit /b 1
)

if "%API_KEY%"=="" (
    echo [ERROR] API_KEY not found in %ENV_FILE%
    pause
    exit /b 1
)
echo [OK] API_KEY loaded

set JAR=%ROOT%app\java-autocad-0.0.1-SNAPSHOT.jar
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

"%JAVA_EXE%" ^
  -Dpython.exe="%PYTHON_EXE%" ^
  -Dpython.script="%PYTHON_SCRIPT%" ^
  -Dapi.key="%API_KEY%" ^
  -jar "%JAR%" %*

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Program exited with error code: %errorlevel%
)

pause
endlocal
