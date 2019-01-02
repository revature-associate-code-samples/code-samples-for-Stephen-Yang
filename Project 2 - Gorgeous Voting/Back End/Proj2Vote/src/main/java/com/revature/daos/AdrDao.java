package com.revature.daos;

import java.util.List;

import com.revature.models.Address;

public interface AdrDao {

	public Address getAddress(int id);
	public int addAdr(Address adr);
	public List<Address> getAllAdr();
	public boolean deleteAdr(int id);
	public void updateAdr(int id);
	
}
