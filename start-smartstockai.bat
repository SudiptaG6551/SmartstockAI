@echo off
title SmartStockAI - Starting...
color 0B

echo.
echo ========================================
echo    SmartStockAI - One-Click Startup
echo ========================================
echo.

cd /d "%~dp0"

REM Check if already running
echo [1/4] Checking if application is already running...
netstat -ano | findstr :8080 >nul
if %errorlevel% equ 0 (
    echo [OK] Application is already running!
    echo.
    echo Opening browser...
    start http://localhost:8080/login.html
    echo.
    echo Press any key to exit...
    pause >nul
    exit
)

REM Check if JAR exists
echo [2/4] Checking if application is built...
if not exist "target\smartstockai-1.0.0.jar" (
    echo [ERROR] Application not built!
    echo.
    echo Please run: mvn clean package -DskipTests
    echo.
    pause
    exit /b 1
)

REM Set database credentials
echo [3/4] Setting up database connection...
echo.
echo IMPORTANT: Update DB_PASSWORD in this file with your MySQL password!
echo Current password: your_password_here
echo.
set DB_USERNAME=root
set DB_PASSWORD=your_password_here

REM Start the application
echo [4/4] Starting SmartStockAI server...
echo.
echo ========================================
echo   Server is starting...
echo   Please wait 15 seconds
echo ========================================
echo.

start /B java -jar target\smartstockai-1.0.0.jar

REM Wait for server to start
echo Waiting for server to start...
timeout /t 15 /nobreak >nul

REM Open browser
echo.
echo ========================================
echo   SUCCESS! Opening browser...
echo ========================================
echo.
start http://localhost:8080/login.html

echo.
echo Application is running!
echo.
echo Login Credentials:
echo   Owner:         prabir / prabir123
echo   Sales Manager: sales / sales123
echo   Stock Manager: stock / stock123
echo.
echo ========================================
echo   Keep this window OPEN
echo   Close it to STOP the application
echo ========================================
echo.
echo Press any key to stop the application...
pause >nul

echo.
echo Stopping application...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do taskkill /F /PID %%a >nul 2>&1
echo Application stopped.
timeout /t 2 >nul
