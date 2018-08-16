package pageObjects;

import java.awt.Label;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import utility.Log;
import utility.Utils;

public class PO_DoctorPayOff 
{
	WebDriver driver;
	public PO_DoctorPayOff(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using = "SearchTexttxt")
	WebElement searchbox;
	
	@FindBy(how = How.NAME, using = "Investigation Name")
	WebElement InvestigationSearchBox;
	
	@FindBy(how = How.XPATH, using = ".//table[@class='table service-table department-table scroll']")
	WebElement tbl_department;
	
	@FindBy(how = How.XPATH, using = ".//table[@class='table service-table department-table scroll']/tbody/tr")
	WebElement tbl_departmentRows;
	
	@FindBy(how = How.XPATH, using = ".//table[@class='table service-table subdepartment-table scroll']")
	WebElement tbl_subDepartment;
	
	@FindBy(how = How.XPATH, using = ".//table[@class='table service-table subdepartment-table scroll']/tbody/tr")
	WebElement tbl_subDepartmentRows;
	
	@FindBy(how = How.XPATH, using = "//table[@class='table service-table investigation-table scroll']")
	WebElement tbl_investigation;

	@FindBy(how = How.XPATH, using = "//table[@class='table service-table investigation-table scroll']/tbody/tr")
	WebElement tbl_investigationRows;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-save']")
	WebElement btn_Save;
	
	@FindBy(how=How.XPATH, using="//i[@class='fa fa-refresh']")
	WebElement btn_Refresh;
	
	public void clickSave()
	{
		btn_Save.click();
		Log.info("      Click action performed on Bulk Entry Save Button");
	}
	
	public void clickRefresh()
	{
		btn_Refresh.click();
		Log.info("      Click action performed on Bulk Entry Refresh Button");
	}

	public void searchDoctor(String sDoctorName) throws Exception
	{
		Utils.waitForElement(searchbox);
		searchbox.click();
		searchbox.clear();
		searchbox.sendKeys(sDoctorName);
		Utils.waitUntilAngularFinishHttpCalls();
		try
		{
		String xpath = ".//*[text()='"+sDoctorName+"']";
		
		String xpath1 = ".//fieldset[1]//a[1]";//div[@id='SearchTexttxt']//span[1]
		Utils.waitForElement(By.xpath(xpath1));
		driver.findElement(By.xpath(xpath)).click();
		}
		catch(Exception e)
		{
			Log.error("      Doctor pay off --> Doctor not listed in universal search"+e.getMessage());
			throw new Exception("Doctor pay off --> Doctor not listed in universal search",e);
		}
/*		searchbox.sendKeys(Keys.TAB);
		searchbox.click();
		searchbox.sendKeys(Keys.ENTER);
*/		Utils.waitUntilAngularFinishHttpCalls();
		Log.info("      Doctor payoff --> Search completed");
	}
	
	public void selectServiceName(String sServiceName) throws Exception
	{	
			InvestigationSearchBox.click();
			InvestigationSearchBox.sendKeys(sServiceName);
			Utils.keyEnter();
			Utils.waitUntilAngularFinishHttpCalls();
			Log.info("      Investigation Name Selected");
	}
	
