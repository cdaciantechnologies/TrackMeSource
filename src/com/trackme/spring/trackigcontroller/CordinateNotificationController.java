package com.trackme.spring.trackigcontroller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CordinateNotificationController {

	Connection conn = null;
	public String latitude = null;
	public String longitude = null;
    Statement stmt = null;
    ResultSet rs  = null;

	public CordinateNotificationController() {
		System.out.println("***********************************The controller has been initialize******************");
		initialize();
	}

	
	private void initialize() {
		try {
			conn=	SCDBConnection.connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void notifyMe() {
		try {
			while (true) {
				if (latitude == null && longitude == null) {
					 stmt = conn.createStatement();
			         rs = stmt.executeQuery( "select LATITUDE,LOGITUDE from GSMSTATUS where STATUS != 0;" );
			         if ( rs.next() ) {
			        	 latitude = rs.getString("LATITUDE");
			        	 longitude = rs.getString("LOGITUDE");
			          }
			         boolean flag =  searchCodinate(latitude,longitude,conn);

			          rs.close();
			          stmt.close();
			          conn.close();

				} else {
					dosleep(1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// To make main thread to sleep for specified millisecond.
	private void dosleep(long lngTime) throws Exception {
		try {
			Thread.sleep(lngTime);
		} catch (Exception objException) {
			System.out.println("Thread Exception...");
		}
	}
	
	//Searching the codinates in the existing table
	private boolean searchCodinate(String latitude, String longitude,Connection conn) {
		boolean flag = false;
		String userStatus=null;
		 try {
			stmt = conn.createStatement();
			 rs = stmt.executeQuery( "select STATUS from GEOFENCEMASTER where LONGITUDE = '"+longitude+"'AND  LATITUDE='"+latitude+"';" );
	         if ( rs.next() ) {
	        	 userStatus = rs.getString("STATUS");
	          }
	         if(userStatus!=null && userStatus.equals("Active") ) {
	        	 flag = true;
	         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return flag;
	}

	public static void main(String[] args) {	
		CordinateNotificationController NotificationController = null;
		try {
			NotificationController = new CordinateNotificationController();
			NotificationController.notifyMe();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
