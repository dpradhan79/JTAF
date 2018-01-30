package com.testreport;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.FileUtil;
import com.google.common.io.Resources;

import templates.TestTemplate;

/**
 * 
 * @author E001518  - Debasish Pradhan (Architect)
 *
 */
public class ExtentReporter implements IReporter {
	
	private static final Logger LOG = Logger.getLogger(ExtentReporter.class);
	private boolean boolAppendExisting = false;
	private boolean isCignitiLogoRequired = false;
	private ExtentReports objExtentReport = null;	
	private static ThreadLocal<ExtentTest> threadLocalExtentTest = new ThreadLocal<ExtentTest>();   
	private ExtentTestVisibilityMode extentTestVisibilityMode;
    
	public enum ExtentTestVisibilityMode
	{
		TestNGTestTagAsTestsAtLeft,
		TestNGTestMethodsAsTestAtLeft
	}
	
    protected ExtentReporter(String filePath, boolean boolAppendExisting, boolean isCignitiLogoRequired, ExtentTestVisibilityMode extentTestVisibilityMode)
	{
		this.boolAppendExisting = boolAppendExisting;
		this.isCignitiLogoRequired = isCignitiLogoRequired;	
		this.extentTestVisibilityMode = extentTestVisibilityMode;
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
		htmlReporter.setAppendExisting(boolAppendExisting);
		this.objExtentReport = new ExtentReports();
		this.objExtentReport.attachReporter(htmlReporter);
			
		
	}
    
    protected ExtentReporter(String filePath, String extentConfigFile, boolean boolAppendExisting, boolean isCignitiLogoRequired, ExtentTestVisibilityMode extentTestVisibilityMode) throws URISyntaxException
	{
		this.boolAppendExisting = boolAppendExisting;
		this.isCignitiLogoRequired = isCignitiLogoRequired;	
		this.extentTestVisibilityMode = extentTestVisibilityMode;
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
		if(extentConfigFile != null)	
		{
			String extentConfigFilePath = Paths.get(Resources.getResource(extentConfigFile).toURI()).toFile().getAbsolutePath();
			htmlReporter.loadXMLConfig(extentConfigFilePath);
		}
		
		htmlReporter.setAppendExisting(boolAppendExisting);
		this.objExtentReport = new ExtentReports();
		this.objExtentReport.attachReporter(htmlReporter);
		
		
	}
	
    public ExtentTestVisibilityMode getExtentTestVisibilityMode()
    {
    	return this.extentTestVisibilityMode;
    }
	@Override
	public void initTestCase(String testcaseName) {
		
		ExtentTest objExtentTest = null;		
		if(this.extentTestVisibilityMode == ExtentTestVisibilityMode.TestNGTestMethodsAsTestAtLeft)
		{
			objExtentTest =  this.objExtentReport.createTest(testcaseName);
			ExtentReporter.threadLocalExtentTest.set(objExtentTest);
			LOG.info(String.format("ExtentTest Created - %s Created, With Name - %s", objExtentTest, testcaseName));
		}
		
		else if(this.extentTestVisibilityMode == ExtentTestVisibilityMode.TestNGTestTagAsTestsAtLeft)
		{
			//The below code is for creating nodes at right panel for test cases in left panel. Does not look good.
				
			objExtentTest = ExtentReporter.threadLocalExtentTest.get().createNode(testcaseName);
			ExtentReporter.threadLocalExtentTest.set(objExtentTest);
			LOG.info(String.format("Node Created - %s For Test Case - %s Started, New ExtentTest - %s", testcaseName, ExtentReporter.threadLocalExtentTest.get(), objExtentTest));		
			
		}
		
	}
	
	@Override
	public void createTestNgXMLTestTag(String testcaseName)
	{
		ExtentTest objExtentTest = null;
		objExtentTest =  this.objExtentReport.createTest(testcaseName);
		ExtentReporter.threadLocalExtentTest.set(objExtentTest);
		LOG.info(String.format("ExtentTest Created - %s Created, With Name - %s", objExtentTest, testcaseName));
		
	}
	@Override
	public void logSuccess(String stepName) {
		
		ExtentReporter.threadLocalExtentTest.get().log(Status.PASS, stepName);
		LOG.info(String.format("Step - %s Passed", stepName));
	}

