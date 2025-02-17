@echo off
echo Testing Tencent Knowledge Analysis API...

set TEST_TEXT="hello"

curl -X POST ^
  http://localhost:9400/knowledge/analyze ^
  -H "Content-Type: application/json" ^
  -d "{\"content\":\"%TEST_TEXT%\"}"

echo.
pause
