package smoke;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.common.Runprop;

public class TestngXMLGenerator_From_XL_File {

	private static FileWriter fw;
	// private static BufferedReader bufRdr;
	private static ArrayList<String> test;
	public static Connection con = null;
	public static ResultSet resultSet = null;
	public static ResultSet value;
	public static String dbvalueid;
	public static String dbvaluetype;
	private static Runprop runConfig;
	public static void main(String[] args) throws Exception {

		String current = new java.io.File(".").getCanonicalPath();
		System.out.println("Current dir:" + current);
		runConfig = new Runprop();

		startWritingXMLFile(current + "\\testng.xml");
		fw.append("<?xml version=" + addQuates("1.0") + " encoding="
				+ addQuates("UTF-8") + "?>");
		addBlankLine(1);
		fw.append("<!DOCTYPE suite SYSTEM "
				+ addQuates("http://testng.org/testng-1.0.dtd") + ">");
		addBlankLine(2);
		suitDetails("testng", null, -10);
		addBlankLine(1);
		fw.append("<!-- Suite -->");
		addBlankLine(1);
		testDetails("smoke");
		addBlankLine(1);
		fw.append("</suite>");

		saveAndCloseXMLFile();
	}

	public static String addQuates(String Name) {
		String quatos = "\"";
		return quatos + Name + quatos;

	}

	public static void testDetails(String testName) throws Exception {

		TestngXMLGenerator_From_XL_File pac = new TestngXMLGenerator_From_XL_File();
		Package pack = pac.getClass().getPackage();
		String packageName = pack.getName();
		fw.append("	<listeners>");
		addBlankLine(1);
		fw.append("		<listener class-name="
				+ addQuates("main.common" + "." + "CustomReport") + "/>");
		addBlankLine(1);
		fw.append("	</listeners>");

		addBlankLine(1);
		fw.append("<test name=" + addQuates("smoke") + ">");
		addBlankLine(1);
		fw.append("<!-- Test -->");
		addBlankLine(1);
		fw.append("	<classes>");
		addBlankLine(1);

		returndata();
		while (resultSet.next()) {
			dbvalueid = resultSet.getString("tc_id");
			dbvaluetype = resultSet.getString("tc_type");
			
			fw.append("		<class name=" + addQuates(dbvaluetype + "." + dbvalueid)
					+ "/>");
			System.out.println("***" + dbvalueid);
		}

		addBlankLine(1);
		fw.append("	</classes>");
		addBlankLine(1);
		fw.append("</test>");
		// }

	}

	public static void suitDetails(String suitName,
			String parallel_methods_or_tests, int parallelThreadCount)
			throws IOException {
		String parallel_Thread_Count;
		if (parallel_methods_or_tests == null) {
			parallel_methods_or_tests = "none";
		}
		if (parallelThreadCount <= 0) {
			parallelThreadCount = 0;
		}
		parallel_Thread_Count = String.valueOf(parallelThreadCount);
		fw.append("<suite name=" + addQuates(suitName) + "	parallel="
				+ addQuates(parallel_methods_or_tests) + "	thread-count="
				+ addQuates(parallel_Thread_Count) + ">");
	}

	public static void addBlankLine(int lineCount) throws IOException {
		for (int count = 0; count < lineCount; count++) {
			fw.append('\n');
		}
	}

	public static void saveAndCloseXMLFile() {
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startWritingXMLFile(String FileName) {
		try {
			fw = new FileWriter(FileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public static ResultSet returndata() throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				DBConnect_Credentials.url1, DBConnect_Credentials.db_username,
				DBConnect_Credentials.db_password);
		System.out.println("Connection Open");

		Statement stmt = con.createStatement();
		System.out.println("Statement Open");
		String query = "select tc_id,tc_type from testplan where testplan_id in(select testplan_id from testlab where tc_status is null and username='"+runConfig.USER_NAME+"' and testsuite_id in(select max(testsuite_id) from testlab where username='"+runConfig.USER_NAME+"'));";

		resultSet = stmt.executeQuery(query);

		return resultSet;

	}

	

}
