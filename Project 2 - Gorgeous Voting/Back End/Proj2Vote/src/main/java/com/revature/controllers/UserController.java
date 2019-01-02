package com.revature.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.daos.UserDaoImpl;
import com.revature.models.User;

//@Controller
@RestController// same as Controller but w/o ResponseBody?
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	
	private final static Logger ucLog = Logger.getLogger(UserController.class);
	
	@GetMapping(value="/test", produces = MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
	public String test() {
		return "heystephen";
	}
	
	/*@GetMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
	public User loginGet(@RequestParam("username") String username, @RequestParam("password") String password) {
		User login = UserDaoImpl.getDao().getUser(username, password);
		if (login != null) {
			ucLog.info("login successful");
		} else {
			ucLog.info("login failed");
		}
		return login;
	}*/
	
	@PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public User login(@RequestBody User user) { // username and password only non-null properties in user object
		User login = UserDaoImpl.getDao().getUser(user);
		if (login != null) {
			ucLog.info("login successful");
		} else {
			ucLog.info("login failed");
		}
		return login;
	}
	
	@PostMapping(value="/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public int createUser(@RequestBody User user) {
		int addedUserPK = UserDaoImpl.getDao().addUser(user);
		if (addedUserPK != 0) {
			ucLog.info("User created");
		} else {
			ucLog.info("User creation failed");
		}
		return addedUserPK;
	}
	
	@PostMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public User update(@RequestBody User user) {
		User updatedUser = null;
		return updatedUser;
	}
	
}
