@echo off
setlocal

set ROOT=%~dp0

cd /d "%ROOT%"
set JAVA_EXE=%ROOT%runtime\bin\java.exe
if not exist "%JAVA_EXE%" (
    echo [ERROR] Embedded Java missing: %JAVA_EXE%
    pause
    exit /b 1
)

set PYTHON_EXE=%ROOT%python\python.exe
set PYTHON_SCRIPT=%ROOT%python\dxf_to_json.py

if not exist "%PYTHON_EXE%" (
    echo [ERROR] Embedded Python missing
    pause
    exit /b 1
)
if not exist "%PYTHON_SCRIPT%" (
    echo [ERROR] Python script missing
    pause
    exit /b 1
)

set JAR=%ROOT%app\java-autocad-0.0.1-SNAPSHOT.jar

"%JAVA_EXE%" ^
  -Dpython.exe="%PYTHON_EXE%" ^
  -Dpython.script="%PYTHON_SCRIPT%" ^
  -jar "%JAR%" %*

endlocal
