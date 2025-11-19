@echo off
REM AutoCAD CLI 런처 - Python 경로 자동 설정
REM 이 파일을 jpackage에 포함시켜서 자동으로 Python 경로를 설정합니다.

REM 현재 디렉토리 저장
set LAUNCHER_DIR=%~dp0

REM Python Embedded 경로 설정
set PYTHON_HOME=%LAUNCHER_DIR%runtime\python
set PYTHON_SCRIPT=%LAUNCHER_DIR%python\dxf_to_json.py

REM Python 경로 확인
if exist "%PYTHON_HOME%\python.exe" (
    echo Found embedded Python: %PYTHON_HOME%\python.exe
) else (
    echo Warning: Embedded Python not found at %PYTHON_HOME%
    echo Falling back to system Python...
)

REM Java 실행 시 Python 경로를 환경 변수로 전달
set PYTHON_EXE=%PYTHON_HOME%\python.exe
set PYTHON_SCRIPT_PATH=%PYTHON_SCRIPT%

REM JAR 실행
java -Dpython.exe="%PYTHON_EXE%" ^
     -Dpython.script="%PYTHON_SCRIPT_PATH%" ^
     -jar "%LAUNCHER_DIR%app\java-autocad-0.0.1-SNAPSHOT.jar" %*
