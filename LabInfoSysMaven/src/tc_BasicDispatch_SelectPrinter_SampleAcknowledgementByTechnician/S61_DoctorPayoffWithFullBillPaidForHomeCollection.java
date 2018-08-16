//Full bill paid in registration. Due clearance not used.
package tc_BasicDispatch_SelectPrinter_SampleAcknowledgementByTechnician;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_BulkEntry;
import pageObjects.PO_DoctorPayoffReport;
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

public class S61_DoctorPayoffWithFullBillPaidForHomeCollection
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
	public void s61_DoctorPayoffWithFullBillPaidForHomeCollection() throws Exception
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
			PO_ManageDispatch manageDispatchobj = PageFactory.initElements(driver,PO_ManageDispatch.class);
			PO_DoctorPayoffReport doctorPayoffReport = PageFactory.initElements(driver,PO_DoctorPayoffReport.class);
			PO_HomeCollectionBooking homeCollectionBooking = PageFactory.initElements(driver,PO_HomeCollectionBooking.class);
			PO_HomeCollectionAssigning homeCollection = PageFactory.initElements(driver,PO_HomeCollectionAssigning.class);
			
			// Fetching Data from excel file
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password);
			String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
			String sFirstName = Utils.getRandomName(45);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sDoctorName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DoctorName);
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
			
			//Register patient in Home collection and generate bill
			MasterControlObject.gotoPage("Home Collection Booking");
			homeCollectionBooking.selectTitle(sTitle);
			homeCollectionBooking.EnterPatientName(sFirstName);
			homeCollectionBooking.selectGender(sGender);
			homeCollectionBooking.enterAge(sAge);
			homeCollectionBooking.enterContactDetails(sLandline, sAddress, sArea, sPincode);
			homeCollectionBooking.selectDoctorName(sDoctorName);
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
			Assert.assertEquals("Saved Successfully", msg1,"***Collecting sample --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			
			//Register patient and generate bill
			MasterControlObject.gotoPage("Registration");
			generateBillPageObject.changeSearchTypeToBooking();
			generateBillPageObject.searchRecord(sFirstName);
			String amount = generateBillPageObject.getDueAmount();
			Log.info("Generated bill amount is : "+amount);
			generateBillPageObject.EnterCashAmount(amount);
			String cashamount = generateBillPageObject.getCashAmount();
			generateBillPageObject.ClickGenerateBill();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Sample Collected"),"***Patient not registered***");
			Assert.assertEquals(cashamount,amount,"***Amount not updated as expected***");
			Assert.assertEquals(patientListPageObjects.getPatientBillDue(),"0.00","***BillDue not updated as expected***");

			//Logout
			MasterControlObject.ClickSignOut();
			
			//User Login
			loginPageObject.login(Constant.Admin, sPassword);
			Assert.assertEquals("Analyte Master", driver.getTitle(),"Login failed");
			
			//Verify Doctor payoff Report
			MasterControlObject.gotoPage("Doctor PayOff Report");
			doctorPayoffReport.searchDoctor(sDoctorName);
			doctorPayoffReport.verifyPayOffAmount("10");
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
//		driver.close();
    }
}