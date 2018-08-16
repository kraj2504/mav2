package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Log;

public class PO_DueClearanceMIS 
{
WebDriver driver;
	
	public PO_DueClearanceMIS(WebDriver driver)
	{
		this.driver=driver;
	}

	 @FindBy(how=How.XPATH,using="//a[text()='Today']")
	   WebElement btn_Today;
	   
	   @FindBy(how=How.XPATH,using="//a[text()='This Week']")
	   WebElement btn_ThisWeek ;
	   
	   @FindBy(how=How.XPATH,using="//a[text()='This Month']")
	   WebElement btn_ThisMonth;
	   
	   @FindBy(how=How.XPATH,using="//a[text()='Custom']")
	   WebElement btn_Custom;
	   
	   @FindBy(how=How.ID,using="FromDate")
	   WebElement txtbx_FromDate;
	   
	   @FindBy(how=How.ID,using="ToDate")
	   WebElement txtbx_ToDate;
	
	  @FindBy(how=How.XPATH, using="//i[@class='fa fa-refresh']")
	 	WebElement btn_Refresh;
	   
	  @FindBy(how=How.XPATH,using="//button[@ng-model='Butt']")
	   WebElement btn_SearchRecords;
	   
	  @FindBy(how=How.XPATH,using="//div[@id='patient-wrap']")
	   WebElement tbl_MISReport ;
	
	  public int getBillTableRows()
	   {
		  List<WebElement> getBillTableRows = tbl_MISReport.findElements(By.tagName("tr"));
		  return getBillTableRows.size();
	   }
	  
	  public String getPatientname()
	   {
		   int rowsize = getBillTableRows();
		   List<WebElement> th= tbl_MISReport.findElements(By.tagName("th"));
			int NamePosition=0;
			for(int j=0;j<th.size();j++)
			{	
				if("Name".equalsIgnoreCase(th.get(j).getText()))
					{
					NamePosition=j+1;
					//System.out.println("ServiceName  : "+ th.get(j).getText());
					}
			}	
			
		   String NamePath="//div[@id='patient-wrap']/table/tbody/tr["+(rowsize-1)+"]/td["+NamePosition+"]";
		   String patientName=driver.findElement(By.xpath(NamePath)).getText();
		   return patientName; 
	   }
	   
	  public String getBillAmount(String sFirstName)
	   {
		   String PtName=getPatientname();
		   List<WebElement> patientBillTableRows = tbl_MISReport.findElements(By.tagName("tr"));
			
			//Get column position
			List<WebElement> th=tbl_MISReport.findElements(By.tagName("th"));
			int BillAmountPosition=0;
			String billValue1 = "";
			
			for(int i=2;i<patientBillTableRows.size();i++)
			{
				
			for(int j=0;j<th.size();j++)
			{	
				
				if("Bill Amount".equalsIgnoreCase(th.get(j).getText()))
					{
					BillAmountPosition=j+1;
					}
			}
			if(PtName.equalsIgnoreCase(sFirstName))
			   {
				  
				
				    String billpath =("//div[@id='patient-wrap']/table/tbody/tr["+i+"]/td["+BillAmountPosition+"]");
				    String billValue =driver.findElement(By.xpath(billpath)).getText();
				   Log.info("      Check the net Value: " + billValue);
				   
				   if(billValue.length()==8)
					{
					  
						String temp2 = billValue.substring(0, 1);
						String temp3 = billValue.substring(2, 8);
						billValue = temp2+temp3;
					}
					else if(billValue.length()==9)
					{
						
						String temp2 = billValue.substring(0, 2);
						String temp3 = billValue.substring(3, 9);
						billValue = temp2+temp3;
					} 
				   billValue1 = billValue;
			   }
			
	   }
			return billValue1;
	   } 
	  
	  public String getTotalPaidAmount(String sFirstName)
	   {
		   String PtName=getPatientname();
		   List<WebElement> patientBillTableRows = tbl_MISReport.findElements(By.tagName("tr"));
			
			//Get column position
			List<WebElement> th=tbl_MISReport.findElements(By.tagName("th"));
			int TotalPaidAmountPosition=0;
			String totalPaidValue1 = "";
			
			for(int i=2;i<patientBillTableRows.size();i++)
			{
				
			for(int j=0;j<th.size();j++)
			{	
				
				if("Total Paid Amount".equalsIgnoreCase(th.get(j).getText()))
					{
					TotalPaidAmountPosition=j+1;
					}
			}
			if(PtName.equalsIgnoreCase(sFirstName))
			   {				
				    String totalPaidpath =("//div[@id='patient-wrap']/table/tbody/tr["+i+"]/td["+TotalPaidAmountPosition+"]");
				    String totalPaidValue =driver.findElement(By.xpath(totalPaidpath)).getText();
				   Log.info("      Check the net Value: " + totalPaidValue);
				   if(totalPaidValue.length()==8)
					{
						String temp2 = totalPaidValue.substring(0, 1);
						String temp3 = totalPaidValue.substring(2, 8);
						totalPaidValue = temp2+temp3;
					}
					else if(totalPaidValue.length()==9)
					{
						String temp2 = totalPaidValue.substring(0, 2);
						String temp3 = totalPaidValue.substring(3, 9);
						totalPaidValue = temp2+temp3;
					} 
				   totalPaidValue1 = totalPaidValue;
			   }
			
	   }
			return totalPaidValue1;
	   }
	  
