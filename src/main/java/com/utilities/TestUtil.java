package com.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import com.excel.Xls_Reader;

/**
 * 
 * @author E001518  - Debasish Pradhan (Architect)
 *
 */
public class TestUtil {
	// true - Y
		// false - N
		/**
		 * Function to check whether test case can be executed or not.
		 * @testCase - Name of the testCase
		 * @Xls_Reader - Xls_Reader object
		 */
		public boolean isTestCaseExecutable(String testCase, Xls_Reader xls){
			/* iterate through the rows of Test Cases sheet from 2nd row till testCase
			name is equal to the value in TCID column.*/
			for(int rNum=2; rNum<=xls.getRowCount("Test Cases"); rNum++){
				//Checks whether testCase(passes value) name is equals to the value in TCID
				if(testCase.equals(xls.getCellData("Test Cases", "TCID", rNum))){
					// check runmode is equals to Y/N. Returns true if Y else return false
					if(xls.getCellData("Test Cases", "Runmode", rNum).equalsIgnoreCase("Y"))
						return true;
					else
						return false;
				}				
			}		
			return false;		
		}
	
	
	
	synchronized public Hashtable<String, String> getData(String testCase, String sheetName){
		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"\\TestData\\LoginData.xlsx");
		System.out.println("*************");
		System.out.println("*************"+System.getProperty("user.dir")+"\\TestData\\LoginData.xlsx");
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		int testCaseStartRowNum=0;
		for(int rNum=1;rNum<=xls.getRowCount(sheetName);rNum++){
			if(testCase.equals(xls.getCellData(sheetName, 0, rNum))){
				testCaseStartRowNum = rNum;
				break;
			}
		}
		System.out.println("Test Starts from row -> "+ testCaseStartRowNum);
		
	
		
		int cols=xls.getColumnCount(sheetName);
		Hashtable<String,String> table=null;
		
		// print the test data
		
