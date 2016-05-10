package servlet;

import java.sql.*;

public class Mysql {
	
	public static Connection connection(){
		
		// Connect to AWS RDS mySQL server
		
		Connection ct = null;
		String URL = "jdbc:mysql://requestdb.cstcf4zyxzcd.us-east-1.rds.amazonaws.com:3306/application";
		String Username = "root";
		String Pwd = "uoftiqua";
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
	        ct = DriverManager.getConnection(URL, Username, Pwd);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}	
		
		return ct;

	}

}
