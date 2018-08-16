package pageObjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Log;
import utility.Utils;

public class PO_HomeCollectionAssigning 
{
	WebDriver driver;
	
	public PO_HomeCollectionAssigning(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using="SearchBooking_value")
	WebElement searchbox;
	
	@FindBy(how = How.ID, using="txt_Phel_1")
	WebElement drpdwn_Phlebotomist;
	
	@FindBy(how = How.ID, using="txt_VDate_1")
	WebElement dtctrl_VisitDateTime;
	
	@FindBy(how = How.ID, using="txtCollectionCode_1")
	WebElement drpdwn_CollectionOption;
	
	@FindBy(how = How.ID, using="txtReason_1")
	WebElement txtbx_Reason;
	
	@FindBy(how = How.XPATH, using="//table[@class='table service-table scroll']")
	WebElement tbl_patientDetails;
	
	@FindBy(how = How.XPATH, using="//i[@class = 'fa fa-save']")
	WebElement btn_Save;
	
	
	public void searchRecord(String sFirstName) throws Exception
	{
		// Waiting till loading the page
		WebDriverWait WaitVar1 = new WebDriverWait(driver,10);
		WaitVar1.until(ExpectedConditions.visibilityOf(searchbox));
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		
		String xpath = ".//*[@id='SearchBooking_dropdown']//span[1]";//div[@id='SearchBooking_value']//span[1]
		Utils.waitForElement(By.xpath(xpath));
		
		searchbox.sendKeys(Keys.TAB);
		//String xpath = ".//*[text()='"+sFirstName+"']";
		//driver.findElement(By.xpath(".//*[@id='typeahead-235-9463-option-0']/a")).click();
		Log.info("      Home Collection --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectPatient() throws Exception
	{
		tbl_patientDetails.click();
		List<WebElement> patientDetailsTableRows = tbl_patientDetails.findElements(By.tagName("tr"));
		int rowsize = patientDetailsTableRows.size();
		String path = "//table[@class = 'table service-table scroll']/tbody/tr["+(rowsize-5)+"]/td[2]/input";
		WebElement selectPatientDetails = driver.findElement(By.xpath(path));
		selectPatientDetails.click();
		
		Log.info("      Home collection --> Patient selected");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectPhlebotomist(String sPhlebotomist)
	{
		Select value=new Select(drpdwn_Phlebotomist);
		value.selectByVisibleText(sPhlebotomist);
		String selectedOption = new Select(drpdwn_Phlebotomist).getFirstSelectedOption().getText();
		Log.info("      Selected Phlebotomist : "+selectedOption);
	}
	
	public void selectCollectionOption(String sCollectionOption)
	{
		Select value=new Select(drpdwn_CollectionOption);
		value.selectByVisibleText(sCollectionOption);
		String selectedOption = new Select(drpdwn_CollectionOption).getFirstSelectedOption().getText();
		Log.info("      Selected CollectionOption : "+selectedOption);
	}
	
	public void EnterReason(String sReason)
	{
		txtbx_Reason.sendKeys(sReason);
		Log.info("      Given Reason : "+ txtbx_Reason.getAttribute("value"));
	}
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Home Collection --> Click action performed on Save button");
	}
	
	@SuppressWarnings("deprecation")
	public void selectVisitDate() throws Exception
	{
		Date date = new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMMMMMM");
		String currentmonth = formatter.format(date);
		currentmonth = currentmonth.substring(0, 3);
		int clicked = 0;
		dtctrl_VisitDateTime.click();
		
		WebElement monthtable = driver.findElement(By.xpath(".//table[@class='table service-table scroll']/tbody/tr[2]/td[11]/div/div/div/table"));
		
		List<WebElement> monthTableRows = monthtable.findElements(By.tagName("tr"));
		for(WebElement monthrow:monthTableRows)
		{
			List<WebElement> monthcells = monthrow.findElements(By.tagName("td"));
			for(WebElement monthcell:monthcells)
			{
				if(monthcell.getAttribute("innerHTML").equalsIgnoreCase(currentmonth))
				{	
					Utils.waitUntilAngularFinishHttpCalls();
					monthcell.click();
					List<WebElement> dateTableRows = monthtable.findElements(By.tagName("tr"));
					for(WebElement daterow:dateTableRows)
					{
						List<WebElement> datecells = daterow.findElements(By.tagName("td"));
						for(WebElement datecell:datecells)
						{
							if(datecell.getAttribute("innerHTML").equalsIgnoreCase(Integer.toString(date.getDate())))
							{
								
							//	driver.findElement(By.xpath(".//div[2]/ui-view/div/div/section/div/div[1]/fieldset[4]/div/table/tbody/tr[2]/td[11]/div/div/div/table/tbody/tr[5]/td[4]")).click();
								datecell.click();
								List<WebElement> hourTableRows = monthtable.findElements(By.tagName("tr"));
								for(WebElement hourrow:hourTableRows)
								{
									List<WebElement> hourcells = hourrow.findElements(By.tagName("td"));
									for(WebElement hourcell:hourcells)
									{
										if(hourcell.getAttribute("innerHTML").startsWith(Integer.toString(date.getHours())))
										{
											hourcell.click();
											
											List<WebElement> minTableRows = monthtable.findElements(By.tagName("tr"));
											for(WebElement minrow:minTableRows)
											{
												List<WebElement> mincells = minrow.findElements(By.tagName("td"));
												for(WebElement mincell:mincells)
												{
													if(mincell.getAttribute("innerHTML").endsWith(Integer.toString(date.getMinutes())))
													{
														mincell.click();
														clicked = 1;
													/*
														List<WebElement> secTableRows = monthtable.findElements(By.tagName("tr"));
														for(WebElement secrow:secTableRows)
														{
															List<WebElement> seccells = secrow.findElements(By.tagName("td"));
															for(WebElement seccell:seccells)
															{
																if(seccell.getAttribute("innerHTML").equalsIgnoreCase(Integer.toString(date.getSeconds())))
																{
																	seccell.click();
																//	clicked = 1;
																	break;
																}
																if(clicked==1)
																	break;
															}
															if(clicked==1)
																break;
														}
														if(clicked==1)
															break;
												*/	}
													if(clicked==1)
														break;
												}
												if(clicked==1)
													break;
											}
											//clicked = 1;
											if(clicked==1)
												break;
										}
										if(clicked==1)
											break;
									}
									if(clicked==1)
										break;
								}
								if(clicked==1)
									break;
							}
							if(clicked==1)
								break;
						}
						if(clicked==1)
							break;
					}
					if(clicked==1)
						break;
				}
				if(clicked==1)
					break;
			}
			if(clicked==1)
				break;
		}
		Log.info("Visit date selected");	
	}
}
