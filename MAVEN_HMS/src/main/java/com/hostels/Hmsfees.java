package com.hostels;
import java.util.Scanner;
import com.hostels.db.user.FeesDB;
public class Hmsfees {
    private static final int SHARING_ROOM_FEES=5000;
    private static final int SINGLE_ROOM_FEES=10000;
    private static final int VEG_FOOD_FEES=3000;
    private static final int NON_VEG_FOOD_FEES=5000;
    public static void setFoodPreference(Scanner scanner, String studentID) {
    	System.out.println("Enter room type(1 for Sharing Room, 2 for Single Room):");
        int roomType=scanner.nextInt();
        int roomFees=(roomType == 1)?SHARING_ROOM_FEES:(roomType==2)?SINGLE_ROOM_FEES:-1;
        if (roomFees==-1) {
            System.out.println("Invalid room type selected.");
            scanner.close();
            return;
        }
        System.out.println("Do you want food? (1 for Yes, 2 for No): ");
        int foodChoice=scanner.nextInt();
        if (foodChoice==1) {
            System.out.println("Choose food type (1 for Veg, 2 for Non-Veg): ");
            int foodType=scanner.nextInt();
            int foodFees=(foodType == 1) ? VEG_FOOD_FEES+roomFees : (foodType == 2) ? NON_VEG_FOOD_FEES+roomFees : -1;
            if (foodFees==-1) {
                System.out.println("❌Invalid food type selected.");
                return;
            }
            FeesDB.initializeStudentFees(studentID, foodFees);
            System.out.println("Food preference set successfully.");
        } else if (foodChoice==2) {
        	int foodFees=roomFees;
        	FeesDB.initializeStudentFees(studentID, foodFees);
            System.out.println("No food preference set.");
        } else {
            System.out.println("❌Invalid choice.");
        }
    }
    public static void payFees(Scanner scanner, String studentID) {
        System.out.print("Enter the amount you want to pay: ");
        int paymentAmount=scanner.nextInt();
        FeesDB.payFees(studentID, paymentAmount);
    }
    public static void viewRecord(String studentID) {
        FeesDB.viewFees(studentID);
    }
    public static void viewRecord() {
    	FeesDB.viewFees();
    }
}