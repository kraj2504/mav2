package Smoke;

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

public class Auto_Smoke_Part1
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

		String sURL = ExcelUtils.getCellData(iTestCaseRow, Constant.col_URL);
		driver = Utils.OpenBrowser(iTestCaseRow, sURL);
	}

	@Test(priority = 1)
	public void auto_Smoke_Part1() throws Exception
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
			String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			String sBillDiscountType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountType);
			String sBillDiscountTypeAs=ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountTypeAs);
			String sEnterBillDiscount=ExcelUtils.getCellData(iTestCaseRow, Constant.col_DiscountValue);
			String sApprovedBy= ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
			String sRemarks= ExcelUtils.getCellData(iTestCaseRow, Constant.col_Remarks);
//			String sPrinterName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_PrinterName);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle(),"Login failed");
//			MasterControlObject.selectPrinter(sPrinterName);
			
			//Register patient and generate bill
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.EnterMailID(sMailID);
			generateBillPageObject.selectServiceName(sServiceName);
			
			//Verify Gross, Net and Due --> No discount, No collected amount
			String TotalAmountOfAllServices = generateBillPageObject.getTotalAmountOfAllServices();
			String actualgross = generateBillPageObject.getGrossAmount();
			Assert.assertTrue(actualgross.startsWith(TotalAmountOfAllServices), "Gross amount showing incorrectly");
			generateBillPageObject.verifyNetAmount();
			generateBillPageObject.verifyDueAmount();
			
			//Verify Collected and Due --> No discount, After collected amount
			String amount = generateBillPageObject.getDueAmount();
			float iamount = Float.parseFloat(amount);
			Log.info("Generated bill amount is : "+amount);
			generateBillPageObject.EnterCashAmount((Float.toString((iamount-iamount/2)+1)));
			String cashamount = generateBillPageObject.getCashAmount();
			String actualcollected = generateBillPageObject.getCollectedAmount();
			Assert.assertTrue(actualcollected.startsWith(cashamount), "Collected amount showing incorrectly");
			generateBillPageObject.verifyDueAmount();
	
			//Verify Discount, Due and collected --> Item level discount, Before and After collecting amount
			generateBillPageObject.ClearEnteredCashAmount();
			generateBillPageObject.EnterItemDiscount(1,"Percentage");
			String Itemprice = generateBillPageObject.GetItemPrice(1);
			String expectedDiscount = Float.toString((Float.parseFloat(Itemprice)*10/100));
			Assert.assertTrue(generateBillPageObject.getDiscountAmount().startsWith(expectedDiscount),"Discount amount showing incorrectly");
			generateBillPageObject.verifyDueAmount();
			String amount1 = generateBillPageObject.getDueAmount();
			float iamount1 = Float.parseFloat(amount1);
			Log.info("Generated bill amount is : "+amount1);
			generateBillPageObject.EnterCashAmount((Float.toString((iamount-iamount/2)+1)));
			String cashamount1 = generateBillPageObject.getCashAmount();
			String actualcollected1 = generateBillPageObject.getCollectedAmount();
			Assert.assertTrue(actualcollected1.startsWith(cashamount1), "Collected amount showing incorrectly");
			generateBillPageObject.verifyDueAmount();
			
			//Verify Discount, Net, and Due --> Both Bill and Item level discount, After collecting amount
			generateBillPageObject.selectBillDiscountType(sBillDiscountType);
			System.out.println("sBillDiscountTypeAs:" + sBillDiscountTypeAs);
			generateBillPageObject.selectBillDiscountTypeAs("Percentage");
			generateBillPageObject.enterBillDiscount(sEnterBillDiscount);
			generateBillPageObject.selectApprovedBy(sApprovedBy);
			generateBillPageObject.enterRemarks(sRemarks);
			String expectedDiscount2 = Float.toString((Float.parseFloat(Itemprice)*(Float.parseFloat(sEnterBillDiscount)/100))+Float.parseFloat(expectedDiscount));
			Assert.assertTrue(generateBillPageObject.getDiscountAmount().startsWith(expectedDiscount2),"Discount amount showing incorrectly");
			generateBillPageObject.verifyNetAmount();
			generateBillPageObject.verifyDueAmount();
			
			String gross = generateBillPageObject.getGrossAmount();
			String discount = generateBillPageObject.getDiscountAmount();
			String net = generateBillPageObject.getNetAmount();
			String collected = generateBillPageObject.getCollectedAmount();
			String due = generateBillPageObject.getDueAmount();
			
			generateBillPageObject.ClickGenerateBill();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Saved successfully","***Registration --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			Assert.assertEquals(patientListPageObjects.getPatientBillGrossAmount(),gross,"Patient List --> Gross amount showing incorrectly");
			Assert.assertEquals(patientListPageObjects.getPatientBillDiscountAmount(),discount,"Patient List --> Discount amount showing incorrectly");
			Assert.assertEquals(patientListPageObjects.getPatientBillNetAmount(),net,"Patient List --> Net amount showing incorrectly");
			Assert.assertEquals(patientListPageObjects.getPatientBillCollected(),collected,"Patient List --> Collected amount showing incorrectly");
			Assert.assertEquals(patientListPageObjects.getPatientBillDue(),due,"Patient List --> Due amount showing incorrectly");
			
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
			patientListPageObjects.verifyingStatusBarCode("Sample Collected");
			List<String> barcodes = patientListPageObjects.getAllBarCode();
			
			//Acknowledge sample
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage(Constant.PAGE_SAMPLE_ACKNOWLEDGEMENT);
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
			MasterControlObject.changeRole(Constant.AUTHORIZATION);
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Approved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Approved");
			
			//EMailing Final report - Verify due alert
			MasterControlObject.changeRole("DISPATCH");
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "The patient has dues","***Alert msg not displayed as expected after selecting the patient***");
			MasterControlObject.acceptAlert();
		
			//Dispatching the record - Verify due alert
			manageDispatchobj.clickRefresh();
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "The patient has dues","***Alert msg not displayed as expected after selecting the patient***");
			MasterControlObject.acceptAlert();
					
			//Enter Due amount
			MasterControlObject.changeRole(Constant.RECEPTION);
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
			MasterControlObject.changeRole(Constant.DISPATCH);
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickEmail();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Emailed","***Alert msg not displayed as expected after click email***");
			MasterControlObject.acceptAlert();
			manageDispatchobj.clickRefresh();
			manageDispatchobj.selectFilter("Show Emailed Report");
			manageDispatchobj.searchRecord(sFirstName);
			Assert.assertEquals(Integer.toString(manageDispatchobj.verifyPatientName(sFirstName)),"1","Patient details not showing in EmailedReport");
			
			//Dispatching the record
			manageDispatchobj.clickRefresh();
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			manageDispatchobj.clickDispatch();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "Dispatch List Printed","***Alert msg not displayed as expected after click dispatch***");
			MasterControlObject.acceptAlert();
			manageDispatchobj.clickRefresh();
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
		driver.quit();
    }
}