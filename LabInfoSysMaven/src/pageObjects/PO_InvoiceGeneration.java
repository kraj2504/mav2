package pageObjects;



import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Constant;
import utility.Log;
import utility.Utils;

public class PO_InvoiceGeneration 
{
   WebDriver driver;
   
   public PO_InvoiceGeneration(WebDriver driver)
   {
	   this.driver=driver;
   }
   

   @FindBy(how=How.XPATH,using="//input[@ng-model='TxtSearchClients']")
   WebElement Txtbx_ClientName;
   
   @FindBy(how=How.XPATH, using="//form[@id='objInvoice']/fieldset[3]/div/div/div[1]")
   WebElement lbl_clientDetails;
   
   @FindBy(how=How.XPATH, using="//form[@id='objInvoice']/fieldset[3]/div/div/div[2]/div[1]")
   WebElement lbl_ContactNo;
   
   @FindBy(how=How.XPATH, using ="//form[@id='objInvoice']/fieldset[3]/div/div/div[2]/div[2]")
   WebElement lbl_EmailID;
   
   @FindBy(how=How.XPATH,using="//form[@name='frmInvoice.objInvoice']/fieldset[3]/div/div/div[3]")
   WebElement lbl_Address;
   
   @FindBy(how=How.XPATH, using="//textarea[@ng-model='objInvoice.InvoiceNotes']")
   WebElement txtbx_InvoiceNotes;
   
   @FindBy(how=How.XPATH, using = "//table[@class='table service-table scroll']")
   WebElement tbl_BillDetails;
   
   @FindBy(how=How.XPATH, using = "//table[@class='table service-table scroll']/tbody/tr")
   List<WebElement> tbl_BillDetailsRows;
   /*
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[1]")
   WebElement lbl_SNo;
  
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[2]")
   WebElement lbl_TransactionDate;
   
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[3]")
   WebElement lbl_BillNo;
   
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[4]")
   WebElement lbl_PatientName;
   
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[5]")
   WebElement BillAmount;
   
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[6]")
   WebElement DiscountAmount;
   
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[7]")
   WebElement NetAmount;
   
   @FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[8]")
   WebElement CollectedAmount;
   
   */
   @FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[1]")
   WebElement Total;
   
   @FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[2]")
   WebElement TotalBillAmount;
   
   @FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[3]")
   WebElement TotalDiscountAmount;
   
   @FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[4]")
   WebElement TotalNetAmount;
   
   @FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[5]")
   WebElement TotalCollectedAmount;
   
   @FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[6]")
   WebElement TotalDueAmount;
       
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
   
   @FindBy(how=How.XPATH,using="//button[@ng-model='Butt']")
   WebElement btn_SearchRecords;
   
   @FindBy(how=How.XPATH,using="//i[@class='fa fa-save']")
   WebElement btn_Save;
   
   @FindBy(how=How.XPATH,using="//div[@class='box-body']/a[5]")
   WebElement btn_Clear;
   
   @FindBy(how=How.XPATH,using="//div[@class='box-body']/a[6]")
   WebElement btn_Refresh;
   
   public void clickSave()
	{
	     btn_Save.click();
		Log.info("      Click action performed on save Button");
	}
   public void selectClientName(String sReferralName)
   {
	   Txtbx_ClientName.sendKeys(sReferralName);
	   Log.info("      Select the client name: " + sReferralName);
	   Txtbx_ClientName.sendKeys(Keys.ENTER);
   }
      public void selectCustom()
   {
	  
	   btn_Custom.sendKeys(Keys.ENTER);
   }
   
   public void enterInvoiceMessage(String sInvoiceMessage )
   {
	   txtbx_InvoiceNotes.sendKeys(sInvoiceMessage);
	   Log.info("      Enter the InvoiceMessage: " + sInvoiceMessage);
	   txtbx_InvoiceNotes.sendKeys(Keys.TAB);
   }
   
   public String getPatientname() throws Exception
   {
	   Utils.waitUntilAngularFinishHttpCalls();
	   int rowsize = tbl_BillDetailsRows.size();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int PatientNamePosition=0;
		for(int j=0;j<th.size();j++)
		{	
			if("Patient Name".equalsIgnoreCase(th.get(j).getText()))
				{
				PatientNamePosition=j+1;
				break;
				}
		}	
	   String patientNamePath="//div[@class='col-sm-12']/table/tbody/tr["+(rowsize-4)+"]/td["+PatientNamePosition+"]";
	   String patientName=driver.findElement(By.xpath(patientNamePath)).getText();
	   return patientName;
   }
   
  
public void verifyTotalNetAmount()
   {
	   List<WebElement> patientServiceTableRows =tbl_BillDetails .findElements(By.tagName("tr"));
	   List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
		int NetAmountPosition=0;
		double  Total = 0;
		//For loop for number of rows
		for(int i=2;i<=(patientServiceTableRows.size()-4);i++)
		{	
		// For loop for number of columns
		for(int j=1;j<=(th.size()-2);j++)
		{
			if("Net Amount".equalsIgnoreCase(th.get(j).getText()))
			{
			NetAmountPosition=j+1;
			String first_path ="//div[@class='col-sm-12']/table/tbody/tr[";
			
			String second_path = "]/td[";
		
			String third_path= "]";
			
			String path = first_path+i+second_path+(j+1)+third_path;
			System.out.println("path-"+ path);
			System.out.println("4");
			String netValue =driver.findElement(By.xpath(path)).getText();
			System.out.println("netValue:"+ netValue);
			Total = Total + Double.parseDouble(netValue);
			break;
			}
			
			System.out.println("out for loop");	
		}
		System.out.println("out j loop");
		//Row_count=i+1;
		}	
		String netamt= TotalNetAmount.getText();
		double net = Double.parseDouble(netamt);
		System.out.println("Totalnetamt:"+net);
		if(net == Total)
		{
			System.out.println("***Net amount sum is equal to the total Net amount  ***");
		}
   }
   
