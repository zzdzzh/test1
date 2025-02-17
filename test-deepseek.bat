@echo off
chcp 65001

echo 测试Deepseek API...

curl -X POST ^
  http://localhost:9400/deepseek/chat/stream ^
  -H "Content-Type: application/json" ^
  -d "[{\"role\": \"user\", \"content\": \"你好，请介绍一下你自己\"}]"

echo.
pause 