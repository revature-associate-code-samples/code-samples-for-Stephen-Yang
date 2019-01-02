package com.revature.util;

import org.apache.log4j.Logger;

import com.revature.daos.BkmkDao;
import com.revature.daos.BkmkDaoImpl;
import com.revature.daos.UserDao;
import com.revature.daos.UserDaoImpl;
import com.revature.models.Address;
import com.revature.models.User;

public class Driver {
	
	private static final Logger drLog = Logger.getLogger(Driver.class); 
	// will log all hibernate

	public static void main(String[] args) {
		
		//Session sess = HibernateUtil.getSession();
		//sess.close();
		
		UserDao ud = UserDaoImpl.getDao();
		BkmkDao bd = BkmkDaoImpl.getDao();
		
		Address revAdr = new Address("Reston", "Virginia", "11730 Plaza America Dr", 20190);
		User revUser = new User("revUser", "revPW", "Revature", "Employee", 
				"chatnoir@amail.fr", revAdr, 0);
		//System.out.println(ud.addUser(revUser)); 
		
		
		//drLog.info(ud.getUser("revUser", "revPW").toString());
		/*
		Bookmark someBkmk = new Bookmark(revUser, "/va-candidates");
		System.out.println(bd.addBkmk(someBkmk));
		*/
		drLog.info(bd.getAllBkmks(revUser).toString());
	}

}
