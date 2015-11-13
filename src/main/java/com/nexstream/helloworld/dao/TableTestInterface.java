package com.nexstream.helloworld.dao;

import java.util.List;

import com.nexstream.helloworld.entity.TableTestEntity;

public interface TableTestInterface {
	public final String beanName = "TableTestInterface";
	public List<TableTestEntity> geTests()throws Exception;
	public void saveOrUpdate(TableTestEntity test)throws Exception;
	public TableTestEntity getTest(int id)throws Exception;
}