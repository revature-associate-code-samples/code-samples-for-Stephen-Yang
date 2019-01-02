package com.revature.model;

public class BasicRequest {

	private int id;
	private int status;
	private String empname;
	private double amount;
	private String mName;
	
	public BasicRequest() {
		super();
	}

	public BasicRequest(int id, int status, String empname, double amount, String mName) {
		super();
		this.id = id;
		this.status = status;
		this.empname = empname;
		this.amount = amount;
		this.mName = mName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	@Override
	public String toString() {
		return "BasicRequest [id=" + id + ", status=" + status + ", empname=" + empname 
				+ ", amount=" + amount + ", mName="+ mName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((empname == null) ? 0 : empname.hashCode());
		result = prime * result + id;
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		result = prime * result + status;
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
		BasicRequest other = (BasicRequest) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (empname == null) {
			if (other.empname != null)
				return false;
		} else if (!empname.equals(other.empname))
			return false;
		if (id != other.id)
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	
	
}
