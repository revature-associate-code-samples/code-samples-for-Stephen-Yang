package com.revature.dao;

import java.util.List;
import com.revature.model.BasicCustomer;

public interface BasicCustomerDao {

	public boolean insertCustomer(BasicCustomer customer);
	public boolean insertCustomerProc(BasicCustomer customer);
	public BasicCustomer getCustomer();
	public List<BasicCustomer> selectAllCustomers();
	
	public boolean updateCustomer(String attribute, String newVal); 
	public String switchCustomerTo(String diffUser); 
	
	
}
