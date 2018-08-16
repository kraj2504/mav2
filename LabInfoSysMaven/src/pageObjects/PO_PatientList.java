package pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utility.Constant;
import utility.Log;
import utility.Utils;

public class PO_PatientList 
{
		WebDriver driver;
		
		public PO_PatientList(WebDriver driver)
		{
			this.driver=driver;
		}
		
		@FindBy(how = How.ID, using = "SearchSubject")
		WebElement searchbox;
		
		@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.Title']")
		WebElement drpdwn_title;
		
		@FindBy(how = How.NAME, using="FirstName")
		WebElement txtbx_FirstName;
		
		@FindBy(how = How.NAME, using="LastName")
		WebElement txtbx_LastName;
		
		@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.Gender']")
		WebElement drpdwn_Gender;
		
		@FindBy(how = How.ID, using="DOB")
		WebElement txtbx_DOB;
		
		@FindBy(how = How.NAME, using="Age")
		WebElement txtbx_Age;
		
		@FindBy(how = How.XPATH, using="//select[@ng-model='ObjRegistration.Demographics.AgeType']")
		WebElement drpdwn_AgeType;
		
		@FindBy(how = How.NAME, using="MobileNo")
		WebElement txtbx_MobileNumber;
		
		@FindBy(how = How.ID, using="Emailid")
		WebElement txtbx_EMailID;
		
		@FindBy(how = How.XPATH, using="//input[@ng-model='row.ServiceInfo']")
		WebElement AutoTxtbx_ServiceName;
			
		@FindBy(how=How.XPATH, using=".//input[@ng-model='CashAmount']")
		WebElement txtbx_CashAmount;
		
		@FindBy(how = How.XPATH, using = "//span[@ng-bind='objBilling.GrossDetails.DueAmount']")
		WebElement DueAmount;
		
		@FindBy(how = How.XPATH, using = ".//*[@id='myModal']//button[text()='Update']")
		WebElement btn_Update;
		
		@FindBy(how = How.XPATH, using = ".//*[@id='myModal']//button[text()='Cancel']")
		WebElement btn_Cancel;
		
		@FindBy(how = How.XPATH, using = "//table[@class = 'table service-table tbl1 scroll']")
		WebElement tbl_patientDetails;
		
		@FindBy(how = How.XPATH, using = "//table[@class = 'table service-table tbl1 scroll']//tr")
		List<WebElement> tbl_patientDetailsRows;
		
		@FindBy(how = How.XPATH, using = "//table[@class = 'table service-table tbl1 scroll']//th")
		List<WebElement> tbl_patientDetailsHeader;
		
		@FindBy(how = How.XPATH, using = "//table[@id = 'TblPatientBill']")
		WebElement tbl_patientBill;
		
		@FindBy(how = How.XPATH, using = ".//fieldset[1]//table[@class = 'table service-table tbl2 t1 scroll']//tr")//table[@class = 'table service-table tbl2 t1 scroll']//tr
		List<WebElement> tbl_patientBillRows;
		
		@FindBy(how = How.XPATH, using = ".//fieldset[1]//table[@class = 'table service-table tbl2 t1 scroll']//th")//table[@class = 'table service-table tbl2 t1 scroll']//th
		List<WebElement> tbl_patientBillHeader;
		
