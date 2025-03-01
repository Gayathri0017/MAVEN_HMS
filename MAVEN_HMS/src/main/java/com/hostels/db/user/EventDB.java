package com.hostels.db.user;
import com.hostels.EventManagement;
import com.hostels.db.connection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EventDB {
   public static void insertEvent(String id,String event_name,String date,String description) {
   String sql="insert into hms.events(event_id,event_name,Event_date,event_description) values(?,?,?,?)";
   try {
	   Connection con=DBConnection.getConnection();
	   PreparedStatement ps=con.prepareStatement(sql);
	   ps.setString(1,id);
	   ps.setString(2,event_name);
	   ps.setString(3,date);
	   ps.setString(4, description);
	   ps.executeUpdate();
	   con.setAutoCommit(true);
	   System.out.println("Event Created successfully!");
	   }catch(Exception e) {
		   System.out.println(e.getMessage());
	   }
   }
   public static void displayEvents() {
	   String sql="select * from hms.events";
	   try {
		   Connection con=DBConnection.getConnection();
		   Statement s=con.createStatement();
		   ResultSet rs=s.executeQuery(sql);
		   while(rs.next()) {
			   System.out.println(rs.getString("event_id"));
			   System.out.println(rs.getString("event_Name"));
			   System.out.println(rs.getString("event_Date"));
			   System.out.println(rs.getString("event_description"));
		   }
	   }catch(Exception e) {
		   System.out.println(e.getMessage());
	   }
   }
}
