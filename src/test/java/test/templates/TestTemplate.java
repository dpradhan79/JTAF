package test.templates;

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
import com.testreport.ExtentReporter;
import com.testreport.ExtentReporter.ExtentTestVisibilityMode;
import com.testreport.IReporter;
import com.testreport.ReportFactory;
import com.testreport.ReportFactory.ReportType;
import com.utilities.ReusableLibs;
import com.utilities.TestUtil;

import aut.pages.SoftCoLoginPage;

/**
 * 
 * @author E001518 - Debasish Pradhan (Architect)
 *
 */
public class TestTemplate {

	private static final Logger LOG = Logger.getLogger(TestTemplate.class);
	protected static IReporter testReport = null;
	protected String ChromeDriverExe = null;
	protected String url = null;
	protected static String implicitWaitInSecs = null;
	protected static String pageLoadTimeOutInSecs = null;
	public static ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<WebDriver>();
	private static ReusableLibs reUsableLib = null;

	@DataProvider(name = "getDataFromExcel", parallel = true)
	public Object[][] getDataFromExcel() throws URISyntaxException {
		URL urlFilePath = Resources.getResource("testdata/WebAutomationTestData.xlsx");
		String filePath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
		Xls_Reader xlsReader = new Xls_Reader(filePath);
		Object[][] objMetrics = TestUtil.getData("UserPermission", xlsReader, "UserPermissions");

		return objMetrics;
	}

	@BeforeSuite
	public void beforeSuite(ITestContext testContext, XmlTest xmlTest) throws Exception {

		LOG.info(String.format("Suite To Be Executed Next -  %s", testContext.getSuite().getName()));
		TestTemplate.reUsableLib = new ReusableLibs();
		TestTemplate.implicitWaitInSecs = reUsableLib.getConfigProperty("ImplicitWaitInSecs");
		TestTemplate.pageLoadTimeOutInSecs = reUsableLib.getConfigProperty("PageLoadTimeOutInSecs");
		String extentTestVisibilityMode = reUsableLib.getConfigProperty("extentTestVisibilityMode");

		TestTemplate.testReport = ReportFactory.getInstance(ReportType.ExtentHtml,
				ExtentTestVisibilityMode.valueOf(extentTestVisibilityMode));
	}

	@BeforeTest
	public void beforeTest(ITestContext testContext) {
		LOG.info(String.format("Thread - %d, Executes Next Test - %s ", Thread.currentThread().getId(),
				testContext.getCurrentXmlTest().getName()));
		if (((ExtentReporter) TestTemplate.testReport)
				.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestTagAsTestsAtLeft) {
			TestTemplate.testReport
					.createTestNgXMLTestTag(String.format("%s", testContext.getCurrentXmlTest().getName()));

		}
	}

	@AfterTest
	public void afterTest(ITestContext testContext) {
		LOG.info(String.format("Test - %s , Completed", testContext.getCurrentXmlTest().getName()));
		TestTemplate.testReport.updateTestCaseStatus();
	}

	@BeforeMethod
	public void beforeMethod(ITestContext testContext, Method m) throws URISyntaxException {
		try {
			LOG.info(String.format("Thread - %d , Executes Next Test Method - %s", Thread.currentThread().getId(),
					m.getName()));

			WebDriver webDriver = null;

			if (TestTemplate.testReport instanceof ExtentReporter) {

				if (((ExtentReporter) TestTemplate.testReport)
						.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestTagAsTestsAtLeft) {

					/*TestTemplate.testReport
							.createTestNgXMLTestTag(String.format("%s", testContext.getCurrentXmlTest().getName()));*/
					TestTemplate.testReport.initTestCase(String.format("%s", m.getName()));
				} else if (((ExtentReporter) TestTemplate.testReport)
						.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestMethodsAsTestAtLeft) {
					TestTemplate.testReport.initTestCase(
							String.format("%s-%s", testContext.getCurrentXmlTest().getName(), m.getName()));
				}
			}

			// Use APPURL if provided in Test Suite XML
			this.url = testContext.getCurrentXmlTest().getParameter("APPURL");
			if (this.url == null) {
				this.url = TestTemplate.reUsableLib.getConfigProperty("APPURL");
			}

			// Use browser specific driver as provided in Test Suite XML or else use
			// chromedriver
			String browser = testContext.getCurrentXmlTest().getParameter("Browser");
			if (browser == null) {
				browser = "Chrome";
			}

			switch (browser) {
			case "Chrome":

				this.ChromeDriverExe = TestTemplate.reUsableLib.getConfigProperty("ChromeDriverExe");
				URL urlFilePath = Resources
						.getResource(String.format("%s%s%s", "drivers", File.separatorChar, this.ChromeDriverExe));
				String chromedriverPath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
				System.setProperty("webdriver.chrome.driver", chromedriverPath);

				/* Chrome Settings */
				Map<String, Object> prefs = new HashMap<String, Object>();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-extensions");
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
				/* Chrome settings Done */

				webDriver = new ChromeDriver(options);
				threadLocalWebDriver.set(webDriver);
				break;
			}

			webDriver.manage().timeouts().implicitlyWait(Integer.parseInt(TestTemplate.implicitWaitInSecs),
					TimeUnit.SECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(Integer.parseInt(TestTemplate.pageLoadTimeOutInSecs),
					TimeUnit.SECONDS);
			webDriver.manage().window().maximize();

		} catch (Exception ex) {
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex);

		} finally {
			TestTemplate.testReport.logInfo(String.format("Thread - %d , Executes Next Test Method - %s",
					Thread.currentThread().getId(), m.getName()));
		}

	}

	@AfterMethod
	public void afterMethod(ITestContext testContext, ITestResult testResult, Method m) throws Exception {
		LOG.info(String.format("Thread - %d , Completes Executing Test Method - %s", Thread.currentThread().getId(),
				m.getName()));
		TestTemplate.testReport.logInfo(String.format("Thread - %d , Completes Executing Test Method - %s",
				Thread.currentThread().getId(), m.getName()));
		try {
			new SoftCoLoginPage(threadLocalWebDriver.get(), TestTemplate.testReport).logout();
		} catch (Exception ex) {
			LOG.error(
					String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}

		try {
			threadLocalWebDriver.get().close();
		} catch (Exception ex) {
			LOG.error(
					String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}

		try {
			threadLocalWebDriver.get().quit();
		} catch (Exception ex) {
			LOG.error(
					String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}

		finally {
			TestTemplate.testReport.updateTestCaseStatus();
		}
	}

	protected synchronized String getScreenShotName() {
		String screenShotLocation = reUsableLib.getConfigProperty("ScreenshotLocation");
		String fileExtension = reUsableLib.getConfigProperty("ScreenshotPictureFormat");
		return ReusableLibs.getScreenshotFile(screenShotLocation, fileExtension);
	}

}
