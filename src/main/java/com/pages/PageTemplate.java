package com.pages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.config.IConstants;
import com.utilities.ReusableLibs;

public abstract class PageTemplate {
	private static final Logger LOG = Logger.getLogger(PageTemplate.class);
	protected WebDriver driver = null;
	protected ReusableLibs reUsableLib = null;
	protected int implicitWaitInSecs = 0;
	protected int pageLoadTimeOutInSecs = 0;
	protected PageTemplate(WebDriver webDriver)
	{
		this.driver = webDriver;
		this.reUsableLib = new ReusableLibs();
		this.implicitWaitInSecs = Integer.parseInt(reUsableLib.getConfigProperty("ImplicitWaitInSecs"));
		this.pageLoadTimeOutInSecs = Integer.parseInt(reUsableLib.getConfigProperty("PageLoadTimeOutInSecs"));
	}	
	
	protected void SendKeys(By byLocator, String text) throws Exception
	{
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			this.driver.findElement(byLocator).sendKeys(text);
			LOG.info(String.format("SendKeys Successful - (By - %s, text - %s)", byLocator, text));
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			throw ex;
		}
		
	}
	
	protected void Click(By byLocator) throws Exception
	{
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			this.driver.findElement(byLocator).click();;
			LOG.info(String.format("Click Successful - (By - %s)", byLocator));
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			throw ex;
		}
		
	}

	protected String getAttribute(By byLocator, String attribute) throws Exception
	{
		String attributeValue = null;
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			attributeValue = this.driver.findElement(byLocator).getAttribute(attribute);
			LOG.info(String.format("Click Successful - (By - %s)", byLocator));
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			throw ex;
		}
		return attributeValue;
		
	}
	
	public void logout() throws Exception
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, this.implicitWaitInSecs);
			String byLoginButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "LoginButton");
			//Click on logout
			String logoutButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "LogoutButton");
			
			//Wait For Logout Button To Be Clickable, as click throwing staleelementexception.
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(logoutButton)));
			
			this.driver.findElement(By.xpath(logoutButton)).click();
			LOG.info(String.format("Click Successful - (By - %s)", logoutButton));
			
			String OkButtonUnSavedChangesPopUp = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "OkButtonUnSavedChangesPopUp");
			
			boolean elementStatus = this.isElementDisplayed(By.xpath(OkButtonUnSavedChangesPopUp));
			if(elementStatus)
			{
				this.Click(By.xpath(OkButtonUnSavedChangesPopUp));
			}
			else
			{
				LOG.info("All changes Saved");
			}
			
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(byLoginButton)));
			LOG.info("Application logged out successfully");
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered While Logging Out - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			throw ex;
		}
	}
	

	protected boolean waitUntilElementIsClickable(By byLocator)
	{
		boolean isSuccess = false;
		try
		{		
			WebDriverWait wait = new WebDriverWait(this.driver,this.implicitWaitInSecs);
			wait.until(ExpectedConditions.elementToBeClickable(byLocator));
			LOG.info(String.format("Element clickable - (By - %s)", byLocator));
			isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			throw ex;
		}
		return isSuccess;
	}
	
	protected boolean waitUntilElementIsVisible(By byLocator)
	{
		boolean isSuccess = false;
		try
		{		
			WebDriverWait wait = new WebDriverWait(this.driver,this.implicitWaitInSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			LOG.info(String.format("Element clickable - (By - %s)", byLocator));
			isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			throw ex;
		}
		return isSuccess;
	}
	
	protected boolean isElementDisplayed(By byLocator)
	{
		boolean isSuccess = false;
		try
		{
			//validate element is displayed or not
			implicitwait(2);
			Assert.assertEquals(driver.findElements(byLocator).size() > 0, true);
			LOG.info(String.format("Element displayed - (By - %s)", byLocator));
			isSuccess = true;
		}
		catch(Exception | AssertionError ex)
		{
			LOG.info(String.format("Element not displayed - (By - %s)", byLocator));
			isSuccess = false;
		}
		
		return isSuccess;
	}
	
	protected void implicitwait(int sec)
	{
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void dragAndDrop(By source , By target)
	{
		implicitwait(3);
		//Read and store drag location
    	WebElement drag = this.driver.findElement(source);
    	
    	//read and store drop location
    	WebElement drop = this.driver.findElement(target);
    	
    	//drag and drop
    	(new Actions(driver)).dragAndDrop(drag, drop).perform();
    	LOG.info(String.format("Drag element - (By - %s)", source));
    	LOG.info(String.format("drop element at - (By - %s)", target));
    	implicitwait(2);
	}
	
	protected String takeScreemShot()
	{
		String screenshotLocation = null;
		String screenShotAbsolutePath = null;
		
		
		
		return screenShotAbsolutePath;
	}
	
	protected boolean checkCheckBox(By byLocator)
	{
		boolean isSuccess = false;
		try
		{
			//read check box web element and store to check box web element obj
	    	WebElement checkBox = this.driver.findElement(byLocator);
	    	
	    	//Wait until element is clickable
	    	this.waitUntilElementIsClickable(byLocator);
	    	
	    	//Click on check-box
	    	if(checkBox.isSelected())
	    	{
	    		LOG.info("check-box already checked");
	    	}
	    	else
	    	{
	    	Actions act = new Actions(this.driver);
	    	act.moveToElement(checkBox).click().build().perform();
	    	LOG.info("check-box checked successfully");
	    	}
	    	isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
			throw ex;
		}
		return isSuccess;
	}
}
