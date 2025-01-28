@echo off
call mvn  package -DskipTests -pl ruoyi-modules/ruoyi-device -am
pause 