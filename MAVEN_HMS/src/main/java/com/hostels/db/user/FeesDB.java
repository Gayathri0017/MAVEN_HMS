package com.hostels.db.user;
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
                       "    UPDATE SET f.totalFees = ?, f.paid = ? " +
                       "WHEN NOT MATCHED THEN " +
                       "    INSERT (studentID, totalFees, paid) VALUES (?, ?, ?)";

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
        String checkBalanceQuery = "SELECT balanceFees FROM hms.fees WHERE studentID = ?";
        String updateQuery = "UPDATE hms.fees SET balanceFees = balanceFees - ? WHERE studentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkBalanceQuery)) {
            checkStmt.setString(1, studentID);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                int balanceFees = rs.getInt("balanceFees");
                if (paymentAmount > balanceFees) {
                    System.out.println("❌ You are trying to pay extra. You only need to pay: ₹" + balanceFees);
                    return;
                }
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, paymentAmount);
                    updateStmt.setString(2, studentID);
                    int updatedRows = updateStmt.executeUpdate();
                    if (updatedRows > 0) {
                        System.out.println("✅ Payment successful: ₹" + paymentAmount);
                    } else {
                        System.out.println("❌ Payment failed. Please check student ID.");
                    }
                }
            } else {
                System.out.println("❌ No fee record found for this student ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch and display a student's fee details
    public static void viewFees(String studentID) {
        String query = "SELECT totalFees, balancefees FROM hms.fees WHERE studentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalFees = rs.getInt("totalFees");
                int remainingFees = rs.getInt("balancefees");
                int paidfees = totalFees - remainingFees;

                System.out.println("💰 Total Fees: ₹" + totalFees);
                System.out.println("💳 Paid : ₹" + paidfees);
                System.out.println("🧾 Balance Fees: ₹" + remainingFees);
                if (totalFees < paidfees) {
                    System.out.println(remainingFees + " returned to student");
                }

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