   public void verifyTotalBillAmount()
   {
	   List<WebElement> patientServiceTableRows =tbl_BillDetails .findElements(By.tagName("tr"));
	   List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
	   System.out.println("row_count:"+ patientServiceTableRows.size());
		int BillAmountPosition=0;
		double  Total = 0;
		
		//For loop for number of rows
		for(int i=2;i<=(patientServiceTableRows.size()-4);i++)
		{	
			System.out.println("inside i loop");
		// For loop for number of columns
		for(int j=1;j<=(th.size()-2);j++)
		{
			if("Bill Amount".equalsIgnoreCase(th.get(j).getText()))
			{
				BillAmountPosition=j+1;
			System.out.println("BillAmountPosition  : "+ BillAmountPosition);
			
			String first_path ="//div[@class='col-sm-12']/table/tbody/tr[";
			
			String second_path = "]/td[";
		
			String third_path= "]";
			
			String path = first_path+i+second_path+(j+1)+third_path;
			System.out.println("path-"+ path);
			System.out.println("4");
			String billValue =driver.findElement(By.xpath(path)).getText();
			System.out.println("billValue:"+ billValue);
			Total = Total + Double.parseDouble(billValue);			
			}
			System.out.println("out for loop");	
		}
		System.out.println("out j loop");
		//Row_count=i+1;
		}
		String billamt= TotalBillAmount.getText();
		double bill = Double.parseDouble(billamt);
		System.out.println("Totalnetamt:"+bill);
		if(bill == Total)
		{
			System.out.println("***Bill amount sum is equal to the total bill amount  ***");
			//Assert.assertEquals("***Net amount sum match assertTrue  ***",Double.compareTo(net, Total));
			//Assert.assertEquals(Double.doubleToLongBits(net), Double.doubleToLongBits(Total));
		}
   }
   
    

   /*
		//System.out.println("Sum of bill Amount " + Total);	
		//WebElement TotalbillAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[1]"));
		String billamt= TotalBillAmount.getText();
		System.out.println("billamt " + billamt);
		double net = Double.parseDouble(billamt);
		if(net == Total){
			System.out.println("***Bill amount sum is equal to the total bill amount  ***");
		}
		//Assert.assertEquals("***Amount not updated as expected***",Total,billamt); 
		//Assert.assertTrue(message, condition);
		// Assert.assertTrue("***Amount not updated as expected***",billamt.equals(Total));
   }
  
   
   public void verifyTotalDiscountAmount()
   {
	   List<WebElement> patientServiceTableRows =tbl_BillDetails .findElements(By.tagName("tr"));
	   List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
	   System.out.println("row_count:"+ patientServiceTableRows.size());
		int DisAmountPosition=0;
		double  Total = 0;
		
		//For loop for number of rows
		for(int i=2;i<=(patientServiceTableRows.size()-4);i++)
		{	
			System.out.println("inside i loop");
		// For loop for number of columns
		for(int j=1;j<=(th.size()-2);j++)
		{
			if("Discount Amount".equalsIgnoreCase(th.get(j).getText()))
			{
				DisAmountPosition=j+1;
			System.out.println("DisAmountPosition  : "+ DisAmountPosition);
			
			String first_path ="//div[@class='col-sm-12']/table/tbody/tr[";
			
			String second_path = "]/td[";
		
			String third_path= "]";
			
			String path = first_path+i+second_path+(j+1)+third_path;
			System.out.println("path-"+ path);
			System.out.println("4");
			String disValue =driver.findElement(By.xpath(path)).getText();
			System.out.println("disValue:"+ disValue);
			Total = Total + Double.parseDouble(disValue);			
			}
			System.out.println("out for loop");	
		}
		System.out.println("out j loop");
		//Row_count=i+1;
		}	
		//System.out.println("Sum of Net Amount " + Total);	
		//WebElement TotalNetAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[3]"));
		String disamt= TotalDiscountAmount.getText();
		double dis = Double.parseDouble(disamt);
		if(dis == Total){
			System.out.println("***Dis amount sum is equal to the total Discount amount  ***");
		}
		
		System.out.println("disamt " + disamt);
		//Assert.assertEquals("***Amount not updated as expected***",disamt,Total); 
		//Assert.assertTrue("***Amount not updated as expected***",disamt.equals(Total));
   }
  
   */ 
   public void verifyTotalCollectedAmount()
   {
	   List<WebElement> patientServiceTableRows =tbl_BillDetails .findElements(By.tagName("tr"));
	   List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
	   System.out.println("row_count:"+ patientServiceTableRows.size());
		int collectAmountPosition=0;
		double  Total = 0;
		
		//For loop for number of rows
		for(int i=2;i<=(patientServiceTableRows.size()-4);i++)
		{	
			System.out.println("inside i loop");
		// For loop for number of columns
		for(int j=1;j<=(th.size()-2);j++)
		{
			if("Collected Amount".equalsIgnoreCase(th.get(j).getText()))
			{
				collectAmountPosition=j+1;
			System.out.println("CollectAmountPosition  : "+ collectAmountPosition);
			
			String first_path ="//div[@class='col-sm-12']/table/tbody/tr[";
			
			String second_path = "]/td[";
		
			String third_path= "]";
			
			String path = first_path+i+second_path+(j+1)+third_path;
			System.out.println("path-"+ path);
			System.out.println("4");
			String collectValue =driver.findElement(By.xpath(path)).getText();
			System.out.println("netValue:"+ collectValue);
			Total = Total + Double.parseDouble(collectValue);			
			}
			System.out.println("out for loop");	
		}
		System.out.println("out j loop");
		//Row_count=i+1;
		}	
		System.out.println("Sum of col Amount " + Total);	
		//WebElement TotalNetAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[3]"));
		String colamt=TotalCollectedAmount .getText();
		double col = Double.parseDouble(colamt);
		if(col == Total){
			System.out.println("***col amount sum is equal to the total Collected amount  ***");
		}
		
		//System.out.println("netamt " + disamt);
		//Assert.assertEquals("***Amount not updated as expected***",disamt,Total); 
		//Assert.assertTrue("***Amount not updated as expected***",disamt.equals(Total));
   }
   
   
  
