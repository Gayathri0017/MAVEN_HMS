package com.hostels;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.hostels.db.user.EventDB;
import hostel_main.HostelManagementSystem;
public class EventManagement {
	static Scanner sc=new Scanner(System.in);
	 	String eventID;
	    String eventName;
	    String eventDate;
	    String eventDescription;
	    EventManagement(String eventID, String eventName, String eventDate, String eventDescription) {
	        this.eventID = eventID;
	        this.eventName = eventName;
	        this.eventDate = eventDate;
	        this.eventDescription = eventDescription;
	}
    public static void manageEvents() {
        while (true) {
            System.out.println("\n----- Event Management -----");
            System.out.println("1) Create Event");
            System.out.println("2) View Events");
            System.out.println("3) Delete Event");
            System.out.println("4) Exit");
            System.out.print("Enter your choice: ");
            int choice =sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    viewEvents();
                    break;
                case 3:
                   //deleteEvent();
                   break;
                case 4:
                    System.out.println("Exiting event management.");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }
    public static void createEvent() {
        System.out.print("Enter Event ID: ");
        String eventID = sc.nextLine();
        System.out.print("Enter Event Name: ");
        String eventName = sc.nextLine();
        System.out.print("Enter Event Date (YYYY-MM-DD): ");
        String eventDate = sc.nextLine();
        System.out.print("Enter Event Description: ");
        String eventDescription = sc.nextLine();
        EventDB.insertEvent(eventID,eventName,eventDate,eventDescription);
    }
    public static void viewEvents() {
        System.out.println("\n----- List of Events -----");
        EventDB.displayEvents();
    }
    /*
    private void deleteEvent() {
        System.out.print("Enter Event ID to delete: ");
        String eventID = scanner.nextLine();
        Event eventToDelete = null;
        for (Event event : events) {
            if (event.eventID.equals(eventID)) {
                eventToDelete = event;
                break;
            }
        }
        if (eventToDelete != null) {
            events.remove(eventToDelete);
            System.out.println("Event deleted successfully!");

        } else {
            System.out.println("Event not found.");
        }
    }
    */
}