package com.revature.dao;

import com.revature.model.BasicEmployee;

public interface BasicEmpDao {
	
	public BasicEmployee selectEmp(String username);
	public boolean addEmp(BasicEmployee emp);
	public boolean updateEmp(String username, String attribute, String newVal); 
	
}
