package testCases2;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.PO_BulkEntry;
import pageObjects.PO_DueClearance;
import pageObjects.PO_Login;
import pageObjects.PO_ManageApproval;
import pageObjects.PO_ManageDispatch;
import pageObjects.PO_ManageSample;
import pageObjects.PO_MasterControl;
import pageObjects.PO_PatientList;
import pageObjects.PO_Registration;
import pageObjects.PO_ResultValidate;
import pageObjects.PO_SampleScannerAck;
import pageObjects.PO_WorkList;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

public class SmokePart_2
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
			PO_Login loginPageObject = PageFactory.initElements(driver,PO_Login.class);
			 
			loginPageObject.login(sUserName, sPassword);
			Assert.assertEquals("Registration", driver.getTitle());
		}
		
		catch(Exception e)
		{
			  ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			  Utils.takeScreenshot(driver, sTestCaseName);
			  Log.error(e.getMessage());
			  throw (e);
		}
	}
	
	//Verify Visibility Of Demographic Details
	@Test(priority = 2)
	public void visibilityOfDemographicDetails() throws Exception
	{
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);	
		try
		{
			Log.info("*** Verifying Visibility Of Demographic Details ***");
			generateBillPageObject.IsdemographicElementsDisplayed();
		}
		
		catch(Exception e)
		{
			  ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			  Utils.takeScreenshot(driver, sTestCaseName);
			  Log.error(e.getMessage());
			  throw (e);
		}
	}
	
	//Verifying Doctor Name Disabled When Referral Type Self
	@Test(priority = 3)
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
	
	//Verifying Doctor Name Enabled and able to select When Referral Type Client
	@Test(priority = 4)
	public void isDoctorNameEabledAndAbleToSelectWhenReferralTypeClient() throws Exception
	{
		String sReferralType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ReferralType);
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
	
	//Verifying Upload Photo in Registration
	@Test(priority = 5) 
	public void uploadPhotoinGenerateBill() throws Exception
	{	
		try
		{		 
			Log.info("*** Verifying Upload Photo in Registration ***");
			 PO_Registration generateBillObj = PageFactory.initElements(driver,PO_Registration.class);
			 PO_MasterControl MasterControlObj = PageFactory.initElements(driver,PO_MasterControl.class);
			 		 
			 generateBillObj.uploadPhoto();
			 
			 Assert.assertEquals(MasterControlObj.getAlertMsg(),"Image Upload Successfully","***Image Upload failed***");
			 MasterControlObj.acceptAlert();
			 generateBillObj.clickFileUploadtool();
		}
		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
			Log.error(e.getMessage());
			throw (e);
		}
	}
	
	//Verify mandatory Option For ApprovedBy and Remarks After Select DiscountType
	@Test(priority = 7)
	public void mandatoryOptionForApprovedByAndRemarksAfterSelectDiscountType() throws Exception
	{
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(45);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(13);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
		String sBillDiscountType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_BillDiscountType);
		String sApprovedBy = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ApprovedBy);
		
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
				
		try 
		{
			Log.info("*** Verify mandatory Option For ApprovedBy and Remarks After Select DiscountType ***");
			generateBillPageObject.clickRefresh();
			 
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.selectServiceName(sServiceName);
			generateBillPageObject.selectBillDiscountType(sBillDiscountType);
			generateBillPageObject.ClickGenerateBill();
			
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Please Select Approved By","***Alert not displayed as expected***");
			MasterControlObject.acceptAlert();
			
			generateBillPageObject.selectApprovedBy(sApprovedBy);
			generateBillPageObject.ClickGenerateBill();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Please Enter Remarks","***Alert not displayed as expected***");
			MasterControlObject.acceptAlert();
		}	
		
		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
			Log.error(e.getMessage());
			throw (e);
		}
	}
	
	// Verifying Registering a patient Using Mandatory Field Inputs
	@Test(priority = 8)
	public void generateBillingUsingMandatoryFieldInputs() throws Exception
	{	
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(45);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(13);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
				
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);
				
		try 
		{
			Log.info("*** Verifying Registering a patient Using Mandatory Field Inputs ***");
			generateBillPageObject.clickRefresh();
			
			//Register patient and generate bill
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.selectServiceName(sServiceName);
			generateBillPageObject.ClickGenerateBill();

			//Verify results
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			
			Assert.assertTrue(patientListPageObjects.getStatus().equalsIgnoreCase("Registered"),"***Status not updated as expected***");
		}
		
		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
			Log.error(e.getMessage());
			throw (e);
		}
	}
	
	//Verifying Registration With No Dues
	@Test(priority = 9)
	public void generateBillingWithNoDues() throws Exception
	{		
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(45);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(13);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
				
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_PatientList patientListPageObject = PageFactory.initElements(driver,PO_PatientList.class);
				
		try 
		{
			Log.info("*** Verifying Registration With No Dues ***");
			MasterControlObject.gotoPage("Registration");
		
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.selectServiceName(sServiceName);
			String amount = generateBillPageObject.getDueAmount();
			Log.info("Generated bill amount is : "+amount);
			generateBillPageObject.EnterCashAmount(amount);
			String cashamount = generateBillPageObject.getCashAmount();
			generateBillPageObject.ClickGenerateBill();

			//Verify results
			MasterControlObject.gotoPage("Patient List");
			patientListPageObject.searchRecord(sFirstName);
			
			Assert.assertEquals(cashamount,amount,"***Amount not updated as expected***");
			Assert.assertEquals(patientListPageObject.getStatus(),"Registered","***Status not updated as expected***");
			Assert.assertEquals(patientListPageObject.getPatientBillDue(),"0.00","***BillDue not updated as expected***");
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
	@Test(priority = 10)
	public void generateBillingWithContactDetailsAndVerifyUserDetails() throws Exception
	{	
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(45);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(13);
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
			float actualgross =  generateBillPageObject.getGrossAmount();
			String sActualgross = Float.toString(actualgross);
			String actualdiscount = generateBillPageObject.getDiscountAmount();
			
			paiddueamount = generateBillPageObject.getCashAmount();
			if(paiddueamount.isEmpty())
				paiddueamount = "0.00";
			generateBillPageObject.ClickGenerateBill();

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
	
	//verifying Registered Patient Details in Manage Samples List And Collecting Sample
	@Test(priority = 11)
	public void verifyRegisteredPatientDetailsinManageSamplesListAndCollectingSample() throws Exception
	{
		// Fetching Data from excel file 
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(45);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(13);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
				
		// Instantiate PageObjects class
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_ManageSample manageSamplePageObject = PageFactory.initElements(driver,PO_ManageSample.class);	
		try 
		{
			Log.info("*** verifying Registered Patient Details in Manage Samples List And Collecting Sample ***");
			MasterControlObject.changeRole("PHLEBOTOMIST");

			int rowSizeBeforeEntry = manageSamplePageObject.getPatientDetailsTableRowSize();
		 
			MasterControlObject.changeRole("RECEPTIONIST");
			
			// Waiting till loading the page
			WebDriverWait WaitVar2 = new WebDriverWait(driver,10);
			WaitVar2.until(ExpectedConditions.visibilityOfElementLocated(By.name("FirstName")));
			
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.selectServiceName(sServiceName);
			generateBillPageObject.ClickGenerateBill();
			
			MasterControlObject.changeRole("PHLEBOTOMIST");

			int rowSizeAfterEntry = manageSamplePageObject.getPatientDetailsTableRowSize();
			
			Log.info("Manage Sample Patient count before registration : "+rowSizeBeforeEntry);
			Log.info("Manage Sample Patient count after registration : "+rowSizeAfterEntry);
			
			Assert.assertEquals(rowSizeAfterEntry,(rowSizeBeforeEntry+1));
			
			manageSamplePageObject.clickPatientDetailsTable();
			manageSamplePageObject.searchRecord(sFirstName);
			manageSamplePageObject.selectPatient();
			manageSamplePageObject.clickCollectSample();
			
			//Verify Results
			String msg = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			
			patientListPageObjects.searchRecord(sFirstName);
			
			Assert.assertEquals("Saved Successfully", msg,"***Alert msg not displayed as expected***");
			Assert.assertEquals("Sample Collected", patientListPageObjects.getStatus(),"***Status not updated as expected***");
			Assert.assertNotEquals(patientListPageObjects.getBarCode(),"Yet to be processed","***Barcode not updated after sample collection***");
//			Assert.assertTrue(patientListPageObjects.getBarCode().startsWith(Constant.BarCodeNumber),"***BarCode not generated as expected***");
		}
		
		catch(Exception e)
		{
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.col_Result);
			Utils.takeScreenshot(driver, sTestCaseName);
			Log.error(e.getMessage());
			throw (e);
		}
	}
	
	// verifying Patient Name in Work List And Submit After Sample Collection
	// verifying Result Submission in Bulk Entry After Sample Collection and verify status
	
	@Test(priority = 12)
	public void verifyWorklistToDispatchFlow() throws Exception
	{	
		String sTitle = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Title);
		String sFirstName = Utils.getRandomName(45);
		String sGender = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Gender);
		String sAge = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Age);
		String sAgeType = ExcelUtils.getCellData(iTestCaseRow, Constant.col_AgeType);
		String sMobileNo = Utils.getRandomNumber(13);
		String sMailID = ExcelUtils.getCellData(iTestCaseRow, Constant.col_EmailID);
		String sServiceName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_ServiceName1);
				
		// Instantiate PageObjects class
		PO_MasterControl MasterControlObject = PageFactory.initElements(driver,PO_MasterControl.class);
		PO_Registration generateBillPageObject = PageFactory.initElements(driver,PO_Registration.class);
		PO_PatientList patientListPageObjects = PageFactory.initElements(driver,PO_PatientList.class);
		PO_ManageSample manageSamplePageObjects = PageFactory.initElements(driver,PO_ManageSample.class);
		PO_SampleScannerAck sampleScannerAck = PageFactory.initElements(driver,PO_SampleScannerAck.class);
		PO_WorkList workOrderObj = PageFactory.initElements(driver,PO_WorkList.class);
		PO_BulkEntry bulkEntryObj = PageFactory.initElements(driver,PO_BulkEntry.class);
		PO_ResultValidate resultValidate = PageFactory.initElements(driver,PO_ResultValidate.class);
		PO_ManageApproval ManageApprovalObj = PageFactory.initElements(driver,PO_ManageApproval.class);
		PO_DueClearance dueClearance = PageFactory.initElements(driver,PO_DueClearance.class);
		PO_ManageDispatch manageDispatchobj = PageFactory.initElements(driver,PO_ManageDispatch.class);
				
		try 
		{
			Log.info("*** verifying Worklist to Dispatch flow ***");
			//Generating Bill
			MasterControlObject.changeRole("RECEPTIONIST");
			generateBillPageObject.selectTitle(sTitle);
			generateBillPageObject.EnterFirstName(sFirstName);
			generateBillPageObject.selectGender(sGender);
			generateBillPageObject.enterAge(sAge);
			generateBillPageObject.selectAgeType(sAgeType);
			generateBillPageObject.EnterMobileNumber(sMobileNo);
			generateBillPageObject.EnterMailID(sMailID);
			generateBillPageObject.selectServiceName(sServiceName);
			generateBillPageObject.ClickGenerateBill();
			
			//Collecting sample
			MasterControlObject.changeRole("PHLEBOTOMIST");
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
			List<String> barcodes = patientListPageObjects.getAllBarCode();
			
			//Verify Patient name in worklist
			MasterControlObject.changeRole("TECHNICIAN");
			MasterControlObject.gotoPage("Work List");
			int i = workOrderObj.verifyPatientName(sFirstName);
			workOrderObj.ClickGenerateWorkList();
			Utils.keyCtrlW();
			String msg1 = MasterControlObject.getAlertMsg();
			MasterControlObject.acceptAlert();
			
			Assert.assertEquals(Integer.toString(i),"0","***Worklist --> Patient Name not displayed***");
			Assert.assertEquals(msg1,"Worklist Created Sucessfully","***Worklist not Created***");

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
			MasterControlObject.changeRole("PATHOLOGIST");
			ManageApprovalObj.searchRecord(sFirstName);
			ManageApprovalObj.clickSave();
			Assert.assertEquals(MasterControlObject.getAlertMsg(),"Results Approved Successfully","***Alert not displayed as expected after approving results***");
			MasterControlObject.acceptAlert();
			MasterControlObject.gotoPage("Patient List");
			patientListPageObjects.searchRecord(sFirstName);
			patientListPageObjects.verifyingStatusBarCode("Approved");
			
			//EMailing Final report with dues
			MasterControlObject.changeRole("DISPATCH");
			manageDispatchobj.searchRecord(sFirstName);
			manageDispatchobj.selectPatient();
			Assert.assertEquals(MasterControlObject.getAlertMsg(), "The patient has dues","Alert not showing as expected after entering Due");
			MasterControlObject.acceptAlert();
					
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
		driver.quit();
	}
}