   /*
   public void verifyTotalNetAmount()
   {
	   int rowsize = getBillTableRows();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int NetAmountPosition=0;
		int sumIt=0;
		for(int i=0;i<=rowsize;i++)
		{
		for(int j=0;j<th.size();j++)
		{	
			if("Net Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				NetAmountPosition=j+1;
				System.out.println("NetAmountPosition  : "+ NetAmountPosition);
				}
			String NetAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(rowsize-1)+"]/td["+NetAmountPosition+"]";
			   String NetAmount=driver.findElement(By.xpath(NetAmountPath)).getText();
			   sumIt = sumIt+ Integer.parseInt(NetAmount);
		}	
		}
		
		WebElement TotalDisAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[4]"));
		String netamt= TotalDisAmount.getText();
		Assert.assertEquals("***Amount not updated as expected***",netamt,sumIt);
   }
  */ 
  
   public void verifyTotalDueAmount() throws Exception
   {
	   System.out.println("Inside verifyTotalDueAmount" );
	   int rowsize = tbl_BillDetailsRows.size();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int DueAmountPosition=0;
		float fdueAmt=0;
		
		for(int j=1;j<th.size();j++)
		{	
			System.out.println("2" );
			if("Due Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				System.out.println("3");
				DueAmountPosition=j+1;
				System.out.println(" DueAmountPosition: "+ DueAmountPosition);
				}
		}
		for(int i=2;i<rowsize-3;i++)
		{
			
			System.out.println("row size1 is : "+ rowsize);
			String DueAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+DueAmountPosition+"]";
			System.out.println("DueAmountPath  : "+ DueAmountPath);
			String DueAmount=driver.findElement(By.xpath(DueAmountPath)).getText();
			System.out.println("DueAmount1  : "+ DueAmount);
			
			if(DueAmount.length()==8)
			{
				String temp2 = DueAmount.substring(0, 1);
				String temp3 = DueAmount.substring(2, 8);
				DueAmount = temp2+temp3;
			}
			else if(DueAmount.length()==9)
			{
				String temp2 = DueAmount.substring(0, 2);
				String temp3 = DueAmount.substring(3, 9);
				DueAmount = temp2+temp3;
			}
			
			//System.out.println("DueAmount  : "+ DueAmount);
			fdueAmt = fdueAmt+ Float.parseFloat(DueAmount);
			System.out.println("fdueAmt  : "+ fdueAmt);
			
		}		   
		String sdue=String.valueOf(fdueAmt);
		Thread.sleep(1000);
		//WebElement TotalDueAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[6]"));
		String dueamt= TotalDueAmount.getText();
	
		if(dueamt.length()==8)
		{
			String temp2 = dueamt.substring(0, 1);
			String temp3 = dueamt.substring(2, 8);
			dueamt = temp2+temp3;
		}
		else if(dueamt.length()==9)
		{
			String temp2 = dueamt.substring(0, 2);
			String temp3 = dueamt.substring(3, 9);
			dueamt = temp2+temp3;
		}
		System.out.println("VerifyTotaldueamt  : "+ dueamt);
		Assert.assertTrue("***Amount not updated as expected***",dueamt.startsWith(sdue));	
		System.out.println("completed verifyTotalDueAmount " );
   }
   
   public void verifyTotalNetAmount1()
   {
	   System.out.println("Inside verifyTotalNetAmount1" );
	   int rowsize = tbl_BillDetailsRows.size();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int NetAmountPosition=0;
		float fnetAmt=0;
		
		for(int j=1;j<th.size();j++)
		{	
			System.out.println("2" );
			if("Net Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				System.out.println("3");
				NetAmountPosition=j+1;
				System.out.println(" NetAmountPosition: "+ NetAmountPosition);
				break;
				}
		}
		for(int i=2;i<rowsize-3;i++)
		{
			System.out.println("row size1 is : "+ rowsize);
			String NetAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+NetAmountPosition+"]";
			String NetAmount=driver.findElement(By.xpath(NetAmountPath)).getText();
			
			if(NetAmount.length()==8)
			{
				String temp2 = NetAmount.substring(0, 1);
				String temp3 = NetAmount.substring(2, 8);
				NetAmount = temp2+temp3;
			}
			else if(NetAmount.length()==9)
			{
				String temp2 = NetAmount.substring(0, 2);
				String temp3 = NetAmount.substring(3, 9);
				NetAmount = temp2+temp3;
			}
			
			System.out.println("NetAmount  : "+ NetAmount);
			fnetAmt = fnetAmt+ Float.parseFloat(NetAmount);
			System.out.println("fnetAmt  : "+ fnetAmt);
		}		   
		String snet=String.valueOf(fnetAmt);
		//WebElement TotalNetAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[3]/b"));
		String netamt= TotalNetAmount.getText();
		if(netamt.length()==8)
		{
			String temp2 = netamt.substring(0, 1);
			String temp3 = netamt.substring(2, 8);
			netamt = temp2+temp3;
		}
		else if(netamt.length()==9)
		{
			String temp2 = netamt.substring(0, 2);
			String temp3 = netamt.substring(3, 9);
			netamt = temp2+temp3;
		}
		System.out.println("VerifyTotalNetamt  : "+ netamt);
		Assert.assertTrue("***Amount not updated as expected***",netamt.startsWith(snet));
		System.out.println("completed verifyTotalNetAmount1 " );
   }
   
