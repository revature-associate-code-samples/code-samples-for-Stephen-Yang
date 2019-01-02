package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.BasicRequest;
import com.revature.utility.Connector;

public class BRDaoImp implements BasicReqDao {
	
	final static Logger brdLogger = Logger.getLogger(BasicReqDao.class);
	private static BRDaoImp brd;
	// use later: Connection conn = Connector.getConnection();
	
	
	public BRDaoImp() {
		super();
	}
	
	public static BRDaoImp getBRDao() {
		if (brd == null) {
			brd = new BRDaoImp();
		}
		return brd;
	}

	// username (parent key) must already exist in basic_employees table
	public BasicRequest selectReq(int reqID) { // having ID as param may lead to problems?
		brdLogger.info("retrieving request");
		try (Connection conn = Connector.getConnection()) { // just have one conn above?
			PreparedStatement srSP = conn.prepareStatement("SELECT * FROM basic_requests WHERE req_id = ?");
			srSP.setInt(1, reqID); // will this work? if I have default value
			ResultSet reqRS = srSP.executeQuery();
			while (reqRS.next()) {
				brdLogger.info("query in BRDaoImp.selectReq succeeded");
				BasicRequest selectedReq = new BasicRequest(
						reqRS.getInt(1),
						reqRS.getInt(3),
						reqRS.getString(2),
						reqRS.getDouble(4),
						reqRS.getString(5)
						);
				brdLogger.info("request object created");
				return selectedReq;
			}
		} catch (SQLException s) {
			brdLogger.error("exception caught in BRDaoImp.selectReq");
			brdLogger.error(s.getMessage());
		}
		return new BasicRequest();
	}
	
	//view their pending reimbursement requests
	//view their resolved reimbursement requests
	//view all pending requests from all employees
	//view all resolved requests from all employees and see which manager resolved it
	public List<BasicRequest> selectReqs(String status, String name) { // handle diff requirements, both emp and managers
		// alternative - use selectAllReqs and filter its list
		try (Connection conn = Connector.getConnection()) {
			String psql = "SELECT * FROM basic_requests";
			if (status.matches("resolved")) {
				brdLogger.info("selecting both approved and denied requests");
				psql+=" WHERE status != 0";
			} else if (status.matches("pending")) {
				brdLogger.info("selecting pending requests");
				psql+=" WHERE status = 0";
			} 
			PreparedStatement reqPS = conn.prepareStatement(psql);
			if (status.matches("all")) {
				brdLogger.info("selecting all requests from one employee");
				psql+=" WHERE emp_name = ? ORDER BY req_id";
				reqPS = conn.prepareStatement(psql);
				reqPS.setString(1, name);
			} else if (!name.matches("all")) {
				brdLogger.info("selecting certain requests from one employee");
				psql+=" AND emp_name = ? ORDER BY req_id";
				reqPS = conn.prepareStatement(psql);
				reqPS.setString(1, name);
			} else {
				reqPS = conn.prepareStatement(psql+" ORDER BY req_id");
			}
			ResultSet reqRS = reqPS.executeQuery();
			List<BasicRequest> rList = new ArrayList<>(); 
			while (reqRS.next()) {
				brdLogger.info("while loop in BRDaoImp.selectReqs executed");
				rList.add(new BasicRequest(reqRS.getInt("req_id"),
						reqRS.getInt("status"),
						reqRS.getString("emp_name"),
						reqRS.getDouble("amount"),
						reqRS.getString("m_name"))
						);
			}
			return rList;

		} catch (SQLException s) {
			brdLogger.error("catch occurred BRDaoImp.selectReqs");
			brdLogger.error(s.getMessage());
		}
		return new ArrayList<BasicRequest>();
	}

	public boolean addReq(BasicRequest req) {
		brdLogger.info("adding Request "+ req.getId());
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement arPS = conn.prepareStatement("INSERT INTO basic_requests VALUES (?,?,?,?,?)");
			arPS.setInt(1, req.getId());
			arPS.setString(2, req.getEmpname());
			arPS.setInt(3, req.getStatus());
			arPS.setDouble(4, req.getAmount());
			arPS.setString(5, req.getmName());
			brdLogger.info("prepared SQL statement in BRDaoImp.addReq");
			if (arPS.executeUpdate()>0) {
				brdLogger.info("statement in BRDaoImp.addReq executed successfully");
				return true;
			} 
		} catch (SQLException s) {
			brdLogger.error("exception caught in BRDaoImp.addReq");
			brdLogger.error(s.getMessage());
		} 
		return false;
	}

	public boolean updateReq(int reqID, String attribute, String newVal) { // need to add id as param
		brdLogger.info("updating Request "+reqID);
		try (Connection conn = Connector.getConnection()) {
			/* non-parametized SQL
			String sql = "UPDATE basic_requests SET "+attribute+" = "+newVal+" WHERE req_id = "+this.selectReq().getId();
			switch (attribute) {
				case "req_id":
				case "status":
					brdLogger.info("met Integer case in BRDaoImp.updateReq");
					sql = "UPDATE basic_requests SET "+attribute+" = "+Integer.parseInt(newVal)+" WHERE req_id = "+this.selectReq().getId();
					break;
				case "amount":
					brdLogger.info("met Double case in BRDaoImp.updateReq");
					sql = "UPDATE basic_requests SET "+attribute+" = "+Double.parseDouble(newVal)+" WHERE req_id = "+this.selectReq().getId();
			}
			*/
			// calling procedure instead of direct sql stops sql injection
			String psql = "UPDATE basic_requests SET "+attribute+" = ? WHERE req_id = ?";
			PreparedStatement urPS = conn.prepareStatement(psql);
			brdLogger.info("using parametized SQL in BRDaoImp.updateReq");	
			
			//urPS.setString(1, attribute); // no, column name cannot be String
			switch (attribute) {
				case "req_id":
				case "status": // int cases necessary? - double could still work
					brdLogger.info("met Integer case in BRDaoImp.updateReq"); 
					urPS.setInt(1, Integer.parseInt(newVal)); 
					break;
				case "amount":
					brdLogger.info("met Double case in BRDaoImp.updateReq");
					urPS.setDouble(1, Double.parseDouble(newVal));
					break;
				default:
					brdLogger.info("using default type in BRDaoImp.updateReq");
					urPS.setString(1, newVal);
					break;
			}			
			//urPS.setString(2, newVal);
			urPS.setInt(2, reqID);
			if (urPS.executeUpdate()>0) {
				brdLogger.info("statement in BRDaoImp.updateReq executed successfully");
				return true;
			} 
		} catch (SQLException s) {
			brdLogger.error("exception caught in BRDaoImp.updateReq");
			brdLogger.error(s.getMessage());
		}
		return false;
	}

}
