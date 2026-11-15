@echo off
echo ========================================================
echo   Launching StaySync Hostel Management System
echo ========================================================
echo.
echo Checking Python runtime...
"C:\Users\hp\AppData\Local\Programs\Python\Python313\python.exe" --version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Starting StaySync Web Application Server at http://localhost:8000 ...
    "C:\Users\hp\AppData\Local\Programs\Python\Python313\python.exe" start_demo_server.py
) else (
    echo Opening StaySync Live App directly in your default browser...
    start "" "%~dp0live-app\index.html"
)
pause
