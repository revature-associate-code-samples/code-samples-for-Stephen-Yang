package com.revature.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.BEDaoImp;
import com.revature.dao.BRDaoImp;
import com.revature.model.BasicRequest;
import com.revature.utility.Connector;

public class BasicService {

	final static Logger servLogger = Logger.getLogger(BasicService.class);
	private static BasicService serv;
	private static String uName;
	
	private BasicService() {}
	
	public static BasicService getService() {
		if (serv == null) {
			serv = new BasicService();
		}
		return serv;
	}
	
	//login
	public boolean login(String username, String password) {	
		try {
			servLogger.info("logging in...");
			
			if (BEDaoImp.getBEDao().selectEmp(username).getPassword().matches(password)) {
				uName = username; // now the current user
				servLogger.info("login successful");
				return true;
			}
			servLogger.info("password failed");
			return false;
		} catch (NullPointerException npe) {
			servLogger.error(npe.getMessage());
			servLogger.warn("login failed");
			return false;
		}
	}
	
	public static String getCurrentUser() {
		return uName;
	}

	//submit a reimbursement request
	public boolean makeReq(double amount) {
		int newID = 0;
		servLogger.info("selecting max id");
		try (Connection conn = Connector.getConnection()) { // do this in imp instead
			PreparedStatement idPS = conn.prepareStatement("SELECT MAX(req_id) FROM basic_requests");
			ResultSet idRS = idPS.executeQuery();
			while (idRS.next()) {
				newID = idRS.getInt(1)+1; // avoids duplicate IDs
				servLogger.info("new ID: "+newID);
			}
		} catch (SQLException s) {
			servLogger.error("catch occurred BEService.makeReq");
			servLogger.error(s.getMessage());
		}
		BasicRequest newReq = new BasicRequest(newID, 0, uName, amount, null); 
		return BRDaoImp.getBRDao().addReq(newReq);
	}
	
	//update their information
	public boolean changeInfo(String pw, String info, String newVal) { // authenticate here or on servlet?
		if (pw.matches(BEDaoImp.getBEDao().selectEmp(uName).getPassword())) {
			servLogger.info("authentication succeeded in BEService.changeInfo");
			return BEDaoImp.getBEDao().updateEmp(uName, info, newVal);
		}
		return false;
	}
	
	public boolean resolveReq(String choice, int reqID) {
		boolean resolved = false;
		if (choice.matches("a")) {
			resolved = BRDaoImp.getBRDao().updateReq(reqID, "status", "1");
		} else {
			resolved = BRDaoImp.getBRDao().updateReq(reqID, "status", "-1");
		}
		if (resolved) {
			return BRDaoImp.getBRDao().updateReq(reqID, "m_Name", uName);
		}
		return false;
	}
	
}
