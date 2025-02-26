package com.hostels.db.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hostels.db.connection.DBConnection;
public class RoomDB {
    public static List<String> getAllRoomsWithStatus() {
        List<String> rooms=new ArrayList<>();
        String query = "SELECT roomID, type, isOccupied FROM hms.rooms";
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement stmt=conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String roomID = rs.getString("roomID");
                String type = rs.getString("type");
                boolean isOccupied = rs.getInt("isOccupied") == 1;
                String status = isOccupied?"[Occupied]":"[Available]";
                rooms.add("Room "+roomID+"("+type+")"+status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    public static int allocateBed(String roomID) {
        String countQuery ="SELECT COUNT(*) FROM hms.beds WHERE roomID = ? AND isOccupied = 1";
        String query = "SELECT bedID FROM (SELECT bedID FROM hms.beds WHERE roomID = ? AND isOccupied = 0 ORDER BY bedID) WHERE ROWNUM = 1";
        String update = "UPDATE hms.beds SET isOccupied = 1 WHERE bedID = ?";
        try (Connection conn =DBConnection.getConnection();
            PreparedStatement countStmt = conn.prepareStatement(countQuery)) {
            countStmt.setString(1, roomID);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next() && countRs.getInt(1) >= 4) {
                //System.out.println("❌ Room " + roomID + " is fully occupied.");
                return -1;
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, roomID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int bedID = rs.getInt("bedID");
                    try (PreparedStatement updateStmt = conn.prepareStatement(update)) {
                        updateStmt.setInt(1, bedID);
                        updateStmt.executeUpdate();
                        //System.out.println("✅ Bed " + bedID + " in Room " + roomID + " has been booked.");
                        return bedID;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("❌ Failed to allocate bed in room " + roomID);
        return -1;
    }
    public static boolean vacateBed(String roomID, int bedNumber) {
        String query = "UPDATE hms.beds SET isOccupied = 0 WHERE roomID = ? AND bedID = ? AND isOccupied = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) { 
            stmt.setString(1, roomID);
            stmt.setInt(2, bedNumber);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean allocateGuestRoom(String roomID) {
        String query = "UPDATE hms.rooms SET isOccupied = 1 WHERE roomID = ? AND isOccupied = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, roomID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean vacateGuestRoom(String roomID) {
        String query = "UPDATE hms.rooms SET isOccupied = 0 WHERE roomID = ? AND isOccupied = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, roomID);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

