package com.trackme.spring.trackigcontroller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SCDBConnection {
private static final String url = "jdbc:postgresql://localhost:5432/trackme";
private static final String user = "postgres";
private static final String password = "root";

/**
 * Connect to the PostgreSQL database
 *
 * @return a Connection object
 * @throws ClassNotFoundException 
 */
public static Connection connect() throws ClassNotFoundException {
    Connection conn = null;
    try {
    	Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return conn;
}
public static void main(String[] args) {
	 try {
		connect();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
