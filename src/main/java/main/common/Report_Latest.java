package main.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Report_Latest 
{
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	OutputStream htmlfile,htmlfile2;
	PrintStream out;
	boolean dirFlag = false;
	String files,files2;
	
	int index=0;
	String[] fileNames = new String[100];
	String[] errorfileNames = new String[100];
	
	Connection con;
	Statement query_stmt;
	String query_query;
	ResultSet query_result;
	
	int queryvalue_testsuiteid;
	int count=0;
	int queryvalue_testplanid;
	String queryvalue_Projectname;
	String queryvalue_browsername;
	String queryvalue_operatingsystem;
	String queryvalue_envid;
	String queryvalue_tcmachine;
	String queryvalue_testcaseid;
	String queryvalue_tcsummary;
	String queryvalue_testsuitename;
	String queryvalue_tcdesc;
	String queryvalue_tcstatus;
	String ReportName,DB_ReportName,Apache_ReportName;
	String temp;
	int testsuite_id;
	String dbtestplan_id;
	String names;
	  String directory ="E:";
	
	public static void main(String[] args) 
	{
		Report_Latest r = new Report_Latest();
		try 
		{
			r.Query1();
			r.startHtml();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}
	
	
	public void Query1() throws ClassNotFoundException, SQLException, IOException
	{
		Properties props = new Properties();
        
        
        String userpaths=System.getProperty("user.home");
		String filenames= userpaths+"\\Desktop\\ExecTest.txt";
		FileInputStream fis = new FileInputStream(filenames);
      
        //loading properites from properties file
        props.load(fis);

        //reading property
        String username = props.getProperty("user");
        
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
		System.out.println("Connection Open1");
	
		query_stmt = con.createStatement();
		query_query = "SELECT testsuite_id,testplan_id,p.project_name,release_id,iteration_id,sprint_id,browser_name,operating_system,environment_id,tc_machine FROM testlab t,projects p where t.project_id=p.project_id and username ='"+ username + "' and automationflag='Yes' group by t.testsuite_id order by testsuite_id desc,username LIMIT 1";
		System.out.println("```````````````````"+query_query);
		query_result = query_stmt.executeQuery(query_query);
		while (query_result.next())
		{
			queryvalue_Projectname = query_result.getString("project_name");
			queryvalue_testsuiteid = query_result.getInt("testsuite_id");
			queryvalue_testplanid = query_result.getInt("testplan_id");
			queryvalue_browsername = query_result.getString("browser_name");
			queryvalue_operatingsystem = query_result.getString("operating_system");
			queryvalue_envid = query_result.getString("environment_id");
			queryvalue_tcmachine = query_result.getString("tc_machine");
		}
		query_result.close();
		query_stmt.close();
		query_stmt.close();
		con.close();
	}
	
	public void startHtml() throws ClassNotFoundException, SQLException, IOException 
	{
		boolean result;
		boolean result1;
		boolean resultstyle;
		boolean resultscript;
		
		Date date = new Date();
		System.out.println("startHtml.....");
		System.out.println("date...."+ dateFormat.format(date));
		
		Properties props = new Properties();
        
        
        String userpaths=System.getProperty("user.home");
		String filenames= userpaths+"\\Desktop\\ExecTest.txt";
		FileInputStream fis = new FileInputStream(filenames);
      
        //loading properites from properties file
        props.load(fis);

        //reading property
        String username = props.getProperty("user");
        System.out.println("Currently Logged in User..."+username);
		 
		//String ts = new java.io.File(".").getCanonicalPath();
		String ts = "E:\\Projects";
		File reportfolder = new File(ts+"\\Reports"+"\\HTML");
		if (!reportfolder.exists()) 
		    result = reportfolder.mkdir();
		
		boolean resultfolder;
		File reportzip = new File(directory+"\\Reports");
		if (!reportzip.exists()) 
		    resultfolder = reportzip.mkdir();
		
		try 
		{		
		
			//htmlfile= new FileOutputStream(new File(ts+"\\Reports\\HTML\\TS_" + queryvalue_testsuiteid + "_"+ dateFormat.format(date) + ".html"));
            ReportName = ts+"\\Reports\\HTML\\TS_" + queryvalue_testsuiteid + "_"+ dateFormat.format(date) + ".html";
            DB_ReportName = "TS_" + queryvalue_testsuiteid + "_"+ dateFormat.format(date);
            
          
            File tempreportfolder = new File(directory+"\\Reports"+"\\HTML");
            if(!tempreportfolder.exists())
            {
            	result1 = tempreportfolder.mkdir();
            }
            
            FileUtils.cleanDirectory(tempreportfolder); 
            
            Apache_ReportName = directory+"\\Reports"+"\\HTML\\TS_" + queryvalue_testsuiteid + "_"+ dateFormat.format(date) + ".html";
            
       
            
            
            File tempreportfolderstyle = new File(directory+"\\Reports"+"\\style");
            if(!tempreportfolderstyle.exists())
            {
            	resultstyle = tempreportfolderstyle.mkdir();
            }
            File sourcestyle = new File(ts+"\\Reports\\style");
            File deststyle = new File(directory+"\\Reports"+"\\style");
            try {
                FileUtils.copyDirectory(sourcestyle, deststyle);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
         
            File tempreportfolderscript = new File(directory+"\\Reports"+"\\script");
            if(!tempreportfolderscript.exists())
            {
            	resultstyle = tempreportfolderstyle.mkdir();
            }
            File sourcescript = new File(ts+"\\Reports\\script");
            File destscript = new File(directory+"\\Reports"+"\\script");
            try {
                FileUtils.copyDirectory(sourcescript, destscript);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Class.forName("com.mysql.jdbc.Driver");
    		Connection con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    	
    		Statement query_stmt = con.createStatement();
    		/*String query_query = "SELECT testsuite_id,username FROM testlab group by testsuite_id order by testsuite_id desc,username  LIMIT 1";
    		System.out.println("...."+query_query);*/
    		
//    		String query_query = "select testsuite_id,username from testlab where tc_status is null and username='" + username + "' and testsuite_id in(select max(testsuite_id) from testlab where username='"+ username+ "') LIMIT 1";
    		String query_query = "select testsuite_id,username from testlab where username='" + username + "' and testsuite_id in(select max(testsuite_id) from testlab where username='"+ username+ "') LIMIT 1";
            System.out.println("----------------"+query_query);
            
    		ResultSet query_result = query_stmt.executeQuery(query_query);
    		while (query_result.next())
    		{
    			testsuite_id = query_result.getInt("testsuite_id");
    			names =  query_result.getString("username");
    		}
    		query_result.close();
    		query_stmt.close();
    		con.close();
    		
    		Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection(DBConnect_Credentials.url1,DBConnect_Credentials.db_username,	DBConnect_Credentials.db_password);
    		System.out.println("Connection Open");
    	
    		Statement stmt = con.createStatement();
    		System.out.println("Statement Open");
   
    		String query1 = "UPDATE testlab SET report_name='" + DB_ReportName	+ "' WHERE testsuite_id='" +testsuite_id + "' and username='"+ username + "'";
    		
    	    System.out.println("@@@@@@@@@@@@@"+query1);
    	    stmt.executeUpdate(query1);
    	    stmt.close();
    	    con.close();
    	    
            htmlfile= new FileOutputStream(new File(ReportName));
			out = new PrintStream(htmlfile);
			
			/*htmlfile2= new FileOutputStream(new File(Apache_ReportName));
			out = new PrintStream(htmlfile2);*/
			
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			
			out.println("<head>");
			out.println("<title>Test Results-"+ queryvalue_testsuiteid +"</title>");
			out.println("<link type=\"text/css\" rel=\"stylesheet\" href=\"../style/styles.css\">");
			out.println("<link type=\"image/x-icon\" rel=\"shortcut icon\" href=\"../style/favicon.ico\">");
			out.println("</head>");
			out.println("<body>");
			
			out.println("<table width='100%'>");
			out.println("<tr>");
			out.println("<td width=\"15%\" class=\"left\"><img src=\"../style/logo.png\" align=\"center\" height=\"65px\"></img></td>");
			out.println("<td width=70% class=\"txt\">Automation Test Execution Report (IGAF)</td>");
			out.println("<td width=\"15%\" align=\"right\"><img src=\"../style/girl1.jpg\" align=\"center\" height=\"100px\"></img></td>");
			out.println("</tr>");
			out.println("</table>");
			
			out.println("<div class=\"innerheading\"></div>");
			out.println("<Table width='100%' id=\"details\" class=\"smalltxt\">");
			out.println("<tr>");
		
			out.println("<td>Environment:  "+ queryvalue_envid +"</td>");
			out.println("<td>Project :  "+ queryvalue_Projectname +"</td>");
			out.println("<td>Machine :  "+ queryvalue_tcmachine +"</td>");
			out.println("<td>OS:  "+ queryvalue_operatingsystem +"</td>");
			out.println("<td>Browser :  "+ queryvalue_browsername +"</td>");
			out.println("<td>Date :  "+ dateFormat.format(date)  +"</td>");
			out.println("</tr>");
			out.println("</Table>");
			out.println("<div class=\"innerheading\"></div>");
			out.println("<br></br>");
			
			out.println("<table id=\"report\">");
			out.println("<tr class=\"head\">");
			out.println("<td align=center>S.No</td>");
			out.println("<td align=center>TestSuiteID</td>");
			out.println("<td align=center>TestSuiteName</td>");
			out.println("<td align=center>TestCaseID</td>");
			out.println("<td align=center>TC Summary</td>");
			out.println("<td align=center>TC Description</td>");
			out.println("<td align=center>Status</td>");
			out.println("<td align=center>Screenshot</td>");
			out.println("<td align=center>ErrorLog</td>");
			out.println("</tr>");
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
			System.out.println("Connection Open2");
		
			query_stmt = con.createStatement();
			query_query = "SELECT tl.testsuite_id,tp.tc_id, tl.testsuite_name, tl.tc_status,tl.testplan_id,tp.tc_summary,tp.tc_desc,tl.tc_status FROM testlab tl,testplan tp where tl.testplan_id = tp.testplan_id and tl.testsuite_id='" + queryvalue_testsuiteid + "' and username ='" + username +"'";
			System.out.println("********************************"+query_query);
			query_result = query_stmt.executeQuery(query_query);
			while (query_result.next())
			{
				count = count+1;
				queryvalue_testsuitename = query_result.getString("tl.testsuite_name");
				queryvalue_testcaseid = query_result.getString("tp.tc_id");
				queryvalue_tcsummary = query_result.getString("tp.tc_summary");
				queryvalue_tcdesc = query_result.getString("tp.tc_desc");
				queryvalue_tcstatus = query_result.getString("tl.tc_status");
				
					out.println("<tr>");
					out.println("<td align=center>"+ count +"</td>");
					out.println("<td align=center>"+ queryvalue_testsuiteid +"</td>");
					out.println("<td align=center>"+ queryvalue_testsuitename +"</td>");
					out.println("<td align=center>"+ queryvalue_testcaseid +"</td>");
					out.println("<td>"+ queryvalue_tcsummary +"</td>");
					out.println("<td>"+ queryvalue_tcdesc +"</td>");
					System.out.println("--------------"+queryvalue_tcstatus);
					if(queryvalue_tcstatus.equalsIgnoreCase("Pass"))
						out.println("<td align=center><font color=green>" + queryvalue_tcstatus + "</font></td>");
					else if(queryvalue_tcstatus.equalsIgnoreCase("Fail"))
						out.println("<td align=center><font color = red>" + queryvalue_tcstatus + "</font></td>");
					else
						out.println("<td align=center>" + queryvalue_tcstatus + "</td>");
					
					//Screenshots
					//String folderpath = new java.io.File(".").getCanonicalPath();
					String folderpath = "E:/Projects";
					String tmpfullpath = folderpath + "/Reports/Screenshots/Testsuite_";
					String fullpath = "../Screenshots/Testsuite_";
					
					System.out.println("Location..."+folderpath+"/Reports/Screenshots/Testsuite_"+queryvalue_testsuiteid);
					System.out.println("fullpath..."+fullpath);
					File folder = new File(tmpfullpath+queryvalue_testsuiteid);
					File[] listOfFiles = folder.listFiles(); 
					if(folder.exists()) 
					{
						for (int i = 0; i < listOfFiles.length; i++) 
						{
							if (listOfFiles[i].isFile()) 
							{
								files = listOfFiles[i].getName();
								if (files.endsWith(".png") || files.endsWith(".PNG"))
								{
									System.out.println("output..."+ i + files);
							        fileNames[i] = files;
							        if((fileNames[i]).contains(queryvalue_testcaseid))
							        	out.println("<td align=center><a href=\""+ fullpath+queryvalue_testsuiteid+"/" + fileNames[i] +"\" target='_blank'> <img src=\"../style/screenshot.png\" height=30 width=25 border=0/> </a> </td>");
									else
										out.println("<td></td>");
						        }
								else
									out.println("<td></td>");
						     }
							else
								out.println("<td></td>");
						}
					}
					else
				    	   out.println("<td></td>");
					
					//Error Log
					//String errorfolderpath = new java.io.File(".").getCanonicalPath();
					String errorfolderpath = "E:/Projects";
					String tmperrorfullpath = errorfolderpath + "/Reports/ErrorLog/Testsuite_";
					String errorfullpath = "../ErrorLog/Testsuite_";
					
					System.out.println("Location..."+errorfolderpath+"/Reports/ErrorLog/Testsuite_"+queryvalue_testsuiteid);
					System.out.println("errorfullpath..."+errorfullpath);
					File folder2 = new File(tmperrorfullpath+queryvalue_testsuiteid);
					File[] listOfFiles2 = folder2.listFiles(); 
					if(folder2.exists()) 
					{
						for (int i = 0; i < listOfFiles2.length; i++) 
						{
							if (listOfFiles2[i].isFile()) 
							{
								files2 = listOfFiles2[i].getName();
								if (files2.endsWith(".txt") || files2.endsWith(".TXT"))
								{
									System.out.println("output..."+ i + files2);
							        errorfileNames[i] = files2;
							        if((errorfileNames[i]).contains(queryvalue_testcaseid))
							        	out.println("<td align=center><a href="+ errorfullpath+queryvalue_testsuiteid+"/" + errorfileNames[i] +" target='_blank'> <img src=../style/error.png height=30 width=25 border=0/> </a> </td>");
									else
										out.println("<td></td>");
						        }
								else
									out.println("<td></td>");
						     }
							else
								out.println("<td></td>");
						}
					}
					else
				    	   out.println("<td></td>");
					out.println("</tr>");
			}
			query_result.close();
			query_stmt.close();
			query_stmt.close();
			con.close();
			out.println("</Table>");
			out.println("</body>");
			out.println("</html>");
			//duplicate the .html file
			File source = new File(ReportName);
	        File dest = new File(Apache_ReportName);
	        FileUtils.copyFile(source, dest);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}