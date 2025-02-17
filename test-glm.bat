@echo off
chcp 65001

curl --ssl-no-revoke -X POST ^
  http://localhost:9400/glm/chat ^
  -H "Content-Type: application/json" ^
  -d "{\"model\":\"glm-4-plus\",\"messages\":[{\"role\":\"user\",\"content\":\"hello\"}],\"temperature\":0.7,\"topP\":0.7,\"maxTokens\":2048}"

pause 