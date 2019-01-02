package com.revature.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.BasicCustomerImp;
import com.revature.model.BasicCustomer;
import com.revature.util.JDBCConnectionUtil;

public class BasicCustomerService {
	
	final static Logger bcsLogger = Logger.getLogger(BasicCustomerService.class);
	static Scanner input = new Scanner(System.in);
	
	private static BasicCustomerService bcService;
	
	private BasicCustomerService() {
		
	}
	
	public static BasicCustomerService getBasicService() {
		if (bcService == null) {
			bcService = new BasicCustomerService();
		}
		return bcService;
	}
	
	// get current user
	public BasicCustomer getUserInfo() {
		return BasicCustomerImp.getDao().getCustomer();
	}
	
	// add new user
	public boolean addNewUser(String username, String password, int approval) {
		System.out.println("Creating new user account...");
		int id = 0; 
		bcsLogger.info("selecting max id");
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			PreparedStatement idPS = conn.prepareStatement("SELECT MAX(id) FROM accounts");
			ResultSet idRS = idPS.executeQuery();
			while (idRS.next()) {
				id = idRS.getInt(1)+7; // avoids duplicate ids
				bcsLogger.info("id is now "+id);
			}
		} catch (SQLException s) {
			bcsLogger.error("catch occurred in addNewUser - Basic Customer Service");
			bcsLogger.error(s.getMessage());
		}
		BasicCustomer newUser = new BasicCustomer(username, password, approval, id);
		if (BasicCustomerImp.getDao().insertCustomer(newUser)) {
			System.out.println("Account created!");
			BasicCustomerImp.getDao().switchCustomerTo(username);
			return true;
		}
		System.out.println("Failed to create account.");
		return false;
	}
	
	// list all users, not showing passwords or balances
	public void displayAllUsers() {
		System.out.println("Preparing list of all existing users...");
		List<BasicCustomer> userList = BasicCustomerImp.getDao().selectAllCustomers();
		for (BasicCustomer user : userList) {
			if (user.getApproved() == 1) {
				System.out.println("	"+user.getUsername()+" - approved");
			} else if (user.getApproved() == 2) {
				System.out.println("	"+user.getUsername()+" - administrator");
			} else {
				System.out.println("	"+user.getUsername()+" - pending");
			}
		}
	}
	
	// approve user - only for admin
	public boolean approve(String user) {
		BasicCustomerImp.getDao().switchCustomerTo(user);
		if (BasicCustomerImp.getDao().getCustomer().getApproved() > 0) {
			System.out.println(user+" has already been approved");
			return false;
		}
		if (BasicCustomerImp.getDao().updateCustomer("approval", "1")) {
			System.out.println(user+" has been approved");
			return true;
		}
		System.out.println(user+" could not be approved");
		return false;
	}
	
	// deposit/withdraw - returns new balance
	public float changeBalance(float change) {
		BasicCustomerImp.getDao().updateBalance(change);
		return BasicCustomerImp.getDao().getBalance();
	}
	
	// welcome
	public void welcome() {
		System.out.println();
		System.out.println("Welcome to Basic Bank!");
		System.out.print("Are you a returning user? (Y/N or Q to quit): ");
		String answer = input.next();
		bcsLogger.info("input accepted");
		if (answer.matches("[Yy]")) {
			System.out.print("Please enter your username: ");
			String userStr = input.next();
			bcsLogger.info("username accepted");
			System.out.print("Please enter your password: ");
			String pwStr = input.next();
			bcsLogger.info("password accepted");
			if (login(userStr, pwStr)) {
				this.giveOptions();
			} else {
				this.welcome();
			}
		} else if (answer.matches("[Nn]")) {
			System.out.print("Please create a username: "); 
			String userStr = input.next();  
			bcsLogger.info("username accepted");
			System.out.print("Please create a password: "); 
			String pwStr = input.next();
			bcsLogger.info("password accepted");
			addNewUser(userStr, pwStr, 0);
			welcome();
		} else if (answer.matches("[Qq]")) {
			System.out.println("Thank you for using Basic Bank!");
		} else {
			System.out.println("Please enter Y, N, or Q");
			this.welcome();
		}
	}
	
	// login
	public boolean login(String username, String password) {	
		try {
			System.out.println("Logging in...");
			BasicCustomerImp.getDao().switchCustomerTo(username);
			if (getUserInfo().getPassword().matches(password)) {
				// change imp method to boolean?
				System.out.println("Login successful.");
				return true; // new customer obj will be the right one
			}
			System.out.println("Password failed.");
			return false;
		} catch (NullPointerException npe) {
			bcsLogger.error(npe);
			System.out.println("Login unsuccessful.");
			return false;
		}
	}
	
	// admin options
	public void adminPage() {

		this.displayAllUsers();
		
		System.out.println("1. Approve customers ");
		System.out.println("2. Add administrator ");
		System.out.println("3. Log out");
		System.out.print("Enter the number of your desired task: ");
		int adNum = input.nextInt();
		if (adNum == 1) {
			System.out.print("Enter the user you would like to approve: ");
			String userStr = input.next();
			this.approve(userStr);
			this.adminPage();
		} else if (adNum == 2) {
			System.out.print("New admin name: ");
			String newAd = input.next();
			System.out.print("Admin passcode: ");
			String newPC = input.next();
			this.addNewUser(newAd, newPC, 2);
			this.adminPage();
		} else if (adNum == 3) {
			welcome();
		} else {
			System.out.println("Please enter 1, 2, or 3");
			this.adminPage();
		}
	}
	
	// actions
	public void giveOptions() {
		int apprNum = BasicCustomerImp.getDao().getCustomer().getApproved();
		System.out.println("Hello, "+BasicCustomerImp.getDao().getCustomer().getUsername());
		
		// if approved
		if (apprNum == 1) {
			float balance = BasicCustomerImp.getDao().getBalance();
			System.out.println("Your current balance is $"+balance);
			System.out.println("What would you like to do?");
			System.out.println("1. Withdraw");
			System.out.println("2. Deposit");
			System.out.println("3. Log out");
			System.out.print("Enter the number of your desired task: ");
			
			if (input.hasNextInt()) {
				int num = input.nextInt();
				switch (num) {
				case 1: 
					System.out.print	("Enter the amount to withdraw: ");
					float amt2get = input.nextFloat();
					if (amt2get > balance) {
						System.out.println("Amount exceeds current balance.");	
					} else {
						System.out.println("Withdrawal successful. Your new balance is $"+changeBalance(balance-amt2get));
					}
					giveOptions(); 
					break;
				case 2: 
					System.out.print("Enter the amount to deposit: ");
					float amt2put = input.nextFloat();
					System.out.println("Deposit successful. Your new balance is $"+changeBalance(balance+amt2put));
					giveOptions();
					break;
				case 3:
					System.out.println("Logout successful.");
					welcome();
					break;
				default:
					System.out.println("Please enter a valid number.");
					giveOptions();
					break;
				}
			} else {
				System.out.println("Please enter a valid number.");
				giveOptions();
			}
		// if admin		
		} else if (apprNum == 2) {
			this.adminPage();
		}
		else {
			System.out.println("Sorry, your account has not yet been approved.");
			welcome();
		}
	}
}	
	
	

