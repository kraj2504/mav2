package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Log;

public class PO_InvoiceMIS 
{
	WebDriver driver;
	
	public PO_InvoiceMIS(WebDriver driver)
	{
		this.driver=driver;
	}
	
	
	   @FindBy(how=How.XPATH, using="//input[@name='q']")
	   WebElement txtbx_search;
		
	   @FindBy(how=How.XPATH, using="//input[@data-ng-model='invoice.IsChecked']")
	   WebElement rdbtn_Select;
	
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
	   
	 @FindBy(how=How.XPATH,using="//div[@class='box-body']/a[3]/i")
	   WebElement btn_print;
	      
	 @FindBy(how=How.XPATH, using="//button[@ class='ng-scope']")
	  WebElement icon_SettlementHistory;
	  
	 @FindBy(how=How.XPATH, using="//div[@class='row']/fieldset[4]/div")
	  WebElement table_MISdetails;
	
	 @FindBy(how=How.ID, using="txtalert")
	 WebElement Alrt_txtalert;
	 

	  public void searchclient(String sReferralName)
	  {
		  txtbx_search.click();
		  txtbx_search.sendKeys(sReferralName);
		  txtbx_search.sendKeys(Keys.ENTER);
		  Log.info("Client name is searched in the search text box");
	  }
	  
	  // click alert when no records...
	  public void clickAlert ()
	  {
		  Alrt_txtalert.click(); 
		  
	  }
	  
