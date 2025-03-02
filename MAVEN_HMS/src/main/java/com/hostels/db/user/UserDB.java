package com.hostels.db.user;
import com.hostels.db.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDB {
	static String gray = "\033[90m";
	static String bold="\033[1m";
	static String reset= "\033[0m"; 
    public static void registerUser(String userID, String name, String email, String password, String phoneNumber) {
        String query = "INSERT INTO HMS.users (userID, name, email, password, phoneNumber) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
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
    public static void ViewProfile(String userId) {
    	String sql="select *from hms.users where userID=?";
    	try {
    		Connection conn=DBConnection.getConnection();
    		PreparedStatement ps=conn.prepareStatement(sql);
    		ps.setString(1,userId);
    		ResultSet rs=ps.executeQuery();
    		if(rs.next()) {
    			System.out.println("Profile Details of "+userId);
    			System.out.println("User Name: "+rs.getString("userid"));
    			System.out.println("Name: "+rs.getString("name"));
    			System.out.println("Email: "+rs.getString("email"));
    			System.out.println("Mobile Number: "+rs.getString("phonenumber"));
    			System.out.println("Password: "+rs.getString("password"));
    			System.out.println("---------------------------------------------");
    		}
    		else {
    			System.out.println("No user found with this user ID "+userId);
    		}
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    }
    public static boolean updateProfile(String userID,int field,String newValue) {
        String query = null;
        switch (field) {
            case 1:
                query = "UPDATE hms.users SET name = ? WHERE userID = ?";
                break;
            case 2:
                query = "UPDATE hms.users SET email = ? WHERE userID = ?";
                break;
            case 3:
                query = "UPDATE hms.users SET password = ? WHERE userID = ?";
                break;
            case 4:
                query = "UPDATE hms.users SET phoneNumber = ? WHERE userID = ?";
                break;
            default:
                System.out.println("❌ Invalid field! Allowed fields: name, email, password, phoneNumber");
                return false;
        }
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newValue);
            pstmt.setString(2, userID);
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                //System.out.println("✅ " + field + " updated successfully!");
                return true;
            } else {
                System.out.println("❌ Update failed! No user found with this ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void getAllUsers() {
        String sql = "SELECT * FROM hms.users";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
        	System.out.println(gray+bold+String.format("%-15s %-15s %-25s %-15s", "User ID", "Name", "Email", "Mobile Number"+reset));
        	System.out.println("-------------------------------------------------------------------------");
        	while (rs.next()) {
        	    System.out.println(String.format("%-15s %-15s %-25s %-15s",
        	            rs.getString("userID"),
        	            rs.getString("name"),
        	            rs.getString("email"),
        	            rs.getString("phoneNumber")));
        	}
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}