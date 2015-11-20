package com.nexstream.helloworld.dao;
import java.util.List;

import com.nexstream.helloworld.entity.UserLogin;

public interface UserLoginDao {
	public final String beanName = "userLoginDao";
	public List<UserLogin> geTests()throws Exception;
	public void saveOrUpdate(UserLogin test)throws Exception;
	public UserLogin getTest(Long id)throws Exception;
	public void deleteTest(Long id)throws Exception;
	public void deleteAllTests()throws Exception;
	public void saveOrUpdateAllTests(List<UserLogin> tests)throws Exception;
}
