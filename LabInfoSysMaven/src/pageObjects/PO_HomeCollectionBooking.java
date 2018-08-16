package pageObjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Log;
import utility.Utils;

public class PO_HomeCollectionBooking 
{
	WebDriver driver;
	
	public PO_HomeCollectionBooking(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using="txtAutocomplete_value")
	WebElement searchbox;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjHomeCollectionEntity.Demographics.Title']")
	WebElement drpdwn_title;
	
	@FindBy(how = How.NAME, using="FirstName")
	WebElement txtbx_PatientName;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjHomeCollectionEntity.Demographics.Gender']")
	WebElement drpdwn_Gender;
	
	@FindBy(how = How.NAME, using="Age")
	WebElement txtbx_Age;
	
	@FindBy(how = How.NAME, using="Landline")
	WebElement txtbx_ContactNo;
	
	@FindBy(how = How.XPATH, using=".//*[@id='idDoctorList']/input")
	WebElement AutoTxtbx_DoctorName;

	@FindBy(how = How.XPATH, using="//*[@ng-model='ObjHomeCollectionEntity.Communication.Address']")
	WebElement txtbx_Address;
	
	@FindBy(how = How.XPATH, using="//*[@ng-model='ObjHomeCollectionEntity.Communication.Area']")
	WebElement txtbx_Area;
	
	@FindBy(how = How.XPATH, using="//*[@ng-model='ObjHomeCollectionEntity.Communication.Pincode']")
	WebElement txtbx_Pincode;
	
	@FindBy(how = How.ID, using="FromDates")
	WebElement txtbx_AppointmentDate;
	
	@FindBy(how = How.ID, using="Appointment")
	WebElement txtbx_AppointmentTime;
	
	@FindBy(how = How.ID, using="ServiceCode-0")
	WebElement AutoTxtbx_TestCode1;
	
	@FindBy(how = How.ID, using="ServiceCode-1")
	WebElement AutoTxtbx_TestCode2;
	
	@FindBy(how = How.XPATH, using="//table[@class='table service-table scroll']/tbody/tr[2]/td[3]//input")
	WebElement AutoTxtbx_Investigation1;
	
	@FindBy(how = How.XPATH, using="//table[@class='table service-table scroll']/tbody/tr[3]/td[3]//input")
	WebElement AutoTxtbx_Investigation2;
	
	@FindBy(how = How.XPATH, using="//table[@class='table service-table scroll']/tbody/tr[2]/td[4]//input")
	WebElement Txtbx_Amount1;
	
	@FindBy(how = How.XPATH, using="//table[@class='table service-table scroll']/tbody/tr[3]/td[4]//input")
	WebElement Txtbx_Amount2;
	
	@FindBy(how = How.XPATH, using="//table[@class='table service-table scroll']/tbody/tr[2]/td[5]/a/img")
	WebElement Delete1;
	
	@FindBy(how = How.XPATH, using="//a[@class = 'btn btn-app']/i[@class = 'fa fa-save']")
	WebElement btn_save;
	
