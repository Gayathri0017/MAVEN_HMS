package com.hostels.db.user;
import com.hostels.db.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDB{
    public static void registerUser(String userID, String name, String email, String password, String phoneNumber) {
        String query = "INSERT INTO HMS.users (userID, name, email, password, phoneNumber) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,userID);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, phoneNumber);
            pstmt.executeUpdate();
            System.out.println("✅ User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean loginUser(String userID, String password) {
        String query = "SELECT * FROM hms.users WHERE userID = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
