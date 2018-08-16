package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import utility.Log;
import utility.Utils;

public class PO_OutsourceAcknowledge 
{
	WebDriver driver;
	
	public PO_OutsourceAcknowledge(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using="SearchSubjectBulk")
	WebElement drpdwn_OutsourcingTo;

	@FindBy(how = How.XPATH, using=".//table[@class='table service-table scroll']")
	WebElement tbl_Service;
	
	@FindBy(how = How.XPATH, using=".//table[@class='table service-table scroll']//th")
	List<WebElement> tbl_ServiceHeaders;
	
	@FindBy(how = How.XPATH, using=".//table[@class='table service-table scroll']//tr")
	List<WebElement> tbl_ServiceRows;
	
	@FindBy(how = How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	public String isPatientDisplayed(String PatientName) throws Exception
	{
		int patientNamePosition=0;
		String isPatientShowing="FALSE";
		for(int j=1;j<tbl_ServiceHeaders.size();j++)
		{
			if("Patient Name".equalsIgnoreCase(tbl_ServiceHeaders.get(j).getText()))
			{
				patientNamePosition=j+1;
				break;
			}
		}
		if(tbl_ServiceRows.size()>=2)
		{
			for(int i=2;i<=tbl_ServiceRows.size();i++)
			{
				String patientNamePath = "./tbody/tr["+i+"]/td["+patientNamePosition+"]";
				WebElement patientName = tbl_Service.findElement(By.xpath(patientNamePath));
			
				if(patientName.getText().trim().equalsIgnoreCase(PatientName)) 
				{
					isPatientShowing = "TRUE";
					break;
				}
			}
		}
		
		return isPatientShowing;
	}
	
	public String isInvestigationDisplayed(String PatientName, String sServiceName) throws Exception
	{
		int patientNamePosition=0, InvestigationNamePosition=0;
		String  Status = "FALSE";
		for(int j=1;j<tbl_ServiceHeaders.size();j++)
		{
			if("Patient Name".equalsIgnoreCase(tbl_ServiceHeaders.get(j).getText()))
				patientNamePosition=j+1;
			
			else if("Service Name".equalsIgnoreCase(tbl_ServiceHeaders.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
				break;
			}
		}
		
		for(int i=2;i<=tbl_ServiceRows.size();i++)
		{
			String patientNamePath = "./tbody/tr["+i+"]/td["+patientNamePosition+"]";
			WebElement patientName = tbl_Service.findElement(By.xpath(patientNamePath));
			
			if(patientName.getText().trim().equalsIgnoreCase(PatientName)) 
			{
				String Investigationpath = "./tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
				WebElement Investigation = tbl_Service.findElement(By.xpath(Investigationpath));
				if(Investigation.getText().equalsIgnoreCase(sServiceName))
				{
					Status = "TRUE";
					break;
				}
			}
		}
		return Status;
	}
	
	public void selectPatient(String PatientName) throws Exception
	{
		int patientNamePosition=0, selectPosition=1;
		
		String isPatientShowing=null;
		for(int j=1;j<tbl_ServiceHeaders.size();j++)
		{
			if("Patient Name".equalsIgnoreCase(tbl_ServiceHeaders.get(j).getText()))
			{
				patientNamePosition=j+1;
				break;
			}
		}
		
		for(int i=2;i<=tbl_ServiceRows.size();i++)
		{
			String patientNamePath = "./tbody/tr["+i+"]/td["+patientNamePosition+"]";
			WebElement patientName = tbl_Service.findElement(By.xpath(patientNamePath));
			
			if(patientName.getText().trim().equalsIgnoreCase(PatientName)) 
			{
				isPatientShowing = "1";
				String selectpath = "./tbody/tr["+i+"]/td["+selectPosition+"]/input";
				WebElement ele = tbl_Service.findElement(By.xpath(selectpath));
				ele.click();
			}
		}
		Assert.assertEquals(isPatientShowing,"1","*** Patient name not showing in the list ***");
	}
	
	public void clickSave() throws Exception
	{
		btn_Save.click();
		Log.info("      Outsource acknowledge --> Click action performed on Save button");
	}
}
