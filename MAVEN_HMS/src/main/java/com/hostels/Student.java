package com.hostels;
import java.util.*;
public class Student {
    private String studentID;
    private Hmsfees hmsFees;
    private Warden warden;
    private Maintenance ma;
    private Notification no;
    private EventManagement ev;
    private UserType us;
    Scanner sc=new Scanner(System.in);
    public Student(String studentID) {
        this.studentID = studentID;
        this.hmsFees = new Hmsfees(); 
    }
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
	        /*
	        while (true) {
	            System.out.println("\n----- Student Portal -----");
	            System.out.println("1) View Room Details");
	            System.out.println("2) Set Food Preference");
	            System.out.println("3) Pay Fees");
	            System.out.println("4) View Fees");
	            System.out.println("5) Raise Complaints");
	            System.out.println("6) View Notifications");
	            System.out.println("7) View Upcoming Events");
	            System.out.println("8) Update Profile");
	            System.out.println("9) Contact Warden");
	            System.out.println("10) Exit");
	            System.out.print("Enter your choice: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine();
	            switch (choice) {
	                case 1:
	                    student.viewRoomDetails();
	                    break;
	                case 2:
	                    student.setFoodPreference(scanner);
	                    break;
	                case 3:
	                    student.payFees(scanner);
	                    break;
	                case 4:
	                    student.viewFees(studentID);
	                    break;
	                case 5:
	                    student.raiseComplaints();
	                    break;
	                case 6:
	                    student.viewNotifications();
	                    break;
	                case 7:
	                    student.viewUpcomingEvents();
	                    break;
	                case 8:
	                    //student.profileUpdation();
	                    break;
	                case 9:
	                    student.contact();
	                    break;
	                case 10:
	                    System.out.println("Exiting Student Portal...");
	                    scanner.close();
	                    return;
	                default:
	                    System.out.println("Invalid choice! Please try again.");
	            }
	        }
	        */
  }
    public void viewRoomDetails() {//no
        System.out.println("Room details for Student ID: " + studentID);
    }
    public void setFoodPreference(Scanner scanner) {//yes
        hmsFees.setFoodPreference(scanner, studentID);
    }
    public void payFees(Scanner scanner) {//yes
        hmsFees.payFees(scanner, studentID);
    }
    public void viewFees(String studentID) {//yes
        hmsFees.viewFees(studentID);
    }
    public void raiseComplaints() {
    	System.out.println("Enter the issue");
    	String s=sc.nextLine();
        System.out.println("Complaint raised successfully");   
    }
    public void viewNotifications() {//yes
    	no.viewMessages();
        System.out.println("No new notifications.");
    }
    public void viewUpcomingEvents() {
    	ev.viewEvents();//error coming
        System.out.println("Upcoming Event: Hostel Cultural Fest on Sunday!");
    }
//    public void profileUpdation() {
//    	us.updateProfile();
//        //System.out.println("Profile updated for Student ID: " + studentID);
//    }
    public void contact() {
        System.out.println("Contact Admin at: wAdmin@hostel.com");
        System.out.println("Contact Warden at: warden@hostel.com");
    }
public static void studentOperations(Scanner sc) {
    	System.out.println("enter id:");
    	String studentID=sc.nextLine();
    	Student hos = new Student(studentID);
        hos.manageStudent();
}
}