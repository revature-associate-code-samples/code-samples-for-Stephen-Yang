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
		drLogger.info("first manager: "+BEDaoImp.getBEDao().selectEmp("manager"));
	}

}
