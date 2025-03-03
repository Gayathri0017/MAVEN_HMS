package com.hostels;
import com.hostels.db.user.FeesDB;
import com.hostels.db.user.UserDB;
import com.hostels.db.user.VisitorDB;
import java.util.Scanner;
public class Admin extends UserType {
    private Scanner sc = new Scanner(System.in);
    public Admin() {
        super("admin");
    }
    @Override
    public void specificActions() {
        adminOperations();
    }
    private void adminOperations() {
        Hmsfees f = new Hmsfees();
        Maintenance m = new Maintenance();
        int adminChoice;
        do {
            printHeader("Admin Panel");
            System.out.println("1) Manage Event");
            System.out.println("2) View Fees Records");
            System.out.println("3) Manage Complaints");
            System.out.println("4) Manage Notifications");
            System.out.println("5) View Student Records");
            System.out.println("6) Exit");
            System.out.print("Enter choice: ");
            adminChoice = sc.nextInt();
            sc.nextLine();
            switch (adminChoice) {
                case 1:
                    System.out.println("1) Create Event\n2) View Events\n3) Update Event\n4) Delete Events");
                    int ev = sc.nextInt();
                    if (ev == 1) {
                        EventManagement.createEvent();
                    } else if (ev == 2) {
                        EventManagement.viewEvents();
                    } else if (ev == 3) {
                        EventManagement.updateEvent(sc);
                    } else if (ev == 4) {
                        EventManagement.deleteEvent(sc);
                    }
                    break;
                case 2:
                    System.out.println("1) Check all students Records\n2) Check Particular student Records");
                    int num = sc.nextInt();
                    sc.nextLine();
                    if (num == 1) {
                        FeesDB.viewAllRecords();
                    } else if (num == 2) {
                        System.out.println("Enter student Id to get fee status");
                        String index = sc.nextLine();
                        f.viewFees(index);
                    }
                    break;
                case 3:
                    System.out.println("1) View Complaints\n2) Update the Complaints Status");
                    int n = sc.nextInt();
                    if (n == 1) {
                        m.viewRequests();
                    } else if (n == 2) {
                        m.updateStatus();
                    }
                    break;
                case 4:
                    System.out.println("1) Send Notification\n2) Modify Notification\n3) Delete Notification");
                    int notify = sc.nextInt();
                    if (notify == 1) {
                        Notification.insertNotify();
                    } else if (notify == 2) {
                        Notification.modifyNotify();
                    } else if (notify == 3) {
                        Notification.DeleteNotify();
                    }
                    break;
                case 5:
                    Student.viewRecords();
                    break;
                case 6:
                    System.out.println("Exiting Admin Panel...");
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
            }
        } while (adminChoice != 6);
    }
    private void printHeader(String title) {
        System.out.println("\n🔹 " + title + " 🔹");
    }
}
