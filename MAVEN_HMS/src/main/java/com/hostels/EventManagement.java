package com.hostels;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hostel_main.HostelManagementSystem;
class Event {
    String eventID;
    String eventName;
    String eventDate;
    String eventDescription;
    Event(String eventID, String eventName, String eventDate, String eventDescription) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
    }
}
public class EventManagement {
    private List<Event> events;
    private Scanner scanner;
    public EventManagement() {
        this.events = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
    public void manageEvents() {
        while (true) {
            System.out.println("\n----- Event Management -----");
            System.out.println("1) Create Event");
            System.out.println("2) View Events");
            System.out.println("3) Delete Event");
            System.out.println("4) Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    viewEvents();
                    break;
                case 3:
                   deleteEvent();
                   break;
                case 4:
                    System.out.println("Exiting event management.");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }
    private void createEvent() {
        System.out.print("Enter Event ID: ");
        String eventID = scanner.nextLine();
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
        System.out.print("Enter Event Date (YYYY-MM-DD): ");
        String eventDate = scanner.nextLine();
        System.out.print("Enter Event Description: ");
        String eventDescription = scanner.nextLine();
        Event newEvent = new Event(eventID, eventName, eventDate, eventDescription);
        events.add(newEvent);
        System.out.println("Event created successfully!");
    }
    public void viewEvents() {
        System.out.println("\n----- List of Events -----");
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }
        for (Event event : events) {
            System.out.printf("Event ID: %s, Name: %s, Date: %s, Description: %s%n",
                    event.eventID, event.eventName, event.eventDate, event.eventDescription);
        }
    }
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
}