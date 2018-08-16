package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import utility.Log;
import utility.Utils;

public class PO_AnalyteMaster 
{
	WebDriver driver;
	
	public PO_AnalyteMaster(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.XPATH, using=".//input[@ng-model='AnalyteSearchText']")
	WebElement searchbox;
	
	@FindBy(how = How.XPATH, using=".//input[@ng-model='objAnalyteMasters.IsOutsource']")
	WebElement chkbx_IsOutsource;

	@FindBy(how = How.XPATH, using=".//select[@ng-model='objAnalyteMasters.ResultType']")
	WebElement drpdwn_ResultType;
	
	@FindBy(how = How.XPATH, using=".//a[text()='Result Details']")
	WebElement tab_ResultDetails;
	
	@FindBy(how = How.XPATH, using=".//input[@ng-model='AnalyteTemplateName']")
	WebElement txtbx_ABRETemplateName;
	
	@FindBy(how = How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	public void searchRecord(String sAnalyteName) throws Exception
	{
		// Waiting till loading the page
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sAnalyteName);

		try
		{
		String xpath = ".//form[@class='sidebar-form searchForm ng-valid ng-dirty ng-valid-parse']/ul//span[1]";
		Utils.waitForElement(By.xpath(xpath));
		}
		catch(Exception e)
		{
			Log.error("      Analyte Master --> Analyte not listed in universal search"+e.getMessage());
			throw new Exception("Analyte Master --> Analyte not listed in universal search",e);
		}
		searchbox.sendKeys(Keys.TAB);
		Log.info("      Analyte Master --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void MarkAnalyteAsOutsource() throws Exception
	{
		if(!chkbx_IsOutsource.isSelected())
			chkbx_IsOutsource.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void MarkAnalyteAsNonOutsource() throws Exception
	{
		if(chkbx_IsOutsource.isSelected())
			chkbx_IsOutsource.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectResultType(String resultType)
	{
		Select option = new Select(drpdwn_ResultType);
		option.selectByVisibleText(resultType);
	}
	
	public void clickResultDetailsTab()
	{
		
	}
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Analyte Master --> Save button clicked");	
	}
}
