package com.testreport;

import org.apache.log4j.Logger;

import com.testreport.ExtentReporter.ExtentTestVisibilityMode;
import com.utilities.ReusableLibs;

/**
 * 
 * @author E001518  - Debasish Pradhan (Architect)
 *
 */

public class ReportFactory {
	
	private static IReporter testReport = null;	
	private static final Logger LOG = Logger.getLogger(ReportFactory.class);
	
	public enum ReportType
	{
		CignitiHtml,
		ExtentHtml
	}
	
	private ReportFactory()
	{
		
	}
	
	public synchronized static IReporter getInstance(ReportType reportType, ExtentTestVisibilityMode extentTestVisibilityMode) throws Exception
	{
		if(ReportFactory.testReport == null)
		{
			switch(reportType)	
			{
				case ExtentHtml :
					ReusableLibs reUsableLib = new ReusableLibs();
					String htmlReportName = reUsableLib.getConfigProperty("HtmlReport");
					String screenShotLocation = reUsableLib.getConfigProperty("ScreenshotLocation");		
					String strBoolAppendExisting = reUsableLib.getConfigProperty("boolAppendExisting");
					String strIsCignitiLogoRequired = reUsableLib.getConfigProperty("isCignitiLogoRequired");
					String extentConfigFile = reUsableLib.getConfigProperty("extentConfigFile");	
					boolean boolAppendExisting = false;
					boolean boolIsCignitiLogoRequired = false;
					if(strBoolAppendExisting !=null && strBoolAppendExisting.equalsIgnoreCase("true"))
					{
						boolAppendExisting = true;
					}
					
					if(strIsCignitiLogoRequired !=null && strIsCignitiLogoRequired.equalsIgnoreCase("true"))
					{
						boolIsCignitiLogoRequired = true;
					}
					
					reUsableLib.makeDir(screenShotLocation);
					String filePath = String.format("%s", htmlReportName);
					ReportFactory.testReport = new ExtentReporter(filePath, extentConfigFile, boolAppendExisting, boolIsCignitiLogoRequired, extentTestVisibilityMode);
					
					break;				
				
				default:
					throw new Exception("Html Report Other Than Extent Is Not Currently Supported...");
					
			}
			
		}
		return ReportFactory.testReport;
			
	}
	
	
}