   public void verifyTotalBillAmount1()
   {
	   System.out.println("Inside verifyTotalBillAmount1" );
	   int rowsize = tbl_BillDetailsRows.size();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int billAmountPosition=0;
		float fbillAmt=0;
		
		for(int j=1;j<th.size();j++)
		{	
			System.out.println("2" );
			if("Bill Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				System.out.println("3");
				billAmountPosition=j+1;
				System.out.println(" billAmountPosition: "+ billAmountPosition);
				break;
				}
		}
		for(int i=2;i<rowsize-3;i++)
		{
			System.out.println("row size1 is : "+ rowsize);
			String BillAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+billAmountPosition+"]";
			String billAmount=driver.findElement(By.xpath(BillAmountPath)).getText();
			if(billAmount.length()==8)
			{
				String temp2 = billAmount.substring(0, 1);
				String temp3 = billAmount.substring(2, 8);
				billAmount = temp2+temp3;
			}
			else if(billAmount.length()==9)
			{
				String temp2 = billAmount.substring(0, 2);
				String temp3 = billAmount.substring(3, 9);
				billAmount = temp2+temp3;
			}
			System.out.println("billAmount  : "+ billAmount);
			fbillAmt = fbillAmt+ Float.parseFloat(billAmount);
			System.out.println("fbillAmt  : "+ fbillAmt);
		}		   
		String sbill=String.valueOf(fbillAmt);
		//WebElement TotalbillAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[4]"));
		String billamt= TotalBillAmount.getText();
		if(billamt.length()==8)
		{
			String temp2 = billamt.substring(0, 1);
			String temp3 = billamt.substring(2, 8);
			billamt = temp2+temp3;
		}
		else if(billamt.length()==9)
		{
			String temp2 = billamt.substring(0, 2);
			String temp3 = billamt.substring(3, 9);
			billamt = temp2+temp3;
		}
		Assert.assertTrue("***Amount not updated as expected***",billamt.startsWith(sbill));
		System.out.println("completed verifyTotalBillAmount1 " );
   }
   
   public void verifyTotalCollectedAmount1()
   {
	   System.out.println("Inside verifyTotalCollectedAmount1" );
	   int rowsize = tbl_BillDetailsRows.size();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int collectedAmountPosition=0;
		float fcolAmt=0;
		
		for(int j=1;j<th.size();j++)
		{	
			System.out.println("2" );
			if("Collected Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				System.out.println("3");
				collectedAmountPosition=j+1;
				System.out.println(" billAmountPosition: "+ collectedAmountPosition);
				break;
				}
		}
		for(int i=2;i<rowsize-3;i++)
		{
			System.out.println("row size1 is : "+ rowsize);
			String colAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+collectedAmountPosition+"]";
			String colAmount=driver.findElement(By.xpath(colAmountPath)).getText();
			if(colAmount.length()==8)
			{
				String temp2 = colAmount.substring(0, 1);
				String temp3 = colAmount.substring(2, 8);
				colAmount = temp2+temp3;
			}
			else if(colAmount.length()==9)
			{
				String temp2 = colAmount.substring(0, 2);
				String temp3 = colAmount.substring(3, 9);
				colAmount = temp2+temp3;
			}
			System.out.println("colAmount  : "+ colAmount);
			fcolAmt = fcolAmt+ Float.parseFloat(colAmount);
			System.out.println("fcolAmt  : "+ fcolAmt);
			
		}		   
		String scol=String.valueOf(fcolAmt);
		//WebElement TotalAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[]"));
		String colamt= TotalCollectedAmount.getText();
		if(colamt.length()==8)
		{
			String temp2 = colamt.substring(0, 1);
			String temp3 = colamt.substring(2, 8);
			colamt = temp2+temp3;
		}
		else if(colamt.length()==9)
		{
			String temp2 = colamt.substring(0, 2);
			String temp3 = colamt.substring(3, 9);
			colamt = temp2+temp3;
		}
		Assert.assertTrue("***Amount not updated as expected***",colamt.startsWith(scol));
		System.out.println("completed verifyTotalCollectedAmount1 " );
   }
   
   public String getBillAmount(String sFirstName) throws Exception
   {
	   System.out.println("Inside getBillAmount" );
	   String PtName=getPatientname();
	   System.out.println("pt name:"+PtName);
		System.out.println("row size1 is : "+ tbl_BillDetailsRows.size());
		//Get column position
		List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
		int BillAmountPosition=0;
		String billValue1 = "";
		System.out.println(0);
		int k =0;
		for(int i=2;i<=(tbl_BillDetailsRows.size()-4);i++)
		{
			System.out.println(".1");
		for(int j=0;j<th.size();j++)
		{	
			System.out.println(".2");
			System.out.println("v:"+th.get(j).getText());
			if("Bill Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				BillAmountPosition=j+1;
				System.out.println("BillAmountPosition  : "+ BillAmountPosition);
				break;
				}
		}
		if(PtName.equalsIgnoreCase(sFirstName))
		   {
			   System.out.println(".3");
			    String billValuepath =("//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+BillAmountPosition+"]");
			    System.out.println("bill amount path:"+billValuepath);
			    String billValue =driver.findElement(By.xpath(billValuepath)).getText();
			    System.out.println("billValue1 : "+ billValue);
			   Log.info("      Check the bill Value: " + billValue);
			   System.out.println(4);
			   if(billValue.length()==8)
				{
				   System.out.println("5.");
					String temp2 = billValue.substring(0, 1);
					String temp3 = billValue.substring(2, 8);
					billValue = temp2+temp3;
				}
				else if(billValue.length()==9)
				{
					System.out.println("6.");
					String temp2 = billValue.substring(0, 2);
					String temp3 = billValue.substring(3, 9);
					billValue = temp2+temp3;
				} 
			   billValue1 = billValue;
		   }
		if(k==1)
			break;
		
   }
		System.out.println("billValue1 :"+billValue1 );
		System.out.println("completed getBillAmount " );
		return billValue1;
   }
   
