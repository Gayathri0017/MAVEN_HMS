
package com.hostels.db.user;
import com.hostels.db.connection.DBConnection;
import java.sql.*;
public class EventDB {
	public static void insertEvent(String id, String event_name, String date, String description) {
        String sql = "INSERT INTO hms.events(event_id, event_name, event_date, event_description) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, event_name);
            ps.setString(3, date);
            ps.setString(4, description);
            ps.executeUpdate();
            System.out.println("✅ Event created successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error inserting event: " + e.getMessage());
        }
    }
    public static void updateEvent(String id, String newEventName, String newDate, String newDescription) {
        String sql = "UPDATE hms.events SET event_name = ?, event_date = ?, event_description = ? WHERE event_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newEventName);
            ps.setString(2, newDate);
            ps.setString(3, newDescription);
            ps.setString(4, id);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Event updated successfully!");
                displayEvents(); // Optional: confirm the change
            } else {
                System.out.println("❌ Event ID not found.");
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
            while (rs.next()) {
                System.out.println("Event ID: " + rs.getString("event_id"));
                System.out.println("Event Name: " + rs.getString("event_name"));
                System.out.println("Event Date: " + rs.getString("event_date"));
                System.out.println("Description: " + rs.getString("event_description"));
                System.out.println("===========================");
            }
        } catch (Exception e) {
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
                System.out.println("✅ Event deleted successfully!");
            } else {
                System.out.println("❌ Event ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
