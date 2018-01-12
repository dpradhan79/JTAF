package com.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.config.IConstants;

public class SoftCoLoginPage extends PageTemplate {
	private static final Logger LOG = Logger.getLogger(SoftCoLoginPage.class);
	public SoftCoLoginPage(WebDriver webDriver) {
		super(webDriver);
		
	}
	
	public boolean login(String url, String userName, String password) throws Exception
	{
		boolean isSuccess = false;
		try
		{
			
			String byUserName = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "UserName");
			String byPassword = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "Password");
			String byLoginLanguageSelection = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "LoginLanguageSelection");
			String byLanguageEnglish = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "LanguageEnglish(eng)");
			String byLoginButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "LoginButton");
			this.driver.get(url);
			this.SendKeys(By.xpath(byUserName), userName);
			this.SendKeys(By.xpath(byPassword), password);
			this.Click(By.xpath(byLoginLanguageSelection));
			this.Click(By.xpath(byLanguageEnglish));
			this.Click(By.xpath(byLoginButton));
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
