package com.hostels.db.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import com.hostels.db.connection.DBConnection;
public class NotificationDB {
public static void addNotification(String details) {
	String query="Insert into hms.NOTIFICATIONS(notification_details,notification_date) values (?,?)";
	try {
		Connection conn=DBConnection.getConnection();
		PreparedStatement ps=conn.prepareStatement(query);
		ps.setString(1,details);
		ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		ps.executeUpdate();
		System.out.println("Notification added successfully!");
	}catch(Exception e) {
		System.out.println(e.getMessage());
	}
}
public static void viewNotifications() {
	String query= "SELECT id, notification_details, notification_date FROM hms.notifications ORDER BY notification_date DESC";
	try {
		Connection conn=DBConnection.getConnection();
		PreparedStatement ps=conn.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		if(!rs.isBeforeFirst()) {
			System.out.println("No notification available");
		}
		else {
			while(rs.next()) {
				 System.out.println("ID: " + rs.getInt("id"));
	             System.out.println("Details: " + rs.getString("notification_details"));
	             System.out.println("Date: " + rs.getTimestamp("notification_date"));
	             System.out.println("----------------------------");	
			}
		}
	}catch(Exception e) {
		System.out.println(e.getMessage());
		
	}
}
}
