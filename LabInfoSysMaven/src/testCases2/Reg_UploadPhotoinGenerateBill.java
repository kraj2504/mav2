package testCases2;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_MasterControl;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class Reg_UploadPhotoinGenerateBill 
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
	public void uploadPhotoinGenerateBill() throws Exception
	{	
		try
		{
			 // Fetching User name and password from excel file
			 String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			 String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password); 
			 
			 // Instantiate PageObjects class
			 PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			 PO_Registration generateBillObj = PageFactory.initElements(driver,PO_Registration.class);
			 PO_MasterControl MasterControlObj = PageFactory.initElements(driver,PO_MasterControl.class);
			 
			 loginPageObject.login(sUserName, sPassword);
			 		 
			 generateBillObj.uploadPhoto();
			 
			 Assert.assertEquals(MasterControlObj.getAlertMsg(),"Image Upload Successfully","***Image Upload failed***");
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
			Log.info("Image uploaded ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Image not uploaded ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Log.endTestCase(sTestCaseName);
		driver.close();
    }
}