package smoketest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import main.cleartrip.CleartripHomePage;
import main.common.Driverpage;
import main.common.Runprop;
import main.common.ScreenshotErrorLog;
import main.common.WriteResulttoDB;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_001 {

	private CleartripHomePage homepage;
	private Runprop property;
	public WebDriver driver;
	private static Runprop runConfig;
	int testsuite_id;
	String dbtestplan_id;
	String currentFilename="";
	String testType="";
	String flag = "Pass";
	
	@Test
	public void example() throws Exception 
	{
		ScreenshotErrorLog.resultFlag = "PASS";
		WriteResulttoDB wr = new WriteResulttoDB();
		 
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
			// driver = driverpage.getDriver(property.BROWSER);
			property = new Runprop();
			driver = driverpage.getDriver(property.BROWSER);
			System.out.println("****" + property.BROWSER);
			homepage = new CleartripHomePage(driver);
	
			homepage.openurl();
			homepage.searchflights();
			// Thread.sleep(3000);
			homepage.bookflight();
			homepage.email();
			homepage.travellers();
			homepage.quit();
			wr.updateDB("Pass",testType,currentFilename);
		}
		 
		catch(Exception e)
		{
			e.printStackTrace();
		    ScreenshotErrorLog ss = new ScreenshotErrorLog();
		    ss.ScreenshotOnTestFailure(testType,currentFilename);
		    ss.CreateLogOnTestFailure(e,testType,currentFilename);
		    System.out.println("Hi.... TC_001 catch block");
		    flag = "Fail";
		    wr.updateDB("Fail",testType,currentFilename);
		    System.out.println("Flag Status..."+flag);
		    Assert.fail();
		 }
		finally
		{
			System.out.println("Inside Finally");
			System.out.println("Closed Status..."+driver.toString());
			if(driver.toString().length()!=0)
				driver.quit();
		}
	}
}
