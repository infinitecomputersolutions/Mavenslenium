package smoke;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class Mailtest {
	
	
	
	@Test
	public void test(){
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		WebDriver driver = new FirefoxDriver(dc);
		driver.manage().window().maximize();
	
		
		driver.get("http://www.google.com");
		
		driver.quit();
		
	}

}
