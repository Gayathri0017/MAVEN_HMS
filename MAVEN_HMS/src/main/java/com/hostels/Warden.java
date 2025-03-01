package com.hostels;
import com.hostels.Room;
import java.util.Scanner;
public class Warden {
    private Scanner sc = new Scanner(System.in);
    private Room roomManager;
    private VisitorManagement visitorManager;
    public Warden() {
        roomManager = new Room();
        visitorManager = new VisitorManagement();
    }
    public void manageRooms() {
        while (true) {
            System.out.println("\n----- Warden Room Management -----");
            System.out.println("1) View All Rooms");
            System.out.println("2) Allocate a Room");
            System.out.println("3) Vacate a Room");
            System.out.println("4) Log Visitors");
            System.out.println("5) View Visitor Details");
            System.out.println("6) Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    roomManager.displayRooms();
                    break;

                case 2:
                    System.out.println("Do you want to allocate:\n1) Single room (C floor)\n2) Shared room (4 beds per room - A/B floors)");
                    int ch = sc.nextInt();
                    sc.nextLine();
                    if (ch == 1) { // Allocate Single Room
                        System.out.print("Enter room label to allocate (e.g., C1, C2... C20): ");
                        String roomLabel = sc.nextLine();
                        roomManager.allocateGuestRoom(roomLabel);
                    } 
                    else if (ch == 2) { // Allocate Shared Room
                        System.out.print("Enter room label to allocate (e.g., A1, B5, etc.): ");
                        String roomLabel = sc.nextLine();
                        roomManager.allocateRoom(roomLabel);
                    } 
                    else {
                        System.out.println("Invalid choice! Please select 1 or 2.");
                    }
                    break;
                case 3:
                    System.out.println("Do you want to vacate:\n1) Single room (C floor)\n2) Shared room (4 beds per room - A/B floors)");
                    int c = sc.nextInt();
                    sc.nextLine();

                    if (c == 1) {
                        System.out.print("Enter room label to vacate (e.g., C1, C2... C20): ");
                        String vacateRoomLabel = sc.nextLine();
                        roomManager.vacateGuestRoom(vacateRoomLabel);
                    } 
                    else if (c == 2) {
                        System.out.print("Enter room label to vacate (e.g., A1, B5, etc.): ");
                        String vacateRoomLabel = sc.nextLine();
                        System.out.print("Enter bed number (1-4) to vacate: ");
                        int bedNumber = sc.nextInt();
                        sc.nextLine();
                        roomManager.vacateRoom(vacateRoomLabel, bedNumber);
                    } 
                    else {
                        System.out.println("Invalid choice! Please select 1 or 2.");
                    }
                    break;

                case 4:
                    System.out.println("1) Log entry of visitor\n2) Log exit of visitor");
                    int vis = sc.nextInt();
                    sc.nextLine();
                    if (vis == 1) {
                        visitorManager.enterDetails();
                    } 
                    else if (vis == 2) {
                        visitorManager.exitDetails();
                    } 
                    else {
                        System.out.println("Enter a valid value");
                    }
                    break;

                case 5:
                    visitorManager.displayDetails();
                    break;

                case 6:
                    System.out.println("Exiting Warden Panel...");
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }
}