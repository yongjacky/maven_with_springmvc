package com.nexstream.helloworld.service;
import java.util.List;

import com.nexstream.helloworld.entity.Test;

public interface TestService {
	final static String beanName="testService";
	
	public List<Test> geTests()throws Exception;
	public void saveOrUpdate(Test test)throws Exception;
	public Test getTest(Long id)throws Exception;
}