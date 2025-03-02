package com.hostels;
import com.hostels.Room;
import com.hostels.db.user.UserDB;
import com.hostels.db.user.VisitorDB;
import java.util.Scanner;
public class Warden {
    private Scanner sc = new Scanner(System.in);
    //private Room roomManager;
    //private static VisitorManagement visitorManager;
    public void manageRooms() {
        while (true) {
            System.out.println("1) View All Rooms");
            System.out.println("2) Allocate a Room");
            System.out.println("3) Vacate a Room");
            System.out.println("4) Log Visitors");
            System.out.println("5) View Visitor Details");
            System.out.println("6) View Student records");
            System.out.println("7) Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Room.displayRooms();
                    break;
                case 2:
                    System.out.println("Do you want to allocate:\n1) Single room (C floor)\n2) Shared room (4 beds per room - A/B floors)");
                    int ch = sc.nextInt();
                    sc.nextLine();
                    if (ch == 1) {
                        System.out.print("Enter room label to allocate (e.g., C1, C2... C20): ");
                        String roomLabel = sc.nextLine();
                        Room.allocateGuestRoom(roomLabel);
                    } 
                    else if (ch == 2) {
                        System.out.print("Enter room label to allocate (e.g., A1, B5, etc.): ");
                        String roomLabel = sc.nextLine();
                        Room.allocateRoom(roomLabel);
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
                        Room.vacateGuestRoom(vacateRoomLabel);
                    } 
                    else if (c == 2) {
                        System.out.print("Enter room label to vacate (e.g., A1, B5, etc.): ");
                        String vacateRoomLabel = sc.nextLine();
                        System.out.print("Enter bed number (1-4) to vacate: ");
                        int bedNumber = sc.nextInt();
                        sc.nextLine();
                        Room.vacateRoom(vacateRoomLabel, bedNumber);
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
                    	VisitorManagement.enterDetails();
                    } 
                    else if (vis == 2) {
                    	VisitorManagement.exitDetails();
                    } 
                    else {
                        System.out.println("Enter a valid value");
                    }
                    break;
                case 5:
                	System.out.println("1)View All Visitor Records\n2)View Visitor by Id");
                	int v=sc.nextInt();
                	if(v==1) {
                		VisitorDB.displayAllVisitor();
                	}
                	else if(v==2) {
                    VisitorManagement.displayDetails();
                	}
                    break;
//                case 6:
//                	 UserDB.getAllUsers();
//                	break;
                case 7:
                    System.out.println("Exiting Warden Panel...");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }
}