set projectLocation=D:\eclipse-workspace\LabInfoSysTestNG
cd %projectLocation% 
set classpath=%projectLocation%\bin;%projectLocation%\lib\* 
java -cp bin;lib/* org.testng.TestNG %projectLocation%\test-output\LIS-Smoke-suite1\testng-failed.xml
pause