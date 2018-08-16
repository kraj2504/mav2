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

import utility.Log;
import utility.Utils;

public class PO_BulkEntry 
{
	WebDriver driver;
	public PO_BulkEntry(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubjectBulk")
	WebElement searchbox;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how=How.XPATH, using=".//table[@class='table service-table scroll']/tbody//td[10]//span")
	List<WebElement> RefereceRange;
	
	@FindBy(how=How.XPATH, using = ".//table[@class='table service-table scroll']")
	WebElement tbl_patientDetails;
	
	@FindBy(how=How.XPATH, using = "//table[@class = 'table service-table scroll']/tbody/tr[2]/td[2]/input")
	WebElement record_patientDetails;
	
	@FindBy(how=How.XPATH, using = ".//fieldset[2]/div[1]/table")
	WebElement tbl_ResultEntry;
	
	@FindBy(how=How.XPATH,using=".//input[@ng-model='group.GroupRecollect']")
	WebElement chkbx_recollect;
	
	@FindBy(how=How.XPATH,using=".//input[@ng-model='group.GroupRecheck']")
	WebElement chkbx_recheck;
	
	public void clickSave()
	{
		Utils.scrollIntoView(btn_Save);
		btn_Save.click();
		Log.info("      Click action performed on Bulk Entry Save Button");
	}

	public void searchRecord(String sFirstName) throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		try
		{
		String xpath = ".//div[@id='SearchSubjectBulkEntry']//span[1]";
		Utils.waitForElement(By.xpath(xpath));
		}
		catch(Exception e)
		{
			Log.error("      Bulk Entry --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Bulk Entry --> Patient not listed in universal search",e);
		}
		Utils.keyEnter();
		Utils.waitUntilAngularFinishHttpCalls();
//		Utils.waitForElement(By.id("f_1"));
		Log.info("      Bulk Entry --> Search completed");
	}
	
	public void enterResult1() throws Exception
	{
		List<String> strings = new ArrayList<String>();
		for(WebElement e : RefereceRange)
		    strings.add(e.getText());

		for(int j=1;j<=RefereceRange.size();j++)
		{
			String RangeValue = strings.get(j-1);
			if(RangeValue.length()>1)
			{
				if(RangeValue.contains("-"))
				{
				int posi = RangeValue.indexOf("-");
				RangeValue = RangeValue.substring(0, posi);
				break;
				}
				else
				{
					RangeValue = "No";
				}
			}
			else
				RangeValue = "60";

			List<WebElement> result = driver.findElements(By.xpath(".//*[@ng-model='a.ResultValue']"));
			WebElement ele = result.get(j-1);
			ele.sendKeys(RangeValue);
		}	
		Log.info("      Results Entered");
	}
	
	public void enterResult() throws Exception
	{
		List<String> strings = new ArrayList<String>();
		for(WebElement e : RefereceRange)
		    strings.add(e.getText());
		for(int j=1;j<=RefereceRange.size();j++)
		{   
			String RangeValue = strings.get(j-1);
			if(RangeValue.length()>1)
			{
				if(RangeValue.contains("-"))
				{
					int posi = RangeValue.indexOf("-");
					RangeValue = RangeValue.substring(0, posi);
				}
				else
				{
					RangeValue = "4";
				}
			}
			else
				RangeValue = "60";

			List<WebElement> result = driver.findElements(By.xpath(".//*[@ng-model='a.ResultValue']"));
			WebElement ele = result.get(j-1);
			if(ele.getTagName().equals("input"))
			{
				ele.sendKeys(RangeValue);//RangeValue
			}
			else if(ele.getTagName().equals("select"))
			{
				Select value=new Select(ele);
				
				value.selectByIndex(1);
			}
		}	
		List<WebElement> cbc = driver.findElements(By.xpath(".//fieldset[2]/div[1]/table/tbody/tr/td[1]/span"));
		for(WebElement e : cbc)
		{
			int x=0;
			if(e.getText().equalsIgnoreCase("Complete Blood Count"))
			{
				x=1;
				List<WebElement> rows = tbl_ResultEntry.findElements(By.tagName("tr"));
				int k = 0;
				for(int i=2;i<=rows.size();i++)
				{
					String rowpath = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]";
					WebElement row = driver.findElement(By.xpath(rowpath));
					List<WebElement> cell = row.findElements(By.tagName("span"));
					if(cell.size()>0)
					{
						String cellpath = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]//span";
						List<WebElement> WCells = driver.findElements(By.xpath(cellpath));
						for(WebElement WCell : WCells)
						{
							if(WCell.getText().equalsIgnoreCase("NEUTROPHILS"))
							{
								String resultEntryPath = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]/td[5]//input";
								WebElement resultEntry = driver.findElement(By.xpath(resultEntryPath));
								resultEntry.clear();
								resultEntry.sendKeys("30");
								k = k+1;							
								break;
							}
							else if(WCell.getText().equalsIgnoreCase("LYMPHOCYTES"))
							{
								String resultEntryPath = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]/td[5]//input";
								WebElement resultEntry = driver.findElement(By.xpath(resultEntryPath));
								resultEntry.clear();
								resultEntry.sendKeys("60");
								k = k+1;
							}
							else if(WCell.getText().equalsIgnoreCase("EOSINOPHILS"))
							{
								String resultEntryPath = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]/td[5]//input";
								WebElement resultEntry = driver.findElement(By.xpath(resultEntryPath));
								resultEntry.clear();
								resultEntry.sendKeys("3");
								k = k+1;
							}
							else if(WCell.getText().equalsIgnoreCase("MONOCYTES"))
							{
								String resultEntryPath = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]/td[5]//input";
								WebElement resultEntry = driver.findElement(By.xpath(resultEntryPath));
								resultEntry.clear();
								resultEntry.sendKeys("6");
								k = k+1;
							}
							else if(WCell.getText().equalsIgnoreCase("BASOPHILS"))
							{
								String resultEntryPath = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]/td[5]//input";
								WebElement resultEntry = driver.findElement(By.xpath(resultEntryPath));
								resultEntry.clear();
								resultEntry.sendKeys("1");
								k = k+1;
								Utils.waitUntilAngularFinishHttpCalls();
								String resultEntryPath1 = ".//fieldset[2]/div[1]/table/tbody/tr["+i+"]/td[6]";
								driver.findElement(By.xpath(resultEntryPath1)).click();
							}
							if(k==5)
							{
								break;
							}
						}
						if(k==5)
						{
							break;
						}
					}
					if(k==5)
					{
						break;
					}
				}
				if(x==1)
					break;
			}
		}
		Log.info("      Results Entered");
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
}