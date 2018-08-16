package tc_BasicDispatch_SelectPrinter_SampleAcknowledgementByTechnician_ResultValidate;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.Robot;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_BulkEntry;
import pageObjects.PO_DueClearance;
import pageObjects.PO_Registration;
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

public class S57_RegisterPatientWithMandatoryFieldsinHomeCollectionAndTimingNotAccept
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
	public void s57_RegisterPatientWithMandatoryFieldsinHomeCollectionAndTimingNotAccept() throws Exception
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
			PO_BulkEntry bulkEntryObj = PageFactory.initElements(driver,PO_BulkEntry.class);
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
			String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			String sLandline = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Landline);
			String sAddress = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Address);
			String sArea = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Area);
			String sPincode = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Pincode);
			String sPhlebotomist = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Phlebotomist);
			String sCollectionOption = ExcelUtils.getCellData(iTestCaseRow, Constant.col_CollectionOption);
			String sReason = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Reason);
			
			//User Login
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle(),"Login failed");
			MasterControlObject.CancelPrinterSetting();
	
			//Register patient in Home collection and generate bill
			MasterControlObject.gotoPage("Home Collection Booking");

			homeCollectionBooking.selectTitle(sTitle);
			homeCollectionBooking.EnterPatientName(sFirstName);
			homeCollectionBooking.selectGender(sGender);
			homeCollectionBooking.enterAge(sAge);
			homeCollectionBooking.enterContactDetails(sLandline, sAddress, sArea, sPincode);
			homeCollectionBooking.selectInvestigation(sServiceName);
			homeCollectionBooking.selectAppointmentDate();
			homeCollectionBooking.EnterAppointmentTime("1145");
			homeCollectionBooking.clickSave();
			String msg = MasterControlObject.getAlertMsg();
			Assert.assertEquals(msg,"Save Successfully","***Registering Patient in Home Collection --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			
			MasterControlObject.gotoPage("Home Collection Assigning");
			homeCollection.searchRecord(sFirstName);
			homeCollection.selectPatient();
			homeCollection.selectPhlebotomist(sPhlebotomist);
			homeCollection.selectVisitDate();
			System.out.println("1");
			homeCollection.selectCollectionOption(sCollectionOption);
			System.out.println("2");
			homeCollection.EnterReason(sReason);
			System.out.println("3");
			homeCollection.clickSave();
			String msg1 = MasterControlObject.getAlertMsg();
			Assert.assertEquals("Saved Successfully", msg1,"***Home Collection --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			
			//Register patient and generate bill
			MasterControlObject.gotoPage("Registration");
			generateBillPageObject.changeSearchTypeToBooking();
			generateBillPageObject.verifyPaientInSearch(sFirstName);
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