	public void searchRecord(String sFirstName) throws Exception
	{
		// Waiting till loading the page
		WebDriverWait WaitVar1 = new WebDriverWait(driver,10);
		WaitVar1.until(ExpectedConditions.visibilityOf(searchbox));
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		String xpath = ".//div[@id='txtAutocomplete_value']//span[1]";
		Utils.waitForElement(By.xpath(xpath));
		
		searchbox.sendKeys(Keys.TAB);
		//String xpath = ".//*[text()='"+sFirstName+"']";
		//driver.findElement(By.xpath(".//*[@id='typeahead-235-9463-option-0']/a")).click();
		Log.info("      Home collection booking --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectTitle(String sTitle)
	{
		Select value=new Select(drpdwn_title);
		value.selectByVisibleText(sTitle);
		String selectedOption = new Select(drpdwn_title).getFirstSelectedOption().getText();
		Log.info("      Selected Title : "+selectedOption);
	}
	
	public String getTitle()
	{
		String selectedOption = new Select(drpdwn_title).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public void EnterPatientName(String sFirstName)
	{
		txtbx_PatientName.sendKeys(sFirstName);
		Log.info("      Given First Name : "+ txtbx_PatientName.getAttribute("value"));
	}
	
	public String getPatientName()
	{
		
		return txtbx_PatientName.getAttribute("value");
	}
	
	public void selectGender(String sGender)
	{
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
	
	public void enterAge(String sAge)
	{
		txtbx_Age.sendKeys(sAge);
		Log.info("      Given Age : "+ sAge);
	}
		
	public String getAge()
	{
		return txtbx_Age.getAttribute("value");
	}
	
	public void selectDoctorName(String sDoctorName) throws Exception
	{
//		String sendkeyvalue = sDoctorName.substring(0, 8);
		AutoTxtbx_DoctorName.sendKeys(sDoctorName);
		AutoTxtbx_DoctorName.sendKeys(Keys.TAB);
		Utils.waitUntilAngularFinishHttpCalls();
	    Log.info("      Selected DoctorName : "+AutoTxtbx_DoctorName.getAttribute("value"));
	}
	
	public String getDoctorName()
	{
		
		return AutoTxtbx_DoctorName.getAttribute("value");
	}
	
	public void enterContactDetails(String sLandline,String sAddress,String sArea,String sPincode) throws Exception
	{
		txtbx_ContactNo.sendKeys(sLandline);
		Log.info("      Given ContactNo : "+ txtbx_ContactNo.getAttribute("value"));
		
		txtbx_Address.sendKeys(sAddress); 
		Log.info("      Given Address : "+ txtbx_Address.getAttribute("value"));
		
		txtbx_Area.sendKeys(sArea);
		Log.info("      Given Area : "+ txtbx_Area.getAttribute("value"));
		
		txtbx_Pincode.sendKeys(sPincode);
		Log.info("      Given Pincode : "+ txtbx_Pincode.getAttribute("value"));
	}

	@SuppressWarnings("deprecation")
	public void selectAppointmentDate() throws Exception
	{
		Date date = new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMMMMMM");
		String currentmonth = formatter.format(date);
		currentmonth = currentmonth.substring(0, 3);
		int clicked = 0;
		txtbx_AppointmentDate.click();
		
		WebElement monthtable = driver.findElement(By.xpath(".//fieldset[@class='cust-fieldset']/div[10]/div/div/div/div/div/table"));
		
		List<WebElement> monthTableRows = monthtable.findElements(By.tagName("tr"));
		for(WebElement monthrow:monthTableRows)
		{
			List<WebElement> monthcells = monthrow.findElements(By.tagName("td"));
			for(WebElement monthcell:monthcells)
			{
				if(monthcell.getAttribute("innerHTML").equalsIgnoreCase(currentmonth))
				{	
					Utils.waitUntilAngularFinishHttpCalls();
					monthcell.click();
					List<WebElement> dateTableRows = monthtable.findElements(By.tagName("tr"));
					for(WebElement daterow:dateTableRows)
					{
						List<WebElement> datecells = daterow.findElements(By.tagName("td"));
						for(WebElement datecell:datecells)
						{
							if(datecell.getAttribute("innerHTML").equalsIgnoreCase(Integer.toString(date.getDate())))
							{
								datecell.click();
								clicked=1;
								break;
							}
							if(clicked==1)
								break;
						}
						if(clicked==1)
							break;
					}
					if(clicked==1)
						break;
				}
				if(clicked==1)
					break;
			}
			if(clicked==1)
				break;
		}
	}
	
	public void EnterAppointmentTime(String amount)
	{
		txtbx_AppointmentTime.sendKeys(amount);
		Log.info("      Given amount : "+txtbx_AppointmentTime.getAttribute("value"));
	}
	
	public String getAppointmentTime()
	{
		return txtbx_AppointmentTime.getAttribute("value");
	}
	
	public void selectTestCode(String sServiceCode) throws Exception
	{
			AutoTxtbx_TestCode1.click();
			String sendkeyvalue = sServiceCode.substring(0, 3);
			AutoTxtbx_TestCode1.sendKeys(sendkeyvalue);
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
			Log.info("      Selected ServiceCode : "+AutoTxtbx_TestCode1.getAttribute("value"));
			Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectInvestigation(String sServiceName) throws Exception
	{	
			AutoTxtbx_Investigation1.click();
			AutoTxtbx_Investigation1.sendKeys(sServiceName);
			Utils.waitUntilAngularFinishHttpCalls();
			List<WebElement> optiontoselect = driver.findElements(By.xpath(".//*[@class='ng-binding ng-scope']"));
			for(WebElement option : optiontoselect)
			{
				if(option.getText().equalsIgnoreCase(sServiceName))
				{
					
					option.click();
					break;
				}
			}
			Log.info("      Selected ServiceName : "+AutoTxtbx_Investigation1.getAttribute("value"));
			Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void EnterCashAmount(String amount)
	{
		Txtbx_Amount1.sendKeys(amount);
		Log.info("      Given amount : "+Txtbx_Amount1.getAttribute("value"));
	}
	
	public String getCashAmount()
	{
		return Txtbx_Amount1.getAttribute("value");
	}
	
	public void clickSave() throws Exception
	{
		btn_save.click();
		Log.info("      Click action performed on save button");
		Utils.keyEsc();
	}
}	