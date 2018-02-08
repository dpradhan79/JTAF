package test.functionaltests;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.config.IConstants;

import aut.pages.SoftCoGlobalSearchPage;
import aut.pages.SoftCoLoginPage;
import test.templates.TestTemplate;

public class TestSearchPerUserInParallel extends TestTemplate {
	
	private static final Logger LOG = Logger.getLogger(TestSearchPerUserInParallel.class);
	
	@Test(dataProvider = "getDataFromExcel", groups = {"ARProcessingQueue", "ARGlobalSearch"})
	public void validateAllSearches(Hashtable<String, String> data, ITestContext testContext, Method m) throws Exception
	{
				
			String userName = null;
			String password = null;
			String isEditable = null;
			
			////
			LOG.info(String.format("Test Method To Be Executed Next -  %s", m.getName()));	
			
					
			LOG.info(String.format("Before Login, Thread Id = %d", Thread.currentThread().getId()));			
			LOG.info(String.format("Before Login webdriver = %s, Thread Id = %d", threadLocalWebDriver.get(), Thread.currentThread().getId()));
			userName = data.get("UserName");
			password = data.get("Password");
			isEditable = data.get("search_isEditable");
			TestTemplate.testReport.logInfo(String.format("Test Data In Use - UserName = %s, Password = %s, search_isEditable = %s", userName, password, isEditable));
			SoftCoLoginPage loginPage = new SoftCoLoginPage(threadLocalWebDriver.get(), TestTemplate.testReport);
			try
			{
				boolean isSuccess = loginPage.login(url, userName, password);
				if(isSuccess)
				{
					LOG.info(String.format("Login Successful for user - %s", userName));
					TestTemplate.testReport.logSuccess(String.format("Login Successful for user - %s", userName));
				}
				else
				{
					LOG.error(String.format("Login Not Successful for user - %s", userName));	
					TestTemplate.testReport.logFailure("Login", String.format("Login Not Successful for user - %s", userName), this.getScreenShotName());
					
				}
				LOG.info(String.format("After Login, Thread Id = %d", Thread.currentThread().getId()));			
				LOG.info(String.format("After Login webdriver = %s, Thread Id = %d", threadLocalWebDriver.get(), Thread.currentThread().getId()));
			}
			catch(Exception ex)
			{
				TestTemplate.testReport.logException(ex, this.getScreenShotName());
			}
		
		/*SoftCoGlobalSearchPage searchPage = new SoftCoGlobalSearchPage(threadLocalWebDriver.get(), TestTemplate.testReport);
		
		try
		{
			searchPage.validateSearchForDocument(isEditable);
		}
		catch(Exception ex)
		{
			Reporter.log(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex, this.getScreenShotName());
			searchPage.clickOnInvoices();
		}
		try
		{
			searchPage.validateSearchForAnInvoice(isEditable);
		}
		catch(Exception ex)
		{
			Reporter.log(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex, this.getScreenShotName());
			searchPage.clickOnInvoices();
		}
		try
		{
			searchPage.validateSearchForATimeSheet(isEditable);
		}
		catch(Exception ex)
		{
			Reporter.log(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex, this.getScreenShotName());
			searchPage.clickOnInvoices();
		}
		try
		{
			searchPage.validateSearchCarRental(isEditable);
		}
		catch(Exception ex)
		{
			Reporter.log(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex, this.getScreenShotName());
			searchPage.clickOnInvoices();
		}
		try
		{
			searchPage.validateSearchAirfare(isEditable);
		}
		catch(Exception ex)
		{
			Reporter.log(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex, this.getScreenShotName());
			searchPage.clickOnInvoices();
		}
		try
		{
			searchPage.validateSearchHotel(isEditable);
		}
		catch(Exception ex)
		{
			Reporter.log(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex, this.getScreenShotName());
			searchPage.clickOnInvoices();
		}
		try
		{
			searchPage.validateSearchRequestIt(isEditable);
		}
		catch(Exception ex)
		{
			Reporter.log(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex, this.getScreenShotName());
			searchPage.clickOnInvoices();
		}		
*/		
	}
	
	

}
