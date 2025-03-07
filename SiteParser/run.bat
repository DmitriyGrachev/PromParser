@echo off
echo Запуск сервісу на Spring boot...
java -jar target/SiteParser-0.0.1-SNAPSHOT.jar
if %ERRORLEVEL% NEQ 0 (
    echo App finished with error. Error message: %ERRORLEVEL%
    pause
) else (
    echo Роботу завершено успішно
    pause
)