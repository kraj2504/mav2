package Smoke;
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
import pageObjects.PO_InvoiceGeneration;
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

public class NewInstance_InvoiceGeneration
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
     // This scenario is for Canceling any 2 of 4 services, with both bill in percentage and item level discount in amount and patient not paid the bill. 
	@Test(priority = 1)
	public void newInstance_Canceling2of4ServicesWithBothBillAndItemLevelDiscountInPercentageAndPatientNotPaidTheBill() throws Exception
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
			PO_InvoiceGeneration invoicegeneration = PageFactory.initElements(driver,PO_InvoiceGeneration.class);
			
			// Fetching Data from excel file
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
			String sFirstName = Utils.getRandomName(45);
			String sSecondName = Utils.getRandomName(45);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
			String sMobileNo = Utils.getRandomNumber(13);
			String sMobileNo2 = Utils.getRandomNumber(13);
			String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
			String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			String sServiceName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName2);
			String sPrinterName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_PrinterName);
			String sReferralType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
			String sReferralName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralName);
			String sDoctorName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DoctorName);
			String sRider = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Rider);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle(),"Login failed");
			MasterControlObject.selectPrinter(sPrinterName);
			
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
			generateBillPageObject.selectRider(sRider);
			generateBillPageObject.selectServiceName(sServiceName);
			
			String actualgross = generateBillPageObject.getGrossAmount();
			String actualdiscount = generateBillPageObject.getDiscountAmount();
			String actualnet = generateBillPageObject.getNetAmount();
			String actualcollected = generateBillPageObject.getCollectedAmount();
			String actualdue = generateBillPageObject.getDueAmount();
			
			System.out.println(actualgross+","+actualdiscount+","+actualnet+","+actualcollected+","+actualdue);
			
			generateBillPageObject.ClickGenerateBill();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Saved Successfully","***Registration --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
/*			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Patient not registered***");
*/			
			//Register second patient and generate bill
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sSecondName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo2);
			generateBillPageObject.EnterMailID(sMailID);
			generateBillPageObject.selectReferralType(sReferralType);
			generateBillPageObject.selectReferralName(sReferralName);
			generateBillPageObject.selectRider(sRider);
			generateBillPageObject.selectServiceName(sServiceName2);
			
			String actualgross2 = generateBillPageObject.getGrossAmount();
			String actualdiscount2 = generateBillPageObject.getDiscountAmount();
			String actualnet2 = generateBillPageObject.getNetAmount();
			String actualcollected2 = generateBillPageObject.getCollectedAmount();
			String actualdue2 = generateBillPageObject.getDueAmount();
			
			System.out.println(actualgross+","+actualdiscount+","+actualnet+","+actualcollected+","+actualdue);
			
			generateBillPageObject.ClickGenerateBill();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Saved Successfully","***Registration --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			
			//Invoice Generation 
			MasterControlObject.changeRole(Constant.ACCOUNTS);
			MasterControlObject.gotoPage("Invoice Generation");
			invoicegeneration.selectClientName(sReferralName);
			
			Assert.assertEquals(actualgross,invoicegeneration.getBillAmount(sFirstName));
			Assert.assertEquals(actualdiscount,invoicegeneration.getDiscountAmount(sFirstName));
			Assert.assertEquals(actualnet,invoicegeneration.getNetAmount(sFirstName));
			Assert.assertEquals(actualcollected,invoicegeneration.getCollectedAmount(sFirstName));
			Assert.assertEquals(actualdue,invoicegeneration.getDueAmount(sFirstName));
			
			Assert.assertEquals(actualgross2,invoicegeneration.getBillAmount(sSecondName));
			Assert.assertEquals(actualdiscount2,invoicegeneration.getDiscountAmount(sSecondName));
			Assert.assertEquals(actualnet2,invoicegeneration.getNetAmount(sSecondName));
			Assert.assertEquals(actualcollected2,invoicegeneration.getCollectedAmount(sSecondName));
			Assert.assertEquals(actualdue2,invoicegeneration.getDueAmount(sSecondName));
			
			invoicegeneration.verifyTotalBillAmount();
			invoicegeneration.verifyTotalNetAmount();
			invoicegeneration.verifyTotalCollectedAmount();
			invoicegeneration.verifyTotalDueAmount();
			
/*			
			invoicegeneration.clickSave();
			Assert.assertEquals("Saved Successfully",MasterControlObject.getAlertMsg());
			MasterControlObject.acceptAlert();
			Utils.keyEsc();
*/		}
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
