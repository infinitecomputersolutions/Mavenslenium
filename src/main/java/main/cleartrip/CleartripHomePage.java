package main.cleartrip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import main.common.Runprop;
import main.common.ScreenshotErrorLog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import smoke.DBConnect_Credentials;
import smoke.TestngXMLGenerator_From_XL_File;

public class CleartripHomePage 
{
	public TestngXMLGenerator_From_XL_File testxl;
	public static Connection con = null;
	public static ResultSet resultSet = null;
	public static ResultSet value;
	public static String dbvalue;
	int i = 1;
	String strI = Integer.toString(i);
	public Runprop property  ;
	private ClearHomepageprop clearproperty = new ClearHomepageprop();
	String str = null;
	WebDriver driver;

	public CleartripHomePage(WebDriver driver) throws FileNotFoundException,IOException, ClassNotFoundException, SQLException 
	{
		super();
		testxl = new TestngXMLGenerator_From_XL_File();
		property = new Runprop();
		this.driver = driver;
	}

	public void openurl() throws ClassNotFoundException, SQLException 
	{
		
		System.out.println("url" + clearproperty.URL);
		driver.get(clearproperty.URL);
		//driver.get("http://www.google.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		System.out.println("Application Title" + driver.getTitle());
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(DBConnect_Credentials.url1,DBConnect_Credentials.db_username,	DBConnect_Credentials.db_password);
	}

	public void searchflights() throws Exception 
	{
		// From
		value = returndata("txt_from", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
		driver.findElement(By.id(clearproperty.FROM)).click();
		while (value.next()) 
		{
			dbvalue = value.getString(1);
			driver.findElement(By.id(clearproperty.FROM)).sendKeys(value.getString(1));
		}
		// To
		driver.findElement(By.id(clearproperty.TO)).click();
		value = returndata("txt_to", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
	
		while (value.next()) 
		{
			dbvalue = value.getString(1);
			driver.findElement(By.id(clearproperty.TO)).sendKeys(value.getString(1));
		}
		System.out.println(property.EXCELPATH);
		// Date
		driver.findElement(By.id(clearproperty.DATETXT)).click();
		// Date
		driver.findElement(By.xpath(clearproperty.DATEPICKER)).click();
		// Search button
		driver.findElement(By.id(clearproperty.SEARCH)).submit();
	}

	public void bookflight() 
	{
		// Book
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(clearproperty.FLIGHTBOOK)).click();
		// checkbox
		driver.findElement(By.id(clearproperty.ITRNRYCHECK)).click();
		// Continue booking
		driver.findElement(By.id(clearproperty.ITRNRYCONTINUE)).click();
	}

	public void email() throws Exception 
	{
		driver.findElement(By.id(clearproperty.EMAIL)).click();
		value = returndata("email", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
		while (value.next()) 
		{
			dbvalue = value.getString(1);
			driver.findElement(By.id(clearproperty.EMAIL)).sendKeys(value.getString(1));
		}
		driver.findElement(By.id(clearproperty.EMAILCONTINUE)).click();
	}

	public void travellers() throws Exception 
	{	
			driver.findElement(By.id(clearproperty.TITLE)).click();
			Select select = new Select(driver.findElement(By.id(clearproperty.TITLE)));
			select.selectByVisibleText(clearproperty.GENDER);

			// traveller Name
			driver.findElement(By.id(clearproperty.FNAME)).click();
			driver.findElement(By.id(clearproperty.FNAME)).clear();
			value = returndata("fname", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
			while (value.next()) 
			{
				dbvalue = value.getString(1);
				driver.findElement(By.id(clearproperty.FNAME)).sendKeys(value.getString(1));
			}

			driver.findElement(By.id(clearproperty.LNAME)).click();
			driver.findElement(By.id(clearproperty.LNAME)).clear();
			value = returndata("lname", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
			while (value.next()) 
			{
				dbvalue = value.getString(1);
				driver.findElement(By.id(clearproperty.LNAME)).sendKeys(value.getString(1));
			}
			// Mobile
			driver.findElement(By.id(clearproperty.MOBILENO)).click();
			driver.findElement(By.id(clearproperty.MOBILENO)).clear();

			value = returndata("mobileno", "selenium_igaf_testdata","selenium_id", strI, "project_id", "1");
			while (value.next()) 
			{
				dbvalue = value.getString(1);
				driver.findElement(By.id(clearproperty.MOBILENO)).sendKeys(value.getString(1));
			}
			// Continue
			driver.findElement(By.id(clearproperty.TRVLRCONTINUE)).click();
	}

	public static ResultSet returndata(String selectedfield, String tablename,String testcaseid, String testcasename, String Projectid,String idvalue) throws Exception 
	{
		PreparedStatement stm = con.prepareStatement(String.format("Select %s from %s where %s='%s' and %s='%s'", selectedfield,tablename, testcaseid, testcasename, Projectid, idvalue));
		System.out.println(stm);
		return stm.executeQuery();
	}

	public void quit() throws BiffException, IOException,RowsExceededException, WriteException 
	{
		driver.quit();
	}
}
