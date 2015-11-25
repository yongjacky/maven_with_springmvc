package com.nexstream.helloworld.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexstream.helloworld.dao.UserDao;
import com.nexstream.helloworld.entity.User;

@Service(UserServiceImpl.beanName)
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	@Transactional
	public List<User> geUsers()throws Exception{
		return userDao.geUsers();
	}
	
	@Transactional
	public void saveOrUpdate(User user)throws Exception{
		userDao.saveOrUpdate(user);
	}
	
	@Transactional(readOnly=true)
	public User getUser(Long id)throws Exception{
		return userDao.getUser(id);
	}
	
	@Transactional
	public void deleteUser(Long id)throws Exception{
		userDao.deleteUser(id);
	}
	
	@Transactional
	public void deleteAllUsers()throws Exception{
		userDao.deleteAllUsers();
	}
	
	@Transactional
	public void saveOrUpdateAllUsers(List<User> users)throws Exception{
		userDao.saveOrUpdateAllUsers(users);
	}
	
	@Transactional
	public User getUserByLoginId(String loginId)throws Exception{
		return userDao.getUserByLoginId(loginId);
	}

	@Transactional
	public void saveOrUpdateAllTests(List<User> tests) throws Exception {
		System.out.println("haven't implement");
	}
	
	@Transactional
	public User getUserLoginId(String userLoginId)throws Exception{
		return userDao.getUserLoginId(userLoginId);
		
	}
}