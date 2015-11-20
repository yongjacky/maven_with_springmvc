package com.nexstream.helloworld.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexstream.helloworld.dao.TestDao;
import com.nexstream.helloworld.entity.Test;

@Service(TestServiceImpl.beanName)
public class TestServiceImpl implements TestService{

	@Resource
	private TestDao testDao;
	
	@Transactional
	public List<Test> geTests()throws Exception{
		return testDao.geTests();
	}
	
	@Transactional
	public void saveOrUpdate(Test test)throws Exception{
		testDao.saveOrUpdate(test);
	}
	
	@Transactional(readOnly=true)
	public Test getTest(Long id)throws Exception{
		return testDao.getTest(id);
	}
	
	@Transactional
	public void deleteTest(Long id)throws Exception{
		testDao.deleteTest(id);
	}
	
	@Transactional
	public void deleteAllTests()throws Exception{
		testDao.deleteAllTests();
	}
	
	@Transactional
	public void saveOrUpdateAllTests(List<Test> tests)throws Exception{
		testDao.saveOrUpdateAllTests(tests);
	}
}