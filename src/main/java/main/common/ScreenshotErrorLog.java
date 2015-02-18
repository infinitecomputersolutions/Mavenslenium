package main.common;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;


public class ScreenshotErrorLog extends TestListenerAdapter
{
	//public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	public DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
	public DateFormat dateFormat3 = new SimpleDateFormat("MM/dd/yyyy");
	public Calendar cal = Calendar.getInstance();
	public static String scriptName = "test";
	public static int i = 1;
	public static String resultFlag="";
	
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	OutputStream txtfile;
	PrintStream out;
	
	Connection con;
	Statement query_stmt;
	String query_query;
	ResultSet query_result;
	
	int queryvalue_testsuiteid;
	int count=0;
	String queryvalue_testcaseid;
	 String directory="D:";

	File screenShotsFolder,errorLogFolder,temp_screenShotsFolder,Temp_errorLogFolder;
	String ts;
	
	 public void ScreenshotOnTestFailure(String testType,String currentFilename) 
	 {
		 WebDriver driver = Driverpage.getDriverInstance();
		 boolean result;
		 Date date = new Date();
		 
		 try 
		 {
			 
			 
				
				boolean resultfolder;
				File reportzip = new File(directory+"\\Reports");
				if (!reportzip.exists()) 
				    resultfolder = reportzip.mkdir();
				
			 	Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
				System.out.println("Connection Open1");
			
				query_stmt = con.createStatement();
				query_query = "SELECT testsuite_id,testplan_id,p.project_name,release_id,iteration_id,sprint_id,browser_name,operating_system,environment_id,tc_machine FROM testlab t,projects p where t.project_id=p.project_id group by t.testsuite_id order by testsuite_id desc,username LIMIT 1;";
				query_result = query_stmt.executeQuery(query_query);
				
				while (query_result.next())
				{
					queryvalue_testsuiteid = query_result.getInt("testsuite_id");
				}
				query_result.close();
				query_stmt.close();
				query_stmt.close();
				con.close();
			 
				 Class.forName("com.mysql.jdbc.Driver");
				 con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
				 System.out.println("Connection Open2");
		
				 query_stmt = con.createStatement();
				 query_query = "SELECT tl.testsuite_id,tp.tc_id, tl.testsuite_name, tl.testplan_id,tp.tc_summary,tp.tc_desc,tl.tc_status FROM testlab tl,testplan tp where tl.testplan_id = tp.testplan_id and tl.testsuite_id='" + queryvalue_testsuiteid + "' and tl.tc_type ='" +testType + "' and tp.tc_id ='" +currentFilename + "'" ;
				 query_result = query_stmt.executeQuery(query_query);
				 System.out.println("Query_Result***"+query_result);
				 while (query_result.next())
				 {
					 count = count+1;
					 queryvalue_testcaseid = query_result.getString("tp.tc_id");
					 System.out.println("......."+queryvalue_testsuiteid);
				 }	
				 
				 //ts = new java.io.File(".").getCanonicalPath();
				 ts="E:\\Projects\\2014\\IGAFControllerbeta\\WebContent";
				 File temp = new File(ts+"\\Reports"+"\\Screenshots");
				 if (!temp.exists()) 
					    result = temp.mkdir();
				 
			
				 screenShotsFolder = new File(ts+"\\Reports\\Screenshots" + "\\Testsuite_"+ queryvalue_testsuiteid);
				 temp_screenShotsFolder = new File(directory+"\\Reports" + "\\Testsuite_Screenshots"+ queryvalue_testsuiteid);
				 System.out.println("..."+screenShotsFolder);
				 if (!screenShotsFolder.exists()) 
					    result = screenShotsFolder.mkdir();
				 
				 System.out.println("..."+temp_screenShotsFolder);
				 if (!temp_screenShotsFolder.exists()) 
					    result = temp_screenShotsFolder.mkdir();
				 
				 String filename = screenShotsFolder + "\\"+  queryvalue_testcaseid +  "_"+ dateFormat.format(date) + ".png";
				 String apache_filename = temp_screenShotsFolder + "\\"+  queryvalue_testcaseid +  "_"+ dateFormat.format(date) + ".png";
				 System.out.println("filename..."+filename);
				 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				 FileUtils.copyFile(scrFile, new File(filename));
				 FileUtils.copyFile(scrFile, new File(apache_filename));
				 Reporter.log("<br/><br/><img src=\"file://" + filename + "\""
							+ "height=\"600\" width=\"600\"" + " /><br/><br/>");
				 filename = "";
		 }
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 }
	 }
	 
	 public void CreateLogOnTestFailure(Exception errors,String testType,String currentFilename) 
	 {
		 WebDriver driver = Driverpage.getDriverInstance();
		 boolean result2;
		 boolean result3;
		 Date date = new Date();
		 resultFlag="FAILS";
		 try 
		 {
			 	Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
				System.out.println("Connection Open1");
			
				query_stmt = con.createStatement();
				query_query = "SELECT testsuite_id,testplan_id,p.project_name,release_id,iteration_id,sprint_id,browser_name,operating_system,environment_id,tc_machine FROM testlab t,projects p where t.project_id=p.project_id group by t.testsuite_id order by testsuite_id desc,username LIMIT 1;";
				query_result = query_stmt.executeQuery(query_query);
				while (query_result.next())
				{
					queryvalue_testsuiteid = query_result.getInt("testsuite_id");
				}
				query_result.close();
				query_stmt.close();
				query_stmt.close();
				con.close();
			 
				 Class.forName("com.mysql.jdbc.Driver");
				 con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
				 System.out.println("Connection Open2");
		
				 query_stmt = con.createStatement();
				 query_query = "SELECT tl.testsuite_id,tp.tc_id, tl.testsuite_name, tl.testplan_id,tp.tc_summary,tp.tc_desc,tl.tc_status FROM testlab tl,testplan tp where tl.testplan_id = tp.testplan_id and tl.testsuite_id='" + queryvalue_testsuiteid + "' and tl.tc_type ='" +testType + "' and tp.tc_id ='" +currentFilename + "'" ;
				 query_result = query_stmt.executeQuery(query_query);
				 while (query_result.next())
				 {
					 count = count+1;
					 queryvalue_testcaseid = query_result.getString("tp.tc_id");
					 System.out.println("......."+queryvalue_testsuiteid);
				 }	
				 
				 //ts = new java.io.File(".").getCanonicalPath();
				 ts="E:\\Projects\\2014\\IGAFControllerbeta\\WebContent";
				 
				 File temp = new File(ts+"\\Reports"+"\\ErrorLog");
				 if (!temp.exists()) 
					    result2 = temp.mkdir();
				 
				 errorLogFolder = new File(ts+"\\Reports\\errorLog" + "\\Testsuite_"+ queryvalue_testsuiteid);
				 if (!errorLogFolder.exists()) 
					    result3 = errorLogFolder.mkdir();
				 
				 Temp_errorLogFolder = new File(directory+"\\Reports\\errorLog" + "\\Testsuite_"+ queryvalue_testsuiteid);
				 if (!Temp_errorLogFolder.exists()) 
					    result3 = Temp_errorLogFolder.mkdir();
				 
				 
				 
				 String filename = errorLogFolder + "\\"+  queryvalue_testcaseid +  "_"+ dateFormat.format(date) + ".txt";
				 System.out.println("after2 ..."+filename);
				 
				 String filename2 = Temp_errorLogFolder + "\\"+  queryvalue_testcaseid +  "_"+ dateFormat.format(date) + ".txt";
				 System.out.println("after2 ..."+filename2);
				 
				 PrintWriter pw = new PrintWriter(filename,"UTF-8");
				 errors.printStackTrace(pw);
				 pw.close();
				 
				 PrintWriter apache_pw = new PrintWriter(filename2,"UTF-8");
				 errors.printStackTrace(apache_pw);
				 apache_pw.close();
		 }
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 }
	 }
}
