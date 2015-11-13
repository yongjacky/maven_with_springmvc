package com.nexstream.helloworld.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.nexstream.helloworld.entity.TableTestEntity;

@Repository(TableTestIDaoImpl.beanName)
public class TableTestIDaoImpl implements TableTestInterface{

	@Resource
	private SessionFactory sessionFactory;
	
	public List<TableTestEntity> geTests()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<TableTestEntity> tests = session.createQuery("from Test").list();
		
		return tests;
	}
	
	public void saveOrUpdate(TableTestEntity test)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(test);
	}
	
	public TableTestEntity getTest(int id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		TableTestEntity test = (TableTestEntity) session.get(TableTestEntity.class, id);
		return test;
	}
}
