package functional;

import main.cleartrip.CleartripHomePage;
import main.common.Driverpage;
import main.common.Runprop;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class TC_001 {

	private CleartripHomePage homepage;
	private Runprop property;
	public WebDriver driver;

	@Test
	public void example() throws Exception {

		Driverpage driverpage = new Driverpage();
		// driver = driverpage.getDriver(property.BROWSER);
		property = new Runprop();
		driver = driverpage.getDriver(property.BROWSER);
		System.out.println("****" + property.BROWSER);
		homepage = new CleartripHomePage(driver);

		homepage.openurl();
		homepage.searchflights();
		// Thread.sleep(3000);
		homepage.bookflight();
		homepage.email();
		homepage.travellers();
		homepage.quit();

	}

}
