package smoke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB_Murali 
{
    public static Connection con = null;
    public static ResultSet resultSet = null;
    public static ResultSet value ;
    public static ResultSet txtvalue ;
    public static String dbvalue;
    public static String txtdbvalue;
    
    
    public static void main(String[] args) throws Exception
    {
	    ConnectToMySQLAccess();
	    
	    con = getConnection("172.16.28.136", "tcoe", "tcoe","tcoe");
	    
	    //Test case name
	  //  datapassing("tc_name","testplan","runmode","yes", "tc_tool","selenium","tc_type","smoke");
	    
	 //   datapassingRETURNTXT("txt_FROM","selenium_igaf_testdata","tc_id","tc_id","testplan","tc_name","TC_001","project_id","seleniu_igaf");
	    
	    //Inserting the values into the table
	  	insertdata("testlab","", "", "PASS","1","","","","","","","","");
    }              
    
    
  //**************************************************************************************************
    public static void ConnectToMySQLAccess() throws ClassNotFoundException 
    {
	    try 
	    {
	        Class.forName("com.mysql.jdbc.Driver");
	        System.out.println(" Driver Found.");
	    } 
	    catch (ClassNotFoundException e) 
	    {
            System.out.println(" Driver Not Found, exiting..");
            throw (e);
	    }
    }
    
    
  //**************************************************************************************************
    public static Connection getConnection(String host, String userDB, String passDB,String database) throws Exception 
    {
 	    String url = "";
	    try 
	    {
	        url = "jdbc:mysql://" + host + "/" + database;
	        con = DriverManager.getConnection(url, userDB, passDB);
	        System.out.println("Connection Open");
	    } 
	    catch (java.sql.SQLException e) 
	    {
	                    throw (e);
	    }
	    return con;
    }
    
  //**************************************************************************************************
    public static  ResultSet returndata(String tc_name,String testplan, String runmode,String runmode_value,String tc_tool,String tctool_value,String tc_type,String tc_type_value) throws Exception 
    {
        PreparedStatement stm = con.prepareStatement(String
                                        .format("Select %s from %s where %s='%s' and %s='%s' and %s='%s'",tc_name, testplan, runmode,runmode_value,tc_tool,tctool_value,tc_type,tc_type_value));
        System.out.println("**********"+stm);
        return stm.executeQuery();
    }
    
    
  //**************************************************************************************************
    public static  ResultSet datapassing(String tc_name,String testplan, String runmode,String runmode_value,String tc_tool,String tctool_value,String tc_type,String tc_type_value) throws Exception
    {
    	//Load the mysql driver dynamically
    	Class.forName("com.mysql.jdbc.Driver");
    	//Establish connection
    	Connection con =  getConnection("172.16.28.136", "tcoe", "tcoe","tcoe");
    	//Create statement Object
    	java.sql.Statement stmt = con.createStatement();
    	//Execute the query and store the results in the ResultSet object
    	ResultSet rs2 = stmt.executeQuery("SELECT * FROM testplan");
    	//Printing the column values of ResultSet
    	while (rs2.next()) 
    	{
	    	runmode_value = rs2.getString("runmode");
	    	System.out.println("Run Mode..."+runmode_value);
	    	
	    	tctool_value = rs2.getString("tc_tool");
	    	System.out.println("Tc Tool..."+tctool_value);

	    	tc_type_value = rs2.getString("tc_type");
	    	System.out.println("Tc Type..."+tc_type_value);
    	}
    	value = returndata(tc_name, testplan, runmode,runmode_value,tc_tool,tctool_value,tc_type,tc_type_value);
            while (value.next()) 
            {
            	dbvalue = value.getString(1);
            	System.out.println("datapassing ***"+dbvalue);
            }
            return value;
    }
    
    
    //**************************************************************************************************
    public static  ResultSet returntxtdata(String txtfield,String selenium_igaf_testdata, String tc_id,String tc_id_1,String testplan,String tc_name,String tc_casenum,String project_id,String seleniu_igaf) throws Exception 
    {
    	PreparedStatement stm = con.prepareStatement(String.format("SELECT %s FROM %s where %s in (SELECT %s FROM %s WHERE %s='%s' and %s='%s')",txtfield,selenium_igaf_testdata,tc_id,tc_id_1,testplan,tc_name,tc_casenum,project_id,seleniu_igaf));
        System.out.println("**********"+stm);
        return stm.executeQuery();
    }
                
    public static  ResultSet datapassingRETURNTXT(String txtfield,String selenium_igaf_testdata, String tc_id,String tc_id_1,String testplan,String tc_name,String tc_casenum,String project_id,String seleniu_igaf) throws Exception
    {
    	txtvalue = returntxtdata(txtfield,selenium_igaf_testdata,tc_id,tc_id_1,testplan,tc_name,tc_casenum,project_id,seleniu_igaf);
        while (txtvalue.next()) 
        {
        	txtdbvalue = txtvalue.getString(1);
            System.out.println("datapassingRETURNTXT ***"+txtdbvalue);
        }
        return txtvalue;
     }
    
    public static void insertdata(String tablename,String projectidval,String testcaseidval,String statusval,String s5,String S6,String s7,String s8,String S9,String s10,String s11, String s12,String s13) throws Exception 
	{
    	//Load the mysql driver dynamically
    	Class.forName("com.mysql.jdbc.Driver");
    	//Establish connection
    	Connection con =  getConnection("172.16.28.136", "tcoe", "tcoe","tcoe");
    	//Create statement Object
    	java.sql.Statement stmt = con.createStatement();
    	//Execute the query and store the results in the ResultSet object
    	ResultSet iterationnumber_rs = stmt.executeQuery("SELECT * FROM releases where project_id='seleniu_igaf'");
    	//Printing the column values of ResultSet
    	while (iterationnumber_rs.next()) 
    	{
	    	String iterationnumber = iterationnumber_rs.getString("iteration");
	    	System.out.println("Iteration No..."+iterationnumber);
	    	s5 = iterationnumber;
    	}
    	
    	ResultSet tcid_rs = stmt.executeQuery("SELECT * FROM testplan where project_id='seleniu_igaf'");
    	//Printing the column values of ResultSet
    	while (tcid_rs.next()) 
    	{
	    	String TC_ID = tcid_rs.getString("tc_id");
	    	System.out.println("Test Case ID...****"+TC_ID);
	    	testcaseidval = TC_ID;
	    	
	    	/*String TC_NAME = tcid_rs.getString("tc_name");
	    	System.out.println("Test Case name...****"+TC_NAME);
	    	testcaseidval = TC_NAME;*/
	    	
	    	String PROJECT_ID = tcid_rs.getString("project_id");
	    	System.out.println("Project ID..."+PROJECT_ID);
	    	projectidval = PROJECT_ID;
    	
    	
    	java.sql.Timestamp datetime = new java.sql.Timestamp(new java.util.Date().getTime());
		PreparedStatement stm = con.prepareStatement(String.format("INSERT INTO %s (project_id,tc_id,tc_status,defect_id,Iteration,Error_Log,ExecutionTime,Dates,tc_execution_history,tc_machine,testset_id,testset_name) "
				+ "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",tablename,projectidval,testcaseidval,statusval,"",s5,"","",datetime,"","","",""));
		System.out.println(stm);
		int countaffectedrows = stm.executeUpdate();
		System.out.println ("Number of rows affected: " + countaffectedrows);
    	}
	}
                
}

