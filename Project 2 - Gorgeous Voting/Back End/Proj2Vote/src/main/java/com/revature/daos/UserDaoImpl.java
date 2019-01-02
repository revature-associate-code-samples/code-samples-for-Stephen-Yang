package com.revature.daos;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class UserDaoImpl implements UserDao {
	
	private static UserDao ud;

	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static UserDao getDao() {
		if (ud == null) {
			ud = new UserDaoImpl();
		}
		return ud;
	}

	@Override
	public User getUser(User user) {
		Session hiSess = HibernateUtil.getSession();
		// HQL uses bean name, NOT table name
		String hql = "FROM User WHERE username = :userVal AND pswd = :pwVal";
		Query<User> selectUser = hiSess.createQuery(hql, User.class);
		selectUser.setParameter("userVal", user.getUsername());
		selectUser.setParameter("pwVal", user.getPswd());
		User dbUser = null;
		try {
			dbUser = (User) selectUser.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace(); // use logging
		}
		hiSess.close();
		return dbUser;
	}	

	@Override
	public int addUser(User user) {
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		// must add address first if not already in db to avoid transient exception
		AdrDao ad = AdrDaoImpl.getDao();
		if (ad.getAddress(user.getAddress().getAdr_id()) == null) { // if adr not in db
			//hiSess.save(user.getAddress()); // add adr to db using save 
			System.out.println("null address found"); // use logging - Spring AOP?
			AdrDaoImpl.getDao().addAdr(user.getAddress()); // or addAdr - if session can be opened within a session
		}
		int userPK = (int) hiSess.save(user);
		// what if user is already in db?
		tx.commit();
		hiSess.close();
 		return userPK;
	}

	@Override
	public List<User> getAllUsers() {
		Session hiSess = HibernateUtil.getSession();
		Query<User> selectAll = hiSess.createQuery("FROM User", User.class);
		List<User> users = selectAll.list();
		hiSess.close();
		return users;
	}
	

	@Override
	public User updateUser(User user) {
		Session hiSess = HibernateUtil.getSession();
		try {
			hiSess.update(user);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		//maintain user object through front end
		/*String hql = "FROM User WHERE user_id= :idVal";
		Query<User> selectUser = hiSess.createQuery(hql, User.class);
		selectUser.setParameter("idVal", user.getUser_id());
		try {
			User updatedUser = (User) selectUser.getSingleResult();
			return updatedUser;
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}*/
		return user;
	}

}
