package com.nexstream.helloworld.dao;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.nexstream.helloworld.entity.Test;

@Repository(TestDaoImpl.beanName)
public class TestDaoImpl implements TestDao{

	@Resource
	private SessionFactory sessionFactory;
	
	public List<Test> geTests()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<Test> tests = session.createQuery("from Test").list();
		
		return tests;
	}
	
	public void saveOrUpdate(Test test)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(test);
	}
	
	public Test getTest(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Test test = (Test) session.get(Test.class, id);
		return test;
	}
	
	public void deleteTest(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Test test = (Test) session.get(Test.class, id);
		
		if (test!=null)
			session.delete(test);
	}
	
	public void deleteAllTests()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<Test> tests = session.createQuery("from Test").list();
		ListIterator<Test> iter = tests.listIterator();
		while(iter.hasNext()){
			Test testtemp = iter.next();
			Long idloop = testtemp.getId();
			Test test = (Test) session.get(Test.class, idloop);
			if (test!=null)
				session.delete(test);
		}
	}
}