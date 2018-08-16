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
import pageObjects.PO_BulkEntryROAR;
import pageObjects.PO_DueClearance;
import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_ManageApproval;
import pageObjects.PO_ManageDispatch;
import pageObjects.PO_ManageSample;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import pageObjects.PO_ROARApproval;
import pageObjects.PO_ResultValidate;
import pageObjects.PO_SampleScannerAck;
import pageObjects.PO_WorkList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class NewInstance_BulkEntryROAR_PreliminaryAndFinal_UptoDispatch_Growth
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
		ExcelUtils.openExcelFile(Constant.Path_TestData + Constant.File_TestData_NewInstance,"Sheet1");
		iTestCaseRow = ExcelUtils.getRowNumber(sTestCaseName,Constant.col_TestCaseName);

		String sURL = ExcelUtils.getCellData(iTestCaseRow, Constant.col_URL);
		driver = Utils.OpenBrowser(iTestCaseRow, sURL);
	}

	@Test(priority = 1)
	public void newInstance_BulkEntryROAR_PreliminaryAndFinal_UptoDispatch_Growth() throws Exception
	{

		try 
		{
			// Instantiate PageObjects class
			PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
			PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
			PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);
			PO_ManageSample manageSamplePageObjects = PageFactory.initElements(driver,PO_ManageSample.class);
			PO_SampleScannerAck sampleScannerAck = PageFactory.initElements(driver,PO_SampleScannerAck.class);
			PO_BulkEntryROAR bulkEntryROAR = PageFactory.initElements(driver,PO_BulkEntryROAR.class);
			PO_ROARApproval ROARApproval = PageFactory.initElements(driver,PO_ROARApproval.class);
			PO_ResultValidate resultValidate = PageFactory.initElements(driver,PO_ResultValidate.class);
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
			String sSampleType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_SampleType);
			String sPrinterName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_PrinterName);
			
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
			generateBillPageObject.selectServiceName(sServiceName);
			generateBillPageObject.ClickGenerateBill();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Saved successfully","***Registration --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
			
			//Collecting sample
			MasterControlObject.changeRole(Constant.PHLEBOTOMY);
			manageSamplePageObjects.clickPatientDetailsTable();
			manageSamplePageObjects.searchRecord(sFirstName);
			manageSamplePageObjects.selectPatient();
			manageSamplePageObjects.clickCollectSample();
			String msg = MasterControlObject.getAlertMsg();
			Assert.assertEquals("Saved Successfully", msg,"***Collecting sample --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample Collected");

			//Enter Bulk Entry ROAR
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage("BulkEntry ROAR");
			bulkEntryROAR.searchRecord(sFirstName);
			bulkEntryROAR.verifyAnalyte(sServiceName);
			bulkEntryROAR.verifySampleType(sSampleType);
			bulkEntryROAR.selectCultureReport("Preliminary");
			bulkEntryROAR.selectResultType("Growth");
			bulkEntryROAR.enterColonyCount("130");
			bulkEntryROAR.selectIsolatedOrganism1("Acinetobacter species");
			bulkEntryROAR.selectResultEntered();
			bulkEntryROAR.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Result Enter Successfully","***Alert not displayed as expected after entering results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Preliminary Result Entered");
			
			//Approve a record in Manage Approval
			MasterControlObject.changeRole(Constant.AUTHORIZATION);
			MasterControlObject.gotoPage("ROAR Approval");
			ROARApproval.searchRecord(sFirstName);
			ROARApproval.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Result Approve Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Preliminary Approved");
			
			//Enter Final Bulk Entry ROAR
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage("BulkEntry ROAR");
			bulkEntryROAR.searchRecord(sFirstName);
			bulkEntryROAR.verifyAnalyte(sServiceName);
			bulkEntryROAR.verifySampleType(sSampleType);
			bulkEntryROAR.selectCultureReport("Final");
			bulkEntryROAR.selectResultType("Growth");
			bulkEntryROAR.enterColonyCount("140");
			bulkEntryROAR.selectIsolatedOrganism1("Acinetobacter species");
			bulkEntryROAR.selectAntibioticForOrganism1("Resistant");
			bulkEntryROAR.selectResultEntered();
			bulkEntryROAR.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Result Enter Successfully","***Alert not displayed as expected after entering results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Final Result Entered");
			
			//Approve a record in Manage Approval
			MasterControlObject.changeRole(Constant.AUTHORIZATION);
			MasterControlObject.gotoPage("ROAR Approval");
			ROARApproval.searchRecord(sFirstName);
			ROARApproval.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Result Approve Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Final Approved");
					
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
			Log.info("Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		Log.endTestCase(sTestCaseName);
//		driver.quit();
    }
}