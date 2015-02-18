package main.common;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import smoke.DBConnect_Credentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HtmlPageValidation
{
 //private Runprop property;
 private static Runprop runConfig;
 String Result;
 Connection con;
 ResultSet resultSet;
 Statement stmt;
 String bName, osName,testEnvType,projectName,testType;
 boolean validTesttype = false;
 boolean validBrowsertype = false;
 boolean validEnvType = false;
 boolean validOStype = false;
 boolean validtesttooltype = false;
 
	/*public boolean browserType() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException
	{
		property=new Runprop();
		String browsername= property.BROWSER;
		if(browsername.equals("FIREFOX")||browsername.equals("IE")||browsername.equals("CHROME"))
		{
			System.out.println("The browser is supported by IGAF:" + browsername);
		}
		else
		{
			System.out.println("The browser is not supported by IGAF:" + browsername);
		}
		return true;
	}*/

	/*public boolean osType() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException
	{
		property=new Runprop();	
		//String osname= property.OS_TYPE;
		String osname=System.getProperty("os.name").toLowerCase();
		System.out.println("OS..........."+osname);
		
		if(osname.equals("win")||osname.equals("windowsxp")||osname.equalsIgnoreCase("windows7")||osname.equals("windows8")||osname.equals("windowsvista")||osname.equals("windows"))
		{
			System.out.println("The os is supported by IGAF:" + osname );
		}
		else
		{
			System.out.println("The os is not supported by IGAF:"+osname);
		}
		return true;
	}*/
	
	/*public void testEnvType() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		property=new Runprop();		
		String testEnvName= property.TEST_ENV;
		if(testEnvName.equals("qa"))
		{
			System.out.println("The TestEnv is supported by IGAF:" + testEnvName);
		}
		else
		{
			System.out.println("The TestEnv is not supported by IGAF:"+testEnvName);
		}
		
	}*/
	
	/*public void projectType() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		property=new Runprop();
		String project= property.PROJECT_NAME;
		String[] str=project.split("\\\\");
		String projectName=str[str.length-1];		
		System.out.println("project name"+ projectName);		
		if(projectName.equalsIgnoreCase("SeleniumIGAF")||projectName.equals("SeleniumIGAFbeta")||projectName.equals("CucumberIGAF")||projectName.equals("JBehaveIGAF")||projectName.equals("SeleniumGridIGAF"))
		{
			System.out.println("The project is supported by IGAF:"+projectName);
		}
		else
		{
			System.out.println("The project is not supported by IGAF:"+projectName);
		}
	}*/
	
	/*public void testType() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		property=new Runprop();
		String test= property.TC_TYPE;
		String[] str=test.split("\\\\");		
		String testTypeName=str[str.length-1];		
		System.out.println("testTypeName:"+ testTypeName);
		
		if(testTypeName.equals("SmokeTest"))
		{
			System.out.println("The TestType is supported by IGAF:"+testTypeName);
		}
		else{
			System.out.println("The TestType is not supported by IGAF:"+testTypeName);
		}
	}*/
	
	
	/*public void runBatFile() throws IOException, ClassNotFoundException, SQLException
	{
		property=new Runprop();
		String browsername= property.BROWSER;
		
		System.out.println("Browser name is :"+browsername);
		String osname= property.OS_TYPE;
		
		System.out.println("OS is"+osname);
		String testEnvName= property.TEST_ENV;
		System.out.println("Test Environment is:"+testEnvName);
		String project= property.PROJECT_NAME;
		
		System.out.println("Project :"+project);
		String[] str1=project.split("\\\\");
		String projectName=str1[str1.length-1];		
		System.out.println("project name"+ projectName);
		
		
		
		String test= property.TC_TYPE;
		String[] str=test.split("\\\\");		
		String testTypeName=str[str.length-1];
		
		
		if((browsername.equals("FIREFOX")||browsername.equals("IE")||browsername.equals("CHROME"))
				&&(osname.equalsIgnoreCase("windowsxp")||osname.equals("win")||osname.equals("windows7")||osname.equals("windows8")||osname.equals("windowsvista")||osname.equals("windows"))
				&&(testEnvName.equals("QA"))
				&&(projectName.equalsIgnoreCase("seleniumIGAF")||projectName.equals("SeleniumIGAFbeta")||projectName.equals("CucumberIGAF")||projectName.equals("JBehaveIGAF")||projectName.equals("SeleniumGridIGAF"))
						&&(testTypeName.equals("smoke"))){
			System.out.println("Testing is supported by IGAF");
			
			Result="PASS";
			
			String userpath=System.getProperty("user.home");
			String filename= userpath+"\\Desktop\\ExecTest.txt";
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			fw.write("Execute="+Result);//appends the string to the file
			fw.close();	
			
			
		}
		
		else
		{
			Result="FAIL";
			String userpath=System.getProperty("user.home");
			String filename= userpath+"\\Desktop\\ExecTest.txt";
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			fw.write("Execute="+Result);//appends the string to the file
			fw.close();	
			System.out.println("testing  is not supported by IGAF:");
		}
		
	}*/
	
	
	public void runBatFile() throws IOException, ClassNotFoundException, SQLException
	{
		Connection con;
		Statement stmt;
		ResultSet rs;
		int projectId = 0;
		int browserId = 0;
		String browsername=null;
		String osname = null;
		String testtype = null;
		String envname = null;
		String tool = null;
		int osId = 0, testtypeId = 0, env_Id = 0, testtoolid = 0;
		
		Properties props = new Properties();
        
        
        String userpaths=System.getProperty("user.home");
		String filenames= userpaths+"\\Desktop\\ExecTest.txt";
		FileInputStream fis = new FileInputStream(filenames);
      
        //loading properites from properties file
        props.load(fis);

        //reading property
        String username = props.getProperty("user");
        System.out.println("Currently Logged in User..."+username);
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    		stmt = con.createStatement();
    		System.out.println("Statement Open");
    		
    		//String query = "select project_id,browser_name,operating_system,tc_type,environment_id,tc_tool from testlab where tc_status is null and testsuite_id in(select max(testsuite_id) from testlab where username='amit' ) LIMIT 1";
    		String query = "select project_id,browser_name,operating_system,tc_type,environment_id,tc_tool from testlab where tc_status is null and testsuite_id in(select max(testsuite_id) from testlab where username='"+ username+ "') LIMIT 1";
            System.out.println("----------------"+query);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
            	projectId = rs.getInt("project_id");
            	browsername = rs.getString("browser_name");
            	osname = rs.getString("operating_system");
            	testtype = rs.getString("tc_type");
            	envname = rs.getString("environment_id");
            	tool = rs.getString("tc_tool");
            }
            
            if(rs!=null)
            	rs.close();
    		if(stmt!=null)
    			stmt.close();
    		if(con!=null)
    			con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
		
		//Get Browser-ID
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    		stmt = con.createStatement();
    		System.out.println("Statement Open");
    		
    		String query = "select browser_id from browser where browser_name='"+ browsername +"'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
            	browserId = rs.getInt("browser_id");
            	System.out.println(browserId);
   
            }
            
            if(rs!=null)
            	rs.close();
    		if(stmt!=null)
    			stmt.close();
    		if(con!=null)
    			con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
		
		//Get operating_system-ID
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    		stmt = con.createStatement();
    		System.out.println("Statement Open");
    		
    		String query = "select operating_system_id from operating_system where operating_system_name ='"+ osname +"'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
            	osId = rs.getInt("operating_system_id");
            	System.out.println(osId);
            }
            
            if(rs!=null)
            	rs.close();
    		if(stmt!=null)
    			stmt.close();
    		if(con!=null)
    			con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
		
		
		//Get testtype-ID
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    		stmt = con.createStatement();
    		System.out.println("Statement Open");
    		
    		String query = "select testtype_id from test_type where testtype_name ='"+ testtype  +"'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
            	testtypeId = rs.getInt("testtype_id");
            	System.out.println(testtypeId);
            }
            
            if(rs!=null)
            	rs.close();
    		if(stmt!=null)
    			stmt.close();
    		if(con!=null)
    			con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
		
		//Get env-ID
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    		stmt = con.createStatement();
    		System.out.println("Statement Open");
    		
    		String query = "select test_envrionment_id from test_environment where test_envrionment_name ='"+ envname  +"'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
            	env_Id = rs.getInt("test_envrionment_id");
            	System.out.println(env_Id);
            }
            
            if(rs!=null)
            	rs.close();
    		if(stmt!=null)
    			stmt.close();
    		if(con!=null)
    			con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
		
		//Get testtool-ID
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    		stmt = con.createStatement();
    		System.out.println("Statement Open");
    		
    		String query = "select testtool_id from test_tool where testtool_name ='"+ tool  +"'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
            	testtoolid = rs.getInt("testtool_id");
            	System.out.println(testtoolid);
            }
            
            if(rs!=null)
            	rs.close();
    		if(stmt!=null)
    			stmt.close();
    		if(con!=null)
    			con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
		
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    		stmt = con.createStatement();
    		System.out.println("Statement Open");
    		//String query = "select * from compatability where project_id =" + projectId + " and testtype_id = " + testtypeId +" and browser_id = " + browserId + " and evnronment_id = " + env_Id + " and operating_system_id = " +  osId + " and testtool_id =" + testtoolid;
    		String query = "select * from compatability where project_id =" + projectId;
    		System.out.println("***********" + query);
    		rs=stmt.executeQuery(query);
    		int count=0;
    		int testtypes[] = new int[100];
    		int browsertypes[] = new int[100];
    		int evnronmenttypes[] = new int[100];
    		int ostypes[] = new int[100];
    		int testtooltypes[] = new int[100];
    		
            while(rs.next())
            {
            	testtypes[count] = rs.getInt("testtype_id");
            	browsertypes[count] = rs.getInt("browser_id");
            	evnronmenttypes[count] = rs.getInt("evnronment_id");
            	ostypes[count] = rs.getInt("operating_system_id");
            	testtooltypes[count] = rs.getInt("testtool_id");
    			count++;
            }
            
            for(int i=0;i<count;i++)
            {
            	System.out.println("testtypes..."+testtypes[i]);
            	System.out.println("browsertypes..."+browsertypes[i]);
            	System.out.println("evnronmenttypes..."+evnronmenttypes[i]);
            	System.out.println("ostypes..."+ostypes[i]);
            	System.out.println("testtooltypes..."+testtooltypes[i]);
            	System.out.println("_______________");
            	
            	if(testtypeId==testtypes[i])
            		validTesttype = true;
            	
            	if(browserId==browsertypes[i])
            		validBrowsertype = true;
            	
            	if(env_Id==evnronmenttypes[i])
            		validEnvType = true;
            	
            	if(osId==ostypes[i])
            		validOStype = true;
            	
            	if(testtoolid==testtooltypes[i])
            		validtesttooltype = true;
            }
            
            System.out.println(validTesttype);
            System.out.println(validBrowsertype);
            System.out.println(validEnvType);
            System.out.println(validOStype);
            System.out.println(validtesttooltype);
            
            if(validTesttype==true && validBrowsertype == true && validEnvType == true && validOStype == true && validtesttooltype == true)
            {
            	System.out.println("Testing is supported by IGAF");
            	Result="PASS";
            	String userpath=System.getProperty("user.home");
    			String filename= userpath+"\\Desktop\\ExecTest.txt";
    			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
    			fw.write("Execute="+Result);//appends the string to the file
    			fw.close();	
            }
            
            else
    		{
    			Result="FAIL";
    			String userpath=System.getProperty("user.home");
    			String filename= userpath+"\\Desktop\\ExecTest.txt";
    			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
    			fw.write("Execute="+Result);//appends the string to the file
    			fw.close();	
    			System.out.println("Testing is NOT supported by IGAF");
    		}
            
            if(rs!=null)
            	rs.close();
    		if(stmt!=null)
    			stmt.close();
    		if(con!=null)
    			con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException
	{
		HtmlPageValidation htmlPageValidation=new HtmlPageValidation();
		htmlPageValidation.runBatFile();
		/*htmlPageValidation.projectType();*/
	}
}
