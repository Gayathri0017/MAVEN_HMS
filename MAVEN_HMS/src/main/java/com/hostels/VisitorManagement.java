package com.hostels;
import com.hostels.db.user.UserDB;
import com.hostels.db.user.VisitorDB;
import java.util.Scanner;
public class VisitorManagement{
    private static Scanner sc = new Scanner(System.in);
    public static void enterDetails() {
        System.out.print("Enter Visitor Name: ");
        String name = sc.nextLine();
        System.out.print("Enter InTime (HH:MM): ");
        String inTime = sc.nextLine();
        int visitorId=VisitorDB.insertVisitor(name, inTime);
        if (visitorId!=-1){
            System.out.println("Visitor entry recorded. Visitor ID: " + visitorId);
        } else{
            System.out.println("Error inserting visitor.");
        }
    }
    public static void exitDetails() {
        System.out.print("Enter Visitor ID for exit: ");
        int visitorId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter OutTime (HH:MM): ");
        String outTime = sc.nextLine();
        VisitorDB.updateExitTime(visitorId, outTime);
        System.out.println("Visitor exit recorded successfully.");
    }
    public static void displayDetails(int id) {
        VisitorDB.displayVisitor(id);
    }
    public static void displayDetails() {
    	VisitorDB.displayVisitor();
    }
}

