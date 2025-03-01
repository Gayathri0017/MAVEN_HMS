package com.hostels.db.user;
import java.sql.*;
import com.hostels.db.connection.DBConnection;
import java.sql.*;
public class VisitorDB {
    public static int insertVisitor(String name, String inTime) {
        String sql = "INSERT INTO hms.Visitors (name, in_time) VALUES (?, ?)";
        int visitorId = -1;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement(sql, new String[]{"id"})) {
            ps.setString(1, name);
            ps.setString(2, inTime);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                visitorId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitorId;
    }
    public static void updateExitTime(int visitorId, String outTime) {
        String sql = "UPDATE hms.Visitors SET out_time = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, outTime);
            pstmt.setInt(2, visitorId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void displayVisitor(int visitorId) {
        String sql = "SELECT * FROM hms.Visitors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps= conn.prepareStatement(sql)) {
            ps.setInt(1, visitorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Visitor Name: " + rs.getString("name"));
                System.out.println("InTime: " + rs.getString("in_time"));
                System.out.println("OutTime: " + (rs.getString("out_time") != null ? rs.getString("out_time") : "Not recorded"));
            } else {
                System.out.println("Visitor not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


