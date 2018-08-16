package pageObjects;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Log;
import utility.Utils;

public class PO_Recall 
{
	WebDriver driver;
	public PO_Recall(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubjectBulk")
	WebElement searchbox;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how = How.XPATH, using = ".//*[@class='table table1 service-table scroll']")
	WebElement tbl_patientDetails;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_Refresh;
	
	@FindBy(how=How.NAME, using="customfilter")
	WebElement drpdwn_customerFilter;
	
	
	public void searchRecord(String sFirstName) throws Exception
	{
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//div[@id='SearchSubjectBulkEntry']//span[1]";
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.waitForElement(By.xpath(xpath));

		searchbox.sendKeys(Keys.TAB);

		Utils.waitUntilAngularFinishHttpCalls();
		}
		catch(Exception e)
		{
			Log.error("      Recall --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Recall --> Patient not listed in universal search",e);
		}
		Log.info("      Recall --> Search completed");
	}
	
	public void clickSave() throws Exception
	{
		Utils.scrollIntoView(btn_Save);
		btn_Save.click();
		Log.info("      Recall --> Click action performed on save Button");
	}
	
	public void clickRefresh() throws Exception
	{
		btn_Refresh.click();
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.waitForElement(drpdwn_customerFilter);
		Log.info("      Recall --> Click action performed on Refresh Button");
	}
}
