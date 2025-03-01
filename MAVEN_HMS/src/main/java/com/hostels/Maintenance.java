package com.hostels;
import java.util.ArrayList;
import java.util.Scanner;
import com.hostels.db.user.UserDB;
import com.hostels.db.user.MaintenaceDB;
import hostel_main.HostelManagementSystem;
public class Maintenance{
    private static Scanner scanner = new Scanner(System.in);
    public void raiseRequest() {
        System.out.print("Enter issue description: ");
        String issueDescription = scanner.nextLine();
        MaintenaceDB.insertRequest(issueDescription);
    }
    public void viewRequests() {
    	MaintenaceDB.getAllRequests();
    }
}
//class Request{
//    int requestID;
//    String issueDescription;
//    String status;
//    public Request(int requestID, String issueDescription) {
//        this.requestID = requestID;
//        this.issueDescription = issueDescription;
//        this.status = "Pending";
//    }
//}