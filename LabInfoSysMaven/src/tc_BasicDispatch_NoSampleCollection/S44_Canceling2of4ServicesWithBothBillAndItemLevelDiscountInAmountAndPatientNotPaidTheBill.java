package tc_BasicDispatch_NoSampleCollection;
//import org.testng.Assert;
//import org.testng.ITestResult;
//import java.awt.Robot;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


//import pageObjects.PO_PatientList;











import pageObjects.PO_BulkEntry;
import pageObjects.PO_DueClearance;
import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_ManageApproval;
import pageObjects.PO_ManageDispatch;
import pageObjects.PO_ManageSample;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import pageObjects.PO_RefundCancel;
import pageObjects.PO_ResultValidate;
import pageObjects.PO_SampleScannerAck;
import pageObjects.PO_WorkList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class S44_Canceling2of4ServicesWithBothBillAndItemLevelDiscountInAmountAndPatientNotPaidTheBill
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
     // This scenario is for Canceling any 2 of 4 services, with both bill in percentage and item level discount in amount and patient not paid the bill. 
	@Test(priority = 1)
	public void s44_Canceling2of4ServicesWithBothBillAndItemLevelDiscountInAmountAndPatientNotPaidTheBill() throws Exception
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
		String sEmailId = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
		String sServiceName1 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
		String sServiceName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName2);
		String sServiceName3 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName3);
		String sServiceName4 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName4);
		String sBillDiscountType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountType);
		String sBillDiscountTypeAs=ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountTypeAs);
		String sEnterBillDiscount=ExcelUtils.getCellData(iTestCaseRow, Constant.col_DiscountValue);
		String sApprovedBy= ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
		String sRemarks= ExcelUtils.getCellData(iTestCaseRow, Constant.col_Remarks);
		String sRefundApprovedBy= ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
		String sRefundRemarks = ExcelUtils.getCellData(iTestCaseRow, Constant.col_RemarksRefund);
	
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_WorkList workOrderObj = PageFactory.initElements(driver,PO_WorkList.class);
		PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);
		PO_RefundCancel Refund_object=PageFactory.initElements(driver,PO_RefundCancel.class);
		PO_ManageSample manageSamplePageObjects = PageFactory.initElements(driver,PO_ManageSample.class);
		PO_SampleScannerAck sampleScannerAck = PageFactory.initElements(driver,PO_SampleScannerAck.class);
		PO_BulkEntry bulkEntryObj = PageFactory.initElements(driver,PO_BulkEntry.class);
		PO_ResultValidate resultValidate = PageFactory.initElements(driver,PO_ResultValidate.class);
		PO_ManageApproval ManageApprovalObj = PageFactory.initElements(driver,PO_ManageApproval.class);
		PO_ManageDispatch manageDispatchobj = PageFactory.initElements(driver,PO_ManageDispatch.class);
		PO_DueClearance dueClearance = PageFactory.initElements(driver,PO_DueClearance.class);
		try 
		{
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
			generateBillPageObject.EnterMailID(sEmailId);
			generateBillPageObject.EnterServiceName(1,sServiceName1);
			generateBillPageObject.EnterServiceName(2,sServiceName2);
			generateBillPageObject.EnterServiceName(3,sServiceName3);
			generateBillPageObject.EnterServiceName(4,sServiceName4);
			generateBillPageObject.EnterItemDiscount(1,"Amount");
			generateBillPageObject.EnterItemDiscount(2,"Amount");
			generateBillPageObject.selectBillDiscountType(sBillDiscountType);
			System.out.println("sBillDiscountTypeAs:" + sBillDiscountTypeAs);
			generateBillPageObject.selectBillDiscountTypeAs(sBillDiscountTypeAs);
			generateBillPageObject.enterBillDiscount(sEnterBillDiscount);
			generateBillPageObject.selectApprovedBy(sApprovedBy);
			generateBillPageObject.enterRemarks(sRemarks);

			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertEquals(patientListPageObjects.getStatus(),"Registered","***Status not updated as expected***");
	
			//Refund Cancel
			MasterControlObject.gotoPage("Refund Cancel");
			Refund_object.searchRecord(sFirstName);
			Refund_object.selectPatient();
			Refund_object.SelectServiceforRefund(sServiceName1);
			Refund_object.SelectServiceforRefund(sServiceName2);
			
			String amt1 = Refund_object.getNetAmountOfService(sServiceName1);
			String amt2 = Refund_object.getNetAmountOfService(sServiceName2);
			
			String sumOfRefundAmt = Float.toString(Float.parseFloat(amt1)+Float.parseFloat(amt2));
						
			String due = Refund_object.verifyOutstandingAmount(sumOfRefundAmt);
			String refund = Refund_object.verifyRefundAmount(sumOfRefundAmt);
			
			Refund_object.EnterCashAmount(refund);
			String Cashamount= Refund_object.getCashAmount();
			Assert.assertEquals(Cashamount,refund,"***Cannot able to key Amount in cash amount text box***");
			
			Refund_object.selectApprovedBy(sRefundApprovedBy);
			Refund_object.enterRemarks(sRefundRemarks);
			Refund_object.clickSave();
			String msg=  MasterControlObject.getAlertMsg();
			Assert.assertEquals(msg, "Saved Successfully","***Alert msg not as expected***");
			MasterControlObject.acceptAlert();
			Utils.keyEsc();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatus(sServiceName1, sServiceName2,"Cancelled");
			patientListPageObjects.verifyingStatus(sServiceName3, sServiceName4,"Registered");
			Assert.assertTrue(patientListPageObjects.getPatientBillDue().startsWith(due),"Due amount not showing correctly in patient list");
			
			//collecting due amount 
			MasterControlObject.gotoPage("Due Clearance");
			dueClearance.clickRefresh();
			dueClearance.searchRecord(sFirstName);
			dueClearance.selectPatient();
			String actualBalance = dueClearance.getBalance();
			dueClearance.enterCashAmount(actualBalance);
			dueClearance.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Saved Successfully","Alert not showing as expected after entering Due");
			MasterControlObject.acceptAlert();
			Utils.keyEsc();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertEquals(patientListPageObjects.getPatientBillDue(),"0.00","Actual and expected due values not same.");
	
			//Collect Sample
			MasterControlObject.changeRole(Constant.PHLEBOTOMY);
			manageSamplePageObjects.clickPatientDetailsTable();
			manageSamplePageObjects.searchRecord(sFirstName);
			manageSamplePageObjects.selectPatient();
			manageSamplePageObjects.clickCollectSample();
			String msg1 = MasterControlObject.getAlertMsg();
			Assert.assertEquals("Saved Successfully", msg1,"***Collecting sample --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatus(sServiceName1, sServiceName2,"Cancelled");
			patientListPageObjects.verifyingStatus(sServiceName3, sServiceName4,"Sample Collected");

			//Enter Bulk Entry
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage("Bulk Entry");
			bulkEntryObj.searchRecord(sFirstName);	
			bulkEntryObj.enterResult();
			bulkEntryObj.clickSave();
			//Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Entered Successfully","***Alert not displayed as expected after bulk entry***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Cancelled", sServiceName1, sServiceName2);
			patientListPageObjects.verifyingStatusBarCode("Result Entered", sServiceName3, sServiceName4);
			
			//Approving the record in Manage Approval
			MasterControlObject.changeRole(Constant.AUTHORIZATION);
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Approved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Cancelled", sServiceName1, sServiceName2);
			patientListPageObjects.verifyingStatusBarCode("Approved", sServiceName3, sServiceName4);
			
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
			
			//Assert.assertEquals(msg,"Dispatch List Printed","***Alert msg not displayed as expected***");
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
			Log.info("Bill generated with no due ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Bill not generated with no due ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		Log.endTestCase(sTestCaseName);
		driver.quit();
    }
	
}
