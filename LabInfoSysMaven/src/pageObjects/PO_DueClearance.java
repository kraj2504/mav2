package pageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Log;
import utility.Utils;

public class PO_DueClearance
{
	WebDriver driver;
	
	public PO_DueClearance(WebDriver driver)
	{
		this.driver=driver;
	}

	@FindBy(how = How.ID, using = "SearchSubject") //txtAutocomplete_value
	public WebElement searchbox;
	
	@FindBy(how=How.XPATH, using=".//input[@ng-model='CashAmount']")
	WebElement txtbx_cashamount;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_Refresh;
	
	@FindBy(how = How.XPATH, using="//a[@class = 'btn btn-app']/i[@class = 'fa fa-save']")
	WebElement btn_GenerateWorkList;
	
	@FindBy(how = How.XPATH, using=".//table[@class='table service-table scroll']")
	WebElement tbl_patientDetails;
		
	public void searchRecord(String sFirstName) throws Exception
	{
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//div[@class='col-sm-offset-3 col-sm-6']/div//span[1]";//div[@id='txtAutocomplete']//span[1]
		Utils.waitForElement(By.xpath(xpath));
		
		searchbox.sendKeys(Keys.TAB);
		searchbox.click();
		searchbox.sendKeys(Keys.ENTER);
		Utils.waitForElement(By.xpath(".//table[@class='table service-table scroll']//th[8]"));
		}
		catch(Exception e)
		{
			Log.error("      Due clearance --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Due clearance --> Patient not listed in universal search",e);
		}
		Log.info("      Due clearance --> Searching record completed");
	}
	
	public void selectPatient() throws Exception
	{
		tbl_patientDetails.click();
		Utils.waitUntilAngularFinishHttpCalls();
		List<WebElement> patientDetailsTableRows = tbl_patientDetails.findElements(By.tagName("tr"));
		int rowsize = patientDetailsTableRows.size();
		String path = "//table[@class = 'table service-table scroll']/tbody/tr["+rowsize+"]/td[2]/input";
		WebElement selectPatientDetails = driver.findElement(By.xpath(path));
		selectPatientDetails.click();
		Log.info("      Patient selected for Due clearance");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void enterCashAmount(String amount)
	{
		txtbx_cashamount.sendKeys(amount);
		Log.info("      Given cashamount : "+ txtbx_cashamount.getAttribute("value"));
	}
	
	public String getBalance()
	{
		WebElement due = driver.findElement(By.xpath(".//label[@ng-bind='DueAmount']"));
/*
		List<WebElement> th=tbl_patientDetails.findElements(By.tagName("th"));
		//Get column position of Patient Name
		int col_position=0;
		for(int j=0;j<th.size();j++)
		{
			if("Balance".equalsIgnoreCase(th.get(j).getText()))
			{
				col_position=j+1;
				break;
			}
		}
		WebElement bal = tbl_patientDetails.findElement(By.xpath("//tr/td["+col_position+"]"));
*/		Log.info("      Retrieved balance amount : "+ due.getText());
		return due.getText();
	}
	
	public void clickRefresh()
	{
		btn_Refresh.click();
		Log.info("      Due clearance --> Click action performed on Refresh button ");
	}
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Due clearance --> Click action performed on save button ");
	}
}