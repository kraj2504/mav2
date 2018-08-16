package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Log;

public class PO_InvoicePayment 
{
 WebDriver driver;
 
 public PO_InvoicePayment(WebDriver driver)
 {
	 this.driver=driver; 
 }
 
 	@FindBy(how=How.ID,using ="SearchInvoice")
 	WebElement txtbx_Search;
 	
 	@FindBy(how=How.ID, using="txtamts_1")
 	WebElement txtbx_CashAmount;
 	
 	@FindBy(how=How.XPATH, using="//a[text()='Cheque Amount']")
 	WebElement lbl_ChequeAmount;
 	
 	@FindBy(how=How.XPATH, using="//a[text()='NEFT']")
 	WebElement lbl_NEFTAmount;
 	
 	@FindBy(how=How.ID, using="txtamts_2")
 	WebElement txtbx_ChequeAmount;
 	
 	@FindBy(how=How.ID, using="txtamts_3")
 	WebElement txtbx_NEFTAmount;
 	
 	@FindBy(how=How.XPATH, using="//input[@ng-model='cheque.Number']")
 	WebElement txtbx_ChequeNumber;
 	
 	@FindBy(how=How.XPATH, using="//input[@ng-model='cheque.Date']")
 	WebElement txtbx_ChequeDate;
 	
 	@FindBy(how=How.XPATH, using="//input[@ng-model='cheque.BankName']")
 	WebElement txtbx_BankName;
 	
 	@FindBy(how=How.XPATH, using="//input[@ng-model='Neft.TransactionNo']")
 	WebElement txtbx_NEFTTransactionNumber;
 	
 	@FindBy(how=How.NAME, using="radInv")
 	WebElement radiobtn_InvoicePayment;
 	
 	@FindBy(how=How.NAME, using="radAdv")
 	WebElement radiobtn_AdvancedAmount;
 	
 	@FindBy(how=How.XPATH, using="//input[@ng-model='CheckAdvanceAmount']")
 	WebElement chxbx_AdjustAdvanceAmount;
 	
 	@FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[2]/b")
 	WebElement TotalBillAmount;
 	
 	@FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[3]/b")
 	WebElement TotalNetAmount;
 	
 	@FindBy(how=How.XPATH, using="//tr[@ng-show='TotalRow']/td[4]/b")
 	WebElement TotalReceivableAmount;
 	
 	@FindBy(how=How.XPATH, using="//div[@class='col-sm-2']/fieldset[2]/div")
 	WebElement tbl_InvoiceNumber;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']")
 	WebElement tbl_TransactionDetails;
 	
 	@FindBy(how=How.XPATH, using="//button[@class='btn btn-flat dropdown-toggle']")
 	WebElement ToggleIcon;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[1]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_InvoiceAmount;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[2]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_TotalOpeningBalance;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[3]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_LastTransaction;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[4]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_TotalReceivableAmount;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[5]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_AmountReceived;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[6]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_AdvancedAdjustedAmount;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[7]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_BalanceAmount;
 	
 	@FindBy(how=How.XPATH, using="//ul[@class='timeline']/li[8]/div/table/tbody/tr/td[2]/span")
 	WebElement lbl_ExcessAmount;
 	
 	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
 	WebElement btn_Save;
 	
 	@FindBy(how=How.XPATH, using="//i[@class='fa fa-refresh']")
 	WebElement btn_Refresh;
 	
 	@FindBy(how=How.ID, using="CurrencyType")
 	WebElement drpdwn_CurrencyType;
 	
 	@FindBy(how=How.XPATH, using="//input[@id='date']")
 	WebElement txtbx_chequedate;
 	
 	@FindBy(how=How.XPATH, using="//td[@class='ng-binding ng-scope highlighted']")
 	WebElement cheque_Month;
 	
 	@FindBy(how=How.XPATH, using="//td[@class='ng-binding ng-scope highlighted today']")
 	WebElement cheque_Date;
 	
