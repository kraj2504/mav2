// 
// Sample type --> Analyte, Referral type --> Self, No bill payment in registration, Full Bill Paid using Due clearance
// Check Email and Courier Check box field, No discounts, Full validate and full approval
package tc_SampleOutsource_ResultValidate_SelectPrinter;

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

public class S021 
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
	public void s021() throws Exception
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
			String sServiceName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName2);
			
			String sAddress = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Address);
			String sArea = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Area);
			String sCity = ExcelUtils.getCellData(iTestCaseRow, Constant.col_City);
			String sState = ExcelUtils.getCellData(iTestCaseRow, Constant.col_State);
			String sCountry = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Country);
			String sPincode = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Pincode);
			
			String sOutsourcingTo = ExcelUtils.getCellData(iTestCaseRow, Constant.col_OutsourcingTo);
			
			//Mark an analyte as Outsource in Analyte master
			loginPageObject.login(sUserName2, sPassword2);
			Assert.assertEquals("Analyte Master", driver.getTitle(),"Login failed");
			MasterControlObject.selectPrinter();
			
			analyteMaster.searchRecord(sServiceName2);
			analyteMaster.MarkAnalyteAsOutsource();
			analyteMaster.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Updated Successfully","Login failed");
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
			generateBillPageObject.checkEmail();
			generateBillPageObject.checkCourier();
			generateBillPageObject.enterAddress(sAddress, sArea, sCity, sState, sCountry, sPincode);
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			
			//Collecting sample
			MasterControlObject.changeRole("PHLEBOTOMIST");
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
			
			//Verify record in Sample Outsource
			MasterControlObject.gotoPage("Sample Outsource");
			Assert.assertEquals(sampleOutsource.isPatientDisplayed(sFirstName),"FALSE");
			sampleOutsource.viewNonOutsourcedTest();
			Assert.assertEquals(sampleOutsource.isPatientDisplayed(sFirstName),"TRUE");
			Assert.assertEquals(sampleOutsource.isInvestigationDisplayed(sFirstName, sServiceName),"TRUE");
			sampleOutsource.selectPatient(sFirstName);
			sampleOutsource.selectOutsourcingTo(sOutsourcingTo);
			sampleOutsource.clickSampleOutsource();
			Assert.assertEquals( MasterControlObject.getAlertMsg(),"Sample Outsourced Successfully","***Sample Outsource --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample OutSourced");
			
			//Outsource acknowledge
			MasterControlObject.gotoPage("Outsource Acknowledge");
			Assert.assertEquals(OutsourceAcknowledge.isPatientDisplayed(sFirstName),"TRUE");
			Assert.assertEquals(OutsourceAcknowledge.isInvestigationDisplayed(sFirstName, sServiceName),"TRUE");
			OutsourceAcknowledge.selectPatient(sFirstName);
			OutsourceAcknowledge.clickSave();
			Assert.assertEquals( MasterControlObject.getAlertMsg(),"Saved Successfully","***Sample Outsource --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Result Received");
			
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
			
			//Result Validate
			MasterControlObject.changeRole("SENIOR TECHNICIAN");
			resultValidate.searchRecord(sFirstName);
			resultValidate.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Results Validate Successfully","*** Alert not shown as expected after RESULT VALIDATION ***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Result Validated");
			
			//Approve a record in Manage Approval
			MasterControlObject.changeRole("PATHOLOGIST");
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.recollectSample();
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Saved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Recollect");
			
			//Collecting sample
			MasterControlObject.changeRole("PHLEBOTOMIST");
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
			
			//Verify record in Sample Outsource
			MasterControlObject.gotoPage("Sample Outsource");
			Assert.assertEquals(sampleOutsource.isPatientDisplayed(sFirstName),"FALSE");
			sampleOutsource.viewNonOutsourcedTest();
			Assert.assertEquals(sampleOutsource.isPatientDisplayed(sFirstName),"TRUE");
			Assert.assertEquals(sampleOutsource.isInvestigationDisplayed(sFirstName, sServiceName),"TRUE");
			sampleOutsource.selectPatient(sFirstName);
			sampleOutsource.selectOutsourcingTo(sOutsourcingTo);
			sampleOutsource.clickSampleOutsource();
			Assert.assertEquals( MasterControlObject.getAlertMsg(),"Sample Outsourced Successfully","***Sample Outsource --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample OutSourced");
			
			//Outsource acknowledge
			MasterControlObject.gotoPage("Outsource Acknowledge");
			Assert.assertEquals(OutsourceAcknowledge.isPatientDisplayed(sFirstName),"TRUE");
			Assert.assertEquals(OutsourceAcknowledge.isInvestigationDisplayed(sFirstName, sServiceName),"TRUE");
			OutsourceAcknowledge.selectPatient(sFirstName);
			OutsourceAcknowledge.clickSave();
			Assert.assertEquals( MasterControlObject.getAlertMsg(),"Saved Successfully","***Sample Outsource --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Result Received");
			
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
			
			//Result Validate
			MasterControlObject.changeRole("SENIOR TECHNICIAN");
			resultValidate.searchRecord(sFirstName);
			resultValidate.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Results Validate Successfully","*** Alert not shown as expected after RESULT VALIDATION ***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Result Validated");
			
			//Approve a record in Manage Approval
			MasterControlObject.changeRole("PATHOLOGIST");
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Approved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Approved");
					
			//Enter Due amount 
			MasterControlObject.changeRole("RECEPTIONIST");
			MasterControlObject.gotoPage("Due Clearance");
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
			
			//EMailing Final report
			MasterControlObject.changeRole("DISPATCH");
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickEmail();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Emailed","***Alert msg not displayed as expected after click email***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			MasterControlObject.gotoPage("Manage Dispatch");
			manageDispatchobj.selectFilter("Show Emailed Report");
			manageDispatchobj.searchRecord(sFirstName);
			Assert.assertEquals(Integer.toString(manageDispatchobj.verifyPatientName(sFirstName)),"1","Patient details not showing in EmailedReport");
			
			//Dispatching the record
			MasterControlObject.gotoPage("Patient List");
			MasterControlObject.gotoPage("Manage Dispatch");
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickDispatch();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Printed","***Alert msg not displayed as expected after click dispatch***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			MasterControlObject.gotoPage("Manage Dispatch");
			manageDispatchobj.selectFilter("Show Printed Report");
			manageDispatchobj.searchRecord(sFirstName);
			Assert.assertEquals(Integer.toString(manageDispatchobj.verifyPatientName(sFirstName)),"1","Patient details not showing in PrintedReport");
			manageDispatchobj.verifyModeOfDispatch("EMAIL,COURIER");
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
