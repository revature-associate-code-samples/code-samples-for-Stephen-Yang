package com.revature.daos;

import java.util.List;

import com.revature.models.Bookmark;
import com.revature.models.User;

public interface BkmkDao {

	public Bookmark getBkmk(User user);
	public List<Bookmark> getAllBkmks(User user);
	public int addBkmk(Bookmark bkmk);
	//public void removeBkmk(User user);
	
}