   public String getNetAmount(String sFirstName) throws Exception
   {
	   System.out.println("Inside getNetAmount" );
	   String PtName=getPatientname();
		System.out.println("row size1 is : "+ tbl_BillDetailsRows.size());
		//Get column position
		List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
		int NetAmountPosition=0;
		String netValue1 = "";
		System.out.println(0);
		for(int i=2;i<=(tbl_BillDetailsRows.size()-4);i++)
		{
			System.out.println(1.);
		for(int j=0;j<th.size();j++)
		{	
			System.out.println(2.);
			if("Net Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				NetAmountPosition=j+1;
				System.out.println("NetAmountPosition  : "+ NetAmountPosition);
				break;
				}
		}
		if(PtName.equalsIgnoreCase(sFirstName))
		   {
			   System.out.println(3.);
			    String netValuepath =("//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+NetAmountPosition+"]");
			    String netValue =driver.findElement(By.xpath(netValuepath)).getText();
			    System.out.println("netValue : "+ netValue);
			   Log.info("      Check the net Value: " + netValue);
			   System.out.println(4);
			   if(netValue.length()==8)
				{
				   System.out.println(5.);
					String temp2 = netValue.substring(0, 1);
					String temp3 = netValue.substring(2, 8);
					netValue = temp2+temp3;
				}
				else if(netValue.length()==9)
				{
					System.out.println(6.);
					String temp2 = netValue.substring(0, 2);
					String temp3 = netValue.substring(3, 9);
					netValue = temp2+temp3;
				} 
			   netValue1 = netValue;
		   }
		
   }
		System.out.println("completed getNetAmount");
		return netValue1;
   }
   
   public String getDiscountAmount(String sFirstName) throws Exception
   {
	   String PtName=getPatientname();
		System.out.println("row size1 is : "+ tbl_BillDetailsRows.size());
		//Get column position
		List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
		int DiscountAmountPosition=0;
		String discountValue1 = "";
		System.out.println(0);
		for(int i=2;i<=(tbl_BillDetailsRows.size()-4);i++)
		{
			System.out.println(1.);
		for(int j=0;j<th.size();j++)
		{	
			System.out.println(2.);
			if("Discount Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				DiscountAmountPosition=j+1;
				System.out.println("DiscountAmountPosition  : "+ DiscountAmountPosition);
				break;
				}
		}
		if(PtName.equalsIgnoreCase(sFirstName))
		   {
			   System.out.println(3.);
			    String discountpath =("//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+DiscountAmountPosition+"]");
			    String disValue =driver.findElement(By.xpath(discountpath)).getText();
			    System.out.println("billValue1 : "+ disValue);
			   Log.info("      Check the bill Value: " + disValue);
			   System.out.println(4);
			   if(disValue.length()==8)
				{
				   System.out.println(5.);
					String temp2 = disValue.substring(0, 1);
					String temp3 = disValue.substring(2, 8);
					disValue = temp2+temp3;
				}
				else if(disValue.length()==9)
				{
					System.out.println(6.);
					String temp2 = disValue.substring(0, 2);
					String temp3 = disValue.substring(3, 9);
					disValue = temp2+temp3;
				} 
			   discountValue1 = disValue;
		   }
		
   }
		System.out.println("completed getDiscountAmount " );
		return discountValue1;
   }
  
   public String getDueAmount(String sFirstName) throws Exception
   {
	   System.out.println("Inside getDueAmount" );
	   String PtName=getPatientname();
		System.out.println("row size1 is : "+ tbl_BillDetailsRows.size());
		//Get column position
		List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
		int DueAmountPosition=0;
		String dueValue1 = "";
		System.out.println(0);
		for(int i=2;i<=(tbl_BillDetailsRows.size()-4);i++)
		{
			System.out.println(1.);
		for(int j=0;j<th.size();j++)
		{	
			System.out.println(2.);
			if("Due Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				DueAmountPosition=j+1;
				System.out.println("DueAmountPosition  : "+ DueAmountPosition);
				}
		}
		if(PtName.equalsIgnoreCase(sFirstName))
		   {
			   System.out.println(3.);
			    String duepath =("//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+DueAmountPosition+"]");
			    String dueValue =driver.findElement(By.xpath(duepath)).getText();
			    System.out.println("billValue1 : "+ dueValue);
			   Log.info("      Check the bill Value: " + dueValue);
			   System.out.println(4);
			   if(dueValue.length()==8)
				{
				   System.out.println(5.);
					String temp2 = dueValue.substring(0, 1);
					String temp3 = dueValue.substring(2, 8);
					dueValue = temp2+temp3;
				}
				else if(dueValue.length()==9)
				{
					System.out.println(6.);
					String temp2 = dueValue.substring(0, 2);
					String temp3 = dueValue.substring(3, 9);
					dueValue = temp2+temp3;
				} 
			   dueValue1 = dueValue;
		   }
		
   }	
		System.out.println("completed getDueAmount " );
		return dueValue1;
   }
   
