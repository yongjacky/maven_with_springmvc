package com.nexstream.helloworld.dao;
import java.util.List;

import com.nexstream.helloworld.entity.Test;

public interface TestDao {
	public final String beanName = "testDao";
	public List<Test> geTests()throws Exception;
	public void saveOrUpdate(Test test)throws Exception;
	public Test getTest(Long id)throws Exception;
	public void deleteTest(Long id)throws Exception;
	public void deleteAllTests()throws Exception;
	public void saveOrUpdateAllTests(List<Test> tests)throws Exception;
}