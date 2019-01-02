package com.revature.model;

import java.io.Serializable;

public class BasicCustomer implements Serializable {
	
	private static final long serialVersionUID = 5747442994470353790L;
	
	private String username;
	private String password;
	private int approved;
	private int id; // account id
	
	public BasicCustomer() {
		super();
		
	}

	public BasicCustomer(String username, String password, int approved, int id) {
		super();
		
		this.username = username;
		this.password = password;
		this.approved = approved;
		this.id = id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "BasicCustomer [username=" + username + ", password=" + password + ", approved="
				+ approved + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + approved;
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicCustomer other = (BasicCustomer) obj;
		if (approved != other.approved)
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
