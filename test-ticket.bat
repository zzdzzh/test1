@echo off
chcp 65001
echo 测试客户服务工单接口
echo.

set BASE_URL=http://localhost:9400/customer/service/ticket
set AUTH_HEADER=Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoxLCJ1c2VyX2tleSI6ImQ3MmRhZmU1LTQwYTYtNGUyZi05ODQ3LTk1NTdlZDAzMWZhOCIsInVzZXJuYW1lIjoiYWRtaW4ifQ.vllWZqiKmr11G5bL2TxIawDeGtKMiziXwuUbl6sKL7HFD2eJc96wal7-pvzGDbozhDeJ44-HN5OSGcWByKaIVg

@REM @REM :: 1. 测试获取工单列表
@REM echo 1. 测试获取工单列表
@REM curl -X GET "%BASE_URL%/list" -H "Accept: application/json" -H "%AUTH_HEADER%"
@REM echo.
@REM echo.

@REM :: 2. 测试新增工单
echo 2. 测试新增工单
curl -X POST "%BASE_URL%" ^
-H "Content-Type: application/json" ^
-H "%AUTH_HEADER%" ^
-d "{\"name\":\"测试客户\",\"phone\":\"13888888888\",\"content\":\"测试工单内容\",\"relatedOrders\":\"TEST202401001\",\"status\":0}"
echo.
echo.

@REM @REM :: 3. 测试获取单个工单
@REM echo 3. 测试获取工单详情（ID=1）
@REM curl -X GET "%BASE_URL%/1" -H "Accept: application/json" -H "%AUTH_HEADER%"
@REM echo.
@REM echo.

@REM @REM :: 4. 测试修改工单
@REM echo 4. 测试修改工单
@REM curl -X PUT "%BASE_URL%" ^
@REM -H "Content-Type: application/json" ^
@REM -H "%AUTH_HEADER%" ^
@REM -d "{\"id\":1,\"name\":\"测试客户-已修改\",\"phone\":\"13888888888\",\"content\":\"测试工单内容-已修改\",\"status\":1}"
@REM echo.
@REM echo.

@REM @REM :: 5. 测试按条件查询
@REM echo 5. 测试按条件查询（按电话号码）
@REM curl -X GET "%BASE_URL%/list?phone=13888888888" -H "Accept: application/json" -H "%AUTH_HEADER%"
@REM echo.
@REM echo.

@REM @REM :: 6. 测试导出功能
@REM echo 6. 测试导出功能
@REM curl -X POST "%BASE_URL%/export" -H "%AUTH_HEADER%" -o "工单导出.xlsx"
@REM echo.
@REM echo.

@REM @REM :: 7. 测试删除功能
@REM echo 7. 测试删除功能（删除ID=1的工单）
@REM curl -X DELETE "%BASE_URL%/1" -H "Accept: application/json" -H "%AUTH_HEADER%"
@REM echo.
@REM echo.

@REM pause 