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

public class PO_ResultValidate 
{
	WebDriver driver;
	public PO_ResultValidate(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubjectBulk")
	WebElement searchbox;
	
	@FindBy(how=How.XPATH,using=".//input[@ng-model='group.GroupRecheck']")
	WebElement chkbx_recheck;
	
	@FindBy(how=How.XPATH,using = ".//input[@ng-model='group.GroupRecollect']")
	WebElement chkbx_recollect;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how = How.XPATH, using = ".//*[@class='table table1 service-table scroll']")
	WebElement tbl_patientDetails;
	
	public void searchRecord(String sFirstName) throws Exception
	{
		try
		{
		Utils.waitForElement(searchbox);
		}
		catch(Exception e)
		{
			Log.error("      Result validate --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Result validate --> Patient not listed in universal search",e);
		}
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//div[@id='SearchSubjectBulkEntry']//span[1]";
		Utils.waitForElement(By.xpath(xpath));
		}
		catch(Exception e)
		{
			Log.error("      Result validate --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Result validate --> Patient not listed in universal search",e);
		}
		searchbox.sendKeys(Keys.TAB);
		Log.info("      Result Validate --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public int getTableRowSize()
	{
		List<WebElement> patientDetailsTableRows = tbl_patientDetails.findElements(By.tagName("tr"));
		return patientDetailsTableRows.size();
	}
	
	public void deselectHalfResults() throws Exception
	{
		List<WebElement> RefereceRange = driver.findElements(By.xpath(".//table[@class='table service-table scroll']/tbody//td[2]/input"));
		int size = RefereceRange.size()/2;
		int j=1;
		for(WebElement e : RefereceRange)
		{
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",e);
		    if(e.isSelected())
		    {
		    	e.click();
		    	j++;
		    }
		    if(j==size)
		    	break;
		}
		Log.info("      Few Results deselected for approval");
	}
	
	public void recollectSample()
	{
		if(!chkbx_recollect.isSelected())
			chkbx_recollect.click();
	}
	public void recheckResult()
	{
		if(!chkbx_recheck.isSelected())
			chkbx_recheck.click();
	}
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Click action performed on Result approval save Button");
	}
}
