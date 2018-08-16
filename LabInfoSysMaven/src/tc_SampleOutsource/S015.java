// 
// Sample type --> Analyte, Referral type --> Self, No bill payment in registration, Full Bill Paid using Due clearance
// Check Email and Courier Check box field, No discounts, Full validate and full approval
package tc_SampleOutsource;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.PO_AnalyteMaster;
import pageObjects.PO_BulkEntry;
import pageObjects.PO_DueClearance;
import pageObjects.PO_Login;
import pageObjects.PO_ManageApproval;
import pageObjects.PO_ManageDispatch;
import pageObjects.PO_ManageSample;
import pageObjects.PO_MasterControl;
import pageObjects.PO_OutsourceAcknowledge;
import pageObjects.PO_PatientList;
import pageObjects.PO_Registration;
import pageObjects.PO_ResultValidate;
import pageObjects.PO_SampleOutsource;
import pageObjects.PO_SampleScannerAck;
import pageObjects.PO_WorkList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class S015 
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
	public void s015() throws Exception
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
			PO_DueClearance dueClearance = PageFactory.initElements(driver,PO_DueClearance.class);
			PO_ManageDispatch manageDispatchobj = PageFactory.initElements(driver,PO_ManageDispatch.class);
			PO_AnalyteMaster analyteMaster = PageFactory.initElements(driver,PO_AnalyteMaster.class);
			PO_SampleOutsource sampleOutsource = PageFactory.initElements(driver,PO_SampleOutsource.class);
			PO_OutsourceAcknowledge OutsourceAcknowledge = PageFactory.initElements(driver,PO_OutsourceAcknowledge.class);
			
			// Fetching Data from excel file
			String sUserName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName2);
			String sPassword2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password2);
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
			String sFirstName = Utils.getRandomName(45);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
			String sMobileNo = Utils.getRandomNumber(13);
			String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
			String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			
			String sOutsourcingTo = ExcelUtils.getCellData(iTestCaseRow, Constant.col_OutsourcingTo);
			
			//Mark an analyte as Outsource in Analyte master
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle(),"Login failed");
			MasterControlObject.changeRole("ADMINSTRATOR");
			
			analyteMaster.searchRecord(sServiceName);
			analyteMaster.MarkAnalyteAsNonOutsource();
			analyteMaster.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Updated Successfully","Login failed");//Updated Saved
			MasterControlObject.acceptAlert();
			MasterControlObject.ClickSignOut();
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals(driver.getTitle(),"Registration","Login failed");

			//Register patient and generate bill
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.selectServiceName(sServiceName);
			generateBillPageObject.EnterMailID(sMailID);
//			generateBillPageObject.enterAddress(sAddress, sArea, sCity, sState, sCountry, sPincode);
			
			String amount = generateBillPageObject.getDueAmount();
			Log.info("Generated bill amount is : "+amount);
			generateBillPageObject.EnterCashAmount(amount);
			String cashamount = generateBillPageObject.getCashAmount();
			
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			
			//Collecting sample
			MasterControlObject.changeRole("PHLEBOTOMY");
			manageSamplePageObjects.clickPatientDetailsTable();
			manageSamplePageObjects.searchRecord(sFirstName);
			manageSamplePageObjects.selectPatient();
			Assert.assertEquals(manageSamplePageObjects.getRowColor(sServiceName), Constant.WHITE,"*** Color not showing as expected ***");
						
			manageSamplePageObjects.clickCollectSample();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Saved Successfully","***Collecting sample --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample Collected");
			List<String> barcodes = patientListPageObjects.getAllBarCode();
			
			//Acknowledge sample
			MasterControlObject.changeRole("TECHNICIAN");
			MasterControlObject.gotoPage("Sample Scanner Ack");
			sampleScannerAck.AcknowledgeAllSamples(barcodes);
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample Received");
			
			//Enter Bulk Entry
			MasterControlObject.gotoPage("Bulk Entry");
			bulkEntryObj.searchRecord(sFirstName);			
			bulkEntryObj.enterResult();
			bulkEntryObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Entered Successfully","***Alert not displayed as expected after bulk entry***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Result Entered");
			
			//Approve a record in Manage Approval
			MasterControlObject.changeRole("PATHOLOGIST");
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.recheckResult();
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Saved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Recheck");
		
			//Enter Bulk Entry
			MasterControlObject.changeRole("TECHNICIAN");
			MasterControlObject.gotoPage("Bulk Entry");
			bulkEntryObj.searchRecord(sFirstName);			
			bulkEntryObj.enterResult();
			bulkEntryObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Entered Successfully","***Alert not displayed as expected after bulk entry***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Result Entered");

			//Approve a record in Manage Approval
			MasterControlObject.changeRole("PATHOLOGIST");
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Approved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Approved");
			
			//EMailing Final report
			MasterControlObject.changeRole("DISPATCH");
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickEmail();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Emailed","***Alert msg not displayed as expected after click email***");
			MasterControlObject.acceptAlert();
			MasterControlObject.changeRole("RECEPTIONIST");
			MasterControlObject.changeRole("DISPATCH");
			manageDispatchobj.selectFilter("Show Emailed Report");
			manageDispatchobj.searchRecord(sFirstName);
			Assert.assertEquals(Integer.toString(manageDispatchobj.verifyPatientName(sFirstName)),"1","Patient details not showing in EmailedReport");
			
			//Dispatching the record
			MasterControlObject.changeRole("RECEPTIONIST");
			MasterControlObject.changeRole("DISPATCH");
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickDispatch();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Printed","***Alert msg not displayed as expected after click dispatch***");
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
		driver.quit();
    }
}
