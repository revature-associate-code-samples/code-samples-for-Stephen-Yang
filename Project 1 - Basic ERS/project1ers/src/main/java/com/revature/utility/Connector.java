package com.revature.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class Connector {
	
	final static Logger cLogger = Logger.getLogger(Connector.class);

	public static Connection getConnection() throws SQLException {
		try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
        	cLogger.error(e.getMessage());
            e.printStackTrace();
        }
		String url = "jdbc:oracle:thin:@sy-revature.cugxe1ca0ol9.us-east-2.rds.amazonaws.com:1521:ORCL"; 
		String user = "sy18"; 
		String pass = "Databaser14";
		
		return DriverManager.getConnection(url, user, pass); // establish connection
	}
	
}
