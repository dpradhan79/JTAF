package com.testreport;

public interface IReporter {
	
	 void InitTestCase(String testcaseName); 	 
	 
	 void CreateNode(String nodeName);	 
	 
	 void LogSuccess(String stepName);
	 void LogSuccess(String stepName, String stepDescription);
	 void LogSuccess(String stepName, String stepDescription, String screenShotPath);
	 
	 void LogFailure(String stepName);
	 void LogFailure(String stepName, String stepDescription);
	 void LogFailure(String stepName, String stepDescription, String screenShotPath);
	 
	 void LogInfo(String message);
	 void LogInfo(String message, String screenShotPath);
	 
	 void LogWarning(String stepName);
	 void LogWarning(String stepName, String stepDescription);
	 void LogWarning(String stepName, String stepDescription, String screenShotPath);
	 
	 void LogException(Exception ex);
	 void LogException(Exception ex, String screenShotPath);
	 
	 void LogFatal(Exception ex);
	 void LogFatal(Exception ex, String screenShotPath);
	 
	 
	 void UpdateTestCaseStatus();
	 void Close();
	 void ManipulateTestReport(ITestReportManipulator objTestReportManipulator);
}
