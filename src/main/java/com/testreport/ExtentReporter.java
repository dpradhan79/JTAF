package com.testreport;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.testng.log4testng.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReporter implements IReporter {

	private static final Logger LOG = Logger.getLogger(ExtentReporter.class);
	private boolean boolAppendExisting= false;
	private boolean isCignitiLogoRequired = false;
	private ExtentReports objExtentReport = null;
    private ExtentTest objExtentTest = null;
    
	public ExtentReporter(String filePath, boolean boolAppendExisting, boolean isCignitiLogoRequired)
	{
		this.boolAppendExisting = boolAppendExisting;
		this.isCignitiLogoRequired = isCignitiLogoRequired;
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
		htmlReporter.setAppendExisting(boolAppendExisting);
		this.objExtentReport = new ExtentReports();
		this.objExtentReport.attachReporter(htmlReporter);
			
		
	}
	

	@Override
	public void InitTestCase(String testcaseName) {
		this.objExtentTest = this.objExtentReport.createTest(testcaseName);		
		LOG.info(String.format("Test Case - %s Started", testcaseName));
	}

	
	@Override
	public void InitTestCase(String testcaseName, String testCaseDescription) {		
		this.objExtentTest = this.objExtentReport.createTest(testcaseName, testCaseDescription);
		LOG.info(String.format("Test Case - %s With Description - %s Started", testcaseName, testCaseDescription));
	}
	
	@Override
	public void LogSuccess(String stepName) {
		
		this.objExtentTest.log(Status.PASS, stepName);
		LOG.info(String.format("Step - %s Passed", stepName));
	}

	@Override
	public void LogSuccess(String stepName, String stepDescription) {
		
		this.objExtentTest.log(Status.PASS, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		LOG.info(String.format("StepName - %s, StepDescription - %s Passed", stepName, stepDescription));
	}
	

	@Override
	public void LogSuccess(String stepName, String stepDescription, String screenShotPath) {
		this.objExtentTest.log(Status.PASS, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		try {			
			this.takeScreenShot(screenShotPath);
			this.objExtentTest.addScreenCaptureFromPath(screenShotPath, String.format("StepName - %s, Step Description", stepName, stepDescription));
			LOG.info(String.format("StepName - %s, StepDescription - %s Passed, ScreenShot - %s", stepName, stepDescription, screenShotPath));
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}

	
	@Override
	public void LogFailure(String stepName) {
		this.objExtentTest.log(Status.FAIL, stepName);
		LOG.error(String.format("Step - %s Failed", stepName));
		
	}

	@Override
	public void LogFailure(String stepName, String stepDescription) {
		this.objExtentTest.log(Status.FAIL, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		LOG.error(String.format("StepName - %s, StepDescription - %s Failed", stepName, stepDescription));
		
	}
	
	@Override
	public void LogFailure(String stepName, String stepDescription, String screenShotPath) {
		this.objExtentTest.log(Status.FAIL, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		try {			
			this.takeScreenShot(screenShotPath);
			this.objExtentTest.addScreenCaptureFromPath(screenShotPath, String.format("StepName - %s, Step Description", stepName, stepDescription));
			LOG.error(String.format("StepName - %s, StepDescription - %s Failed, ScreenShot - %s", stepName, stepDescription, screenShotPath));
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}



	@Override
	public void LogInfo(String message) {
		this.objExtentTest.log(Status.INFO, message);
		LOG.info(message);
		
	}

	@Override
	public void LogInfo(String message, String screenShotPath) {
		this.objExtentTest.log(Status.INFO, message);
		try {			
			this.takeScreenShot(screenShotPath);
			this.objExtentTest.addScreenCaptureFromPath(screenShotPath, message);
			LOG.info(message);
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}


	@Override
	public void LogWarning(String stepName) {
		this.objExtentTest.log(Status.WARNING, stepName);
		LOG.warn(stepName);
		
	}

	@Override
	public void LogWarning(String stepName, String stepDescription) {
		this.objExtentTest.log(Status.WARNING, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		LOG.warn(String.format("StepName - %s, StepDescription - %s Warning", stepName, stepDescription));
		
	}
	
	@Override
	public void LogWarning(String stepName, String stepDescription, String screenShotPath) {
		this.objExtentTest.log(Status.WARNING, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		try {			
			this.takeScreenShot(screenShotPath);
			this.objExtentTest.addScreenCaptureFromPath(screenShotPath, String.format("StepName - %s, Step Description", stepName, stepDescription));
			LOG.warn(String.format("StepName - %s, StepDescription - %s Failed, ScreenShot - %s", stepName, stepDescription, screenShotPath));
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}
	


	@Override
	public void LogException(Exception ex) {
		this.objExtentTest.log(Status.ERROR, ex);
		LOG.error(ex.getMessage(), ex);
		
	}

	@Override
	public void LogException(Exception ex, String screenShotPath) {
		this.objExtentTest.log(Status.ERROR, ex);
		try {			
			this.takeScreenShot(screenShotPath);
			this.objExtentTest.addScreenCaptureFromPath(screenShotPath, ex.getMessage());
			LOG.error(ex.getMessage(), ex);
		} catch (IOException | AWTException e) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", e.getMessage(), e.getStackTrace()));
		}
		
	}
	

	@Override
	public void LogFatal(Exception ex) {
		this.objExtentTest.log(Status.FATAL, ex);
		LOG.fatal(ex.getMessage(), ex);
		
	}
	

	@Override
	public void LogFatal(Exception ex, String screenShotPath) {
		this.objExtentTest.log(Status.FATAL, ex);
		try {			
			this.takeScreenShot(screenShotPath);
			this.objExtentTest.addScreenCaptureFromPath(screenShotPath, ex.getMessage());
			LOG.fatal(ex.getMessage(), ex);
		} catch (IOException | AWTException e) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", e.getMessage(), e.getStackTrace()));
		}
		
	}

	@Override
	public void UpdateTestCaseStatus() {
		this.objExtentReport.flush();
		
	}

	@Override
	public void Close() {
		this.objExtentReport.flush();
		
	}

	@Override
	public void ManipulateTestReport(ITestReportManipulator objTestReportManipulator) {
		// TODO Auto-generated method stub
		
	}


	
	private void takeScreenShot(String screenShotPath) throws IOException, AWTException
	{
		Robot objRobot = new Robot();		
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = objRobot.createScreenCapture(screenRect);            
        String format = screenShotPath.substring(0, screenShotPath.indexOf("."));
       	ImageIO.write(screenFullImage, format, new File(screenShotPath));
		
	}

}