 	@FindBy(how=How.XPATH, using="//div[@class='col-sm-12']/table/tbody/tr[2]/td[3]")
 	WebElement InvoiceNumber;
 	
 	public void selectInvoice()
 	{
 		 List<WebElement> rows =tbl_InvoiceNumber.findElements(By.tagName("tr"));
 		 List<WebElement> th= tbl_InvoiceNumber.findElements(By.tagName("th"));
 		int SelectPosition=0;
		for(int i=2;i<=rows.size();i++)
		{
			
		for(int j=0;j<th.size();j++)
		{	
			
			if("Select".equalsIgnoreCase(th.get(j).getText()))
				{
				SelectPosition=j+1;
				System.out.println("InvoiceNumPosition  : "+ SelectPosition);
				}
		}
		
			
			String seelctpath =("//div[@class='col-sm-12']/table/tbody/tr["+i+"]/td["+SelectPosition+"]");
		    String invoiceValue =driver.findElement(By.xpath(seelctpath)).getText();
			System.out.println("invoiceValue : "+ invoiceValue);
		  Log.info("      Check the invoice amount Value: " + invoiceValue);
			   
		   
   }	
 	}
 	
 	public void searchClient(String sReferralName)
 	{
 		txtbx_Search.click();
 		txtbx_Search.sendKeys(sReferralName);
 		Log.info("      Client name is searched");
 		txtbx_Search.sendKeys(Keys.ENTER);
 	}
 	
 	public String getInvoiceNumber()
 	{
 		System.out.println("InvoiceNumber:"+InvoiceNumber.getText());
 		return InvoiceNumber.getText();
 	}
 	public void clickSave()
	{
 		btn_Save.click();
		Log.info("      Click action performed on save Button");
	}
 	
 	public void EnterCashAmount(String amount)
	{
		txtbx_CashAmount.sendKeys(amount);
		Log.info("      Given amount : "+txtbx_CashAmount.getAttribute("value"));
		
	}
 	
 	public String getcashAmount()
 	{
 		System.out.println("CashAmount:"+txtbx_CashAmount.getText());
 		return txtbx_CashAmount.getText(); 
 	}
 	
 	
 	public void EnterChequeAmount(String amount)
	{
 		lbl_ChequeAmount.click();
 		txtbx_ChequeAmount.sendKeys(amount);
 		txtbx_ChequeNumber.sendKeys("123456");
 		txtbx_ChequeDate.sendKeys("");
 		txtbx_ChequeDate.click();
    	//driver.findElement(By.xpath("//div[@class='moment-picker-container year-view open']"))
 		cheque_Month.click();
 		cheque_Date.click();
 		txtbx_BankName.sendKeys("state bank of india");
		Log.info("      Amount is entered via Cheque Amount");
	}
 	
 	public void EnterNEFTAmount(String amount)
	{
 		lbl_NEFTAmount.click();
 		txtbx_NEFTAmount.sendKeys(amount);
 		txtbx_NEFTTransactionNumber.sendKeys("1234567890");
		Log.info("      Amount is entered via NEFT Amount");
	}
 	
 	public void clickAdvancedAmount()
 	{
 		radiobtn_AdvancedAmount.click();
 		Log.info("      Advanced Amount is clicked to pay advance amount");
 	}
	
 	public void enterAdvanceAmountCash(String AdvanceAmount)
 	{
 		txtbx_CashAmount.sendKeys(AdvanceAmount);
 		Log.info("      Advanced Amount is entered via Cash Amount");
 	}
 	
 	public void enterAdvanceAmountCheque(String AdvanceAmount)
 	{
 		lbl_ChequeAmount.click();
 		txtbx_ChequeAmount.sendKeys(AdvanceAmount);
 		txtbx_ChequeNumber.sendKeys("123456");
 		//txtbx_ChequeDate.sendKeys("");
 		txtbx_BankName.sendKeys("state bank of india");
		Log.info("      Advanced Amount is entered via Cheque Amount");
 	}
 	
