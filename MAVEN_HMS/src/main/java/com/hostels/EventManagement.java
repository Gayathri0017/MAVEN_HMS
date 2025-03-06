package com.hostels;
import java.util.Scanner;
import com.hostels.db.user.EventDB;
public class EventManagement {
    public static void createEvent() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Event ID: ");
        String id=sc.nextLine();
        System.out.print("Enter Event Name: ");
        String event_name=sc.nextLine();
        System.out.print("Enter Event Date (YYYY-MM-DD): ");
        String date=sc.nextLine();
        System.out.print("Enter Event Description: ");
        String description=sc.nextLine();
        EventDB.insertEvent(id, event_name, date, description);
    }
    public static void viewEvents() {
        System.out.println("Fetching events...");
        EventDB.displayEvents();
    }
    public static void updateEvent(Scanner sc) {
        System.out.print("Enter Event ID to update: ");
        sc.nextLine();
        String id=sc.nextLine();
        System.out.println("Select the field to update:");
        System.out.println("1) Event Name");
        System.out.println("2) Event Date (YYYY-MM-DD)");
        System.out.println("3) Event Description");
        System.out.print("Enter choice: ");
        int choice=sc.nextInt();
        sc.nextLine(); 
        int column = 0;
        switch (choice) {
            case 1:
                column=1;
                break;
            case 2:
                column=2;
                break;
            case 3:
                column=3;
                break;
            default:
                System.out.println("Invalid choice! Try again.");
                return;
        }
        System.out.print("Enter new value: ");
        String newValue = sc.nextLine();
        EventDB.updateEvent(id,column,newValue);
    }
    public static void deleteEvent(Scanner sc){
    	sc.nextLine();
        System.out.print("Enter Event ID to delete: ");
        String id = sc.nextLine();
        EventDB.deleteEvent(id);
    }
}