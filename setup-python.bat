@echo off
echo [1/4] Downloading Python 3.11.9...
set PYTHON_VERSION=3.11.9
set PYTHON_URL=https://www.python.org/ftp/python/%PYTHON_VERSION%/python-%PYTHON_VERSION%-embed-amd64.zip
set PYTHON_DIR=python-embedded

if not exist %PYTHON_DIR% mkdir %PYTHON_DIR%
powershell -Command "& {Invoke-WebRequest -Uri '%PYTHON_URL%' -OutFile 'python-embedded.zip'}"
if %errorlevel% neq 0 (
    echo Error: Failed to download Python!
    pause
    exit /b 1
)

echo [2/4] Extracting...
powershell -Command "& {Expand-Archive -Path 'python-embedded.zip' -DestinationPath '%PYTHON_DIR%' -Force}"
del python-embedded.zip

echo [3/4] Downloading pip...
powershell -Command "& {Invoke-WebRequest -Uri 'https://bootstrap.pypa.io/get-pip.py' -OutFile '%PYTHON_DIR%\get-pip.py'}"
if %errorlevel% neq 0 (
    echo Error: Failed to download pip!
    pause
    exit /b 1
)

echo [4/4] Installing ezdxf...
echo import site >> %PYTHON_DIR%\python311._pth
%PYTHON_DIR%\python.exe %PYTHON_DIR%\get-pip.py
%PYTHON_DIR%\python.exe -m pip install ezdxf
if %errorlevel% neq 0 (
    echo Error: Failed to install ezdxf!
    pause
    exit /b 1
)

echo.
echo Setup complete! Run create-exe-full.bat next.
pause
