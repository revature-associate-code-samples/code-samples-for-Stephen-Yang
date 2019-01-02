package com.revature.service;

import com.revature.daos.UserDaoImpl;
import com.revature.models.User;

public class VoteServImpl implements VoteService {

	public VoteServImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean login(User user) {
		if (UserDaoImpl.getDao().getUser(user) != null) {
			return true;
		}
		return false;
	}

}
