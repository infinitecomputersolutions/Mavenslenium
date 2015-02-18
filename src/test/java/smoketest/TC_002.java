package smoketest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import main.common.Driverpage;
import main.common.Runprop;
import main.common.ScreenshotErrorLog;
import main.common.WriteResulttoDB;
import main.wordpress.WordpressHomepageprop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import smoke.DBConnect_Credentials;
import smoke.TestngXMLGenerator_From_XL_File;

public class TC_002 {

	private TestngXMLGenerator_From_XL_File testxl;
	private static Runprop runConfig;
	private static WordpressHomepageprop wordpresproperty;
	WebDriver driver;
	
	public static Connection con = null;
	public static ResultSet resultSet = null;
	public static ResultSet value;
	public static String dbvalue;
	String currentFilename="";
	String testType="";
	
	int testsuite_id;
	String dbtestplan_id;
	WriteResulttoDB wr = new WriteResulttoDB();

	String flag = "Pass";
	
	int i = 2;

	String strI = Integer.toString(i);

	@Test
	public void example() throws Exception 
	{
		
		
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		ScreenshotErrorLog.resultFlag = "PASS";
		String Str = new String(this.getClass().getName());
		System.out.println("Str...: "+this.getClass().getName());
		String[] retval = Str.split("\\.");
		testType = retval[0];
		currentFilename = retval[1];
	    System.out.println("testType..."+ testType);
	    System.out.println("currentFilename..."+ currentFilename);
	     
		try 
		{
			Driverpage driverpage = new Driverpage();
			runConfig = new Runprop();
			driver = driverpage.getDriver(runConfig.BROWSER);
			testxl = new TestngXMLGenerator_From_XL_File();
			wordpresproperty = new WordpressHomepageprop();
			System.out.println(wordpresproperty.URL);
			driver.get(wordpresproperty.URL);
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DBConnect_Credentials.url1,DBConnect_Credentials.db_username,DBConnect_Credentials.db_password);

			// Enter UserName
			Thread.sleep(3000);
			driver.findElement(By.id(wordpresproperty.LOGIN)).clear();

			value = returndata("username", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
			while (value.next()) 
			{
				dbvalue = value.getString(1);
				driver.findElement(By.id(wordpresproperty.LOGIN)).sendKeys(value.getString(1));
			}
			// Enter Password
			driver.findElement(By.id(wordpresproperty.PWD)).clear();
			
			value = returndata("password", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
			while (value.next()) 
			{
				dbvalue = value.getString(1);
				driver.findElement(By.id(wordpresproperty.PWD)).sendKeys(value.getString(1));// demo123
			}
			// Click on Submit button
			driver.findElement(By.id(wordpresproperty.SUBMIT)).submit();
			if (driver.findElement(By.xpath(wordpresproperty.ERRORMSG))
					.isDisplayed()) {
				System.out.println("error msg is displayed");
			}
			wr.updateDB("Pass",testType,currentFilename);

		} 
		
		catch(Exception e)
		{
			e.printStackTrace();
		    ScreenshotErrorLog ss = new ScreenshotErrorLog();
		    ss.ScreenshotOnTestFailure(testType,currentFilename);
		    ss.CreateLogOnTestFailure(e,testType,currentFilename);
		    System.out.println("Hi.... TC_002 Catch block");
		    flag = "Fail";
		    wr.updateDB("Fail",testType,currentFilename);
		    System.out.println(flag);
		    Assert.fail();		    
		}
		finally
		{
			driver.quit();
		}
	}
	
	public static ResultSet returndata(String selectedfield, String tablename,
			String testcaseid, String testcasename, String Projectid,
			String idvalue) throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"Select %s from %s where %s='%s' and %s='%s'", selectedfield,
				tablename, testcaseid, testcasename, Projectid, idvalue));

		System.out.println(stm);

		return stm.executeQuery();

	}


}
