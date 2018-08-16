package pageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utility.Log;
import utility.Utils;

public class PO_WorkList
{
	WebDriver driver;
	
	public PO_WorkList(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(how = How.ID, using="testname")
	WebElement drpdwn_filter;
	
	@FindBy(how = How.XPATH, using="//a[@class = 'btn btn-app']/i[@class = 'fa fa-save']")
	WebElement btn_GenerateWorkList;
	
	@FindBy(how = How.XPATH, using=".//div[@id='nongen']//table[@class='table service-table scroll']")
	WebElement tbl_worklist;
	
	public int verifyPatientName(String sFirstName) throws Exception
	{
		sFirstName = sFirstName.toUpperCase();
		int i = 0;
		System.out.println("1");
//		Utils.waitForElement(tbl_worklist);
		tbl_worklist.click();
		System.out.println("2");
		List<WebElement> th=tbl_worklist.findElements(By.tagName("th"));
		//Get column position of Patient Name
		int col_position=0;
		for(int j=0;j<th.size();j++)
		{
			System.out.println("3");
			if("Patient Name".equalsIgnoreCase(th.get(j).getText()))
			{
				System.out.println("4");
				col_position=j+1;
				break;
			}
			System.out.println("5");
		}
		Utils.waitUntilAngularFinishHttpCalls();
		List<WebElement> FirstColumns = tbl_worklist.findElements(By.xpath("//tr/td["+col_position+"]"));
		for(WebElement cell: FirstColumns)
		{
			if(cell.getText().length()!=0)
	      	{
				if(cell.getText().endsWith(sFirstName))
	      		{
					System.out.println("6");
					i = 1;
					break;
	      		}
	      		if(i==1)
	      		break;
	      	}
	      	if(i==1)
    		break;
		}
		Log.info("      Work List --> Patient name verified");
		return i;
	}
	
	public void ClickGenerateWorkList() throws Exception
	{
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.clickElement(btn_GenerateWorkList);
//		btn_GenerateWorkList.click();
		Log.info("      Click action performed on Generate WorkList button");
		Utils.waitUntilAngularFinishHttpCalls();
		Utils.keyCtrlW();
	}
}