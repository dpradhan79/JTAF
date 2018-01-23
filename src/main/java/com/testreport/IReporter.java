package com.testreport;
/**
 * 
 * @author E001518 - Debasish Pradhan (Architect)
 *
 */
public interface IReporter {
	
	 void initTestCase(String testcaseName); 	 
	 
	 void createTestNgXMLTestTag(String nodeName);	 
	 
	 void logSuccess(String stepName);
	 void logSuccess(String stepName, String stepDescription);
	 void logSuccess(String stepName, String stepDescription, String screenShotPath);
	 
	 void logFailure(String stepName);
	 void logFailure(String stepName, String stepDescription);
	 void logFailure(String stepName, String stepDescription, String screenShotPath);
	 
	 void logInfo(String message);
	 void logInfo(String message, String screenShotPath);
	 
	 void logWarning(String stepName);
	 void logWarning(String stepName, String stepDescription);
	 void logWarning(String stepName, String stepDescription, String screenShotPath);
	 
	 void logException(Exception ex);
	 void logException(Exception ex, String screenShotPath);
	 
	 void logFatal(Exception ex);
	 void logFatal(Exception ex, String screenShotPath);
	 
	 
	 void updateTestCaseStatus();
	 void close();
	 void manipulateTestReport(ITestReportManipulator objTestReportManipulator);
}
