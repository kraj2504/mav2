package pageObjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import org.testng.Assert;

import utility.Constant;
import utility.Log;
import utility.Utils;

public class PO_RefundCancel

{

	WebDriver driver;
	
	public PO_RefundCancel(WebDriver driver)
	{
		this.driver=driver;
	}	
	
	@FindBy(how = How.ID, using = "SearchSubjectRefund") //How.Name = q
	WebElement searchbox;
	
	@FindBy(how= How.XPATH, using = "//div[1]/fieldset/div/table")
	WebElement tbl_PatientSummary;
	
	@FindBy(how= How.XPATH, using = "//div[1]/fieldset/div/table//tr")
	List<WebElement> tbl_PatientSummaryRows;
	
	@FindBy(how= How.XPATH, using = "//div[1]/fieldset/div/table//th")
	List<WebElement> tbl_PatientSummaryHeaders;

	@FindBy(how= How.XPATH, using = "//div[2]/fieldset/div/table[@class='table service-table scroll']")
	WebElement tbl_PatientServiceDetails;
	
	@FindBy(how= How.XPATH, using = "//div[2]/fieldset/div/table[@class='table service-table scroll']//tr")
	List<WebElement> tbl_PatientServiceDetailsRows;
	
	@FindBy(how= How.XPATH, using = "//div[2]/fieldset/div/table[@class='table service-table scroll']//th")
	List<WebElement> tbl_PatientServiceDetailsHeaders;
	
	@FindBy(how= How.XPATH, using = "//input[@ng-model='isAllSelected']")
	WebElement selectallservice;
	
	@FindBy(how= How.ID, using = "UserName")
	WebElement Drpdwn_Approvedby;
	
	@FindBy(how=How.NAME, using = "Comments")
	WebElement txtbx_Remarks;
	
	@FindBy(how=How.ID, using= "CurrencyType")
	WebElement drpdwn_CurrencyType;
	
	@FindBy(how=How.XPATH,using= "//a[text()='Cash Amount']")
	WebElement lbl_cashamount;
	
	@FindBy(how= How.XPATH, using= ".//input[@ng-model='RefundCancel.Amount']")
	WebElement txtbx_Cashamount;
	
	@FindBy(how=How.XPATH, using="//a[text()='Card Amount']")
	WebElement lbl_Cardamount;
	
	@FindBy(how=How.XPATH,using="//input[@ng-model='RefundCancel.CreditDebit']")
	WebElement txtbx_CardAmount;
	
	@FindBy(how=How.XPATH,using="//input[@ng-model='RefundCancel.CreditDebitNo']")
	WebElement txtbx_Cardnumber;
	
	@FindBy(how=How.XPATH,using="//input[@ng-model='RefundCancel.CreditDebitTransactionID']")
	WebElement txtbx_TransactionID;
	
	@FindBy(how=How.XPATH, using="//a[text()='Cheque Amount']")
	WebElement lbl_Chequeamount;
	
	@FindBy(how=How.XPATH,using="//input[@ng-model='RefundCancel.Cheque']")
	WebElement txtbx_chequeAmount;
	
	@FindBy(how=How.XPATH,using="//input[@ng-model='RefundCancel.ChequeNo']")
	WebElement txtbx_ChequeNumber;
	
	@FindBy(how=How.ID,using="FromDate")
	WebElement txtbx_chequeDate;
	
	@FindBy(how=How.XPATH,using="//input[@ng-model='RefundCancel.ChequeBankName']")
	WebElement txtbx_BankName;
	
	@FindBy(how= How.XPATH, using= "//li[1]/div/table/tbody/tr/td[2]")
	WebElement Gross;
	
	@FindBy(how= How.XPATH, using= "//li[2]/div/table/tbody/tr/td[2]")
	WebElement Discount;
	
	@FindBy(how= How.XPATH, using= "//li[3]/div/table/tbody/tr/td[2]")
	WebElement Net;
	
	@FindBy(how= How.XPATH, using= "//li[4]/div/table/tbody/tr/td[2]")
	WebElement Refundamount;
	