	public void selectDepartment(String department)
	{
		int departmentPosition = 0;
		List<WebElement> th=tbl_departmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Department".equalsIgnoreCase(th.get(j).getText()))
			{
				departmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+departmentPosition+"]";
		List<WebElement> departmentList = tbl_department.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+departmentPosition+"]";
			WebElement dep = tbl_department.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(department))
			{
				String valuePath = ".//tr["+i+"]/td[1]/input";
				WebElement txtbx_value =  tbl_department.findElement(By.xpath(valuePath));
				txtbx_value.click();
				break;
			}
		}
		Log.info("      Department Selected");
	}
	
	public void enterValueForDept(String department, String value)
	{
		int valuePosition = 0, departmentPosition = 0;
		List<WebElement> th=tbl_departmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value%".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Department".equalsIgnoreCase(th.get(j).getText()))
			{
				departmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+departmentPosition+"]";
		List<WebElement> departmentList = tbl_department.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+departmentPosition+"]";
			WebElement dep = tbl_department.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(department))
			{
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
				WebElement txtbx_value =  tbl_department.findElement(By.xpath(valuePath));
				txtbx_value.clear();
				txtbx_value.sendKeys(value);
				break;
			}
		}
		Log.info("      Value of department entered");
	}
	
	public void verifyValueForDept(String department, String value)
	{
		int valuePosition = 0, departmentPosition = 0;
		List<WebElement> th=tbl_departmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value%".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Department".equalsIgnoreCase(th.get(j).getText()))
			{
				departmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+departmentPosition+"]";
		List<WebElement> departmentList = tbl_department.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+departmentPosition+"]";
			WebElement dep = tbl_department.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(department))
			{
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
				WebElement txtbx_value =  tbl_department.findElement(By.xpath(valuePath));
				Assert.assertEquals("*** Given value not updated in Department ***", value, txtbx_value.getAttribute("value"));
				break;
			}
		}
		Log.info("      Value of department verified");
	}
	
	public void clearAllValuesForAllDept()
	{
		int valuePosition = 0, departmentPosition = 0;
		List<WebElement> th=tbl_departmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value%".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Department".equalsIgnoreCase(th.get(j).getText()))
			{
				departmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+departmentPosition+"]";
		List<WebElement> departmentList = tbl_department.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+departmentPosition+"]";
			WebElement dep = tbl_department.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
			WebElement txtbx_value =  tbl_department.findElement(By.xpath(valuePath));
			txtbx_value.clear();
			txtbx_value.sendKeys("0");
		}
		Log.info("      All Values of all departments cleared");
	}
	
	public void enterRangeForDept(String department, String range)
	{
		int rangePosition = 0, departmentPosition = 0;
		List<WebElement> th=tbl_departmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Range%".equalsIgnoreCase(th.get(j).getText()))
			{
				rangePosition=j+1;
			}
			else if("Department".equalsIgnoreCase(th.get(j).getText()))
			{
				departmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+departmentPosition+"]";
		List<WebElement> departmentList = tbl_department.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+departmentPosition+"]";
			WebElement dep = tbl_department.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(department))
			{
				String valuePath = ".//tr["+i+"]/td["+rangePosition+"]/input";
				WebElement txtbx_value =  tbl_department.findElement(By.xpath(valuePath));
				txtbx_value.clear();
				txtbx_value.sendKeys(range);
				break;
			}
		}
		Log.info("      Range of department entered");
	}
	
	public void verifyRangeForDept(String department, String range)
	{
		int rangePosition = 0, departmentPosition = 0;
		List<WebElement> th=tbl_departmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Range%".equalsIgnoreCase(th.get(j).getText()))
			{
				rangePosition=j+1;
			}
			else if("Department".equalsIgnoreCase(th.get(j).getText()))
			{
				departmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+departmentPosition+"]";
		List<WebElement> departmentList = tbl_department.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+departmentPosition+"]";
			WebElement dep = tbl_department.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(department))
			{
				String valuePath = ".//tr["+i+"]/td["+rangePosition+"]/input";
				WebElement txtbx_value =  tbl_department.findElement(By.xpath(valuePath));
				Assert.assertEquals("*** Given Range not updated in Department ***", range, txtbx_value.getAttribute("value"));
				break;
			}
		}
		Log.info("      Range of department verified");
	}
	
	public void clearAllRangeForAllDept()
	{
		int rangePosition = 0, departmentPosition = 0;
		List<WebElement> th=tbl_departmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Range%".equalsIgnoreCase(th.get(j).getText()))
			{
				rangePosition=j+1;
			}
			else if("Department".equalsIgnoreCase(th.get(j).getText()))
			{
				departmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+departmentPosition+"]";
		List<WebElement> departmentList = tbl_department.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+departmentPosition+"]";
			WebElement dep = tbl_department.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			String valuePath = ".//tr["+i+"]/td["+rangePosition+"]/input";
			WebElement txtbx_value =  tbl_department.findElement(By.xpath(valuePath));
			txtbx_value.clear();
			txtbx_value.sendKeys("0");
		}
		Log.info("      All range of all department cleared");
	}
	
	public void selectSubDepartment(String department)
	{
		int subdepartmentPosition = 0;
		List<WebElement> th=tbl_subDepartmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Sub Department".equalsIgnoreCase(th.get(j).getText()))
			{
				subdepartmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+subdepartmentPosition+"]";
		List<WebElement> departmentList = tbl_subDepartment.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+subdepartmentPosition+"]";
			WebElement dep = tbl_subDepartment.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(department))
			{
				String valuePath = ".//tr["+i+"]/td[1]/input";
				WebElement txtbx_value =  tbl_subDepartment.findElement(By.xpath(valuePath));
				txtbx_value.click();
				break;
			}
		}
		Log.info("      Sub department selected");
	}
	
	public void enterValueForSubDept(String subdepartment, String value)
	{
		int valuePosition = 0, subdepartmentPosition = 0;
		List<WebElement> th=tbl_subDepartmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value%".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Sub Department".equalsIgnoreCase(th.get(j).getText()))
			{
				subdepartmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+subdepartmentPosition+"]";
		List<WebElement> departmentList = tbl_subDepartment.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+subdepartmentPosition+"]";
			WebElement dep = tbl_subDepartment.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(subdepartment))
			{
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
				WebElement txtbx_value =  tbl_subDepartment.findElement(By.xpath(valuePath));
				txtbx_value.clear();
				txtbx_value.sendKeys(value);
				break;
			}
		}
		Log.info("      Value of sub department entered");
	}
	
	public void verifyValueForSubDept(String subdepartment, String value)
	{
		int valuePosition = 0, subdepartmentPosition = 0;
		List<WebElement> th=tbl_subDepartmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value%".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Sub Department".equalsIgnoreCase(th.get(j).getText()))
			{
				subdepartmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+subdepartmentPosition+"]";
		List<WebElement> departmentList = tbl_subDepartment.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+subdepartmentPosition+"]";
			WebElement dep = tbl_subDepartment.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(subdepartment))
			{
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
				WebElement txtbx_value =  tbl_subDepartment.findElement(By.xpath(valuePath));
				Assert.assertEquals("*** Given Value not updated in SubDepartment ***", value, txtbx_value.getAttribute("value"));
				break;
			}
		}
		Log.info("      Value of sub department verified");
	}
	
	public void clearAllValueForAllSubDept()
	{
		int valuePosition = 0, subdepartmentPosition = 0;
		List<WebElement> th=tbl_subDepartmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value%".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Sub Department".equalsIgnoreCase(th.get(j).getText()))
			{
				subdepartmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+subdepartmentPosition+"]";
		List<WebElement> departmentList = tbl_subDepartment.findElements(By.xpath(alldeppath));
		for(int i=2;i<=departmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+subdepartmentPosition+"]";
			WebElement dep = tbl_subDepartment.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
			WebElement txtbx_value =  tbl_subDepartment.findElement(By.xpath(valuePath));
			txtbx_value.clear();
			txtbx_value.sendKeys("0");
		}
		Log.info("      All Values of all sub departments verified");
	}
	
	public void enterRangeForSubDept(String subdepartment, String range)
	{
		int rangePosition = 0, subdepartmentPosition = 0;
		List<WebElement> th=tbl_subDepartmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Range%".equalsIgnoreCase(th.get(j).getText()))
			{
				rangePosition=j+1;
			}
			else if("Sub Department".equalsIgnoreCase(th.get(j).getText()))
			{
				subdepartmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+subdepartmentPosition+"]";
		List<WebElement> subdepartmentList = tbl_subDepartment.findElements(By.xpath(alldeppath));
		for(int i=2;i<=subdepartmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+subdepartmentPosition+"]";
			WebElement dep = tbl_subDepartment.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(subdepartment))
			{
				String valuePath = ".//tr["+i+"]/td["+rangePosition+"]/input";
				WebElement txtbx_value =  tbl_subDepartment.findElement(By.xpath(valuePath));
				txtbx_value.clear();
				txtbx_value.sendKeys(range);
				break;
			}
		}
	}
	
	public void verifyRangeForSubDept(String subdepartment, String range)
	{
		int rangePosition = 0, subdepartmentPosition = 0;
		List<WebElement> th=tbl_subDepartmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Range%".equalsIgnoreCase(th.get(j).getText()))
			{
				rangePosition=j+1;
			}
			else if("Sub Department".equalsIgnoreCase(th.get(j).getText()))
			{
				subdepartmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+subdepartmentPosition+"]";
		List<WebElement> subdepartmentList = tbl_subDepartment.findElements(By.xpath(alldeppath));
		for(int i=2;i<=subdepartmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+subdepartmentPosition+"]";
			WebElement dep = tbl_subDepartment.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			if(dep.getText().equalsIgnoreCase(subdepartment))
			{
				String valuePath = ".//tr["+i+"]/td["+rangePosition+"]/input";
				WebElement txtbx_value =  tbl_subDepartment.findElement(By.xpath(valuePath));
				Assert.assertEquals("*** Given Range not updated in SubDepartment ***", range, txtbx_value.getAttribute("value"));
				break;
			}
		}
	}
	
	public void clearAllRangeForAllSubDept()
	{
		int rangePosition = 0, subdepartmentPosition = 0;
		List<WebElement> th=tbl_subDepartmentRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Range%".equalsIgnoreCase(th.get(j).getText()))
			{
				rangePosition=j+1;
			}
			else if("Sub Department".equalsIgnoreCase(th.get(j).getText()))
			{
				subdepartmentPosition=j+1;
			}
		}
		String alldeppath = ".//tbody/tr/td["+subdepartmentPosition+"]";
		List<WebElement> subdepartmentList = tbl_subDepartment.findElements(By.xpath(alldeppath));
		for(int i=2;i<=subdepartmentList.size()+1;i++)
		{
			String deppath = ".//tbody/tr["+i+"]/td["+subdepartmentPosition+"]";
			WebElement dep = tbl_subDepartment.findElement(By.xpath(deppath));
			Utils.scrollIntoView(dep);
			String valuePath = ".//tr["+i+"]/td["+rangePosition+"]/input";
			WebElement txtbx_value =  tbl_subDepartment.findElement(By.xpath(valuePath));
			txtbx_value.clear();
			txtbx_value.sendKeys("0");
		}
	}
	
	public void searchInvestigation(String sInvestigationName) throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		InvestigationSearchBox.clear();
		InvestigationSearchBox.sendKeys(sInvestigationName);
		Utils.waitUntilAngularFinishHttpCalls();
		String xpath = ".//*[text()='"+sInvestigationName+"']";
		driver.findElement(By.xpath(xpath)).click();
