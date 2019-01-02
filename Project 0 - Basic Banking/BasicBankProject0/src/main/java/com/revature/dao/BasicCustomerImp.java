package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.BasicCustomer;
import com.revature.util.JDBCConnectionUtil;

public class BasicCustomerImp implements BasicCustomerDao {
	
	final static Logger Log = Logger.getLogger(BasicCustomerImp.class);
	private static BasicCustomerImp dao;
	
	private static String username;
	private static int id;
	
	private BasicCustomerImp() {
		
	}
	
	public static BasicCustomerImp getDao() { 
		if (dao == null) {
			dao = new BasicCustomerImp();
		}
		return dao;
	}

	@Override
	public boolean insertCustomer(BasicCustomer customer) {
		Log.info("registering "+ customer.getUsername());
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			PreparedStatement psc = conn.prepareStatement("INSERT INTO customers VALUES (?,?,?,?)");
			psc.setString(1, customer.getUsername());
			psc.setString(2, customer.getPassword());
			psc.setInt(3, customer.getApproved());
			psc.setInt(4, customer.getId());
			PreparedStatement psa = conn.prepareStatement("INSERT INTO accounts VALUES (?,?)");
			psa.setInt(1, customer.getId());
			psa.setInt(2, 0);
			Log.info("prepared both statements in insertCustomer");
			if (psa.executeUpdate()>0 && psc.executeUpdate()>0) {
				Log.info("passed the condition in insertCustomer");
				return true;
			} 
		} catch (SQLException s) {
			Log.error("catch occurred in insertCustomer - Basic Customer Implementation");
			Log.error(s.getMessage());
		} 
		return false;
	}

	@Override
	public boolean insertCustomerProc(BasicCustomer customer) { // currently unused - app displays failure
		Log.info("registering "+ customer.getUsername());
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String storePro = "CALL add_customer(?,?,?,?,?,?)";
			CallableStatement cs = conn.prepareCall(storePro);
			cs.setString(1, customer.getUsername());
			cs.setString(2, customer.getPassword());
			cs.setInt(3, customer.getApproved());
			cs.setInt(4, customer.getId());
			cs.setInt(5, customer.getId());
			cs.setInt(6, 0);
			if (cs.executeUpdate()>0) {
				return true;
			} 
		} catch (SQLException s) {
			Log.error("catch in CustomerProcedure - Basic Implementation - occurred");
			Log.error(s.getMessage());
		} finally {
			Log.warn("finally block executed");
		}
		return false;
	}

	@Override
	public BasicCustomer getCustomer() {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM customers WHERE user_name = '" + username+"'";
			//String psql = "SELECT * FROM customers WHERE user_name = ?"; // query doesn't work, something to do with varchar?
			//PreparedStatement pstmt = conn.prepareStatement(psql);
			//Log.info("passing '"+username+"' into psql");
			//pstmt.setString(1, "'"+username+"'");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			//Log.info(rs.next());
			while (rs.next()) {
				Log.info("while loop executed for getCustomer");
				return new BasicCustomer(
						rs.getString("user_name"),
						rs.getString("passcode"),
						rs.getInt("approval"),
						rs.getInt("acct_id"));
			}
		} catch (SQLException s) {
			Log.error("catch occurred in getCustomer - Basic Customer Implementation");
			Log.error(s.getMessage());
		}
		return new BasicCustomer();
	}
	

	@Override
	public List<BasicCustomer> selectAllCustomers() {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM customers";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<BasicCustomer> cList = new ArrayList<>(); 
			while (rs.next()) {
				Log.info("while loop executed");
				cList.add(new BasicCustomer(rs.getString("user_name"),
						rs.getString("passcode"),
						rs.getInt("approval"),
						rs.getInt("acct_id"))
						);
			}
			return cList;
		} catch (SQLException s) {
			Log.error("catch occurred in selectAllCustomers - Basic Implementation");
			Log.error(s.getMessage());
		}
		return new ArrayList<>();
	}
	
	@Override
	public String switchCustomerTo(String diffUser) {
		username = diffUser;
		id = this.getCustomer().getId();
		return this.getCustomer().getUsername();
	}
	
	public float getBalance() {
		Log.info("retreiving "+ this.getCustomer().getUsername()+"'s balance");
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM accounts WHERE id = ?";
			PreparedStatement psb = conn.prepareStatement(sql);
			psb.setInt(1, id);
			ResultSet rsb = psb.executeQuery();
			while (rsb.next()) {
				return rsb.getFloat(1);
			}
		} catch (SQLException s) {
			Log.error("catch occurred in updateCustomer - Basic Customer Implementation");
			Log.error(s.getMessage());
		} 
		return -1;
	}
	
	public boolean updateBalance(float amount) {
		Log.info("updating "+ this.getCustomer().getUsername()+"'s balance");
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
			PreparedStatement psu = conn.prepareStatement(sql);
			psu.setFloat(1, amount); // compatible data types?
			psu.setInt(2, id);
			if (psu.executeUpdate()>0) {
				return true;
			} 
		} catch (SQLException s) {
			Log.error("catch occurred in updateCustomer - Basic Customer Implementation");
			Log.error(s.getMessage());
		} 
		return false;
	}

	@Override
	public boolean updateCustomer(String attribute, String value) {
		Log.info("updating "+ this.getCustomer().getUsername());
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// calling procedure instead of direct sql stops sql injection
			//String sql = "UPDATE customers SET ? = ? WHERE acct_id = ?"; // doesn't work
			String sql = "UPDATE customers SET "+attribute+" = "+value+" WHERE acct_id = "+id;
			switch (attribute) {
				case "acct_id":
				case "approval":
					Log.info("executing number case");
					sql = "UPDATE customers SET "+attribute+" = "+Integer.parseInt(value)+" WHERE acct_id = "+id;
					break;
			}
			
			PreparedStatement psu = conn.prepareStatement(sql);
			/*
			psu.setString(1, attribute);
			switch (attribute) {
				case "acct_id":
				case "approval":
					Log.info("executing number case");
					int numVal = Integer.parseInt(value);
					psu.setInt(2, numVal);
					break;
				default:
					Log.info("executing default case");
					psu.setString(2, "'"+value+"'");
					break;
			}
			psu.setInt(3, id);
			*/
			//Log.info("Prepared Statement: "+ psu);
			if (psu.executeUpdate()>0) {
				return true;
			} 
		} catch (SQLException s) {
			Log.error("catch occurred in updateCustomer - Basic Customer Implementation");
			Log.error(s.getMessage());
		} 
		return false;
	}

	
	
	
	
}
