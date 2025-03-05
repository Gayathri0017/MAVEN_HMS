package com.hostels.db.user;
import com.hostels.db.connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class FeesDB {
	static String gray = "\033[90m";
	static String bold="\033[1m";
	static String reset= "\033[0m"; 
    public static void initializeStudentFees(String studentID, int totalFees) {
        if (!studentExists(studentID)){
            System.out.println("❌ Error: Student ID not found in users table.");
            return;
        }
        String query="MERGE INTO hms.fees f " +
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
            pstmt.setInt(3, totalFees);
            pstmt.setString(4, studentID);
            pstmt.setInt(5, totalFees);
            pstmt.setInt(6, totalFees);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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
    public static void payFees(String studentID,int paymentAmount) {
        String query ="SELECT balanceFees FROM hms.fees WHERE studentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                int balanceFees = rs.getInt("balanceFees");
                if (balanceFees == 0) {
                    System.out.println("✅ All fees are already paid. No payment required.");
                    return;
                }
                int extraAmount = paymentAmount - balanceFees;
                int newBalance = Math.max(balanceFees - paymentAmount, 0);
                String updateQuery = "UPDATE hms.fees SET balanceFees = ? WHERE studentID = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, newBalance);
                    updateStmt.setString(2, studentID);
                    updateStmt.executeUpdate();
                }
                System.out.println("Payment Successful: ₹" + paymentAmount);
                if (extraAmount>0) {
                    System.out.println("Extra Amount Returned: ₹" + extraAmount);
                }
                System.out.println("Remaining Fees: ₹" + newBalance);
            } else {
                System.out.println("No fee record found for this student ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void viewFees(String studentID) {
        String query = "SELECT totalFees, balanceFees FROM hms.fees WHERE studentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                int totalFees = rs.getInt("totalFees");
                int remainingFees = rs.getInt("balanceFees");
                int paidFees = totalFees - remainingFees;
                System.out.println("Total Fees: ₹" + totalFees);
                System.out.println("Paid: ₹" + paidFees);
                if (remainingFees == 0) {
                    System.out.println("All fees have been paid.");
                }else if (remainingFees > 0) {
                    System.out.println("Balance Fees: ₹" + remainingFees);
                    System.out.println("You still need to pay: ₹" + remainingFees);
                }
            } else {
                System.out.println("❌ No fee record found for this student ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void viewFees() {
    	String sql="select * from hms.fees";
    	try 
    	{
    		Connection conn = DBConnection.getConnection();
    		PreparedStatement ps= conn.prepareStatement(sql);
    		ResultSet rs=ps.executeQuery();
    		System.out.println(gray+bold+String.format("%-15s %-15s %-14s %-15s %-15s", "Student ID", "Total Fees", "Fees Paid", "Balance Fees", "Remaining Fees"+reset));
    		System.out.println("===============================================================================");
    		while (rs.next()) {
    		    int totalFees = rs.getInt("totalFees");
    		    int remainingFees = rs.getInt("balancefees");
    		    int paidfees = totalFees - remainingFees;
    		    String stuId = rs.getString("StudentID");

    		    System.out.println(String.format("%-15s ₹%-14d ₹%-14d ₹%-14d ₹%-14d", stuId, totalFees, paidfees, remainingFees, totalFees - paidfees));
    		}
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }
}