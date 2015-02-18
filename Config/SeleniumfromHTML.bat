@echo on
c:
REM cd C:\Users\muralikrishnanb\Desktop
cd %userprofile%\Desktop

FOR /F "tokens=* delims=" %%p in ('findstr "Project=" ExecTest.txt') DO (
set q=%%p


)
ECHO %SUBSTRING%
SET SUBSTRING=%q:~8%

for /f %%i in ("%0") do set curpath=%%~dpi


set javaTestProjectPath=%SUBSTRING%

D:

cd %javaTestProjectPath%

set classpath=%javaTestProjectPath%\bin;%javaTestProjectPath%\Libraries\testng-6.8.jar;%javaTestProjectPath%\Libraries\jxl.jar;%javaTestProjectPath%\Libraries\selenium-server-standalone-2.39.0.jar;%javaTestProjectPath%\Libraries\mail-1.4.7.jar;%javaTestProjectPath%\Libraries\mysql-connector-java-5.0.8-bin.jar;

javac -verbose %javaTestProjectPath%\src\smoke\*.java %javaTestProjectPath%\src\main\common\*.java -d %javaTestProjectPath%\bin


	
	java smoke.TestngXMLGenerator_From_XL_File

	java org.testng.TestNG %javaTestProjectPath%\testng.xml
	
