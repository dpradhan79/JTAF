package aut.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.config.IConstants;
import com.testreport.IReporter;

import aut.pages.templates.PageTemplate;

public class SoftCoHomePage extends PageTemplate {
	private static final Logger LOG = Logger.getLogger(SoftCoHomePage.class);
	
	String bySearchforATimeSheet = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ARInvoice_searchForATimeSheet");
	String bySearchforAnExpences = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "searchForExpences");
	String bySearchforCarRental = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "searchCarRental");
	String bySearchforRequestIt = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "searchforarequestit");
	String bySearchforHotel = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "searchforaHotel");
	String bySearchforAirFare = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "searchforaAirFare");
	
	public SoftCoHomePage(WebDriver webDriver, IReporter testReport) {
		super(webDriver, testReport);
		
	}
	
	private boolean expandARInvoice() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.implicitwait(3);
			String byARInvoice = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ARInvoiceExpand/CollapseICon");
			String className = this.getAttribute(By.xpath(byARInvoice), "class");
			
			
			
			//Validating whether the ICON is in expand / collapse status 
			boolean status = false;
			if(className.contains("closed"))
			{
				status = false;	
				
			}
			else if(className.contains("open"))
			{
				status = true;
			}
			
			if(status)
			{
				LOG.info(String.format("AR Invoice is already expanded - (By - %s)", byARInvoice));
				this.implicitwait(2);
			}
			else
			{
				this.waitUntilElementIsClickable(By.xpath(byARInvoice));
				this.Click(By.xpath(byARInvoice));
				this.implicitwait(2);
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
	
	private boolean expandARProcessingQueue() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			String byARProcessingQueue = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ARProcessingQueueExpand/CollapseICon");
			String className = this.getAttribute(By.xpath(byARProcessingQueue), "class");
			
			//Validating whether the ICON is in expand / collapse status 
			boolean status = false;
			if(className.contains("closed"))
				status = false;	
			else if(className.contains("open"))
				status = true;
			
			if(status){
				LOG.info(String.format("ARProcessingQueue is already expanded - (By - %s)", byARProcessingQueue));
				this.implicitwait(2);
				}
			else{
				this.waitUntilElementIsClickable(By.xpath(byARProcessingQueue));
				this.Click(By.xpath(byARProcessingQueue));
				this.implicitwait(2);
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
	
	private boolean expandARGlobalSearch() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			String byARGlobalSearch = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ARGlobalSearchExpand/CollapseICon");
			String className = this.getAttribute(By.xpath(byARGlobalSearch), "class");
			
			//Validating whether the ICON is in expand / collapse status 
			boolean status = false;
			if(className.contains("closed"))
				status = false;	
			else if(className.contains("open"))
				status = true;
			
			if(status){
				LOG.info(String.format("byARGlobalSearch is already expanded - (By - %s)", byARGlobalSearch));
				this.implicitwait(2);
				}
			else{
				this.waitUntilElementIsClickable(By.xpath(byARGlobalSearch));
				this.Click(By.xpath(byARGlobalSearch));
				this.implicitwait(2);
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
	
	protected boolean navigateToSearchForADocument() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.navigateToGlobalSearch();
			
			String bySearchforADocument = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ARInvoice_SearchForADocument");
			this.Click(By.xpath(bySearchforADocument));
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
	
	protected boolean navigateToMissingClient() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.expandARInvoice();
			this.expandARProcessingQueue();
			
			String byMissingClient = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "MissingClientLink");
			this.Click(By.xpath(byMissingClient));
			this.implicitwait(3);
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
	
	protected boolean navigateToSearchForInvoice() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.navigateToGlobalSearch();
			
			String bySearchforAnInvoice = this.reUsableLib.getElementLocator(IConstants.LOCATORSFILENAME, "ARInvoice_searchForAnInvoice");
			this.Click(By.xpath(bySearchforAnInvoice));
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
	
	protected boolean navigateToSearchForATimeSheet() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.navigateToGlobalSearch();
			
			this.Click(By.xpath(bySearchforATimeSheet));
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
	
	private boolean navigateToGlobalSearch() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.expandARInvoice();
			this.expandARProcessingQueue();
			this.expandARGlobalSearch();
			this.implicitwait(3);
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
	
	protected boolean navigateToSearchCarRental() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.navigateToGlobalSearch();
			this.expandSearchForAnExpence();
			this.Click(By.xpath(bySearchforCarRental));
			this.implicitwait(3);
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
	
	protected boolean navigateToSearchHotel() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.navigateToGlobalSearch();
			this.expandSearchForAnExpence();
			this.Click(By.xpath(bySearchforHotel));
			this.implicitwait(3);
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
	
	protected boolean navigateToSearchAirfare() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.navigateToGlobalSearch();
			this.expandSearchForAnExpence();
			this.Click(By.xpath(bySearchforAirFare));
			this.implicitwait(3);
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
	
	protected boolean navigateToSearchRequestIt() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.navigateToGlobalSearch();
			this.expandSearchForAnExpence();
			this.Click(By.xpath(bySearchforRequestIt));
			this.implicitwait(3);
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
	
	private boolean expandSearchForAnExpence() throws Exception
	{
		boolean isSuccess = false;
		try
		{
			this.implicitwait(3);
			String className = this.getAttribute(By.xpath(bySearchforAnExpences), "class");
			
			
			
			//Validating whether the ICON is in expand / collapse status 
			boolean status = false;
			if(className.contains("closed"))
			{
				status = false;	
				
			}
			else if(className.contains("open"))
			{
				status = true;
			}
			
			if(status)
			{
				LOG.info(String.format("Search for an Expence- (By - %s)", bySearchforAnExpences));
				this.implicitwait(2);
			}
			else
			{
				this.waitUntilElementIsClickable(By.xpath(bySearchforAnExpences));
				this.Click(By.xpath(bySearchforAnExpences));
				this.implicitwait(2);
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
}
