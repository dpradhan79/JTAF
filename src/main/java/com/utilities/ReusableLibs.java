package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.testng.log4testng.Logger;

import com.config.IConstants;
import com.google.common.io.Resources;

/**
 * 
 * @author E001518 - Debasish Pradhan (Architect)
 *
 */
public class ReusableLibs 
{
    private static final Logger LOG = Logger.getLogger(ReusableLibs.class);

    /**
     * Purpose: Invoke unlock.vbs process in master machine 
     */
    public void invokeUnlockProcessInHub()
    {
        try
        {
            String sVBSFilePath = System.getProperty("user.dir") + File.separatorChar + "unlock.vbs";
            Runtime rt = Runtime.getRuntime();
            rt.exec("wscript.exe " + sVBSFilePath);
        }
        catch(Exception e)
        {
            System.out.println("Error Exception occured in Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());
            
        }
    }

    /**
     * Purpose: Gets Config property
     * @param sKeyName
     * @return String
     */
    public String getConfigProperty(String sKeyName)
    {
        try
        {
            Properties prop = new Properties();            

            String sFilePath = Paths.get(Resources.getResource(IConstants.TEST_CONFIG_FILE).toURI()).toFile().getAbsolutePath();

            FileInputStream file = new FileInputStream(sFilePath);
            InputStreamReader inputFileStreamReader = new InputStreamReader(file,"UTF-8");

            prop.load(inputFileStreamReader);
            String sValue = prop.getProperty(sKeyName);
            return sValue;
        }
        catch(Exception e)
        {

           System.out.println("Error" + e.getCause().toString());
            return "";
        }
    }

    

    /**
     * Purpose:Returns the date for the key word.
     * @return String 
     */
    public String getDate() 
    {	
        try
        {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dt = sdf.format(cal.getTime());
            return dt;
        }
        catch(Exception e)
        {
        	LOG.debug(e.getStackTrace());
            return "false";
        }
    }

   

    /**
     * Purpose:Returns the date for the key word.
     * @param sValue 
     * @return String 
     * @throws Exception
     */
    public String getDate(String sValue) throws Exception
    {	  
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dt = sValue;

        if (sValue.trim().equalsIgnoreCase("Today"))
        {
            dt = sdf.format(cal.getTime());
        }

        if (sValue.trim().contains("Today_"))
        {
            String [] arrValues = sValue.split("_");
            int iDays = Integer.parseInt(arrValues[1]);
            cal.add(Calendar.DATE, iDays);
            dt = sdf.format(cal.getTime());
        }
        if (sValue.trim().contains("Today#"))
        {
            String [] arrValues = sValue.split("#");
            int iDays = Integer.parseInt(arrValues[1]);
            cal.add(Calendar.DATE, -iDays);
            dt = sdf.format(cal.getTime());
        }
        return dt;
    }


    public boolean fileExists(String sFileName)
    {		
        try
	    {		
	        File objFile = new File(sFileName);
	        if (objFile.exists()){
	            return true;
	        }		
        }
        
        catch(Exception e)
        {
        	LOG.error(e.getStackTrace());
          
        }
	    return false;
    }
    
    public boolean makeDir(String sFileName)
    {		
        try
	    {		
	        if(!this.fileExists(sFileName))	
	        {
	        	File objFile = new File(sFileName);
	        	objFile.mkdir();
	        	return true;
	        }
        }
        
        catch(Exception e)
        {
        	LOG.error(e.getStackTrace());
          
        }
	    return false;
    }

    /**
     * @param sPropertiesFileName
     * @param sElementKey
     * @return
     */
    public String getElementLocator(String sPropertiesFileName,String sElementKey)
    {
        boolean bExists = false;
        try
        {			
            Properties prop = new Properties();

            //String sFilePath = getConfigProperty("XPathFolderPath") + sPropertiesFileName;
            String sFilePath = Paths.get(Resources.getResource(IConstants.PAGE_ELEMENTS_LOCATION + File.separatorChar + sPropertiesFileName).toURI()).toFile().getAbsolutePath();

            File newfile = new File(sFilePath);
            bExists = newfile.exists();
            if(!bExists)
            {
            	String gErrMsg = "Properties file not displayed in the given path:" + newfile.getAbsolutePath();
                LOG.error(gErrMsg);              
            	return "-1";
            } 

            FileInputStream file = new FileInputStream(sFilePath);
            InputStreamReader input = new InputStreamReader(file,"UTF-8");

            prop.load(input);
            String sValue = prop.getProperty(sElementKey);

            return sValue;
        }
        catch(Exception e)
        {
        	String gErrMsg = e.getMessage().toString();        	           
            LOG.error("Error Exception occured while trying to get XPath for the element:" + sElementKey + " from file:" + sPropertiesFileName + "\n " + gErrMsg);
            return "-1";
        }
    }
    
    public static String getScreenshotFile(String screenshotFolder, String extension)
	{
		ReusableLibs reUsableLib = new ReusableLibs();
		String fileName = null;
		int count = 1;
		String strScreenshotFileName = String.format("%s%s%s%d.%s", screenshotFolder, File.separatorChar, "Screenshot_", count, extension);
		
		while(reUsableLib.fileExists(strScreenshotFileName))
		{
			//get new file name
			//increase count
			strScreenshotFileName = String.format("%s%s%s%d.%s", screenshotFolder, File.separatorChar, "Screenshot_", ++count, extension);
			
		}		
		fileName = strScreenshotFileName;
		return fileName;
	}
}