 	public void enterAdvanceAmountNEFT(String AdvanceAmount)
 	{
 		lbl_NEFTAmount.click();
 		txtbx_NEFTAmount.sendKeys(AdvanceAmount);
 		txtbx_NEFTTransactionNumber.sendKeys("1234567890");
		Log.info("      Advanced Amount is entered via NEFT Amount");
 	}
 	
 	
 	public void adjustAdvanceCollectedAmount()
 	{
 		chxbx_AdjustAdvanceAmount.click();
 		
 		Log.info("      Adjust Advanced Amount is clicked to replace the amount");
 		
 	}
 	
 	public String getBalanceAmount()
 	{
 		String balanceAmt=lbl_BalanceAmount.getText();
 		System.out.println("balanceAmt:"+balanceAmt);
 		return balanceAmt;
 	}
 	
 	public String getInvoiceAmount()
 	{
 		String invoiceAmt=lbl_InvoiceAmount.getText();
 		if(invoiceAmt.length()==8)
		{
			String temp2 = invoiceAmt.substring(0, 1);
			String temp3 = invoiceAmt.substring(2, 8);
			invoiceAmt = temp2+temp3;
		}
		else if(invoiceAmt.length()==9)
		{
			String temp2 = invoiceAmt.substring(0, 2);
			String temp3 = invoiceAmt.substring(3, 9);
			invoiceAmt = temp2+temp3;
		}
 		System.out.println("invoiceAmt:"+invoiceAmt);
 		return invoiceAmt;
 	}
 	
 	public String getTotalReceivableAmount()
 	{
 		String totalReceivableAmt=lbl_TotalReceivableAmount.getText();
 		if(totalReceivableAmt.length()==8)
		{
			String temp2 = totalReceivableAmt.substring(0, 1);
			String temp3 = totalReceivableAmt.substring(2, 8);
			totalReceivableAmt = temp2+temp3;
		}
		else if(totalReceivableAmt.length()==9)
		{
			String temp2 = totalReceivableAmt.substring(0, 2);
			String temp3 = totalReceivableAmt.substring(3, 9);
			totalReceivableAmt = temp2+temp3;
		}
 		System.out.println("totalReceivableAmt:"+totalReceivableAmt);
 		return totalReceivableAmt;
 	}
 	
 	public String getExcessAmount()
 	{
 		String excessAmt=lbl_ExcessAmount.getText();
 		if(excessAmt.length()==8)
		{
			String temp2 = excessAmt.substring(0, 1);
			String temp3 = excessAmt.substring(2, 8);
			excessAmt = temp2+temp3;
		}
		else if(excessAmt.length()==9)
		{
			String temp2 = excessAmt.substring(0, 2);
			String temp3 = excessAmt.substring(3, 9);
			excessAmt = temp2+temp3;
		}
 		System.out.println("excessAmt:"+excessAmt);
 		return excessAmt;
 	}
 	
 	public String getAmountReceived()
 	{
 		String amtReceived =lbl_AmountReceived.getText();
 		if(amtReceived.length()==8)
		{
			String temp2 = amtReceived.substring(0, 1);
			String temp3 = amtReceived.substring(2, 8);
			amtReceived = temp2+temp3;
		}
		else if(amtReceived.length()==9)
		{
			String temp2 = amtReceived.substring(0, 2);
			String temp3 = amtReceived.substring(3, 9);
			amtReceived = temp2+temp3;
		}
 		System.out.println("amtReceived:"+amtReceived);
 		return amtReceived;
 	}
 	
 	public String getAdvancedAdjustedAmount()
 	{
 		String advAmt =lbl_AdvancedAdjustedAmount.getText();
 		if(advAmt.length()==8)
		{
			String temp2 = advAmt.substring(0, 1);
			String temp3 = advAmt.substring(2, 8);
			advAmt = temp2+temp3;
		}
		else if(advAmt.length()==9)
		{
			String temp2 = advAmt.substring(0, 2);
			String temp3 = advAmt.substring(3, 9);
			advAmt = temp2+temp3;
		}
 		System.out.println("amtReceived:"+advAmt);
 		return advAmt;
 	}
}
