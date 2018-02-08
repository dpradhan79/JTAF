package test.troubleshoot;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import test.templates.TestTemplate;

public class TestSubstract extends TestTemplate{
	
private static final Logger LOG = Logger.getLogger(TestSubstract.class);
	
	@DataProvider(name="getSubTestData", parallel = true)
	public Object[][] getSubTestData()
	{
		Object [][] subData = new Object[][] {{3,1,2}, {5,3,2}};
		return subData;
	}

	@Parameters({"testSub1", "testSub2", "expectedSubResult"})	
	@Test(dataProvider="getSubTestData")
	public void testSubstract(int testSub1, int testSub2, int expectedSubResult)
	{
		LOG.info(String.format("%d - %d = %d", testSub1, testSub2, expectedSubResult));
		Assert.assertEquals(expectedSubResult, testSub1 - testSub2);
		/*int i = 1000;
		while(i > 900)
		{
			LOG.info(i);
			i--;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}

}
