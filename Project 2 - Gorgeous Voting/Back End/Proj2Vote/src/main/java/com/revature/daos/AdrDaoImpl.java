package com.revature.daos;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.Address;
import com.revature.models.User;
import com.revature.models.Address;
import com.revature.util.HibernateUtil;

public class AdrDaoImpl implements AdrDao {
	
	public static AdrDao ad;

	public AdrDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static AdrDao getDao() {
		if (ad == null) {
			ad = new AdrDaoImpl();
		}
		return ad;
	}

	@Override
	public Address getAddress(int id) {
		Session hiSess = HibernateUtil.getSession();
		// HQL uses bean name, NOT table name
		String hql = "FROM Address WHERE adr_id = :idVal";
		Query<Address> selectAdr = hiSess.createQuery(hql, Address.class);
		selectAdr.setParameter("idVal", id);
		Address adr = null;
		try {
			adr = (Address) selectAdr.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace(); // use logging
		}
		hiSess.close();
		return adr;
	}

	@Override
	public int addAdr(Address adr) {
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		int adrPK = (int) hiSess.save(adr);
		tx.commit();
		hiSess.close();
 		return adrPK;
	}

	@Override
	public List<Address> getAllAdr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAdr(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void updateAdr(int id) {
		
	}
	

}
