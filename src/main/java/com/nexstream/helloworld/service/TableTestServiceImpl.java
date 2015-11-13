package com.nexstream.helloworld.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexstream.helloworld.dao.TableTestInterface;
import com.nexstream.helloworld.entity.TableTestEntity;

@Service(TableTestServiceImpl.beanName)
public class TableTestServiceImpl implements TableTestService{

	@Resource
	private TableTestInterface testDao;
	
	@Transactional
	public List<TableTestEntity> geTests()throws Exception{
		return testDao.geTests();
	}
	
	@Transactional
	public void saveOrUpdate(TableTestEntity test)throws Exception{
		testDao.saveOrUpdate(test);
	}
	
	@Transactional(readOnly=true)
	public TableTestEntity getTest(int id)throws Exception{
		return testDao.getTest(id);
	}
}