	@Override
	public void logSuccess(String stepName, String stepDescription) {
		
		ExtentReporter.threadLocalExtentTest.get().log(Status.PASS, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		LOG.info(String.format("StepName - %s, StepDescription - %s Passed", stepName, stepDescription));
	}
	

	@Override
	public void logSuccess(String stepName, String stepDescription, String screenShotPath) {
		
		try {	
			this.takeScreenShot(screenShotPath);
			ExtentReporter.threadLocalExtentTest.get().log(Status.PASS, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription), MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());			
			LOG.info(String.format("StepName - %s, StepDescription - %s Passed, ScreenShot - %s", stepName, stepDescription, screenShotPath));
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}

	
	@Override
	public void logFailure(String stepName) {
		ExtentReporter.threadLocalExtentTest.get().log(Status.FAIL, stepName);
		LOG.error(String.format("Step - %s Failed", stepName));
		
	}

	@Override
	public void logFailure(String stepName, String stepDescription) {
		ExtentReporter.threadLocalExtentTest.get().log(Status.FAIL, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		LOG.error(String.format("StepName - %s, StepDescription - %s Failed", stepName, stepDescription));
		
	}
	
	@Override
	public void logFailure(String stepName, String stepDescription, String screenShotPath) {
		
		try {	
			this.takeScreenShot(screenShotPath);
			ExtentReporter.threadLocalExtentTest.get().log(Status.FAIL, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription), MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());			
			LOG.error(String.format("StepName - %s, StepDescription - %s Failed, ScreenShot - %s", stepName, stepDescription, screenShotPath));
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}



	@Override
	public void logInfo(String message) {
		ExtentReporter.threadLocalExtentTest.get().log(Status.INFO, message);
		LOG.info(message);
		
	}

	@Override
	public void logInfo(String message, String screenShotPath) {
		
		try {			
			this.takeScreenShot(screenShotPath);
			ExtentReporter.threadLocalExtentTest.get().log(Status.INFO, message, MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			LOG.info(message);
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}


	@Override
	public void logWarning(String stepName) {
		ExtentReporter.threadLocalExtentTest.get().log(Status.WARNING, stepName);
		LOG.warn(stepName);
		
	}

	@Override
	public void logWarning(String stepName, String stepDescription) {
		ExtentReporter.threadLocalExtentTest.get().log(Status.WARNING, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription));
		LOG.warn(String.format("StepName - %s, StepDescription - %s Warning", stepName, stepDescription));
		
	}
	
	@Override
	public void logWarning(String stepName, String stepDescription, String screenShotPath) {
		
		try {			
			this.takeScreenShot(screenShotPath);
			ExtentReporter.threadLocalExtentTest.get().log(Status.WARNING, String.format("StepName - %s, StepDescription - %s", stepName, stepDescription, MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build()));
			LOG.warn(String.format("StepName - %s, StepDescription - %s Failed, ScreenShot - %s", stepName, stepDescription, screenShotPath));
		} catch (IOException | AWTException ex) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}
		
	}
	


	@Override
	public void logException(Throwable ex) {
		ExtentReporter.threadLocalExtentTest.get().log(Status.ERROR, ex);
		LOG.error(ex.getMessage(), ex);
		
	}

	@Override
	public void logException(Throwable ex, String screenShotPath) {
		
		try {			
			this.takeScreenShot(screenShotPath);
			ExtentReporter.threadLocalExtentTest.get().log(Status.ERROR, ex, MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			LOG.error(ex.getMessage(), ex);
		} catch (IOException | AWTException e) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", e.getMessage(), e.getStackTrace()));
		}
		
	}
	

	@Override
	public void logFatal(Throwable ex) {
		ExtentReporter.threadLocalExtentTest.get().log(Status.FATAL, ex);
		LOG.fatal(ex.getMessage(), ex);
		
	}
	

	@Override
	public void logFatal(Throwable ex, String screenShotPath) {
		
		try {			
			this.takeScreenShot(screenShotPath);
			ExtentReporter.threadLocalExtentTest.get().log(Status.FATAL, ex, MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			LOG.fatal(ex.getMessage(), ex);
		} catch (IOException | AWTException e) {			
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", e.getMessage(), e.getStackTrace()));
		}
		
	}

	@Override
	public void updateTestCaseStatus() {
		this.objExtentReport.flush();
		
	}

	@Override
	public void close() {
		this.objExtentReport.flush();
		
	}

	@Override
	public void manipulateTestReport(ITestReportManipulator objTestReportManipulator) {
		// TODO Auto-generated method stub
		
	}


	
	private void takeScreenShot(String screenShotPath) throws IOException, AWTException
	{
		/*Robot objRobot = new Robot();		
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = objRobot.createScreenCapture(screenRect);            
        String format = screenShotPath.substring(screenShotPath.indexOf(".") + 1);       
       	ImageIO.write(screenFullImage, format, new File(screenShotPath));	*/	
		
		File screenShotFile = ((TakesScreenshot)TestTemplate.threadLocalWebDriver.get()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenShotFile, new File(screenShotPath));
	}
	
	
}
