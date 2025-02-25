package com.hostels;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.hostels.db.user.NotificationDB;

import hostel_main.HostelManagementSystem;
public class Admin {
	Scanner sc=new Scanner(System.in);
    private int eventCount = 0;
    Scanner scanner = new Scanner(System.in);
    public void createEvent() {
    	EventManagement.manageEvents();
    }
    public void sendNotification() {
    	System.out.println("Enter Notification details");
    	String details=sc.nextLine();
    	NotificationDB.addNotification(details);
    }
}
