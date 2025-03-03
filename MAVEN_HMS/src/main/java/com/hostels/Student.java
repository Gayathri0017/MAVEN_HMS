package com.hostels;
import java.util.Scanner;
import com.hostels.db.user.MaintenaceDB;
import com.hostels.db.user.NotificationDB;
import com.hostels.db.user.UserDB;
public class Student extends UserType {
	 static String reset= "\033[0m"; 
	 static String blue="\033[34m";
	 static String purple= "\033[35m";
	 static String cyan="\033[36m";
	 static String bold="\033[1m";
    private Hmsfees hmsFees;
    private EventManagement eventManagement;
    private Scanner sc = new Scanner(System.in);
    public Student(String studentID) {
        super(studentID);
        this.hmsFees = new Hmsfees();
        this.eventManagement = new EventManagement();
    }
    private void printHeader(String title){
    	System.out.println("===========================");
        System.out.println(cyan+bold+"     🔹 "+ title + " 🔹"+reset);
        System.out.println("===========================");
    }
    @Override
    public void specificActions() {
    	int choice=0;
    	do {
    	printHeader("Student Panel");
        System.out.println("1. View Profile");
        System.out.println("2. View Fees");
        System.out.println("3. Pay Fees");
        System.out.println("4. Raise Complaint");
        System.out.println("5. Remove Complaint");
        System.out.println("6. View Notifications");
        System.out.println("7. View Upcoming Events");
        System.out.println("8. Update Profile");
        System.out.println("9. Contact Admin/Warden");
        System.out.println("10. Exit");
        System.out.println("Enter your choice: ");
        choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
            	viewFees();
            	break;
            case 3:
                payFees();
                break;
            case 4:
                raiseComplaints();
                break;
            case 5:
            	removeComplaints();
            	break;
            case 6:
                viewNotifications();
                break;
            case 7:
                viewUpcomingEvents();
                break;
            case 8:
                updateProfile();
                break;
            case 9:
                contact();
                break;
            case 10:
            	System.out.println("Exitinmg Student Panel..");
            	break;
            default:
                System.out.println("❌ Invalid choice. Please try again.");
        }
    	}while(choice!=10);
    }
    public void viewProfile() {
        UserDB.ViewProfile(userID);
    }
    public static void viewRecords() {
        UserDB.getAllUsers();
    }
    public void setFoodPreference() {
        hmsFees.setFoodPreference(sc, userID);
    }
    public void payFees() {
        hmsFees.payFees(sc, userID);
    }
    public void viewFees() {
        hmsFees.viewRecord(userID);
    }
    public void raiseComplaints() {
        System.out.print("Enter the issue: ");
        String issue = sc.nextLine();
        MaintenaceDB.insertRequest(issue);
        System.out.println("✅ Complaint raised successfully.");
    }
    public void removeComplaints() {
        System.out.print("Enter the Request ID to delete the complaint: ");
        int id = sc.nextInt();
        sc.nextLine();
        MaintenaceDB.deleteRequest(id);
    }
    public void viewNotifications() {
        NotificationDB.viewNotifications();
    }
    public void viewUpcomingEvents() {
        eventManagement.viewEvents();
    }
    public void updateProfile() {
        System.out.println("What do you want to update:\n1) Name\n2) Email\n3) Password\n4) Mobile Number");
        int type = sc.nextInt();
        sc.nextLine();
        boolean updated = false;

        while (!updated) {
            System.out.print("Enter the new value: ");
            String newVal = sc.nextLine();

            switch (type) {
                case 1:
                    UserDB.updateProfile(userID, type, newVal);
                    updated = true;
                    break;
                case 2:
                    if (isValidEmail(newVal)) {
                        UserDB.updateProfile(userID, type, newVal);
                        System.out.println("✔️ Email updated successfully!");
                        updated = true;
                    } else {
                        System.out.println("❌ Invalid email format. Try again.");
                    }
                    break;
                case 3:
                    if (isValidPassword(newVal)) {
                        UserDB.updateProfile(userID, type, newVal);
                        System.out.println("✔️ Password updated successfully!");
                        updated = true;
                    } else {
                        System.out.println("❌ Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a digit, and a special character.");
                    }
                    break;
                case 4:
                    if (isValidPhoneNumber(newVal)) {
                        UserDB.updateProfile(userID, type, newVal);
                        System.out.println("✔️ Mobile number updated successfully!");
                        updated = true;
                    } else {
                        System.out.println("❌ Invalid phone number! Must be exactly 10 digits.");
                    }
                    break;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }
    public void contact() {
        System.out.println("Contact Admin at: admin@hostel.com");
        System.out.println("Contact Warden at: warden@hostel.com");
    }
}
