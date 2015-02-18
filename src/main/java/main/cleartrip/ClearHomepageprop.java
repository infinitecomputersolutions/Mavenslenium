package main.cleartrip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ClearHomepageprop {

	public static Properties seleniumConfig = null;
	public String URL;
	public String FROM;
	public String TO;
	public String DATETXT;
	public String DATEPICKER;
	public String SEARCH;
	public String FLIGHTBOOK;
	public String ITRNRYCHECK;
	public String ITRNRYCONTINUE;
	public String EMAIL;
	public String EMAILCONTINUE;
	public String TITLE;
	public String GENDER;
	public String FNAME;
	public String LNAME;
	public String MOBILENO;
	public String TRVLRCONTINUE;

	/**
	 * Configuration file location
	 */
	public static String SELENIUM_CONFIG_FILE ;

	public ClearHomepageprop() throws FileNotFoundException, IOException {
		
		seleniumConfig = new Properties();
		String current = new java.io.File( "." ).getCanonicalPath();

		SELENIUM_CONFIG_FILE=current+"\\ObjectRepository\\cleartriphome.properties";
		
				
		seleniumConfig.load(new FileInputStream(SELENIUM_CONFIG_FILE));
		URL = seleniumConfig.getProperty("url");
		FROM = seleniumConfig.getProperty("From.id");
		TO = seleniumConfig.getProperty("To.id");
		DATETXT = seleniumConfig.getProperty("Date.txt.id");
		DATEPICKER = seleniumConfig.getProperty("Datepicker.xpath");
		SEARCH = seleniumConfig.getProperty("Search.id");
		FLIGHTBOOK = seleniumConfig.getProperty("Book.xpath");
		ITRNRYCHECK = seleniumConfig.getProperty("Checkbox.id");
		ITRNRYCONTINUE = seleniumConfig.getProperty("Continue.id");
		EMAIL = seleniumConfig.getProperty("Email.id");
		EMAILCONTINUE = seleniumConfig.getProperty("Emailcontinue.id");
		TITLE = seleniumConfig.getProperty("Nametitle.id");
		FNAME = seleniumConfig.getProperty("FName.id");
		LNAME = seleniumConfig.getProperty("Lname.id");
		MOBILENO = seleniumConfig.getProperty("MobileNo.id");
		TRVLRCONTINUE = seleniumConfig.getProperty("trvlrcont.id");
		GENDER = seleniumConfig.getProperty("Gender");
		
			}
}
