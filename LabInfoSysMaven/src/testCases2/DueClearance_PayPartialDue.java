package testCases2;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_DueClearance;
import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class DueClearance_PayPartialDue
{
	private static WebDriver driver = null;
	private String sTestCaseName;
	private int iTestCaseRow;
	
	@BeforeTest
	public void beforeMethod() throws Exception
	{
		// Provide Log4j configuration settings
		DOMConfigurator.configure(Constant.log4jXMLpath);

		// Getting current Test Case name
		sTestCaseName = Utils.getTestCaseName(this.toString());
		
		Log.startTestCase(sTestCaseName);

		// Fetching test data's row from excel using current test case name
		ExcelUtils.openExcelFile(Constant.Path_TestData + Constant.File_TestData,"Sheet1");
		iTestCaseRow = ExcelUtils.getRowNumber(sTestCaseName,Constant.col_TestCaseName);

		//Launching Browser using test data's row and load default URL
		driver = Utils.OpenBrowser(iTestCaseRow);
	}

	@Test(priority = 1)
	public void generateBillingUsingMandatoryFieldInputs() throws Exception
	{
		// Fetching Data from excel file
		String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
		String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
		
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(45);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(13);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
				
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);
		PO_DueClearance dueClearance = PageFactory.initElements(driver,PO_DueClearance.class);
				
		try 
		{
			//User Login
			loginPageObject.login(sUserName, sPassword);
			
			//Register patient and generate bill
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.selectServiceName(sServiceName);
			generateBillPageObject.ClickGenerateBill();

			//Enter Due amount
			MasterControlObject.gotoPage("Due Clearance");
			dueClearance.searchRecord(sFirstName);
			dueClearance.selectPatient();
			String actualBalance = dueClearance.getBalance();
			int balance = Integer.parseInt(actualBalance);
			dueClearance.enterCashAmount(Integer.toString(balance-(balance/3)));
			dueClearance.clickSave();
			String msg = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			
			//To close print window
			Utils.keyEsc();
			
			//Verify due in patient list
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			
			Assert.assertEquals(msg, "Saved Successfully","Alert not showing as expected");
			Assert.assertTrue(patientListPageObjects.getPatientBillDue().startsWith(Integer.toString(balance-(balance-(balance/3)))),"Actual and expected due values not same");
		}
		
		catch(Exception e)
		{
			// If in case you got any exception during the test, it will mark your test as Fail in the test result sheet
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
	 
			// If the exception is in between the test, bcoz of any element not found or anything, this will take a screen shot
			Utils.takeScreenshot(driver, sTestCaseName);
	 
			// This will print the error log message
			Log.error(e.getMessage());
	 
			// Again throwing the exception to fail the test completely in the TestNG results
			throw (e);
		}
	}
	
	@AfterMethod
	public void updateResult(ITestResult result) throws Exception
	{
		if(result.getStatus() == ITestResult.SUCCESS)
		{
			ExcelUtils.setCellData("Pass", iTestCaseRow, Constant.col_Result);
			Log.info("Partial Due paid ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Due amount not correct ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Log.endTestCase(sTestCaseName);
		driver.close();
    }
}
