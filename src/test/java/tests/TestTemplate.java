package tests;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.excel.Xls_Reader;
import com.google.common.io.Resources;
import com.pages.SoftCoLoginPage;
import com.utilities.ReusableLibs;
import com.utilities.TestUtil;

public class TestTemplate {
	
	private static final Logger LOG = Logger.getLogger(TestTemplate.class);
	protected WebDriver webDriver = null;
	protected String ChromeDriverExe = null;
	protected String url = null;
	protected String implicitWaitInSecs = null;
	protected String pageLoadTimeOutInSecs = null;
	
	@DataProvider(name = "getDataFromExcel")
	public Object[][] getDataFromExcel() throws URISyntaxException	
	{
		URL urlFilePath = Resources.getResource("testdata/WebAutomationTestData.xlsx");
		String filePath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
		Xls_Reader xlsReader = new Xls_Reader(filePath);
		Object [][] objMetrics = TestUtil.getData("UserPermission", xlsReader, "UserPermissions");
		
		return objMetrics;
	}
	
	@BeforeMethod
	public void beforeMethod(ITestContext testContext, Method m) throws URISyntaxException
	{
		LOG.info(String.format("Test Method To Be Executed Next -  %s", m.getName()));	
		
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
				
				this.webDriver = new ChromeDriver(options);
				
				break;
		}
		
		this.implicitWaitInSecs = reUsableLib.getConfigProperty("ImplicitWaitInSecs");
		this.pageLoadTimeOutInSecs = reUsableLib.getConfigProperty("PageLoadTimeOutInSecs");
		
    	this.webDriver.manage().timeouts().implicitlyWait(Integer.parseInt(this.implicitWaitInSecs), TimeUnit.SECONDS);
    	this.webDriver.manage().timeouts().pageLoadTimeout(Integer.parseInt(this.pageLoadTimeOutInSecs), TimeUnit.SECONDS);
    	this.webDriver.manage().window().maximize();
		
	}
	
	@AfterMethod
	public void afterMethod(ITestContext testContext, Method m) throws Exception
	{
		LOG.info(String.format("Test Method Execution Completed For -  %s", m.getName()));	
		new SoftCoLoginPage(this.webDriver).logout();
		this.webDriver.close();
		this.webDriver.quit();
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
