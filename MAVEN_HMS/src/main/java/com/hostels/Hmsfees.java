package com.hostels;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Hmsfees {
    private static final int SHARING_ROOM_FEES = 5000;
    private static final int SINGLE_ROOM_FEES = 10000;
    private static final int VEG_FOOD_FEES = 3000;
    private static final int NON_VEG_FOOD_FEES = 5000;
    private static int totalFees = 0;
    private static int balanceFees = 0;
    private static boolean foodPreferenceSet = false;
    private static List<String> studentIDs = new ArrayList<>();
    private static List<Integer> accountDetails = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your student ID: ");
        String studentID = scanner.next();
        studentIDs.add(studentID);

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

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Set Food Preference");
            System.out.println("2. Pay Fees");
            System.out.println("3. View Fees");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    setFoodPreference(scanner, studentID);
                    break;
                case 2:
                    payFees(scanner, studentID);
                    break;
                case 3:
                    viewFees(studentID);
                    break;
                case 4:
                    System.out.println("Exit.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    public static void setFoodPreference(Scanner scanner, String studentID) {
        if (foodPreferenceSet) {
            System.out.println("Food preference already set.");
            return;
        }
        System.out.println("Do you want food? (1 for Yes, 2 for No): ");
        int foodChoice = scanner.nextInt();
        if (foodChoice == 1) {
            System.out.println("Choose food type (1 for Veg, 2 for Non-Veg): ");
            int foodType = scanner.nextInt();
            if (foodType == 1) {
                totalFees += VEG_FOOD_FEES;
                System.out.println("Food preference set to Vegetarian.");
            } else if (foodType == 2) {
                totalFees += NON_VEG_FOOD_FEES;
                System.out.println("Food preference set to Non-Vegetarian.");
            } else {
                System.out.println("Invalid food type selected.");
            }
        } else if (foodChoice == 2) {
            System.out.println("No food preference set.");
        } else {
            System.out.println("Invalid choice.");
        }
        foodPreferenceSet = true;
    }

    public static void payFees(Scanner scanner, String studentID) {
        System.out.print("Enter the amount you want to pay: ");
        int paymentAmount = scanner.nextInt();

        if (paymentAmount > totalFees - balanceFees) {
            int excessAmount = paymentAmount - (totalFees - balanceFees);
            balanceFees = totalFees;
            System.out.println("Payment of " + paymentAmount + " successful. Excess amount returned: " + excessAmount);
        } else {
            balanceFees += paymentAmount;
            System.out.println("Payment of " + paymentAmount + " successful. Remaining balance: " + (totalFees - balanceFees));
        }
    }
    public static void viewFees(String studentID) {
        int remainingFees = totalFees - balanceFees;
        System.out.println("Total Fees: " + totalFees);
        System.out.println("Balance Fees: " + remainingFees);
        if (remainingFees > 0) {
            System.out.println("You still need to pay: " + remainingFees);
        } else {
            System.out.println("All fees have been paid.");
        }
    }
}