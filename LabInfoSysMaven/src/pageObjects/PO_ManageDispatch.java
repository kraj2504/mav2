package pageObjects;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

public class PO_ManageDispatch 
{
	WebDriver driver;
	public PO_ManageDispatch(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "printed")
	WebElement chkbx_ShowPrintedReport;
	
	@FindBy(how = How.ID, using = "FilterType")
	WebElement drpdwn_ReportFilter;
	
	@FindBy(how = How.ID, using = "SearchSubjectDispatch")
	WebElement searchbox;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-inbox']")
	WebElement btn_EMail;
	
	@FindBy(how=How.XPATH, using="//a[contains(text(),\"Dispatch\")]")
	WebElement btn_Dispatch;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_Refresh;
	
	@FindBy(how = How.XPATH, using = ".//div[@id='patient-wrap']/table[@class = 'table service-table scroll']") //*[@class='table service-table scroll']
	WebElement tbl_patientDetails;
	
	@FindBy(how = How.XPATH, using = ".//div[@id='patient-wrap']/table[@class = 'table service-table scroll']/tbody/tr")
	List<WebElement> tbl_patientDetailsRows;

	@FindBy(how = How.XPATH, using = ".//div[@id='patient-wrap']/table[@class = 'table service-table scroll']/tbody/tr/th")
	List<WebElement> tbl_patientDetailsHeaders;
	
	public void searchRecord(String sFirstName) throws Exception
	{
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.clear();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//div[@id='SearchSubjectDetails']/ul//li";//div[@id='SearchSubjectDetails']//span[1]
		Utils.waitForElement(By.xpath(xpath));
		
		List<WebElement> searchresult = driver.findElements(By.xpath(xpath));
		
		String xpath2 = ".//div[@id='SearchSubjectDetails']/ul//li["+searchresult.size()+"]";

		driver.findElement(By.xpath(xpath2)).click();
		}
		catch(Exception e)
		{
			Log.error("      Manage dispatch --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Manage dispatch --> Patient not listed in universal search",e);
		}
		Log.info("      Manage Dispatch --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public int getTableRowSize()
	{
		List<WebElement> patientDetailsTableRows = tbl_patientDetails.findElements(By.tagName("tr"));
		return patientDetailsTableRows.size();
	}
	
	public void clickEmail()
	{
		btn_EMail.click();
		Log.info("      Click action performed on EMail Button");
	}
	
	public void clickRefresh() throws Exception
	{
		btn_Refresh.click();
		Utils.waitUntilAngularFinishHttpCalls();
		Log.info("      ManageDispatch --> Click action performed on Refresh Button");
	}
	
	public void clickDispatch() throws Exception
	{
		String parentWindow = driver.getWindowHandle();
		Utils.waitUntilAngularFinishHttpCalls();
		btn_Dispatch.click();
		Utils.keyEsc();
		Log.info("      Click action performed on Dispatch Button");
		Utils.waitUntilAngularFinishHttpCalls();
		
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while(i1.hasNext())
		{
			String child_window = i1.next();
			if(!parentWindow.equalsIgnoreCase(child_window))
			{
				Utils.keyCtrlW();
				driver.switchTo().window(parentWindow);
			}
		}
	}
	
	public void selectFilter(String filtername) throws Exception
	{
		try
		{
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Utils.waitForElement(drpdwn_ReportFilter);
		Select value=new Select(drpdwn_ReportFilter);
		value.selectByVisibleText(filtername);
		String selectedOption = new Select(drpdwn_ReportFilter).getFirstSelectedOption().getText();
		Log.info("      Selected Title : "+selectedOption);
		Log.info("      ShowPrintedReport' selected as Report filter");
		Utils.waitUntilAngularFinishHttpCalls();
		}
		catch(Exception e)
		{
			Log.error("ManageDispatch --> selectFilter : ");
		}
	}
	
	public void selectPatient() throws Exception
	{
		int rowsize = tbl_patientDetailsRows.size();
		try
		{
		String path = ".//div[@id='patient-wrap']/table[@class = 'table service-table scroll']/tbody/tr["+(rowsize-1)+"]/td[3]/input";//table[@class = 'table service-table scroll']/tbody/tr["+rowsize+"]/td[2]/input
		WebElement record_patientDetails = driver.findElement(By.xpath(path));
		record_patientDetails.click();
		}
		catch(Exception e)
		{
			Log.error("Cannot able to select the patient or Patient details not showing in dispatch screen: "+e.getMessage());
			throw new Exception("Cannot able to select the patient or Patient details not showing in dispatch screen: ",e);
		}
		Log.info("      Patient selected for Dispatching report");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public int verifyPatientName(String sFirstName)
	{
		tbl_patientDetails.click();
		List<WebElement> th=tbl_patientDetails.findElements(By.tagName("th"));
		//Get column position of Patient Name
		int col_position=0;
		for(int j=0;j<th.size();j++)
		{
			if("Patient Name".equalsIgnoreCase(th.get(j).getText()))
			{
				col_position=j+1;
				break;
			}
		}
		String path = "//tr/td["+col_position+"]";
		//Get patient name and verify
  		List<WebElement> FirstColumns = tbl_patientDetails.findElements(By.xpath(path));
		int i = 0,rcount=0;
		for(WebElement cell: FirstColumns)
		{
			String name = cell.getText();
			int sposition = name.indexOf(".");
			int eposition = name.length();
			String pname = name.substring(sposition+1, eposition);
			if(pname.trim().equalsIgnoreCase(sFirstName))
	      	{
				i = 1;
				rcount=rcount+1;
	      	}
		}
//		Assert.assertTrue(rcount<=1,"***Duplicate records found in Dispatch report***");
		return i;
	}
	
	public void verifyModeOfDispatch(String expModOfDispatch)
	{
		int visitIDPosition=0,ModeOfDispatchPosition=0;
		for(int j=0;j<tbl_patientDetailsHeaders.size();j++)
		{
			if("Visit ID".equalsIgnoreCase(tbl_patientDetailsHeaders.get(j).getText()))
			{
				visitIDPosition=j+1;
			}
			else if("Mode of Dispatch".equalsIgnoreCase(tbl_patientDetailsHeaders.get(j).getText()))
			{
				ModeOfDispatchPosition=j+1;
			}
		}
		int max = 0, maxIndex = 0;
		for(int i=2;i<=tbl_patientDetailsRows.size();i++)
		{
			String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
			WebElement visitID =  tbl_patientDetails.findElement(By.xpath(visitIDpath));
			String val = visitID.getText();
			
			val = val.substring(6, 10);
			int ival = Integer.parseInt(val);
			if (ival>max)
			{
				max = ival;
				maxIndex = i;
			}
		}
		String selectpath = "//*[@class='table service-table scroll']//tr["+maxIndex+"]/td["+ModeOfDispatchPosition+"]";
		WebElement ele =  driver.findElement(By.xpath(selectpath));

		Log.info("      GrossAmount in patient list is : "+ele.getText());
		String ModeOfDispatch = ele.getText();
		
		Assert.assertEquals(ModeOfDispatch, expModOfDispatch,"*** Mode of Dispatch not showing correctly ***");
	}
}
