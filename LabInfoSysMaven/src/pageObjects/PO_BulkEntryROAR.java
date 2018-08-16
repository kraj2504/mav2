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

public class PO_BulkEntryROAR 
{
	private static final Boolean FALSE = false;

	WebDriver driver;
	public PO_BulkEntryROAR(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchSubject")
	WebElement searchbox;
	
	@FindBy(how = How.XPATH, using = ".//select[@ng-model='LoadServiceList.AnalyteID']")
	WebElement drpdwn_analyte;
	
	@FindBy(how = How.XPATH, using = ".//input[@ng-model='ObjSingleService.SampleName']")
	WebElement txtbx_sampleType;
	
	@FindBy(how = How.XPATH, using = ".//select[@ng-model='ObjSingleService.CultureReportCode']")
	WebElement drpdwn_cultureReport;
	
	@FindBy(how = How.XPATH, using = ".//select[@ng-model='ObjSingleService.ReportType']")
	WebElement drpdwn_resultType;
	
	@FindBy(how = How.XPATH, using = ".//input[@id='ColonyCount']")
	WebElement txtbx_ColonyCount;
	
	@FindBy(how = How.XPATH, using = ".//textarea[@id='ColonyCount']")
	WebElement txtarea_resultTypeDesc;
	
	@FindBy(how = How.ID, using = "GramStains")
	WebElement txtarea_microscopy;
	
	@FindBy(how = How.XPATH, using = ".//textarea[@ng-model='ObjSingleService.Comments']")
	WebElement txtarea_notes;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[4]//select[@ng-model='ObjOrganism.OrganismID']")
	WebElement drpdwn_isolatedOrganism1;

	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[5]//select[@ng-model='ObjOrganism.OrganismID']")
	WebElement drpdwn_isolatedOrganism2;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[6]//select[@ng-model='ObjOrganism.OrganismID']")
	WebElement drpdwn_isolatedOrganism3;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[4]//select[@ng-model='ObjOrganism.OrganismSubTypeID']")
	WebElement drpdwn_organismSubType1;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[5]//select[@ng-model='ObjOrganism.OrganismSubTypeID']")
	WebElement drpdwn_organismSubType2;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[6]//select[@ng-model='ObjOrganism.OrganismSubTypeID']")
	WebElement drpdwn_organismSubType3;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[3]//button")
	WebElement btn_addisolatedOrganism2;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[5]//button[2]")
	WebElement btn_deleteIsolatedOrganism2;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[5]//button[1]")
	WebElement btn_addisolatedOrganism3;
	
	@FindBy(how = How.XPATH, using = ".//fieldset[3]/div[6]//button[2]")
	WebElement btn_deleteIsolatedOrganism3;
	
	@FindBy(how=How.XPATH, using=".//table[@class='table service-table scroll']/tbody//td[10]//span")
	List<WebElement> RefereceRange;
	
	@FindBy(how=How.XPATH, using = ".//table[@class='table service-table tbl2 scroll newScroll']")
	WebElement tbl_Antibiotic;
	
	@FindBy(how=How.XPATH, using = ".//table[1]//tr[2]//select")
	WebElement drpdwn_antibioticSucceptibilityTest1;
	
	@FindBy(how=How.XPATH, using = ".//input[@ng-model='ObjSingleService.DraftSave']")
	WebElement chkbx_ResultEntered;
	
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
		try
		{
		String xpath = ".//*[@id]/a/span[1]";
		Utils.waitForElement(By.xpath(xpath));
		}
		catch(Exception e)
		{
			Log.error("      Bulk Entry ROAR --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Bulk Entry ROAR --> Patient not listed in universal search",e);
		}
		Utils.keyEnter();
		Utils.waitForElement(drpdwn_analyte);
		Log.info("      Bulk Entry ROAR --> Search completed");
	}
	
	public void selectResultEntered()
	{
		if(!chkbx_ResultEntered.isSelected())
		{
			chkbx_ResultEntered.click();
			Log.info("      Bulk Entry ROAR --> Result Entered check box selected");
		}
	}
	
	public void selectAnalyte(String sServiceName) throws Exception
	{
		Utils.waitForElement(drpdwn_analyte);
		Select option = new Select(drpdwn_analyte);
		if(!option.getFirstSelectedOption().getText().trim().equalsIgnoreCase(sServiceName))
		{
			option.selectByVisibleText(sServiceName);
			Utils.waitUntilAngularFinishHttpCalls();
			if(txtbx_sampleType.isEnabled() == FALSE)
				driver.findElement(By.id("btnServicesYes")).click();
		}
		Log.info("      BulkEntry ROAR --> Analyte selected");
	}
	public void verifyAnalyte(String sServiceName) throws Exception
	{
		Utils.waitForElement(drpdwn_analyte);
		String selectedOption = new Select(drpdwn_analyte).getFirstSelectedOption().getText();
		Assert.assertEquals(selectedOption.trim(), sServiceName, "Analyte name not in selected mode or not selected in BulkEntry ROAR ");
		Log.info("      Analyte name showing correctly in BulkEntry ROAR");
	}
	
	public void verifySampleType(String sSampleType) throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		Assert.assertEquals(txtbx_sampleType.getAttribute("value"), sSampleType, "Sample type of the analyte showing wrongly");
		Log.info("      Sample type showing correctly in BulkEntry ROAR");
	}
	
	public void selectCultureReport(String reportValue) throws Exception
	{
		Utils.waitForElement(drpdwn_cultureReport);
		Select option = new Select(drpdwn_cultureReport);
		option.selectByVisibleText(reportValue);
		Log.info("      BulkEntry ROAR --> CultureReport selected");
	}
	
	public void selectResultType(String resultTypeValue) throws Exception
	{
		Utils.waitForElement(drpdwn_resultType);
		Utils.waitUntilAngularFinishHttpCalls();
		Select option = new Select(drpdwn_resultType);
		option.selectByVisibleText(resultTypeValue);
		Log.info("      BulkEntry ROAR --> ResultType selected");
	}
	
	public void enterColonyCount(String colonyCount) throws Exception
	{
		Utils.waitForElement(txtbx_ColonyCount);
		txtbx_ColonyCount.clear();
		txtbx_ColonyCount.sendKeys(colonyCount);
		Log.info("      BulkEntry ROAR --> ColonyCount entered");
	}
	
	public void selectIsolatedOrganism1(String isolatedOrganism) throws Exception
	{
		Utils.waitForElement(drpdwn_isolatedOrganism1);
		Select option = new Select(drpdwn_isolatedOrganism1);
		option.selectByVisibleText(isolatedOrganism);
		Log.info("      BulkEntry ROAR --> IsolatedOrganism1 selected");
	}
	public void selectAntibioticForOrganism1(String antibiotic) throws Exception
	{
		Utils.waitForElement(drpdwn_antibioticSucceptibilityTest1);
		Select option = new Select(drpdwn_antibioticSucceptibilityTest1);
		option.selectByVisibleText(antibiotic);
		Log.info("      BulkEntry ROAR --> AntibioticForOrganism1 selected");
	}
}