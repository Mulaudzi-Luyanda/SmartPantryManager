@echo off
setlocal
set "PROJECT=%~dp0"
set "VERSION=8.2.1"
set "DIST=%USERPROFILE%\.gradle\smartpantry-distributions\gradle-%VERSION%"
set "GRADLE_EXE=%DIST%\bin\gradle.bat"
if not exist "%GRADLE_EXE%" (
  echo First run: downloading Gradle %VERSION% from services.gradle.org...
  powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%PROJECT%scripts\install-gradle.ps1" -Version %VERSION%
  if errorlevel 1 exit /b 1
)
call "%GRADLE_EXE%" -p "%PROJECT%" %*
exit /b %errorlevel%
