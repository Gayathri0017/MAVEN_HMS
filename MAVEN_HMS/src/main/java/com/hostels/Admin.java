package com.hostels;
import java.util.Scanner;
import com.hostels.db.user.NotificationDB;
public class Admin {
	Scanner sc=new Scanner(System.in);
    public void sendNotification() {
    	System.out.println("Enter Notification details");
    	String details=sc.nextLine();
    	NotificationDB.addNotification(details);
    }
}
