package main.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import smoke.DBConnect_Credentials;


public class WriteResulttoDB 
{

	private static Runprop runConfig;
	int testsuite_id;
	String dbtestplan_id;
	String names;
	
	
	public void updateDB(String status,String TestType, String TC_id) throws ClassNotFoundException, SQLException, IOException
	{
		
		Properties props = new Properties();
        
        
        String userpaths=System.getProperty("user.home");
		String filenames= userpaths+"\\Desktop\\ExecTest.txt";
		FileInputStream fis = new FileInputStream(filenames);
      
        //loading properites from properties file
        props.load(fis);

        //reading property
        String username = props.getProperty("user");
        System.out.println("Currently Logged in User..."+username);

        
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(DBConnect_Credentials.url1, DBConnect_Credentials.db_username, DBConnect_Credentials.db_password);
		System.out.println("Connection Open");
	
		Statement query_stmt = con.createStatement();
		String query_query = "SELECT testsuite_id,username FROM testlab where  username='" + username + "' group by testsuite_id order by testsuite_id desc,username  LIMIT 1";
		System.out.println("...."+query_query);
		ResultSet query_result = query_stmt.executeQuery(query_query);
		while (query_result.next())
		{
			testsuite_id = query_result.getInt("testsuite_id");
			names =  query_result.getString("username");
		}
		query_result.close();
		query_stmt.close();
		con.close();
		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(DBConnect_Credentials.url1,DBConnect_Credentials.db_username,	DBConnect_Credentials.db_password);
		System.out.println("Connection Open");
	
		Statement stmt = con.createStatement();
		System.out.println("Statement Open");
		String query = "select tc_id,testplan_id from testplan where tc_id= '" + TC_id + "' and testplan_id in(select testplan_id from testlab where tc_status is null and username='"	+ username + "' and testsuite_id in(select max(testsuite_id) from testlab where username='"+ username + "')) LIMIT 1";
		System.out.println("!!!!!!!"+query);
		ResultSet resultSet = stmt.executeQuery(query);
		while (resultSet.next()) 
		{
			dbtestplan_id = resultSet.getString("testplan_id");
		
		}
		String query1 = "UPDATE testlab SET tc_status='" + status	+ "' WHERE testplan_id='" +dbtestplan_id + "'and testsuite_id='" +testsuite_id + "'and tc_type ='" +TestType + "'";
		
	    System.out.println("--------------------------------------------Write To DB..."+query1);
	    stmt.executeUpdate(query1);
		
		
	}
}
