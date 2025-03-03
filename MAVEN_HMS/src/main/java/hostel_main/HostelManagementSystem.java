package hostel_main;
import java.util.Scanner;
import com.hostels.*;
import com.hostels.db.user.FeesDB;
public class HostelManagementSystem {
    static String reset= "\033[0m"; 
    static String blue="\033[34m";
    static String purple= "\033[35m";
    static String cyan="\033[36m";
    static String bold="\033[1m";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("**************************************");
        System.out.println("**  Welcome to Hostel Management    **");
        System.out.println("**            System                **");
        System.out.println("**************************************");
        int role = chooseUserRole(sc);
        UserType user = handleUserAuthentication(sc, role);
        if (user == null) {
            System.out.println("Authentication failed. Exiting...");
            return;
        }
        user.specificActions();
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
    private static UserType handleUserAuthentication(Scanner sc, int role) {
        if (role == 1) {
            System.out.print("Enter Admin Username-> ");
            String username = sc.next();
            System.out.print("Enter Admin Password-> ");
            String password = sc.next();
            if (username.equals("admin") && password.equals("admin123")) {
                return new Admin();
            } else {
                System.out.println("Invalid Admin credentials!");
            }
        } else if (role == 2) {
            System.out.print("Enter Warden Username-> ");
            String username = sc.next();
            System.out.print("Enter Warden Password-> ");
            String password = sc.next();
            if (username.equals("warden") && password.equals("warden123")) {
                return new Warden();
            } else {
                System.out.println("Invalid Warden credentials!");
            }
        } else {
            while (true) {
                System.out.println("1) Register\n2) Login");
                int option = sc.nextInt();
                sc.nextLine();
                if (option == 1) {
                    UserType.register();
                    System.out.println("Registration successful! Please log in to continue.");
                } else if (option == 2) {
                    return UserType.login();
                } else {
                    System.out.println("Please enter a valid choice!");
                }
            }
        }
        return null;
    }
}