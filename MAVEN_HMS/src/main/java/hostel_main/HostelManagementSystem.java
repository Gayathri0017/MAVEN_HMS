package hostel_main;
import java.util.Scanner;
import com.hostels.*;
import com.hostels.db.user.FeesDB;
import java.util.Scanner;
public class HostelManagementSystem {
public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        System.out.println("Welcome to Hostel Management System!");
//        System.out.println("====================================");
        System.out.println("**************************************");
        System.out.println("**  Welcome to Hostel Management    **");
        System.out.println("**  	       System               **");
        System.out.println("**************************************");
        int role = chooseUserRole(sc);
        if (!handleUserAuthentication(sc,role)) {
            System.out.println("Authentication failed. Exiting...");
            return;
        }
        switch (role){
            case 1:
            	adminOperations(sc);
                break;
            case 2:
                wardenOperations(sc);
                break;
            case 3:
                studentOperations(sc);
                break;
            default:
                System.out.println("Invalid role. Exiting...");
        }
        sc.close();
	}
    private static int chooseUserRole(Scanner sc) {
        int role;
        do {
            System.out.println("Select your role:");
            System.out.println("1) Admin");
            System.out.println("2) Warden");
            System.out.println("3) Student");
            System.out.print("Enter choice->");
            role = sc.nextInt();
            if (role < 1 || role > 3) {
                System.out.println("Invalid choice! Please enter a valid role.");
            }
        } while (role < 1 || role > 3);
        return role;
    }
    private static void printHeader(String title) {
        System.out.println("\n==============================");
        System.out.println("\t"+title+"\t");
        System.out.println("==============================");
    }
    private static boolean handleUserAuthentication(Scanner sc, int role) {
        boolean isAuthenticated = false;
        if (role == 1) {
            System.out.print("Enter Admin Username-> ");
            String username = sc.next();
            System.out.print("Enter Admin Password-> ");
            String password = sc.next();
            if (username.equals("admin") && password.equals("admin123")) {
                isAuthenticated = true;
            } else {
                System.out.println("Invalid Admin credentials!");
            }
        } 
        else if (role == 2) {
            System.out.print("Enter Warden Username-> ");
            String username = sc.next();
            System.out.print("Enter Warden Password-> ");
            String password = sc.next();
          
            if (username.equals("warden") && password.equals("warden123")) {
                isAuthenticated = true;
            } else {
                System.out.println("Invalid Warden credentials!");
           }

        } 
        else{
            while (!isAuthenticated) {
                System.out.println("1) Register\n2) Login");
                int option = sc.nextInt();
                sc.nextLine(); 
                if (option == 1) {
                    UserType.register();
                    System.out.println("Registration successful! Please log in to continue.");
                } else if (option == 2) {
                    UserType.login();
                    isAuthenticated = true;
                } else {
                    System.out.println("Please enter a valid choice!");
                }
            }
        }
        return isAuthenticated;
    }
        private static void adminOperations(Scanner sc) {
            Hmsfees f = new Hmsfees();
            Admin admin = new Admin();
            Maintenance m = new Maintenance();
            int adminChoice;
            do {
                printHeader("Admin Panel");
                System.out.println("1) Create Event");
                System.out.println("2) View Events");
                System.out.println("3) View Fees Records");
                System.out.println("4) Complaints");
                System.out.println("5) Add Notifications");
                System.out.println("6) Exit");
                System.out.print("Enter choice: ");
                adminChoice = sc.nextInt();
                sc.nextLine();
                switch (adminChoice) {
                    case 1:
                    	EventManagement.createEvent();
                        break;
                    case 2:
                        EventManagement.viewEvents();
                        break;
                    case 3:
                    	System.out.println("1)Check all students Records\n2)Chcek Particular student Records");
                    	int num=sc.nextInt();
                    	sc.nextLine();
                    	if(num==1) {
                    		FeesDB.viewAllRecords();
                    	}
                    	else if(num==2) {
                        System.out.println("Enter student Id to get fee status");
                        String index = sc.nextLine();
                        f.viewFees(index);
                    	}
                        break;
                    case 4:
                    	System.out.println("1)View Complients\n2)Update the Complients Status");
                    	int n=sc.nextInt();
                    	if(n==1) {
                    		m.viewRequests();
                    	}else if(n==2) {
                    		m.updateStatus();
                    	}
                        break;
                    case 5:
                    	admin.sendNotification();
                    	break;
                    case 6:
                        System.out.println("Exiting Admin Panel...");
                        break;
                    default:
                        System.out.println("Please enter a valid choice.");
                }
            } while (adminChoice != 6);
    }
    private static void wardenOperations(Scanner sc) {
        Warden warden = new Warden();
        printHeader("Warden Panel");
        warden.manageRooms();
    }
    private static void studentOperations(Scanner sc) {
        System.out.print("Enter Student ID: ");
        String studentID = sc.nextLine();
        Student student = new Student(studentID);
        student.setFoodPreference(sc);
        while (true) {
            printHeader("Student Panel");
            //System.out.println("1) View Room Details");
            //System.out.println("2) Set Food Preference");
            System.out.println("1) View Profile");
            System.out.println("2) Pay Fees");
            System.out.println("3) View Fees");
            System.out.println("4) Raise Complaints");
            System.out.println("5) Delete Complaints");
            System.out.println("6) View Notifications");
            System.out.println("7) View Upcoming Events");
            System.out.println("8) Update Profile");
            System.out.println("9) Contact Warden");
            System.out.println("10) Exit");
            System.out.print("Enter your choice: ");
            int studentChoice = sc.nextInt();
            sc.nextLine();
            switch (studentChoice) {
            	case 1:
            		Student.viewProfile();
            		break;
                case 2:
                    student.payFees(sc);
                    break;
                case 3:
                    student.viewFees(studentID);
                    break;
                case 4:
                    student.raiseComplaints();
                    break;
                case 5:
                	student.RemoveComplaints();
                	break;
                case 6:
                    student.viewNotifications();
                    break;
                case 7:
                    EventManagement.viewEvents();
                    break;
                case 8:
                	student.profileUpdation();
                    break;
                case 9:
                    student.contact();
                    break;
                case 10:
                    System.out.println("Exiting Student Panel...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
          }
        }
}