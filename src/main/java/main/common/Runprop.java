package main.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Runprop {

	public static Properties seleniumConfig = null;
	public String CLEARTRIP_URL;
	public String BIGACE_URL;
	public String WORDPRESS_URL;
	public String LOGIN;
	public String PWD;
	public String QC_URL;
	public String QC_USERNAME;
	public String QC_DOMAIN;
	public String QC_PROJECT;
	public String BROWSER;
	public String PLATFORM;
	public String TESTFOLDER;
	public String TESTSET;
	public String TESTSETID;
	public Integer TESTID;
	public Object BROW;
	public Object TCTOOL;
	public Object E_MAIL;
	public Object OS;
	public Object PATH;
	public static String browser;
	public static String email;
	public String EXCELPATH;
	public String TEST_REPORTS_PATH;
	public String REPORT_SENDER_MAIL_ID;
	public String REPORT_RECEIVERS_BCC_LIST;
	public String MOBILE_REPORT_RECEIVERS_LIST;
	public String REPORT_SENDER_MAIL_PASSWORD;
	public String REPORT_RECEIVERS_LIST;
	public String SMTP_HOST_NAME;
	public String SMTP_HOST_PORT_NO;

	public String OS_TYPE;
	public String TEST_ENV;
	public String PROJECT_NAME;
	public Object TEST_TYPE;
	public Object ENV;
	public String tctool;
	public String PROJECT_PATH;
	public String TC_TYPE;
	private static FileWriter fw;
	private static BufferedReader bufRdr;
	private static ArrayList<String> test;
	public String user;
	public Object USER_NAME;

	/**
	 * Configuration file location
	 */
	public static String SELENIUM_CONFIG_FILE; 

	public Runprop() throws FileNotFoundException, IOException,
			ClassNotFoundException, SQLException {
		seleniumConfig = new Properties();

		String current = new java.io.File(".").getCanonicalPath();// .getCanonicalPath();

		System.out.println("current" + current);

		SELENIUM_CONFIG_FILE = current + "\\Config\\selenium.properties";

		seleniumConfig.load(new FileInputStream(SELENIUM_CONFIG_FILE));
		
		

		String userpath = System.getProperty("user.home");
		//test = readFile(userpath+"\\desktop\\ExecTest.txt");
		
		
		/*PROJECT_NAME=test.get(0).toString().replace("Project=", "");
		
		user = test.get(1).toString().replace("user=", "");

		Class.forName("com.mysql.jdbc.Driver");

		Connection con = DriverManager.getConnection(
				DBConnect_Credentials.url1, DBConnect_Credentials.db_username,
				DBConnect_Credentials.db_password);

		Statement query_stmt = con.createStatement();
		String query_query = "select testlab_id,testplan_id,tc_tool,browser_name,operating_system,project_path,tc_type,environment_id,email from testlab where username='"+user+"' and automationflag='Yes' group by testsuite_id,username order by testsuite_id desc LIMIT 1;";
		ResultSet query_result = query_stmt.executeQuery(query_query);
		while (query_result.next()) {
			tctool = query_result.getString("tc_tool");

			System.out.println("$$$$$$$$$$$Tc_tool" + tctool);
			browser = query_result.getString("browser_name");
			OS_TYPE = query_result.getString("operating_system");
			
			TC_TYPE = query_result.getString("tc_type");
			TEST_ENV = query_result.getString("environment_id");
			email = query_result.getString("email");
			
			
			System.out.println("Browser is .........."+browser);
			System.out.println("OS_TYPE is .........."+OS_TYPE);
			System.out.println("TC_TYPE is .........."+TC_TYPE);
			System.out.println("TEST_ENV is .........."+TEST_ENV);
			System.out.println("Email is....."+email);
			

		}*/
		/*BROW = seleniumConfig.setProperty("browser", browser);

		E_MAIL = seleniumConfig.setProperty("report.receivers.list", email);

		TCTOOL = seleniumConfig.setProperty("tctool", tctool);

		OS = seleniumConfig.setProperty("ostype", OS_TYPE);
		
		PATH = seleniumConfig.setProperty("projectpath", PROJECT_NAME);
		
		TEST_TYPE = seleniumConfig.setProperty("tctype", TC_TYPE);
		
		USER_NAME = seleniumConfig.setProperty("username", user);
		
		ENV = seleniumConfig.setProperty("test.environment", TEST_ENV);*/
		
	
		
		BROW = seleniumConfig.getProperty("browser");
		E_MAIL = seleniumConfig.getProperty("report.receivers.list");
		TCTOOL = seleniumConfig.getProperty("tctool"); 
		OS = seleniumConfig.getProperty("ostype");
		PATH =seleniumConfig.getProperty("projectpath");
		TEST_TYPE =seleniumConfig.getProperty("tctype");
		USER_NAME=seleniumConfig.getProperty("username");
		ENV=seleniumConfig.getProperty("test.environment");
				
		CLEARTRIP_URL = seleniumConfig.getProperty("cleartripurl");
		BIGACE_URL = seleniumConfig.getProperty("bigaceurl");
		WORDPRESS_URL = seleniumConfig.getProperty("wordpressurl");
		LOGIN = seleniumConfig.getProperty("login");
		PWD = seleniumConfig.getProperty("pwd");
		QC_URL = seleniumConfig.getProperty("qcurl");
		QC_USERNAME = seleniumConfig.getProperty("qcusrname");
		QC_DOMAIN = seleniumConfig.getProperty("qcdomain");
		QC_PROJECT = seleniumConfig.getProperty("qcproject");
		BROWSER = seleniumConfig.getProperty("browser");
		PLATFORM = seleniumConfig.getProperty("platform");
		TESTFOLDER = seleniumConfig.getProperty("testFolder");
		TESTSET = seleniumConfig.getProperty("testSet");
		TESTSETID = seleniumConfig.getProperty("testSetID");
		TEST_REPORTS_PATH = seleniumConfig.getProperty("test.reports.path");
		REPORT_RECEIVERS_LIST = seleniumConfig
				.getProperty("report.receivers.list");
		REPORT_SENDER_MAIL_ID = seleniumConfig
				.getProperty("report.sender.mail.id");
		REPORT_SENDER_MAIL_PASSWORD = seleniumConfig
				.getProperty("report.sender.mail.password");
		REPORT_RECEIVERS_BCC_LIST = seleniumConfig
				.getProperty("report.receivers.bcc.list");
		
		
		
		

		SMTP_HOST_NAME = seleniumConfig.getProperty("smtp.host.name");
		SMTP_HOST_PORT_NO = seleniumConfig.getProperty("smtp.host.port");
		TCTOOL = seleniumConfig.getProperty("tctool");
		OS = seleniumConfig.getProperty("ostype");
		PATH = seleniumConfig.getProperty("projectpath");
		TEST_TYPE = seleniumConfig.getProperty("tctype");
		USER_NAME = seleniumConfig.getProperty("username");
				
				
		ENV=seleniumConfig.getProperty("test.environment");
		
		TESTID = Integer.parseInt(TESTSETID);
		//EXCELPATH = current + "\\TestData\\Input.xls";
		seleniumConfig.store(new FileOutputStream(SELENIUM_CONFIG_FILE),
				"Browser updated");
	
		
		
		
		

	

	}
	public static ArrayList<String> readFile(String filePath)
			throws FileNotFoundException {
		File file = new File(filePath);
		bufRdr = new BufferedReader(new FileReader(filePath));
		ArrayList<String> out = new ArrayList<String>();
		String line = null;
		try {
			for (int i = 0; (line = bufRdr.readLine()) != null; i++) {
				out.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;

	}

}
