package Smoke;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
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

public class Trident_Smoke_Part1
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
		ExcelUtils.openExcelFile(Constant.Path_TestData + Constant.File_TestData_Trident,"Sheet1");
		iTestCaseRow = ExcelUtils.getRowNumber(sTestCaseName,Constant.col_TestCaseName);

		String sURL = ExcelUtils.getCellData(iTestCaseRow, Constant.col_URL);
		driver = Utils.OpenBrowser(iTestCaseRow, sURL);
	}
	
	//Verify successful login
	@Test(priority = 1) 
	public void successfulLogin() throws Exception
	{	
		try
		{
			Log.info(" ");
			Log.info("*** Verifying successful login ***");
			String sUserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_UserName);
			String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Password); 
			String sPrinterName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_PrinterName);
			
			PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
			PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			 
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle());
			MasterControlObject.selectPrinter(sPrinterName);
		}
		
		catch(Exception e)
		{
			  ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			  Utils.takeScreenshot(driver, sTestCaseName);
			  Log.error(e.getMessage());
			  throw (e);
		}
	}
/*	
	//Verifying Doctor Name Disabled When Referral Type Self
	@Test(priority = 2)
	public void isDoctorNameDisabledWhenReferralTypeSelf() throws Exception
	{
		Log.info("*** Verifying Doctor Name Disabled When Referral Type Self ***");
				
		// Instantiate PageObjects class
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
				
		try
		{
			Assert.assertFalse(generateBillPageObject.doctorNameEnableStatus(),"***Doctor name not disabled***");
		}
		
		catch(Exception e)
		{
			  ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			  Utils.takeScreenshot(driver, sTestCaseName);
			  Log.error(e.getMessage());
			  throw (e);
		}
	}
*/	
	//Verifying Doctor Name Enabled and able to select When Referral Type Client
	@Test(priority = 3)
	public void isDoctorNameEabledAndAbleToSelectWhenReferralTypeClient() throws Exception
	{
//		String sReferralType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
		String sReferralType = "CLIENT";
		String sDoctorName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_DoctorName);
		
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		try
		{
			Log.info("*** Verifying Doctor Name Enabled and able to select When Referral Type Client ***");
			generateBillPageObject.selectReferralType(sReferralType);
			Assert.assertTrue(generateBillPageObject.doctorNameEnableStatus(),"***Doctor name not enabled***");
			
			generateBillPageObject.selectDoctorName(sDoctorName);
			Assert.assertTrue(generateBillPageObject.getDoctorName().trim().equals(sDoctorName),"***Actual and expected doctor names not equal***");
		}
		
		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
			Log.error(e.getMessage());
			throw (e);
		}
	}
	
	//Verify mandatory fields
	@Test(priority = 4)
	public void mandatoryFieldsVerificationInRegistration() throws Exception
	{
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(25);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(10);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
		String sBillDiscountType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountType);
		String sBillDiscountTypeAs = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountTypeAs);
		String sApprovedBy = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
		
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
				
		try 
		{
			Log.info("*** Verify mandatory Option For ApprovedBy and Remarks After Select DiscountType ***");
			generateBillPageObject.clickRefresh();
			 
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.ClickGenerateBill();
			String msg4 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg4,"Please Enter First Name","***Alert not displayed as expected***");
			
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.ClickGenerateBill();
			String msg5 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg5,"Please Select Gender","***Alert not displayed as expected***");
			
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.ClickGenerateBill();
			String msg6 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg6,"Please Enter Age","***Alert not displayed as expected***");
			
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.ClickGenerateBill();
			String msg7 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg7,"Please Enter Mobile No","***Alert not displayed as expected***");
			
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.ClickGenerateBill();
			String msg8 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg8,"Please Enter Order Details","***Alert not displayed as expected***");
			
			generateBillPageObject.selectServiceName(sServiceName);
			
			generateBillPageObject.selectBillDiscountType(sBillDiscountType);
			generateBillPageObject.ClickGenerateBill();
			String msg1 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg1,"Please Enter Amount Or %","***Alert not displayed as expected***");
			
			generateBillPageObject.selectBillDiscountTypeAs(sBillDiscountTypeAs);
			generateBillPageObject.ClickGenerateBill();
			String msg2 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg2,"Please Select Approved By","***Alert not displayed as expected***");
			
			generateBillPageObject.selectApprovedBy(sApprovedBy);
			generateBillPageObject.ClickGenerateBill();
			String msg3 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg3,"Please Enter Remarks","***Alert not displayed as expected***");
		}	
		
		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
			Log.error(e.getMessage());
			throw (e);
		}
	}
	
	//Verifying Registration With Contact Details And Verify Patient Details in Patient List
	@Test(priority = 5)
	public void generateBillingWithContactDetailsAndVerifyUserDetails() throws Exception
	{	
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(25);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(10);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
		String sRefferedType = "SELF";
		
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
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_PatientList patientListPageObject = PageFactory.initElements(driver,PO_PatientList.class);
				
		try 
		{
			Log.info("*** Verifying Registration With Contact Details And Verify Patient Details in Patient List ***");
			MasterControlObject.gotoPage("Registration");
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.enterContactDetails(sLandline, sMaritalStatus, sUIDType, sUIDNo, sExternalID, sAddress, sArea, sCity, sState, sCountry, sPincode);
			generateBillPageObject.selectServiceName(sServiceName);
			
			String actualnetamount = generateBillPageObject.getNetAmount();
			float factualdueamount = Float.parseFloat(actualnetamount);
			Log.info("Generated bill amount is : "+actualnetamount);
			
			String paiddueamount = Utils.roundUsingDecimalFormat(factualdueamount-factualdueamount/2);

			generateBillPageObject.EnterCashAmount(paiddueamount);
			String currentdueamount = generateBillPageObject.getDueAmount();
			String sActualgross = generateBillPageObject.getGrossAmount();
			String actualdiscount = generateBillPageObject.getDiscountAmount();
			
			paiddueamount = generateBillPageObject.getCashAmount();
			if(paiddueamount.isEmpty())
				paiddueamount = "0.00";
			generateBillPageObject.ClickGenerateBill();
			String msg = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			Assert.assertEquals(msg,"Saved successfully","***Registration --> Alert msg not displayed as expected.***");

			//Verify results
			MasterControlObject.gotoPage("Patient List");			
			patientListPageObject.searchRecord(sFirstName);
			
			patientListPageObject.editPatientDetails();
			
			String rTitle = generateBillPageObject.getTitle(); 
			String rFirstName = generateBillPageObject.getFirstName();
			String rGender = generateBillPageObject.getGender();
			String rAge = generateBillPageObject.getAge();
			String rAgeType = generateBillPageObject.getAgeType();
			String rMobileNo = generateBillPageObject.getMobileNumber();
			
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
			
			patientListPageObject.selectPatientBill();

			String rPatientBillGross = patientListPageObject.getPatientBillGrossAmount();
			String rPatientBillDiscount = patientListPageObject.getPatientBillDiscountAmount();
			String rPatientBillNet = patientListPageObject.getPatientBillNetAmount();			
			String rPatientBillCollected = patientListPageObject.getPatientBillCollected();
			String rPatientBillDue = patientListPageObject.getPatientBillDue();
			String rPatientBillRefferedBy = patientListPageObject.getPatientBillRefferedBy();
			String rServiceName = patientListPageObject.getServiceName();
			
			Assert.assertEquals(sTitle,rTitle,"***Title not updated as expected***");
			Assert.assertTrue(sFirstName.equalsIgnoreCase(rFirstName),"***FirstName not updated as expected***");
			Assert.assertEquals(sGender,rGender,"***Gender not updated as expected***");
			
			Assert.assertEquals(rAge,sAge,"***Age not updated as expected***");
			Assert.assertEquals(rAgeType,sAgeType,"***AgeType not updated as expected***");
			Assert.assertEquals(rMobileNo,sMobileNo,"***MobileNo not updated as expected***");
			Assert.assertTrue(rPatientBillGross.startsWith(sActualgross),"*** PatientBill Gross not updated as expected ***");
			Assert.assertEquals(rPatientBillDiscount,actualdiscount,"*** PatientBill discount not updated as expected ***");
			Assert.assertEquals(rPatientBillNet,actualnetamount,"***PatientBill Net not updated as expected***");
			Assert.assertTrue(rPatientBillCollected.startsWith(paiddueamount),"***PatientBillCollected not updated as expected***");
			Assert.assertEquals(rPatientBillDue,currentdueamount,"*** PatientBill due not updated as expected ***");
			Assert.assertEquals(rPatientBillRefferedBy,sRefferedType,"***PatientBillRefferedBy not updated as expected***");
			Assert.assertTrue(rServiceName.equalsIgnoreCase(sServiceName),"***ServiceName not updated as expected***");
			
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

	@Test(priority = 6)
	public void RegistrationToDispatch_BulkEntryFlow() throws Exception
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
			String sFirstName = Utils.getRandomName(25);
			String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
			String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
			String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
			String sMobileNo = Utils.getRandomNumber(10);
			String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
			String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
			String sBillDiscountType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountType);
			String sBillDiscountTypeAs=ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountTypeAs);
			String sEnterBillDiscount=ExcelUtils.getCellData(iTestCaseRow, Constant.col_DiscountValue);
			String sApprovedBy= ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
			String sRemarks= ExcelUtils.getCellData(iTestCaseRow, Constant.col_Remarks);
