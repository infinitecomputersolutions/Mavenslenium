package main.wordpress;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class WordpressHomepageprop {

	public static Properties seleniumConfig = null;
	public String URL;
	public String LOGIN;
	public String PWD;
	public String SUBMIT;
	public String ERRORMSG;
	
	/**
	 * Configuration file location
	 */
	public static String SELENIUM_CONFIG_FILE ;

	public WordpressHomepageprop() throws FileNotFoundException, IOException {
		
		seleniumConfig = new Properties();
		String current = new java.io.File( "." ).getCanonicalPath();

		SELENIUM_CONFIG_FILE=current+"\\ObjectRepository\\wordpresshome.properties";
		
				
		seleniumConfig.load(new FileInputStream(SELENIUM_CONFIG_FILE));
		URL = seleniumConfig.getProperty("url");
		LOGIN = seleniumConfig.getProperty("login.id");
		PWD = seleniumConfig.getProperty("pwd.id");
		SUBMIT = seleniumConfig.getProperty("submit.id");
		ERRORMSG = seleniumConfig.getProperty("errormsg.xpath");
		
		
		
			}
}
