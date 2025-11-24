@echo off
echo Creating desktop shortcut for SmartStockAI...
echo.

set SCRIPT_DIR=%~dp0

REM Try multiple desktop locations
set DESKTOP=%USERPROFILE%\Desktop
if not exist "%DESKTOP%" set DESKTOP=%USERPROFILE%\OneDrive\Desktop
if not exist "%DESKTOP%" set DESKTOP=%OneDrive%\Desktop

echo Desktop location: %DESKTOP%
echo Script location: %SCRIPT_DIR%
echo.

powershell -ExecutionPolicy Bypass -Command "$WshShell = New-Object -ComObject WScript.Shell; $Desktop = [Environment]::GetFolderPath('Desktop'); $Shortcut = $WshShell.CreateShortcut(\"$Desktop\SmartStockAI.lnk\"); $Shortcut.TargetPath = '%SCRIPT_DIR%start-smartstockai.bat'; $Shortcut.WorkingDirectory = '%SCRIPT_DIR%'; $Shortcut.IconLocation = 'shell32.dll,21'; $Shortcut.Description = 'Start SmartStockAI Application'; $Shortcut.Save(); Write-Host 'Shortcut saved to:' $Desktop"

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo Desktop shortcut created successfully!
    echo ========================================
    echo.
    echo Look for "SmartStockAI" icon on your desktop.
) else (
    echo.
    echo ========================================
    echo Failed to create shortcut automatically
    echo ========================================
    echo.
    echo Please create a shortcut manually:
    echo 1. Right-click on start-smartstockai.bat
    echo 2. Select "Create shortcut"
    echo 3. Drag the shortcut to your desktop
)

echo.
pause
