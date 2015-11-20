package com.nexstream.helloworld.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexstream.helloworld.dao.UserLoginDao;
import com.nexstream.helloworld.entity.UserLogin;

@Service(UserLoginServiceImpl.beanName)
public class UserLoginServiceImpl implements UserLoginService{

	@Resource
	private UserLoginDao userLoginDao;
	
	@Transactional
	public List<UserLogin> geTests()throws Exception{
		return userLoginDao.geTests();
	}
	
	@Transactional
	public void saveOrUpdate(UserLogin test)throws Exception{
		userLoginDao.saveOrUpdate(test);
	}
	
	@Transactional(readOnly=true)
	public UserLogin getTest(Long id)throws Exception{
		return userLoginDao.getTest(id);
	}
	
	@Transactional
	public void deleteTest(Long id)throws Exception{
		userLoginDao.deleteTest(id);
	}
	
	@Transactional
	public void deleteAllTests()throws Exception{
		userLoginDao.deleteAllTests();
	}
	
	@Transactional
	public void saveOrUpdateAllTests(List<UserLogin> tests)throws Exception{
		userLoginDao.saveOrUpdateAllTests(tests);
	}
}