	  public String getDuePaidAmount(String sFirstName)
	   {
		   String PtName=getPatientname();
		   List<WebElement> patientBillTableRows = tbl_MISReport.findElements(By.tagName("tr"));
			
			//Get column position
			List<WebElement> th=tbl_MISReport.findElements(By.tagName("th"));
			int DuePaidAmountPosition=0;
			String duePaidValue1 = "";
			
			for(int i=2;i<patientBillTableRows.size();i++)
			{
				
			for(int j=0;j<th.size();j++)
			{	
				
				if("DuePaid Amount".equalsIgnoreCase(th.get(j).getText()))
					{
					DuePaidAmountPosition=j+1;
					}
			}
			if(PtName.equalsIgnoreCase(sFirstName))
			   {
				   
				    String duePaidpath =("//div[@id='patient-wrap']/table/tbody/tr["+i+"]/td["+DuePaidAmountPosition+"]");
				    String duePaidValue =driver.findElement(By.xpath(duePaidpath)).getText();
				   Log.info("      Check the net Value: " + duePaidValue);
				   if(duePaidValue.length()==8)
					{
						String temp2 = duePaidValue.substring(0, 1);
						String temp3 = duePaidValue.substring(2, 8);
						duePaidValue = temp2+temp3;
					}
					else if(duePaidValue.length()==9)
					{
						String temp2 = duePaidValue.substring(0, 2);
						String temp3 = duePaidValue.substring(3, 9);
						duePaidValue = temp2+temp3;
					} 
				   duePaidValue1 = duePaidValue;
			   }
			
	   }
			return duePaidValue1;
	   } 
	  
	  public String getDiscountAmount(String sFirstName)
	   {
		   String PtName=getPatientname();
		   List<WebElement> patientBillTableRows = tbl_MISReport.findElements(By.tagName("tr"));
			
			//Get column position
			List<WebElement> th=tbl_MISReport.findElements(By.tagName("th"));
			int DiscountAmountPosition=0;
			String discountValue1 = "";
			
			for(int i=2;i<patientBillTableRows.size();i++)
			{
				
			for(int j=0;j<th.size();j++)
			{	
				
				if("Discount Amount".equalsIgnoreCase(th.get(j).getText()))
					{
					DiscountAmountPosition=j+1;
					}
			}
			if(PtName.equalsIgnoreCase(sFirstName))
			   {
				   
				    String discountpath =("//div[@id='patient-wrap']/table/tbody/tr["+i+"]/td["+DiscountAmountPosition+"]");
				    String discountValue =driver.findElement(By.xpath(discountpath)).getText();
				   Log.info("      Check the net Value: " + discountValue);
				   if(discountValue.length()==8)
					{
						String temp2 = discountValue.substring(0, 1);
						String temp3 = discountValue.substring(2, 8);
						discountValue = temp2+temp3;
					}
					else if(discountValue.length()==9)
					{
						String temp2 = discountValue.substring(0, 2);
						String temp3 = discountValue.substring(3, 9);
						discountValue = temp2+temp3;
					} 
				   discountValue1 = discountValue;
			   }
			
	   }
			return discountValue1;
	   }  
	  
	  public String getDueAmount(String sFirstName)
	   {
		   String PtName=getPatientname();
		   List<WebElement> patientBillTableRows = tbl_MISReport.findElements(By.tagName("tr"));
			
			//Get column position
			List<WebElement> th=tbl_MISReport.findElements(By.tagName("th"));
			int DueAmountPosition=0;
			String dueValue1 = "";
			
			for(int i=2;i<patientBillTableRows.size();i++)
			{
				
			for(int j=0;j<th.size();j++)
			{	
				
				if("Due Amount".equalsIgnoreCase(th.get(j).getText()))
					{
					DueAmountPosition=j+1;
					}
			}
			if(PtName.equalsIgnoreCase(sFirstName))
			   {
				   
				    String duepath =("//div[@id='patient-wrap']/table/tbody/tr["+i+"]/td["+DueAmountPosition+"]");
				    String dueValue =driver.findElement(By.xpath(duepath)).getText();
				   Log.info("      Check the net Value: " + dueValue);
				   if(dueValue.length()==8)
					{
						String temp2 = dueValue.substring(0, 1);
						String temp3 = dueValue.substring(2, 8);
						dueValue = temp2+temp3;
					}
					else if(dueValue.length()==9)
					{
						String temp2 = dueValue.substring(0, 2);
						String temp3 = dueValue.substring(3, 9);
						dueValue = temp2+temp3;
					} 
				   dueValue1 = dueValue;
			   }
			
	   }
			return dueValue1;
	   }  
	  
	  
}
