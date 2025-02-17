@echo off
chcp 65001

echo 测试Ollama AI服务...
echo.

curl -X POST ^
  http://localhost:9400/ollama/generate ^
  -H "Content-Type: application/json" ^
  -d "{\"prompt\": \"你好，请介绍一下你自己\"}"

echo.
pause 