	  public void clickSettlementHistory()
	  {
		List<WebElement> Rows = driver.findElements(By.xpath("//div/ui-view/div/div/section/div/div[1]/fieldset[4]/div/table/tbody/tr"));
		List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		int settlementHistoryPosition=0;
		for(int i=2;i<Rows.size();i=i+3)
		{	
		for(int j=1;j<th.size();j++)
		{	
			
			if("Settlement History".equalsIgnoreCase(th.get(j).getText()))
				{
				settlementHistoryPosition=j+1;
				System.out.println("settlementHistoryPosition :  "+ settlementHistoryPosition);	
				}
		}

		   String settlementHistorypath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+(settlementHistoryPosition+1)+"]/div/button");   
		  driver.findElement(By.xpath(settlementHistorypath)).click();
		 // System.out.println(settlementHistorypath+ "settlementHistorypath"); 
			   //Log.info("Check the advanced adjusted Value: " + selectpath);
		  
		  if (settlementHistory.length == 0)
		  {
			  
		  }
   }
		System.out.println("Completed ClickSelect");
		
   }

	  
	  public void clickPrint()
		{
		   btn_print.click();
			Log.info("Click action performed on print Button");
		}
	  
	  public void clickRefresh()
		{
		    btn_Refresh.click();
			Log.info("Click action performed on Refresh Button");
		}
	  
	  public void clickThisWeek()
		{
		    btn_ThisWeek.click();
			Log.info("Click action performed on this week Button");
		}
	  
	  public void clickThisMonth()
		{
		    btn_ThisMonth.click();
			Log.info("Click action performed on Refresh Button");
		}
	  
	  public void clickCustom()
		{
		  btn_Custom.click();
			Log.info("Click action performed on Custom Button");
		}
	  
	  public void customDatePicker() throws Exception
	    {
	    	txtbx_FromDate.click();
	    	//driver.findElement(By.xpath("//div[@class='moment-picker-container year-view open']"))
	    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted']")).click();
	    	Thread.sleep(2000);
	    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted today']")).click();
	    	txtbx_FromDate.sendKeys(Keys.TAB);
	    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted']")).click();
	    	Thread.sleep(2000);
	    	driver.findElement(By.xpath("//td[@class='ng-binding ng-scope highlighted today']")).click();
	    	txtbx_ToDate.sendKeys(Keys.TAB);
	    	btn_SearchRecords.click();
	    }
	  
	  public void clickSearchRecords()
		{
		  btn_SearchRecords.click();
		  Log.info("Click action performed on Refresh Button");
		}
	  
	  
	  public void selectcustomDate(String CustomFromDate, String CustomToDate)
	  {
		  txtbx_FromDate.click();
		  txtbx_FromDate.sendKeys(CustomFromDate);
		  txtbx_FromDate.sendKeys(Keys.TAB);
		  txtbx_ToDate.sendKeys(CustomToDate);
	  }
	  
	  
	  public List<String> getInvoiceNumber()
	   {
		// int rowSize = getRowsInTable();
		// System.out.println("rowsSize in invoice: "+ rowSize);
		 // List<WebElement> misTableRows = table_MISdetails.findElements(By.tagName("tr"));
		  List<WebElement> Rows = driver.findElements(By.xpath("//div/ui-view/div/div/section/div/div[1]/fieldset[4]/div/table/tbody/tr"));
			//int totalRows = Rows.size();
			System.out.println(" Total rows : "+ Rows);
			
		   List<WebElement> th= table_MISdetails.findElements(By.tagName("th"));
		   System.out.println("Column invoice : "+th);
		 List<String> value = new ArrayList<String>();
			int InvoiceNumberPosition=0;
			// String invoiceNumber[]= new String[8];
			 System.out.println("1");
			
			for(int i=2;i<Rows.size();i=i+3)
			{
	
				System.out.println("rowSize inside loop"+Rows.size());
			                  
			for(int j=1;j<th.size();j++)
			{	
				if("Invoice No".equalsIgnoreCase(th.get(j).getText()))
					{	
					InvoiceNumberPosition = j+1;
					System.out.println("InvoiceNumberPosition  : "+ InvoiceNumberPosition);
					
					}
			}				
			String InvoiceNumPath ="//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+InvoiceNumberPosition+"]";	
			
			System.out.println("path  : "+ InvoiceNumPath);
			String invoiceNumber = driver.findElement(By.xpath(InvoiceNumPath)).getText(); 
			
			value.add(invoiceNumber);
			
			//List<WebElement> optiontoselect = driver.findElements(By.xpath(InvoiceNumPath));
			
			System.out.println("value  : "+ value);
			 
			//value.add(column.get(j).getText());
			}
			
			 // invoiceNumber[i]= driver.findElement(By.xpath(InvoiceNumPath)).getText();  
			
			
			//invoiceNumber=invoiceNumber1;
			//System.out.println("ServiceName  : "+ th.get(j).getText());
			return value;
	
		}
			
			 
			
			
			
	    
	  public int getRowsInTable()
	  {
		  int count = 0;
		  List<WebElement> rows = driver.findElements(By.xpath("//table//tr"));
		  //System.out.println("The number of rows that are visible is: "+ rows);
		  for(WebElement row: rows){
		    if(row.isDisplayed())
		      count++;
		  }
		  System.out.println("The number of rows that are visible is: "+ count);
		  return count;
	  }
	  
	public void clickSelect(String sInvoiceNumber)
	{
		System.out.println("Inside clickSelect" ); 
		List<WebElement> Rows = driver.findElements(By.xpath("//div/ui-view/div/div/section/div/div[1]/fieldset[4]/div/table/tbody/tr"));
		 //List<WebElement> patientBillTableRows = table_MISdetails.findElements(By.name("MISname"));
		System.out.println("patientBillTableRows : "+ Rows.size());
		 List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		 System.out.println("column is : "+ th);
		 //int rowSize = getRowsInTable();
		 	
	List<String> invoiceNumber = getInvoiceNumber();
		 
			int selectPosition=0;
			
			for(int j=0;j<th.size();j++)
			{	
				
				if("Select".equalsIgnoreCase(th.get(j).getText()))
					{
					selectPosition=j+1;
					System.out.println("selectPosition  : "+ selectPosition);
					}
			}
			
			for(int i=2;i<Rows.size();i=i+3)
			{
				System.out.println(1.);
			
			   if(invoiceNumber.equals(sInvoiceNumber)); 	
			   {
				    System.out.println("invoiceNumber 1 : "+ invoiceNumber);
					System.out.println("invoiceNumber 2 : "+ sInvoiceNumber);
					
				    String selectpath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+selectPosition+"]/input");
				    
				    driver.findElement(By.xpath(selectpath)).click();
				    System.out.println("advAdjValue : "+ selectpath);
				   //Log.info("Check the advanced adjusted Value: " + selectpath);
			   }
			
	   }
			System.out.println("Completed ClickSelect");
			
	   }
				   
	 
     /*
	   public String getINvoiceNUmber(String sInvoiceNumber)
	   {
		   System.out.println("Inside getINvoiceNUmber");
		   String invoiceNum = getInvoiceNumber();
		   List<WebElement> patientBillTableRows = table_MISdetails.findElements(By.tagName("tr"));
			System.out.println("row size1 is : "+ patientBillTableRows.size());
			//Get column position
			List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
			int invoiceNoPosition=0;
			String  invoiceNumber1="";
			System.out.println(0);
			for(int i=2;i<=patientBillTableRows.size();i=i+3)
			{
				System.out.println(1.);
			for(int j=0;j<th.size();j++)
			{	
				System.out.println(2.);
				if("Invoice No".equalsIgnoreCase(th.get(j).getText()))
					{
					invoiceNoPosition=j+1;
					System.out.println("invoiceNoPosition  : "+ invoiceNoPosition);
					}
			}
			if(invoiceNum.equalsIgnoreCase(sInvoiceNumber))
			   {
				String InvoiceNumPath="//table[@class='table service-table scroll']/tbody/tr["+i+"]/td["+invoiceNoPosition+"]";
				String invoiceNo=driver.findElement(By.xpath(InvoiceNumPath)).getText(); 
				//String InvoiceNumPath="//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+InvoiceNumberPosition+"]";	
				System.out.println("path  : "+ InvoiceNumPath);
				invoiceNumber1=invoiceNo;   
			   }
			
	   }
			System.out.println("completed getINvoiceNumber" );
			return invoiceNumber1 ;
	   }
	   /*
	public void getINvoiceNUmber(String sInvoiceNumber)
	{
		System.out.println("Inside clickSelect" ); 
		 List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		 System.out.println("column is : "+ th);
		 List<WebElement> misTableRows = table_MISdetails.findElements(By.tagName("tr"));
		 System.out.println("row size1 is : "+ misTableRows.size());	
		 String invoiceNum = getInvoiceNumber();
				if(invoiceNum.equalsIgnoreCase(sInvoiceNumber))
				   {
					//driver.findElement(By.xpath(""))   
				   }
				
		   }
	*/
	
	
	   
	public void getInvoiceAmount(String sInvoiceNumber)
	{
		 System.out.println("Inside getInvoiceAmount " ); 
		 List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		 System.out.println("column is : "+ th);
		 List<WebElement> misTableRows = table_MISdetails.findElements(By.name("MISname"));
		 System.out.println("row size1 is : "+ misTableRows.size());	
		 String invoiceNum = getInvoiceNumber();
				int InvoiceNumPosition=0;
				for(int i=2;i<=(misTableRows.size());i=i+3)
				{
					
				for(int j=0;j<th.size();j++)
				{	
					
					if("Invoice Amount".equalsIgnoreCase(th.get(j).getText()))
						{
						InvoiceNumPosition=j+1;
						System.out.println("InvoiceNumPosition  : "+ InvoiceNumPosition);
						}
				}
				if(invoiceNum.equalsIgnoreCase(sInvoiceNumber))
				   {
					
					String invoiceValuepath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+InvoiceNumPosition+"]");
				    String invoiceValue =driver.findElement(By.xpath(invoiceValuepath)).getText();
					System.out.println("invoiceValue : "+ invoiceValue);
				  Log.info("Check the invoice amount Value: " + invoiceValue);
					   
				   }
		   }	
     }
	
	public void getExcessAmount(String sInvoiceNumber)
	{
		 System.out.println("Inside getInvoiceAmount " ); 
		 List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		 System.out.println("column is : "+ th);
		 List<WebElement> misTableRows = table_MISdetails.findElements(By.tagName("tr"));
		 System.out.println("row size1 is : "+ misTableRows.size());	
		 String invoiceNum = getInvoiceNumber();
				int ExcessAmountPosition=0;
				for(int i=2;i<=(misTableRows.size());i=i+3)
				{
					
				for(int j=0;j<th.size();j++)
				{	
					
					if("Excess Amount".equalsIgnoreCase(th.get(j).getText()))
						{
						ExcessAmountPosition=j+1;
						System.out.println("ExcessAmountPosition  : "+ ExcessAmountPosition);
						}
				}
				if(invoiceNum.equalsIgnoreCase(sInvoiceNumber))
				   {

					String ExcessValuepath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+ExcessAmountPosition+"]");
				    String excessValue =driver.findElement(By.xpath(ExcessValuepath)).getText();
					System.out.println("invoiceValue : "+ excessValue);
					Log.info("Check the invoice amount Value: " + excessValue); 
				   }
		   }	
     }
	
	
	
	public void getPaidAmount(String sInvoiceNumber)
	{
		 System.out.println("Inside getInvoiceAmount " ); 
		 List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		 System.out.println("column is : "+ th);
		 List<WebElement> misTableRows = table_MISdetails.findElements(By.tagName("tr"));
		 System.out.println("row size1 is : "+ misTableRows.size());	
		 String invoiceNum = getInvoiceNumber();
				int paidAmountPosition=0;
				for(int i=2;i<=(misTableRows.size());i=i+3)
				{
					
				for(int j=0;j<th.size();j++)
				{	
					if("Paid Amount".equalsIgnoreCase(th.get(j).getText()))
						{
						paidAmountPosition=j+1;
						System.out.println("InvoiceNumPosition  : "+ paidAmountPosition);
						}
				}
				 if(invoiceNum.equalsIgnoreCase(sInvoiceNumber))
				   {
					String paidValuepath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+paidAmountPosition+"]");
				    String paidAmtValue =driver.findElement(By.xpath(paidValuepath)).getText();
					System.out.println("PaidAmount value : "+ paidAmtValue);
					Log.info("Check the paid amount Value: " + paidAmtValue);
				   }
		   }	
     }
	
	public void getBalanceAmount(String sInvoiceNumber)
	{
		 System.out.println("Inside getInvoiceAmount"); 
		 List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		 System.out.println("column is : "+ th);
		 List<WebElement> misTableRows = table_MISdetails.findElements(By.tagName("tr"));
		 System.out.println("row size1 is : "+ misTableRows.size());	
		 String invoiceNum = getInvoiceNumber();
				int balanceAmountPosition=0;
				for(int i=2;i<misTableRows.size();i=i+3)	
				{
				for(int j=0;j<th.size();j++)
				{	
					if("Balance Amount".equalsIgnoreCase(th.get(j).getText()))
						{
						balanceAmountPosition=j+1;
						System.out.println("BalanceAmountPosition  : "+ balanceAmountPosition);
						}
				}
				 if(invoiceNum.equalsIgnoreCase(sInvoiceNumber))
				   {
					String balValuepath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+balanceAmountPosition+"]");
				    String balAmtValue =driver.findElement(By.xpath(balValuepath)).getText();
					System.out.println("balanceAmount value : "+ balAmtValue);
					Log.info("Check the balance amount Value: " + balAmtValue);
				   }
		   }	
     }
	
	 public String getAdvancedAdjustedAmount(String sInvoiceNumber)
	   {
		   System.out.println("Inside getAdvancedAdjustedAmount" );
		   List<String> value = getInvoiceNumber();
		 /*  for(int k=0;k<value.;k=i++)
			{
				value[j]
			}*/
		   List<WebElement> patientBillTableRows = table_MISdetails.findElements(By.xpath("//div/ui-view/div/div/section/div/div[1]/fieldset[4]/div/table/tbody/tr"));
			System.out.println("row size1 is : "+ patientBillTableRows.size());
			//Get column position:
			List<WebElement> th= table_MISdetails.findElements(By.tagName("th"));
			int AdvancedAmountPosition=0;
			String advValue1 = "";
			System.out.println(0);
			
			for(int j=0;j<th.size();j++)
			{	
				
				if("Advanced AdjustedAmount".equalsIgnoreCase(th.get(j).getText()))
					{
					AdvancedAmountPosition=j+1;
					System.out.println("AdvancedAmountPosition  : "+ AdvancedAmountPosition);
					}
			}
			
			for(int i=2;i<patientBillTableRows.size();i=i+3)
			{
				System.out.println(0);
				
			if(value.equals(sInvoiceNumber))
			   {
				  System.out.println(value);
				  System.out.println(sInvoiceNumber);
			String advancedAdjustedpath =("//div[@class='col-sm-12']/table/tbody/tr["+(i)+"]/td["+AdvancedAmountPosition+"]");
	 	    String advAdjValue =driver.findElement(By.xpath(advancedAdjustedpath)).getText();
		    System.out.println("advAdjValue : "+ advAdjValue);
				   Log.info("Check the advanced adjusted Value: " + advAdjValue);
				   System.out.println(2);
				  
				   advValue1 = advAdjValue;
			   }
			
	   }
			System.out.println("Completed getAdvancedAdjustedAmount ");
			return advValue1;
	   }
	
	public void getClientName(String sInvoiceNumber)
	{
		 System.out.println("Inside getInvoiceAmount " ); 
		 List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		 System.out.println("column is : "+ th);
		 List<WebElement> misTableRows = table_MISdetails.findElements(By.tagName("tr"));
		 System.out.println("row size1 is : "+ misTableRows.size());	
		 String invoiceNum = getInvoiceNumber();
				int clientNamePosition=0;
				for(int i=2;i<=(misTableRows.size());i++)
				{
				for(int j=0;j<th.size();j++)
				{	
					if("Balance Amount".equalsIgnoreCase(th.get(j).getText()))
						{
						clientNamePosition=j+1;
						System.out.println("clientNamePosition  : "+ clientNamePosition);
						}
				}
				 if(invoiceNum.equalsIgnoreCase(sInvoiceNumber))
				   {
					String clientNamepath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+clientNamePosition+"]");
				    String clientNameValue =driver.findElement(By.xpath(clientNamepath)).getText();
					System.out.println("balanceAmount value : "+ clientNameValue);
					Log.info("Check the balance amount Value: " + clientNameValue);
				   }
		   }	
     }
	
	public void verifyBalanceAmount() throws Exception
	{
	
        List<WebElement> rowSize = table_MISdetails.findElements(By.xpath("//div/ui-view/div/div/section/div/div[1]/fieldset[4]/div/table/tbody/tr"));
        System.out.println("row size fr verify balance amount : "+rowSize.size());
		//Get column position
		List<WebElement> th=table_MISdetails.findElements(By.tagName("th"));
		
		int InvoiceAmountPosition=0,PaidAmountPosition=0,balanceAmountPosition=0;
		for(int j=0;j<th.size();j++)
		{	
			if("Invoice Amount".equalsIgnoreCase(th.get(j).getText()))
			{
			    System.out.println("1");
			    InvoiceAmountPosition=j+1;
				System.out.println("InvoiceAmountPosition  : "+InvoiceAmountPosition);
			}
			if("Paid Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				System.out.println("2");
				PaidAmountPosition=j+1;
				System.out.println("PaidAmountPosition  : "+PaidAmountPosition);
				}
			
			if("Balance Amount".equalsIgnoreCase(th.get(j).getText()))
			{
			System.out.println("2.5");
			balanceAmountPosition=j+1;
			System.out.println("balanceAmountPosition  : "+balanceAmountPosition);
			}
			
		}
		System.out.println("Row size: "+rowSize.size()); 
		
		
		for(int i=2;i<rowSize.size();i=i+3)
		{
			System.out.println("RowSize: "+rowSize.size());
			String invoicepath ="//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+InvoiceAmountPosition+"]";
			System.out.println("invoicepath  : "+ invoicepath);
			String invoiceAmount =  driver.findElement(By.xpath(invoicepath)).getText();
			System.out.println("invoiceAmount  : "+ invoiceAmount);
			if(invoiceAmount.length()==8)
			{
				String temp2 = invoiceAmount.substring(0, 1);
				String temp3 = invoiceAmount.substring(2, 8);
				invoiceAmount = temp2+temp3;
			}
			else if(invoiceAmount.length()==9)
			{
				String temp2 = invoiceAmount.substring(0, 2);
				String temp3 = invoiceAmount.substring(3, 9);
				invoiceAmount = temp2+temp3;
			}
			
			String paidpath = "//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+PaidAmountPosition+"]";
			System.out.println("paidpath  : "+ paidpath);
			String paidAmount =  driver.findElement(By.xpath(paidpath)).getText();
			System.out.println("paidAmount  : "+ paidAmount);
			if(paidAmount.length()==8)
			{
				String temp2 = paidAmount.substring(0, 1);
				String temp3 = paidAmount.substring(2, 8);
				paidAmount = temp2+temp3;
			}
			else if(paidAmount.length()==9)
			{
				String temp2 = paidAmount.substring(0, 2);
				String temp3 = paidAmount.substring(3, 9);
				paidAmount = temp2+temp3;
			}
			
			String balancepath = "//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+balanceAmountPosition+"]";
			System.out.println("balancepath  : "+ balancepath);
			String balanceAmount =  driver.findElement(By.xpath(balancepath)).getText();
			System.out.println("balanceAmount  : "+ balanceAmount);
			if(balanceAmount.length()==8)
			{
				String temp2 = balanceAmount.substring(0, 1);
				String temp3 = balanceAmount.substring(2, 8);
				balanceAmount = temp2+temp3;
			}
			else if(balanceAmount.length()==9)
			{
				String temp2 = balanceAmount.substring(0, 2);
				String temp3 = balanceAmount.substring(3, 9);
				balanceAmount = temp2+temp3;
			}
			float balance = Float.parseFloat(balanceAmount);
			/*
			String value = balanceAmount;
			 System.out.println("value: "+value);
			 int posi = value.indexOf(".");
	         value = value.substring(0, posi);
	         System.out.println("deci: "+value);
	         */
			System.out.println("inside loop 5");			
			float invoice = Float.parseFloat(invoiceAmount);
			System.out.println("invoice  : "+ invoice);
			float paid = Float.parseFloat(paidAmount);
			System.out.println("paid  : "+ paid);
			float calBal = invoice-paid;
			System.out.println(calBal);
			Assert.assertEquals("***balance amount does not match with the calculated amount***", balance, calBal, calBal);
			//String sValueofBalance= Float.toString(invoice-paid);
			/*
			int bal = Integer.parseInt(sValueofBalance);
			System.out.println("sValueofBalance  : "+ sValueofBalance);
			if (bal<=0)
	        {
	       	 System.out.println("due: "+bal);
	       	 bal=0;
	       	 System.out.println("due1: "+bal);
	        }
			 String calBal= Integer.toString(bal);
			Assert.assertTrue("***Amount not updated as expected***",balanceAmount.startsWith(calBal));
			*/
			//Assert.assertTrue("***Due amount does not match with the outstanding amount***",outamt.startsWith(verifydueAmt));
			}
		
	}
	
	
}
