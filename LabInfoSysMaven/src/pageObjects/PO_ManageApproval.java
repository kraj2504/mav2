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

public class PO_ManageApproval 
{
	WebDriver driver;
	public PO_ManageApproval(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubjectBulk")
	WebElement searchbox;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how = How.XPATH, using = ".//*[@class='table table1 service-table scroll']")
	WebElement tbl_patientDetails;
	
	@FindBy(how=How.XPATH,using=".//input[@ng-model='group.GroupRecheck']")
	WebElement chkbx_recheck;
	
	@FindBy(how=How.XPATH,using = ".//input[@ng-model='group.GroupRecollect']")
	WebElement chkbx_recollect;
	
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
		Utils.waitForElement(By.xpath(xpath));

		searchbox.sendKeys(Keys.TAB);

		Utils.waitForElement(By.xpath(".//*[@id='patient-wrap']/table/tbody/tr[1]/th[12]"));
		}
		catch(Exception e)
		{
			Log.error("      Manage approval --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Manage approval --> Patient not listed in universal search",e);
		}
		Log.info("      Manage Approval --> Search completed");
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
		    if(j>=size)
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
	
	public void clickSave() throws Exception
	{
		Utils.scrollIntoView(btn_Save);
		btn_Save.click();
		Log.info("      Click action performed on Result approval save Button");
	}
	
	public void clickRefresh() throws Exception
	{
		btn_Refresh.click();
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.waitForElement(drpdwn_customerFilter);
		Log.info("      ManageApproval --> Click action performed on Refresh Button");
	}
}
