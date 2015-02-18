package smoke;



import main.common.Driverpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class NegativeScenario 
{
public WebDriver driver;
	@Test
	public void f() throws Exception {

		Driverpage driverpage = new Driverpage();
		
		driver = driverpage.getDriver("FIREFOX");
		
		final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
		System.setProperty(ESCAPE_PROPERTY, "false");
		
		driver.get("http://www.google.com/");

		driver.findElement(By.id("wrwer")).click();

		driver.quit();

	}
}
