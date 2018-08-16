package testCases2;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class Reg_GenerateBillingWithContactDetails
{
	private static WebDriver driver = null;
	private String sTestCaseName;
	private int iTestCaseRow;
	
	@BeforeTest
	public void beforeMethod() throws Exception
	{
		// Provide Log4j configuration settings
		DOMConfigurator.configure(Constant.log4jXMLpath);

		// Getting current Test Case name
		sTestCaseName = Utils.getTestCaseName(this.toString());
		
		Log.startTestCase(sTestCaseName);

		// Fetching test data's row from excel using current test case name
		ExcelUtils.openExcelFile(Constant.Path_TestData + Constant.File_TestData,"Sheet1");
		iTestCaseRow = ExcelUtils.getRowNumber(sTestCaseName,Constant.col_TestCaseName);

		//Launching Browser using test data's row and load default URL
		driver = Utils.OpenBrowser(iTestCaseRow);
	}

	@Test(priority = 1)
	public void generateBillingWithContactDetails() throws Exception
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
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
		
		String sLandline = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Landline);
		String sMaritalStatus = ExcelUtils.getCellData(iTestCaseRow, Constant.col_MaritalStatus);
		String sUIDType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UIDType);
		String sUIDNo = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UIDNo);
		String sExternalID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ExternalID);
		String sAddress = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Address);
		String sArea = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Area);
		String sCity = ExcelUtils.getCellData(iTestCaseRow, Constant.col_City);
		String sState = ExcelUtils.getCellData(iTestCaseRow, Constant.col_State);
		String sCountry = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Country);
		String sPincode = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Pincode);
				
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_PatientList patientListPageObject = PageFactory.initElements(driver,PO_PatientList.class);
				
		try 
		{
			loginPageObject.login(sUserName, sPassword);
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.enterContactDetails(sLandline, sMaritalStatus, sUIDType, sUIDNo, sExternalID, sAddress, sArea, sCity, sState, sCountry, sPincode);
			generateBillPageObject.selectServiceName(sServiceName);
			String amount = generateBillPageObject.getDueAmount();
			Log.info("Generated bill amount is : "+amount);
			generateBillPageObject.EnterCashAmount(amount);
			generateBillPageObject.ClickGenerateBill();

			//Verify results
			MasterControlObject.gotoPage("Patient List");			
			patientListPageObject.searchRecord(sFirstName);
			patientListPageObject.editPatientDetails();
			
			String landline = generateBillPageObject.getLanline();
			String marital = generateBillPageObject.getMaritalStatus();
			String UID_type = generateBillPageObject.getUIDType();
			String UID_no = generateBillPageObject.getUIDNo();
			String externalid = generateBillPageObject.getExternalID();
			String address = generateBillPageObject.getAddress();
			String area = generateBillPageObject.getArea();
			String city = generateBillPageObject.getCity();
			String state = generateBillPageObject.getState();
			String country = generateBillPageObject.getCountry();
			String pincode = generateBillPageObject.getPincode();
			
			patientListPageObject.closeEditWindow();
			patientListPageObject.selectPatientDetail();
			
			Assert.assertTrue(patientListPageObject.getStatus().equalsIgnoreCase("Registered"),"***Status not updated as expected***");
			Assert.assertTrue(landline.equalsIgnoreCase(sLandline),"***Landline not updated as expected***");
			Assert.assertTrue(marital.equalsIgnoreCase(sMaritalStatus),"***MaritalStatus not updated as expected***");
			Assert.assertTrue(UID_type.equalsIgnoreCase(sUIDType),"***UIDType not updated as expected***");
			Assert.assertTrue(UID_no.equalsIgnoreCase(sUIDNo),"***UIDNo not updated as expected***");
			Assert.assertTrue(externalid.equalsIgnoreCase(sExternalID),"***ExternalID not updated as expected***");
			Assert.assertTrue(address.equalsIgnoreCase(sAddress),"***Address not updated as expected***");
			Assert.assertTrue(area.equalsIgnoreCase(sArea),"***Area not updated as expected***");
			Assert.assertTrue(city.equalsIgnoreCase(sCity),"***City not updated as expected***");
			Assert.assertTrue(state.equalsIgnoreCase(sState),"***State not updated as expected***");
			Assert.assertTrue(country.equalsIgnoreCase(sCountry),"***Country not updated as expected***");
			Assert.assertTrue(pincode.equalsIgnoreCase(sPincode),"***Pincode not updated as expected***");
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
			Log.info("Bill generated with contact details ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Bill not generated with contact details ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Log.endTestCase(sTestCaseName);
		driver.close();
    }
}