//			String sPrinterName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_PrinterName);
		
			MasterControlObject.gotoPage("Registration");
			
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
			generateBillPageObject.EnterCashAmount((Float.toString((iamount1-iamount1/2)+1)));
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
//			Assert.assertEquals(patientListPageObjects.getPatientBillNetAmount(),net,"Patient List --> Net amount showing incorrectly");
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
			bulkEntryObj.recollectSample();
			bulkEntryObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Entered Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Recollect");
			
			//Collecting sample
			MasterControlObject.changeRole(Constant.PHLEBOTOMY);
			manageSamplePageObjects.clickPatientDetailsTable();
			manageSamplePageObjects.searchRecord(sFirstName);
			manageSamplePageObjects.selectPatient();
			manageSamplePageObjects.clickCollectSample();
			String msg2 = MasterControlObject.getAlertMsg();
			Assert.assertEquals(msg2,"Saved Successfully","***Collecting sample --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample Collected");
			List<String> barcodes2 = patientListPageObjects.getAllBarCode();
			
			//Acknowledge sample
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage(Constant.PAGE_SAMPLE_ACKNOWLEDGEMENT);
			sampleScannerAck.AcknowledgeAllSamples(barcodes2);
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
		
			//Approve a record in Manage Approval
			MasterControlObject.changeRole(Constant.AUTHORIZATION);
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.recollectSample();
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Saved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Recollect");
			
			//Collecting sample
			MasterControlObject.changeRole(Constant.PHLEBOTOMY);
			manageSamplePageObjects.clickPatientDetailsTable();
			manageSamplePageObjects.searchRecord(sFirstName);
			manageSamplePageObjects.selectPatient();
			manageSamplePageObjects.clickCollectSample();
			String msg3 = MasterControlObject.getAlertMsg();
			Assert.assertEquals(msg3,"Saved Successfully","***Collecting sample --> Alert msg not displayed as expected.***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Sample Collected");
			List<String> barcodes3 = patientListPageObjects.getAllBarCode();
			
			//Acknowledge sample
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage(Constant.PAGE_SAMPLE_ACKNOWLEDGEMENT);
			sampleScannerAck.AcknowledgeAllSamples(barcodes3);
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
			
			//Approve a record in Manage Approval
			MasterControlObject.changeRole(Constant.AUTHORIZATION);
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.recheckResult();
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Saved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Recheck");
			
			//Enter Bulk Entry
			MasterControlObject.changeRole(Constant.TECHNICIAN);
			MasterControlObject.gotoPage("Bulk Entry");
			bulkEntryObj.searchRecord(sFirstName);			
			bulkEntryObj.enterResult();
			bulkEntryObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Entered Successfully","***Alert not displayed as expected after bulk entry***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Result Entered");
			
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
			Log.info(result.getMethod().getMethodName() + " --> Test Passed");
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Log.info(result.getMethod().getMethodName() + " --> Test Failed");
			Utils.takeScreenshot(driver, sTestCaseName);
		}
		else
		{
			ExcelUtils.setCellData("NIL", iTestCaseRow, Constant.col_Result);
			Log.info(result.getMethod().getMethodName()+" --> Cannot able to get result");
		}
		Log.endTestCase(result.getMethod().getMethodName());
    }
	
	@AfterTest
	public void closeDriver()
	{
//		driver.quit();
	}
}