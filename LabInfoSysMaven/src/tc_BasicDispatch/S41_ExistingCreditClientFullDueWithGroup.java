package tc_BasicDispatch;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_BulkEntry;
import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_ManageApproval;
import pageObjects.PO_ManageDispatch;
import pageObjects.PO_ManageSample;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import pageObjects.PO_ResultValidate;
import pageObjects.PO_SampleScannerAck;
import pageObjects.PO_WorkList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class S41_ExistingCreditClientFullDueWithGroup
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
	public void s41_ExistingClientFullDueWithGroup() throws Exception
	{

		try 
		{
			// Instantiate PageObjects class
			PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
			PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
			PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);
			PO_ManageSample manageSamplePageObjects = PageFactory.initElements(driver,PO_ManageSample.class);
			PO_WorkList workOrderObj = PageFactory.initElements(driver,PO_WorkList.class);
			PO_SampleScannerAck sampleScannerAck = PageFactory.initElements(driver,PO_SampleScannerAck.class);
			PO_BulkEntry bulkEntryObj = PageFactory.initElements(driver,PO_BulkEntry.class);
			PO_ResultValidate resultValidate = PageFactory.initElements(driver,PO_ResultValidate.class);
			PO_ManageApproval ManageApprovalObj = PageFactory.initElements(driver,PO_ManageApproval.class);
			PO_ManageDispatch manageDispatchobj = PageFactory.initElements(driver,PO_ManageDispatch.class);
			
			// Fetching Data from excel file
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
			String sFirstName = Utils.getRandomName(45);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
			String sMobileNo = Utils.getRandomNumber(13);
			String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
			String sReferralType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
			String sReferralName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralName);
			String sDoctorName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DoctorName);
			String sRider = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Rider);
			String sServiceName1 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			String sServiceName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName2);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle(),"Login failed");
			
			//Register patient and generate bill
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.EnterMailID(sMailID);
			generateBillPageObject.selectReferralType(sReferralType);
			generateBillPageObject.selectReferralName(sReferralName);
			generateBillPageObject.selectDoctorName(sDoctorName);
			generateBillPageObject.selectRider(sRider);
			generateBillPageObject.selectServiceName(sServiceName1);
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			
			//Generate a new bill for the patient
			MasterControlObject.gotoPage("Registration");
			generateBillPageObject.searchRecord(sFirstName);
			generateBillPageObject.selectReferralType(sReferralType);
			generateBillPageObject.selectReferralName(sReferralName);
			generateBillPageObject.selectDoctorName(sDoctorName);
			generateBillPageObject.selectRider(sRider);
			generateBillPageObject.selectServiceName(sServiceName2);
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.selectPatientBill();
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			
			//Collecting sample
			MasterControlObject.changeRole(Constant.PHLEBOTOMY);
			manageSamplePageObjects.clickPatientDetailsTable();
			manageSamplePageObjects.searchRecord(sFirstName);
			manageSamplePageObjects.selectPatient();
			manageSamplePageObjects.clickCollectSample();
			String msg = MasterControlObject.getAlertMsg();
			Assert.assertEquals(msg,"Saved Successfully","***Collecting sample --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.selectPatientBill();
			patientListPageObjects.verifyingStatusBarCode("Sample Collected");

			//Enter Bulk Entry
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage("Bulk Entry");
			bulkEntryObj.searchRecord(sFirstName);			
			bulkEntryObj.enterResult();
			bulkEntryObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Entered Successfully","***Alert not displayed as expected***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.selectPatientBill();
			patientListPageObjects.verifyingStatusBarCode("Result Entered");
			
			//Approve only half results in Manage Approval
			MasterControlObject.changeRole(Constant.AUTHORIZATION);
			ManageApprovalObj.searchRecord(sFirstName);
//			ManageApprovalObj.deselectHalfResults();
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Approved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.selectPatientBill();
			patientListPageObjects.verifyingStatusBarCode("Approved");
		
			//EMailing Final report
			MasterControlObject.changeRole(Constant.DISPATCH);
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickEmail();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Emailed","***Alert msg not displayed as expected after click email***");
			MasterControlObject.acceptAlert();
			MasterControlObject.changeRole(Constant.RECEPTION);
			MasterControlObject.changeRole(Constant.DISPATCH);
			manageDispatchobj.selectFilter("Show Emailed Report");
			manageDispatchobj.searchRecord(sFirstName);
			Assert.assertEquals(Integer.toString(manageDispatchobj.verifyPatientName(sFirstName)),"1","Patient details not showing in EmailedReport");

			//Dispatching the record
			MasterControlObject.changeRole(Constant.DISPATCH);
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickDispatch();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Printed","***Alert msg not displayed as expected after click dispatch***");
			MasterControlObject.acceptAlert();
			MasterControlObject.changeRole(Constant.RECEPTION);
			MasterControlObject.changeRole(Constant.DISPATCH);
			manageDispatchobj.selectFilter("Show Printed Report");
			manageDispatchobj.searchRecord(sFirstName);
			Assert.assertEquals(Integer.toString(manageDispatchobj.verifyPatientName(sFirstName)),"1","Patient details not showing in PrintedReport");
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
		driver.quit();
    }
}