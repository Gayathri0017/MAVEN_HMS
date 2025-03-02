package com.hostels;

import java.util.Scanner;
import java.util.regex.Pattern;
import com.hostels.db.user.UserDB;

class User {
    String userID, name, email, password, phoneNumber;

    User(String userID, String name, String email, String password, String phoneNumber) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}

public class UserType {
    private static Scanner s = new Scanner(System.in);

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
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        } while (!isValidEmail(email));

        String password;
        do {
            System.out.print("Enter Password: ");
            password = s.nextLine().trim();
            if (!isValidPassword(password)) {
                System.out.println("Invalid password! Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit, and a special character.");
            }
        } while (!isValidPassword(password));

        String phoneNumber;
        do {
            System.out.print("Enter Phone Number: ");
            phoneNumber = s.nextLine().trim();
            if (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid phone number! Phone number must be exactly 10 digits.");
            }
        } while (!isValidPhoneNumber(phoneNumber));

        UserDB.registerUser(userID, name, email, password, phoneNumber);
        System.out.println("Please set the preferences");

        Student student = new Student(userID);
        student.setFoodPreference(s);

        s.nextLine(); // Consume any leftover newline character before moving to login
    }

    public static void login() {
        while (true) {
            System.out.println(); // Ensure a new line before input
            System.out.print("Enter User ID-> ");  
            String userID = s.nextLine().trim();

            System.out.print("Enter Password-> ");  
            String password = s.nextLine().trim();

            if (UserDB.loginUser(userID, password)) {
                System.out.println("✅ Login successful! Welcome, " + userID);
                break;
            } else {
                System.out.println("❌ Invalid User ID or Password. Please try again.");
            }
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }
}