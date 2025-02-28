package com.hostels.db.user;
import com.hostels.db.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hostels.db.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeesDB {

    // Method to initialize fees for a student
    public static void initializeStudentFees(String studentID, int totalFees) {
        if (!studentExists(studentID)) {
            System.out.println("❌ Error: Student ID not found in users table.");
            return;
        }

        String query = "MERGE INTO hms.fees f " +
                       "USING (SELECT ? AS studentID FROM dual) temp " +
                       "ON (f.studentID = temp.studentID) " +
                       "WHEN MATCHED THEN " +
                       "    UPDATE SET f.totalFees = ?, f.balanceFees = ? " +
                       "WHEN NOT MATCHED THEN " +
                       "    INSERT (studentID, totalFees, balanceFees) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentID);
            pstmt.setInt(2, totalFees);
            pstmt.setInt(3, 0); // Initial balance is 0
            pstmt.setString(4, studentID);
            pstmt.setInt(5, totalFees);
            pstmt.setInt(6, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a student exists in the database
    private static boolean studentExists(String studentID) {
        String query = "SELECT 1 FROM hms.users WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update fees when payment is made
    public static void payFees(String studentID, int paymentAmount) {
        String query = "UPDATE hms.fees SET balanceFees = balanceFees + ? WHERE studentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, paymentAmount);
            pstmt.setString(2, studentID);
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("✅ Payment successful: ₹" + paymentAmount);
            } else {
                System.out.println("❌ Payment failed. Please check student ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch and display a student's fee details
    public static void viewFees(String studentID) {
        String query = "SELECT totalFees, balanceFees FROM hms.fees WHERE studentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalFees = rs.getInt("totalFees");
                int balanceFees = rs.getInt("balanceFees");
                int remainingFees = totalFees - balanceFees;

                System.out.println("💰 Total Fees: ₹" + totalFees);
                System.out.println("💳 Paid Fees: ₹" + balanceFees);
                System.out.println("🧾 Remaining Fees: ₹" + remainingFees);

                if (remainingFees > 0) {
                    System.out.println("⚠️ You still need to pay: ₹" + remainingFees);
                } else {
                    System.out.println("✅ All fees have been paid.");
                }
            } else {
                System.out.println("❌ No fee record found for this student ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
