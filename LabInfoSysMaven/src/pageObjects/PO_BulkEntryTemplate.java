package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import utility.Log;
import utility.Utils;

public class PO_BulkEntryTemplate 
{
	private static final Boolean FALSE = false;

	WebDriver driver;
	public PO_BulkEntryTemplate(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubjectBulk")
	WebElement searchbox;
	
	@FindBy(how = How.XPATH, using = ".//input[@ng-model='service.IsApprove']")
	WebElement chkbx_selectService;
	
	@FindBy(how = How.ID, using = "Template_0")
	WebElement drpdwn_template;
	
	@FindBy(how = How.XPATH, using = ".//iframe[@id='templateEditor0_ifr']")
	WebElement iframe;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	public void clickSave() throws Exception
	{
		Utils.scrollIntoView(btn_Save);
		Utils.waitUntilAngularFinishHttpCalls();
		btn_Save.click();
		Log.info("      Click action performed on Bulk Entry Save Button");
	}

	public void searchRecord(String sFirstName) throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//*[@id]/a/span[1]";
		Utils.waitForElement(By.xpath(xpath));

		Utils.keyEnter();
		Utils.waitForElement(chkbx_selectService);
		}
		catch(Exception e)
		{
			Log.error("      Bulk Entry Template --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Bulk Entry Template --> Patient not listed in universal search",e);
		}
		Log.info("      Bulk Entry Template --> Search completed");
	}
	
	public void selectService() throws Exception
	{
		Utils.waitForElement(chkbx_selectService);
		if(!chkbx_selectService.isSelected())
		{
			chkbx_selectService.click();
			Log.info("      Bulk Entry Template --> Service selected");
		}
	}
	
	public void selectTemplate(String sTemplateName) throws Exception
	{
		Utils.waitForElement(drpdwn_template);
		Select option = new Select(drpdwn_template);
		if(!option.getFirstSelectedOption().getText().trim().equalsIgnoreCase(sTemplateName))
		{
			option.selectByVisibleText(sTemplateName);
		}
		Log.info("      BulkEntry ROAR --> Analyte selected");
	}
	
	public void enterResultText() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		driver.switchTo().frame("templateEditor0_ifr");
		WebElement editable = driver.switchTo().activeElement();
		editable.sendKeys("Bulk entry template result by technician");
		driver.switchTo().defaultContent();
	}
}