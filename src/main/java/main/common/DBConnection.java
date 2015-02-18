package main.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnection {
	public static Connection con = null;
	public static ResultSet resultSet = null;
	public static ResultSet value;
	public static String dbvalueid;
	public static String dbvaluename;

	public static void main(String[] args) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		 con = DriverManager.getConnection(
				DBConnect_Credentials.url1, DBConnect_Credentials.db_username,
				DBConnect_Credentials.db_password);
		System.out.println("Connection Open");

		Statement stmt = con.createStatement();
		System.out.println("Statement Open");
		String query = "select tc_id,tc_name from testplan where tc_id in (select tc_id from testlab where tc_status is null);";

		resultSet = stmt.executeQuery(query);

		ArrayList<String> tc_id = new ArrayList<String>();
		ArrayList<String> tc_name = new ArrayList<String>();

		while (resultSet.next()) {
			dbvalueid = resultSet.getString("tc_id");
			dbvaluename=resultSet.getString("tc_name");
			

			tc_id.add(dbvalueid);
			tc_name.add(dbvaluename);

			//System.out.println("***" + dbvalueid);
			//System.out.println("^^^^"+dbvaluename);
		}

		for (int j = 0; j < tc_id.size()&& j < tc_name.size(); j++) {		
				
			
			String id = tc_id.get(j);

			System.out.println("value ***" + id);
			
			
			String name = tc_name.get(j);
			
			System.out.println("*** value"+ name);
			
			String query1 = "UPDATE testlab SET tc_status='Pass' WHERE tc_id='"+id+"';";
			
		

		 stmt.executeUpdate(query1);
		 

		
		}

		

	}

	/*
	 * public static void ConnectToMySQLAccess() throws ClassNotFoundException {
	 * try { Class.forName("com.mysql.jdbc.Driver");
	 * System.out.println(" Driver Found."); } catch (ClassNotFoundException e)
	 * { System.out.println(" Driver Not Found, exiting.."); throw (e); } }
	 * 
	 * public static Connection getConnection() throws Exception {
	 * 
	 * try {
	 * 
	 * Connection con = DriverManager.getConnection( DBConnect_Credentials.url1,
	 * DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
	 * 
	 * System.out.println("Connection Open"); } catch (java.sql.SQLException e)
	 * { throw (e); } return con; }
	 * 
	 * public static ResultSet returndata(String selectedfield, String
	 * tablename, String testcaseid, String testcasename, String Projectid,
	 * String idvalue) throws Exception { PreparedStatement stm =
	 * con.prepareStatement(String.format(
	 * "Select %s from %s where %s='%s' and %s='%s'", selectedfield, tablename,
	 * testcaseid, testcasename, Projectid, idvalue)); System.out.println(stm);
	 * return stm.executeQuery(); }
	 * 
	 * public static void datapassing(String selectedfield, String tablename,
	 * String testcaseid, String testcasename, String Projectid, String idvalue)
	 * throws Exception { value = returndata(selectedfield, tablename,
	 * testcaseid, testcasename, Projectid, idvalue); while (value.next()) {
	 * dbvalue = value.getString(1); System.out.println(dbvalue); } }
	 * 
	 * public static void insertdata(String tablename, String projectidval,
	 * String testcaseidval, String statusval, String s5, String S6, String s7,
	 * String s8, String S9, String s10, String s11, String s12, String s13)
	 * throws Exception { PreparedStatement stm = con .prepareStatement(String
	 * .format(
	 * "INSERT INTO %s (project_id,tc_id,tc_status,defect_id,Iteration,Error_Log,ExecutionTime,Date,tc_execution_history,tc_machine,testset_id,testset_name) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"
	 * , tablename, projectidval, testcaseidval, statusval, "", "", "", "", "",
	 * "", "", "", "")); System.out.println(stm); stm.executeUpdate(); }
	 */

}
