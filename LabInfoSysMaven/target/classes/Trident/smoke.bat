set projectLocation=D:\eclipse-workspace\LabInfoSysTestNG
cd %projectLocation% 
set classpath=%projectLocation%\bin;%projectLocation%\lib\* 
java -cp bin;lib/* org.testng.TestNG %projectLocation%\Clients\Trident\smoke.xml
pause