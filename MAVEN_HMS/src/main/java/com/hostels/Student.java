package com.hostels;
import java.util.*;

import com.hostels.db.user.MaintenaceDB;
import com.hostels.db.user.NotificationDB;
import com.hostels.db.user.UserDB;
public class Student {
    private static String studentID;
    private Hmsfees hmsFees;
    private Warden warden;
    private Maintenance ma;
    private EventManagement ev;
    private UserType us;
    Scanner sc=new Scanner(System.in);
    public Student(String studentID) {
        this.studentID = studentID;
        this.hmsFees = new Hmsfees(); 
    }
    /*
  public void manageStudent(){
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter Student ID: ");
	        String studentID = scanner.nextLine();
	        int totalFees=0;
	        int SHARING_ROOM_FEES=5000;
	        int SINGLE_ROOM_FEES=10000;
	        System.out.println("Enter room type (1 for Sharing Room, 2 for Single Room): ");
	        int roomType = scanner.nextInt();
	        if (roomType == 1) {
	            totalFees += SHARING_ROOM_FEES;
	        } else if (roomType == 2) {
	            totalFees += SINGLE_ROOM_FEES;
	        } else {
	            System.out.println("Invalid room type selected.");
	            scanner.close();
	            return;
	        }
	        Student student = new Student(studentID); 
  }
  */
//    public void viewRoomDetails() {
//        System.out.println("Room details for Student ID: " + studentID);
//    }
    public static void viewProfile(){
    	UserDB.ViewProfile(studentID);
    }
    public static void viewRecords() {
    	UserDB.getAllUsers();
    }
    public void setFoodPreference(Scanner scanner) {
        hmsFees.setFoodPreference(scanner, studentID);
    }
    public void payFees(Scanner scanner) {
        hmsFees.payFees(scanner, studentID);
    }
    public void viewFees(String studentID) {
        hmsFees.viewFees(studentID);
    }
    public void raiseComplaints() {
    	System.out.println("Enter the issue");
    	String s=sc.nextLine();
    	MaintenaceDB.insertRequest(s);
        System.out.println("Complaint raised successfully");   
    }
    public void RemoveComplaints() {
    	System.out.println("Enter the Request ID to delete Complaint");
    	int id=sc.nextInt();
    	MaintenaceDB.deleteRequest(id);
    }
    public void viewNotifications() {
    	NotificationDB.viewNotifications();
    }
    public void viewUpcomingEvents() {
    	ev.viewEvents();
    }
    public void profileUpdation() {
    	System.out.println("What do you want to update:\n1)Name\n2)Email\n3)Password\n4)MobileNumber");
    	int type=sc.nextInt();
    	sc.nextLine();
    	System.out.println("Enter the value to update");
    	String newVal=sc.nextLine();
    	UserDB.updateProfile(studentID,type,newVal);
    }
    public void contact() {
        System.out.println("Contact Admin at: admin@hostel.com");
        System.out.println("Contact Warden at: warden@hostel.com");
    }
}