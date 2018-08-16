package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utility.Log;
import utility.Utils;

public class PO_Login
{
	WebDriver driver;
	
	public PO_Login(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.NAME, using="first_name")
	WebElement txtbx_username;
	
	@FindBy(how = How.NAME, using="email")
	WebElement txtbx_password;
	
	@FindBy(how = How.XPATH, using=".//*[@id='contact_form']/fieldset/div/div[3]/div/p/button[1]")
	WebElement btn_login;
	
	@FindBy(how = How.NAME, using="FirstName")
	WebElement txtbx_FirstName;
	
	@FindBy(how = How.NAME, using="AnalyteCode")
	WebElement txtbx_AnalyteCode;
	
	public void login(String uname,String Pswd) throws Exception
	{
			txtbx_username.sendKeys(uname);
			Log.info("      "+uname+" is entered in UserName text box" );
		
			txtbx_password.sendKeys(Pswd);
			Log.info("      "+Pswd+" is entered in Password text box" );
			
			Thread.sleep(500);
		
			btn_login.click();
			
			Thread.sleep(1000);
			try
			{
				Utils.waitForElement(txtbx_FirstName);
			}
			catch(Exception e)
			{
				Log.error("      Cannot able to login to the application"+e.getMessage());
				throw new Exception("Cannot able to login to the application",e);
			}
			Assert.assertTrue(txtbx_FirstName.isDisplayed(),"*** Login failed ***");
/*		try
		{
			if(uname.equalsIgnoreCase("admin"))
			{
				Utils.waitForElement(txtbx_AnalyteCode);
				Assert.assertTrue(txtbx_AnalyteCode.isDisplayed(),"*** Login failed ***");
			}
			else if(uname.equalsIgnoreCase("gpladmin"))
			{
				Utils.waitForElement(txtbx_AnalyteCode);
				Assert.assertTrue(txtbx_AnalyteCode.isDisplayed(),"*** Login failed ***");
			}
			else
			{
				Thread.sleep(1000);
				Utils.waitForElement(txtbx_FirstName);
				Assert.assertTrue(txtbx_FirstName.isDisplayed(),"*** Login failed ***");
			}
		}
		catch(Exception e)
		{
			Log.error("      Cannot able to login to the application"+e.getMessage());
			throw new Exception("Cannot able to login to the application",e);
		}
*/			Log.info("      Click action is performed on Login button");
	}
}