package tc_BasicDispatch_SelectPrinter_SampleAcknowledgementByTechnician_ResultValidate;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.PO_Registration;
import pageObjects.PO_Registration;
import pageObjects.PO_Login;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class VerifyingTransactionBillDiscount
{
	private static WebDriver driver = null;
	private String sTestCaseName;
	private int iTestCaseRow;
	
	@BeforeTest
	public void beforeMethod() throws Exception
	{
		// Provide Log4j configuration settings
		DOMConfigurator.configure("D://eclipse-workspace//LIS//src//log4j.xml");

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
	public void generateBillingWithNoDues() throws Exception
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
		String sReferralType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
		String sServiceName1 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
		String sServiceName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName2);
		String sServiceName3 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName3);
				
		//String sServiceName2 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName2);
		//String sServiceName3 = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName3);
		String ItemDiscount= ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscount);
		String ItemDiscountType= ExcelUtils.getCellData(iTestCaseRow, Constant.col_ItemDiscountType);
		String sApprovedBy = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
		String sRemarks = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Remarks);		
		String sBillDiscountType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountType);
		String sBillDiscountTypeAs = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountTypeAs);
		String sEnterBillDiscount = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DiscountValue);
		
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
		//PO_PatientList patientListPageObject = PageFactory.initElements(driver,PO_PatientList.class);
		PO_GenerateBill2 generateBillPageObject1 = PageFactory.initElements(driver,PO_GenerateBill2.class);	
		
		try 
		{
			loginPageObject.login(sUserName, sPassword);
		
			generateBillPageObject1.selectTitle(sTitle);
			generateBillPageObject1.EnterFirstName(sFirstName);
			generateBillPageObject1.selectGender(sGender);
			generateBillPageObject1.enterAge(sAge);
			generateBillPageObject1.selectAgeType(sAgeType);
			generateBillPageObject1.EnterMobileNumber(sMobileNo);
			generateBillPageObject1.selectReferralType(sReferralType);
			generateBillPageObject1.EnterServiceName(1, sServiceName1);
			generateBillPageObject1.EnterServiceName(2, sServiceName2);
			generateBillPageObject1.EnterServiceName(3, sServiceName3);
			Thread.sleep(2000);
			generateBillPageObject1.selectBillDiscountType(sBillDiscountType);
			generateBillPageObject1.selectBillDiscountTypeAs(sBillDiscountTypeAs);
			generateBillPageObject1.enterBillDiscount(sEnterBillDiscount);			
			generateBillPageObject1.selectApprovedBy(sApprovedBy);
			generateBillPageObject1.enterRemarks(sRemarks);
		
			float amt = generateBillPageObject1.calculateAmount();
		    float gross=generateBillPageObject1.getGrossAmount();	
			Assert.assertEquals(gross,amt,"*** Gross Amount not updated as expected***");
			
			String net=	generateBillPageObject1.calculateNetAmountForBillDiscount(sEnterBillDiscount);
			String netamt =generateBillPageObject1.getNetAmount();	
			Assert.assertEquals(net,netamt,"***Net Amount not updated as expected***");
			
			String amount = generateBillPageObject1.getDueAmount();
			float iamount = Float.parseFloat(amount);
			Log.info("Generated bill amount is : "+amount);
			generateBillPageObject1.EnterCashAmount(Float.toString(iamount-iamount/2));
			generateBillPageObject1.EnterCashAmount(amount);
			String cashamount = generateBillPageObject1.getCashAmount();
			generateBillPageObject1.getCollectedAmount();
			Assert.assertEquals(cashamount,generateBillPageObject1.getCollectedAmount(),"***Collected Amount not updated as expected***");
			
			
			String finaldue = generateBillPageObject1.verifyDueAmount();
			String du = generateBillPageObject1.getDueAmount();
			Assert.assertEquals(finaldue,du,"***Due Amount not updated as expected***");
			
			Thread.sleep(2000);
			String dis = generateBillPageObject1.calBillDiscountInAmount();
			String Discount=generateBillPageObject1.getDiscountAmount();
			Assert.assertEquals(dis,Discount,"***Discount Amount not updated as expected***");
			Assert.assertEquals(generateBillPageObject1.getRoundOff(),"0.00","***RoundOFf not updated as expected***");	
		/*
			String calRoundOff=generateBillPageObject1.getRoundOff();
			double roundOff = generateBillPageObject1.calculateRoundOff();
			Assert.assertEquals(calRoundOff,roundOff,"***RoundOFf Amount not updated as expected***");
			*/
			generateBillPageObject1.ClickGenerateBill();
			Utils.keyEsc();
		
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
			Log.info("Bill generated with no dues ==> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info("Bill not generated with no dues ==> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Log.endTestCase(sTestCaseName);
		//driver.close();
    }
}
