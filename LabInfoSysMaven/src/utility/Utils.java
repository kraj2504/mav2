package utility;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils
{
	public static WebDriver driver = null;

	public static WebDriver OpenBrowser(int iTestCaseRow) throws Exception
	{
		String sBrowserName;

		try
		{
			sBrowserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Browser);

			if(sBrowserName.equals("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver", ".//Drivers//Chrome//chromedriver_win32//chromedriver.exe");
			
				driver = new ChromeDriver();

				Log.info("Chrome driver instantiated");
				
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				driver.get(Constant.URL);

				Log.info("Web application launched successfully");
				
				Utils.waitForElement(By.name("first_name"));
			}
			else if(sBrowserName.equals("Firefox"))
			{
				System.setProperty("webdriver.gecko.driver", ".//Drivers//Firefox//geckodriver-v0.17.0-win64//geckodriver.exe");
			
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				
			//	DesiredCapabilities dc = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			
				capabilities.setCapability("marionette", false);

				driver = new FirefoxDriver();

				Log.info("Firefox driver instantiated");
				
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				driver.get(Constant.URL);

				Log.info("Web application launched successfully");
				
				Utils.waitForElement(By.name("first_name"));
			}
			else if(sBrowserName.equals("IE"))
			{
				System.setProperty("webdriver.ie.driver", ".//Drivers//IE//IEDriverServer_x64_3.4.0//IEDriverServer.exe");
			
				driver = new InternetExplorerDriver();

				Log.info("IE driver instantiated");
				
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				driver.get(Constant.URL);

				Log.info("Web application launched successfully");
				
				Utils.waitForElement(By.name("first_name"));
			}

		}
		catch (Exception e)
		{
			Log.error("Class Utils | Method OpenBrowser | Exception desc : "+e.getMessage());
		}
		return driver;
	}

	public static WebDriver OpenBrowser(int iTestCaseRow,String URL) throws Exception
	{
		String sBrowserName;

		try
		{
			sBrowserName = ExcelUtils.getCellData(iTestCaseRow, Constant.col_Browser);

			if(sBrowserName.equals("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver", ".//Drivers//Chrome//chromedriver_win32//chromedriver.exe");
			
				driver = new ChromeDriver();

				Log.info("Chrome driver instantiated");
				
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				driver.get(URL);

				Log.info("Web application launched successfully");
				
				Utils.waitForElement(By.name("first_name"));
			}
			else if(sBrowserName.equals("Firefox"))
			{
				System.setProperty("webdriver.gecko.driver", ".//Drivers//Firefox//geckodriver-v0.17.0-win64//geckodriver.exe");
			
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				
			//	DesiredCapabilities dc = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			
				capabilities.setCapability("marionette", false);

				driver = new FirefoxDriver();

				Log.info("Firefox driver instantiated");
				
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				driver.get(Constant.URL);

				Log.info("Web application launched successfully");
				
				Utils.waitForElement(By.name("first_name"));
			}
			else if(sBrowserName.equals("IE"))
			{
				System.setProperty("webdriver.ie.driver", ".//Drivers//IE//IEDriverServer_x64_3.4.0//IEDriverServer.exe");
			
				driver = new InternetExplorerDriver();

				Log.info("IE driver instantiated");
				
				driver.manage().window().maximize();

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				driver.get(Constant.URL);

				Log.info("Web application launched successfully");
				
				Utils.waitForElement(By.name("first_name"));
			}

		}
		catch (Exception e)
		{
			Log.error("Class Utils | Method OpenBrowser | Exception desc : "+e.getMessage());
		}
		return driver;
	}

	public static String getTestCaseName(String sTestCase)throws Exception
	{
		String value = sTestCase; //Ex test case name is --> testCases.GeneratingBilling@3e6fa38a
		try
		{
			int posi = value.indexOf("@");

			value = value.substring(0, posi);

			posi = value.lastIndexOf(".");	

			value = value.substring(posi + 1);

			return value;

		}
		catch (Exception e)
		{
			Log.error("Class Utils | Method getTestCaseName | Exception desc : "+e.getMessage());

			throw (e);
		}
	}

	public static void mouseHoverAction(WebElement mainElement, String subElement)
	{
		 Actions action = new Actions(driver);

         action.moveToElement(mainElement).perform();

         if(subElement.equals("Accessories"))
         {
        	 action.moveToElement(driver.findElement(By.linkText("Accessories")));

        	 Log.info("Accessories link is found under Product Category");
         }
         action.click();

         action.perform();

         Log.info("Click action is performed on the selected Product Type");
	 }

	public static void scrollIntoView(WebElement eName)
	{
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",eName);
	}
	
	public static void scrollIntoView(By locator)
	{
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(locator));
	}
	
	public static void waitUntilPageLoad(String title) throws Exception
	 {
		 waitUntilAngularFinishHttpCalls();
		 WebDriverWait wait = new WebDriverWait(driver, 10);
	     wait.until(ExpectedConditions.titleContains(title));
	     waitUntilAngularFinishHttpCalls();
	 }
	
	 public static void waitForElement(WebElement element) throws Exception
	 {
		 WebDriverWait wait = new WebDriverWait(driver, 20);
	     wait.until(ExpectedConditions.visibilityOf(element));// elementToBeClickable(element)
	 }
	 
	 public static void waitForElement(By locator) throws Exception
	 {
		 waitUntilAngularFinishHttpCalls();
		 WebDriverWait wait = new WebDriverWait(driver, 10);
	     wait.until(ExpectedConditions.visibilityOfElementLocated(locator));//elementToBeClickable(locator)
	     
//	     WebDriverWait WaitVar1 = new WebDriverWait(driver,10);
//	     WaitVar1.until(ExpectedConditions.visibilityOfElementLocated(By.id("SearchTexttxt")));
	 }
	 
	 public static void waitUntilInvisibilityOf(WebElement webElement) throws Exception
	 {
		 waitUntilAngularFinishHttpCalls();
		 WebDriverWait wait = new WebDriverWait(driver, 10);
	     wait.until(ExpectedConditions.invisibilityOf(webElement));
	 }
