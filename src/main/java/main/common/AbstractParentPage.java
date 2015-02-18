package main.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

public class AbstractParentPage {

	public int DRIVER_WAIT = 120; // 120 seconds
	public WebDriver driver;
	public StringBuffer verificationErrors = new StringBuffer();
	public static Connection con = null;
	public Statement statement = null;
	public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;
	public String strWriteResultstoDB = "Y";
	public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	public DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
	public DateFormat dateFormat3 = new SimpleDateFormat("MM/dd/yyyy");
	public Calendar cal = Calendar.getInstance();
	public Integer errorno;
	public String pageTitle = "portletTitle";
	public String podFieldSetTitle1 = "ui-fieldset-legend ui-corner-all ui-state-default";
	public String providerbg = "providerbg";
	public String errorMsgClass = "errorMessage";
	public String podTitle = "podTitle";
	public String infoTitle = "infoTitle";
	public String dataScrollerText = "dataScrollerText";
	public String moreInfoBar = "moreInfoBar";
	public String moreInfo = "moreInfo";
	public String padb = "padb";
	public String podbg = "podbg";
	public static String scriptName = null;
	public String portletmsginfo = "portlet-msg-info";
	public List<String> failValues = new ArrayList<String>();
	public static List<String> Val = new ArrayList<String>();
	public static int i = 1;

	/**
	 * 
	 * <p>
	 * Loading a Ajax functionality
	 * <P>  
	 * Description 
	 * <p>
	 * This Constructor will loading a Ajax functionality and connect to the Database
	
	 * @param  driver an instance of WebDriver
	 * @throws SQLException
	 */

