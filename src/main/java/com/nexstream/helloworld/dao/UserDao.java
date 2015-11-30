package com.nexstream.helloworld.dao;
import java.util.List;

import com.nexstream.helloworld.entity.User;

public interface UserDao {
	public final String beanName = "userDao";
	public List<User> geUsers()throws Exception;
	public void saveOrUpdate(User user)throws Exception;
	public User getUser(Long id)throws Exception;
	public void deleteUser(Long id)throws Exception;
	public void deleteAllUsers()throws Exception;
	public void saveOrUpdateAllUsers(List<User> tests)throws Exception;
	public User getUserByLoginId(String loginId)throws Exception;
	public User getUserLoginId(String userLoginId)throws Exception;
	public User getUserByAuthenticationToken(String authToken)throws Exception;
}
