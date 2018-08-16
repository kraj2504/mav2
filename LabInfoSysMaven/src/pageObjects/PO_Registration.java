
package pageObjects;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utility.Log;
import utility.Utils;

public class PO_Registration
{
	WebDriver driver;
	
	public PO_Registration(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using="SearchSubject")
	WebElement searchbox;
	
	@FindBy(how = How.XPATH, using="//*[@id='SearchSubjectDetails']//a[text()='No Results Found!']")
	WebElement noRecordFound;

	@FindBy(how = How.XPATH, using="//button[@class='btn btn-flat dropdown-toggle']")
	WebElement searchboxType;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.Title']")
	WebElement drpdwn_title;
	
	@FindBy(how = How.NAME, using="FirstName")
	WebElement txtbx_FirstName;
	
	@FindBy(how = How.NAME, using="LastName")
	WebElement txtbx_LastName;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.Gender']")
	WebElement drpdwn_Gender;
	
	@FindBy(how = How.ID, using="DOB")
	WebElement txtbx_DOB;
	
	@FindBy(how = How.NAME, using="Age")
	WebElement txtbx_Age;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.AgeType']")
	WebElement drpdwn_AgeType;
	
	@FindBy(how = How.NAME, using="MobileNo")
	WebElement txtbx_MobileNumber;
	
	@FindBy(how = How.ID, using="Emailid")
	WebElement txtbx_EMailID;
	
	@FindBy(how = How.XPATH, using="//div[2]/ui-view/div[1]/form/div/section/div/div/div[1]/div[1]/div/button")
	WebElement contactDetails;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.Communication.AlternateNO']")
	WebElement txtbx_Landline;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.MaritalStatus']")
	WebElement drpdwn_MaritalStatus;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Identification.URNIDType']")
	WebElement drpdwn_UIDType;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.Identification.URNID']")
	WebElement txtbx_UIDNo;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.Identification.MRDID']")
	WebElement txtbx_ExternalID;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.Communication.Address']")
	WebElement txtbx_Address;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.Communication.Area']")
	WebElement txtbx_Area;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Communication.City']")
	WebElement drpdwn_City;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Communication.State']")
	WebElement drpdwn_State;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Communication.Country']")
	WebElement drpdwn_Country;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.Communication.Pincode']")
	WebElement txtbx_Pincode;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Referral.PrimaryRefferralType']")
	WebElement drpdwn_ReferralType;
	
	@FindBy(how = How.ID, using="ClientName")
	WebElement AutoTxtbx_ReferralName;
	