	public AbstractParentPage(WebDriver driver) throws SQLException {
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,
				DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		// driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		this.driver = driver;

		if (con == null) {
			try {
				ConnectToMySQLAccess();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				con = getConnection("172.16.16.109", "root", "raghu1976%",
						"Liberty");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/** 
	 * <p>
	 * Inserts Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will insert a record in DB , detailing  
	 * step performed, verification details of the test case
	 * result of the test case and time of execution   
	 * 
	 * @param  tcid            Id of the Test case 
	 * @param  Stepdesc        Description of the test step that is going to be performed 
	 * @param  Verification    Verification performed
	 * @param  Result		   Result of Test case
	 * @param  Stamp		   Display current time
	 */
	
	
	public void writetestresult(String tcid, String Stepdesc,
			String Verification, String Result, String Stamp) throws Exception {
		Val.clear();
		scriptName = tcid;
		System.out.println(tcid);
		PreparedStatement stm = con.prepareStatement(String.format(
				"insert into results values('%s','%s','%s','%s','%s')", tcid,
				Stepdesc, Verification, Result, Stamp));
		System.out.println(stm);
		stm.executeUpdate();
		Reporter.log("<table border='1' bordercolor='Peru' bgcolor='LightGray'><tr><td><font face='cursive' size='4'>");
	}

	/** 
	 * <p>
	 *  Waits for a page to load.
	 * <P>  
	 * Description 
	 * <p>
	 * if you want to wait for a page to load, you must wait immediately after a Selenium command that caused a page-load.
	 */
	
	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		Wait<WebDriver> wait = new WebDriverWait(this.driver, 100);
		try {
			wait.until(expectation);
			System.out.println("In waitForPageLoaded ");
		} catch (Throwable error) {
			System.out
					.println("Timeout waiting for Page Load Request to complete.");
		}
	}

	/** 
	 * <p>
	 *  To handle popup window in WebDriver
	 * <P>  
	 * Description 
	 * <p>
	 * This method will handle the popup window by using vbscript   
	 */
	
	public void handlepopup() throws InterruptedException, IOException {
		Thread.sleep(500);
		Runtime.getRuntime().exec("wscript.exe D:\\auto.vbs");
		Thread.sleep(500);
		isProcessRunning("auto.vbs");
	}
	
	/** 
	 * <p>
	 *  To handle popup window in WebDriver
	 * <P>  
	 * Description 
	 * <p>
	 * This method will handle the popup window by using vbscript   
	 */
	

	public void handlepopup1() throws InterruptedException, IOException {
		Thread.sleep(500);
		Runtime.getRuntime().exec("wscript.exe D:\\auto1.vbs");
		Thread.sleep(500);
		isProcessRunning("auto1.vbs");
		waitForPageLoaded();
	}

	/** 
	 * <p>
	 *  To verify process running or not in webdriver
	 * <P>  
	 * Description 
	 * <p>
	 * This method will be verify process running or not 
	 * 
	 *  @param  processName   name of the process
	 *  @return   true if the process running, otherwise false
	 */
	
	public static boolean isProcessRunning(String processName) {
		try {
			String line;
			Process p = Runtime.getRuntime().exec(
					"tasklist /FI \"IMAGENAME eq " + processName + "\"");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (line.contains(processName)) {
					input.close();
					return true;

				}
			}
			input.close(); // the process was not found

		} catch (Exception err) {
			err.printStackTrace();

		}
		return false;
	}

	/** 
	 * <p>
	 *  load the MySQL driver.
	 * <P>  
	 * Description 
	 * <p>
	 * This method will load the MySQL driver
	 * 
	 */
	
	public void ConnectToMySQLAccess() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println(" Driver Found.");
		} catch (ClassNotFoundException e) {
			System.out.println(" Driver Not Found, exiting..");
			throw (e);
		}
	}
	
	/** 
	 * <p>
	 *  Attempts to establish a connection to the given database URL
	 * <P>  
	 * Description 
	 * <p>
	 * The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
	 * 
	 * @param host   IP address
	 * @param userDB username of Database
	 * @param passDB password of Database
	 * @param database name of the Database
	 */

	public Connection getConnection(String host, String userDB, String passDB,
			String database) throws Exception {

		String url = "";
		try {
			url = "jdbc:mysql://" + host + "/" + database;
			con = DriverManager.getConnection(url, userDB, passDB);
			System.out.println("Connection Open");
		} catch (java.sql.SQLException e) {
			throw (e);
		}
		return con;
	}

	/** 
	 * <p>
	 *  selects Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will select a record in DB , detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @return it returns resultSet
	 */
	
	public ResultSet returndata(String testdata) throws Exception {

		PreparedStatement stm = con.prepareStatement(String.format(
				"Select * from %s", testdata));
		System.out.println(stm);
		return resultSet = stm.executeQuery();

	}

	/** 
	 * <p>
	 *  selects Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will select a record in DB with condition , detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param testfield  name of the condition
	 * @param fieldval  value of the condition
	 * @return it returns resultSet
	 */
	
	public ResultSet returndata(String testdata, String testfield,
			String fieldval) throws Exception {
		PreparedStatement stm = con.prepareStatement(String
				.format("Select * from %s where %s='%s'", testdata, testfield,
						fieldval));
		System.out.println(stm);
		return resultSet = stm.executeQuery();
	}

	/** 
	 * <p>
	 *  selects Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will select a record in DB with conditions , detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param testfield  name of the condition1
	 * @param fieldval  value of the condition1
	 * @param testfield1  name of the condition2
	 * @param fieldval1  value of the condition2
	 * @return it returns resultSet
	 */
	
	public ResultSet returndata(String testdata, String testfield,
			String fieldval, String testfield1, String fieldval1)
			throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"Select * from %s where %s='%s' and %s='%s'", testdata,
				testfield, fieldval, testfield1, fieldval1));
		System.out.println(stm);
		return resultSet = stm.executeQuery();
	}
	
	/** 
	 * <p>
	 *  updates Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will update a record in DB with condition, detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param testfield  name of the column
	 * @param fieldval  value of the column
	 * @param testfield1  name of the condition1
	 * @param fieldval1  value of the condition1
	 */

	public void updatedata(String testdata, String testfield, String fieldval,
			String testfield1, String fieldval1) throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"update  %s set " + testfield + "='%s' where %s='%s'", testdata,
				fieldval, testfield1, fieldval1));
		System.out.println(stm);
		stm.executeUpdate();
	}
	
	/** 
	 * <p>
	 *  updates Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will update a record in DB with conditions, detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param coloumn1  name of the column
	 * @param coloumn2  name of the column1
	 * @param testfield1  value of the column1
	 * @param testfield2  value of the column2
	 * @param wfield  name of the condition1
	 * @param wvalue  value of the condition1
	 */

	public void updatedata(String testdata, String coloumn1, String coloumn2,
			String testfield1, String testfield2, String wfield, String wvalue)
			throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"update  %s set " + coloumn1 + "='%s'," + coloumn2
						+ "='%s' where %s='%s'", testdata, testfield1,
				testfield2, wfield, wvalue));
		System.out.println(stm);
		stm.executeUpdate();
	}

	/** 
	 * <p>
	 *  updates Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will update a record in DB with conditions, detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param coloumn1  name of the column
	 * @param coloumn2  name of the column1
	 * @param testfield1  value of the column1
	 * @param testfield2  value of the column2
	 * @param wfield  name of the condition1
	 * @param wvalue  value of the condition1
	 * @param wfield1  name of the condition2
	 * @param wvalue1  value of the condition2
	 */

	public void updatedata(String testdata, String coloumn1, String coloumn2,
			String testfield1, String testfield2, String wfield, String wvalue,
			String wfield1, String wvalue1) throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"update  %s set " + coloumn1 + "='%s'," + coloumn2
						+ "='%s' where %s='%s' and %s='%s'", testdata,
				testfield1, testfield2, wfield, wvalue, wfield1, wvalue1));
		System.out.println(stm);
		stm.executeUpdate();
	}

	/** 
	 * <p>
	 *  updates Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will update a record in DB with conditions, detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param coloumn1  name of the column1
	 * @param coloumn2  name of the column2
	 * @param coloumn3  name of the column3
	 * @param testfield1  value of the column1
	 * @param testfield2  value of the column2
	 * @param testfield3  value of the column3
	 * @param wfield  name of the condition1
	 * @param wvalue  value of the condition1
	 * @param wfield1  name of the condition2
	 * @param wvalue1  value of the condition2
	 */
	
	public void updatedata(String testdata, String coloumn1, String coloumn2,
			String coloumn3, String testfield1, String testfield2,
			String testfield3, String wfield, String wvalue, String wfield1,
			String wvalue1) throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"update  %s set " + coloumn1 + "='%s'," + coloumn2 + "='%s',"
						+ coloumn3 + "='%s' where %s='%s' and %s='%s'",
				testdata, testfield1, testfield2, testfield3, wfield, wvalue,
				wfield1, wvalue1));
		System.out.println(stm);
		stm.executeUpdate();
	}

	/** 
	 * <p>
	 *  updates Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will update a record in DB with conditions, detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param testfield  value of the column1
	 * @param wfield  name of the condition1
	 * @param wvalue  value of the condition1
	 */
	
	public void updatedata(String testdata, String testfield, String wfield,
			String wvalue) throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"update  %s set used='%s' where %s='%s'", testdata, testfield,
				wfield, wvalue));
		stm.executeUpdate();
	}

	/** 
	 * <p>
	 *  updates Test Results into Test DataBase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will update a record in DB with conditions, detailing  
	 * step performed, verification details of the test case
	 * 
	 * @param testdata  name of the table
	 * @param testfield  value of the column1
	 * @param wfield  name of the condition1
	 * @param wvalue  value of the condition1
	 * @param wfield1  name of the condition2
	 * @param wvalue1  value of the condition2
	 */
	
	public void updatedata(String testdata, String testfield, String wfield,
			String wvalue, String wfield1, String wvalue1) throws Exception {
		PreparedStatement stm = con.prepareStatement(String.format(
				"update  %s set used='%s' where %s='%s' and %s='%s'", testdata,
				testfield, wfield, wvalue, wfield1, wvalue1));
		System.out.println(stm);
		stm.executeUpdate();
	}

	/** 
	 * <p>
	 *  To verify the expected value by using Class,CSS,ID,XPATH
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the expected value by using either Class,CSS,ID and XPATH 
	 * 
	 * @param verification  expected value
	 * @param ID  name of the element on the page with Class or CSS or ID or XPATH
	 */
	
	// Method to verify error messages
	public void verify_error_msg(String verification, String ID)
			throws Exception {
		waitForPageLoaded();
		String[] values = verification.split("\\.");
		for (String value : values) {
			boolean result = false;

			if (lookForClass(ID)) {
				List<WebElement> elements = driver.findElements(By
						.className(ID));
				for (WebElement element : elements) {
					if (element.getText().contains(value)) {
						result = true;
						break;
					}
				}

			} else if (lookForCSS(ID)) {
				List<WebElement> elements = driver.findElements(By
						.cssSelector(ID));
				for (WebElement element : elements) {
					if (element.getText().contains(value)) {
						result = true;
						break;
					}
				}

			} else if (lookForID(ID)) {
				List<WebElement> elements = driver.findElements(By.id(ID));
				for (WebElement element : elements) {
					if (element.getText().contains(value)) {
						result = true;
						break;
					}
				}

			} else if (lookForXpath(ID)) {
				List<WebElement> elements = driver.findElements(By.xpath(ID));
				for (WebElement element : elements) {
					if (element.getText().contains(value)) {
						result = true;
						break;
					}
				}
			} else {

				Reporter.log("Required Error Message --><FONT COLOR='red'>"
						+ value + " </FONT> ---is not displayed");
				String userHome = System.getProperty("user.dir");
				String screenShotsFolder = userHome
						+ "\\Test-Failure-Screenshots"
						+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
						+ dateFormat.format(cal.getTime()).replace(":", "_")
						+ "\\";
				String filename = screenShotsFolder + scriptName + "_" + (i++)
						+ ".png";

				File screenshot = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(screenshot, new File(filename));
				} catch (IOException e) {
					e.printStackTrace();
				}

				Reporter.log("<img src=\"file:///" + filename + "\""
						+ "height=\"600\" width=\"600\"" + " />");
				Val.add("Verification Failed");
				break;
			}
			if (result) {
				Reporter.log("<FONT COLOR='ForestGreen'><b>ErrorMessage Pass ---------></b></FONT>"
						+ value + "." + "<br>");
			} else {

				failValues.add(value);
				Val.add(value);
				Reporter.log("<FONT COLOR='red'>ErrorMessage Fail --------->"
						+ value + ".</FONT>" + "<br>");
			}

		}
		if (failValues.size() > 0) {
			String screenShotsFolder = null;
			String filename = null;
			if (lookForClass(ID)) {
				List<WebElement> elements = driver.findElements(By
						.className(ID));
				WebElement element1 = null;
				for (WebElement element : elements) {
					if (!element.equals(element1)) {
						Reporter.log(element.getText() + "<br/>");
					}
					element1 = element;
				}
			} else if (lookForCSS(ID)) {
				List<WebElement> elements = driver.findElements(By
						.cssSelector(ID));
				for (WebElement element : elements) {
					Reporter.log(element.getText() + "<br/>");
				}
			} else if (lookForID(ID)) {
				List<WebElement> elements = driver.findElements(By.id(ID));
				for (WebElement element : elements) {
					Reporter.log(element.getText() + "<br/>");
				}
			} else if (lookForXpath(ID)) {
				List<WebElement> elements = driver.findElements(By.xpath(ID));
				for (WebElement element : elements) {
					Reporter.log(element.getText() + "<br/>");
				}
			}
			String userHome = System.getProperty("user.dir");
			filename = "";
			screenShotsFolder = userHome + "\\Test-Failure-Screenshots"
					+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
					+ dateFormat.format(cal.getTime()).replace(":", "_") + "\\";
			filename = screenShotsFolder + scriptName + "_" + (i++) + ".png";

			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Reporter.log("<br><br><img src=\"file:///" + filename + "\""
					+ "height=\"600\" width=\"600\"" + " /><br><br>");

			failValues.clear();
		}
	}

	/** 
	 * <p>
	 *  To verify the unexpected error by using Class,CSS,ID,XPATH
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the unexpected error by using either Class,CSS,ID and XPATH 
	 * 
	 * @param id  name of the element on the page with Class or CSS or ID or XPATH
	 */
	
	public void unexpectedError(String id) throws Exception {
		String unexcepted = "NO";
		if (lookForClass(id)) {
			unexcepted = "YES";
			Reporter.log("UnExpected Error Message :--<FONT COLOR='red'>"
					+ driver.findElement(By.className("id")).getText()
					+ "</FONT>");
		} else if (lookForID(id)) {
			unexcepted = "YES";
			Reporter.log("UnExpected Error Message :--<FONT COLOR='red'>"
					+ driver.findElement(By.id(id)).getText() + "</FONT>");
		} else if (lookForCSS(id)) {
			unexcepted = "YES";
			Reporter.log("UnExpected Error Message :--<FONT COLOR='red'>"
					+ driver.findElement(By.cssSelector(id)).getText()
					+ "</FONT>");
		}
		if (unexcepted.equalsIgnoreCase("YES")) {
			String userHome = System.getProperty("user.dir");
			String screenShotsFolder = userHome + "\\Test-Failure-Screenshots"
					+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
					+ dateFormat.format(cal.getTime()).replace(":", "_") + "\\";

			String filename = screenShotsFolder + scriptName + "_" + (i++)
					+ ".png";

			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Reporter.log("<img src=\"file:///" + filename + "\""
					+ "height=\"600\" width=\"600\"" + " />");
			filename = "";
			tearDown();
			assert false : "Unexpected Error";
		}
	}

	// Method for fields not to be present
	
	/** 
	 * <p>
	 *  To verify the fieldSet Fields are present or NotPresent by using id
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the fieldSet Fields are present or NotPresent by using id
	 * 
	 * @param fieldSetID  name of the element on the page with Class or CSS or ID or XPATH
	 * @param verify  expected value
	 */

	public void verify_fieldSetFieldsNotPresent(String fieldSetID, String verify)
			throws Exception {
		if (!verify.isEmpty()) {
			waitForPageLoaded();
			String[] values = verify.split("\\.");
			List<String> failValues = new ArrayList<String>();
			for (String value : values) {
				if (lookForID(fieldSetID)) {
					if (driver.findElement(By.id(fieldSetID)).getText()
							.contains(value)) {
						failValues.add(value);
					} else {
						// value is invalid
						Reporter.log("<FONT COLOR='ForestGreen'><b>PASS"
								+ "---></b></FONT>" + "Field Not Present--->"
								+ "\t" + value + "<br>");
					}
				}
			}
			if (failValues.size() > 0) {
				for (String failValue : failValues) {
					Reporter.log("<FONT COLOR='red'>Fail --------->Field Present--->"
							+ failValue + ".</FONT>" + "<br>");
				}
				Reporter.log(driver.findElement(By.id(fieldSetID)).getText());
				String userHome = System.getProperty("user.dir");
				String screenShotsFolder = userHome
						+ "\\Test-Failure-Screenshots"
						+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
						+ dateFormat.format(cal.getTime()).replace(":", "_")
						+ "\\";
				String filename = screenShotsFolder + scriptName + "_" + (i++)
						+ ".png";
				File screenshot = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(screenshot, new File(filename));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Reporter.log("<img src=\"file:///" + filename + "\""
						+ "height=\"600\" width=\"600\"" + " />");
				filename = "";
			}
		}
	}

	/** 
	 * <p>
	 *  To verify the values in Drop down field
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify values in Drop down field by using either xpath,id,class name,css
	 * 
	 * @param ID  name of the element on the page with Class or CSS or ID or XPATH
	 * @param Values  expected value
	 */
	
	public void verifyDropdown(String ID, String Values) throws Exception {
		waitForPageLoaded();
		if (lookForXpath(ID)) {
			List<String> failValues = new ArrayList<String>();
			driver.findElement(By.xpath(ID)).click();
			WebElement dropdown = driver.findElement(By.xpath(ID));
			Select select = new Select(dropdown);
			List<WebElement> options = select.getOptions();
			String[] values = Values.split("\\.");
			for (String value : values) {
				boolean verify = false;
				for (WebElement we : options) {
					if (we.getText().contains(value)) {
						verify = true;
						break;
					}
				}
				if (verify) {
					Reporter.log("<FONT COLOR='ForestGreen'><b>Pass----></b></FONT>"
							+ value + "<br/>");
				} else {
					failValues.add(value);
				}

			}
			if (failValues.size() > 0) {
				for (String failValue : failValues) {
					Reporter.log("<FONT COLOR='red'>Fail --------->"
							+ failValue + ".</FONT>" + "<br>");
				}
				Reporter.log(driver.findElement(By.xpath(ID)).getText());
				String userHome = System.getProperty("user.dir");
				String screenShotsFolder = userHome
						+ "\\Test-Failure-Screenshots"
						+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
						+ dateFormat.format(cal.getTime()).replace(":", "_")
						+ "\\";
				String filename = screenShotsFolder + scriptName + "_" + (i++)
						+ ".png";
				File screenshot = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(screenshot, new File(filename));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Reporter.log("<img src=\"file:///" + filename + "\""
						+ "height=\"600\" width=\"600\"" + " />");
				filename = "";
			}
		}
	}

	/** 
	 * <p>
	 *  To verify the success message by using either Class name,CSS selector,ID,XPATH
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify success message by using either Class name,CSS selector,ID,XPATH
	 * 
	 * @param value  expected value
	 */
	
	public void verifyMessage(String value) throws Exception {

		waitForPageLoaded();
		String names[] = { "msgBox", "msgbox" };

		for (String name : names) {
			boolean result = false;
			if (lookForClass(name)) {
				if (driver.findElement(By.className(name)).getText()
						.contains(value)) {
					result = true;
				}

				if (result) {
					Reporter.log("Pass" + "--->\t" + "successMessage--->"
							+ "\t" + value + "<br>");
					break;
				} else {
					Reporter.log("<FONT COLOR='red'>Fail" + "--->\t"
							+ "successMessage--->" + "\t" + value + "</FONT>");
					Val.add(value);
					Reporter.log(driver.findElement(By.className(name))
							.getText());
					String userHome = System.getProperty("user.dir");
					String screenShotsFolder = userHome
							+ "\\Test-Failure-Screenshots"
							+ dateFormat1.format(cal.getTime())
							+ "\\"
							+ scriptName
							+ "_Fail_Message_"
							+ dateFormat.format(cal.getTime())
									.replace(":", "_") + "\\";
					String filename = screenShotsFolder + scriptName + "_"
							+ (i++) + ".png";
					File screenshot = ((TakesScreenshot) driver)
							.getScreenshotAs(OutputType.FILE);
					try {
						FileUtils.copyFile(screenshot, new File(filename));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Reporter.log("<img src=\"file:///" + filename + "\""
							+ "height=\"600\" width=\"600\"" + " />");
					filename = "";
					// assert false : "Verification Failed";
				}
			}
		}
	}
	
	/** 
	 * <p>
	 * Subtract years from current date
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to Subtract years from current date
	 * 
	 * @param noofyers  number of years to subtract from the current date
	 * @return date
	 */

	public String date_fill(int noofyers) {

		Calendar calt = new GregorianCalendar();

		calt.add(Calendar.YEAR, -noofyers);
		String DD = null, MM = null;
		int month = calt.get(Calendar.MONTH);
		month = month + 1;
		int date = calt.get(Calendar.DAY_OF_MONTH);
		if (date < 10) {
			DD = "0" + date;
		} else {
			DD = Integer.toString(date);
		}
		if ((month) < 10) {
			MM = "0" + month;
		} else {
			MM = Integer.toString(month);
		}
		String tYears = "";

		tYears = MM + "/" + DD + "/" + calt.get(Calendar.YEAR);

		return tYears;

	}

	/** 
	 * <p>
	 * Add years from the current date
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to Add years from the current date
	 * 
	 * @param noofyers  number of years to add from the current date
	 * @return date
	 */
	
	

	public String date_add(int noofyers) {

		Calendar calt = new GregorianCalendar();

		calt.add(Calendar.YEAR, noofyers);
		String DD = null, MM = null;
		int month = calt.get(Calendar.MONTH);
		month = month + 1;
		int date = calt.get(Calendar.DAY_OF_MONTH);
		if (date < 10) {
			DD = "0" + date;
		} else {
			DD = Integer.toString(date);
		}
		if ((month) < 10) {
			MM = "0" + month;
		} else {
			MM = Integer.toString(month);
		}
		String tYears = "";

		tYears = MM + "/" + DD + "/" + calt.get(Calendar.YEAR);

		return tYears;

	}

	/** 
	 * <p>
	 * Add days from the current date
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to Add day from the current date
	 * 
	 * @param noofDays  number of years to add from the current date
	 * @return date
	 */
	
	
	public String date_addDays(int noofDays) {

		Calendar cal = Calendar.getInstance();
		Date x = new Date();
		cal.setTime(x);
		cal.add(Calendar.DATE, 10);
		return dateFormat3.format(cal.getTime());

	}
	
	/** 
	 * <p>
	 *  To verify the expected data is present or not by using Class,CSS,ID,XPATH
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the expected data is present or not by using Class,CSS,ID,XPATH
	 * 
	 * @param how  name of the element on the page with Class or CSS or ID or XPATH
	 * @param verification  expected value
	 */

	// verify required data is present or not
	public void verify_Data(String how, String verification) throws Exception {
		waitForPageLoaded();
		String[] values = verification.split("\\.");
		List<String> failValues = new ArrayList<String>();
		for (String value : values) {

			boolean result = false;
			if (how.isEmpty()) {
				if (driver.findElement(By.linkText(value)).isDisplayed()) {
					result = true;
					break;
				}
			} else if (lookForXpath(how)) {
				if (driver.findElement(By.xpath(how)).getText().contains(value)) {
					result = true;
				}
			} else if (lookForClass(how)) {
				List<WebElement> elements = driver.findElements(By
						.className(how));
				for (WebElement element : elements) {
					if (element.getText().contains(value)) {
						result = true;
						break;
					}
				}
			} else if (lookForCSS(how)) {
				if (driver.findElement(By.cssSelector(how)).getText()
						.contains(value)) {
					result = true;
				}
			} else if (lookForID(how)) {
				if (driver.findElement(By.id(how)).getText().contains(value)) {
					result = true;
				}
			}

			if (result) {
				Reporter.log("<FONT COLOR='ForestGreen'><b>Pass"
						+ "---></b></FONT>" + "\t" + value + "<br>");
			} else {
				failValues.add(value);
				Val.add(value);
			}
		}
		if (failValues.size() > 0) {
			for (String failValue : failValues) {
				Reporter.log("<FONT COLOR='red'>Fail --------->" + failValue
						+ "." + "</FONT><br>");
			}

			String userHome = System.getProperty("user.dir");
			String screenShotsFolder = userHome + "\\Test-Failure-Screenshots"
					+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
					+ dateFormat.format(cal.getTime()).replace(":", "_") + "\\";
			String filename = screenShotsFolder + scriptName + "_" + (i++)
					+ ".png";
			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Reporter.log("<img src=\"file:///" + filename + "\""
					+ "height=\"600\" width=\"600\"" + " />");
			filename = "";

		}
	}
	
	/** 
	 * <p>
	 *  To verify the Closed Functionality by using Class,CSS,ID,XPATH
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the Closed Functionality by using Class,CSS,ID,XPATH
	 * 
	 * @param Id  name of the element on the page with Class or CSS or ID or XPATH
	 * @param desc  Description of the test step that is going to be performed 
	 */

	public void verify_Cancel(String Id, String desc) {
		waitForPageLoaded();
		boolean result = false;
		if (lookForXpath(Id)) {
			result = true;
		} else if (lookForCSS(Id)) {
			result = true;
		} else if (lookForClass(Id)) {
			result = true;
		} else if (lookForClass(Id)) {
			result = true;
		}
		if (result) {
			Reporter.log("<FONT COLOR='red'>Fail----->" + desc
					+ "----->Section Not Closed</FONT>");
			Val.add("Section Not Closed");
			String userHome = System.getProperty("user.dir");
			String screenShotsFolder = userHome + "\\Test-Failure-Screenshots"
					+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
					+ dateFormat.format(cal.getTime()).replace(":", "_") + "\\";
			String filename = screenShotsFolder + scriptName + "_" + (i++)
					+ ".png";
			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Reporter.log("<img src=\"file:///" + filename + "\""
					+ "height=\"600\" width=\"600\"" + " />");
			filename = "";
		} else {
			Reporter.log("<FONT COLOR='ForestGreen'><b>Pass-----></b></FONT>"
					+ desc + "----->Section Closed<br>");
		}
	}
	
	/** 
	 * <p>
	 *  To verify the Closed Functionality by using Class,CSS,ID,XPATH
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the Closed Functionality when click on cancel button in Alert box by using Class,CSS,ID,XPATH
	 * 
	 * @param Id  name of the element on the page with Class or CSS or ID or XPATH
	 * @param desc  Description of the test step that is going to be performed 
	 */

	public void verify_CancelCancel(String Id, String desc) {
		waitForPageLoaded();
		boolean result = false;
		if (lookForXpath(Id)) {
			result = true;
		} else if (lookForCSS(Id)) {
			result = true;
		} else if (lookForClass(Id)) {
			result = true;
		} else if (lookForClass(Id)) {
			result = true;
		}
		if (result) {
			Reporter.log("<FONT COLOR='ForestGreen'><b>Pass-----></b></FONT>"
					+ desc + "----->Section Not Closed<br>");

		} else {
			Reporter.log("<FONT COLOR='red'>Fail----->" + desc
					+ "----->Section Closed</FONT>");
			Val.add("SectionClosed");
			String userHome = System.getProperty("user.dir");
			String screenShotsFolder = userHome + "\\Test-Failure-Screenshots"
					+ dateFormat1.format(cal.getTime()) + "\\" + scriptName
					+ dateFormat.format(cal.getTime()).replace(":", "_") + "\\";
			String filename = screenShotsFolder + scriptName + "_" + (i++)
					+ ".png";
			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Reporter.log("<img src=\"file:///" + filename + "\""
					+ "height=\"600\" width=\"600\"" + " />");
			filename = "";
		}
	}

	/** 
	 * <p>
	 *  To select required row in table by using Class,CSS,ID,XPATH
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to select required row in table by using Class,CSS,ID,XPATH
	 * 
	 * @param StartID expected data
	 * @param paginator  name of the element on the page with Class or CSS or ID or XPATH
	 * @param dataScrollerright xpath of the data Scroller right
	 * @param rows no.of rows
	 * @param textVerify  expected value
	 */
	
	// Select required row in table
	public String select_Row(String StartID, String paginator,
			String dataScrollerright, int rows, String textVerify)
			throws Exception {
		String[] values = StartID.split("\\:");
		String[] pags;
		String[] pages = null;
		int i, j, var = 0;
		if (lookForClass(paginator)) {
			String numval = driver.findElement(By.className(paginator))
					.getText();
			pags = numval.split("\\ ");
			pages = pags[2].trim().split("\\)");
		} else if (lookForXpath(paginator)) {
			String numval = driver.findElement(By.xpath(paginator)).getText();
			pags = numval.split("\\ ");
			pages = pags[2].trim().split("\\)");
		}

		for (i = 1; i <= Integer.parseInt(pages[0]); i++) {
			waitForPageLoaded();
			for (j = var; j < (var + rows); j++) {
				String id = values[0] + ":" + j + ":" + values[2];
				System.out.println(id);
				if (lookForCSS(id)) {
					System.out.println(id + "------"
							+ driver.findElement(By.cssSelector(id)).getText());
					if (driver.findElement(By.cssSelector(id)).getText()
							.contains(textVerify)) {
						return id;

					}
				}
			}
			var = j;
			System.out.println("--" + var);
			if (lookForXpath(dataScrollerright)) {
				if (driver.findElement(By.xpath(dataScrollerright)).isEnabled())
					driver.findElement(By.xpath(dataScrollerright)).click();
				waitForPageLoaded();

			}
		}

		return null;
	}

	// verify Field Enable / Disable
	
	/** 
	 * <p>
	 *  To verify the Field is Enable or Disable by using Xpath
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the Field is Enable or Disable by using Xpath
	 * 
	 * @param id  name of the element on the page with Class or CSS or ID or XPATH
	 * @param enable option of the field
	 * @param FieldName name of the field
	 */

	public void verify_FieldEnableDisable(String id, String enable,
			String FieldName) {

		if (lookForXpath(id)) {
			switch (enable.toUpperCase()) {
			case "ENABLE":
				if (driver.findElement(By.xpath(id)).isEnabled()) {
					Reporter.log("<FONT COLOR='ForestGreen'><b>Pass----></b></FONT>"
							+ FieldName + "--->Field is Enable<br/>");
				} else {
					Reporter.log("<FONT COLOR='red'>Fail----->" + FieldName
							+ "--->Field is Disable</FONT><br/>");
					Val.add("Field is Disable");
					String userHome = System.getProperty("user.dir");
					String screenShotsFolder = userHome
							+ "\\Test-Failure-Screenshots"
							+ dateFormat1.format(cal.getTime())
							+ "\\"
							+ scriptName
							+ dateFormat.format(cal.getTime())
									.replace(":", "_") + "\\";
					String filename = screenShotsFolder + scriptName + "_"
							+ (i++) + ".png";
					File screenshot = ((TakesScreenshot) driver)
							.getScreenshotAs(OutputType.FILE);
					try {
						FileUtils.copyFile(screenshot, new File(filename));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Reporter.log("<img src=\"file:///" + filename + "\""
							+ "height=\"600\" width=\"600\"" + " />");
					filename = "";
				}
				break;
			case "DISABLE":
				if (driver.findElement(By.xpath(id)).isEnabled()) {
					Reporter.log("<FONT COLOR='red'>Fail----->" + FieldName
							+ "--->Field is Enable</FONT><br/>");
					Val.add("Filed is Enable");
					String userHome = System.getProperty("user.dir");
					String screenShotsFolder = userHome
							+ "\\Test-Failure-Screenshots"
							+ dateFormat1.format(cal.getTime())
							+ "\\"
							+ scriptName
							+ dateFormat.format(cal.getTime())
									.replace(":", "_") + "\\";
					String filename = screenShotsFolder + scriptName + "_"
							+ (i++) + ".png";
					File screenshot = ((TakesScreenshot) driver)
							.getScreenshotAs(OutputType.FILE);
					try {
						FileUtils.copyFile(screenshot, new File(filename));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Reporter.log("<img src=\"file:///" + filename + "\""
							+ "height=\"600\" width=\"600\"" + " />");
					filename = "";
				} else {
					Reporter.log("<FONT COLOR='ForestGreen'><b>Pass----></b></FONT>"
							+ FieldName + "--->Field is Enable<br/>");
				}
			}

		}
	}

	/** 
	 * <p>
	 *  To verify the data is convert upper case to lowercase
	 * <P>  
	 * Description 
	 * <p>
	 * This method will used to verify the data is convert upper case to lowercase by using Xpath
	 * 
	 * @param id  name of the element on the page with XPATH
	 * @param txt expected value
	 */
	
	// TO verify Upper Case
	public void isUpperCase(String id, String txt) throws Exception {

		waitForPageLoaded();

		String userid = driver.findElement(By.xpath(id)).getAttribute("value");
		String givendata = txt.toUpperCase();

		if (userid.equals(givendata) == true) {
			stepDescription("Entered Data is converted Lowercase to Uppercase.");
		} else {
			stepDescription("Entered Data is not converted Lowercase to Uppercase.");
		}

	}

	// To verify Reset
	
	/** 
	 * <p>
	 *  To verify the reset functionality
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify the reset functionality i.e) clear the text data
	 * 
	 * @param fieldname  xpath of the element on the page
	 * @param Description name of the field
	 */

	public void verifyreset(String fieldname, String Description) {

		String text1 = (driver.findElement(By.xpath(fieldname))
				.getAttribute("value"));

		if (text1.length() <= 0) {

			Reporter.log("system cleared the entered data in" + "--->\t" + "\t"
					+ Description);

		} else

			Reporter.log("system not cleared the entered data in" + "--->\t"
					+ "\t" + Description);

	}

	/** 
	 * <p>
	 *  To verify the default value in the drop down box
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify the default value in the drop down box
	 * 
	 * @param id  xpath of the element on the page
	 * @param value expected value
	 */
	
	public void verify_DefaultValueDropDown(String id, String value) {
		try {
			WebElement option = driver.findElement(By.xpath(id));

			if (option.isDisplayed()) {
				// option.click();
				Select select = new Select(option);
				WebElement element = select.getFirstSelectedOption();
				if (element.getText().equals(value)) {
					stepDescription("Default value ---->" + value
							+ " is selected in drop down box.");
				} else {
					stepDescription("Default value ----> " + value
							+ " is not selected in drop down box.");
					Val.add(value);
				}
			}
		} catch (Exception e) {

			Reporter.log(e.toString());

		}

	}
	
	/** 
	 * <p>
	 *  To select required row in table
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to select required row in table
	 * 
	 * @param StartID  required value
	 * @param dataScrollerright class name of the element on the page
	 * @param textVerify expexted value
	 */

	public String select_Row(String StartID, String dataScrollerright,
			String textVerify) throws Exception {
		String[] values = StartID.split("\\:");
		String[] val;
		int loopCount, loop, num, i, j;
		int var = 0;
		if (lookForClass(dataScrollerText)) {
			String numval = driver.findElement(By.className(dataScrollerText))
					.getText();
			val = numval.split("\\ ");
			num = Integer.parseInt(val[4]);
			loop = num / 10;
			loopCount = loop;
			if (num % 10 > 0) {
				loopCount = loop + 1;
			}
			for (i = 0; i <= (loopCount - 1); i++) {
				for (j = var; j <= (var + 9); j++) {
					String id = values[0] + ":" + j + ":" + values[2];
					if (driver.findElement(By.cssSelector(id)).getText()
							.contains(textVerify)) {
						return id;
					}
				}
				var = j;
				if (lookForCSS(dataScrollerright)) {
					driver.findElement(By.cssSelector(dataScrollerright))
							.click();
				}
			}
		}

		return null;
	}

	/** 
	 * <p>
	 *  To print the text into Report page
	 * <P>  
	 * Description 
	 * <p>
	 * This method used print the text with font colour 'Navi' into Report page
	 * 
	 * @param description  text of the data
	 */
	
	public void stepDescription(String description) {
		Reporter.log("<FONT color='Navy'>" + description + "</FONT><br>");
	}
	
	/** 
	 * <p>
	 *  To print the text into Report page
	 * <P>  
	 * Description 
	 * <p>
	 * This method used print the text with font colour 'SaddleBrown' into Report page
	 * 
	 * @param description  text of the data
	 */

	public void stepDescription1(String description) {
		Reporter.log("<FONT  COLOR='SaddleBrown'>" + description
				+ "</FONT><br>");
	}

	/** 
	 * <p>
	 *  To print the data enter in the field
	 * <P>  
	 * Description 
	 * <p>
	 * This method used print the data enter in the field
	 * 
	 * @param label  name of the label
	 * @param Value entered data
	 */
	
	// Fields Data
	public static void dataEnterInField(String label, String Value) {
		Reporter.log("Entering Data In Field ' " + label + " '----->" + "'"
				+ Value + "'<br>");
	}

	/** 
	 * <p>
	 *  To verify the Tracking Number is digits or not
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify the Tracking Number is digits or not
	 * 
	 * @return string 
	 */
	
	public String trackingNumber() {
		String str = driver.findElement(By.className(errorMsgClass)).getText();
		StringBuilder myNumbers = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				myNumbers.append(str.charAt(i));

			}
		}
		return myNumbers.toString();
	}
	
	/** 
	 * <p>
	 *  verify alert is present or not
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify alert is present or not
	 * 
	 * @return true if the Alert is Present, otherwise false
	 */
	
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}

	/** 
	 * <p>
	 *  verify class name is present or not
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify class name is present or not
	 * 
	 * @param name class name
	 * @return true if the class name is Present, otherwise false
	 */
	
	// Verifying Elements Presense
	public boolean lookForClass(String name) {
		try {
			driver.findElement(By.className(name));
			System.out.println("Class Present");
			return true;
		} catch (Exception e) {
			System.out.println("Class Not Present");
			return false;
		}
	}
	
	/** 
	 * <p>
	 *  verify xpath is present or not
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify xpath present or not
	 * 
	 * @param name Xpath
	 * @return true if the xpath is Present, otherwise false
	 */

	public boolean lookForXpath(String name) {
		try {
			driver.findElement(By.xpath(name));
			System.out.println("Xpath Present");
			return true;
		} catch (Exception e) {
			System.out.println("Xpath Not Present");
			return false;
		}
	}
	
	/** 
	 * <p>
	 *  verify CSS is present or not
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify CSS present or not
	 * 
	 * @param cssSel CSS Selector
	 * @return true if the CSS is Present, otherwise false
	 */

	public Boolean lookForCSS(String cssSel) {
		waitForPageLoaded();
		try {
			driver.findElement(By.cssSelector(cssSel));
			System.out.println("CSS Selector Present");
			return true;
		} catch (Exception e) {
			System.out.println("CSS Selector Not Present");
			return false;
		}
	}
	
	/** 
	 * <p>
	 *  verify id is present or not
	 * <P>  
	 * Description 
	 * <p>
	 * This method used to verify id present or not
	 * 
	 * @param ID id
	 * @return true if the CSS is Present, otherwise false
	 */

	public boolean lookForID(String ID) {
		try {
			driver.findElement(By.id(ID));
			System.out.println("ID Present");
			return true;
		} catch (Exception e) {
			System.out.println("ID Not Present");
			return false;
		}
	}

	/** 
	 * <p>
	 *  Close the Connection and Quit driver instance
	 * <P>  
	 * Description 
	 * <p>
	 * This method used close the Connection,Close Pop-up windows and Quit driver instance
	 */
	
	@AfterMethod
	public void tearDown() throws Exception {

		waitForPageLoaded();
		System.out.println(this.driver.getCurrentUrl());
		try {
			((JavascriptExecutor) this.driver)
					.executeScript("window.onbeforeunload = function(e){};");
			this.driver.quit();
			con.close();
		} catch (Exception e) {
			this.driver.quit();
			con.close();
		}
		if (Val.size() > 0) {
			assert false : "Verification Failed";
			Val.clear();
		}

	}
}
