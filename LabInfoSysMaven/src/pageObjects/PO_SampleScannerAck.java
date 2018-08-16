package pageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class PO_SampleScannerAck 
{
	WebDriver driver;
	public PO_SampleScannerAck(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubject")
	WebElement searchbox;
	
	@FindBy(how = How.XPATH, using = "//table[@class='table service-table scroll']")
	WebElement tbl_AckList;
	
	@FindBy(how = How.XPATH, using = "//table[@class='table service-table scroll']//tr")
	List<WebElement> tbl_AckListRows;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_Refresh;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='modernAlert']/p")
	WebElement alertbox;
	
	public String getAlertMsg() throws Exception
	{
		Utils.waitForElement(By.xpath(".//*[@id='modernAlert']/p"));
		String msg = alertbox.getText();
		return msg;
	}
	public void acceptAlert() throws Exception
	{
		Utils.waitForElement(By.xpath("//input[@id='txtalert']"));
		Log.info("      Alert shown as '"+getAlertMsg()+"'. Alert accepted");
		driver.findElement(By.xpath("//input[@id='txtalert']")).click();
	}
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Click action performed on Bulk Entry Save Button");
	}
	
	public void clickRefresh()
	{
		btn_Refresh.click();
		Log.info("      Click action performed on Bulk Entry Refresh Button");
	}

	public void searchBarcode(String Barcode) throws Exception
	{
		Utils.waitForElement(searchbox);
		searchbox.clear();
		searchbox.sendKeys(Barcode);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath1 = ".//div[@id='SearchSubject']//span[1]";
		Utils.waitForElement(By.xpath(xpath1));
		
		String xpath = ".//*[text()='"+Barcode+"']";
		driver.findElement(By.xpath(xpath)).click();
		}
		catch(Exception e)
		{
			Log.error("      Sample Acknowledgement --> Barcode not listed in universal search"+e.getMessage());
			throw new Exception("Sample Acknowledgement --> Barcode not listed in universal search",e);
		}
		Log.info("      Sample Acknowledgement --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void AcknowledgeAllSamples(List<String> Barcode) throws Exception
	{
		for(int i = 0; i<Barcode.size();i++)
		{
			searchbox.clear();
			if(!Barcode.get(i).equals("Yet to be processed"))
			{
				searchbox.sendKeys(Barcode.get(i));
				String xpath = ".//*[text()='"+Barcode.get(i)+"']";

				Utils.waitForElement(By.xpath(xpath));
				driver.findElement(By.xpath(xpath)).click();
				Utils.waitUntilAngularFinishHttpCalls();

				if(tbl_AckListRows.size()>2)
				{
					clickSave();
					Utils.waitUntilAngularFinishHttpCalls();
					Assert.assertEquals(getAlertMsg(),"Saved Successfully","*** Alert not showing as expected after sample acknowledge ***");
					acceptAlert();
					Log.info("      Samples Acknowledged  for barcode : "+Barcode.get(i));
				}
			}
		}
		Log.info("      Sample Ack --> All samples received by technician");
	}
}
