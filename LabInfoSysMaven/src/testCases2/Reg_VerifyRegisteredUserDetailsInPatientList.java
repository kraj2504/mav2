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

public class Reg_VerifyRegisteredUserDetailsInPatientList
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
	public void verifyRegisteredUserDetailsInPatientList() throws Exception
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
		//String sRefferedType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
		String sRefferedType = "SELF";

		// Instantiate PageObjects class
		PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
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
			generateBillPageObject.selectServiceName(sServiceName);
			
			String actualdueamount = generateBillPageObject.getDueAmount();
			float factualdueamount = Float.parseFloat(actualdueamount);
			Log.info("Generated bill amount is : "+actualdueamount);
			
			actualdueamount = Utils.roundUsingDecimalFormat(factualdueamount-factualdueamount/2);
/*			
			String actualdueamount = generateBillPageObject.getDueAmount();
			Log.info("Generated bill amount is : "+actualdueamount);
*/			generateBillPageObject.EnterCashAmount(actualdueamount);
			String paiddueamount = generateBillPageObject.getCashAmount();
			if(paiddueamount.isEmpty())
				paiddueamount = "0.00";
			generateBillPageObject.ClickGenerateBill();
			
			MasterControlObject.gotoPage("Patient List");
			
			patientListPageObject.searchRecord(sFirstName);
			
			String rPatientBillRefferedBy = patientListPageObject.getPatientBillRefferedBy();
			String due = patientListPageObject.getPatientBillDue();
			String rPatientBillTotal = patientListPageObject.getPatientBillNetAmount();			
			String rPatientBillCollected = patientListPageObject.getPatientBillCollected();
			
			String rServiceName = patientListPageObject.getServiceName(); 
			
			patientListPageObject.editPatientDetails();
			
			String rTitle = generateBillPageObject.getTitle(); 
			String rFirstName = generateBillPageObject.getFirstName();
			String rGender = generateBillPageObject.getGender();
			String rAge = generateBillPageObject.getAge();
			String rAgeType = generateBillPageObject.getAgeType();
			String rMobileNo = generateBillPageObject.getMobileNumber();
			
			patientListPageObject.closeEditWindow();
			
			Assert.assertEquals(sTitle,rTitle,"***Title not updated as expected***");
			Assert.assertTrue(sFirstName.equalsIgnoreCase(rFirstName),"***FirstName not updated as expected***");
			Assert.assertEquals(sGender,rGender,"***Gender not updated as expected***");
			Assert.assertEquals(sAge,rAge,"***Age not updated as expected***");
			Assert.assertEquals(sAgeType,rAgeType,"***AgeType not updated as expected***");
			Assert.assertEquals(sMobileNo,rMobileNo,"***MobileNo not updated as expected***");
			Assert.assertEquals(actualdueamount,rPatientBillTotal,"***PatientBill Net not updated as expected***");
			Assert.assertEquals(paiddueamount,rPatientBillCollected,"***PatientBillCollected not updated as expected***");
			Assert.assertEquals(sRefferedType,rPatientBillRefferedBy,"***PatientBillRefferedBy not updated as expected***");
			Assert.assertTrue(sServiceName.equalsIgnoreCase(rServiceName),"***ServiceName not updated as expected***");
		}

		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
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
			Log.info("Patient Details inserted in corresponding fields ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Patient Details mismatching ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Log.endTestCase(sTestCaseName);
//		driver.close();
    }
}
