package smoke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class NewTest {

	public static Properties seleniumConfig = null;

	private static FileWriter fw;
	private static BufferedReader bufRdr;
	private static ArrayList<String> test;
	public String user;
	public Object USER_NAME;

	/**
	 * Configuration file location
	 */
	public static String SELENIUM_CONFIG_FILE;

	public NewTest() throws FileNotFoundException, IOException,
			ClassNotFoundException, SQLException {
		seleniumConfig = new Properties();

		String current = new java.io.File(".").getCanonicalPath();

		System.out.println("current" + current);

		SELENIUM_CONFIG_FILE = current + "\\Config\\selenium.properties";

		seleniumConfig.load(new FileInputStream(SELENIUM_CONFIG_FILE));

		String userpath = System.getProperty("user.home");
		test = readFile(userpath + "\\desktop\\ExecTest.txt");

		user = test.get(1).toString().replace("user=", "");

		USER_NAME = seleniumConfig.setProperty("username", user);

		seleniumConfig.store(new FileOutputStream(SELENIUM_CONFIG_FILE),
				"Browser updated");

	}

	public static ArrayList<String> readFile(String filePath)
			throws FileNotFoundException {
		File file = new File(filePath);
		bufRdr = new BufferedReader(new FileReader(filePath));
		ArrayList<String> out = new ArrayList<String>();
		String line = null;
		try {
			for (int i = 0; (line = bufRdr.readLine()) != null; i++) {
				out.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;

	}

}
