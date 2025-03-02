package com.hostels;
import java.util.Scanner;
import com.hostels.db.user.EventDB;
public class EventManagement {
    public static void createEvent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Event ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Event Name: ");
        String event_name = sc.nextLine();
        System.out.print("Enter Event Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter Event Description: ");
        String description = sc.nextLine();
        EventDB.insertEvent(id, event_name, date, description);
    }
    public static void viewEvents() {
        System.out.println("Fetching events...");
        EventDB.displayEvents();
    }
    public static void updateEvent(Scanner sc) {
        System.out.print("Enter Event ID to update: ");
        String id = sc.nextLine();
        System.out.print("Enter new Event Name: ");
        String newEventName = sc.nextLine();
        System.out.print("Enter new Event Date (YYYY-MM-DD): ");
        String newDate = sc.nextLine();
        System.out.print("Enter new Event Description: ");
        String newDescription = sc.nextLine();
        EventDB.updateEvent(id, newEventName, newDate, newDescription);
    }
    public static void deleteEvent(Scanner sc){
    	sc.nextLine();
        System.out.print("Enter Event ID to delete: ");
        String id = sc.nextLine();
        EventDB.deleteEvent(id);
    }
}