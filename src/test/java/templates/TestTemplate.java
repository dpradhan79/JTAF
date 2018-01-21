package templates;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlTest;

import com.excel.Xls_Reader;
import com.google.common.io.Resources;
import com.pages.SoftCoLoginPage;
import com.testreport.ExtentReporter;
import com.testreport.IReporter;
import com.utilities.ReusableLibs;
import com.utilities.TestUtil;

public class TestTemplate {
	
	private static final Logger LOG = Logger.getLogger(TestTemplate.class);	
	protected IReporter testReport = null;
	protected String ChromeDriverExe = null;
	protected String url = null;
	protected String implicitWaitInSecs = null;
	protected String pageLoadTimeOutInSecs = null;	
	protected ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<WebDriver>();
	
	@DataProvider(name = "getDataFromExcel")
	public Object[][] getDataFromExcel() throws URISyntaxException	
	{
		URL urlFilePath = Resources.getResource("testdata/WebAutomationTestData.xlsx");
		String filePath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
		Xls_Reader xlsReader = new Xls_Reader(filePath);
		Object [][] objMetrics = TestUtil.getData("UserPermission", xlsReader, "UserPermissions");
		
		return objMetrics;
	}
	@BeforeSuite
	public void beforeSuite(ITestContext testContext, XmlTest xmlTest) throws URISyntaxException
	{
		String htmlReportName = null;
		String screenShotLocation = null;
		String strBoolAppendExisting = null;
		String strIsCignitiLogoRequired = null;
		boolean boolAppendExisting = false;
		boolean boolIsCignitiLogoRequired = false;
		String extentConfigFile = null;
		
		LOG.info(String.format("Suite To Be Executed Next -  %s", testContext.getSuite().getName()));	
		ReusableLibs reUsableLib = new ReusableLibs();
		htmlReportName = reUsableLib.getConfigProperty("HtmlReport");
		screenShotLocation = reUsableLib.getConfigProperty("ScreenshotLocation");		
		strBoolAppendExisting = reUsableLib.getConfigProperty("boolAppendExisting");
		strIsCignitiLogoRequired = reUsableLib.getConfigProperty("isCignitiLogoRequired");
		extentConfigFile = reUsableLib.getConfigProperty("extentConfigFile");		
		if(strBoolAppendExisting !=null && strBoolAppendExisting.equalsIgnoreCase("true"))
		{
			boolAppendExisting = true;
		}
		
		if(strIsCignitiLogoRequired !=null && strIsCignitiLogoRequired.equalsIgnoreCase("true"))
		{
			boolIsCignitiLogoRequired = true;
		}
		
		reUsableLib.makeDir(screenShotLocation);
		String filePath = String.format("%s%s%s", screenShotLocation, File.separatorChar, htmlReportName);
		this.testReport = new ExtentReporter(filePath, extentConfigFile, boolAppendExisting, boolIsCignitiLogoRequired);
	}
	
	@BeforeMethod
	public void beforeMethod(ITestContext testContext, Method m) throws URISyntaxException
	{
		LOG.info(String.format("Test Method To Be Executed Next -  %s", m.getName()));	
		WebDriver webDriver = null;
		this.testReport.InitTestCase(m.getName());
		ReusableLibs reUsableLib = new ReusableLibs();
		
		//Use APPURL if provided in Test Suite XML
		this.url = testContext.getCurrentXmlTest().getParameter("APPURL");
		if(this.url == null)
		{
			this.url = reUsableLib.getConfigProperty("APPURL");
		}
		
		//Use browser specific driver as provided in Test Suite XML or else use chromedriver
		String browser = testContext.getCurrentXmlTest().getParameter("Browser");
		if(browser == null)
		{
			browser = "Chrome";
		}
		
		switch(browser)
		{
			case "Chrome":
				
				this.ChromeDriverExe = reUsableLib.getConfigProperty("ChromeDriverExe");
				URL urlFilePath = Resources.getResource(String.format("%s%s%s", "drivers", File.separatorChar, this.ChromeDriverExe));
				String chromedriverPath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
				System.setProperty("webdriver.chrome.driver",chromedriverPath);
				
				/*Chrome Settings */
				Map<String, Object> prefs =new HashMap<String, Object>();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-extensions");
				prefs .put("credentials_enable_service", false);
				prefs .put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
				/*Chrome settings Done*/
				
				webDriver = new ChromeDriver(options);
				threadLocalWebDriver.set(webDriver);
				break;
		}
		
		this.implicitWaitInSecs = reUsableLib.getConfigProperty("ImplicitWaitInSecs");
		this.pageLoadTimeOutInSecs = reUsableLib.getConfigProperty("PageLoadTimeOutInSecs");
		
    	webDriver.manage().timeouts().implicitlyWait(Integer.parseInt(this.implicitWaitInSecs), TimeUnit.SECONDS);
    	webDriver.manage().timeouts().pageLoadTimeout(Integer.parseInt(this.pageLoadTimeOutInSecs), TimeUnit.SECONDS);
    	webDriver.manage().window().maximize();
		
	}
	
	@AfterMethod
	public void afterMethod(ITestContext testContext, ITestResult testResult, Method m) throws Exception
	{
		LOG.info(String.format("Test Method Execution Completed For -  %s", m.getName()));	
		try
		{
			new SoftCoLoginPage(threadLocalWebDriver.get(), this.testReport).logout();
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
		try
		{
			threadLocalWebDriver.get().close();
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
		try
		{
			threadLocalWebDriver.get().quit();	
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
		try
		{
			//Log to extent report
			switch(testResult.getStatus())
			{
			case ITestResult.SUCCESS :
				this.testReport.LogSuccess(m.getName());
				break;
				
			case ITestResult.FAILURE :
				this.testReport.LogFailure(m.getName());
				break;
			}
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		this.testReport.UpdateTestCaseStatus();
	}
	
	@BeforeTest
	public void beforeTest(ITestContext testContext)
	{
		LOG.info(String.format("Test - %s , About To Start", testContext.getCurrentXmlTest().getName()));
	}
	
	@AfterTest
	public void afterTest(ITestContext testContext)
	{
		LOG.info(String.format("Test - %s , Completed", testContext.getCurrentXmlTest().getName()));
	}

}
