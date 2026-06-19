@echo off
set JAVA_HOME=C:\Users\guill\.jdks\openjdk-26.0.1
set JAVAFX_HOME=C:\Users\guill\Downloads\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1
set PROJECT_DIR=C:\Users\guill\OneDrive\Desktop\java_practices\WC2026Fixture
set TARGET=%PROJECT_DIR%\target\classes
set LIBS=%PROJECT_DIR%\lib

"%JAVA_HOME%\bin\javac" --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml -cp "%LIBS%\*" -d "%TARGET%" -sourcepath "%PROJECT_DIR%\src\main\java" "%PROJECT_DIR%\src\main\java\com\mundial\Main.java"

"%JAVA_HOME%\bin\java" --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics,ALL-UNNAMED -cp "%TARGET%;%LIBS%\*" com.mundial.Main
