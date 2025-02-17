@echo off
echo Testing Customer Service Manual Signal API

rem Set the base URL - modify this according to your deployment
set BASE_URL=http://localhost:9400

rem Test manual service signal endpoint
echo.
echo Sending manual service signal request...
curl -X POST ^
  %BASE_URL%/customer-service/manual-signal ^
  -H "Content-Type: application/json" ^
  -d "{\"deviceId\": \"test-device-001\", \"userId\": \"user-001\", \"reason\": \"Test manual service request\"}"

echo.
pause