	@FindBy(how= How.XPATH, using= "//li[5]/div/table/tbody/tr/td[2]")
	WebElement outstanding;
	
	@FindBy(how= How.XPATH, using= "//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how=How.XPATH, using= "//i[@class='fa fa-eraser']")
	WebElement btn_clear;
	
	@FindBy(how= How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_refresh;
	
	@FindBy(how = How.XPATH, using = "//div[@class='row']/div[1]/fieldset/div/table/tbody/tr[2]/td[5]")
	WebElement serviceNetamount ;
	
	@FindBy(how = How.XPATH, using = "//table[@class='table service-table scroll']/tbody/tr[2]/td[7]")
	WebElement Paidamount ;
	
	public void selectPatient() throws Exception
	{
		int rowsize= tbl_PatientSummaryRows.size();
		String path= "//div[1]/fieldset/div/table/tbody/tr["+rowsize+"]/td[1]/input";
		WebElement Vradio= driver.findElement(By.xpath(path));
		Vradio.click();
		Log.info("      Patient selected");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectAllService()
	{
		selectallservice.click();
		Log.info("      Click action performed on all services");
	}
	
	public void EnterCashAmount(String amount)
	{
		txtbx_Cashamount.sendKeys(amount);
		Log.info("      Given amount : "+txtbx_Cashamount.getAttribute("value"));
	}
	
	public String getCashAmount() throws Exception
	{
		return  txtbx_Cashamount.getAttribute("value");
	}
	
	public void searchRecord (String sFirstName) throws Exception
	{
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.sendKeys(sFirstName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//div[@class='col-sm-offset-3 col-sm-6']//ul/li[1]";//div[@name='q']//span[1]
		Utils.waitForElement(By.xpath(xpath));
		}
		catch(Exception e)
		{
			Log.error("      Refund Cancel --> Patient not listed in universal search"+e.getMessage());
			throw new Exception("Refund Cancel --> Patient not listed in universal search",e);
		}
		searchbox.sendKeys(Keys.TAB);
		Utils.waitUntilAngularFinishHttpCalls();
		Log.info("      Refund Cancel --> Search completed");
	}
	
	public void selectApprovedBy(String sApprovedByRefund)
	{
		Select value=new Select(Drpdwn_Approvedby);
		value.selectByVisibleText(sApprovedByRefund);
		String selectedOption = new Select(Drpdwn_Approvedby).getFirstSelectedOption().getText();
		Log.info("      Selected ApprovedBy : "+selectedOption);
	}
	
	public void enterRemarks(String sRemarksRefund)
	{
    	txtbx_Remarks.sendKeys(sRemarksRefund);	
    	Log.info("      Enter remark is + "+ txtbx_Remarks.getAttribute("value"));
	}
	
	public String getRefundAmount() throws Exception
	{
		String rfd= Refundamount.getText();
		
		if(rfd.length()==8)
		{
			String temp2 = rfd.substring(0, 1);
			String temp3 = rfd.substring(2, 8);
			rfd = temp2+temp3;
		}
		else if(rfd.length()==9)
		{
			String temp2 = rfd.substring(0, 2);
			String temp3 = rfd.substring(3, 9);
			rfd = temp2+temp3;
		}
		return rfd;
	}
	
	public String getOutStandingAmount() throws Exception
	{
		String outAmt= outstanding.getText();
		
		if(outAmt.length()==8)
		{
			String temp2 = outAmt.substring(0, 1);
			String temp3 = outAmt.substring(2, 8);
			outAmt = temp2+temp3;
		}
		else if(outAmt.length()==9)
		{
			String temp2 = outAmt.substring(0, 2);
			String temp3 = outAmt.substring(3, 9);
			outAmt = temp2+temp3;
		}
		return outAmt;
	}
	
	public String getPatientSummaryNetAmount()
	{
		int headersize= tbl_PatientSummaryHeaders.size(), NetAmountPosition=0;

		for(int j=1;j<=headersize;j++)
		{
			if("Net Amount".equalsIgnoreCase(tbl_PatientSummaryHeaders.get(j).getText()))
			{
				NetAmountPosition=j+2;
				break;
			}
		}
		
		String path= "//div[1]/fieldset/div/table/tbody/tr["+tbl_PatientSummaryRows.size()+"]/td["+NetAmountPosition+"]";
		WebElement NetAmount= driver.findElement(By.xpath(path));

		Log.info("      NetAmount in patient list is : "+NetAmount.getText());
		String Amt = NetAmount.getText();
		
		if(Amt.length()==8)
		{
			String temp2 = Amt.substring(0, 1);
			String temp3 = Amt.substring(2, 8);
			Amt = temp2+temp3;
		}
		else if(Amt.length()==9)
		{
			String temp2 = Amt.substring(0, 2);
			String temp3 = Amt.substring(3, 9);
			Amt = temp2+temp3;
		}
		return Amt;
	}
	
	public String getPatientSummaryDiscountAmount()
	{
		int rowsize= tbl_PatientSummaryHeaders.size(), DiscountAmountPosition=0;

		for(int j=1;j<=rowsize;j++)
		{
			if("Discount Amount".equalsIgnoreCase(tbl_PatientSummaryHeaders.get(j).getText()))
			{
				DiscountAmountPosition=j+2;
				break;
			}
		}
		
		String path= "//div[1]/fieldset/div/table/tbody/tr["+tbl_PatientSummaryRows.size()+"]/td["+DiscountAmountPosition+"]";
		WebElement DiscountAmount= driver.findElement(By.xpath(path));

		Log.info("      NetAmount in patient list is : "+DiscountAmount.getText());
		String Amt = DiscountAmount.getText();
		
		if(Amt.length()==8)
		{
			String temp2 = Amt.substring(0, 1);
			String temp3 = Amt.substring(2, 8);
			Amt = temp2+temp3;
		}
		else if(Amt.length()==9)
		{
			String temp2 = Amt.substring(0, 2);
			String temp3 = Amt.substring(3, 9);
			Amt = temp2+temp3;
		}
		return Amt;
	}
	
	public String getPatientSummaryPaidAmount()
	{
		int rowsize= tbl_PatientSummaryHeaders.size(), PaidAmountPosition=0;

		for(int j=1;j<=rowsize;j++)
		{
			if("Paid Amount".equalsIgnoreCase(tbl_PatientSummaryHeaders.get(j).getText()))
			{
				PaidAmountPosition=j+2;
				break;
			}
		}
		
		String path= "//div[1]/fieldset/div/table/tbody/tr["+tbl_PatientSummaryRows.size()+"]/td["+PaidAmountPosition+"]";
		WebElement PaidAmount= driver.findElement(By.xpath(path));

		Log.info("      NetAmount in patient list is : "+PaidAmount.getText());
		String Amt = PaidAmount.getText();
		
		if(Amt.length()==8)
		{
			String temp2 = Amt.substring(0, 1);
			String temp3 = Amt.substring(2, 8);
			Amt = temp2+temp3;
		}
		else if(Amt.length()==9)
		{
			String temp2 = Amt.substring(0, 2);
			String temp3 = Amt.substring(3, 9);
			Amt = temp2+temp3;
		}
		return Amt;
	}
	
	public String getTotalAmountOfService(String serviceName)
	{
		int rowsize= tbl_PatientServiceDetailsHeaders.size(), TotalAmountPosition=0, ServiceNamePosition=0;
		String amount = null;

		for(int j=0;j<rowsize;j++)
		{
			if("Service Name".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				ServiceNamePosition=j+1;
			}
			else if("Discount Amount".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				TotalAmountPosition=j+1;
				break;
			}
		}
		
		String allservicepath = ".//tbody/tr/td["+ServiceNamePosition+"]";
		List<WebElement> serviceList = tbl_PatientServiceDetails.findElements(By.xpath(allservicepath));
		
		for(int i=2;i<=serviceList.size();i++)
		{
			String servicepath = ".//tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
			WebElement service = tbl_PatientServiceDetails.findElement(By.xpath(servicepath));
			if(service.getText().equalsIgnoreCase(serviceName))
			{
				String valuePath = ".//tr["+i+"]/td["+TotalAmountPosition+"]";
				WebElement value =  tbl_PatientServiceDetails.findElement(By.xpath(valuePath));
				amount = value.getText();
				break;
			}
		}
		
		if(amount.length()==8)
		{
			String temp2 = amount.substring(0, 1);
			String temp3 = amount.substring(2, 8);
			amount = temp2+temp3;
		}
		else if(amount.length()==9)
		{
			String temp2 = amount.substring(0, 2);
			String temp3 = amount.substring(3, 9);
			amount = temp2+temp3;
		}
		return amount;
	}
	
	public String getDiscountAmountOfService(String serviceName)
	{
		int rowsize= tbl_PatientServiceDetailsHeaders.size(), DiscountAmountPosition=0, ServiceNamePosition=0;
		String amount = null;

		for(int j=0;j<rowsize;j++)
		{
			if("Service Name".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				ServiceNamePosition=j+1;
			}
			else if("Discount Amount".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				DiscountAmountPosition=j+1;
				break;
			}
		}
		
		String allservicepath = ".//tbody/tr/td["+ServiceNamePosition+"]";
		List<WebElement> serviceList = tbl_PatientServiceDetails.findElements(By.xpath(allservicepath));
		
		for(int i=2;i<=serviceList.size();i++)
		{
			String servicepath = ".//tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
			WebElement service = tbl_PatientServiceDetails.findElement(By.xpath(servicepath));
			if(service.getText().equalsIgnoreCase(serviceName))
			{
				String valuePath = ".//tr["+i+"]/td["+DiscountAmountPosition+"]";
				WebElement value =  tbl_PatientServiceDetails.findElement(By.xpath(valuePath));
				amount = value.getText();
				break;
			}
		}
		
		if(amount.length()==8)
		{
			String temp2 = amount.substring(0, 1);
			String temp3 = amount.substring(2, 8);
			amount = temp2+temp3;
		}
		else if(amount.length()==9)
		{
			String temp2 = amount.substring(0, 2);
			String temp3 = amount.substring(3, 9);
			amount = temp2+temp3;
		}
		
		return amount;
	}
	
	public String getNetAmountOfService(String serviceName)
	{
		int rowsize= tbl_PatientServiceDetailsHeaders.size(), NetAmountPosition=0, ServiceNamePosition=0;
		String amount = null;

		for(int j=0;j<rowsize;j++)
		{
			if("Service Name".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				ServiceNamePosition=j+1;
			}
			else if("Net Amount".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				NetAmountPosition=j+1;
				break;
			}
		}
		
		String allservicepath = ".//tbody/tr/td["+ServiceNamePosition+"]";
		List<WebElement> serviceList = tbl_PatientServiceDetails.findElements(By.xpath(allservicepath));
		
		for(int i=2;i<=serviceList.size();i++)
		{
			String servicepath = ".//tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
			WebElement service = tbl_PatientServiceDetails.findElement(By.xpath(servicepath));
			if(service.getText().equalsIgnoreCase(serviceName))
			{
				String valuePath = ".//tr["+i+"]/td["+NetAmountPosition+"]";
				WebElement value =  tbl_PatientServiceDetails.findElement(By.xpath(valuePath));
				amount = value.getText();
				break;
			}
		}
		
		if(amount.length()==8)
		{
			String temp2 = amount.substring(0, 1);
			String temp3 = amount.substring(2, 8);
			amount = temp2+temp3;
		}
		else if(amount.length()==9)
		{
			String temp2 = amount.substring(0, 2);
			String temp3 = amount.substring(3, 9);
			amount = temp2+temp3;
		}
		
		return amount;
	}
	
	public void SelectServiceforRefund(String serviceName)
	{
		int headersize= tbl_PatientServiceDetailsHeaders.size(), SelectPosition=0, ServiceNamePosition=0;
		for(int j=0;j<headersize;j++)
		{
			if("Service Name".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				ServiceNamePosition=j+1;
			}
			else if("".equalsIgnoreCase(tbl_PatientServiceDetailsHeaders.get(j).getText()))
			{
				SelectPosition=j+1;
				break;
			}
		}
		
		String allservicepath = ".//tbody/tr/td["+ServiceNamePosition+"]";
		List<WebElement> serviceList = tbl_PatientServiceDetails.findElements(By.xpath(allservicepath));
		
		for(int i=2;i<=serviceList.size();i++)
		{
			String servicepath = ".//tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
			WebElement service = tbl_PatientServiceDetails.findElement(By.xpath(servicepath));
			if(service.getText().equalsIgnoreCase(serviceName))
			{
				String valuePath = ".//tr["+i+"]/td["+SelectPosition+"]/input";
				WebElement value =  tbl_PatientServiceDetails.findElement(By.xpath(valuePath));
				value.click();
				break;
			}
		}
	}
/*	
	public String verifydueamount() throws Exception
	{    		
             String pNtamt=getPatientSummaryNetAmount(), pdAmt= getPatientSummaryPaidAmount(), outamt= getOutStandingAmount();

			 String expectedDue = Float.toString(Float.parseFloat(pNtamt)-(Float.parseFloat(pdAmt)+Float.parseFloat(getRefundAmount())));

			 int posi = expectedDue.indexOf(".");
			 expectedDue = expectedDue.substring(0, posi);
			 
			 int iexpectedDue = Integer.parseInt(expectedDue);
			 
	         if (iexpectedDue <=0)
	        	 iexpectedDue=0;
	        
	         expectedDue= Integer.toString(iexpectedDue);

			Assert.assertTrue(outamt.startsWith(expectedDue),"*** Refund Cancel --> Due amount not showing correctly****");
			return expectedDue;
	}
*/	
	public String verifyOutstandingAmount(String sumOfRefundAmt) throws Exception
	{
		String pNtamt=getPatientSummaryNetAmount(), pdAmt= getPatientSummaryPaidAmount(), actualOutstanding= getOutStandingAmount();
		double expectedOutstanding;
		
		expectedOutstanding = Float.parseFloat(pNtamt)-(Float.parseFloat(pdAmt)+Float.parseFloat(sumOfRefundAmt));
		
		if(expectedOutstanding<0.00)
			expectedOutstanding = 0.00;
		
		String s_expectedOutstanding = Utils.roundUsingDecimalFormat(expectedOutstanding);
		Assert.assertTrue(actualOutstanding.startsWith(s_expectedOutstanding),"*** Outstanding amount not showing correctly ***");
		return actualOutstanding;
	}
	
	public String verifyRefundAmount(String sumOfRefundAmt) throws Exception
	{
		String pNtamt=getPatientSummaryNetAmount(), pdAmt= getPatientSummaryPaidAmount(), actualRefund= getRefundAmount();
		double expectedRefund;
		
		expectedRefund = (Float.parseFloat(pdAmt)+Float.parseFloat(sumOfRefundAmt)-Float.parseFloat(pNtamt));

		if(expectedRefund<0)
			expectedRefund = 0.00;
		
		String s_expectedRefund = Utils.roundUsingDecimalFormat(expectedRefund);
		Assert.assertTrue(actualRefund.startsWith(s_expectedRefund),"*** Refund amount not showing correctly ***");
		return actualRefund;
	}
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Click action performed on save Button");
	}
	
/*	
	public String verifyRefundAmount(String sServiceName1) throws Exception
	{		
        List<WebElement> patientServiceTableRows = tbl_selectService.findElements(By.tagName("tr"));
		//Get column position
		List<WebElement> th=tbl_selectService.findElements(By.tagName("th"));

		int ServiceNamePosition=0,NetAmountPosition=0;
		for(int j=0;j<th.size();j++)
		{	
			if("Service Name".equalsIgnoreCase(th.get(j).getText()))
			{
				ServiceNamePosition=j+1;
			}
			if("Net Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				NetAmountPosition=j+1;
				}
		} 
		
		String netamount=" ";
		for(int i=2;i<patientServiceTableRows.size();i++)
		{
			String servicepath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
			String netpath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td["+NetAmountPosition+"]";
			WebElement service =  driver.findElement(By.xpath(servicepath));
			WebElement NetAmount =  driver.findElement(By.xpath(netpath));			
			if(service.getText().trim().equalsIgnoreCase(sServiceName1))
			{
				 
				 netamount= NetAmount.getText();
			
				 if(netamount.length()==8)
					{
						String temp2 = netamount.substring(0, 1);
						String temp3 = netamount.substring(2, 8);
						netamount = temp2+temp3;
					}
				
					else if(netamount.length()==9)
					{
						String temp2 = netamount.substring(0, 2);
						String temp3 = netamount.substring(3, 9);
						netamount = temp2+temp3;
					}	 
			}
			}
		 float service1amt = Float.parseFloat(netamount);
		
		 //WebElement outstandingamount = driver.findElement(By.xpath("//ul[@class='timeline']/li[5]/div/table/tbody/tr/td[2]"));
		 //String outamt= outstandingamount.getText();

		// WebElement Paidamount = driver.findElement(By.xpath("//table[@class='table service-table scroll']/tbody/tr[2]/td[7]"));
		 //String paidamt= Paidamount.getText();
		
		 String pdAmt= getPatientPaidAmount();

		 
		 float pdamt=Float.parseFloat(pdAmt);
		 
		 String sValueofrefund= Float.toString(service1amt+pdamt);

		 String ntamt=getPatientNetAmount();
		 //this.getPatientNetAmount();
		 //WebElement ptNetAmount = driver.findElement(By.xpath("//div[@class='row']/div[1]/fieldset/div/table/tbody/tr[2]/td[5]"));
		 //String ntamt= ptNetAmount.getText();
		 
		 float Pntamt=Float.parseFloat(ntamt);
		 float calRefundValue= Float.parseFloat(sValueofrefund);
		 String finalCalValue= Float.toString(calRefundValue-Pntamt);
         String rfdamt= getRefundAmount();
		 String value = finalCalValue;
		 int posi = value.indexOf(".");
         value = value.substring(0, posi);
         int due = Integer.parseInt(value);
          
         if (due <=0)
         {
        	 due=0;
         }
        String calVal= Integer.toString(due);
        
         Assert.assertTrue("***Amount not updated as expected***",rfdamt.startsWith(calVal));
         Thread.sleep(2000);
		return sValueofrefund;
		
	}

/*  
 * 

	public String verifyDueAmount(String sValueofrefund) throws Exception
	{
		     String sRefund= verifyRefundAmount(sValueofrefund);
		     float fRefund = Float.parseFloat(sRefund);		    
             String pNtamt=getPatientNetAmount(); 
             Thread.sleep(1000);
			 float fntamt=Float.parseFloat(pNtamt); 
			 
			 Thread.sleep(1000);
			 WebElement outstandingamount = driver.findElement(By.xpath("//ul[@class='timeline']/li[5]/div/table/tbody/tr/td[2]"));
			 String outamt= outstandingamount.getText();
			 Thread.sleep(2000);
			 String verifydue = Float.toString(fRefund-fntamt);
			 String value = verifydue;
			 Thread.sleep(2000);
			 int posi = value.indexOf(".");
	         value = value.substring(0, posi); 
			 int vrfydue = Integer.parseInt(value);
	         
	         if (vrfydue <=0)
	         {
	        	 Thread.sleep(1000);
	        	 vrfydue=0;
	         }
	         Thread.sleep(1000);
	        String verifydueAmt= Integer.toString(vrfydue);
			Assert.assertTrue("***Due amount does not match with the outstanding amount***",outamt.startsWith(verifydueAmt));
			return verifydueAmt;
	}
/*
	public String verifyRefundAmount(String sServiceName1,String sServiceName2) throws Exception
	{
		
		
        List<WebElement> patientServiceTableRows = tbl_selectService.findElements(By.tagName("tr"));
		//Get column position
		List<WebElement> th=tbl_selectService.findElements(By.tagName("th"));

		int ServiceNamePosition=0,NetAmountPosition=0;
		for(int j=0;j<th.size();j++)
		{	
			if("Service Name".equalsIgnoreCase(th.get(j).getText()))
			{
		
				ServiceNamePosition=j+1;
				
			}
			if("Net Amount".equalsIgnoreCase(th.get(j).getText()))
				{
				
				NetAmountPosition=j+1;
				
				}
		}
		 
		
		String netamount=" ", netamount1=" ";
		for(int i=2;i<=patientServiceTableRows.size();i++)
		{
		//	System.out.println( "RowSize: "+patientServiceTableRows.size());
			String servicepath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
			String netpath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td["+NetAmountPosition+"]";
			WebElement service =  driver.findElement(By.xpath(servicepath));
			WebElement NetAmount =  driver.findElement(By.xpath(netpath));
			
			
			if(service.getText().trim().equalsIgnoreCase(sServiceName1))
			{
				
				 netamount= NetAmount.getText();
				
				 //System.out.println("netamount"+ netamount.length());
				 if(netamount.length()==8)
					{
					
						String temp2 = netamount.substring(0, 1);
						String temp3 = netamount.substring(2, 8);
						netamount = temp2+temp3;
						
						
					}
				
					else if(netamount.length()==9)
					{
						
						String temp2 = netamount.substring(0, 2);
						String temp3 = netamount.substring(3, 9);
						netamount = temp2+temp3;
						
					}
					 
			}
			else if (service.getText().trim().equalsIgnoreCase(sServiceName2))
			{

				netamount1= NetAmount.getText();
				if(netamount1.length()==8)
				{
	
					String temp2 = netamount1.substring(0, 1);
					String temp3 = netamount1.substring(2, 8);
					netamount1 = temp2+temp3;
					
					
				}
				else if(netamount1.length()==9)
				{
					
					String temp2 = netamount1.substring(0, 2);
					String temp3 = netamount1.substring(3, 9);
					netamount1 = temp2+temp3;
					 
				 
				}
			}
		}
		

		 float service1amt = Float.parseFloat(netamount);

		 
		 float service2amt = Float.parseFloat(netamount1);

		
		 float sumOfCancelledAmt = service1amt+service2amt;

		 //WebElement outstandingamount = driver.findElement(By.xpath("//ul[@class='timeline']/li[5]/div/table/tbody/tr/td[2]"));
		 //String outamt= outstandingamount.getText();

		// WebElement Paidamount = driver.findElement(By.xpath("//table[@class='table service-table scroll']/tbody/tr[2]/td[7]"));
		 //String paidamt= Paidamount.getText();
		 
		 String pdAmt= getPatientPaidAmount();

		 float fpdamt=Float.parseFloat(pdAmt);

		 float sValueofrefund= sumOfCancelledAmt+fpdamt;

		 
		 //this.getPatientNetAmount();
		 //WebElement ptNetAmount = driver.findElement(By.xpath("//div[@class='row']/div[1]/fieldset/div/table/tbody/tr[2]/td[5]"));
		 //String ntamt= ptNetAmount.getText();
		 
		 String ntamt=getPatientNetAmount();

		 float Pntamt=Float.parseFloat(ntamt);


		 String finalCalValue= Float.toString(sValueofrefund-Pntamt);


		 WebElement Refundamount =  driver.findElement(By.xpath(".//li[4]//table[@class='table invoice_table']//td[2]"));
		 //getRefundAmount
		 
		 //String rfdamt= getRefundAmount.
		 //String rfdamt= Refundamount.getText();
		String rfdamt= getRefundAmount();

		 String value = finalCalValue;

		 int posi = value.indexOf(".");
         value = value.substring(0, posi);

         int due = Integer.parseInt(value);
         if (due <=0)
         {
        	 
        	 due=0;
        	 
         }
        String calVal= Integer.toString(due);

        
         Assert.assertTrue("***Amount not updated as expected***",rfdamt.startsWith(calVal)); 
        
         return Float.toString(sumOfCancelledAmt); 
	}
	/*
	public void verifyOutAmount() throws Exception
	{
	
		String sServiceName2 = "";
		String re = ;
		this.verifyRefundAmount(sServiceName1, sServiceName2);
		
		//String due = verifyRefundAmount(finalCalValue);
		 //String ntamt=getPatientNetAmount();
	}
		/*
		 Math.round(calValue);
		 System.out.println("calamt: "+ Math.round(calValue));
		 String finalCalValue= Float.toString((calValue));
		  */
		 
         //System.out.println("sumOfCancelledAmt: "+sumOfCancelledAmt);
		 
		  
/*
 *          int refund = value.lastIndexOf(".");
			int posi = value.indexOf("@");

			value = value.substring(0, posi);

			posi = value.lastIndexOf(".");	

			value = value.substring(posi + 1);

			return value;*/
		  //return refund;
		  /*
		 if(rfdamt.length()==5)
			{
			 System.out.println("String0");
			 
			    rfdamt.substring(2);
			   Thread.sleep(2000);
				//String temp2 = rfdamt.substring(0, 2);
				//String temp3 = rfdamt.substring(0, 3);
				//rfdamt = temp2+temp3;
			   return rfdamt;
			}
		 if (rfdamt.length()==6)
		 {
			 System.out.println("string");
			 Thread.sleep(2000);
			 rfdamt.substring(3); 
			 return rfdamt;
		 }
	   */
/*		
 * 
 */  


	
/*	
	public void selectService(String sServiceName1) throws Exception
	{
		List<WebElement> patientServiceTableRows = tbl_selectService.findElements(By.tagName("tr"));
		
		//Get column position
		List<WebElement> th=tbl_selectService.findElements(By.tagName("th"));
		int ServiceNamePosition=0;
		for(int j=0;j<th.size();j++)
		{	
			if("Service Name".equalsIgnoreCase(th.get(j).getText()))
				{
					ServiceNamePosition=j+1;
					
				}
		}	
		
	for(int i=2;i<=patientServiceTableRows.size();i++)
	{
		String servicepath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
		WebElement service =  driver.findElement(By.xpath(servicepath));
		
		if(service.getText().trim().equalsIgnoreCase(sServiceName1))
		{
			String selectpath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td[6]/input";
			driver.findElement(By.xpath(selectpath)).click();
			Log.info("Services are successfully cancelled");
		}
		
	}
	Thread.sleep(1000);
}
	public void selectService(String sServiceName1,String sServiceName2) throws Exception
	{
		List<WebElement> patientServiceTableRows = tbl_selectService.findElements(By.tagName("tr"));
		
		//Get column position
		List<WebElement> th=tbl_selectService.findElements(By.tagName("th"));

		int ServiceNamePosition=0;
		for(int j=0;j<th.size();j++)
		{	
			if("Service Name".equalsIgnoreCase(th.get(j).getText()))
				{
					ServiceNamePosition=j+1;
					
				}
				
			//	Log.info("Click action is performed on assigned service of the table");
		}
		
		for(int i=2;i<=patientServiceTableRows.size();i++)
		{
			String servicepath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td["+ServiceNamePosition+"]";
			WebElement service =  driver.findElement(By.xpath(servicepath));
			if(service.getText().trim().equalsIgnoreCase(sServiceName1))
			{
				
				
				String selectpath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td[6]/input";
				driver.findElement(By.xpath(selectpath)).click();
			}
//				Assert.assertEquals("***Status not updated after sample collection***",estatus,status.getText());
//				Log.info("Current Status of the patient : "+status.getText());
//				Assert.assertTrue("***Barcode not updated after sample collection***",barcode.getText().startsWith(Constant.BarCodeNumber));
			
			else if (service.getText().trim().equalsIgnoreCase(sServiceName2))
			{
				
				String selectpath = "//fieldset[@class='cust-fieldset1']/div/table/tbody/tr["+i+"]/td[6]/input";
				driver.findElement(By.xpath(selectpath)).click();
				
			}
		}
		Thread.sleep(1000);
	}
*/	

	
}
	