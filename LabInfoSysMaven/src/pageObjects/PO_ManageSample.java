package pageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import utility.Log;
import utility.Utils;

public class PO_ManageSample 
{
	WebDriver driver;
	public PO_ManageSample(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubjectManagesample")
	WebElement searchbox;
	
	@FindBy(how = How.XPATH, using = "//input[@ng-model='ManageSampl.objSampleTestNameList.ColectedDate']")
	WebElement SampleCollectedDate;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_CollectSample;
	
	@FindBy(how=How.XPATH, using = "//table[@class = 'table service-table tbl1 scroll']")
	WebElement tbl_patientDetails;

	@FindBy(how=How.XPATH, using = "//table[@class='table service-table']")
	WebElement tbl_investigationDetails;
	
	@FindBy(how=How.XPATH, using = "//table[@class='table service-table']/tbody/tr/th")
	List<WebElement> tbl_investigationDetailsHeaders;
	
	@FindBy(how=How.XPATH, using = "//table[@class='table service-table']/tbody/tr")
	List<WebElement> tbl_investigationDetailsRows;
	
	public void clickCollectSample() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		btn_CollectSample.click();
		Log.info("      Click action performed on Collect Sample button");
	}
	
	public String getSampleCollectedDate()
	{
		Log.info("      Sample collected date is : "+SampleCollectedDate.getAttribute("value"));
		return SampleCollectedDate.getAttribute("value");
	}
	
	public void clickPatientDetailsTable()
	{
		tbl_patientDetails.click();
	}
	
	public int getPatientDetailsTableRowSize()
	{
		List<WebElement> patientDetailsTableRows = tbl_patientDetails.findElements(By.tagName("tr"));
		return patientDetailsTableRows.size();
	}
	
	public void selectPatient() throws Exception
	{
		tbl_patientDetails.click();
		List<WebElement> patientDetailsTableRows = tbl_patientDetails.findElements(By.tagName("tr"));
		int rowsize = patientDetailsTableRows.size();
		
		String path1 = "//table[@class = 'table service-table tbl1 scroll']/tbody/tr["+rowsize+"]/td";
		List<WebElement> patientDetailsTableFirstRowColumnsSize = driver.findElements(By.xpath(path1));
		
		if(patientDetailsTableFirstRowColumnsSize.size()>1)
		{
			String path = "//table[@class = 'table service-table tbl1 scroll']/tbody/tr["+rowsize+"]/td[3]/input";
			try
			{
				WebElement record_patientDetails = driver.findElement(By.xpath(path));
				record_patientDetails.click();
			}
			catch(Exception e)
			{
				Log.error("Manage sample --> Cannot able to select patient : "+e.getMessage());
				throw new Exception("Manage sample --> Cannot able to select patient",e);
			}
			Log.info("      Patient selected for collecting sample");
			Utils.waitUntilAngularFinishHttpCalls();
		}
		else
		{
			Log.info("      Patient name not listed in list for collecting sample");
			Assert.assertTrue(1==0, "Patient name not listed in list for collecting sample");
		}
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
		String xpath = ".//div[@id='SearchSubjectDetails']//span[1]";
		Utils.waitForElement(By.xpath(xpath));

		searchbox.sendKeys(Keys.TAB);
		}
		catch(Exception e)
		{
			Log.error("      Manage sample --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Manage sample --> Patient not listed in universal search",e);
		}
		Log.info("      Manage Sample --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public String getRowColor(String sServiceName) throws Exception
	{
		int InvestigationPosition=0;
		String actualColor = "rgba(0, 0, 0, 0)";
		
		for(int j=1;j<tbl_investigationDetailsHeaders.size();j++)
		{
			if("Investigation".equalsIgnoreCase(tbl_investigationDetailsHeaders.get(j).getText()))
				InvestigationPosition=j+1;
		}
		
		for(int i=2;i<=tbl_investigationDetailsRows.size();i++)
		{
			String servicepath = "./tbody/tr["+i+"]/td["+InvestigationPosition+"]";
			WebElement service = tbl_investigationDetails.findElement(By.xpath(servicepath));
			
			if(service.getText().trim().equalsIgnoreCase(sServiceName)) 
			{
				String servicepath1 = "./tbody/tr["+i+"]";
				WebElement service1 = tbl_investigationDetails.findElement(By.xpath(servicepath1));
				actualColor = service1.getCssValue("background-color");
			}
		}
		return actualColor;
	}

/*	public void selectSampleCollectedDateUsingCalendar() throws Exception
	{
		SampleCollectedDate.click();
		WebElement monthtable = driver.findElement(By.xpath("//div[2]/ui-view/div/div/section/div/div/div/div[2]/fieldset/div/div/table/tbody/tr[1]/th[4]/div/div/div/table"));
		
		List<WebElement> monthTableRows = monthtable.findElements(By.tagName("tr"));
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMMMMMM");
		String currentmonth = formatter.format(date);
		currentmonth = currentmonth.substring(0, 3);
		
		for(WebElement monthrow:monthTableRows)
		{
			List<WebElement> monthcells = monthrow.findElements(By.tagName("td"));
			for(WebElement monthcell:monthcells)
			{
				if(monthcell.getAttribute("innerHTML").equalsIgnoreCase(currentmonth))
				{	
					Thread.sleep(1000);
					
					monthcell.click();
					List<WebElement> dateTableRows = monthtable.findElements(By.tagName("tr"));
					for(WebElement daterow:dateTableRows)
					{
						List<WebElement> datecells = daterow.findElements(By.tagName("td"));
						for(WebElement datecell:datecells)
						{
							if(datecell.getAttribute("innerHTML").equalsIgnoreCase("19"))
							{
								datecell.click();
								List<WebElement> hourTableRows = monthtable.findElements(By.tagName("tr"));
								for(WebElement hourrow:hourTableRows)
								{
									List<WebElement> hourcells = hourrow.findElements(By.tagName("td"));
									for(WebElement hourcell:hourcells)
									{
										if(hourcell.getAttribute("innerHTML").equalsIgnoreCase("17:00"))
										{
											hourcell.click();
											List<WebElement> minutesTableRows = monthtable.findElements(By.tagName("tr"));
											for(WebElement minutesrow:minutesTableRows)
											{
												List<WebElement> minutescells = minutesrow.findElements(By.tagName("td"));
												for(WebElement minutescell:minutescells)
												{
													if(minutescell.getAttribute("innerHTML").equalsIgnoreCase("17:48"))
													{
														minutescell.click();
														List<WebElement> secondsTableRows = monthtable.findElements(By.tagName("tr"));
														for(WebElement secondsrow:secondsTableRows)
														{
															List<WebElement> secondscells = secondsrow.findElements(By.tagName("td"));
															for(WebElement secondscell:secondscells)
															{
																if(secondscell.getAttribute("innerHTML").equalsIgnoreCase("01"))
																{
																	secondscell.click();
																	break;
																}
															}
														}
														break;
													}
												}
											}
											break;
										}
									}
								}
								break;
							}
						}
					}
					break;	
				}	
			}
		}
	}*/
}