   public String getCollectedAmount(String sFirstName) throws Exception
   {
	   System.out.println("Inside getCollectedAmount" );
	   String PtName=getPatientname();
		System.out.println("row size1 is : "+ tbl_BillDetailsRows.size());
		//Get column position
		List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
		int CollectedAmountPosition=0;
		String colValue1 = "";
		System.out.println(0);
		for(int i=2;i<=(tbl_BillDetailsRows.size()-4);i++)
		{
			System.out.println(1);
		for(int j=0;j<th.size();j++)
		{	
			System.out.println(2);
			if("Collected Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				CollectedAmountPosition=j+1;
				System.out.println("CollectedAmountPosition  : "+ CollectedAmountPosition);
				}
		}
		if(PtName.equalsIgnoreCase(sFirstName))
		   {
			   System.out.println(3);
			    String colValuepath =("//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+CollectedAmountPosition+"]");
			    String colValue =driver.findElement(By.xpath(colValuepath)).getText();
			    System.out.println("billValue1 : "+ colValue);
			   Log.info("      Check the bill Value: " + colValue);
			   System.out.println(4);
			   if(colValue.length()==8)
				{
				   System.out.println(5.);
					String temp2 = colValue.substring(0, 1);
					String temp3 = colValue.substring(2, 8);
					colValue = temp2+temp3;
				}
				else if(colValue.length()==9)
				{
					System.out.println(6.);
					String temp2 = colValue.substring(0, 2);
					String temp3 = colValue.substring(3, 9);
					colValue = temp2+temp3;
				} 
			   colValue1 = colValue;
		   }
		
   }
		 System.out.println("completed getCollectedAmount" );
		return colValue1;
   }
   
   /*
    * 
     public void verifyTotalCollectedAmount()
   {
	   int rowsize = getBillTableRows();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int CollectedAmountPosition=0;
		int sumIt=0;
		for(int i=0;i<=rowsize;i++)
		{
		for(int j=0;j<th.size();j++)
		{	
			if("Collected Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				CollectedAmountPosition=j+1;
				System.out.println("CollectedAmountPosition  : "+ CollectedAmountPosition);
				}
			String CollectedAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(rowsize-1)+"]/td["+CollectedAmountPosition+"]";
			String CollectedAmount=driver.findElement(By.xpath(CollectedAmountPath)).getText();
			   sumIt = sumIt+ Integer.parseInt(CollectedAmount);
		}	
		}
		
		WebElement TotalCollectedAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[5]"));
		String Collectedamt= TotalCollectedAmount.getText();
		Assert.assertEquals("***Amount not updated as expected***",Collectedamt,sumIt);
   }
   
   
   public String getBillAmount(String sFirstName)
   {
	   System.out.println(1);
	   String PtName=getPatientname();
	  // String billValue = "";
	   System.out.println(2);
	   int rowsize = getBillTableRows();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int BillAmountPosition=0;
		for(int j=0;j<th.size();j++)
		{	
			if("Bill Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				BillAmountPosition=j+1;
				System.out.println("BillAmountPosition  : "+ BillAmountPosition);
				}
	   if(PtName.equalsIgnoreCase(sFirstName))
	   {
		   System.out.println(3);
		    String billValue = ("//div[@class='col-sm-12']/table/tbody/tr["+(rowsize-1)+"]/td["+(BillAmountPosition)+"]");
		    System.out.println("billValue1  : "+ billValue);
		   Log.info("Check the bill Value: " + billValue);
		   System.out.println(4);
		   if(billValue.length()==8)
			{
			   System.out.println(5);
				String temp2 = billValue.substring(0, 1);
				String temp3 = billValue.substring(2, 8);
				billValue = temp2+temp3;
			}
			else if(billValue.length()==9)
			{
				System.out.println(6);
				String temp2 = billValue.substring(0, 2);
				String temp3 = billValue.substring(3, 9);
				billValue = temp2+temp3;
			} 
		 //  billValue1 = billValue;
	   }	
	   System.out.println("billValue2 : "+ billValue);
	   return billValue;
   }   
	 
   public String getDiscountAmount(String sFirstName)
   {
	   String PtName=getPatientname();
	   String discountValue="";
	   if(PtName.equalsIgnoreCase(sFirstName))
	   {
		    discountValue=DiscountAmount.getText();
		   Log.info("Check the Discount Value: " + discountValue );
		   //discountValue1= discountValue;
       }
	   return discountValue ;
   }
   */ 
  
   /*
   public String getCollectedAmount(String sFirstName)
   {
	   String PtName=getPatientname();
	   String collectedValue="";
       if(PtName.contentEquals(sFirstName))
       {
    	 collectedValue=.getText(); 
    	 Log.info("Check the Net Value: " + collectedValue );
    	 if(collectedValue.length()==8)
			{
				String temp2 = collectedValue.substring(0, 1);
				String temp3 = collectedValue.substring(2, 8);
				collectedValue = temp2+temp3;
			}
			else if(collectedValue.length()==9)
			{
				String temp2 = collectedValue.substring(0, 2);
				String temp3 = collectedValue.substring(3, 9);
				collectedValue = temp2+temp3;
			} 
    	 //collectedAmount1= collectedValue;
       }
	   return collectedValue;
   }
    
   public String getDueAmount(String sFirstName)
   {
	   String PtName=getPatientname();
	   String dueValue="";
       if(PtName.contentEquals(sFirstName))
       {
    	  dueValue=.getText(); 
    	 Log.info("Check the Net Value: " + dueValue );
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
    	// DueAmount1= dueValue;
       }
	   return dueValue;
   }
 
*/
    public void customDatePickerManual()
     {
	 txtbx_FromDate.sendKeys("09202017");
	 txtbx_FromDate.sendKeys(Keys.TAB);
	 txtbx_ToDate.sendKeys("09212017");
	 txtbx_ToDate.sendKeys(Keys.TAB);
	 btn_SearchRecords.click();
     }
    
