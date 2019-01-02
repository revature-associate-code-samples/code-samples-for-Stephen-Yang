package com.revature.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.daos.BkmkDaoImpl;
import com.revature.models.Bookmark;
import com.revature.models.User;

@RestController
@CrossOrigin
@RequestMapping("/bookmark")
public class BookmarkController {
	
	private final static Logger ucLog = Logger.getLogger(UserController.class);
	
	@PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public int createBkmk(@RequestBody Bookmark bkmk) {
		int bkmkPK=BkmkDaoImpl.getDao().addBkmk(bkmk);
		if (bkmkPK!=0) {
			ucLog.info("A new bookmark was created.");
		} else {
			ucLog.info("A bookmark failed to create.");
		}
		return bkmkPK;
	}
	
	@PostMapping(value="/view", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Bookmark> viewAllBookmarks(User user){
		List<Bookmark> bkmkList = BkmkDaoImpl.getDao().getAllBkmks(user);
		return bkmkList;
	}
	
	/*@PostMapping(value="/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public void removeBookmark(@RequestBody int id) {
		BkmkDaoImpl.getDao().removeBkmk(id);
		}*/

}
