package main.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;

import smoke.DBConnect_Credentials;



public class Finalemail {

	public static final String SOURCE_VERSION_ID = "$Id$";
	
	// public static final Log log = LogFactory.getLog(EmailTestResults.class);
	
	String content = null;
	String mobileContent = null;
	FileWriter fw;
	LinkedHashMap<String, String[]> storyReportMap = new LinkedHashMap<String, String[]>();
	LinkedHashMap<Integer, String> storyMap = new LinkedHashMap<Integer, String>();
	LinkedHashMap<Integer, String> failedStoryMap = new LinkedHashMap<Integer, String>();
	
	// private static BufferedReader bufRdr;
		
	String jbehaveReportHtml;
	String failedStoryHtml;
	String[] customFailedReports = new String[1];
	String storyName;
	boolean storyReportToBeFetched;
	boolean totalReportToBeFetched;
	String[] storyReportTdArray;
	String[] totalReportTdArray;
	int tdCount = 0;
	int lineCount;
	int failedStoryCount = 0;
	String reportDirectory;
	int storyCount = 1;
	String generateOn;
	String iMCopyRight;
	String customReportHtml;
	String browserOnTest;
	String urlOnTest;
	String testName;
	String browserVersion = "Default";
	String totalStoriesExecuted;
	String totalScenariosExecuted;
	String totalScenariosPassed;
	String totalScenariosFailed = "0";
	String totalExecutionTime;

	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	OutputStream htmlfile, htmlfile2;
	PrintStream out;
	boolean dirFlag = false;
	String files, files2;

	int index = 0;
	String[] fileNames = new String[100];
	String[] errorfileNames = new String[100];

	Connection con;
	Statement query_stmt;
	String query_query;
	ResultSet query_result;

	int queryvalue_testsuiteid ;
	int count ;
	int queryvalue_testplanid ;
	String queryvalue_Projectname ;
	String queryvalue_browsername ;
	String queryvalue_operatingsystem ;
	String queryvalue_envid;
	String queryvalue_tcmachine;
	String queryvalue_testcaseid;
	String queryvalue_tcsummary;
	String queryvalue_testsuitename;
	String queryvalue_tcdesc;
	String queryvalue_tcstatus="pass";
	String ReportName, DB_ReportName;
	String getcss;
	int testsuite_id;
	String dbtestplan_id;
	String names;
	Date date = new Date();
	public static String decrypted;
	String colorValue;
	static Runprop prop;
	
	String directory="D:";

	/*public static void main(String args[]) throws Exception {
	
		Encryptdecrypt encrypter = new Encryptdecrypt("endryptpassword");
		
		decrypted = encrypter.decrypt(encrypter.data);

		Finalemail f = new Finalemail();

		Report_Latest r = new Report_Latest();
		
		 prop = new Runprop();
		r.Query1();
		r.startHtml();
		
		System.setProperty("java.net.preferIPv4Stack", "true"); 
		
		//runtimeEnv = new RunTimeEnv();
		String[] recipients = null;
		String subject = "Automation Test Results !!!";
		f.Query1();
		f.postMail(recipients, subject + ": ", "COEADMIN@myinfinite.com");
	}*/
	
	public Finalemail() throws Exception {

		Thread.currentThread().setContextClassLoader(
				getClass().getClassLoader());
				
				System.setProperty("java.net.preferIPv4Stack", "true"); 
	
		String[] recipients = null;
		String subject = "Automation Test Results !!!";
		Report_Latest r = new Report_Latest();
		
		 prop = new Runprop();
		r.Query1();
		r.startHtml();
		Query1();
		
		//postMail(recipients, subject + ": ",	"COEADMIN@myinfinite.com");
		
	}

	
	public void Query1() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
		System.out.println("Connection Open1");
	
		query_stmt = con.createStatement();
		query_query = "SELECT testsuite_id,testplan_id,p.project_name,release_id,iteration_id,sprint_id,browser_name,operating_system,environment_id,tc_machine FROM testlab t,projects p where t.project_id=p.project_id and username ='" + prop.user+ "' and automationflag='Yes'group by t.testsuite_id order by testsuite_id desc,username LIMIT 1;";
		System.out.println("--------------------"+query_query);
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
	
