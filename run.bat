@echo off
set JAVA_HOME=C:\Users\guill\.jdks\openjdk-26.0.1
set PROJECT_DIR=C:\Users\guill\OneDrive\Desktop\java_practices\WC2026Fixture
set TARGET=%PROJECT_DIR%\target\classes
set LIBS=%PROJECT_DIR%\lib

"%JAVA_HOME%\bin\javac" -cp "%LIBS%\*" -d "%TARGET%" -sourcepath "%PROJECT_DIR%\src\main\java" "%PROJECT_DIR%\src\main\java\com\mundial\Main.java" "%PROJECT_DIR%\src\main\java\com\mundial\server\ApiServer.java"

"%JAVA_HOME%\bin\java" -cp "%TARGET%;%LIBS%\*" com.mundial.Main
