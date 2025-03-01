package com.hostels.db.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.hostels.db.connection.DBConnection;
public class MaintenaceDB {
	public static void insertRequest(String issueDescription) {
        String sql ="INSERT INTO hms.maintenance_requests(issue_description) VALUES (?)";
        try (Connection conn =DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setString(1, issueDescription);
            stmt.executeUpdate();
            //System.out.println("Request successfully stored in the database.");
            conn.setAutoCommit(true);
        }catch (SQLException e) {
            e.printStackTrace();
        }
	}
	public static void deleteRequest(int id) {
		String sql="delete from hms.maintenance_requests where request_id=?";
		try {
			Connection conn =DBConnection.getConnection();
			 PreparedStatement ps=conn.prepareStatement(sql);
			 ps.setInt(1,id);
			 int r=ps.executeUpdate();
			 conn.setAutoCommit(true);
			 if(r>0) {
				 System.out.println("Request Deleted Successfully");
			 }
			 else {
				 System.out.println("No record found with this request ID "+id);
			 }
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static void updateStatus(String status,int id) {
		String sql="update hms.maintenance_requests set status=? where request_id=?";
		try {
			Connection conn =DBConnection.getConnection();
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(2, id);
			ps.setString(1,status);
			int r=ps.executeUpdate();
			conn.setAutoCommit(true);
			if(r>0) {
				System.out.println("Status has been Updated");
			}
			else {
				System.out.println("No record found with this request ID "+id);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static void getAllRequests() {
	    try (Connection conn =DBConnection.getConnection()) {
	        String sql = "SELECT * FROM hms.maintenance_requests";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        System.out.println("\n===== List of Maintenance Requests =====");
	        while (rs.next()) {
	            int requestID = rs.getInt("request_id");
	            String issueDescription = rs.getString("issue_description");
	            String status = rs.getString("status");
	            System.out.println("Request ID: " + requestID);
	            System.out.println("Issue: " + issueDescription);
	            System.out.println("Status: " + status);
	            System.out.println("------------------------------------");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
