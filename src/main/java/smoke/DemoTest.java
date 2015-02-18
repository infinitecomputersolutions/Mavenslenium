package smoke;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class DemoTest {
	
 @Test
  public void f() throws InterruptedException {
	  
	  DesiredCapabilities dc = DesiredCapabilities.firefox(); 
		WebDriver driver = new FirefoxDriver(dc);
		driver.manage().window().maximize();
		
		
		driver.get("http://www.google.com");
		
		driver.findElement(By.name("q")).click();
		
		driver.findElement(By.name("q")).sendKeys("Selenium");
		
		Thread.sleep(3000);
		
		driver.quit();
	  
 /* public static void main(String[] args){
	  
	  String userpath = System.getProperty("user.home");
	  
	  System.out.println("User path"+userpath);
  }*/
}
}