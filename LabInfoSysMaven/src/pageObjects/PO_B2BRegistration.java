
package pageObjects;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
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

public class PO_B2BRegistration
{
	WebDriver driver;
	
	public PO_B2BRegistration(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using="ClientName")
	WebElement AutoTxtbx_ClientName;
	
	@FindBy(how = How.ID, using="idDoctorList")
	WebElement AutoTxtbx_DoctorName;
	
	@FindBy(how = How.ID, using="RiderID")
	WebElement drpdwn_Rider;
	
	@FindBy(how = How.ID, using="ShwTitle")
	WebElement drpdwn_Title;
	
	@FindBy(how = How.ID, using="ShwTitle")
	WebElement txtbx_Name;
	
	@FindBy(how = How.ID, using="ShwGender")
	WebElement drpdwn_Gender;
	
	@FindBy(how = How.ID, using="ShwAge")
	WebElement txtbx_Age;
	
	@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.AgeType']")
	WebElement drpdwn_AgeType;
	
	@FindBy(how = How.ID, using="SamCltDate")
	WebElement calctrl_sampleCollectedDate;
	
	@FindBy(how = How.XPATH, using=".//input[@ng-model='CashAmount']")
	WebElement txtbx_CashAmount;
	
	@FindBy(how = How.XPATH, using="//input[@ng-model='row.ServiceInfo']")
	WebElement AutoTxtbx_ServiceName;
	
	@FindBy(how = How.XPATH, using="//table[@id='Servicetable']/tbody/tr[2]/td[2]/div/input")
	WebElement AutoTxtbx_ServiceName2;
	
	@FindBy(how = How.XPATH, using="//table[@id='Servicetable']/tbody/tr[3]/td[2]/div/input")
	WebElement AutoTxtbx_ServiceName3;
	
	@FindBy(how = How.XPATH, using="//table[@id='Servicetable']/tbody/tr[4]/td[2]/div/input")
	WebElement AutoTxtbx_ServiceName4;
	
	@FindBy(how = How.XPATH, using="//i[@class = 'fa fa-save']")
	WebElement btn_save;
	
	@FindBy(how = How.XPATH, using="//i[@class = 'fa fa-eraser']")
	WebElement btn_Clear;
	
	@FindBy(how = How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_Refresh;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.GrossAmount']")
	WebElement GrossAmount;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.CollectedAmount']")
	WebElement CollectedAmount;
	
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.DueAmount']")
	WebElement DueAmount;
		
	public String getTotalAmountOfAllServices() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		List<WebElement> e = driver.findElements(By.xpath(".//*[@id='Servicetable']/tbody/tr/td[7]/label"));
		DecimalFormat df = new DecimalFormat("#.##");
		double ActualTotalAmount = 0.00;
		for(int i=3;i<(e.size() + 2);i++)
		{	
			String amountPath=".//*[@id='Servicetable']/tbody/tr["+(i)+"]/td[7]/label";
			String Amount =driver.findElement(By.xpath(amountPath)).getText();
			ActualTotalAmount = ActualTotalAmount + Double.parseDouble(Amount);
		}
		return df.format(ActualTotalAmount);
	}
	
