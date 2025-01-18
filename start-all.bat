@echo off
echo [INFO] Ready to start RuoYi microservices.
echo.

echo [INFO] 1. Starting Gateway Service
start "ruoyi-gateway" %~dp0start-gateway.bat

echo [INFO] 2. Starting Auth Service
start "ruoyi-auth" %~dp0start-auth.bat

echo [INFO] 3. Starting System Service
start "ruoyi-system" %~dp0start-system.bat

echo [INFO] 4. Starting UI Service
start "ruoyi-ui" %~dp0start-ui.bat

echo.
echo [INFO] Services started. Please wait a few minutes then visit http://localhost:80
echo [INFO] Account: admin  Password: admin123
