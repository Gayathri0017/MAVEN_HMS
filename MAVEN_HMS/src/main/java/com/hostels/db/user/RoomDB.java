package com.hostels.db.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hostels.db.connection.DBConnection;
public class RoomDB {
    public static List<String> getAllRoomsWithStatus() {
        List<String> rooms = new ArrayList<>();
        String query = "SELECT roomID,isOccupied FROM hms.rooms";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String roomID = rs.getString("roomID");
                //String type = rs.getString("type");
                boolean isOccupied = rs.getInt("isOccupied") == 1;
                String status = isOccupied ? "[X]" : " ";
                rooms.add(roomID+" "+status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static int allocateBed(String roomID) {
        String countQuery = "SELECT COUNT(*) FROM hms.beds WHERE roomID = ? AND isOccupied = 1";
        String selectBedQuery = "SELECT bedID FROM (SELECT bedID FROM hms.beds WHERE roomID = ? AND isOccupied = 0 ORDER BY bedID) WHERE ROWNUM = 1";
        String updateBedQuery = "UPDATE hms.beds SET isOccupied = 1 WHERE bedID = ?";
        String updateRoomQuery = "UPDATE hms.rooms SET isOccupied = 1 WHERE roomID = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Check if the room is fully occupied
            try (PreparedStatement countStmt = conn.prepareStatement(countQuery)) {
                countStmt.setString(1, roomID);
                ResultSet countRs = countStmt.executeQuery();
                if (countRs.next() && countRs.getInt(1) >= 4) {
                    conn.rollback();
                    return -1; // Room fully occupied
                }
            }

            // Find an available bed
            int bedID = -1;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectBedQuery)) {
                selectStmt.setString(1, roomID);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    bedID = rs.getInt("bedID");
                }
            }

            if (bedID != -1) {
                // Allocate the bed
                try (PreparedStatement updateBedStmt = conn.prepareStatement(updateBedQuery)) {
                    updateBedStmt.setInt(1, bedID);
                    updateBedStmt.executeUpdate();
                }

                // Check again if all beds are now occupied, then mark room as occupied
                try (PreparedStatement countStmt = conn.prepareStatement(countQuery)) {
                    countStmt.setString(1, roomID);
                    ResultSet countRs = countStmt.executeQuery();
                    if (countRs.next() && countRs.getInt(1) == 4) {
                        try (PreparedStatement updateRoomStmt = conn.prepareStatement(updateRoomQuery)) {
                            updateRoomStmt.setString(1, roomID);
                            updateRoomStmt.executeUpdate();
                        }
                    }
                }

                conn.commit();
                return bedID;
            }
            
            conn.rollback(); // Rollback if no available bed found
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean vacateBed(String roomID, int bedNumber) {
        String vacateBedQuery = "UPDATE hms.beds SET isOccupied = 0 WHERE roomID = ? AND bedID = ? AND isOccupied = 1";
        String countOccupiedBedsQuery = "SELECT COUNT(*) FROM hms.beds WHERE roomID = ? AND isOccupied = 1";
        String updateRoomQuery = "UPDATE hms.rooms SET isOccupied = 0 WHERE roomID = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Vacate the bed
            try (PreparedStatement vacateStmt = conn.prepareStatement(vacateBedQuery)) {
                vacateStmt.setString(1, roomID);
                vacateStmt.setInt(2, bedNumber);
                int rowsAffected = vacateStmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return false; // Bed was not occupied
                }
            }

            // Check if at least one bed is now available
            try (PreparedStatement countStmt = conn.prepareStatement(countOccupiedBedsQuery)) {
                countStmt.setString(1, roomID);
                ResultSet countRs = countStmt.executeQuery();
                if (countRs.next() && countRs.getInt(1) < 4) { // If at least one bed is free
                    try (PreparedStatement updateRoomStmt = conn.prepareStatement(updateRoomQuery)) {
                        updateRoomStmt.setString(1, roomID);
                        updateRoomStmt.executeUpdate(); // Set room as available
                    }
                }
            }

            conn.commit();
            return true;
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