		@FindBy(how = How.XPATH, using = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll']")
		WebElement tbl_patientService;
		
		@FindBy(how = How.XPATH, using = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll']//tr")
		List<WebElement> tbl_patientServiceRows;
		
		@FindBy(how = How.XPATH, using = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll']//th")
		List<WebElement> tbl_patientServiceHeader;
		
		@FindBy(how=How.XPATH,using="//table[@class='table service-table tbl2 scroll']/tbody/tr[2]/td[6]")
		WebElement status1;
		
		@FindBy(how=How.XPATH,using="//table[@class='table service-table tbl2 scroll']/tbody/tr[3]/td[6]")
		WebElement status2;
		
		@FindBy(how=How.XPATH,using="//table[@class='table service-table tbl2 scroll']/tbody/tr[4]/td[6]")
		WebElement status3;
		
		@FindBy(how=How.XPATH,using="//table[@class='table service-table tbl2 scroll']/tbody/tr[5]/td[6]")
		WebElement status4;
		
		public void closeEditWindow() 
		{
			btn_Cancel.click();
		}
		
		public void searchRecord(String sFirstName) throws Exception
		{
			Utils.waitForElement(searchbox);
			searchbox.click();
			searchbox.sendKeys(sFirstName);
			Utils.waitUntilAngularFinishHttpCalls();
			try
			{
			String xpath = ".//div[@id='SearchSubjectListDetails']//span[1]";
			Utils.waitForElement(By.xpath(xpath));

			searchbox.sendKeys(Keys.ENTER);
			}
			catch(Exception e)
			{
				Log.error("      Patient list --> Patient not listed in universal search"+e.getMessage());
				throw new Exception("Patient list --> Patient not listed in universal search",e);
			}
			Log.info("      Patient list --> Search completed");
			Utils.waitUntilAngularFinishHttpCalls();
//			Utils.waitForJSandJQueryToLoad();
//			Utils.WaitForAjax();
		}
		
		public void editPatientDetails() throws Exception
		{
			String first_path = "//table[@class = 'table service-table tbl1 scroll']/tbody/tr[2]/td[";
			String second_path = "]";
			String patientDetailsEdit_path = first_path+Constant.col_PatientDetailsEdit+second_path;
			WebElement patientDetailsEdit = driver.findElement(By.xpath(patientDetailsEdit_path));
			patientDetailsEdit.click();
			Log.info("      Click action performed on edit button");
			Utils.waitUntilAngularFinishHttpCalls();
		}
		
		public void selectPatientDetail() throws Exception
		{
			String first_path = "//table[@class = 'table service-table tbl1 scroll']/tbody/tr[2]/td[";
			String second_path = "]/input";
			String patientDetailsSelect_path = first_path+Constant.col_PatientDetailsSelect+second_path;
			WebElement patientDetailsSelect = driver.findElement(By.xpath(patientDetailsSelect_path));
			Utils.waitUntilAngularFinishHttpCalls();
			patientDetailsSelect.click();
			Log.info("      Patient detail selected");
		}
	
		public void selectPatientBill() throws Exception
		{
			Utils.waitUntilAngularFinishHttpCalls();
			int visitIDPosition=0,selectPosition=0;
			for(int j=0;j<tbl_patientBillHeader.size();j++)
			{
				if("Visit ID".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					visitIDPosition=j+1;
				}
				else if("Select".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					selectPosition=j+1;
				}
			}
			int max = 0, maxIndex = 0;
			for(int i=2;i<=tbl_patientBillRows.size();i++)
			{
				String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
				WebElement visitID =  tbl_patientBill.findElement(By.xpath(visitIDpath));
				String val = visitID.getText();
				val = val.substring((val.length()-3), val.length());
				int ival = Integer.parseInt(val);
				if (ival>max)
				{
					max = ival;
					maxIndex = i;
				}
			}
			String selectpath = "//table[@id = 'TblPatientBill']//tr["+maxIndex+"]/td["+selectPosition+"]/input";
			WebElement ele =  driver.findElement(By.xpath(selectpath));
			if(!ele.isSelected())
			{
				ele.click();
				Utils.waitUntilAngularFinishHttpCalls();
			}
		}
		
		public String getPatientBillGrossAmount()
		{
			int visitIDPosition=0,GrossPosition=0;
			for(int j=0;j<tbl_patientBillHeader.size();j++)
			{
				if("Visit ID".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					visitIDPosition=j+1;
				}
				else if("Gross".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					GrossPosition=j+1;
				}
			}
			int max = 0, maxIndex = 0;
			for(int i=2;i<=tbl_patientBillRows.size();i++)
			{
				String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
				WebElement visitID =  tbl_patientBill.findElement(By.xpath(visitIDpath));
				String val = visitID.getText();


				val = val.substring((val.length()-3), val.length());
				int ival = Integer.parseInt(val);
				if (ival>max)
				{
					max = ival;
					maxIndex = i;
				}
			}
			String selectpath = "//table[@class = 'table service-table tbl2 t1 scroll']//tr["+maxIndex+"]/td["+GrossPosition+"]";
			WebElement ele =  driver.findElement(By.xpath(selectpath));

			Log.info("      GrossAmount in patient list is : "+ele.getText());
			String GrossAmt = ele.getText();
			
			if(GrossAmt.length()==8)
			{
				String temp2 = GrossAmt.substring(0, 1);
				String temp3 = GrossAmt.substring(2, 8);
				GrossAmt = temp2+temp3;
			}
			else if(GrossAmt.length()==9)
			{
				String temp2 = GrossAmt.substring(0, 2);
				String temp3 = GrossAmt.substring(3, 9);
				GrossAmt = temp2+temp3;
			}
			return GrossAmt;
		}
		
		public String getPatientBillDiscountAmount()
		{
			int visitIDPosition=0,DiscountPosition=0;
			for(int j=0;j<tbl_patientBillHeader.size();j++)
			{
				if("Visit ID".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					visitIDPosition=j+1;
				}
				else if("Discount".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					DiscountPosition=j+1;
				}
			}
			int max = 0, maxIndex = 0;
			for(int i=2;i<=tbl_patientBillRows.size();i++)
			{
				String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
				WebElement visitID =  tbl_patientBill.findElement(By.xpath(visitIDpath));
				String val = visitID.getText();
				
				val = val.substring((val.length()-3), val.length());
				int ival = Integer.parseInt(val);
				if (ival>max)
				{
					max = ival;
					maxIndex = i;
				}
			}
			String selectpath = "//table[@class = 'table service-table tbl2 t1 scroll']//tr["+maxIndex+"]/td["+DiscountPosition+"]";
			WebElement ele =  driver.findElement(By.xpath(selectpath));

			Log.info("      DiscountAmount in patient list is : "+ele.getText());
			String GrossAmt = ele.getText();
			
			if(GrossAmt.length()==8)
			{
				String temp2 = GrossAmt.substring(0, 1);
				String temp3 = GrossAmt.substring(2, 8);
				GrossAmt = temp2+temp3;
			}
			else if(GrossAmt.length()==9)
			{
				String temp2 = GrossAmt.substring(0, 2);
				String temp3 = GrossAmt.substring(3, 9);
				GrossAmt = temp2+temp3;
			}
			return GrossAmt;
		}
		
		public String getPatientBillNetAmount()
		{
			int visitIDPosition=0,NetPosition=0;
			for(int j=0;j<tbl_patientBillHeader.size();j++)
			{
				if("Visit ID".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					visitIDPosition=j+1;
				}
				else if("Net".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					NetPosition=j+1;
				}
			}
			int max = 0, maxIndex = 0;
			for(int i=2;i<=tbl_patientBillRows.size();i++)
			{
				String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
				WebElement visitID =  tbl_patientBill.findElement(By.xpath(visitIDpath));
				String val = visitID.getText();
				
				val = val.substring((val.length()-3), val.length());
				int ival = Integer.parseInt(val);
				if (ival>max)
				{
					max = ival;
					maxIndex = i;
				}
			}
			String selectpath = "//table[@class = 'table service-table tbl2 t1 scroll']//tr["+maxIndex+"]/td["+NetPosition+"]";
			WebElement ele =  driver.findElement(By.xpath(selectpath));
			
			Log.info("      NetAmount in patient list is : "+ele.getText());
			String Amt = ele.getText();
			
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
		
		public String getPatientBillCollected()
		{
			int visitIDPosition=0,CollectedPosition=0;
			for(int j=0;j<tbl_patientBillHeader.size();j++)
			{
				if("Visit ID".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					visitIDPosition=j+1;
				}
				else if("Collected".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					CollectedPosition=j+1;
				}
			}
			int max = 0, maxIndex = 0;
			for(int i=2;i<=tbl_patientBillRows.size();i++)
			{
				String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
				WebElement visitID =  tbl_patientBill.findElement(By.xpath(visitIDpath));
				String val = visitID.getText();
				
				val = val.substring((val.length()-3), val.length());
				int ival = Integer.parseInt(val);
				if (ival>max)
				{
					max = ival;
					maxIndex = i;
				}
			}
			String selectpath = "//table[@class = 'table service-table tbl2 t1 scroll']//tr["+maxIndex+"]/td["+CollectedPosition+"]";
			WebElement ele =  driver.findElement(By.xpath(selectpath));
			
			Log.info("      CollectedAmount in patient list is : "+ele.getText());
			String Amt = ele.getText();
			
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
		
		public String getPatientBillDue() throws Exception
		{
			int visitIDPosition=0,DuePosition=0;
			Utils.waitUntilAngularFinishHttpCalls();
			for(int j=0;j<tbl_patientBillHeader.size();j++)
			{
				if("Visit ID".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					visitIDPosition=j+1;
				}
				else if("Due".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					DuePosition=j+1;
				}
			}
			int max = 0, maxIndex = 0;
			for(int i=2;i<=tbl_patientBillRows.size();i++)
			{
				String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
				WebElement visitID =  tbl_patientBill.findElement(By.xpath(visitIDpath));
				String val = visitID.getText();
				val = val.substring((val.length()-3), val.length());
				int ival = Integer.parseInt(val);
				if (ival>max)
				{
					max = ival;
					maxIndex = i;
				}
			}
			String selectpath = "//table[@class = 'table service-table tbl2 t1 scroll']//tr["+maxIndex+"]/td["+DuePosition+"]";
			WebElement ele =  driver.findElement(By.xpath(selectpath));

			Log.info("      DueAmount in patient list is : "+ele.getText());
			String Amt = ele.getText();
			
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
		
		public String getPatientBillRefferedBy()
		{
			int visitIDPosition=0,RefferedByPosition=0;
			for(int j=0;j<tbl_patientBillHeader.size();j++)
			{
				if("Visit ID".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					visitIDPosition=j+1;
				}
				else if("Referred By".equalsIgnoreCase(tbl_patientBillHeader.get(j).getText()))
				{
					RefferedByPosition=j+1;
				}
			}
			int max = 0, maxIndex = 0;
			for(int i=2;i<=tbl_patientBillRows.size();i++)
			{
				String visitIDpath = ".//tr["+i+"]/td["+visitIDPosition+"]";
				WebElement visitID =  tbl_patientBill.findElement(By.xpath(visitIDpath));
				String val = visitID.getText();
				
				val = val.substring((val.length()-3), val.length());
				int ival = Integer.parseInt(val);
				if (ival>max)
				{
					max = ival;
					maxIndex = i;
				}
			}
			String selectpath = "//tr["+maxIndex+"]/td["+RefferedByPosition+"]";
			WebElement ele =  tbl_patientBill.findElement(By.xpath(selectpath));

			Log.info("      ReferedBy in patient list is : "+ele.getText());
			return ele.getText();
		}
		
		public int getPatientServiceTableRowSize()
		{
			List<WebElement> patientServiceTableRows = tbl_patientService.findElements(By.tagName("tr"));
			return patientServiceTableRows.size();
		}
		
		public String getServiceName()
		{
			//Get Patient Service Table Row
			//List<WebElement> patientServiceTableRows = tbl_patientService.findElements(By.tagName("tr"));
			tbl_patientService.click();
			
			String first_path3 = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll']/tbody/tr[2]/td";
			List<WebElement> serviceDetailsTableFirstRowColumnsSize = driver.findElements(By.xpath(first_path3));
			
			if(serviceDetailsTableFirstRowColumnsSize.size()>1)
			{
				String first_path2 = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll']/tbody/tr[2]/td[";
				String third_path2 = "]";
				String status_path = first_path2+Constant.col_PatientServiceName+third_path2;
				
				WebElement serviceName = driver.findElement(By.xpath(status_path));
				
				Log.info("      Service Name in patient list is : "+serviceName.getText());
				
				return serviceName.getText();
			}
			else
			{
				Log.info("      Service Name not listed in service list of patient list");
				Assert.assertTrue(1==0, "Service Name not listed in service list of patient list");
			}
			return "";
		}
		
		public String getStatus() throws Exception
		{
			Utils.waitUntilAngularFinishHttpCalls();
			//Get Patient Service Table Row
			//List<WebElement> patientServiceTableRows = tbl_patientService.findElements(By.tagName("tr"));
			tbl_patientService.click();
			String first_path2 = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll' ]/tbody/tr[2]/td[";
			String third_path2 = "]";
			String status_path = first_path2+Constant.col_PatientStatus+third_path2;
			
			Utils.waitForElement(By.xpath(status_path));
			WebElement PatientStatus = driver.findElement(By.xpath(status_path));
			
			Log.info("      Status after sample collection is : "+PatientStatus.getText());
			
			return PatientStatus.getText();
		}
/*		
		public String getStatus1()
		{
			return status1.getText();
			
		}
		
		public String getStatus2()
		{
			return status2.getText();
		}
		
		public String getStatus3()
		{
			return status3.getText();
		}
		public String getStatus4()
		{
			return status4.getText();
		}
*/		
		public String getBarCode()
		{
			//Get Patient Service Table Row
			//List<WebElement> patientServiceTableRows = tbl_patientService.findElements(By.tagName("tr"));
			tbl_patientService.click();
			String first_path2 = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll' ]/tbody/tr[2]/td[";
			String third_path2 = "]";
			String barCode_path = first_path2+Constant.col_PatientBarCode+third_path2;
			
			WebElement barCode = driver.findElement(By.xpath(barCode_path));
			
			Log.info("      Generated Bar code is : "+barCode.getText());
			
			return barCode.getText();
		}
		
		public List<String> getAllBarCode()
		{
			//Get Patient Service Table Row
			//List<WebElement> patientServiceTableRows = tbl_patientService.findElements(By.tagName("tr"));
			tbl_patientService.click();
			String first_path2 = "//fieldset[2]//table[@class = 'table service-table tbl2 scroll' ]/tbody/tr/td[";
			String third_path2 = "]";
			String barCode_path = first_path2+Constant.col_PatientBarCode+third_path2;
			
			List<WebElement> barCodes = driver.findElements(By.xpath(barCode_path));
			List<String> codes = new ArrayList<String>();
			for(int i =0; i<barCodes.size();i++)
			{
				if(!codes.contains(barCodes.get(i).getText()))
					codes.add(barCodes.get(i).getText());
			}
			return codes;
		}
		
		public void verifyingStatus(String sServiceName1,String sServiceName2,String eStatus ) throws Exception
		{		
			int servicePosition=0,statusPosition=0;
			for(int j=1;j<tbl_patientServiceHeader.size();j++)
			{
				if("Service".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
					servicePosition=j+1;
				else if("Status".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))	
					statusPosition=j+1;
			}
			for(int i=2;i<tbl_patientServiceRows.size();i++)
			{
				String servicepath = ".//tr["+i+"]/td["+servicePosition+"]";
				String statuspath = ".//tr["+i+"]/td["+statusPosition+"]";
				
				WebElement service = tbl_patientService.findElement(By.xpath(servicepath));
				WebElement status =  tbl_patientService.findElement(By.xpath(statuspath));
				
				if(service.getText().trim().equalsIgnoreCase(sServiceName1)) 
				{
						Assert.assertEquals(status.getText(),eStatus,"***Status not updated as expected***");
				}
				else if (service.getText().trim().equalsIgnoreCase(sServiceName2))
				{
						Assert.assertEquals(status.getText(),eStatus,"***Status not updated as expected***");
				}	
			}
		}

		
		public void verifyingStatus(String sServiceName1, String eStatus) throws Exception
		{		
			int servicePosition=0,statusPosition=0;
			for(int j=1;j<tbl_patientServiceHeader.size();j++)
			{
				if("Service".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					servicePosition=j+1;
				}
				else if("Status".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					statusPosition=j+1;
				}
			}
			
			for(int i=2;i<tbl_patientServiceRows.size();i++)
			{
				String servicepath = ".//tr["+i+"]/td["+servicePosition+"]";
				String statuspath = ".//tr["+i+"]/td["+statusPosition+"]";
				
				WebElement service = tbl_patientService.findElement(By.xpath(servicepath));
				WebElement status =  tbl_patientService.findElement(By.xpath(statuspath));
				
				if(service.getText().trim().equalsIgnoreCase(sServiceName1)) 
				{
						Assert.assertEquals(status.getText(),eStatus,"***Status not updated as expected***");
				}
			}
		}
		
		public void verifyingStatus(String eStatus) throws Exception
		{		
			int statusPosition=0;
			for(int j=1;j<tbl_patientServiceHeader.size();j++)
			{
				if("Status".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
					statusPosition=j+1;
			}
			
			for(int i=2;i<tbl_patientServiceRows.size();i++)
			{
				String statuspath = ".//tr["+i+"]/td["+statusPosition+"]";
				WebElement status =  tbl_patientService.findElement(By.xpath(statuspath));
				Assert.assertEquals(status.getText(),eStatus,"***Status not updated as expected***");
			}
		}	
		
		public void verifyingStatusBarCode(String estatus) throws Exception
		{		
			List<WebElement> patientServiceTableRows = tbl_patientService.findElements(By.tagName("tr"));
			
			//Get column position
			List<WebElement> th=tbl_patientService.findElements(By.tagName("th"));
			int samplePosition=0,statusPosition=0,barCodePosition=0;
			for(int j=0;j<th.size();j++)
			{
				if("Sample".equalsIgnoreCase(th.get(j).getText()))
				{
					samplePosition=j+1;
				}
				else if("Status".equalsIgnoreCase(th.get(j).getText()))
				{
					statusPosition=j+1;
				}
				else if("Barcode No".equalsIgnoreCase(th.get(j).getText()))
				{
					barCodePosition=j+1;
					break;
				}
			}
			for(int i=2;i<=patientServiceTableRows.size();i++)
			{
				String samplepath = ".//tr["+i+"]/td["+samplePosition+"]";
				String statuspath = ".//tr["+i+"]/td["+statusPosition+"]";
				String barcodepath = ".//tr["+i+"]/td["+barCodePosition+"]";
				
				WebElement sample =  tbl_patientService.findElement(By.xpath(samplepath));
				WebElement status =  tbl_patientService.findElement(By.xpath(statuspath));
				WebElement barcode =  tbl_patientService.findElement(By.xpath(barcodepath));
				if(!sample.getText().trim().equalsIgnoreCase("ECHO") & 
						!sample.getText().trim().equalsIgnoreCase("X - RAY") & 
						!sample.getText().trim().equalsIgnoreCase("SCAN") &
						!sample.getText().trim().equalsIgnoreCase(""))
				{
					Assert.assertEquals(status.getText(),estatus,"***Status not updated after sample collection***");
					Log.info("      Current Status of the patient : "+status.getText());
					if(estatus.equals("Recollect")||estatus.equals("Registered"))
					{
						Assert.assertEquals(barcode.getText(),"Yet to be processed","***Barcode generated/not cleared after Registration/Recollect***");
					}
					else
					{
						Assert.assertNotEquals(barcode.getText(),"Yet to be processed","***Barcode not generated/retained***");
		//				Assert.assertTrue(barcode.getText()..startsWith(Constant.BarCodeNumber),"***Barcode not updated after sample collection***");
					}
				}
			}
		}
		
		public void verifyingStatusBarCode(String eStatus,String sServiceName1,String sServiceName2 ) throws Exception
		{
			int servicePosition=0,samplePosition=0,statusPosition=0,barCodePosition=0;
			for(int j=0;j<tbl_patientServiceHeader.size();j++)
			{
				if("Service".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					servicePosition=j+1;
				}
				else if("Sample".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					samplePosition=j+1;
				}
				else if("Status".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					statusPosition=j+1;
				}
				else if("Barcode No".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					barCodePosition=j+1;
					break;
				}
			}
			
			for(int i=2;i<=tbl_patientServiceRows.size();i++)
			{
				String servicepath = ".//tr["+i+"]/td["+servicePosition+"]";
				String samplepath = ".//tr["+i+"]/td["+samplePosition+"]";
				String statuspath = ".//tr["+i+"]/td["+statusPosition+"]";
				String barcodepath = ".//tr["+i+"]/td["+barCodePosition+"]";
				
				WebElement service = tbl_patientService.findElement(By.xpath(servicepath));
				WebElement sample =  tbl_patientService.findElement(By.xpath(samplepath));
				WebElement status =  tbl_patientService.findElement(By.xpath(statuspath));
				WebElement barcode = tbl_patientService.findElement(By.xpath(barcodepath));
				
				
				if(!sample.getText().trim().equalsIgnoreCase("ECHO") &
						!sample.getText().trim().equalsIgnoreCase("X - RAY") &
						!sample.getText().trim().equalsIgnoreCase("SCAN") &
						!sample.getText().trim().equalsIgnoreCase(""))
				{
					if(service.getText().trim().equalsIgnoreCase(sServiceName1)) 
					{
						Assert.assertEquals(status.getText(),eStatus,"***Status not updated as expected after sample collection***");
					}
					else if (service.getText().trim().equalsIgnoreCase(sServiceName2))
					{
						Assert.assertEquals(status.getText(),eStatus,"***Status not updated as expected after sample collection***");
					}
/*					else
					{
						System.out.println("inside else, act:"+status.getText()+" for "+service.getText());
						System.out.println("inside else, exp:"+eStatus+" for "+service.getText());
						//Assert.assertNotEquals(status.getText(),eStatus,"***Status not updated after sample collection***");
						Log.info("      Current Status of the patient : "+status.getText());
						//Assert.assertEquals(barcode.getText(),"Yet to be processed","***Barcode not updated after sample collection***");
						//Assert.assertTrue(barcode.getText().startsWith(Constant.BarCodeNumber),"***Barcode not updated after sample collection***");
					}
*/				}
			}
		}
		public void verifyingStatusBarCode(String eStatus,String sServiceName1) throws Exception
		{
			int servicePosition=0,samplePosition=0,statusPosition=0,barCodePosition=0;
			for(int j=0;j<tbl_patientServiceHeader.size();j++)
			{
				if("Service".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					servicePosition=j+1;
				}
				else if("Sample".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					samplePosition=j+1;
				}
				else if("Status".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					statusPosition=j+1;
				}
				else if("Barcode No".equalsIgnoreCase(tbl_patientServiceHeader.get(j).getText()))
				{
					barCodePosition=j+1;
					break;
				}
			}
			
			for(int i=2;i<=tbl_patientServiceRows.size();i++)
			{
				String servicepath = ".//tr["+i+"]/td["+servicePosition+"]";
				String samplepath = ".//tr["+i+"]/td["+samplePosition+"]";
				String statuspath = ".//tr["+i+"]/td["+statusPosition+"]";
				String barcodepath = ".//tr["+i+"]/td["+barCodePosition+"]";
				
				WebElement service = tbl_patientService.findElement(By.xpath(servicepath));
				WebElement sample =  tbl_patientService.findElement(By.xpath(samplepath));
				WebElement status =  tbl_patientService.findElement(By.xpath(statuspath));
				WebElement barcode = tbl_patientService.findElement(By.xpath(barcodepath));
				
				if(!sample.getText().trim().equalsIgnoreCase("ECHO") & 
						!sample.getText().trim().equalsIgnoreCase("X - RAY") & 
						!sample.getText().trim().equalsIgnoreCase("SCAN") &
						!sample.getText().trim().equalsIgnoreCase(""))
				{
					if(service.getText().trim().equalsIgnoreCase(sServiceName1))
					{
						Assert.assertEquals(status.getText(),eStatus,"***Status not updated as expected after sample collection***");
						Assert.assertNotEquals(barcode.getText(),"Yet to be processed","***Barcode not updated after sample collection***");
		//				Assert.assertTrue(barcode.getText().startsWith(Constant.BarCodeNumber),"***Barcode not updated after sample collection***");
						Log.info("      Current Status of the patient : "+status.getText());
					}
				}
			}
		}
}
