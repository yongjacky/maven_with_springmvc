package com.nexstream.helloworld.dao;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nexstream.helloworld.entity.User;

@Repository(UserDaoImpl.beanName)
public class UserDaoImpl implements UserDao{

	@Resource
	private SessionFactory sessionFactory;
	
	public List<User> geUsers()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<User> users = session.createQuery("from User").list();
		ListIterator<User> iter = users.listIterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		return users;
	}
	
	public void saveOrUpdate(User user)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(user);
	}
	
	public User getUser(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);
		return user;
	}
	
	public void deleteUser(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);
		
		if (user!=null)
			session.delete(user);
	}
	
	public void deleteAllUsers()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<User> users = session.createQuery("from User").list();
		ListIterator<User> iter = users.listIterator();
		while(iter.hasNext()){
			User userTemp = iter.next();
			Long idLoop = userTemp.getId();
			User user = (User) session.get(User.class, idLoop);
			if (user!=null)
				session.delete(user);
		}
	}
	
	public void saveOrUpdateAllUsers(List<User> users)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		ListIterator<User> iter = users.listIterator();
		while(iter.hasNext()){
			//System.out.println(iter.next());
			User userTemp = iter.next();
			session.merge(userTemp);
		}
	}
	
	public User getUserByLoginId(String loginId)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> users = session.createQuery("FROM User us WHERE us.loginId = '" + loginId +"'").list();
		
		User user = null;
		if (users.size()>0)
			user = users.get(0);
		
		return user;
	}
	
	public User getUserByAuthenticationToken(String authToken)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> users = session.createQuery("FROM User us WHERE us.authenticationToken = '" + authToken +"'").list();
		
		User user = null;
		if (users.size()>0)
			user = users.get(0);
		
		return user;
	}

	
	@SuppressWarnings("unchecked")
	public User getUserLoginId(String userLoginId)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("loginId", userLoginId));
		List<User> results = cr.list();
		User returnUser = null;
		if(results.size()>0){
			returnUser = results.get(0);
		}
		return returnUser;
	}

}