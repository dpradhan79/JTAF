package test.troubleshoot;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import test.templates.TestTemplate;



public class TestSum extends TestTemplate {
	private static final Logger LOG = Logger.getLogger(TestSum.class);
	
	@DataProvider(name="getSumTestData", parallel = true)
	public Object[][] getSumTestData()
	{
		Object [][] sumData = new Object[][] {{1,2,3}, {2,3,5}};
		return sumData;
	}
	
	@Parameters({"testSum1", "testSum2", "expectedSumResult"})
	@Test(dataProvider="getSumTestData", testName = "testAddition", description = "validate sum")
	public void testAddition(int testSum1, int testSum2, int expectedSumResult)
	{
		LOG.info(String.format("%d + %d = %d", testSum1, testSum2, expectedSumResult));
		Assert.assertEquals(expectedSumResult, testSum1 + testSum2);
		/*int i = 0;
		while(i < 100)
		{
			LOG.info(i);
			i++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}

}
