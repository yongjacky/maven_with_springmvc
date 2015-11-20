package com.nexstream.helloworld.dao;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.nexstream.helloworld.entity.UserLogin;

@Repository(UserLoginDaoImpl.beanName)
public class UserLoginDaoImpl implements UserLoginDao{

	@Resource
	private SessionFactory sessionFactory;
	
	public List<UserLogin> geTests()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<UserLogin> tests = session.createQuery("from Test").list();
		ListIterator<UserLogin> iter = tests.listIterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		
		return tests;
	}
	
	public void saveOrUpdate(UserLogin test)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(test);
	}
	
	public UserLogin getTest(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		UserLogin test = (UserLogin) session.get(UserLogin.class, id);
		return test;
	}
	
	public void deleteTest(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		UserLogin test = (UserLogin) session.get(UserLogin.class, id);
		
		if (test!=null)
			session.delete(test);
	}
	
	public void deleteAllTests()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<UserLogin> tests = session.createQuery("from Test").list();
		ListIterator<UserLogin> iter = tests.listIterator();
		while(iter.hasNext()){
			UserLogin testtemp = iter.next();
			Long idloop = testtemp.getId();
			UserLogin test = (UserLogin) session.get(UserLogin.class, idloop);
			if (test!=null)
				session.delete(test);
		}
	}
	
	public void saveOrUpdateAllTests(List<UserLogin> tests)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		ListIterator<UserLogin> iter = tests.listIterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
			UserLogin testtemp = iter.next();
			session.merge(testtemp);
		}
	}
}