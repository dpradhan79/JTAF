package functionaltests;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pages.SoftCoGlobalSearchPage;
import com.pages.SoftCoLoginPage;

import templates.TestTemplate;

public class TestCarRentalSearch extends TestTemplate{
	
	private static final Logger LOG = Logger.getLogger(TestCarRentalSearch.class);
	@Test(dataProvider = "getDataFromExcel", groups = {"ARProcessingQueue", "ARGlobalSearch"})
	public void validateCarRentalSearch(Hashtable<String, String> data) throws Exception
	{
		String userName = data.get("UserName");
		String password = data.get("Password");
		String isEditable = data.get("searchDocument_isEditable");
		
		SoftCoLoginPage loginPage = new SoftCoLoginPage(this.webDriver, this.testReport);
		boolean isSuccess = loginPage.login(this.url, userName, password);
		if(isSuccess)
		{
			LOG.info(String.format("Login Successful for user - %s", userName));
		}
		else
		{
			LOG.error(String.format("Login Not Successful for user - %s", userName));
		}
		
		SoftCoGlobalSearchPage searchPage = new SoftCoGlobalSearchPage(this.webDriver, this.testReport);
		searchPage.validateSearchCarRental(isEditable);
	}

}
