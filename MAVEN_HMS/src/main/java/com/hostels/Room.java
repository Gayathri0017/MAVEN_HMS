package com.hostels;
import com.hostels.db.user.RoomDB;
import java.util.List;
public class Room{
    public static void displayRooms() {
    	int c=0;
        List<String> availableRooms = RoomDB.getAllRoomsWithStatus();
        System.out.println("----- Available Rooms -----");
        for (String room : availableRooms){
            System.out.printf("%-10s",room);
            c++;
            if(c%10==0) {
            	System.out.println();
            }
        }
    }
    public static void allocateRoom(String roomID) {
        int bedID = RoomDB.allocateBed(roomID);
        if (bedID != -1) {
            System.out.println("Bed " +bedID+" in Room "+roomID+" has been booked.");
        }else {
            System.out.println("Room "+roomID+" is fully occupied.");
        }
    }
    public static void vacateRoom(String roomID, int bedNumber) {
        boolean success = RoomDB.vacateBed(roomID, bedNumber);
        if (success) {
            System.out.println("Bed "+bedNumber+"in Room " + roomID+"vacated successfully.");
        } else {
            System.out.println("Bed "+bedNumber+" is already vacant or does not exist.");
        }
    }
    public static void allocateGuestRoom(String roomID) {
        boolean success = RoomDB.allocateGuestRoom(roomID);
        if (success) {
            System.out.println("Room "+roomID+" has been allocated.");
        } else {
            System.out.println("Room "+roomID+ " is already occupied or does not exist.");
        }
    }
    public static void vacateGuestRoom(String roomID) {
        boolean success = RoomDB.vacateGuestRoom(roomID);
        if (success) {
            System.out.println("Room "+roomID+" has been vacated.");
        } else {
            System.out.println("Room "+roomID+" is already vacant or does not exist.");
        }
    }
}