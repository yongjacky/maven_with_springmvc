package com.nexstream.helloworld.service;
import java.util.List;

import com.nexstream.helloworld.entity.User;

public interface UserService {
	final static String beanName="userService";
	
	public List<User> geUsers()throws Exception;
	public void saveOrUpdate(User user)throws Exception;
	public User getUser(Long id)throws Exception;
	public void deleteUser(Long id)throws Exception;
	public void deleteAllUsers()throws Exception;
	public void saveOrUpdateAllTests(List<User> tests)throws Exception;
	public List<User> getUserPass(String userName)throws Exception;
	public User getUserLoginId(String userLoginId)throws Exception;
}