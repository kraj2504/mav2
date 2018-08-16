package tc_BasicDispatch_NoSampleCollection;

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
import pageObjects.PO_DueClearance;
import pageObjects.PO_Registration;
import pageObjects.PO_ResultValidate;
import pageObjects.PO_SampleScannerAck;
import pageObjects.PO_HomeCollectionAssigning;
import pageObjects.PO_HomeCollectionBooking;
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

public class S56_RegisterPatientWithMandatoryFieldsReferralType_SelfinHomeCollection
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
	public void s56_RegisterPatientWithMandatoryFieldsReferralType_SelfinHomeCollection() throws Exception
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
			PO_HomeCollectionBooking homeCollectionBooking = PageFactory.initElements(driver,PO_HomeCollectionBooking.class);
			PO_HomeCollectionAssigning homeCollection = PageFactory.initElements(driver,PO_HomeCollectionAssigning.class);
			
			// Fetching Data from excel file
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
			String sFirstName = Utils.getRandomName(45);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
			String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			String sLandline = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Landline);
			String sAddress = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Address);
			String sArea = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Area);
			String sPincode = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Pincode);
			String sPhlebotomist = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Phlebotomist);
			String sCollectionOption = ExcelUtils.getCellData(iTestCaseRow, Constant.col_CollectionOption);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle(),"Login failed");
			MasterControlObject.selectPrinter();
			
			//Register patient in Home collection and generate bill
			MasterControlObject.gotoPage("Home Collection Booking");
			homeCollectionBooking.selectTitle(sTitle);
			homeCollectionBooking.EnterPatientName(sFirstName);
			homeCollectionBooking.selectGender(sGender);
			homeCollectionBooking.enterAge(sAge);
			homeCollectionBooking.enterContactDetails(sLandline, sAddress, sArea, sPincode);
			homeCollectionBooking.selectAppointmentDate();
			homeCollectionBooking.EnterAppointmentTime("1145");
			homeCollectionBooking.selectInvestigation(sServiceName);
			homeCollectionBooking.clickSave();
			String msg = MasterControlObject.getAlertMsg();
			Assert.assertEquals(msg,"Save Successfully","***Registering Patient in Home Collection --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			
			MasterControlObject.gotoPage("Home Collection Assigning");
			homeCollection.searchRecord(sFirstName);
			homeCollection.selectPatient();
			homeCollection.selectPhlebotomist(sPhlebotomist);
			homeCollection.selectVisitDate();
			homeCollection.selectCollectionOption(sCollectionOption);
			homeCollection.clickSave();
			String msg1 = MasterControlObject.getAlertMsg();
			Assert.assertEquals("Saved Successfully", msg1,"***Home Collection --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			
			//Register patient and generate bill
			MasterControlObject.gotoPage("Registration");
			generateBillPageObject.changeSearchTypeToBooking();
			generateBillPageObject.searchRecord(sFirstName);
			generateBillPageObject.EnterMailID(sMailID);
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertEquals(patientListPageObjects.getStatus(),"Sample Collected","***Patient not registered***");
			List<String> barcodes = patientListPageObjects.getAllBarCode();
			
			//Acknowledge sample
			MasterControlObject.changeRole("TECHNICIAN");
			MasterControlObject.gotoPage("Sample Scanner Ack");
			sampleScannerAck.AcknowledgeAllSamples(barcodes);
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample Received");

			//Verify Patient name in worklist
			MasterControlObject.gotoPage("Work List");
			int i = workOrderObj.verifyPatientName(sFirstName);
			workOrderObj.ClickGenerateWorkList();
			Utils.keyCtrlW();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Worklist Created Sucessfully","***Worklist not Created***");
			MasterControlObject.acceptAlert();
			Assert.assertEquals(Integer.toString(i),"0","***Worklist --> Worklist not created***");
			
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
			MasterControlObject.changeRole("DISPATCH");
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