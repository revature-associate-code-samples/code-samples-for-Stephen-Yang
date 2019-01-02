package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.BasicEmployee;
import com.revature.utility.Connector;

public class BEDaoImp implements BasicEmpDao {
	
	final static Logger bedLogger = Logger.getLogger(BasicReqDao.class);
	private static BEDaoImp bed;
	// use later: Connection conn = Connector.getConnection();
	
	
	public BEDaoImp() {
		super();
	}
	
	public static BEDaoImp getBEDao() {
		if (bed == null) {
			bed = new BEDaoImp();
		}
		return bed;
	}

	public BasicEmployee selectEmp(String username) {
		bedLogger.info("retrieving employee");
		try (Connection conn = Connector.getConnection()) { // just have one conn above?
			PreparedStatement seSP = conn.prepareStatement("SELECT * FROM basic_employees WHERE user_name = ?");
			seSP.setString(1, username); // will this work? 
			ResultSet empRS = seSP.executeQuery();
			while (empRS.next()) {
				bedLogger.info("query in BEDaoImp.selectEmp succeeded");
				BasicEmployee selectedEmp = new BasicEmployee(
						empRS.getString(1),
						empRS.getString(2),
						empRS.getString(3),
						empRS.getInt(4)
						);
				bedLogger.info("employee object created");
				return selectedEmp;
			}
		} catch (SQLException s) {
			bedLogger.error("exception caught in BEDaoImp.selectEmp");
			bedLogger.error(s.getMessage());
		}
		return new BasicEmployee();
	}

	public boolean addEmp(BasicEmployee emp) {
		bedLogger.info("adding Employee "+ emp.getUsername());
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement aePS = conn.prepareStatement("INSERT INTO basic_employees VALUES (?,?,?,?)");
			aePS.setString(1, emp.getUsername());
			aePS.setString(2, emp.getPassword());
			aePS.setString(3, emp.getEmail());
			aePS.setInt(4, emp.getLevel());
			bedLogger.info("prepared SQL statement in BEDaoImp.addEmp");
			if (aePS.executeUpdate()>0) {
				bedLogger.info("statement in BRDaoImp.addEmp executed successfully");
				return true;
			} 
		} catch (SQLException s) {
			bedLogger.error("exception caught in BRDaoImp.addEmp");
			bedLogger.error(s.getMessage());
		} 
		return false;
	}

	// service should require password
	public boolean updateEmp(String username, String attribute, String newVal) {
		bedLogger.info("updating Employee "+username);
		try (Connection conn = Connector.getConnection()) {
			String psql = "UPDATE basic_employees SET "+attribute+" = ? WHERE user_name = ?";
			PreparedStatement uePS = conn.prepareStatement(psql);
			bedLogger.info("using parametized SQL in BEDaoImp.updateEmp");
			if (attribute.matches("level")) {
				uePS.setInt(1, Integer.parseInt(newVal));
			} else {
				uePS.setString(1, newVal);
			}			
			uePS.setString(2, username);
			if (uePS.executeUpdate()>0) {
				bedLogger.info("statement in BEDaoImp.updateEmp executed successfully");
				return true;
			} 
		} catch (SQLException s) {
			bedLogger.error("exception caught in BEDaoImp.updateEmp");
			bedLogger.error(s.getMessage());
		}
		return false;
	}
	
	public List<BasicEmployee> selectAllEmps() { // no managers
		try (Connection conn = Connector.getConnection()) {
			String sql = "SELECT * FROM basic_employees WHERE emp_level = 0";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<BasicEmployee> eList = new ArrayList<>(); 
			while (rs.next()) {
				bedLogger.info("while loop executed in BEDaoImp.selectAllEmps");
				eList.add(new BasicEmployee(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4)
						));
			}
			return eList;
		} catch (SQLException s) {
			bedLogger.error("catch occurred in BEDaoImp.selectAllEmps");
			bedLogger.error(s.getMessage());
		}
		return new ArrayList<BasicEmployee>();
	}
}
