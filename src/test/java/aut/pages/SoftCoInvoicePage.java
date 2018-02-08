package aut.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.config.IConstants;
import com.testreport.IReporter;

import aut.pages.templates.PageTemplate;

public class SoftCoInvoicePage extends PageTemplate {
	SoftCoHomePage homePageObj = new SoftCoHomePage(this.driver, this.testReport);
	private static final Logger LOG = Logger.getLogger(SoftCoInvoicePage.class);
	public SoftCoInvoicePage(WebDriver webDriver, IReporter testReport) {
		super(webDriver, testReport);
		
	}	
	
	private boolean addButtonVisibility(String isEditable) throws Exception
	{
		boolean isSuccess = false;
		try
		{
			String addButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "AddButton");
			String ElipsesMenu = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ElipsesMenu");
			this.waitUntilElementIsClickable(By.xpath(ElipsesMenu));
			boolean addButtonStatus = this.isElementPresent(By.xpath(addButton));
			if(isEditable.equalsIgnoreCase("yes"))
			{
				if(addButtonStatus)
				{
					LOG.info("Add Button Displayed");
					this.testReport.logSuccess("Expected - Add Button Should Be Displayed, Actual - Add Button Displayed");
				}
				else
				{
					LOG.error("Expected - Should Be Displayed, Actual - Add Button Not Displayed");
					this.testReport.logFailure("Expected - Should Be Displayed, Actual - Add Button Not Displayed");
					throw new Exception("Expected - Should Be Displayed, Actual - Add Button Not Displayed");
				}
			}
			else if(isEditable.equalsIgnoreCase("No"))
			{
				if(addButtonStatus)
				{
					LOG.error("Expected - Should Not Be Displayed, Actual - Add Button Displayed");
					this.testReport.logFailure("Expected - Should Not Be Displayed, Actual - Add Button Displayed");
					throw new Exception("Expected - Should Not Be Displayed, Actual - Add Button Displayed");
				}
				else
				{
					LOG.info("Expected - Should Not Be Displayed, Actual - Add Button Not Displayed");
					this.testReport.logSuccess("Expected - Should Not Be Displayed, Actual - Add Button Not Displayed");
				}
			}
			isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure(
					String.format("Class Name - %s , Method Name - %s, Line Number - %s, Exception Encountered - %s",
					ex.getStackTrace()[2].getClassName(), ex.getStackTrace()[2].getMethodName(),
					ex.getStackTrace()[2].getLineNumber(), ex.getMessage()));
			throw ex;
		}
		return isSuccess;
	}
	
	private boolean emailTemplateValidation() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			String elipses = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ElipsesMenu");
			String emailButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "EmailButton");
			String emailPopUpCloseIcon = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "EmailPopupCloseIcon");
			
			this.waitUntilElementIsClickable(By.xpath(elipses));
			this.Click(By.xpath(elipses));
			this.waitUntilElementIsClickable(By.xpath(emailButton));
			this.Click(By.xpath(emailButton));
			this.implicitwait(2);
			this.waitUntilElementIsClickable(By.xpath(emailPopUpCloseIcon));
			this.Click(By.xpath(emailPopUpCloseIcon));
			this.implicitwait(2);
		}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure(
					String.format("Class Name - %s , Method Name - %s, Line Number - %s, Exception Encountered - %s",
					ex.getStackTrace()[2].getClassName(), ex.getStackTrace()[2].getMethodName(),
					ex.getStackTrace()[2].getLineNumber(), ex.getMessage()));
			throw ex;
		}
		return isSuccess;
	}
	
	private boolean validateAllFieldsInHeader(String isEditable) throws Exception
	{
		boolean isSuccess = false;
		try
		{
			String headerInputFields = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "Header_InputFields");
			String headerInputLables = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "Header_inputFieldNames");
			
		//Store all elements into input fields
    	List<WebElement> inputFields = driver.findElements(By.xpath(headerInputFields));
    	List<WebElement> inputFieldLables = driver.findElements(By.xpath(headerInputLables));
    	
    	//Print total elements
    	LOG.info("Total elements displayed are " + inputFields.size());
    	
    	//iterate and validate all are disabled or not
    	for(int i=1;i<inputFields.size();i++)
    	{
    		String attribute = inputFields.get(i).getAttribute("readonly");
    		LOG.info("Read Only Attribute value of " + inputFieldLables.get(i).getText() + " is " + attribute);
    		if(isEditable.equalsIgnoreCase("yes"))
    		{
    			try
        		{
        			Assert.assertNotNull(attribute);
        			LOG.info("Input field " + inputFieldLables.get(i).getText() + " is diabled");
        			this.testReport.logInfo("Input field " + inputFieldLables.get(i).getText() + " is diabled");
        			//throw new Exception("Input field " + inputFieldLables.get(i).getText() + " is disabled");
        		}
        		catch(AssertionError a)
        		{
        			LOG.info("Input field " + inputFieldLables.get(i).getText() + " is enabled");
        			this.testReport.logInfo("Input field " + inputFieldLables.get(i).getText() + " is enabled");
        		}
    		}
    		else if(isEditable.equalsIgnoreCase("No"))
    		{
	    		try
	    		{
	    			Assert.assertNotNull(attribute);
	    			LOG.info("Input field " + inputFieldLables.get(i).getText() + " is diabled");
	    			this.testReport.logInfo("Input field " + inputFieldLables.get(i).getText() + " is diabled");
	    		}
	    		catch(AssertionError a)
	    		{
	    			LOG.info("Input field " + inputFieldLables.get(i).getText() + " is enabled");
	    			this.testReport.logInfo("Input field " + inputFieldLables.get(i).getText() + " is enabled");
	    			//throw new Exception("Input field " + inputFieldLables.get(i).getText() + " is enabled");
	    		}
    		}
    		isSuccess = true;
    	}
	}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure(
					String.format("Class Name - %s , Method Name - %s, Line Number - %s, Exception Encountered - %s",
					ex.getStackTrace()[2].getClassName(), ex.getStackTrace()[2].getMethodName(),
					ex.getStackTrace()[2].getLineNumber(), ex.getMessage()));
			throw ex;
		}
		return isSuccess;
	}
	
	private boolean isPossibleToAddComment(String isEditable) throws Exception
	{
		boolean isSuccess = false;
		try
		{
			String commentIcon = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "CommentIcon");
			String addCommentButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "AddCommentButton");
			String addCommentPopUpCloseIcon = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "CommentpopupCloseIcon");
			String AddCommentButtonParentTag = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "AddCommentButtonParentTag");
			this.waitUntilElementIsClickable(By.xpath(commentIcon));
			this.Click(By.xpath(commentIcon));
			if(isEditable.equalsIgnoreCase("yes"))
			{
				this.waitUntilElementIsClickable(By.xpath(addCommentButton));
				this.waitUntilElementIsClickable(By.xpath(addCommentPopUpCloseIcon));
				this.Click(By.xpath(addCommentPopUpCloseIcon));
			}
			else if(isEditable.equalsIgnoreCase("No"))
			{
				try
	    		{
	    			Assert.assertNotNull(this.getAttribute(By.xpath(AddCommentButtonParentTag), "aria-disabled"));
	    			LOG.info("Adding comment is disabled for current logged in user");
	    			this.testReport.logInfo("Adding comment is disabled for current logged in user");
	    			this.Click(By.xpath(addCommentPopUpCloseIcon));
	    		}
	    		catch(AssertionError a)
	    		{
	    			LOG.error("Adding comment is enabled");
	    			this.testReport.logFailure("Adding comment is enabled");
	    			throw new Exception("Adding comment is enabled");
	    		}
			}
			isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure(
					String.format("Class Name - %s , Method Name - %s, Line Number - %s, Exception Encountered - %s",
					ex.getStackTrace()[2].getClassName(), ex.getStackTrace()[2].getMethodName(),
					ex.getStackTrace()[2].getLineNumber(), ex.getMessage()));
			throw ex;
		}
		return isSuccess;
	}
	
	private List<String> validateHeadersAvailableInPostingRow(String isEditable) throws Exception
	{
		//print all the options available in Row
		List<String> headerDetails = new ArrayList<String>();
		String postingRowHeaders = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "PostingRowHeader");
		String RecordsInPostingRow = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "RecordsInPostingRow");
		
    	List<WebElement> headersAvailable = this.driver.findElements(By.xpath(postingRowHeaders));
    	LOG.info("Total columns available are " + headersAvailable.size());
    	this.testReport.logInfo("Total columns available are " + headersAvailable.size());
    	int recordsPresent = this.driver.findElements(By.xpath(RecordsInPostingRow)).size();
    	LOG.info("Total records present in posting Row are " + recordsPresent);
    	this.testReport.logInfo("Total records present in posting Row are " + recordsPresent);
    	if(recordsPresent>0)
    	{
    	for(int i=1;i<headersAvailable.size();i++)
    	{
    		WebElement horizontal_scroll = this.driver.findElement(By.xpath("(//div[contains(@class,'table-header-wrap')])[2]//td["+(i+1)+"]")); 
        	((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", horizontal_scroll);
        	
        	LOG.info("Column " + i + " title is " + headersAvailable.get(i).getText());
        	this.testReport.logInfo("Column " + i + " title is " + headersAvailable.get(i).getText());
        	
        	WebElement rowInfo = driver.findElement(By.xpath("//div[contains(@class,'scrollable v-table')]//tr[@class='v-table-row']/td["+(i+1)+"]//input")); 
        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", rowInfo);
    		
        	String attribute = driver.findElement(By.xpath("//div[contains(@class,'scrollable v-table')]//tr[@class='v-table-row']/td["+(i+1)+"]//input")).getAttribute("readonly");
    		LOG.info("Read Only Attribute value of " + headersAvailable.get(i).getText() + " " + attribute);
    		this.testReport.logInfo("Read Only Attribute value of " + headersAvailable.get(i).getText() + " " + attribute);
    		
    		if(isEditable.equalsIgnoreCase("Yes"))
    		{
	    		try
	    		{
	    			Assert.assertNotNull(attribute);
	    			LOG.info("Input field " + headersAvailable.get(i).getText() + " is disabled");
	    			this.testReport.logInfo("Input field " + headersAvailable.get(i).getText() + " is disabled");
	    			//throw new Exception("Input field " + headersAvailable.get(i).getText() + " is disabled");
	    		}
	    		catch(AssertionError a)
	    		{
	    			LOG.info("Input field " + headersAvailable.get(i).getText() + " is enabled");
	    			this.testReport.logInfo("Input field " + headersAvailable.get(i).getText() + " is enabled");
	    		}
    		}
    		else if(isEditable.equalsIgnoreCase("No"))
    		{
    			try
        		{
        			Assert.assertNotNull(attribute);
        			LOG.info("Input field " + headersAvailable.get(i).getText() + " is disabled");
        			this.testReport.logInfo("Input field " + headersAvailable.get(i).getText() + " is disabled");
        		}
        		catch(AssertionError a)
        		{
        			LOG.info("Input field " + headersAvailable.get(i).getText() + " is enabled");
        			this.testReport.logInfo("Input field " + headersAvailable.get(i).getText() + " is enabled");
        			//throw new Exception("Input field " + headersAvailable.get(i).getText() + " is enabled");
        		}
    		}
    	}
    	}
    	else
    	{
    		for(int i=1;i<headersAvailable.size();i++)
    		{
        		WebElement horizontal_scroll = this.driver.findElement(By.xpath("(//div[contains(@class,'table-header-wrap')])[2]//td["+(i+1)+"]")); 
            	((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", horizontal_scroll);
            	
            	LOG.info("Column " + i + " title is " + headersAvailable.get(i).getText());
            	this.testReport.logInfo("Column " + i + " title is " + headersAvailable.get(i).getText());
    		}
    		LOG.info("No Records Present in Posting Row");
    		this.testReport.logInfo("No Records Present in Posting Row");
    	}
		return headerDetails;
	}
	
	protected void validateInvoicePage(String isEditable) throws Exception
	{
		String splitter = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "splitter");
		String divisionInputFiels = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "document_default_divisionInputField");
		String RecordsInPostingRow = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "RecordsInPostingRow");
    	
		this.addButtonVisibility(isEditable);
		this.emailTemplateValidation();
		this.validateAllFieldsInHeader(isEditable);
		this.isPossibleToAddComment(isEditable);
		
		this.dragAndDrop(By.xpath(splitter), By.xpath(divisionInputFiels));
		
		this.validateHeadersAvailableInPostingRow(isEditable);
    	int recordsPresent = this.driver.findElements(By.xpath(RecordsInPostingRow)).size();
    	if(recordsPresent>0)
    	{
		String firstRowCheckBox = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "firstRowCheckBox");
		this.checkCheckBox(By.xpath(firstRowCheckBox));
		this.validateCRUDOptionsPresentForASelectedRow();
    	}
    	else
    	{
    		LOG.info("No Records Present in Posting Row");
    		this.testReport.logInfo("No Records Present in Posting Row");
    	}
	}
	
	private void validateCRUDOptionsPresentForASelectedRow() throws Exception
	{
		String CaseRowOptionsDD = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "CaseRowOptionsDD");
		String optionsAvailableInDD = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "optionsAvailableInDD");
		this.Click(By.xpath(CaseRowOptionsDD));
		List<WebElement> options = this.driver.findElements(By.xpath(optionsAvailableInDD));
		for(int i=0;i<options.size();i++)
		{
			LOG.info("Option " + i + " is " + options.get(i).getText());
			this.testReport.logInfo("Option " + i + " is " + options.get(i).getText());
		}
	}
	
	protected void acceptUnSavedChangesPopUp() throws Exception
	{
		String OkButtonUnSavedChangesPopUp = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "OkButtonUnSavedChangesPopUp");
		
		boolean elementStatus = this.isElementPresent(By.xpath(OkButtonUnSavedChangesPopUp));
		if(elementStatus)
		{
			this.Click(By.xpath(OkButtonUnSavedChangesPopUp));
			this.implicitwait(3);
		}
		else
		{
			LOG.info("All changes Saved");
			this.testReport.logInfo("All changes Saved");
		}
	}
}
