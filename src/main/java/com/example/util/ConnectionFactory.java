package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static String url = "jdbc:postgresql://localhost/postgres";
	private static String username = "postgres";
	private static String password = "postgres17#";
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			//This lie uses DriverManager to try to connect to the postgresql database
			conn = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return conn;
	}

}
