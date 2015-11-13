package com.nexstream.helloworld.service;

import java.util.List;

import com.nexstream.helloworld.entity.TableTestEntity;

public interface TableTestService {
	final static String beanName="TableTestService";
	
	public List<TableTestEntity> geTests()throws Exception;
	public void saveOrUpdate(TableTestEntity test)throws Exception;
	public TableTestEntity getTest(int id)throws Exception;
}