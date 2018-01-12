package tests;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pages.SoftCoGlobalSearchPage;
import com.pages.SoftCoLoginPage;

public class TestHotelSearch extends TestTemplate{
	
	private static final Logger LOG = Logger.getLogger(TestHotelSearch.class);
	@Test(dataProvider = "getDataFromExcel", groups = {"ARProcessingQueue", "ARGlobalSearch"})
	public void validateHotelSearch(Hashtable<String, String> data) throws Exception
	{
		String userName = data.get("UserName");
		String password = data.get("Password");
		String isEditable = data.get("searchDocument_isEditable");
		
		SoftCoLoginPage loginPage = new SoftCoLoginPage(this.webDriver);
		boolean isSuccess = loginPage.login(this.url, userName, password);
		if(isSuccess)
		{
			LOG.info(String.format("Login Successful for user - %s", userName));
		}
		else
		{
			LOG.error(String.format("Login Not Successful for user - %s", userName));
		}
		
		SoftCoGlobalSearchPage searchPage = new SoftCoGlobalSearchPage(this.webDriver);
		searchPage.validateSearchHotel(isEditable);
	}

}