/*	 
	 public static void WaitForAjax() throws InterruptedException
	 {
	        while (true)
	        {
	            if ((Boolean) ((JavascriptExecutor)driver).executeScript("return jQuery.active == 0"))
	                break;
	            Thread.sleep(100);
	        }
	        waitForJSandJQueryToLoad();
	 }
*/	 
	 public static void waitUntilAngularFinishHttpCalls() throws Exception 
	 {
		 Thread.sleep(500);
	        final String javaScriptToLoadAngular =
	                "var injector = window.angular.element('body').injector();" + 
	                "var $http = injector.get('$http');" + 
	                "return ($http.pendingRequests.length === 0)";

	        ExpectedCondition<Boolean> pendingHttpCallsCondition = new ExpectedCondition<Boolean>() 
	        {
	            public Boolean apply(WebDriver driver) 
	            {
	                return ((JavascriptExecutor) driver).executeScript(javaScriptToLoadAngular).equals(true);
	            }
	        };
	        WebDriverWait wait = new WebDriverWait(driver, 30); // timeout = 20 secs
	        wait.until(pendingHttpCallsCondition);
	        Thread.sleep(1000);
	  }
/*	 
	 public static boolean waitForJSandJQueryToLoad() 
	 {

		    WebDriverWait wait = new WebDriverWait(driver, 30);

		    // wait for jQuery to load
		    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		        try {
		          return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
		        }
		        catch (Exception e) {
		          // no jQuery present
		          return true;
		        }
		      }
		    };

		    // wait for Javascript to load
		    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		        return ((JavascriptExecutor)driver).executeScript("return document.readyState")
		        .toString().equals("complete");
		      }
		    };

		  return wait.until(jQueryLoad) && wait.until(jsLoad);
		}
*/	 
	 public static void waitUntillStaleness(WebElement element)
	 {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.stalenessOf(element));
	 }
	 
	 public static void fluentwait(final By locator)
	 {
		 try
		 {
		 	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			       .withTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
			       .pollingEvery(3, java.util.concurrent.TimeUnit.SECONDS)
			       .ignoring(NoSuchElementException.class);

			   WebElement ele = wait.until(new Function<WebDriver, WebElement>() 
			   {
			     public WebElement apply(WebDriver driver) 
			     {
			       return driver.findElement(locator);
			     }
			   });
		 }
		 catch(NoSuchElementException e)
		 {
			 Log.error("Element not found after fluent wait --> NoSuchElementException");
			 throw (e);
		 }
		 catch(TimeoutException e)
		 {
			 Log.error("Element not found after fluent wait --> Timeout Exception");
			 throw (e);
		 }
		 
	 }
	 
	 public static void clickElement(WebElement element)
	 {
		 WebDriverWait wait = new WebDriverWait(driver, 15);
	     wait.until(ExpectedConditions.elementToBeClickable(element));
	     element.click();
	 }

	 public static void takeScreenshot(WebDriver driver, String sTestCaseName) throws Exception
	 {
			try
			{
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

				FileUtils.copyFile(scrFile, new File(Constant.Path_ScreenShot + sTestCaseName +".jpg"));
			
				Log.info("screenshot taken");
			}
			catch (Exception e)
			{

				Log.error("Class Utils | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());

				throw new Exception();
			}
	 }
	 
	 public static String getRandomName(int length) 
	 {
	        return "ABC"+RandomStringUtils.randomAlphabetic(length-3);
	 }
	 
	 public static String getRandomNumber(int length) 
	 {
	        return "9"+RandomStringUtils.randomNumeric(length-1);
	 }
	 
	 public static void keyEsc() throws Exception
	 {
//		 driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		 Thread.sleep(3000);
		 Robot r = new Robot();
		 r.keyPress(KeyEvent.VK_ESCAPE);
		 r.keyRelease(KeyEvent.VK_ESCAPE);
	 }
	 
	 public static void keyEnter() throws Exception
	 {
//		 driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		 Thread.sleep(1000);
		 Robot r = new Robot();
		 r.keyPress(KeyEvent.VK_ENTER);
		 r.keyRelease(KeyEvent.VK_ENTER);
	 }
	 
	 public static void keyCtrlW() throws Exception
	 {
//		 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		 Thread.sleep(2000);
		 Robot robot = new Robot();
		 robot.keyPress(KeyEvent.VK_CONTROL);
		 robot.keyPress(KeyEvent.VK_W);
		 robot.keyRelease(KeyEvent.VK_CONTROL);
		 robot.keyRelease(KeyEvent.VK_W);
	 }
	 
	 public static String roundUsingBigDecimal(double value, int decimalPlace) 
	 {
		 BigDecimal bd = new BigDecimal(Double.toString(value));
		 bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		 return Float.toString(bd.floatValue());
	 }
	 
	 public static String roundUsingDecimalFormat(double expectedRefund) 
	 {
		 DecimalFormat df = new DecimalFormat("###.##");
		 return df.format(expectedRefund);
	 }
}