package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionUtil {
	
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@sy-revature.cugxe1ca0ol9.us-east-2.rds.amazonaws.com:1521:ORCL"; 
		String user = "sy18"; 
		String pass = "Databaser14";
		
		return DriverManager.getConnection(url, user, pass); // establish connection
	}
	
}
