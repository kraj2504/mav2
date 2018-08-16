package testCases2;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_Login;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class Login_SuccessfulLogin 
{
	private static WebDriver driver = null;
	private String sTestCaseName;
	private int iTestCaseRow;
	
	@BeforeTest
	public void beforeMethod() throws Exception
	{
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
	public void successfulLogin() throws Exception
	{	
		try
		{
			 // Fetching User name and password from excel file
			 String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			 String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password); 
			 
			 // Instantiate LoginPageObjects class
			 PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			 
			 loginPageObject.login(sUserName, sPassword);
			 Assert.assertEquals("Registration", driver.getTitle());
		}
		
		catch(Exception e)
		{
			  ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			  Utils.takeScreenshot(driver, sTestCaseName);
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
			Log.info("Login success ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Login not success ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		else
		{
			ExcelUtils.setCellData("NIL", iTestCaseRow, Constant.col_Result);
			Log.info("Cannot able to get result");	
		}
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Log.endTestCase(sTestCaseName);
		driver.close();
    }
}