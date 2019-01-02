package com.revature.drivers;

import org.apache.log4j.Logger;

import com.revature.dao.BEDaoImp;
import com.revature.dao.BRDaoImp;
import com.revature.model.BasicEmployee;
import com.revature.model.BasicRequest;
import com.revature.service.BasicService;

public class BasicDriver {
	
	final static Logger drLogger = Logger.getLogger(BasicDriver.class);

	public static void main(String[] args) {
		drLogger.info("test driving");
		/*
		BasicEmployee testEmp = new BasicEmployee("someone70", "someWord", "something@somemail.com", 0);
		if (BEDaoImp.getBEDao().addEmp(testEmp)) {
			drLogger.info("employee insertion method works");
		}
		*/
		//drLogger.info("result of employee selection method: "+BEDaoImp.getBEDao().selectEmp("someone70"));
		/*
		if (BEDaoImp.getBEDao().updateEmp("username", "email", "new@email.org")) {
			drLogger.info("employee update method works");
			drLogger.info("new value: "+BEDaoImp.getBEDao().selectEmp("username").getPassword());
		}
		*/
		
		/*
		// username (parent key) must already exist in basic_employees table
		BasicRequest testReq = new BasicRequest(2, 0, "someone70", 70.70, "NA"); 
		if (BRDaoImp.getBRDao().addReq(testReq)) { // include maxID in add Req?
			drLogger.info("request insertion method works");
		}
		
		
		drLogger.info("result of request selection method: "+BRDaoImp.getBRDao().selectReq(2));
		// should not change username because it's a foreign key
		/*
		if (BRDaoImp.getBRDao().updateReq(0, "status", "1")) {
			drLogger.info("request update method works");
			drLogger.info("new value: "+BRDaoImp.getBRDao().selectReq(0).getStatus());
		}
		*/
		//BasicEmployee emp = new BasicEmployee("manager", "code", "manager@portal.com", 1);
		//BEDaoImp.getBEDao().addEmp(emp);
		drLogger.info("first manager: "+BEDaoImp.getBEDao().selectEmp("manager"));
	}

}
