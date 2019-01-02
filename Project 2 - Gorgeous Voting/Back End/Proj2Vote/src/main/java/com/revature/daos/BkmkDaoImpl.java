package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.Bookmark;
import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class BkmkDaoImpl implements BkmkDao {
	
	private static BkmkDao bd; // will typing as interface work?

	public BkmkDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static BkmkDao getDao() {
		if (bd == null) {
			bd = new BkmkDaoImpl();
		}
		return bd;
	}

	@Override
	public Bookmark getBkmk(User user) {
		Session hiSess = HibernateUtil.getSession();
		// HQL uses bean name, NOT table name
		String hql = "FROM Bookmark WHERE book_id = :idVal";
		Query<Bookmark> selectBkmk = hiSess.createQuery(hql, Bookmark.class);
		selectBkmk.setParameter("idVal", user.getUser_id());
		Bookmark bkmk = (Bookmark) selectBkmk.getSingleResult(); // exception needs handling
		hiSess.close();
		return bkmk;
	}

	@Override
	public List<Bookmark> getAllBkmks(User user) {
		Session hiSess = HibernateUtil.getSession();
		String hql = "FROM Bookmark WHERE user_id = :uIdVal";
		Query<Bookmark> selectBkmks = hiSess.createQuery(hql, Bookmark.class);
		selectBkmks.setParameter("uIdVal", user.getUser_id());
		List<Bookmark> bkmks = selectBkmks.list();
		hiSess.close();
		return bkmks;
	}

	@Override
	public int addBkmk(Bookmark bkmk) { // add condition here or in service: user must already exist in db 
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		int bkmkPK = (int) hiSess.save(bkmk);
		tx.commit();
		hiSess.close();
 		return bkmkPK;
	}

	/*@Override
	public void removeBkmk(User user) {
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		Bookmark bkmk = BkmkDaoImpl.getDao().getBkmk(user.getUser_id());
		hiSess.delete(bkmk);
		tx.commit();
		hiSess.close();
	}*/
	
	
	
	
	

}
