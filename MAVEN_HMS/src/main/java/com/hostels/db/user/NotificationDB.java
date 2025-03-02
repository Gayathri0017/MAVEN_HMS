package com.hostels.db.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import com.hostels.db.connection.DBConnection;
public class NotificationDB {
    public static void addNotification(String details) {
        String query = "INSERT INTO hms.NOTIFICATIONS(notification_details, notification_date) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, details);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
            System.out.println("Notification added successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void viewNotifications() {
        String query = "SELECT id, notification_details, notification_date FROM hms.notifications ORDER BY notification_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (!rs.isBeforeFirst()) {
                System.out.println("No notification available");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Details: " + rs.getString("notification_details"));
                    System.out.println("Date: " + rs.getTimestamp("notification_date"));
                    System.out.println("----------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void modifyNotification(int id, String newDetails) {
        String query = "UPDATE hms.NOTIFICATIONS SET notification_details = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newDetails);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification updated successfully!");
            } else {
                System.out.println("Notification with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void deleteNotification(int id) {
        String query = "DELETE FROM hms.NOTIFICATIONS WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification deleted successfully!");
            } else {
                System.out.println("Notification with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
