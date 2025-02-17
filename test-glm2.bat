@echo off
setlocal
chcp 65001

rem 设置API密钥
set API_KEY=b72aa9e36abc4ba49f0719e4e01610d5.V6Y0BtH7cKe99Ty5

rem 构建curl命令

curl --ssl-no-revoke --location "https://open.bigmodel.cn/api/paas/v4/chat/completions" ^
--header "Authorization: Bearer %API_KEY%" ^
--header "Content-Type: application/json" ^
--data "{\"model\":\"glm-4\",\"messages\":[{\"role\":\"user\",\"content\":\"请介绍一下你自己，你有什么功能？\"}],\"temperature\":0.7,\"max_tokens\":2048}"

pause 