package main.common;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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

import main.common.Runprop;


public class EmailTestResultsGeneric  {

	public static final String SOURCE_VERSION_ID = "$Id$";
	//public static final Log log = LogFactory.getLog(EmailTestResults.class);
	String content = null;
	String mobileContent = null;
	FileWriter fw;
	LinkedHashMap<String, String[]> storyReportMap = new LinkedHashMap<String, String[]>();
	LinkedHashMap<Integer, String> storyMap = new LinkedHashMap<Integer, String>();
	LinkedHashMap<Integer, String> failedStoryMap = new LinkedHashMap<Integer, String>();
	//private static BufferedReader bufRdr;
	private static Runprop runtimeEnv;
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

	public static void main(String args[]) throws ClassNotFoundException, IOException, SQLException{
	/*public EmailTestResultsGeneric() throws Exception {*/
/*
		Thread.currentThread().setContextClassLoader(
				getClass().getClassLoader());*/
		EmailTestResultsGeneric  e= new EmailTestResultsGeneric();
		runtimeEnv = new Runprop();
		String[] recipients = null;
		String subject = "Automation Test Results !!!";
		e.postMail(recipients, subject + ": ","naga.jyothirmaya@infinite.com");
	}

	public void postMail(String[] recipients, String subject, final String from)
			throws FileNotFoundException, UnsupportedEncodingException {
		int smtpportno=Integer.parseInt("25");
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host","webmail.infinite.com");
		props.put("mail.smtp.user", "nagaj");
		props.put("mail.smtp.password",	"Chakri@593");
		props.put("mail.smtp.port", "25");
		//User Authentication Part 
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);		
		
		
		try {
			MimeMessage message = new MimeMessage(session);	
			
			message.setFrom(new InternetAddress(from,"Automation Test Results"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("Muralikrishnan.Balasubramanian@infinite.com"));
			message.setSubject(subject);
			message.setSentDate(new Date());
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setText("Please check the attachments for test reports");
			
			Multipart multipart = new MimeMultipart();
					
			multipart.addBodyPart(messageBodyPart);
			
			messageBodyPart = new MimeBodyPart();		
			
			

			String filename=runtimeEnv.TEST_REPORTS_PATH;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);					
			message.setContent(multipart);
			
			Transport transport = session.getTransport("smtp");
			transport.connect("webmail.infinite.com", smtpportno, from,"cHakri@593");
			//transport.connect(runtimeEnv.SMTP_HOST_NAME, from,runtimeEnv.REPORT_SENDER_MAIL_PASSWORD);
//			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			System.out.println("**********");
			
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}

		

	}	

} // End of class
