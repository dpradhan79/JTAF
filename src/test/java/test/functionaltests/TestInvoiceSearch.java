package test.functionaltests;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import aut.pages.SoftCoGlobalSearchPage;
import aut.pages.SoftCoLoginPage;
import test.templates.TestTemplate;

public class TestInvoiceSearch extends TestTemplate{
	
	private static final Logger LOG = Logger.getLogger(TestInvoiceSearch.class);
	@Test(dataProvider = "getDataFromExcel", groups = {"ARProcessingQueue", "ARGlobalSearch"})
	public void validateInvoiceSearch(Hashtable<String, String> data) throws Exception
	{
		String userName = data.get("UserName");
		String password = data.get("Password");
		String isEditable = data.get("search_isEditable");
		
		SoftCoLoginPage loginPage = new SoftCoLoginPage(threadLocalWebDriver.get(), TestTemplate.testReport);
		boolean isSuccess = loginPage.login(this.url, userName, password);
		if(isSuccess)
		{
			LOG.info(String.format("Login Successful for user - %s", userName));
		}
		else
		{
			LOG.error(String.format("Login Not Successful for user - %s", userName));
		}
		
		SoftCoGlobalSearchPage searchPage = new SoftCoGlobalSearchPage(threadLocalWebDriver.get(), TestTemplate.testReport);
		searchPage.validateSearchForAnInvoice(isEditable);
	}

}
