package com.hostels.db.user;
import com.hostels.db.connection.DBConnection;
import java.sql.*;
import java.util.*;
public class EventDB {
	static String gray = "\033[90m";
	static String bold="\033[1m";
	static String reset= "\033[0m"; 
	public static void insertEvent(String id, String event_name, String date, String description) {
        String sql = "INSERT INTO hms.events(event_id, event_name, event_date, event_description) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, event_name);
            ps.setString(3, date);
            ps.setString(4, description);
            ps.executeUpdate();
            System.out.println("Event created successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error inserting event: " + e.getMessage());
        }
    }
	public static void updateEvent(String eventId, int field, String newValue) {
        String query = null;
        switch (field) {
            case 1:
                query = "UPDATE hms.events SET event_name = ? WHERE event_id = ?";
                break;
            case 2:
                query = "UPDATE hms.events SET event_date = ? WHERE event_id = ?";
                break;
            case 3:
                query = "UPDATE hms.events SET event_description = ? WHERE event_id = ?";
                break;
            default:
                System.out.println("❌Invalid field! Choose 1 for Event Name, 2 for Date, 3 for Description.");
        }
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1,newValue);
            ps.setString(2,eventId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated>0) {
                System.out.println("Event updated successfully!");
            } else {
                System.out.println("❌ Update failed! No event found with this ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating event: " + e.getMessage());
        }
    }
    public static void displayEvents() {
        String sql = "SELECT * FROM hms.events";
        try {
            Connection con = DBConnection.getConnection();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            System.out.println(gray+bold+String.format("%-15s %-25s","Event ID","Event Name"+reset));
            System.out.println("======================================================");
            while (rs.next()) {
                System.out.println(String.format("%-15s %-25s",rs.getString("event_id"),rs.getString("event_name")));
                System.out.println(String.format("%-15s %-25s","Event Date: ",rs.getString("event_date")));
                System.out.println(String.format("%-15s %-25s","Description: ",rs.getString("event_description")));
                System.out.println("------------------------------------------------------");
            }
        }
            catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    public static void deleteEvent(String id) {
        String sql = "DELETE FROM hms.events WHERE event_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Event deleted successfully!");
            } else {
                System.out.println("❌ Event ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
