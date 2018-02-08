package aut.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.config.IConstants;
import com.testreport.IReporter;

import aut.pages.templates.PageTemplate;

public class SoftCoGlobalSearchPage extends PageTemplate {
	SoftCoInvoicePage invoicePageObj = new SoftCoInvoicePage(this.driver, this.testReport);
	SoftCoHomePage homePageObj = new SoftCoHomePage(this.driver, this.testReport);

	// Locators
	private String recordsDisplayed = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME,
			"rowsInResultsTable");
	private String searchButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME,
			"SearchForDoc_SearchButton");
	private String searchCriteriaButton = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME,
			"searchCriteriaButton");
	private String invoice = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "Invoices");

	private static final Logger LOG = Logger.getLogger(SoftCoGlobalSearchPage.class);

	public SoftCoGlobalSearchPage(WebDriver webDriver, IReporter testReport) {
		super(webDriver, testReport);

	}

	public boolean validateSearchForDocument(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			// Navigate to search for a document page
			homePageObj.navigateToSearchForADocument();

			// wait until search button is clickable and click on search button
			this.waitUntilElementIsClickable(By.xpath(searchButton));
			this.Click(By.xpath(searchButton));

			// wait until search criteria button enables so that search results displayed
			// completely
			this.waitUntilElementIsClickable(By.xpath(searchCriteriaButton));

			// click on first available record in search results
			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

	public boolean validateMissingClient(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			homePageObj.navigateToMissingClient();

			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			isSuccess = true;
		} catch (Exception ex) {
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

	private boolean clickOnFirstAvailableRecordInSearchResults() {
		boolean isSuccess = false;
		try {
			// Get all the records displayed in results table
			List<WebElement> results = this.driver.findElements(By.xpath(recordsDisplayed));
			LOG.info("Store all elements to obj " + results);
			// Check results count
			int resultsCount = results.size();
			LOG.info("Total elements displayed are " + resultsCount);

			// validate results count and click on first record if records displayed
			if (resultsCount == 0)
				LOG.info("No records displayed in search results");
			else
				// click on the first record displayed
				results.get(0).click();
			LOG.info("clicked on first available record");
		} catch (Exception ex) {
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

	public boolean validateSearchForAnInvoice(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			// Navigate to search for a document page
			homePageObj.navigateToSearchForInvoice();

			// wait until search button is clickable and click on search button
			this.waitUntilElementIsClickable(By.xpath(searchButton));
			this.Click(By.xpath(searchButton));

			// wait until search criteria button enables so that search results displayed
			// completely
			this.waitUntilElementIsClickable(By.xpath(searchCriteriaButton));

			// click on first available record in search results
			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

	public boolean validateSearchForATimeSheet(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			// Navigate to search for a document page
			homePageObj.navigateToSearchForATimeSheet();

			// wait until search button is clickable and click on search button
			this.waitUntilElementIsClickable(By.xpath(searchButton));
			this.Click(By.xpath(searchButton));

			// wait until search criteria button enables so that search results displayed
			// completely
			this.waitUntilElementIsClickable(By.xpath(searchCriteriaButton));

			// click on first available record in search results
			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

	public boolean validateSearchCarRental(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			// Navigate to search for a document page
			homePageObj.navigateToSearchCarRental();

			// wait until search button is clickable and click on search button
			this.waitUntilElementIsClickable(By.xpath(searchButton));
			this.Click(By.xpath(searchButton));

			// wait until search criteria button enables so that search results displayed
			// completely
			this.waitUntilElementIsClickable(By.xpath(searchCriteriaButton));

			// click on first available record in search results
			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

	public boolean validateSearchHotel(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			// Navigate to search for a document page
			homePageObj.navigateToSearchHotel();

			// wait until search button is clickable and click on search button
			this.waitUntilElementIsClickable(By.xpath(searchButton));
			this.Click(By.xpath(searchButton));

			// wait until search criteria button enables so that search results displayed
			// completely
			this.waitUntilElementIsClickable(By.xpath(searchCriteriaButton));

			// click on first available record in search results
			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

	public boolean validateSearchAirfare(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			// Navigate to search for a document page
			homePageObj.navigateToSearchAirfare();

			// wait until search button is clickable and click on search button
			this.waitUntilElementIsClickable(By.xpath(searchButton));
			this.Click(By.xpath(searchButton));

			// wait until search criteria button enables so that search results displayed
			// completely
			this.waitUntilElementIsClickable(By.xpath(searchCriteriaButton));

			// click on first available record in search results
			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

	public boolean validateSearchRequestIt(String isEditable) throws Exception {
		boolean isSuccess = false;
		try {
			// Navigate to search for a document page
			homePageObj.navigateToSearchRequestIt();

			// wait until search button is clickable and click on search button
			this.waitUntilElementIsClickable(By.xpath(searchButton));
			this.Click(By.xpath(searchButton));

			// wait until search criteria button enables so that search results displayed
			// completely
			this.waitUntilElementIsClickable(By.xpath(searchCriteriaButton));

			// click on first available record in search results
			this.clickOnFirstAvailableRecordInSearchResults();

			invoicePageObj.validateInvoicePage(isEditable);
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

	public boolean clickOnInvoices() throws Exception {
		boolean isSuccess = false;
		try {
			this.Click(By.xpath(invoice));
			invoicePageObj.acceptUnSavedChangesPopUp();
			isSuccess = true;
		} catch (Exception ex) {
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

}
