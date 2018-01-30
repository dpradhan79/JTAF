package testexecution;

import org.testng.TestNG;

public class TestDriver {
	
	public static void main(String [] args)
	{
		TestNG testNG = new TestNG();
		testNG.setDefaultSuiteName("SoftCo");
		testNG.setDefaultTestName("Test Search");
		testNG.setTestClasses(new Class[] {functionaltests.TestSearchPerUserInParallel.class});
		testNG.run();
	}
}
