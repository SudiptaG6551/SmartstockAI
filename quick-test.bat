@echo off
echo ========================================
echo   SmartStockAI - Quick Test Mode
echo   (Using H2 In-Memory Database)
echo ========================================
echo.
echo This will start the application with H2 database.
echo No MySQL setup required!
echo.
echo Data will be temporary and lost when you close the app.
echo.
pause

echo.
echo Building application...
call mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo.
    echo Build failed! Make sure Maven is installed.
    pause
    exit /b 1
)

echo.
echo Starting application in TEST mode...
echo.
echo Opening browser in 10 seconds...
echo.

start /B java -jar target\smartstockai-1.0.0.jar --spring.profiles.active=test

timeout /t 10 /nobreak >nul

echo Opening browser...
start http://localhost:8080/login.html

echo.
echo ========================================
echo   Application is running!
echo ========================================
echo.
echo Access at: http://localhost:8080
echo.
echo Register a new user or use test credentials:
echo   Username: admin / Password: admin123
echo.
echo Press any key to stop the application...
pause >nul

echo.
echo Stopping application...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do taskkill /F /PID %%a >nul 2>&1
echo Application stopped.
timeout /t 2