	@FindBy(how = How.ID, using="idDoctorList")
	WebElement AutoTxtbx_DoctorName;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.ClinicalHistory.Diagnosis']")
	WebElement txtbx_ClinicalSymptoms;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='ObjRegistration.ClinicalHistory.Emergency']")
	WebElement chkbx_Emergency;
	
	@FindBy(how = How.ID, using="Rider")
	WebElement drpdwn_Rider;
	
	@FindBy(how = How.ID, using="ServiceCode-0")
	WebElement AutoTxtbx_ServiceCode;
	
	@FindBy(how = How.ID, using="ServiceCode-1")
	WebElement AutoTxtbx_ServiceCode2;
	
	@FindBy(how = How.ID, using="ServiceCode-2")
	WebElement AutoTxtbx_ServiceCode3;
	
	@FindBy(how = How.ID, using="ServiceCode-3")
	WebElement AutoTxtbx_ServiceCode4;
	
	@FindBy(how=How.ID, using ="ServiceCode-4")
	WebElement AutoTxtbx_ServiceCode5;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='row.ServiceInfo']")
	WebElement AutoTxtbx_ServiceName;
	
	@FindBy(how = How.XPATH, using="//table[@id='Servicetable']/tbody/tr[4]/td[3]/div/input")
	WebElement AutoTxtbx_ServiceName2;
	
	@FindBy(how = How.XPATH, using="//table[@id='Servicetable']/tbody/tr[5]/td[3]/div/input")
	WebElement AutoTxtbx_ServiceName3;//fieldset[@id='BillingDivHide1']/div/table/tbody/tr[5]/td[3]/div/input
	
	@FindBy(how = How.XPATH, using="//table[@id='Servicetable']/tbody/tr[6]/td[3]/div/input")
	WebElement AutoTxtbx_ServiceName4;
	
	@FindBy(how=How.XPATH, using= "//div[@class='col-sm-12']/table/tbody/tr[3]/td[8]/input")
	WebElement Txtbx_ItemDiscountValue1;
	
	@FindBy(how=How.XPATH, using= "//div[@ class='col-sm-12']/table/tbody/tr[4]/td[8]/input")
	WebElement Txtbx_ItemDiscountValue2;
	
	@FindBy(how=How.XPATH, using= "//div[@ class='col-sm-12']/table/tbody/tr[5]/td[8]/input")
	WebElement Txtbx_ItemDiscountValue3;
	
	@FindBy(how=How.XPATH, using= "//div[@ class='col-sm-12']/table/tbody/tr[6]/td[8]/input")
	WebElement Txtbx_ItemDiscountValue4;
	
	@FindBy(how=How.XPATH, using = "//div[@class='col-sm-12']/table/tbody/tr[3]/td[9]/select")
	WebElement Drpdwn_ItemDiscountType1;
	
	@FindBy(how=How.XPATH, using = "//table[@id='Servicetable']/tbody/tr[4]/td[9]/select")
	WebElement Drpdwn_ItemDiscountType2;
	
	@FindBy(how=How.XPATH, using = "//table[@id='Servicetable']/tbody/tr[5]/td[9]/select")
	WebElement Drpdwn_ItemDiscountType3;
	
	@FindBy(how=How.XPATH, using = "//table[@id='Servicetable']/tbody/tr[6]/td[9]/select")
	WebElement Drpdwn_ItemDiscountType4;
	
	@FindBy(how = How.XPATH, using="//i[@class = 'fa fa-save']") //a[@class = 'btn btn-app']/i[@class = 'fa fa-save']
	WebElement btn_GenerateBill;
	
	@FindBy(how = How.XPATH, using="//a[@class = 'btn btn-app']/i[@class = 'fa fa-eraser']")
	WebElement btn_Clear;
	
	@FindBy(how = How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_Refresh;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='objBilling.DiscountEntity.DiscountType']")
	WebElement drpdwn_BillDiscountType;
	
	@FindBy(how=How.ID, using="ApprovedBy")
	WebElement drpdwn_ApprovedBy;
	
	@FindBy(how=How.ID, using="RPercentage")
	WebElement Radio_Percent;
	
	@FindBy(how=How.ID, using="RAmount")
	WebElement Radio_Amount;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='BillingDivHide2']/div[3]/div")
	WebElement lbl_Discount;
	
	@FindBy(how=How.XPATH, using ="//input[@ng-model='objBilling.DiscountEntity.DiscountAmountRPercentage']")
	WebElement txtbx_BillDiscount;
	
	@FindBy(how=How.ID, using="Remarks")
	WebElement txtbx_Remarks;
	
	@FindBy(how=How.ID, using="CurrencyType")
	WebElement drpdwn_CurrencyType;
	
	@FindBy(how=How.XPATH, using=".//input[@ng-model='CashAmount']")
	WebElement txtbx_CashAmount;
	
	@FindBy(how = How.ID, using = "close-bar")
	WebElement FileUpload;
	
	@FindBy(how = How.NAME, using = "Upload Photo")
	WebElement ChooseFile;
	
	@FindBy(how = How.ID, using = "drpfile")
	WebElement drpdwn_UploadFile;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Upload TRF']")
	WebElement UploadTRF;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.GrossAmount']")
	WebElement GrossAmount;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.DiscountAmount']")
	WebElement DiscountAmount;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.RoundOff']")
	WebElement RoundOffAmount;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.NetAmount']")
	WebElement NetAmount;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.CollectedAmount']")
	WebElement CollectedAmount;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.DueAmount']")
	WebElement DueAmount;
	
	@FindBy(how = How.XPATH, using = ".//input[@value='EMAIL']")
	WebElement chkbx_Email;
	
	@FindBy(how = How.XPATH, using = ".//input[@value='COURIER']")
	WebElement chkbx_Courier;
	
	// maria
	
		@FindBy(how = How.ID, using ="BillingDivHide1")
		WebElement tbl_Particulars;
		
		@FindBy(how = How.XPATH, using = "//input[@ng-model='objBilling.DiscountEntity.DiscountAmountRPercentage']")
		WebElement BillDiscountAmount;	
	
	public void searchRecord(String sFirstName) throws Exception
	{
		// Waiting till loading the page
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//div[@id='SearchSubjectDetails']//span[1]";
		Utils.waitForElement(By.xpath(xpath));
		}
		catch(Exception e)
		{
			Log.error("      Registration --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Registration --> Patient not listed in universal search",e);
		}
		searchbox.sendKeys(Keys.TAB);
		//String xpath = ".//*[text()='"+sFirstName+"']";
		//driver.findElement(By.xpath(".//*[@id='typeahead-235-9463-option-0']/a")).click();
		Log.info("      Registration --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public String getTotalAmountOfAllServices() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		List<WebElement> e = driver.findElements(By.xpath(".//*[@id='Servicetable']/tbody/tr/td[7]/label"));
		DecimalFormat df = new DecimalFormat("#.##");
		double ActualTotalAmount = 0.00;
		for(int i=3;i<(e.size() + 2);i++)
		{	
			String amountPath=".//*[@id='Servicetable']/tbody/tr["+(i)+"]/td[7]/label";
			String Amount =driver.findElement(By.xpath(amountPath)).getText();
			ActualTotalAmount = ActualTotalAmount + Double.parseDouble(Amount);
		}
		return df.format(ActualTotalAmount);
	}
	
	public String getNetAmountOfAllServices() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		List<WebElement> e = driver.findElements(By.xpath(".//*[@id='Servicetable']/tbody/tr/td[10]/label"));
		DecimalFormat df = new DecimalFormat("#.##");
		double ActualTotalAmount = 0.00;
		for(int i=3;i<(e.size() + 2);i++)
		{	
			String amountPath=".//*[@id='Servicetable']/tbody/tr["+(i)+"]/td[10]/label";
			String Amount =driver.findElement(By.xpath(amountPath)).getText();
			ActualTotalAmount = ActualTotalAmount + Double.parseDouble(Amount);
		}
		return df.format(ActualTotalAmount);
	}
	
	public void verifyPaientInSearch(String sFirstName) throws Exception
	{
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='SearchSubjectDetails']//a[text()='No Results Found!']")).isDisplayed());
		}
		catch(NoSuchElementException e)
		{
			Log.info("      Registration --> Record showing");
			Assert.assertEquals("Record Found","No Results Found","***Registration --> Record found***");
		}
	}
	
	public void changeSearchTypeToBooking() throws Exception
	{
		Utils.waitForElement(searchboxType);
		searchboxType.click();
		driver.findElement(By.xpath(".//*[@id='SearchPatient']/li[2]/a")).click();
	}
	
	public void changeSearchType() throws Exception
	{
		Utils.waitForElement(searchboxType);
		Select value=new Select(searchboxType);
		value.selectByVisibleText("Search by Booking");
		String selectedOption = new Select(searchboxType).getFirstSelectedOption().getText();
		Log.info("      Search box type changed to : "+selectedOption);
	}
		
	public void IsdemographicElementsDisplayed()
	{
		Assert.assertEquals(true,drpdwn_title.isDisplayed());
		Log.info("      Title field displayed");
		Assert.assertEquals(true,txtbx_FirstName.isDisplayed());
		Log.info("      FirstName field displayed");
		Assert.assertEquals(true,txtbx_LastName.isDisplayed());
		Log.info("      LastName field displayed");
		Assert.assertEquals(true,drpdwn_Gender.isDisplayed());
		Log.info("      Gender field displayed");
		Assert.assertEquals(true,txtbx_DOB.isDisplayed());
		Log.info("      DOB field displayed");
		Assert.assertEquals(true,txtbx_Age.isDisplayed());
		Log.info("      Age field displayed");
		Assert.assertEquals(true,drpdwn_AgeType.isDisplayed());
		Log.info("      AgeType field displayed");
		Assert.assertEquals(true,txtbx_MobileNumber.isDisplayed());
		Log.info("      MobileNumber field displayed");
		Assert.assertEquals(true,txtbx_EMailID.isDisplayed());
		Log.info("      EMailID field displayed");
	}
	
	public void enterContactDetails(String sLandline,String sMaritalStatus,String sUIDType, String sUIDNo, String sExternalID, String sAddress, String sArea, String sCity, String sState,String sCountry,String sPincode) throws Exception
	{
		//driver.findElement(By.xpath("//a[@class='dropdown-toggle cust-logo']/img")).click();
		Utils.waitForElement(txtbx_EMailID);
		txtbx_EMailID.click();
		txtbx_EMailID.sendKeys(Keys.TAB);
		
		Utils.keyEnter();

		txtbx_Landline.sendKeys(sLandline);
		Log.info("      Given Landline : "+ txtbx_Landline.getAttribute("value"));

		Select value=new Select(drpdwn_MaritalStatus);
		value.selectByVisibleText(sMaritalStatus);
		String selectedOption = new Select(drpdwn_MaritalStatus).getFirstSelectedOption().getText();
		Log.info("      Selected MaritalStatus : "+selectedOption);
		
		Select value1=new Select(drpdwn_UIDType);
		value1.selectByVisibleText(sUIDType);
		String selectedOption1 = new Select(drpdwn_UIDType).getFirstSelectedOption().getText();
		Log.info("      Selected UIDType : "+selectedOption1);
		
		txtbx_UIDNo.sendKeys(sUIDNo);
		Log.info("      Given UIDNo : "+ txtbx_UIDNo.getAttribute("value"));
		
		txtbx_ExternalID.sendKeys(sExternalID);
		Log.info("      Given ExternalID : "+ txtbx_ExternalID.getAttribute("value"));
		
		txtbx_Address.sendKeys(sAddress); 
		Log.info("      Given Address : "+ txtbx_Address.getAttribute("value"));
		
		txtbx_Area.sendKeys(sArea);
		Log.info("      Given Area : "+ txtbx_Area.getAttribute("value"));
		
		Utils.waitUntilAngularFinishHttpCalls();
		Select value2=new Select(drpdwn_City);
		value2.selectByVisibleText(sCity);
		Utils.waitUntilAngularFinishHttpCalls();
		String selectedOption2 = new Select(drpdwn_City).getFirstSelectedOption().getText();
		Log.info("      Selected City : "+selectedOption2);
		
		Utils.waitUntilAngularFinishHttpCalls();
//		Select value3=new Select(drpdwn_State);
//		value3.selectByVisibleText(sState);
		String selectedOption3 = new Select(drpdwn_State).getFirstSelectedOption().getText();
		Log.info("      Selected State : "+selectedOption3);
		
//		Utils.waitUntilAngularFinishHttpCalls();
//		Select value4=new Select(drpdwn_Country);
//		value4.selectByVisibleText(sCountry);
		String selectedOption4 = new Select(drpdwn_Country).getFirstSelectedOption().getText();
		Log.info("      Selected Country : "+selectedOption4);
		
//		Utils.waitUntilAngularFinishHttpCalls();
		txtbx_Pincode.sendKeys(sPincode);
		Log.info("      Given Pincode : "+ txtbx_Pincode.getAttribute("value"));
	}
	
	public void enterAddress(String sAddress, String sArea, String sCity, String sState,String sCountry,String sPincode) throws Exception
	{
		//driver.findElement(By.xpath("//a[@class='dropdown-toggle cust-logo']/img")).click();
		Utils.waitForElement(txtbx_EMailID);
		txtbx_EMailID.click();
		txtbx_EMailID.sendKeys(Keys.TAB);
		
		Utils.keyEnter();
		
		txtbx_Address.sendKeys(sAddress); 
		Log.info("      Given Address : "+ txtbx_Address.getAttribute("value"));
		
		txtbx_Area.sendKeys(sArea);
		Log.info("      Given Area : "+ txtbx_Area.getAttribute("value"));
		
		Select value2=new Select(drpdwn_City);
		value2.selectByVisibleText(sCity);
		String selectedOption2 = new Select(drpdwn_City).getFirstSelectedOption().getText();
		Log.info("      Selected City : "+selectedOption2);
		
		Select value3=new Select(drpdwn_State);
		value3.selectByVisibleText(sState);
		String selectedOption3 = new Select(drpdwn_State).getFirstSelectedOption().getText();
		Log.info("      Selected State : "+selectedOption3);
		
		Select value4=new Select(drpdwn_Country);
		value4.selectByVisibleText(sCountry);
		String selectedOption4 = new Select(drpdwn_Country).getFirstSelectedOption().getText();
		Log.info("      Selected Country : "+selectedOption4);
		
		txtbx_Pincode.sendKeys(sPincode);
		Log.info("      Given Pincode : "+ txtbx_Pincode.getAttribute("value"));
	}
	
	public void selectTitle(String sTitle) throws Exception
	{
		Utils.waitForElement(drpdwn_title);
		Select value=new Select(drpdwn_title);
		value.selectByVisibleText(sTitle);
		String selectedOption = new Select(drpdwn_title).getFirstSelectedOption().getText();
		Assert.assertEquals(selectedOption,sTitle, "*** Registration --> Title not selected as expected ***");
		Log.info("      Selected Title : "+selectedOption);
	}
	
	public String getTitle() throws Exception
	{
		Utils.waitForElement(drpdwn_title);
		String selectedOption = new Select(drpdwn_title).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public void EnterFirstName(String sFirstName) throws Exception
	{
		Utils.waitForElement(txtbx_FirstName);
		txtbx_FirstName.sendKeys(sFirstName);
		Log.info("      Given First Name : "+ txtbx_FirstName.getAttribute("value"));
		
		txtbx_FirstName.sendKeys(Keys.chord(Keys.CONTROL,"a"));
	}
	
	public String getFirstName()
	{
		
		return txtbx_FirstName.getAttribute("value");
	}
	
	public void EnterLastName(String sLastName) throws Exception
	{
		Utils.waitForElement(txtbx_LastName);
		txtbx_LastName.sendKeys(sLastName);
		Log.info("      Given Last Name : "+ txtbx_LastName.getAttribute("value"));
	}
	
	public String getLastName()
	{
		
		return txtbx_LastName.getAttribute("value");
	}
	
	public void selectGender(String sGender) throws Exception
	{
		Utils.waitForElement(drpdwn_Gender);
		Select value=new Select(drpdwn_Gender);
		value.selectByVisibleText(sGender);
		String selectedOption = new Select(drpdwn_Gender).getFirstSelectedOption().getText();
		Log.info("      Selected Gender : "+selectedOption);
	}
	
	public String getGender()
	{
		String selectedOption = new Select(drpdwn_Gender).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public void enterDOB(String sDOB) throws Exception
	{
		Utils.waitForElement(txtbx_DOB);
		txtbx_DOB.sendKeys(sDOB);
		Log.info("      Given DOB : "+txtbx_DOB.getAttribute("value"));
	}
	
	public String getDOB()
	{
		return txtbx_DOB.getAttribute("value");
	}
	
	public void enterAge(String sAge) throws Exception
	{
		Utils.waitForElement(txtbx_Age);
		txtbx_Age.sendKeys(sAge);
		Log.info("      Given Age : "+ sAge);
	}
		
	public String getAge()
	{
		return txtbx_Age.getAttribute("value");
	}
	
	public void selectAgeType(String sAgeType) throws Exception
	{
		Utils.waitForElement(drpdwn_AgeType);
		Select value=new Select(drpdwn_AgeType);
		value.selectByVisibleText(sAgeType);
		Log.info("      Selected AgeType : "+sAgeType);
	}
	
	public String getAgeType()
	{
		String selectedOption = new Select(drpdwn_AgeType).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public void EnterMobileNumber(String sMobileNumber) throws Exception
	{
		Utils.waitForElement(txtbx_MobileNumber);
		txtbx_MobileNumber.sendKeys(sMobileNumber);
		Log.info("      Given MobileNumber : "+ sMobileNumber);
	}
	
	public String getMobileNumber()
	{
		return txtbx_MobileNumber.getAttribute("value");
	}
	
	public void EnterMailID(String sMailID) throws Exception
	{
		Utils.waitForElement(txtbx_EMailID);
		txtbx_EMailID.sendKeys(sMailID);
		Log.info("      Given EMailID : "+ txtbx_EMailID.getAttribute("Value"));
	}
	
	public String getMailID()
	{
		return txtbx_EMailID.getAttribute("value");
	}
	
	public String getLanline()
	{
		return txtbx_Landline.getAttribute("value");
	}
	
	public String getMaritalStatus()
	{
		String selectedOption = new Select(drpdwn_MaritalStatus).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public String getUIDType()
	{
		String selectedOption = new Select(drpdwn_UIDType).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public String getUIDNo()
	{
		return txtbx_UIDNo.getAttribute("value");
	}
	
	public String getExternalID()
	{
		return txtbx_ExternalID.getAttribute("value");
	}
	
	public String getAddress()
	{
		return txtbx_Address.getAttribute("value");
	}
	
	public String getArea()
	{
		return txtbx_Area.getAttribute("value");
	}
	
	public String getCity()
	{
		String selectedOption = new Select(drpdwn_City).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public String getState()
	{
		String selectedOption = new Select(drpdwn_State).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public String getCountry()
	{
		String selectedOption = new Select(drpdwn_Country).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public String getPincode()
	{
		return txtbx_Pincode.getAttribute("value");
	}
	
	public void selectReferralType(String sReferralType)
	{
		Select value=new Select(drpdwn_ReferralType);
		value.selectByVisibleText(sReferralType);
		String selectedOption = new Select(drpdwn_ReferralType).getFirstSelectedOption().getText();
		Log.info("      Selected ReferralType : "+selectedOption);
	}
	
	public void selectReferralName(String sReferralName) throws Exception
	{	
		AutoTxtbx_ReferralName.sendKeys(sReferralName);
		Utils.waitUntilAngularFinishHttpCalls();
		AutoTxtbx_ReferralName.sendKeys(Keys.TAB);
	    Log.info("      Selected ReferralName : "+AutoTxtbx_ReferralName.getAttribute("value"));
	}
	
	public boolean doctorNameEnableStatus()
	{
		return AutoTxtbx_DoctorName.isEnabled();
	}
	
	public void selectDoctorName(String sDoctorName) throws Exception
	{
		AutoTxtbx_DoctorName.sendKeys(sDoctorName);
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.keyEnter();
	    Log.info("      Selected DoctorName : "+AutoTxtbx_DoctorName.getAttribute("value"));
	}
	
	public String getDoctorName()
	{
		
		return AutoTxtbx_DoctorName.getAttribute("value");
	}

	public void EnterClinicalSymptoms(String sClinicalSymptoms)
	{
		txtbx_ClinicalSymptoms.sendKeys(sClinicalSymptoms);
		Log.info("      Given ClinicalSymptom : "+txtbx_ClinicalSymptoms.getAttribute("value"));
	}
	
	public void ClickEmergency(String sEmergency)
	{
		if(sEmergency.equalsIgnoreCase("Yes"))
		{
		chkbx_Emergency.click();
		Log.info("      Emergency check box clicked" );
		}
	}
	
	public void selectRider(String sRider)
	{
		Select value=new Select(drpdwn_Rider);
		value.selectByVisibleText(sRider);
		String selectedOption = new Select(drpdwn_Rider).getFirstSelectedOption().getText();
		Log.info("      Selected Rider : "+selectedOption);
	}
	
	public void selectServiceCode(String sServiceCode) throws Exception
	{
			AutoTxtbx_ServiceCode.click();
			String sendkeyvalue = sServiceCode.substring(0, 3);
			AutoTxtbx_ServiceCode.sendKeys(sendkeyvalue);
			Utils.waitUntilAngularFinishHttpCalls();
			List<WebElement> optiontoselect = driver.findElements(By.xpath("//a[@class='ng-scope']"));
			for(WebElement option : optiontoselect)
			{
				if(option.getText().equalsIgnoreCase(sServiceCode))
				{
					option.click();
					break;
				}
			}
			Utils.waitUntilAngularFinishHttpCalls();
			Log.info("      Selected ServiceCode : "+AutoTxtbx_ServiceCode.getAttribute("value"));
	}
	
	public  void EnterServiceCode(int rowIndex, String sServicecode)
	{
		switch(rowIndex)
		{
			case 1:
					//AutoTxtbx_ServiceCode.click();
					AutoTxtbx_ServiceCode.sendKeys(sServicecode);
					AutoTxtbx_ServiceCode.sendKeys(Keys.TAB);
					break;
        
			case 2: 
					//AutoTxtbx_ServiceCode2.click();
					AutoTxtbx_ServiceCode2.sendKeys(sServicecode);
					AutoTxtbx_ServiceCode2.sendKeys(Keys.TAB);
					break;
		
			case 3: 
					//AutoTxtbx_ServiceCode3.click();
					AutoTxtbx_ServiceCode3.sendKeys(sServicecode);
					AutoTxtbx_ServiceCode3.sendKeys(Keys.TAB);
					break;
		
			case 4: 
					//AutoTxtbx_ServiceCode4.click();
					AutoTxtbx_ServiceCode4.sendKeys(sServicecode);
					AutoTxtbx_ServiceCode4.sendKeys(Keys.TAB);
					break;	
		}
	}
	
	public void EnterServiceName(int rowIndex, String sServiceName) throws Exception
	{
		switch(rowIndex)
		{
			case 1: 
					AutoTxtbx_ServiceName.click();
					AutoTxtbx_ServiceName.sendKeys(sServiceName);
					Utils.waitUntilAngularFinishHttpCalls();
					AutoTxtbx_ServiceName.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
		
			case 2:
					AutoTxtbx_ServiceName2.click();
					AutoTxtbx_ServiceName2.sendKeys(sServiceName);
					Utils.waitUntilAngularFinishHttpCalls();
					AutoTxtbx_ServiceName2.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
			
			case 3:
					AutoTxtbx_ServiceName3.click();
					AutoTxtbx_ServiceName3.sendKeys(sServiceName);
					Utils.waitUntilAngularFinishHttpCalls();
					AutoTxtbx_ServiceName3.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
			case 4:
					AutoTxtbx_ServiceName4.click();
					AutoTxtbx_ServiceName4.sendKeys(sServiceName);
					Utils.waitUntilAngularFinishHttpCalls();
					AutoTxtbx_ServiceName4.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
		}
	}
	
	public void selectServiceName(String sServiceName) throws Exception
	{	
			AutoTxtbx_ServiceName.click();
			AutoTxtbx_ServiceName.sendKeys(sServiceName);
			Utils.waitUntilAngularFinishHttpCalls();
			AutoTxtbx_ServiceName.sendKeys(Keys.ENTER);
			Utils.waitUntilAngularFinishHttpCalls();
			Log.info("      Selected ServiceName : "+AutoTxtbx_ServiceName.getAttribute("value"));
	}
	//Discount Value hardcoded in this method
	public void EnterItemDiscount(int rowIndex,String discountType) throws Exception
	{
		switch(rowIndex)
		{
			case 1:
				Select value1= new Select(Drpdwn_ItemDiscountType1);
				value1.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue1.sendKeys("30");
				else
					Txtbx_ItemDiscountValue1.sendKeys("10");
				break;
			
			case 2:
				Select value2= new Select(Drpdwn_ItemDiscountType2);
				value2.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue2.sendKeys("50");
				else
					Txtbx_ItemDiscountValue2.sendKeys("10");
				break;
				
			case 3:
				Select value3= new Select(Drpdwn_ItemDiscountType3);
				value3.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue2.sendKeys("50");
				else
					Txtbx_ItemDiscountValue2.sendKeys("10");
				break;
				
			case 4:
				Select value4= new Select(Drpdwn_ItemDiscountType3);
				value4.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue2.sendKeys("50");
				else
					Txtbx_ItemDiscountValue2.sendKeys("10");
				break;
		}
	}
	
	public String GetItemPrice(int rowIndex) throws Exception
	{
		String ItemPrice = "";
		switch(rowIndex)
		{
			case 1:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[3]/td[7]/label")).getText();
				return ItemPrice;
			case 2:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[4]/td[7]/label")).getText();
				return ItemPrice;
				
			case 3:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[5]/td[7]/label")).getText();
				return ItemPrice;
				
			case 4:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[6]/td[7]/label")).getText();
				return ItemPrice;
		}
		return ItemPrice;
	}
	
	public void EnterItemDiscount(int rowIndex,String discountType, String discountvalue) throws Exception
	{
		switch(rowIndex)
		{
			case 1:
				Utils.waitUntilAngularFinishHttpCalls();
				Select value1= new Select(Drpdwn_ItemDiscountType1);
				value1.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue1.sendKeys(discountvalue);
				else
					Txtbx_ItemDiscountValue1.sendKeys(discountvalue);
				break;
			
			case 2:
				Utils.waitUntilAngularFinishHttpCalls();
				Select value2= new Select(Drpdwn_ItemDiscountType2);
				value2.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue2.sendKeys(discountvalue);
				else
					Txtbx_ItemDiscountValue2.sendKeys(discountvalue);
				break;
				
			case 3:
				Utils.waitUntilAngularFinishHttpCalls();
				Select value3= new Select(Drpdwn_ItemDiscountType3);
				value3.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue3.sendKeys(discountvalue);
				else
					Txtbx_ItemDiscountValue3.sendKeys(discountvalue);
				break;
				
			case 4:
				Utils.waitUntilAngularFinishHttpCalls();
				Select value4= new Select(Drpdwn_ItemDiscountType4);
				value4.selectByVisibleText(discountType);
				if(discountType.contains("Amount"))
					Txtbx_ItemDiscountValue4.sendKeys(discountvalue);
				else
					Txtbx_ItemDiscountValue4.sendKeys(discountvalue);
				break;
		}
	}
	
	public void selectItemDiscountType(int rowIndex,String discountType) throws Exception
	{
		switch(rowIndex)
		{
			case 1:
				Select value1= new Select(Drpdwn_ItemDiscountType1);
				value1.selectByVisibleText(discountType);
				break;
			
			case 2:
				Select value2= new Select(Drpdwn_ItemDiscountType2);
				value2.selectByVisibleText(discountType);
				break;
				
			case 3:
				Select value3= new Select(Drpdwn_ItemDiscountType3);
				value3.selectByVisibleText(discountType);
				break;
			
			case 4:
				Select value4= new Select(Drpdwn_ItemDiscountType4);
				value4.selectByVisibleText(discountType);
				break;
		}
	}
	
	public void EnterItemDiscountValue(int rowIndex,String discountValue)
	{
		switch(rowIndex)
		{
			case 1:
				Txtbx_ItemDiscountValue1.sendKeys(discountValue);
				break;
			
			case 2:
				Txtbx_ItemDiscountValue2.sendKeys(discountValue);
				break;
				
			case 3:
				Txtbx_ItemDiscountValue3.sendKeys(discountValue);
				break;
				
			case 4:
				Txtbx_ItemDiscountValue4.sendKeys(discountValue);
				break;
		}
	}
	 
	public void selectBillDiscountType(String sBillDiscountType)
	{
		Select value=new Select(drpdwn_BillDiscountType);
		value.selectByVisibleText(sBillDiscountType);
		String selectedOption = new Select(drpdwn_BillDiscountType).getFirstSelectedOption().getText();
		Log.info("      Selected BillDiscountType : "+selectedOption);
	}
	
	public void selectBillDiscountTypeAs(String sBillDiscountTypeAs) throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		 if(sBillDiscountTypeAs.equalsIgnoreCase("Percentage"))
		{
			 Utils.waitUntilAngularFinishHttpCalls();
			Radio_Percent.click();

			Log.info("      Radio_Percent is clicked");
		}
		else if (sBillDiscountTypeAs.equalsIgnoreCase("Amount"))
		{
			Utils.waitUntilAngularFinishHttpCalls();
			Radio_Amount.click();
			
			Log.info("      Radio_Amount is clicked");
		}
	}
	
	public void enterBillDiscount(String sEnterBillDiscount)
	{
		//txtbx_BillDiscount.click();
		//txtbx_BillDiscount.sendKeys(Keys.CONTROL +"a");
		txtbx_BillDiscount.clear();
		txtbx_BillDiscount.sendKeys(sEnterBillDiscount);
		Log.info("      Enter Discount value is : "+ txtbx_BillDiscount.getAttribute("value"));
	}
	
	public void selectApprovedBy(String sApprovedBy)
	{
		Select value=new Select(drpdwn_ApprovedBy);
		value.selectByVisibleText(sApprovedBy);
		String selectedOption = new Select(drpdwn_ApprovedBy).getFirstSelectedOption().getText();
		Log.info("      Selected ApprovedBy : "+selectedOption);
	}
	
	public void enterRemarks(String sRemarks)
	{
    	txtbx_Remarks.sendKeys(sRemarks);	
    	Log.info("      Enter remark is : "+ txtbx_Remarks.getAttribute("value"));
	}
	
	public void EnterCashAmount(String amount)
	{
		txtbx_CashAmount.sendKeys(amount);
		Log.info("      Given amount : "+txtbx_CashAmount.getAttribute("value"));
	}
	
	public void ClearEnteredCashAmount()
	{
		txtbx_CashAmount.clear();
		Log.info("      Entered cash amount cleared");
	}
	
	public String getCashAmount()
	{
		return txtbx_CashAmount.getAttribute("value");
	}
	
	public void uploadPhoto() throws Exception
	{
		FileUpload.click();
		Utils.waitUntilAngularFinishHttpCalls();
		ChooseFile.click();
		Runtime.getRuntime().exec(".//AutoITFiles//uploadphoto.exe");
	}
	
	public void clickFileUploadtool() throws Exception
	{
		FileUpload.click();
	}
	
	public void uploadTRF(String sDocName) throws Exception
	{
		FileUpload.click();
		Utils.waitUntilAngularFinishHttpCalls();
/*		drpdwn_UploadFile.click();
		Thread.sleep(3000);
		UploadTRF.click();
*/		
		Select value=new Select(drpdwn_UploadFile);
		value.selectByVisibleText(sDocName);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		String selectedOption = new Select(drpdwn_UploadFile).getFirstSelectedOption().getText();
		Utils.waitUntilAngularFinishHttpCalls();
		
//		Runtime.getRuntime().exec("D://AutoITFiles//uploadTRF.exe");
//		Log.info("      Selected Upload File type is : "+selectedOption);
	}
	
	public String getGrossAmount()
	{
		return GrossAmount.getText();
	}
	
	public String getDiscountAmount()
	{
		return DiscountAmount.getText();
	}
	
	public String getRoundOffAmount()
	{
		return RoundOffAmount.getText();
	}
	
	public String getNetAmount()
	{
		return NetAmount.getText();
	}
	
	public String getCollectedAmount()
	{
		return CollectedAmount.getText();
	}
	
	public String getDueAmount() throws Exception
	{
		return DueAmount.getText();
	}
	
	public  void verifyDueAmount() throws Exception
	{
		float Netamt = Float.parseFloat(NetAmount.getText());
		float colamt = Float.parseFloat(CollectedAmount.getText());
		float expecteddue = Netamt-colamt;
		
		Assert.assertTrue(getDueAmount().startsWith(Float.toString(expecteddue)), "Due amount showing incorrectly");
	}
	
	public  void verifyNetAmount() throws Exception
	{
		float grossamt = Float.parseFloat(GrossAmount.getText());
		float discountamt = Float.parseFloat(DiscountAmount.getText());
		float roundoffamt = Float.parseFloat(RoundOffAmount.getText());
		float expectedNet = grossamt-discountamt+roundoffamt;
		Assert.assertTrue(getNetAmount().startsWith(Float.toString(expectedNet)), "Net amount showing incorrectly");
	}
	
	public void ClickGenerateBill() throws Exception
	{
		btn_GenerateBill.click();
		Log.info("      Patient registration completed successfully");
	}
	public void clearForm()
	{
		btn_Clear.click();
	}
	
	public void clickRefresh() throws Exception
	{
		btn_Refresh.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	//maria 
/*	
		public String getDiscountValueforOneService()
		{
			String discount =driver.findElement(By.xpath("//table[@id='Servicetable']/tbody/tr[3]/td[8]/input")).getAttribute("value");
			String value = discount;
			int posi = value.indexOf(".");
		    value = value.substring(0, posi);
			return value;
		}
		
		public String getDiscountAmount()
		{
/*			String value = DiscountAmount.getText();
			System.out.println("value: "+value);
			int posi = value.indexOf(".");
			value = value.substring(0, posi);
			System.out.println("deci: "+value);
			return value;
			return DiscountAmount.getText();
		}
/*		
		public float getGrossAmount()
		{
			String value = GrossAmount.getText();
			 System.out.println("value: "+value);
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
	        System.out.println("deci: "+value);
			return Float.parseFloat(value);
		}
		
		
		public String getDueAmount() throws Exception
		{
			String value =DueAmount.getText();
			 System.out.println("value: "+value);
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
			System.out.println("DueAmount  : "+ value);
			Thread.sleep(1000);
			//float getdue= Float.parseFloat(value);
			return value;
		}
		
		
		public String getNetAmount()
		{
/*			String netAmt= driver.findElement(By.xpath("//span[@ng-bind='objBilling.GrossDetails.NetAmount']")).getText();
			String value =netAmt;
			 System.out.println("value: "+value);
			 int posi = value.indexOf(".");
	      value = value.substring(0, posi);
	      System.out.println("deci: "+value);
			System.out.println("Amount  : "+ value);
			return value;
			return NetAmount.getText();
			}
				
		public String getCollectedAmount()
		{
			return CollectedAmount.getText();
		}
*/		
		public float calculateAmount()
		{
		    List<WebElement> Rows = tbl_Particulars.findElements(By.tagName("tr"));
			List<WebElement> th=tbl_Particulars.findElements(By.tagName("th"));
			int AmountPosition=0;
			float  Total = 0;
			for(int j=1;j<th.size();j++)
			{
				if("Amount".equalsIgnoreCase(th.get(j).getText()))
				{
					AmountPosition=j+1;
				} 
			}  
			for(int i=2;i<(Rows.size()-1);i++)
			{	
				String amountPath="//table[@id='Servicetable']/tbody/tr["+(i+1)+"]/td["+AmountPosition+"]/label";
				String Amount =driver.findElement(By.xpath(amountPath)).getText();
				Total = Total + Float.parseFloat(Amount);
			}
			return Total;
		}
		
		public String calculateNetAmount()
		{
		    List<WebElement> Rows = tbl_Particulars.findElements(By.tagName("tr"));
			List<WebElement> th=tbl_Particulars.findElements(By.tagName("th"));
			int NetPosition=0;
			float  Total = 0;
			String value1="";
			for(int j=1;j<th.size();j++)
			{
				if("Net".equalsIgnoreCase(th.get(j).getText()))
				{
					NetPosition=j+1;
				} 
			}  
			for(int i=2;i<(Rows.size()-1);i++)
			{	
				
			String netPath="//table[@id='Servicetable']/tbody/tr["+(i+1)+"]/td["+NetPosition+"]/label";
			String Net =driver.findElement(By.xpath(netPath)).getText();
			Total = Total + Float.parseFloat(Net);
			
			String calNet= Float.toString(Total);
			String value =calNet;
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
			value1=value;
			}
			
			return value1;
		}
		
		public String calculateNetAmountForBillDiscount(String sEnterBillDiscount)
		{
		    List<WebElement> Rows = tbl_Particulars.findElements(By.tagName("tr"));
			List<WebElement> th=tbl_Particulars.findElements(By.tagName("th"));
			int NetPosition=0;
			float  Total = 0;
			String value1="";
			for(int j=1;j<th.size();j++)
			{
				if("Net".equalsIgnoreCase(th.get(j).getText()))
				{
					NetPosition=j+1;
				} 
			}  
			for(int i=2;i<(Rows.size()-1);i++)
			{		
			String netPath="//table[@id='Servicetable']/tbody/tr["+(i+1)+"]/td["+NetPosition+"]/label";
			String Net =driver.findElement(By.xpath(netPath)).getText();
			Total = Total + Float.parseFloat(Net);
			float billVal= Float.parseFloat(sEnterBillDiscount);
		    float net= Total-billVal;
		    String calNet= Float.toString(net);
			String value =calNet;
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
			value1=value;
			}
			
			return value1;
		}
		
		public String getRoundOff()
		{
			/*
			 String rdoff= RoundOffAmount.getText();
				String value =rdoff;
				 System.out.println("value: "+value);
				 int posi = value.indexOf(".");
		        value = value.substring(0, posi);
				System.out.println("NetAmount  : "+ value);
				*/
			return RoundOffAmount.getText();
		}
		
		public String calBillDiscountInAmount()
		{
			String billDiscount= BillDiscountAmount.getAttribute("value");
			return billDiscount;
		}
		
		public String calculateRoundOff()
		{
			String disValue = DiscountAmount.getText();
			
			double d= Double.parseDouble(disValue);
			double dis = d - Math.floor(d);
			String rndOFF = Double.toString(dis);
			return rndOFF;
		}
		public String calBillDiscountInPercentage()
		{
		 float gross= Float.parseFloat(getGrossAmount());
		 float a= gross*10;
			float b= a/100;
			String billPerc= Float.toString(b);
			return billPerc;
		}
		
		public String calculateItemDiscountInPercentage()
		{
			float amount = calculateAmount();
			float a= amount*10;
			float b= a/100;
			String Dis= Float.toString(b);
			String value =Dis;
			int posi = value.indexOf(".");
	        value = value.substring(0, posi);
		
			return value;
		}
		public void checkEmail()
		{
			if(!chkbx_Email.isSelected())
				chkbx_Email.click();
		}
		public void unCheckEmail()
		{
			if(chkbx_Email.isSelected())
				chkbx_Email.click();
		}
		public void checkCourier()
		{
			if(!chkbx_Courier.isSelected())
				chkbx_Courier.click();
		}
		public void unCheckCourier()
		{
			if(chkbx_Courier.isSelected())
				chkbx_Courier.click();
		}
}