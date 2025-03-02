package com.hostels;
import java.lang.*;
import java.util.*;
import com.hostels.db.user.NotificationDB;
public class Notification{
	static Scanner sc=new Scanner(System.in);
	public static void insertNotify() {
	System.out.print("Enter notification details: ");
    String notificationDetails = sc.nextLine();
    NotificationDB.addNotification(notificationDetails);
	}
	public static void DeleteNotify() {
		 System.out.print("Enter notification ID to delete: ");
         int deleteId = sc.nextInt();
         NotificationDB.deleteNotification(deleteId);
	}
	public static void modifyNotify() {
		System.out.print("Enter notification ID to modify: ");
        int modifyId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new notification details: ");
        String newDetails = sc.nextLine();
        NotificationDB.modifyNotification(modifyId, newDetails);
	}
}