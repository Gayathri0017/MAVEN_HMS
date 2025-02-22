package com.hostels;
import java.util.*;
class Rooms {
    private static final int CAPACITY = 60;
    private static final int ROOMS_PER_FLOOR = 20;
    private static final int BEDS_PER_ROOM = 4;
    private static final int GUEST_ROOM_START = 41;
    
    private Map<String, List<Boolean>> rooms;
    private Set<String> guestRooms;
    private int bookedBeds;
    private int bookedRooms;
    
    Rooms() {
        rooms = new HashMap<>();
        guestRooms = new HashSet<>();
        bookedBeds = 0;
        bookedRooms = 0;
        
        initializeRooms();
    }
    
    private void initializeRooms() {
        for (char floor = 'A'; floor <= 'C'; floor++) {
            for (int i = 1; i <= ROOMS_PER_FLOOR; i++) {
                String roomLabel = floor + Integer.toString(i);
                if (floor == 'C') {
                    guestRooms.add(roomLabel);
                } else {
                    rooms.put(roomLabel, new ArrayList<>(Collections.nCopies(BEDS_PER_ROOM, false)));
                }
            }
        }
    }
    
    public void displayRooms() {
        System.out.println("-----Available Rooms and Beds-----");
        for (char floor = 'A'; floor <= 'C'; floor++) {
            System.out.println("Floor " + floor);
            for (int i = 1; i <= ROOMS_PER_FLOOR; i++) {
                String roomLabel = floor + Integer.toString(i);
                if (floor == 'C') {
                    System.out.print(guestRooms.contains(roomLabel) ? roomLabel + " [X]  " : roomLabel + " [O]  ");
                } else {
                    System.out.print(roomLabel + " [");
                    List<Boolean> beds = rooms.get(roomLabel);
                    for (int b = 0; b < BEDS_PER_ROOM; b++) {
                        System.out.print(beds.get(b) ? "X" : "O");
                        if (b < BEDS_PER_ROOM - 1) System.out.print(",");
                    }
                    System.out.print("]  ");
                }
                if (i % 10 == 0) System.out.println();
            }
            System.out.println("--------------------------------------------------");
        }
    }
    
    public void allocateRoom(String roomLabel) {
        if (!rooms.containsKey(roomLabel)) {
            System.out.println("Invalid room number, please choose a valid number.");
            return;
        }
        List<Boolean> beds = rooms.get(roomLabel);
        for (int i = 0; i < BEDS_PER_ROOM; i++) {
            if (!beds.get(i)) {
                beds.set(i, true);
                bookedBeds++;
                System.out.println("Bed " + (i + 1) + " in room " + roomLabel + " is booked.");
                return;
            }
        }
        System.out.println(roomLabel + " is fully occupied.");
    }
    
    public void vacateRoom(String roomLabel, int bedNumber) {
        if (!rooms.containsKey(roomLabel)) {
            System.out.println("Invalid room number, please choose a valid number.");
            return;
        }
        List<Boolean> beds = rooms.get(roomLabel);
        if (!beds.get(bedNumber - 1)) {
            System.out.println("Bed " + bedNumber + " in room " + roomLabel + " is already vacant.");
        } else {
            beds.set(bedNumber - 1, false);
            bookedBeds--;
            System.out.println("Bed " + bedNumber + " in room " + roomLabel + " vacated successfully.");
        }
    }
    
    public void allocateGuestRoom(String roomLabel) {
        if (!guestRooms.contains(roomLabel)) {
            System.out.println("Invalid room! Only C1-C20 can be booked as full rooms.");
            return;
        }
        if (guestRooms.contains(roomLabel)) {
            System.out.println("Room " + roomLabel + " is already booked.");
        } else {
            guestRooms.add(roomLabel);
            bookedRooms++;
            System.out.println("Room " + roomLabel + " booked successfully!");
        }
    }
    
    public void vacateGuestRoom(String roomLabel) {
        if (!guestRooms.contains(roomLabel)) {
            System.out.println("Invalid room! Only C1-C20 can be vacated as full rooms.");
            return;
        }
        guestRooms.remove(roomLabel);
        bookedRooms--;
        System.out.println("Room " + roomLabel + " vacated successfully!");
    }
}

