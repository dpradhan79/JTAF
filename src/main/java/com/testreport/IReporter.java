package com.testreport;

public interface IReporter {
	
	 void InitTestCase(String testcaseName, String testCaseDescription);
	 void LogSuccess(String stepName, String stepDescription, String screenShotPath);
	 void LogFailure(String stepName, String stepDescription, String screenShotPath);
	 void LogInfo(String message, String screenShotPath);
	 void LogWarning(String stepName, String stepDescription, String screenShotPath);
	 void LogException(Exception ex, String screenShotPath);
	 void LogFatal(Exception ex, String screenShotPath);
	 void UpdateTestCaseStatus();
	 void Close();
	 void ManipulateTestReport(ITestReportManipulator objTestReportManipulator);
}
