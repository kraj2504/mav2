//Full bill paid in registration. Due clearance not used.
package tc_BasicDispatch_SelectPrinter_SampleAcknowledgementByTechnician_ResultValidate;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pageObjects.PO_DoctorPayOff;
import pageObjects.PO_Login;
import pageObjects.PO_MasterControl;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class S64_DoctorPayoffNotAllowed_Value25_Range20
{
	private static WebDriver driver = null;
	private String sTestCaseName;
	private int iTestCaseRow;
		
	@BeforeTest
	public void beforeMethod() throws Exception
	{
		DOMConfigurator.configure(Constant.log4jXMLpath);
		sTestCaseName = Utils.getTestCaseName(this.toString());
		Log.startTestCase(sTestCaseName);
		ExcelUtils.openExcelFile(Constant.Path_TestData + Constant.File_TestData,"Sheet1");
		iTestCaseRow = ExcelUtils.getRowNumber(sTestCaseName,Constant.col_TestCaseName);
		driver = Utils.OpenBrowser(iTestCaseRow);
	}

	@Test(priority = 1)
	public void s64_DoctorPayoffNotAllowed_Value25_Range20() throws Exception
	{

		try 
		{
			// Instantiate PageObjects class
			PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
			PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			PO_DoctorPayOff doctorPayoff = PageFactory.initElements(driver,PO_DoctorPayOff.class);
			
			// Fetching Data from excel file
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sDoctorName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DoctorName);
			String sDepartmentName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Department1);
			String sValue = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DepValue1);
			String sRange = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DepRange1);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals(driver.getTitle(),"Registration","Login failed");
			MasterControlObject.CancelPrinterSetting();
			MasterControlObject.changeRole("ACCOUNTANT");
			MasterControlObject.gotoPage("PayOff");
			
			doctorPayoff.searchDoctor(sDoctorName);
			doctorPayoff.clearAllValuesForAllDept();
			doctorPayoff.clearAllRangeForAllDept();
			doctorPayoff.clearAllValueForAllSubDept();
			doctorPayoff.clearAllRangeForAllSubDept();
			doctorPayoff.deleteAllInvestigation();
			
			doctorPayoff.enterValueForDept(sDepartmentName, sValue);
			doctorPayoff.enterRangeForDept(sDepartmentName, sRange);
			
			doctorPayoff.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Value must be less than Range", "*** Doctor payoff --> Alert not shown as expected ***");
			MasterControlObject.acceptAlert();
		}
		
		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
			Log.error(e.getMessage());
			throw (e);
		}
	}
	
	@AfterMethod
	public void updateResult(ITestResult result) throws Exception
	{
		if(result.getStatus() == ITestResult.SUCCESS)
		{
			ExcelUtils.setCellData("Pass", iTestCaseRow, Constant.col_Result);
			Log.info("Full due paid ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Full due not paid ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		Log.endTestCase(sTestCaseName);
		driver.close();
    }
}