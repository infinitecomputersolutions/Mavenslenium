package main.bigace;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BigaceHomepageprop {

	public static Properties seleniumConfig = null;
	public String URL;
	public String LOGIN;
	public String USRNAME;
	public String PWD;
	public String SUBMIT;
	

	/**
	 * Configuration file location
	 */
	public static String SELENIUM_CONFIG_FILE ;

	public BigaceHomepageprop() throws FileNotFoundException, IOException {
		
		seleniumConfig = new Properties();
		String current = new java.io.File( "." ).getCanonicalPath();

		SELENIUM_CONFIG_FILE=current+"\\ObjectRepository\\bigacehome.properties";
		
				
		seleniumConfig.load(new FileInputStream(SELENIUM_CONFIG_FILE));
		URL = seleniumConfig.getProperty("url");
		LOGIN = seleniumConfig.getProperty("loginlink.xapth");
		USRNAME = seleniumConfig.getProperty("username.id");
		PWD = seleniumConfig.getProperty("Password.id");
		SUBMIT = seleniumConfig.getProperty("submit.classname");
		
		
			}
}
