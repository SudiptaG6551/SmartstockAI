# How to Start SmartStockAI

## Quick Start (Easiest Method)

### Option 1: Create Desktop Shortcut (Recommended)
1. Double-click `create-desktop-shortcut.bat`
2. A shortcut named "SmartStockAI" will appear on your desktop
3. From now on, just double-click the desktop shortcut to start the application
4. The browser will open automatically at http://localhost:8080/login.html

### Option 2: Use Startup Script
1. Double-click `start-smartstockai.bat` in the project folder
2. Wait 10-15 seconds for the application to start
3. Browser will open automatically
4. Keep the window open while using the application
5. Close the window or press any key to stop

## Manual Start (Advanced)

If you prefer to start manually:

1. Open Command Prompt or PowerShell
2. Navigate to the project folder:
   ```
   cd C:\Users\SUDIPTA\OneDrive\Desktop\SmartstockAI
   ```
3. Run the application:
   ```
   java -jar target\smartstockai-1.0.0.jar
   ```
4. Open browser and go to: http://localhost:8080/login.html

## Login Credentials

### Owner Account
- Username: `prabir`
- Password: `prabir123`

### Sales Manager Account
- Username: `sales`
- Password: `sales123`

### Stock Manager Account
- Username: `stock`
- Password: `stock123`

## Troubleshooting

### Port Already in Use
If you see "port 8080 already in use":
1. Close any running instance of the application
2. Or run this command to kill the process:
   ```
   netstat -ano | findstr :8080
   taskkill /F /PID [PID_NUMBER]
   ```

### Application Won't Start
1. Make sure Java is installed (Java 17 or higher)
2. Check that the `target\smartstockai-1.0.0.jar` file exists
3. If not, rebuild the project:
   ```
   D:\apache-maven-3.9.11\bin\mvn.cmd clean package -DskipTests
   ```

### Browser Doesn't Open
Manually open your browser and go to: http://localhost:8080/login.html

## Accessing from Mobile/Other Devices

To access from your phone or another computer on the same network:

1. Find your computer's IP address:
   ```
   ipconfig
   ```
   Look for "IPv4 Address" (e.g., 192.168.1.100)

2. On your mobile device, open browser and go to:
   ```
   http://YOUR_IP_ADDRESS:8080/login.html
   ```
   Example: http://192.168.1.100:8080/login.html

## Stopping the Application

- If using the startup script: Close the window or press any key
- If running manually: Press `Ctrl+C` in the command window
- Force stop: Use Task Manager to end the Java process
