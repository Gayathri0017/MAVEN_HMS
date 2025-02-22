package com.hostels;
import java.util.ArrayList;
import java.util.Scanner;
import com.hostels.db.user.UserDB;
import com.hostels.db.user.MaintenaceDB;
import hostel_main.HostelManagementSystem;
public class Maintenance{
	//private static ArrayList<Request> requests = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public void manageRequests() {
        while (true) {
            System.out.println("\nRequest Management System");
            System.out.println("1. Raise a Request (Student)");
            System.out.println("2. View Requests (Admin/Warden)");
            System.out.println("3. Update Request Status (Warden)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    raiseRequest();
                    break;
                case 2:
                    viewRequests();
                    break;
                case 3:
                    updateRequestStatus();
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void raiseRequest() {
        System.out.print("Enter issue description: ");
        String issueDescription = scanner.nextLine();
        MaintenaceDB.insertRequest(issueDescription);
    }
    public void viewRequests() {
    	MaintenaceDB.getAllRequests();
    }
    private void updateRequestStatus() {
    	
    }
}
class Request{
    int requestID;
    String issueDescription;
    String status;
    public Request(int requestID, String issueDescription) {
        this.requestID = requestID;
        this.issueDescription = issueDescription;
        this.status = "Pending";
    }
}