	public String getNetAmountOfAllServices() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		List<WebElement> e = driver.findElements(By.xpath(".//*[@id='Servicetable']/tbody/tr/td[10]/label"));
		DecimalFormat df = new DecimalFormat("#.##");
		double ActualTotalAmount = 0.00;
		for(int i=3;i<(e.size() + 2);i++)
		{	
			String amountPath=".//*[@id='Servicetable']/tbody/tr["+(i)+"]/td[10]/label";
			String Amount =driver.findElement(By.xpath(amountPath)).getText();
			ActualTotalAmount = ActualTotalAmount + Double.parseDouble(Amount);
		}
		return df.format(ActualTotalAmount);
	}
		
	public void selectTitle(String sTitle) throws Exception
	{
		Utils.waitForElement(drpdwn_Title);
		Select value=new Select(drpdwn_Title);
		value.selectByVisibleText(sTitle);
		String selectedOption = new Select(drpdwn_Title).getFirstSelectedOption().getText();
		Assert.assertEquals(selectedOption,sTitle, "*** Registration --> Title not selected as expected ***");
		Log.info("      Selected Title : "+selectedOption);
	}
	
	public String getTitle() throws Exception
	{
		Utils.waitForElement(drpdwn_Title);
		String selectedOption = new Select(drpdwn_Title).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public void EnterFirstName(String sName) throws Exception
	{
		Utils.waitForElement(txtbx_Name);
		txtbx_Name.sendKeys(sName);
		Log.info("      Given First Name : "+ txtbx_Name.getAttribute("value"));
		
		txtbx_Name.sendKeys(Keys.chord(Keys.CONTROL,"a"));
	}
	
	public String getFirstName()
	{
		
		return txtbx_Name.getAttribute("value");
	}
	
	public void selectGender(String sGender) throws Exception
	{
		Utils.waitForElement(drpdwn_Gender);
		Select value=new Select(drpdwn_Gender);
		value.selectByVisibleText(sGender);
		String selectedOption = new Select(drpdwn_Gender).getFirstSelectedOption().getText();
		Log.info("      Selected Gender : "+selectedOption);
	}
	
	public String getGender()
	{
		String selectedOption = new Select(drpdwn_Gender).getFirstSelectedOption().getText();
		return selectedOption;
	}
	
	public void enterAge(String sAge) throws Exception
	{
		Utils.waitForElement(txtbx_Age);
		txtbx_Age.sendKeys(sAge);
		Log.info("      Given Age : "+ sAge);
	}
		
	public String getAge()
	{
		return txtbx_Age.getAttribute("value");
	}
	
	public void selectAgeType(String sAgeType) throws Exception
	{
		Utils.waitForElement(drpdwn_AgeType);
		Select value=new Select(drpdwn_AgeType);
		value.selectByVisibleText(sAgeType);
		Log.info("      Selected AgeType : "+sAgeType);
	}
	
	public String getAgeType()
	{
		String selectedOption = new Select(drpdwn_AgeType).getFirstSelectedOption().getText();
		return selectedOption;
	}
		
	public void selectDoctorName(String sDoctorName) throws Exception
	{
		AutoTxtbx_DoctorName.sendKeys(sDoctorName);
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.keyEnter();
	    Log.info("      Selected DoctorName : "+AutoTxtbx_DoctorName.getAttribute("value"));
	}
	
	public String getDoctorName()
	{
		
		return AutoTxtbx_DoctorName.getAttribute("value");
	}
	
	public void selectRider(String sRider)
	{
		Select value=new Select(drpdwn_Rider);
		value.selectByVisibleText(sRider);
		String selectedOption = new Select(drpdwn_Rider).getFirstSelectedOption().getText();
		Log.info("      Selected Rider : "+selectedOption);
	}
		
	public void EnterServiceName(int rowIndex, String sServiceName) throws Exception
	{
		switch(rowIndex)
		{
			case 1: 
					AutoTxtbx_ServiceName.click();
					AutoTxtbx_ServiceName.sendKeys(sServiceName);
					AutoTxtbx_ServiceName.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
		
			case 2:
					AutoTxtbx_ServiceName2.click();
					AutoTxtbx_ServiceName2.sendKeys(sServiceName);
					AutoTxtbx_ServiceName2.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
			
			case 3:
					AutoTxtbx_ServiceName3.click();
					AutoTxtbx_ServiceName3.sendKeys(sServiceName);
					AutoTxtbx_ServiceName3.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
			case 4:
					AutoTxtbx_ServiceName4.click();
					AutoTxtbx_ServiceName4.sendKeys(sServiceName);
					AutoTxtbx_ServiceName4.sendKeys(Keys.ENTER);
					Utils.waitUntilAngularFinishHttpCalls();
					break;
		}
	}
	
	public void selectServiceName(String sServiceName) throws Exception
	{	
			AutoTxtbx_ServiceName.click();
			AutoTxtbx_ServiceName.sendKeys(sServiceName);
			Utils.waitUntilAngularFinishHttpCalls();
			AutoTxtbx_ServiceName.sendKeys(Keys.ENTER);
			Utils.waitUntilAngularFinishHttpCalls();
			Log.info("      Selected ServiceName : "+AutoTxtbx_ServiceName.getAttribute("value"));
	}
	
	public String GetItemPrice(int rowIndex) throws Exception
	{
		String ItemPrice = "";
		switch(rowIndex)
		{
			case 1:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[1]/td[3]//input")).getText();
				return ItemPrice;
			case 2:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[2]/td[3]//input")).getText();
				return ItemPrice;
				
			case 3:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[3]/td[3]//input")).getText();
				return ItemPrice;
				
			case 4:
				ItemPrice = driver.findElement(By.xpath(".//*[@id='Servicetable']/tbody/tr[4]/td[3]//input")).getText();
				return ItemPrice;
		}
		return ItemPrice;
	}
		
	public void EnterCashAmount(String amount)
	{
		txtbx_CashAmount.sendKeys(amount);
		Log.info("      Given amount : "+txtbx_CashAmount.getAttribute("value"));
	}
	
	public void ClearEnteredCashAmount()
	{
		txtbx_CashAmount.clear();
		Log.info("      Entered cash amount cleared");
	}
	
	public String getCashAmount()
	{
		return txtbx_CashAmount.getAttribute("value");
	}
		
	public String getGrossAmount()
	{
		return GrossAmount.getText();
	}
	
	public String getCollectedAmount()
	{
		return CollectedAmount.getText();
	}
	
	public String getDueAmount() throws Exception
	{
		return DueAmount.getText();
	}
	
	public  void verifyDueAmount() throws Exception
	{
		float Grossamt = Float.parseFloat(GrossAmount.getText());
		float colamt = Float.parseFloat(CollectedAmount.getText());
		float expecteddue = Grossamt-colamt;
		
		Assert.assertTrue(getDueAmount().startsWith(Float.toString(expecteddue)), "Due amount showing incorrectly");
	}
	
	public  void verifyNetAmount() throws Exception
	{
		float grossamt = Float.parseFloat(GrossAmount.getText());
		float discountamt = Float.parseFloat(DiscountAmount.getText());
		float roundoffamt = Float.parseFloat(RoundOffAmount.getText());
		float expectedNet = grossamt-discountamt+roundoffamt;
		Assert.assertTrue(getNetAmount().startsWith(Float.toString(expectedNet)), "Net amount showing incorrectly");
	}
	
	public void ClickGenerateBill() throws Exception
	{
		btn_GenerateBill.click();
		Log.info("      Patient registration completed successfully");
	}
	public void clearForm()
	{
		btn_Clear.click();
	}
	
	public void clickRefresh() throws Exception
	{
		btn_Refresh.click();
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	//maria 
/*	
		public String getDiscountValueforOneService()
		{
			String discount =driver.findElement(By.xpath("//table[@id='Servicetable']/tbody/tr[3]/td[8]/input")).getAttribute("value");
			String value = discount;
			int posi = value.indexOf(".");
		    value = value.substring(0, posi);
			return value;
		}
		
		public String getDiscountAmount()
		{
/*			String value = DiscountAmount.getText();
			System.out.println("value: "+value);
			int posi = value.indexOf(".");
			value = value.substring(0, posi);
			System.out.println("deci: "+value);
			return value;
			return DiscountAmount.getText();
		}
/*		
		public float getGrossAmount()
		{
			String value = GrossAmount.getText();
			 System.out.println("value: "+value);
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
	        System.out.println("deci: "+value);
			return Float.parseFloat(value);
		}
		
		
		public String getDueAmount() throws Exception
		{
			String value =DueAmount.getText();
			 System.out.println("value: "+value);
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
			System.out.println("DueAmount  : "+ value);
			Thread.sleep(1000);
			//float getdue= Float.parseFloat(value);
			return value;
		}
		
		
		public String getNetAmount()
		{
/*			String netAmt= driver.findElement(By.xpath("//span[@ng-bind='objBilling.GrossDetails.NetAmount']")).getText();
			String value =netAmt;
			 System.out.println("value: "+value);
			 int posi = value.indexOf(".");
	      value = value.substring(0, posi);
	      System.out.println("deci: "+value);
			System.out.println("Amount  : "+ value);
			return value;
			return NetAmount.getText();
			}
				
		public String getCollectedAmount()
		{
			return CollectedAmount.getText();
		}
*/		
		public float calculateAmount()
		{
		    List<WebElement> Rows = tbl_Particulars.findElements(By.tagName("tr"));
			List<WebElement> th=tbl_Particulars.findElements(By.tagName("th"));
			int AmountPosition=0;
			float  Total = 0;
			for(int j=1;j<th.size();j++)
			{
				if("Amount".equalsIgnoreCase(th.get(j).getText()))
				{
					AmountPosition=j+1;
				} 
			}  
			for(int i=2;i<(Rows.size()-1);i++)
			{	
				String amountPath="//table[@id='Servicetable']/tbody/tr["+(i+1)+"]/td["+AmountPosition+"]/label";
				String Amount =driver.findElement(By.xpath(amountPath)).getText();
				Total = Total + Float.parseFloat(Amount);
			}
			return Total;
		}
		
		public String calculateNetAmount()
		{
		    List<WebElement> Rows = tbl_Particulars.findElements(By.tagName("tr"));
			List<WebElement> th=tbl_Particulars.findElements(By.tagName("th"));
			int NetPosition=0;
			float  Total = 0;
			String value1="";
			for(int j=1;j<th.size();j++)
			{
				if("Net".equalsIgnoreCase(th.get(j).getText()))
				{
					NetPosition=j+1;
				} 
			}  
			for(int i=2;i<(Rows.size()-1);i++)
			{	
				
			String netPath="//table[@id='Servicetable']/tbody/tr["+(i+1)+"]/td["+NetPosition+"]/label";
			String Net =driver.findElement(By.xpath(netPath)).getText();
			Total = Total + Float.parseFloat(Net);
			
			String calNet= Float.toString(Total);
			String value =calNet;
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
			value1=value;
			}
			
			return value1;
		}
		
		public String calculateNetAmountForBillDiscount(String sEnterBillDiscount)
		{
		    List<WebElement> Rows = tbl_Particulars.findElements(By.tagName("tr"));
			List<WebElement> th=tbl_Particulars.findElements(By.tagName("th"));
			int NetPosition=0;
			float  Total = 0;
			String value1="";
			for(int j=1;j<th.size();j++)
			{
				if("Net".equalsIgnoreCase(th.get(j).getText()))
				{
					NetPosition=j+1;
				} 
			}  
			for(int i=2;i<(Rows.size()-1);i++)
			{		
			String netPath="//table[@id='Servicetable']/tbody/tr["+(i+1)+"]/td["+NetPosition+"]/label";
			String Net =driver.findElement(By.xpath(netPath)).getText();
			Total = Total + Float.parseFloat(Net);
			float billVal= Float.parseFloat(sEnterBillDiscount);
		    float net= Total-billVal;
		    String calNet= Float.toString(net);
			String value =calNet;
			 int posi = value.indexOf(".");
	        value = value.substring(0, posi);
			value1=value;
			}
			
			return value1;
		}
		
		public String getRoundOff()
		{
			/*
			 String rdoff= RoundOffAmount.getText();
				String value =rdoff;
				 System.out.println("value: "+value);
				 int posi = value.indexOf(".");
		        value = value.substring(0, posi);
				System.out.println("NetAmount  : "+ value);
				*/
			return RoundOffAmount.getText();
		}
		
		public String calBillDiscountInAmount()
		{
			String billDiscount= BillDiscountAmount.getAttribute("value");
			return billDiscount;
		}
		
		public String calculateRoundOff()
		{
			String disValue = DiscountAmount.getText();
			
			double d= Double.parseDouble(disValue);
			double dis = d - Math.floor(d);
			String rndOFF = Double.toString(dis);
			return rndOFF;
		}
		public String calBillDiscountInPercentage()
		{
		 float gross= Float.parseFloat(getGrossAmount());
		 float a= gross*10;
			float b= a/100;
			String billPerc= Float.toString(b);
			return billPerc;
		}
		
		public String calculateItemDiscountInPercentage()
		{
			float amount = calculateAmount();
			float a= amount*10;
			float b= a/100;
			String Dis= Float.toString(b);
			String value =Dis;
			int posi = value.indexOf(".");
	        value = value.substring(0, posi);
		
			return value;
		}
		public void checkEmail()
		{
			if(!chkbx_Email.isSelected())
				chkbx_Email.click();
		}
		public void unCheckEmail()
		{
			if(chkbx_Email.isSelected())
				chkbx_Email.click();
		}
		public void checkCourier()
		{
			if(!chkbx_Courier.isSelected())
				chkbx_Courier.click();
		}
		public void unCheckCourier()
		{
			if(chkbx_Courier.isSelected())
				chkbx_Courier.click();
		}
}