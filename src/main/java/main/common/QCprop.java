package main.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class QCprop {

	public static Properties seleniumConfig = null;
	public String URL;
	public String USRNAME;
	public String DOMAIN;
	public String PROJECT;
	public String TESTFOLDER;
	public String TESTSET;
	public String TESTSETID;
	public Integer TESTID;
	

	/**
	 * Configuration file location
	 */
	public static String SELENIUM_CONFIG_FILE ;

	public QCprop() throws FileNotFoundException, IOException {
		
		seleniumConfig = new Properties();
		String current = new java.io.File( "." ).getCanonicalPath();

		SELENIUM_CONFIG_FILE=current+"\\Config\\qcconnection.properties";
		
				
		seleniumConfig.load(new FileInputStream(SELENIUM_CONFIG_FILE));
		
		URL = seleniumConfig.getProperty("qcurl");
		USRNAME = seleniumConfig.getProperty("qcusrname");
		DOMAIN = seleniumConfig.getProperty("qcdomain");
		PROJECT = seleniumConfig.getProperty("qcproject");
		TESTFOLDER = seleniumConfig.getProperty("testFolder");
		TESTSET = seleniumConfig.getProperty("testSet");
		TESTSETID = seleniumConfig.getProperty("testSetID");
		TESTID = Integer.parseInt(TESTSETID);
		
		
			}
}
