
set javaTestProjectPath=D:\WORKSPACE\selenium

c:
cd %javaTestProjectPath%

set path=C:\Program Files (x86)\Java\jdk1.7.0_03\bin

set classpath=%javaTestProjectPath%\bin;%javaTestProjectPath%\Lib\testng-6.8.jar;%javaTestProjectPath%\Lib\ATU_ALM_ServiceWrapper_1.0.jar;%javaTestProjectPath%\Lib\jacob.jar;%javaTestProjectPath%\Lib\jacob-1.17-x86.dll;%javaTestProjectPath%\Lib\jxl-2.6.jar;%javaTestProjectPath%\Lib\selenium-server-standalone-2.33.0.jar;

javac -verbose %javaTestProjectPath%\src\cleartrip\*.java -d %javaTestProjectPath%\bin

java cleartrip.TestngXMLGenerator_From_Text_File

java org.testng.TestNG %javaTestProjectPath%\testng.xml