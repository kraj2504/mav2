package pageObjects;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class PO_MasterControl 
{
	WebDriver driver;
	
	public PO_MasterControl(WebDriver driver)
	{
		this.driver=driver;
	}
	//a[@class='dropdown-toggle cust-logo']/img
	@FindBy(how = How.XPATH, using = "//a[@class='dropdown-toggle ng-binding']")
	WebElement Logo;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='TriggerMenu']")
	WebElement Menu;
	
	@FindBy(how = How.XPATH, using = ".//a[@class = 'dropdown-toggle' ]/i[@class = 'fa fa-user']")
	WebElement RoleMenu;
	
	@FindBy(how = How.XPATH, using = "//div[@class = 'navbar-custom-menu']//li[@class = 'dropdown user user-menu']")
	WebElement userMenu;
	
	@FindBy(how = How.XPATH, using = "//li[@class = 'user-footer']//a[text()='Sign out']")
	WebElement Signout;
	
	@FindBy(how = How.XPATH, using = "//li[@class = 'user-footer']//a[text()='Change Password']")
	WebElement ChangePassword;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='modernAlert']/p")
	WebElement alertbox;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='Mechine_form']/div[2]/button[2]")
	WebElement btn_CancelPrinterSetting;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='Mechine_form']/div[2]/button[1]")
	WebElement btn_SavePrinterSetting;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='Mechine_form']//select")
	WebElement drpdwn_printer;
	
	public void selectPrinter() throws Exception
	{
		Utils.waitForElement(drpdwn_printer);
		Select options = new Select(drpdwn_printer);
		options.selectByVisibleText("Printer For Trichy");//printer1, Printer For Trichy,
		btn_SavePrinterSetting.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectPrinter(String PrinterName) throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		Select options = new Select(drpdwn_printer);
		options.selectByVisibleText(PrinterName);//printer1, Printer For Trichy,
		btn_SavePrinterSetting.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void CancelPrinterSetting() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		btn_CancelPrinterSetting.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void changeRole(String sRole) throws Exception
	{
		//WebDriverWait WaitVar = new WebDriverWait(driver,10);
		//WaitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[@class = 'dropdown-toggle' ]/i[@class = 'fa fa-user']")));
		Utils.waitForElement(RoleMenu);
		RoleMenu.click();
		Utils.waitUntilAngularFinishHttpCalls();
		
		List<WebElement> Rolelist = driver.findElements(By.xpath("//li[@class = 'footer ng-scope']/a"));
		int i = 0;
		for(WebElement ele : Rolelist)
		{
			Utils.waitForElement(ele);
			String value = ele.getText();
			if(value.equalsIgnoreCase(sRole))
			{
				i = 1;
				ele.click();
				
				if(value.equalsIgnoreCase("RECEPTION"))
					Utils.waitUntilPageLoad("Registration");
				else if(value.equalsIgnoreCase("PHLEBOTOMY"))
					Utils.waitUntilPageLoad("Manage Sample");
				else if(value.equalsIgnoreCase("TECHNICIAN"))
					Utils.waitUntilAngularFinishHttpCalls();
				else if(value.equalsIgnoreCase("SENIOR TECHNICIAN"))
					Utils.waitUntilPageLoad("Result Validate");
				else if(value.equalsIgnoreCase("AUTHORIZATION"))
					Utils.waitUntilPageLoad("Manage Approval");
				else if(value.trim().equalsIgnoreCase("DISPATCH"))
					Utils.waitUntilPageLoad("Manage Dispatch");
				else if(value.trim().equalsIgnoreCase("ACCOUNTS"))
					Utils.waitUntilAngularFinishHttpCalls();
				else if(value.trim().equalsIgnoreCase("ADMINSTRATOR"))
					Utils.waitUntilPageLoad("Analyte Master");
				else
				{
					Log.info("      Wait action not performed after role change : "+value);
					System.out.println("Wait action not performed after role change : "+value);
				}
				Log.info("      Role changed to : "+sRole);
				
				try
				{
					Utils.waitForElement(Logo);
					Logo.click();
				}
				catch(Exception e)
				{
					Utils.waitUntilAngularFinishHttpCalls();
					Log.info("      Cannot able to click Logo after change the role to:"+sRole);
					System.out.println("Cannot able to click Logo after change the role to:"+sRole);
				}
				break;
			}
		}
		if(i==0)
		{
			Log.error(sRole+" role not listed in role menu");
			Assert.fail(sRole+" role not listed in role menu");
		}
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void gotoPage(String pagename) throws Exception
	{
		Utils.waitForElement(Logo);
		Utils.scrollIntoView(Logo);
		try
		{
		Logo.click();
		}
		catch(Exception e)
		{
			Utils.waitUntilAngularFinishHttpCalls();
			Log.info("      Cannot able to click Logo before change the page to:"+pagename);
			System.out.println("Cannot able to click Logo before change the page to:"+pagename);
			Menu.click();
			Utils.waitUntilAngularFinishHttpCalls();
		}
		Menu.click();
		Utils.waitUntilAngularFinishHttpCalls();

		List<WebElement> pagelist = driver.findElements(By.xpath(".//*[@id='TriggerMenu']//a"));
		int j=0;
		for (WebElement ele:pagelist)
		{
			if(ele.getText().equals(pagename))
			{
				j=1;
				ele.click();
				Log.info("      Page changed to : "+pagename);
				 
				if(pagename.equalsIgnoreCase("Patient List"))
					Utils.waitUntilPageLoad("Patient List");
				else if(pagename.equalsIgnoreCase("Sample Acknowledgement"))
					Utils.waitUntilPageLoad("Sample Acknowledgement");
				else if(pagename.equalsIgnoreCase("Work List"))
					Utils.waitUntilPageLoad("Work List");
				else if(pagename.equalsIgnoreCase("Bulk Entry"))
					Utils.waitUntilPageLoad("Bulk Entry");
				else if(pagename.equalsIgnoreCase("Manage Approval"))
					Utils.waitUntilPageLoad("Manage Approval");
				else if(pagename.trim().equalsIgnoreCase("Manage Dispatch"))
					Utils.waitUntilPageLoad("Manage Dispatch");
				else if(pagename.equalsIgnoreCase("Due Clearance"))
					Utils.waitUntilPageLoad("Due Clearance");
				else if(pagename.equalsIgnoreCase("Registration"))
					Utils.waitUntilPageLoad("Registration");
				else if(pagename.equalsIgnoreCase("ROAR Approval"))
					Utils.waitUntilPageLoad("ROAR Approval");
				else if(pagename.equalsIgnoreCase("PayOff"))
					Utils.waitUntilPageLoad("PayOff");
				else if(pagename.equalsIgnoreCase("PayOff Report"))
					Utils.waitUntilPageLoad("PayOff Report");
				else if(pagename.equalsIgnoreCase("Home Collection Booking"))
					Utils.waitUntilPageLoad("Home Collection Booking");
				else if(pagename.equalsIgnoreCase("Home Collection Assigning"))
					Utils.waitUntilPageLoad("Home Collection Assigning");
				else if(pagename.equalsIgnoreCase("Sample Outsource"))
					Utils.waitUntilPageLoad("Sample Outsource");
				else if(pagename.equalsIgnoreCase("Outsource Acknowledge"))
					Utils.waitUntilPageLoad("Outsource Acknowledge");
				else if(pagename.equalsIgnoreCase("Recall"))
					Utils.waitUntilPageLoad("Recall");
				else if(pagename.equalsIgnoreCase("Refund Cancel"))
					Utils.waitUntilPageLoad("Refund Cancel");
				else
				{
					Log.info("      Wait action not performed after page change : "+pagename);
					System.out.println("Wait action not performed after page change : "+pagename);
				}
				Logo.click();
				break;
			}
		}
		if(j==0)
		{
			Log.error(pagename+" page not listed in Menu list");
			Assert.fail(pagename+" page not listed in Menu list");
		}
		Log.info("      End of goto page");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	public void ClickSignOut() throws Exception
	{
		userMenu.click();
		Signout.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public String getAlertMsg() throws Exception
	{
		WebDriverWait WaitVar = new WebDriverWait(driver,10);
		WaitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='modernAlert']/p")));
		Utils.waitUntilAngularFinishHttpCalls();
		String msg = alertbox.getText();
		return msg;
	}
	public void acceptAlert() throws Exception
	{
		WebDriverWait WaitVar = new WebDriverWait(driver,10);
		WaitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtalert']")));
		Log.info("      Alert shown as '"+getAlertMsg()+"'. Alert accepted");
		driver.findElement(By.xpath("//input[@id='txtalert']")).click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
}
