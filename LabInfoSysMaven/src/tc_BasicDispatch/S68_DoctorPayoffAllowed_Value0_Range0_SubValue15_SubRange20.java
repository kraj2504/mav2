//Full bill paid in registration. Due clearance not used.
package tc_BasicDispatch;

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

public class S68_DoctorPayoffAllowed_Value0_Range0_SubValue15_SubRange20
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
	public void s68_DoctorPayoffAllowed_Value0_Range0_SubValue15_SubRange20() throws Exception
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
			PO_ManageSample manageSamplePageObjects = PageFactory.initElements(driver,PO_ManageSample.class);
			PO_WorkList workOrderObj = PageFactory.initElements(driver,PO_WorkList.class);
			PO_BulkEntry bulkEntryObj = PageFactory.initElements(driver,PO_BulkEntry.class);
			PO_ManageApproval ManageApprovalObj = PageFactory.initElements(driver,PO_ManageApproval.class);
			PO_DueClearance dueClearance = PageFactory.initElements(driver,PO_DueClearance.class);
			PO_ManageDispatch manageDispatchobj = PageFactory.initElements(driver,PO_ManageDispatch.class);

			// Fetching Data from excel file
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sDoctorName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DoctorName);
			
			String sDepartmentName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Department1);
			String sValue = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DepValue1);
			String sRange = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DepRange1);
			
			String sSubDepartmentName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_SubDepartment1);
			String sSubDepValue = ExcelUtils.getCellData(iTestCaseRow, Constant.col_SubDepValue1);
			String sSubDepRange = ExcelUtils.getCellData(iTestCaseRow, Constant.col_SubDepRange1);
			
			String sInvestigationName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_InvestigationName1);
			
			String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
			String sFirstName = Utils.getRandomName(45);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
			String sMobileNo = Utils.getRandomNumber(13);
			String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
			String sReferralType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
			String sReferralName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralName);
			
			String sServiceName1 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			String sServiceName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName2);
			String sServiceName3 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName3);
			String sServiceName4 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName4);
			
			String sItemDiscountType1 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountType);
			String sItemDiscountType2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountType2);
			String sItemDiscountType3 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountType3);
			String sItemDiscountType4 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountType4);
			
			String sItemDiscountValue1 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountValue);
			String sItemDiscountValue2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountValue2);
			String sItemDiscountValue3 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountValue3);
			String sItemDiscountValue4 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountValue4);
			
			String sBillDiscountType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountType);
			String sApprovedBy = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
			String sBillDiscountTypeAs = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountTypeAs);
			String sBillDiscountValue = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DiscountValue);
			String sRemarks = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Remarks);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals(driver.getTitle(),"Registration","Login failed");
			MasterControlObject.changeRole("ACCOUNTANT");
			MasterControlObject.gotoPage("PayOff");
			
			doctorPayoff.searchDoctor(sDoctorName);
			doctorPayoff.deleteAllInvestigation();
			doctorPayoff.clearAllValuesForAllDept();
			doctorPayoff.clearAllRangeForAllDept();
			doctorPayoff.clearAllValueForAllSubDept();
			doctorPayoff.clearAllRangeForAllSubDept();
			
			doctorPayoff.clickSave();
//			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Updated Successfully", "*** Doctor payoff --> Alert not shown as expected ***");
			MasterControlObject.acceptAlert();
			
			doctorPayoff.selectDepartment(sDepartmentName);
			doctorPayoff.enterValueForSubDept(sSubDepartmentName, sSubDepValue);
			doctorPayoff.enterRangeForSubDept(sSubDepartmentName, sSubDepRange);
			
			doctorPayoff.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Updated Successfully", "*** Doctor payoff --> Alert not shown as expected ***");
			MasterControlObject.acceptAlert();
			
			doctorPayoff.searchDoctor(sDoctorName);
			doctorPayoff.verifyValueForSubDept(sSubDepartmentName, sSubDepValue);
			doctorPayoff.verifyRangeForSubDept(sSubDepartmentName, sSubDepRange);
			
			MasterControlObject.changeRole("RECEPTIONIST");
			
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
			String cashamount = generateBillPageObject.getCashAmount();
			
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			
			MasterControlObject.changeRole("ACCOUNTANT");
			MasterControlObject.gotoPage("PayOff Report");
			
			doctorPayoffReport.searchDoctor(sDoctorName);
			Assert.assertTrue(doctorPayoffReport.getPatientName().equalsIgnoreCase(sFirstName),"*** Patient details not showing in Doctor payoff Report ***");
			doctorPayoffReport.verifyPayOffAmount(sSubDepValue);
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