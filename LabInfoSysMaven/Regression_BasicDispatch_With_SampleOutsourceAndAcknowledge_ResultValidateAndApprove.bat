set projectLocation=D:\eclipse-workspace\LabInfoSysTestNG
cd %projectLocation% 
set classpath=%projectLocation%\bin;%projectLocation%\lib\* 
java -cp bin;lib/* org.testng.TestNG %projectLocation%\BasicDispatch_With_SampleOutsourceAndAcknowledge_ResultValidateAndApprove.xml
pause