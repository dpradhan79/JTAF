package tests;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.excel.Xls_Reader;
import com.google.common.io.Resources;
import com.utilities.TestUtil;
public class TestDataProvider {

	@DataProvider(name = "getDataFromExcel")
	public Object[][] getDataFromExcel() throws URISyntaxException	
	{
		URL urlFilePath = Resources.getResource("testdata/WebAutomationTestData.xlsx");
		String filePath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
		Xls_Reader xlsReader = new Xls_Reader(filePath);
		Object [][] objMetrics = TestUtil.getData("UserPermission", xlsReader, "UserPermissions");
		
		return objMetrics;
	}
	
	@Test(dataProvider = "getDataFromExcel")
	public void validateDataProvider(Hashtable<String, String> data)
	{
		System.out.println();
	}
	
	
}
