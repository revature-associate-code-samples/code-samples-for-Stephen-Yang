package com.revature.dao;

import com.revature.model.BasicRequest;

public interface BasicReqDao {

	public BasicRequest selectReq(int reqID);
	public boolean addReq(BasicRequest req);
	public boolean updateReq(int reqID, String attribute, String newVal);
		// convert string to datatype of attribute if necessary
	// is separate get all req method needed?
	
}
