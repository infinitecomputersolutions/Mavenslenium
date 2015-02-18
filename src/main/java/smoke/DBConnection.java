package smoke;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import main.common.Runprop;

public class DBConnection {
	
	
	public static Connection con = null;
	


	public static String dbvalueid;
	public static String dbvaluetype;

	
	
	public static void main(String[] args) throws Exception{
		
		
		/*String testtype = "smoke";
		
		
		int i= testtype.length();
		
		System.out.println("***"+i);*/
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
		System.out.println("Connection Open");
	
		Statement stmt = con.createStatement();
		System.out.println("Statement Open");
		String query = "select tc_id,tc_type from testplan where testplan_id in(select testplan_id from testlab where tc_status is null and username='nagaj' and testsuite_id in(select max(testsuite_id) from testlab));";
		//String query = "select project_id,release_id,iteration_id,sprint_id,testsuite_id,SUM(CASE WHEN tc_status = 'Pass' THEN 1 ELSE 0 END) as 'Passed',SUM(CASE WHEN tc_status = 'Fail' THEN 1 ELSE 0 END) as 'Failed',SUM(CASE WHEN tc_status = 'NoRun' THEN 1 ELSE 0 END) as 'No_Run' from testlab where project_id='" +query_projectId + "' and release_id='" + query_releaseId + "' and iteration_id='" + query_iterationId + "' and sprint_id='" + query_sprintId + "' group by testsuite_id";
		ResultSet result = stmt.executeQuery(query);
		
		
		 while (result.next()) {
             dbvalueid = result.getString("tc_id");
             dbvaluetype = result.getString("tc_type");
             
             
            System.out.println("***"+dbvalueid);
            System.out.println("$$$$$$$"+dbvaluetype);
        }

		 
	}	
	
	/*public static void ConnectToMySQLAccess() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println(" Driver Found.");
		} catch (ClassNotFoundException e) {
			System.out.println(" Driver Not Found, exiting..");
			throw (e);
		}
	}
	
	public static Connection getConnection(String host, String userDB, String passDB,
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
	
	public static  ResultSet returndata(String tc_name,String testplan, String runmode,String yes,String tc_tool,String selenium,String tc_type,String smoke) throws Exception {
		PreparedStatement stm = con.prepareStatement(String
				.format("Select %s from %s where %s='%s' and %s='%s' and %s='%s'",tc_name, testplan, runmode,yes,tc_tool,selenium,tc_type,smoke));
		
		
		System.out.println("**********"+stm);
		
		
		 return stm.executeQuery();
		 
		 
		
	}
	
	

	public static  ResultSet datapassing(String tc_name,String testplan, String runmode,String yes,String tc_tool,String selenium,String tc_type,String smoke) throws Exception{
		
		value = returndata(tc_name, testplan, runmode,yes,tc_tool,selenium,tc_type,smoke);
		
		 while (value.next()) {
	             dbvalue = value.getString(1);
	             
	            System.out.println("***"+dbvalue);
	        }
		 return value;
	}
	
	
	

	public static  ResultSet returntxtdata(String txtfield,String selenium_igaf_testdata, String tc_id,String tc_id_1,String testplan,String tc_name,String tc_casenum,String project_id,String seleniu_igaf) throws Exception {
		PreparedStatement stm = con.prepareStatement(String
				.format("SELECT %s FROM %s where %s in (SELECT %s FROM %s WHERE %s='%s' and %s='%s')",txtfield,selenium_igaf_testdata,tc_id,tc_id_1,testplan,tc_name,tc_casenum,project_id,seleniu_igaf));
		
		System.out.println("**********"+stm);
		
		
		 return stm.executeQuery();
		 
		 
		
	}
	
public static  ResultSet datapassingRETURNTXT(String txtfield,String selenium_igaf_testdata, String tc_id,String tc_id_1,String testplan,String tc_name,String tc_casenum,String project_id,String seleniu_igaf) throws Exception{
		
		txtvalue = returntxtdata(txtfield,selenium_igaf_testdata,tc_id,tc_id_1,testplan,tc_name,tc_casenum,project_id,seleniu_igaf);
		
		 while (txtvalue.next()) {
	             txtdbvalue = txtvalue.getString(1);
	             
	            System.out.println("***"+txtdbvalue);
	        }
		 return txtvalue;
	}*/
	


}
