package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
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

public class PO_SampleOutsource 
{
	WebDriver driver;
	
	public PO_SampleOutsource(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.XPATH, using=".//select[@ng-model='VendorName']")
	WebElement drpdwn_OutsourcingTo;
	
	@FindBy(how = How.XPATH, using=".//input[@ng-model='IsnonOutSourcecheck']")
	WebElement chkbx_IsnonOutSourcedTest;
	
	@FindBy(how = How.XPATH, using=".//table[@class='table service-table scroll']")
	WebElement tbl_outsourceDetails;
	
	@FindBy(how = How.XPATH, using=".//table[@class='table service-table scroll']//th")
	List<WebElement> tbl_outsourceDetailsHeaders;
	
	@FindBy(how = How.XPATH, using=".//table[@class='table service-table scroll']/tbody/tr")
	List<WebElement> tbl_outsourceDetailsRows;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_SampleOutsource;
	
	public void selectOutsourcingTo(String VendorName)
	{
		Select option = new Select(drpdwn_OutsourcingTo);
		option.selectByVisibleText(VendorName);
//		Log.info("      Click action performed on Sample Outsource button");
	}
	
	public void viewNonOutsourcedTest()
	{
		if(!chkbx_IsnonOutSourcedTest.isSelected())
			chkbx_IsnonOutSourcedTest.click();
	}
	
	public void viewOutsourcedTest()
	{
		if(chkbx_IsnonOutSourcedTest.isSelected())
			chkbx_IsnonOutSourcedTest.click();
	}
	
	public String isInvestigationDisplayed(String PatientName, String sServiceName) throws Exception
	{
		int patientNamePosition=0, InvestigationNamePosition=0;
		String  Status = "FALSE";
		for(int j=1;j<tbl_outsourceDetailsHeaders.size();j++)
		{
			if("Patient Name".equalsIgnoreCase(tbl_outsourceDetailsHeaders.get(j).getText()))
				patientNamePosition=j+1;
			
			else if("Investigation Name".equalsIgnoreCase(tbl_outsourceDetailsHeaders.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
				break;
			}
		}
		
		for(int i=2;i<=tbl_outsourceDetailsRows.size();i++)
		{
			String patientNamePath = "./tbody/tr["+i+"]/td["+patientNamePosition+"]";
			WebElement patientName = tbl_outsourceDetails.findElement(By.xpath(patientNamePath));
			
			if(patientName.getText().trim().equalsIgnoreCase(PatientName)) 
			{
				String Investigationpath = "./tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
				WebElement Investigation = tbl_outsourceDetails.findElement(By.xpath(Investigationpath));
				if(Investigation.getText().equalsIgnoreCase(sServiceName))
				{
					Status = "TRUE";
					break;
				}
			}
		}
		return Status;
	}
	
	public String isPatientDisplayed(String PatientName) throws Exception
	{
		int patientNamePosition=0;
		String isPatientShowing="FALSE";
		for(int j=1;j<tbl_outsourceDetailsHeaders.size();j++)
		{
			if("Patient Name".equalsIgnoreCase(tbl_outsourceDetailsHeaders.get(j).getText()))
			{
				patientNamePosition=j+1;
				break;
			}
		}
		if(tbl_outsourceDetailsRows.size()>=3)
		{
			for(int i=2;i<=tbl_outsourceDetailsRows.size();i++)
			{
				String patientNamePath = "./tbody/tr["+i+"]/td["+patientNamePosition+"]";
				WebElement patientName = tbl_outsourceDetails.findElement(By.xpath(patientNamePath));
			
				if(patientName.getText().trim().equalsIgnoreCase(PatientName)) 
				{
					isPatientShowing = "TRUE";
					break;
				}
			}
		}
		return isPatientShowing;
	}
	
	public void selectPatient(String PatientName) throws Exception
	{
		int patientNamePosition=0, selectPosition=1;
		
		String isPatientShowing=null;
		for(int j=1;j<tbl_outsourceDetailsHeaders.size();j++)
		{
/*			if(" ".equalsIgnoreCase(tbl_outsourceDetailsHeaders.get(j).getText()))
				selectPosition=j+1;
*/			if("Patient Name".equalsIgnoreCase(tbl_outsourceDetailsHeaders.get(j).getText()))
			{
				patientNamePosition=j+1;
				break;
			}
		}
		
		for(int i=2;i<=tbl_outsourceDetailsRows.size();i++)
		{
			String patientNamePath = "./tbody/tr["+i+"]/td["+patientNamePosition+"]";
			WebElement patientName = tbl_outsourceDetails.findElement(By.xpath(patientNamePath));
			
			if(patientName.getText().trim().equalsIgnoreCase(PatientName)) 
			{
				isPatientShowing = "1";
				String selectpath = "./tbody/tr["+i+"]/td["+selectPosition+"]/input";
				WebElement ele = tbl_outsourceDetails.findElement(By.xpath(selectpath));
				ele.click();
			}
		}
		Assert.assertEquals(isPatientShowing,"1","*** Patient name not showing in the list ***");
	}
	
	public void clickSampleOutsource() throws Exception
	{
		btn_SampleOutsource.click();
		Log.info("      Click action performed on Sample Outsource button");
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.keyCtrlW();
	}
}
