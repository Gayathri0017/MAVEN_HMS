package com.hostels;
import com.hostels.db.user.UserDB;
import java.util.Scanner;

public abstract class UserType {
    protected String userID, name, email, password, phoneNumber;
    private static Scanner s = new Scanner(System.in);
    public UserType(String userID) {
        this.userID = userID;
    }
    //user Registration..
    public static void register() {
        System.out.print("Enter User ID: ");
        String userID = s.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = s.nextLine().trim();

        String email;
        do {
            System.out.print("Enter Email: ");
            email = s.nextLine().trim();
            if (!isValidEmail(email)) {
                System.out.println("❌Invalid email format! Try again.");
            }
        } while (!isValidEmail(email));

        String password;
        do {
            System.out.print("Enter Password: ");
            password = s.nextLine().trim();
            if (!isValidPassword(password)) {
                System.out.println("❌Password must be 8+ characters, with uppercase, lowercase, digit, and special char.");
            }
        } while (!isValidPassword(password));

        String phoneNumber;
        do {
            System.out.print("Enter Phone Number: ");
            phoneNumber = s.nextLine().trim();
            if (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("❌Invalid phone number! Must be 10 digits.");
            }
        } while (!isValidPhoneNumber(phoneNumber));

        UserDB.registerUser(userID, name, email, password, phoneNumber);

        // Set preferences
        Student student = new Student(userID);
        student.setFoodPreference();
    }

    //Login Function
    public static UserType login() {
        while (true) {
            System.out.println();
            System.out.print("Enter User ID-> ");
            String userID = s.nextLine().trim();
            System.out.print("Enter Password-> ");
            String password = s.nextLine().trim();
            if (UserDB.loginUser(userID, password)) {
                System.out.println("✅Login successful! Welcome, " + userID);
                return new Student(userID);
            } else {
                System.out.println("❌Invalid User ID or Password. Try again.");
            }
        }
    }
    //abstract method(implemented in warden,admin,student)
    public abstract void specificActions();
    //field validations..
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$";
        return password.matches(passwordRegex);
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";
        return phoneNumber.matches(phoneRegex);
    }
}
