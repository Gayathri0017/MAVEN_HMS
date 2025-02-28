package com.hostels;
import java.util.Scanner;

import com.hostels.db.user.FeesDB;

public class Hmsfees {
    private static final int SHARING_ROOM_FEES = 5000;
    private static final int SINGLE_ROOM_FEES = 10000;
    private static final int VEG_FOOD_FEES = 3000;
    private static final int NON_VEG_FOOD_FEES = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your student ID: ");
        String studentID = scanner.next();

        System.out.println("Enter room type (1 for Sharing Room, 2 for Single Room): ");
        int roomType = scanner.nextInt();
        int roomFees = (roomType == 1) ? SHARING_ROOM_FEES : (roomType == 2) ? SINGLE_ROOM_FEES : -1;

        if (roomFees == -1) {
            System.out.println("Invalid room type selected.");
            scanner.close();
            return;
        }

        // Initialize fees in the database
        FeesDB.initializeStudentFees(studentID, roomFees);

        boolean running = true;
        while (running) {
            System.out.println("\n📌 Student Fee Menu:");
            System.out.println("1. Set Food Preference");
            System.out.println("2. Pay Fees");
            System.out.println("3. View Fees");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:setFoodPreference(scanner, studentID);break;
                case 2:payFees(scanner, studentID);break;
                case 3:viewFees(studentID);break;
                case 4:{
                    System.out.println("✅ Exiting Fee System.");
                    running = false;
                    break;
                }
                default:System.out.println("❌ Invalid choice. Please try again.");break;
            }
        }
        scanner.close();
    }

    // Set Food Preference
    public static void setFoodPreference(Scanner scanner, String studentID) {
        System.out.println("Do you want food? (1 for Yes, 2 for No): ");
        int foodChoice = scanner.nextInt();

        if (foodChoice == 1) {
            System.out.println("Choose food type (1 for Veg, 2 for Non-Veg): ");
            int foodType = scanner.nextInt();
            int foodFees = (foodType == 1) ? VEG_FOOD_FEES : (foodType == 2) ? NON_VEG_FOOD_FEES : -1;

            if (foodFees == -1) {
                System.out.println("❌ Invalid food type selected.");
                return;
            }

            FeesDB.initializeStudentFees(studentID, foodFees);
            System.out.println("✅ Food preference set successfully.");
        } else if (foodChoice == 2) {
            System.out.println("📌 No food preference set.");
        } else {
            System.out.println("❌ Invalid choice.");
        }
    }

    // Pay Fees
    public static void payFees(Scanner scanner, String studentID) {
        System.out.print("Enter the amount you want to pay: ");
        int paymentAmount = scanner.nextInt();
        FeesDB.payFees(studentID, paymentAmount);
    }

    // **NEW** View Fees (Added this method)
    public static void viewFees(String studentID) {
        FeesDB.viewFees(studentID);
    }
}