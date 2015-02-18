package main.common;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Driverpage {

	public WebDriver driver;
	
	 private static HashMap<Long, WebDriver> map = new HashMap<Long, WebDriver>();
		
		public static WebDriver getDriverInstance() {
			WebDriver d = map.get(Thread.currentThread().getId());
			return d;
		}

	public WebDriver getDriver(String browser) throws Exception {

		switch (browser.toUpperCase()) {
		case "IE": {
			System.out.println("done  for ie");
			String current = new java.io.File(".").getCanonicalPath();
			File file = new File(
					current
							+ "\\Drivers\\IEDriverServer_x64_2.35.3\\IEDriverServer.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities
					.internetExplorer();
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			ieCapabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			ieCapabilities.setJavascriptEnabled(true);
			WebDriver driver = new InternetExplorerDriver(ieCapabilities);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			map.put(Thread.currentThread().getId(), driver);
			return driver;

		}

		case "FIREFOX": {
			System.out.println("Done for ff");
			DesiredCapabilities dc = DesiredCapabilities.firefox();
			WebDriver driver = new FirefoxDriver(dc);
			driver.manage().window().maximize();
			map.put(Thread.currentThread().getId(), driver);
			return driver;
		}
		case "CHROME": {
			System.out.println("Done for chrome");
			String current = new java.io.File(".").getCanonicalPath();
			File file = new File(current
					+ "\\Drivers\\chromedriver_win32\\chromedriver.exe");
			DesiredCapabilities capability = DesiredCapabilities.chrome();
			System.setProperty("webdriver.chrome.driver",
					file.getAbsolutePath());
			WebDriver driver = new ChromeDriver(capability);
			driver.manage().window().maximize();
			map.put(Thread.currentThread().getId(), driver);
			return driver;
		}
		}
		return driver;
	}

}
