package pageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Log;
import utility.Utils;

public class PO_DoctorPayoffReport 
{
WebDriver driver;
	
	public PO_DoctorPayoffReport(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using="SearchTexttxt")
	WebElement searchbox;
	
	@FindBy(how = How.ID, using="tblWraaper")
	WebElement tbl_payoff;
	
	@FindBy(how = How.XPATH, using="//i[@class = 'fa fa-save']")
	WebElement btn_Save;
	
	public void searchDoctor(String sDoctorName) throws Exception
	{
		// Waiting till loading the page
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sDoctorName);
		Utils.waitUntilAngularFinishHttpCalls();
		String xpath = ".//ul[@class='dropdown-menu ng-isolate-scope']//a";//div[@id='SearchTexttxt']//span[1]
		Utils.waitForElement(By.xpath(xpath));
		
		searchbox.sendKeys(Keys.TAB);
		Log.info("      Doctor payoff report --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	public void verifyPayOffAmount(String Discountpercentage)
	{
		List<WebElement> tablebody = tbl_payoff.findElements(By.tagName("tbody"));
		String ServiceAmountpath,DoctorPayOffpath;
		if(tablebody.size()<=2)
		{
			ServiceAmountpath = ".//tbody["+(tablebody.size()-1)+"]/tr[4]/td[3]";
			DoctorPayOffpath = ".//tbody["+(tablebody.size()-1)+"]/tr[4]/td[5]";
		}
		else
		{
			ServiceAmountpath = ".//tbody["+(tablebody.size()-1)+"]/tr[3]/td[3]";
			DoctorPayOffpath = ".//tbody["+(tablebody.size()-1)+"]/tr[3]/td[5]";	
		}
		WebElement ServiceAmount = tbl_payoff.findElement(By.xpath(ServiceAmountpath));
		WebElement DoctorPayOff = tbl_payoff.findElement(By.xpath(DoctorPayOffpath));
		float samount = Float.parseFloat(ServiceAmount.getText());
		Assert.assertTrue("Doctor payoff not showing correctly", DoctorPayOff.getText().startsWith(Float.toString(samount*Float.parseFloat(Discountpercentage)/100)));
		Log.info("      Doctor payoff amount verified. Showing correctly");
	}
	
	public void verifyPayOffAmount(String patientname, String Discountpercentage)
	{
		List<WebElement> tablebody = tbl_payoff.findElements(By.tagName("tbody"));
		String ServiceAmountpath,DoctorPayOffpath,Namepath;
		if(tablebody.size()<=2)
		{
			ServiceAmountpath = ".//tbody["+(tablebody.size()-1)+"]/tr[4]/td[3]";
			DoctorPayOffpath = ".//tbody["+(tablebody.size()-1)+"]/tr[4]/td[5]";
			Namepath = ".//tbody["+(tablebody.size()-1)+"]/tr[3]/td[3]";
		}
		else
		{
			ServiceAmountpath = ".//tbody["+(tablebody.size()-1)+"]/tr[3]/td[3]";
			DoctorPayOffpath = ".//tbody["+(tablebody.size()-1)+"]/tr[3]/td[5]";
			Namepath = ".//tbody["+(tablebody.size()-1)+"]/tr[2]/td[3]";
		}
		WebElement PatientName = tbl_payoff.findElement(By.xpath(Namepath));
		WebElement ServiceAmount = tbl_payoff.findElement(By.xpath(ServiceAmountpath));
		WebElement DoctorPayOff = tbl_payoff.findElement(By.xpath(DoctorPayOffpath));
		float samount = Float.parseFloat(ServiceAmount.getText());
		Assert.assertTrue("Patient details not showing in Doctor payoff Report",PatientName.getText().equalsIgnoreCase(patientname));
		Assert.assertTrue("Doctor payoff not showing correctly", DoctorPayOff.getText().startsWith(Float.toString(samount*Integer.parseInt(Discountpercentage)/100)));
		Log.info("      Doctor payoff amount verified. Showing correctly");
	}
	
	public String getPatientName()
	{
		List<WebElement> tablebody = tbl_payoff.findElements(By.tagName("tbody"));
		String Namepath;
		if(tablebody.size()<=2)
			Namepath = ".//tbody["+(tablebody.size()-1)+"]/tr[3]/td[3]";
		else
			Namepath = ".//tbody["+(tablebody.size()-1)+"]/tr[2]/td[3]";
		WebElement PatientName = tbl_payoff.findElement(By.xpath(Namepath));
		return PatientName.getText();
	}
	
	public void verifyOverAllAmount()
	{
		List<WebElement> tablebody = tbl_payoff.findElements(By.tagName("tbody"));
		String overallamountpath = ".//tbody["+tablebody.size()+"]/tr[2]/td[6]";
		WebElement overallamount = tbl_payoff.findElement(By.xpath(overallamountpath));
		float sumOfSubTotal = 0;
		for(int i = 1; i<tablebody.size();i++)
		{
			String payoffbodyrowpath = ".//*[@id='tblWraaper']/tbody["+i+"]/tr";
			List<WebElement> payoffbody = driver.findElements(By.xpath(payoffbodyrowpath));
			String subtotalpath = payoffbodyrowpath+"["+payoffbody.size()+"]/td[6]";
			WebElement subtotal = driver.findElement(By.xpath(subtotalpath));
			sumOfSubTotal = sumOfSubTotal+Float.parseFloat(subtotal.getText());
		}
		Assert.assertTrue("Overall amount not matched with sum of subtotals",overallamount.getText().startsWith(Float.toString(sumOfSubTotal)));
		Log.info("      OverAll amount verified. Showing correctly");
	}
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Doctor Payoff Report --> Click action performed on Save button");
	}
}
