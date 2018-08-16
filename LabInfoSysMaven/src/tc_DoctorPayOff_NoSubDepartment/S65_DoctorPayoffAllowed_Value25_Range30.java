//Full bill paid in registration. Due clearance not used.
package tc_DoctorPayOff_NoSubDepartment;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_BulkEntry;
import pageObjects.PO_DoctorPayOff;
import pageObjects.PO_DoctorPayoffReport;
import pageObjects.PO_DueClearance;
import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_ManageApproval;
import pageObjects.PO_ManageDispatch;
import pageObjects.PO_ManageSample;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import pageObjects.PO_WorkList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class S65_DoctorPayoffAllowed_Value25_Range30
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
	public void s65_DoctorPayoffAllowed_Value25_Range30() throws Exception
	{

		try 
		{
			// Instantiate PageObjects class
			PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
			PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			PO_DoctorPayOff doctorPayoff = PageFactory.initElements(driver,PO_DoctorPayOff.class);
			PO_DoctorPayoffReport doctorPayoffReport = PageFactory.initElements(driver,PO_DoctorPayoffReport.class);
			PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
			PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);

			// Fetching Data from excel file
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sDoctorName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DoctorName);
			String sDepartmentName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Department1);
			String sValue = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DepValue1);
			String sRange = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DepRange1);
			String sInvestigationName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_InvestigationName1);
			String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
			String sFirstName = Utils.getRandomName(45);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
			String sMobileNo = Utils.getRandomNumber(13);
			String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
			String sReferralType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals(driver.getTitle(),"Registration","Login failed");
			MasterControlObject.CancelPrinterSetting();
			MasterControlObject.changeRole(Constant.ACCOUNTS);
			MasterControlObject.gotoPage("PayOff");
			
			doctorPayoff.searchDoctor(sDoctorName);
			doctorPayoff.deleteAllInvestigation();
			doctorPayoff.clearAllRangeForAllDept();
			doctorPayoff.clearAllValuesForAllDept();
			
			doctorPayoff.enterValueForDept(sDepartmentName, sValue);
			doctorPayoff.enterRangeForDept(sDepartmentName, sRange);
			
			doctorPayoff.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Updated Successfully", "*** Doctor payoff --> Alert not shown as expected ***");
			MasterControlObject.acceptAlert();
			
			doctorPayoff.searchDoctor(sDoctorName);
			doctorPayoff.verifyRangeForDept(sDepartmentName, sRange);
			doctorPayoff.verifyValueForDept(sDepartmentName, sValue);
			
			MasterControlObject.changeRole(Constant.RECEPTION);
			
			//Register patient and generate bill
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.EnterMailID(sMailID);
			generateBillPageObject.selectReferralType(sReferralType);
//			generateBillPageObject.selectReferralName(sReferralName);
			generateBillPageObject.selectDoctorName(sDoctorName);
			
			generateBillPageObject.EnterServiceName(1,sInvestigationName);
			
			String amount = generateBillPageObject.getDueAmount();
			Log.info("Generated bill amount is : "+amount);
			generateBillPageObject.EnterCashAmount(amount);
//			String cashamount = generateBillPageObject.getCashAmount();
			
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			
			MasterControlObject.changeRole(Constant.ACCOUNTS);
			MasterControlObject.gotoPage("PayOff Report");
			
			doctorPayoffReport.searchDoctor(sDoctorName);
			Assert.assertTrue(doctorPayoffReport.getPatientName().equalsIgnoreCase(sFirstName),"*** Patient details not showing in Doctor payoff Report ***");
			doctorPayoffReport.verifyPayOffAmount(sValue);
			doctorPayoffReport.verifyOverAllAmount();
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
			Log.info("Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		Log.endTestCase(sTestCaseName);
//		driver.close();
    }
}