    public void customDatePicker() throws Exception
    {
    	txtbx_FromDate.click();
    	//driver.findElement(By.xpath("//div[@class='moment-picker-container year-view open']"))
    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted']")).click();
    	Thread.sleep(1000);
    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted today']")).click();
    	txtbx_FromDate.sendKeys(Keys.TAB);
    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted']")).click();
    	Thread.sleep(1000);
    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted today']")).click();
    	txtbx_ToDate.sendKeys(Keys.TAB);
    	btn_SearchRecords.click();
    }
}

/*
  
   public String getNetAmount(String sFirstName)
   {
	   String PtName=getPatientname();
	   System.out.println("PtName:"+PtName);
	   List<WebElement> rowSize = tbl_BillDetails.findElements(By.tagName("tr"));
	   String netValue="";
	   System.out.println("1");
	   for(int i=2;i<=rowSize.size();i++)
		{
		   
       if(PtName.contentEquals(sFirstName))
       {
    	   System.out.println("2.5");
    	 
    	 netValue=driver.findElement(By.xpath("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td[7]")).getText(); 
    	 System.out.println("netValue:"+netValue);
    	 Log.info("Check the Net Value: " + netValue );
    	 if(netValue.length()==8)
			{
				String temp2 = netValue.substring(0, 1);
				String temp3 = netValue.substring(2, 8);
				netValue = temp2+temp3;
			}
			else if(netValue.length()==9)
			{
				String temp2 = netValue.substring(0, 2);
				String temp3 = netValue.substring(3, 9);
				netValue = temp2+temp3;
			} 
    	
       }
	   
   }
	return netValue;
   }
   
   
   
   public String getNetAmount()
	{
		
		int rowsize = getBillTableRows();
		String path = "//div[@class='col-sm-12']/table/tbody/tr["+(rowsize-1)+"]/td[7]";
		WebElement NetAmount = driver.findElement(By.xpath(path));
		Log.info("NetAmount in invoice bill details is : "+NetAmount.getText());
		String NetAmt = NetAmount.getText();
		
		if(NetAmt.length()==8)
		{
			String temp2 = NetAmt.substring(0, 1);
			String temp3 = NetAmt.substring(2, 8);
			NetAmt = temp2+temp3;
		}
		else if(NetAmt.length()==9)
		{
			String temp2 = NetAmt.substring(0, 2);
			String temp3 = NetAmt.substring(3, 9);
			NetAmt = temp2+temp3;
		}
		return NetAmt;
	}
   
   public int verifyTotalBillAmount()
   {
	   int Row_count= driver.findElements(By.xpath("//div[@class='col-sm-12']/table/tbody/tr")).size();
	   int Col_count=driver.findElements(By.xpath("//div[@class='col-sm-12']/table/tbody/tr[1]/th")).size();
	   System.out.println("row_count:"+ Row_count);
	   System.out.println("col_count:"+ Col_count);
	   
		String first_path ="//div[@class='col-sm-12']/table/tbody/tr[";
		String second_path = "]/th[";
		String third_path= "]";
		int sumIt=0;
		//int sum=0;
		//For loop for number of rows
		for(int i=0;i<=Row_count;i++)
		{	
		// For loop for number of columns
		for(int j=1; j<= Col_count; j++)
		{
			String path = first_path+i+second_path+j+third_path;
			String billValue =driver.findElement(By.xpath(path)).getText();
			System.out.println(" "+ billValue); 
			sumIt = sumIt+ Integer.parseInt(billValue);
		}
		}	
		  
		WebElement TotalBillAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[1]"));
		String billamt= TotalBillAmount.getText();
		Assert.assertEquals("***Amount not updated as expected***",billamt,sumIt);
		
		return sumIt;
   }
   
    public void verifyTotalDiscountAmount()
   {
	   int rowsize = getBillTableRows();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
		int DisAmountPosition=0;
		int sumIt=0;
		for(int i=0;i<=rowsize;i++)
		{
		for(int j=0;j<th.size();j++)
		{	
			if("Discount Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				DisAmountPosition=j+1;
				System.out.println("DisAmountPosition  : "+ DisAmountPosition);
				}
			String DisAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(rowsize-1)+"]/td["+(DisAmountPosition)+"]";
			   String DisAmount=driver.findElement(By.xpath(DisAmountPath)).getText();
			   sumIt = sumIt+ Integer.parseInt(DisAmount);
		}	
		}	
		WebElement TotalDisAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[3]"));
		String disamt= TotalDisAmount.getText();
		Assert.assertEquals("***Amount not updated as expected***",disamt,sumIt);
   }
   
   public int verifyTotalDiscountAmount()
   {
	   int Row_count= driver.findElements(By.xpath("//div[@class='col-sm-12']/table/tbody/tr")).size();
	   int Col_count=driver.findElements(By.xpath("//div[@class='col-sm-12']/table/tbody/tr[2]/td")).size();
	   System.out.println("row_count:"+ Row_count);
	   System.out.println("col_count:"+ Col_count);
	   
		String first_path ="//div[@class='col-sm-12']/table/tbody/tr[";
		String second_path = "]/th[";
		String third_path= "]";
		int sumIt=0;
		//int sum=0;
		//For loop for number of rows
		for(int i=0;i<=Row_count;i++)
		{	
		// For loop for number of columns
		for(int j=1; j<= Col_count; j++)
		{
			String path = first_path+i+second_path+j+third_path;
			String disValue =driver.findElement(By.xpath(path)).getText();
			System.out.println(" "+ disValue); 
			sumIt = sumIt+ Integer.parseInt(disValue);
		}
		}	
		  
		WebElement TotalDisAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[3]"));
		String disamt= TotalDisAmount.getText();
		Assert.assertEquals("***Amount not updated as expected***",disamt,sumIt);
		return sumIt;
   }
   
    public void verifyTotalBillAmount()
   {
	   System.out.println("m");
	   int rowsize = getBillTableRows();
	   List<WebElement> th= tbl_BillDetails.findElements(By.tagName("th"));
	   for(WebElement ele:th)
		{
			System.out.println("Header list : "+ele.getText());
		}
	   System.out.println("1");
		int BillAmountPosition=0;
		int sumIt=0;
		for(int i=0;i<=rowsize;i++)
		{
		System.out.println("a");
		for(int j=0;j<=th.size();j++)
		{
			System.out.println(th.get(j).getText());
			if("Bill Amount".equalsIgnoreCase(th.get(j).getText()))
			 {
			System.out.println("c");				
			System.out.println("BillAmountPosition  : "+ BillAmountPosition);		
			  }		
		}
		//BillAmountPosition=j+1;
		}
		System.out.println("d");
		String BillAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(rowsize-4)+"]/td["+(BillAmountPosition)+"]";
		System.out.println("rowsize  : "+ rowsize + "BillAmountPosition:"+ BillAmountPosition);
		System.out.println("e");
		String BillAmount= driver.findElement(By.xpath(BillAmountPath)).getText();
		System.out.println(BillAmount);
		sumIt = sumIt+ Integer.parseInt(BillAmount);
		
		WebElement TotalBillAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[1]"));
		String billamt= TotalBillAmount.getText();
		System.out.println("TotalBillAmount"+ TotalBillAmount);
		Assert.assertEquals("***Amount not updated as expected***",billamt,sumIt);
   }
  
   public void verifyTotalNetAmount() throws Exception
   {
	   List<WebElement> patientServiceTableRows =tbl_BillDetails .findElements(By.tagName("tr"));
	   //int Row_count= driver.findElements(By.xpath("//div[@class='col-sm-12']/table/tbody/tr")).size();
	   //int Col_count=driver.findElements(By.xpath("//div[@class='col-sm-12']/table/tbody/tr[2]/td")).size();
	   List<WebElement> th=tbl_BillDetails.findElements(By.tagName("th"));
	   System.out.println("row_count:"+ patientServiceTableRows.size());
	   //System.out.println("col_count:"+ Col_count);
		int sum=0;
		int NetAmountPosition=0;
		double  Total = 0;
		
		//For loop for number of rows
		for(int i=2;i<=(patientServiceTableRows.size()-4);i++)
		{	
			System.out.println("inside i loop");
		// For loop for number of columns
		for(int j=1;j<=(th.size()-2);j++)
		{
			if("Net Amount".equalsIgnoreCase(th.get(j).getText()))
			{
			NetAmountPosition=j+1;
			System.out.println("NetAmountPosition  : "+ NetAmountPosition);
			
			String first_path ="//div[@class='col-sm-12']/table/tbody/tr[";
			
			String second_path = "]/td[";
		
			String third_path= "]";
			
			String path = first_path+i+second_path+(j+1)+third_path;
			System.out.println("path-"+ path);
			System.out.println("4");
			String netValue =driver.findElement(By.xpath(path)).getText();
			System.out.println("netValue:"+ netValue);
			Total = Total + Double.parseDouble(netValue);
			/*
			NumberFormat format= netValue.getCurrencyInstance();
			Number number =format.parse("netValue");
			System.out.println("number:"+ number);
			/*
			 //String OSNAMES= Driver.findElement(By.xpath("YOUR XPATH")).getAttribute("value");
			 String[] parts = netValue.split("");
			 System.out.println(parts);
			 String OS = parts[3];
			 int a=Integer.parseInt(OS);
			 System.out.println(a);
			
			//System.out.println(" NetAmount"+ Integer.parseInt(netValue));
			Thread.sleep(2000);
			//int net=Integer.parseInt(netValue1);
			//System.out.println(" net"+ netValue1);
			//sum = sum+netValue1;
			//System.out.println("Sum"+ sum);
//			if(sumIt == 0){
//				sumIt =  Integer.parseInt(netValue);
//			}else{
//				sumIt = sumIt+ Integer.parseInt(netValue);
//			}
			//System.out.println(" sumIt"+ sumIt);
			}
			System.out.println("out for loop");
			//String NetAmountPath="//div[@class='col-sm-12']/table/tbody/tr["+(Row_count-4)+"]/td["+NetAmountPosition+"]";
			//String NetAmount=driver.findElement(By.xpath(NetAmountPath)).getText(); 
			//sumIt = sumIt+ Integer.parseInt(disValue);
			 //Col_count=j+1;
		}
		System.out.println("out j loop");
		//Row_count=i+1;
		}	
		System.out.println("Sum of Net Amount " + Total);
		
		WebElement TotalNetAmount = driver.findElement(By.xpath("//tr[@ng-show='TotalRow']/td[4]"));
		String netamt= TotalNetAmount.getText();
		System.out.println("netamt " + netamt);
		Assert.assertEquals("***Amount not updated as expected***",netamt,Total);
		 Assert.assertEquals("***Amount not updated as expected***", net, Total);
		Assert.assertEquals(Total, net);
		//Assert.assertEquals("***Amount not updated as expected***",netamt,Total); 
		//Assert.assertTrue("***Amount not updated as expected***",net.equals(Total));
		 
		 
   }
  
   }  
   */