	public void postMail(String[] recipients, String subject, final String from)
			throws ClassNotFoundException, SQLException, IOException {
		int smtpportno = 25;
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", "172.16.25.54");
		props.put("mail.smtp.user", "coeadmin");
		props.put("mail.smtp.password", "lock@321");
			
		System.out.println("In Final email.....");
		props.put("mail.smtp.port", "25");
		// User Authentication Part
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from, "Automation Test Results"));
					
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(prop.REPORT_RECEIVERS_LIST));
			InternetAddress[] iAdressArray = InternetAddress.parse(prop.REPORT_RECEIVERS_LIST);
			
			message.addRecipients(Message.RecipientType.TO, iAdressArray);
					
			message.setSubject(subject);
			message.setSentDate(new Date());

			
			StringBuffer mess = new StringBuffer();
			
			String	stylepath = new java.io.File(".").getCanonicalPath();
								
			mess.append("<html>\n"
					+ "<head>\n"
					+ "<title>\n"
					+ "Test Results-"+ queryvalue_testsuiteid 
					+ "</title>\n"
					//+ "<link type=\"text/css\" rel=\"stylesheet\" href=\"../style/styles.css\">\n"
					+"<style>\n");
			String workingDir = System.getProperty("user.dir");
		   	   System.out.println("Current working directory : " + workingDir);
			
			BufferedReader reader = new BufferedReader(new FileReader(workingDir+"\\Reports\\style\\styles.css"));
			String getcss = null;
			while ((getcss = reader.readLine()) != null) {
			    
				mess.append(getcss);
				
			}
					
								
			mess.append("</style>"
					+ "<link type=\"image/x-icon\" rel=\"shortcut icon\" href=\"../style/favicon.ico\">\n"
					+ "</head>\n"
					+ "<body>\n"
					+ "<table width='100%'>\n"
					+ "<tr>\n"
					+ "<td width=\"15%\" class=\"left\">\n"
					+ "Infinite"
					+ "</td>"
					+ "<td width=70% class=\"txt\">\n"
					+ "Automation Test Execution Report"
					+ "</td>\n"
					+ "</tr>\n"
					+ "</table>\n"
					+ "<div class=\"innerheading\">\n"
					+ "</div>\n"
					+ "<Table width='100%' id=\"details\" class=\"smalltxt\">\n"
					+ "<tr>\n" + "<td>\n" + "Environment:  "
					+ queryvalue_envid
					+ "</td>\n"
					+ "<td>\n"
					+ "Project :  "
					+ queryvalue_Projectname
					+ "</td>\n"
					+ "<td>\n"
					+ "Machine :  "
					+ queryvalue_tcmachine
					+ "</td>\n"
					+ "<td>\n"
					+ "OS:  "
					+ queryvalue_operatingsystem
					+ "</td>\n"
					+ "<td>\n"
					+ "Browser :  "
					+ queryvalue_browsername
					+ "</td>\n"
					+ "<td>\n"
					+ "Date :  "
					+ dateFormat.format(date)
					+ "</td>\n"
					+ "</tr>\n"
					+ "</Table>\n"
					+ "<div class=\"innerheading\">\n"
					+ "</div>\n"
					+ "<br>\n"
					+ "</br>\n"
					+ "<table id=\"report\">\n"
					+ "<tr class=\"head\">\n"
					+ "<td align=center>\n"
					+ "S.No"
					+ "</td>\n"
					+ "<td align=center>\n"
					+ "TestSuiteID"
					+ "</td>\n"
					+ "<td align=center>\n"
					+ "TestSuiteName"
					+ "</td>\n"
					+ "<td align=center>\n"
					+ "TestCaseID"
					+ "</td>\n"
					+ "<td align=center>\n"
					+ "TC Summary"
					+ "</td>\n"
					+ "<td align=center>\n"
					+ "TC Description"
					+ "</td>\n"
					+ "<td align=center>\n"
					+ "Status"
					+ "</td>\n"
					//+ "</tr>\n" + "<tr>\n");
					+ "</tr>\n");
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
			System.out.println("Connection Open2");
		
			query_stmt = con.createStatement();
			query_query = "SELECT tl.testsuite_id,tp.tc_id, tl.testsuite_name, tl.tc_status,tl.testplan_id,tp.tc_summary,tp.tc_desc,tl.tc_status FROM testlab tl,testplan tp where tl.testplan_id = tp.testplan_id and tl.testsuite_id='" + queryvalue_testsuiteid + "' and username ='" + prop.user+ "'";
			System.out.println("******************"+query_query);
			query_result = query_stmt.executeQuery(query_query);
			while (query_result.next())
			{
				count = count+1;
				queryvalue_testsuitename = query_result.getString("tl.testsuite_name");
				queryvalue_testcaseid = query_result.getString("tp.tc_id");
				queryvalue_tcsummary = query_result.getString("tp.tc_summary");
				queryvalue_tcdesc = query_result.getString("tp.tc_desc");
				queryvalue_tcstatus = query_result.getString("tl.tc_status");
				
				if(queryvalue_tcstatus.equalsIgnoreCase("Pass")){System.out.println("-----"+queryvalue_testcaseid);
				mess.append("<tr bgcolor=#00CC66>\n");}
				else if(queryvalue_tcstatus.equalsIgnoreCase("Fail")){System.out.println("-----"+queryvalue_testcaseid);
				mess.append("<tr bgcolor=#FF6666>\n");}
				else
				mess.append("<tr>\n");				
				
				mess.append("<td align=center>\n" + count + "</td>\n"
						+ "<td align=center>\n" + queryvalue_testsuiteid
						+ "</td>\n" + "<td align=center>\n"
						+ queryvalue_testsuitename + "</td>\n"
						+ "<td align=center>\n" + queryvalue_testcaseid
						+ "</td>\n" + "<td>\n" + queryvalue_tcsummary
						+ "</td>\n" + "<td>\n" + queryvalue_tcdesc + "</td>\n");
				
				if (queryvalue_tcstatus.equalsIgnoreCase("Pass"))
					mess.append("<td align=center><font color=black>"
							+ queryvalue_tcstatus + "</font></td>");
				else if (queryvalue_tcstatus.equalsIgnoreCase("Fail"))
					mess.append("<td align=center><font color = black>"
							+ queryvalue_tcstatus + "</font></td>");
				else
					mess.append("<td align=center>" + queryvalue_tcstatus
							+ "</td>");
				
				mess.append("</tr>");
			}
			query_result.close();
			query_stmt.close();
			query_stmt.close();
			con.close();

			mess.append("</tr>\n" + "</body>\n" + "</html>");

			Zipcodeadvncd Zipcode = new Zipcodeadvncd();
	    	Zipcode.generateFileList(new File(Zipcode.SOURCE_FOLDER));
	    	Zipcode.zipIt(Zipcode.OUTPUT_ZIP_FILE);
	    	
	    	String tempfolder="D:\\Reports.zip";
	    	
	    	
	    	
	    	 
	    	 
	    	String filename = tempfolder;
					
			DataSource source = new FileDataSource(filename);	
			
			BodyPart messageBodyPart = new MimeBodyPart();
		
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			messageBodyPart.setText("Please check the attachments for test reports");
			
							
		    BodyPart htmlPart = new MimeBodyPart();
		    htmlPart.setContent(mess.toString(), "text/html");
		    
		    
		    messageBodyPart.setDataHandler(new DataHandler(source));
		    messageBodyPart.setFileName(filename);
		        
		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messageBodyPart);
		   
		    multipart.addBodyPart(htmlPart);
		   
		    message.setContent(multipart);
		        
			
			System.out.println("Log Starts..............");
			Transport transport = session.getTransport("smtp");
			transport.connect("172.16.25.54", smtpportno, "coeadmin","lock@321");
			
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			 File tempreportfolder = new File(directory+"\\Reports");
			 FileUtils.deleteDirectory(tempreportfolder);

		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}

	}

} // End of class