		table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++){
				table.put(xls.getCellData(sheetName, cNum, 1),xls.getCellData(sheetName, cNum, testCaseStartRowNum));
				System.out.print(xls.getCellData(sheetName, cNum, testCaseStartRowNum)+" - ");
			}
			

		return table;// dummy
		
		
		
		
	}
	
	
	synchronized public static Hashtable<String, String> getDataSignUp(String testCase, String sheetName){
		Xls_Reader xls2 = new Xls_Reader(System.getProperty("user.dir")+"/TestData/LoginData.xlsx");
		System.out.println("*************");
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		int testCaseStartRowNum=0;
		for(int rNum=1;rNum<=xls2.getRowCount(sheetName);rNum++){
			if(testCase.equals(xls2.getCellData(sheetName, 0, rNum))){
				testCaseStartRowNum = rNum;
				break;
			}
		}
		//System.out.println("Test Starts from row -> "+ testCaseStartRowNum);
		
	
		
		int cols=xls2.getColumnCount(sheetName);
		Hashtable<String,String> table=null;
		
		// print the test data
		
		table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++){
				table.put(xls2.getCellData(sheetName, cNum, 1),xls2.getCellData(sheetName, cNum, testCaseStartRowNum));
				//System.out.print(xls2.getCellData(sheetName, cNum, testCaseStartRowNum)+" - ");
			}
			

		return table;// dummy
		
		
		
		
	}
	
	
	synchronized public List<String> getAllRow(String sheetName){
		Xls_Reader xls1 = new Xls_Reader(System.getProperty("user.dir")+"/TestData/TestData.xlsx");
		System.out.println("*************");
		List<String> l = new ArrayList<String>();
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		//System.out.println(xls1.getRowCount(sheetName));
		
		for(int rNum=1;rNum<=xls1.getRowCount(sheetName)-1;rNum++){
			l.add(xls1.getCellData(sheetName, 0, rNum+1));
			//System.out.println(l.get(rNum-1));
			}
		return l;
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Function to get data from xls sheet in 2 dimensional array
	 * @param testCase - testCase name
	 * @param xls - Xls_Reader Object
	 * @return 2 dimensional array
	 */
	public static Object[][] getData(String testCase, Xls_Reader xls, String sheetName){
	
		System.out.println("******getData*******: "+testCase);
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array		
		int testCaseStartRowNum=0;
		//iterate through all rows from the sheet Test Data
		for(int rNum=1; rNum<=xls.getRowCount(sheetName); rNum++){
			//to identify testCase starting row number
			if(testCase.equals(xls.getCellData(sheetName, 0, rNum))){
				testCaseStartRowNum = rNum;
				break;
			}
		}

		System.out.println("Test Starts from row -> "+ testCaseStartRowNum);				
		// total cols
		int colStartRowNum=testCaseStartRowNum  + 1;
		
		int cols=0;
		
		//Get the total number of columns for which test data is present
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals("")){
			cols++;
		}

		System.out.println("Total cols in test -> "+ cols);		
		// rows
		int rowStartRowNum=testCaseStartRowNum+2;
		int rows=0;
		//Get the total number of rows for which test data is present
		while(!xls.getCellData(sheetName, 0, (rowStartRowNum+rows)).equals("")){
			rows++;
		}
		
		System.out.println("Total rows in test -> "+ rows);
		Object[][] data = new Object[rows][1];
		Hashtable<String,String> table=null;		
		// print the test data
		for(int rNum=rowStartRowNum;rNum<(rows+rowStartRowNum);rNum++){
			table=new Hashtable<String,String>();
				for(int cNum=0;cNum<cols;cNum++){
					table.put(xls.getCellData(sheetName, cNum, colStartRowNum), xls.getCellData(sheetName, cNum, rNum));
					System.out.print(xls.getCellData(sheetName, cNum, rNum)+" - ");
				}
				data[rNum-rowStartRowNum][0]=table;
				System.out.println();
		}
		return data;// dummy								
	}
	//Below method is to get test data for cucumber
	public static Hashtable<String,String>  getDataCucumber(String testCase, Xls_Reader xls, String sheetName){

		System.out.println("******getData For Cucumber*******: "+testCase);
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		int testCaseStartRowNum=0;
		//iterate through all rows from the sheet Test Data
		for(int rNum=1; rNum<=xls.getRowCount(sheetName); rNum++){
			//to identify testCase starting row number
			if(testCase.equals(xls.getCellData(sheetName, 0, rNum))){
				testCaseStartRowNum = rNum;
				break;
			}
		}

		System.out.println("Test Starts from row -> "+ testCaseStartRowNum);
		// total cols
		int colStartRowNum=testCaseStartRowNum+1;
		int cols=0;
		//Get the total number of columns for which test data is present
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals("")){
			cols++;
		}

		System.out.println("Total cols in test -> "+ cols);
		// rows
		int rowStartRowNum=testCaseStartRowNum+2;
		int rows=0;
		//Get the total number of rows for which test data is present
		while(!xls.getCellData(sheetName, 0, (rowStartRowNum+rows)).equals("")){
			rows++;
		}

		System.out.println("Total rows in test -> "+ rows);
		Object[][] data = new Object[rows][1];

		Hashtable<String,String> table=null;
		// print the test data
		for(int rNum=rowStartRowNum;rNum<(rows+rowStartRowNum);rNum++){
			table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++){
				table.put(xls.getCellData(sheetName, cNum, colStartRowNum), xls.getCellData(sheetName, cNum, rNum));
				System.out.print(xls.getCellData(sheetName, cNum, rNum)+" - ");
			}
			//data[rNum-rowStartRowNum][0]=table;
			System.out.println();

			//HashMap<String, String> map = (HashMap<String, String>) data;
		}
		return table;// dummy
	}
	/**
	 * Function to get start row number
	 * @param xls - Xls_Reader Object
	 * @param xls - row number
	 */
	public int startRow(Xls_Reader xls, int rowNum){
		String testCaseStartRow= null;
		testCaseStartRow = xls.getCellData("Test Cases", 3, rowNum);
		int testCaseStartRowNum = Integer.parseInt(testCaseStartRow);
			return testCaseStartRowNum;
	}
	/**
	 * Function to get End row number
	 * @param xls - Xls_Reader Object
	 * @param xls - row number
	 */
	public int endRow(Xls_Reader xls, int rowNum){
		String testCaseEndRow= null;
		testCaseEndRow = xls.getCellData("Test Cases", 4, rowNum);
		int testCaseEndRowNum = Integer.parseInt(testCaseEndRow);
			return testCaseEndRowNum;
	}

	
}
