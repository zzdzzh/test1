@echo off
chcp 65001

@REM echo 测试创建菜单
@REM curl -X POST ^
@REM   http://localhost:9400/wx/menu ^
@REM   -H "Content-Type: application/json" ^
@REM   -d "{\"button\":[{\"type\":\"view\",\"name\":\"百度\",\"url\":\"http://www.baidu.com\"},{\"type\":\"click\",\"name\":\"点击\",\"key\":\"V1001_CLICK\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com\"},{\"type\":\"click\",\"name\":\"赞一下\",\"key\":\"V1001_GOOD\"}]}]}"

echo.
echo 测试查询菜单
curl -X GET http://localhost:9400/wx/menu

@REM echo.
@REM echo 测试删除菜单
@REM curl -X DELETE http://localhost:9400/wx/menu

pause 