//		InvestigationSearchBox.sendKeys(Keys.TAB);
		Log.info("      Doctor payoff --> Search completed");
		Utils.waitUntilAngularFinishHttpCalls();
	}
	
	public void selectPayoutTypeForInvestigation(String InvestigationName, String PayoutType) throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		int InvestigationNamePosition = 0, PayoutTypePosition = 0;
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Payout Type".equalsIgnoreCase(th.get(j).getText()))
			{
				PayoutTypePosition=j+1;
			}
			else if("Investigation Name".equalsIgnoreCase(th.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+InvestigationNamePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		for(int i=2;i<=invList.size()+1;i++)
		{
			String invpath = ".//tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
			WebElement inv = tbl_investigation.findElement(By.xpath(invpath));
			Utils.scrollIntoView(inv);
			if(inv.getText().equalsIgnoreCase(InvestigationName))
			{
				String valuePath = ".//tr["+i+"]/td["+PayoutTypePosition+"]/select";
				WebElement drpdwn_payoutType =  tbl_investigation.findElement(By.xpath(valuePath));
				Select option = new Select(drpdwn_payoutType);
				option.selectByVisibleText(PayoutType);
				break;
			}
		}
	}
	
	public void verifyPayoutTypeForInvestigation(String InvestigationName, String PayoutType)
	{
		int InvestigationNamePosition = 0, PayoutTypePosition = 0;
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Payout Type".equalsIgnoreCase(th.get(j).getText()))
			{
				PayoutTypePosition=j+1;
			}
			else if("Investigation Name".equalsIgnoreCase(th.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+InvestigationNamePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		for(int i=2;i<=invList.size()+1;i++)
		{
			String invpath = ".//tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
			WebElement inv = tbl_investigation.findElement(By.xpath(invpath));
			Utils.scrollIntoView(inv);
			if(inv.getText().equalsIgnoreCase(InvestigationName))
			{
				String valuePath = ".//tr["+i+"]/td["+PayoutTypePosition+"]/select";
				WebElement drpdwn_payoutType =  tbl_investigation.findElement(By.xpath(valuePath));
				Select option = new Select(drpdwn_payoutType);
				Assert.assertEquals("*** Given PayoutType not updated in Investigation ***",PayoutType, option.getFirstSelectedOption().getText());
				break;
			}
		}
	}
	
	public void enterValueForInvestigationForPercent(String InvestigationName, String PayoutValue)
	{
		int InvestigationNamePosition = 0, valuePosition = 0;
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Investigation Name".equalsIgnoreCase(th.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+InvestigationNamePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		for(int i=2;i<=invList.size()+1;i++)
		{
			String invpath = ".//tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
			WebElement inv = tbl_investigation.findElement(By.xpath(invpath));
			Utils.scrollIntoView(inv);
			if(inv.getText().equalsIgnoreCase(InvestigationName))
			{
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
				WebElement txtbx_value =  tbl_investigation.findElement(By.xpath(valuePath));
				txtbx_value.clear();
				txtbx_value.sendKeys(PayoutValue);
				break;
			}
		}
	}
	
	public String enterValueForInvestigationForFlat(String InvestigationName, String PayoutValueInPercentage)
	{
		int InvestigationNamePosition = 0, valuePosition = 0;
		String value = getPriceForInvestigation(InvestigationName);
		double fvalue = Double.parseDouble(value);
		value = Double.toString(fvalue*Double.parseDouble(PayoutValueInPercentage)/100);
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Investigation Name".equalsIgnoreCase(th.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+InvestigationNamePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		for(int i=2;i<=invList.size()+1;i++)
		{
			String invpath = ".//tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
			WebElement inv = tbl_investigation.findElement(By.xpath(invpath));
			Utils.scrollIntoView(inv);
			if(inv.getText().equalsIgnoreCase(InvestigationName))
			{
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
				WebElement txtbx_value =  tbl_investigation.findElement(By.xpath(valuePath));
				txtbx_value.clear();
				txtbx_value.sendKeys(value);
				break;
			}
		}
		return value;
	}
	
	public void verifyValueForInvestigation(String InvestigationName, String PayoutValue)
	{
		int InvestigationNamePosition = 0, valuePosition = 0;
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Value".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
			else if("Investigation Name".equalsIgnoreCase(th.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+InvestigationNamePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		for(int i=2;i<=invList.size()+1;i++)
		{
			String invpath = ".//tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
			WebElement inv = tbl_investigation.findElement(By.xpath(invpath));
			Utils.scrollIntoView(inv);
			if(inv.getText().equalsIgnoreCase(InvestigationName))
			{
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/input";
				WebElement txtbx_value =  tbl_investigation.findElement(By.xpath(valuePath));
				Assert.assertTrue("*** Given PayoutValue not updated in Investigation ***",PayoutValue.contains(txtbx_value.getAttribute("value")));
				break;
			}
		}
	}
	
	public String getAmountForInvestigation(String InvestigationName, String PayoutValue)
	{
		int InvestigationNamePosition = 0, amountPosition = 0;
		String amount="";
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("Amount".equalsIgnoreCase(th.get(j).getText()))
			{
				amountPosition=j+1;
			}
			else if("Investigation Name".equalsIgnoreCase(th.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+InvestigationNamePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		for(int i=2;i<=invList.size()+1;i++)
		{
			String invpath = ".//tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
			WebElement inv = tbl_investigation.findElement(By.xpath(invpath));
			Utils.scrollIntoView(inv);
			if(inv.getText().equalsIgnoreCase(InvestigationName))
			{
				String amountPath = ".//tr["+i+"]/td["+amountPosition+"]/span";
				WebElement txtbx_value =  tbl_investigation.findElement(By.xpath(amountPath));
				amount = txtbx_value.getText();
				break;
			}
		}
		return amount;
	}
	
	public String getPriceForInvestigation(String InvestigationName)
	{
		int InvestigationNamePosition = 0, pricePosition = 0;
		String amount="";
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("General Price".equalsIgnoreCase(th.get(j).getText()))
			{
				pricePosition=j+1;
			}
			else if("Investigation Name".equalsIgnoreCase(th.get(j).getText()))
			{
				InvestigationNamePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+InvestigationNamePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		for(int i=2;i<=invList.size()+1;i++)
		{
			String invpath = ".//tbody/tr["+i+"]/td["+InvestigationNamePosition+"]";
			WebElement inv = tbl_investigation.findElement(By.xpath(invpath));
			Utils.scrollIntoView(inv);
			if(inv.getText().equalsIgnoreCase(InvestigationName))
			{
				String amountPath = ".//tr["+i+"]/td["+pricePosition+"]/span";
				WebElement txtbx_value =  tbl_investigation.findElement(By.xpath(amountPath));
				amount = txtbx_value.getText();
				break;
			}
		}
		return amount;
	}

	public void deleteAllInvestigation() throws Exception
	{
		int valuePosition = 0;
		List<WebElement> th=tbl_investigationRows.findElements(By.tagName("th"));
		for(int j=0;j<th.size();j++)
		{
			if("".equalsIgnoreCase(th.get(j).getText()))
			{
				valuePosition=j+1;
			}
		}
		String allinvpath = ".//tbody/tr/td["+valuePosition+"]";
		List<WebElement> invList = tbl_investigation.findElements(By.xpath(allinvpath));
		int i = 2;
		while(i<=invList.size()+1)
		{
			try
			{			
				String valuePath = ".//tr["+i+"]/td["+valuePosition+"]/a";
				WebElement txtbx_value =  tbl_investigation.findElement(By.xpath(valuePath));
				Utils.scrollIntoView(txtbx_value);
				txtbx_value.click();
			}
			catch(Exception e)
			{
				break;
			}
		}
	}
}