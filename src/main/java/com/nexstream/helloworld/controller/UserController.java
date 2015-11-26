package com.nexstream.helloworld.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexstream.helloworld.domains.BaseResp;
import com.nexstream.helloworld.domains.ErrorResp;
import com.nexstream.helloworld.entity.User;
import com.nexstream.helloworld.service.UserService;

@RestController
public class UserController {
	ApplicationContext context = new ClassPathXmlApplicationContext("springModules.xml");
	
	@Resource
	private UserService userService;
	
	//-----------database------------------	
	@RequestMapping(value="/getUsers", method=RequestMethod.GET)
	@ResponseBody
	public List<User> geTests()throws Exception{
		return userService.geUsers();
	}
	
	@RequestMapping(value="/getUser/{id}", method=RequestMethod.GET)
	@ResponseBody
	public User geUserById(@PathVariable Long id)throws Exception{
		return userService.getUser(id);
	}
	
	//data type validation
	@SuppressWarnings("deprecation")
	private Boolean validateType(String value, Object type){
		Boolean isValid = true;
		
		try{
				if(type instanceof Date){
				Date.parse(value);
				return isValid;
			}
		}catch(Exception ex){
			isValid = false;
		}
		return isValid;
	}
	
	@RequestMapping(value="/saveNewUser", method=RequestMethod.POST)
	@ResponseBody
	public Object saveUser(@RequestBody User user)throws Exception{
	
		
		String userNameField = user.getUserName();
		String inputLoginId = user.getLoginId();
		String inputtimeStamp = user.getInputTimeStamp();
		String inputloginPass = user.getInputLoginPass();
		
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode("500");			
				
		if (userNameField==null) userNameField="";	
		
		if (userNameField.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("empty.field", new Object[] {"user name"}, Locale.US));
			return errorResp;
		}
		
		
		if (inputLoginId==null) inputLoginId="";
		
		if (inputLoginId.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("empty.field", new Object[] {"login id"}, Locale.US));
			return errorResp;
		}
		
		if(userService.getUserLoginId(inputLoginId)!=null){
			errorResp.setMessage("login id field is exist in database!");
			return errorResp;
		}
		
		
		if (inputloginPass == null) inputloginPass = "";
		
		if(inputloginPass.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("empty.field", new Object[] {"password"}, Locale.US));
			return errorResp;
		}
		
		if (inputtimeStamp == null) inputtimeStamp="";
		
		if(inputtimeStamp.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("empty.field", new Object[] {"time stamp"}, Locale.US));	
			return errorResp;
		}
		
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();		
		user.setPassword(md5.encodePassword(inputloginPass, inputLoginId));
		
		if(validateType(inputtimeStamp, null)){
			Date date = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			try{
				date = df.parse(inputtimeStamp);
				user.setTimeStamp(date);
			}catch(ParseException e){
				errorResp.setMessage("Invalid date value specified!(yyyy-MM-dd kk:mm:ss)");
				return errorResp;
			}
		}
		
		userService.saveOrUpdate(user);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage(context.getMessage("user.status", new Object[] {"add"}, Locale.US));
		return resp;
	}
	
	@RequestMapping(value="/updateUser/{id}", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveTest(@PathVariable Long id, @RequestBody User user)throws Exception{
		user.setId(id);
		userService.saveOrUpdate(user);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage(context.getMessage("user.status", new Object[] {"update"}, Locale.US));
		return resp;
	}
	
	@RequestMapping(value="/saveOrUpdateUser", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveOrUpdateTest(@RequestBody User user)throws Exception{
		userService.saveOrUpdate(user);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		long checkid = user.getId();
				
		if(userService.getUser(checkid)!=null)
			resp.setMessage(context.getMessage("user.status", new Object[] {"update"}, Locale.US));
		else
			resp.setMessage(context.getMessage("user.status", new Object[] {"add"}, Locale.US));
		return resp;
	}
	
	@RequestMapping(value="/deleteUser", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp deleteTest(@RequestBody User user)throws Exception{
		Long id = user.getId();
		userService.deleteUser(id);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage(context.getMessage("user.status", new Object[] {"delete"}, Locale.US));
		return resp;
	}
	
	@RequestMapping(value="/deleteAllUsers", method=RequestMethod.GET)
	@ResponseBody
	public BaseResp deleteAllUsers()throws Exception{
		userService.deleteAllUsers();
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage(context.getMessage("user.status", new Object[] {"delete all"}, Locale.US));
		return resp;
	}
	
	//haven't complete
	@RequestMapping(value="/saveOrUpdateAllUser", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveOrUpdateAllTests(@RequestBody List<User> users)throws Exception{
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		ListIterator<User> iter = users.listIterator();
		while(iter.hasNext()){
			User userTemp = iter.next();
			Long checkidloop = userTemp.getId();
			if(userService.getUser(checkidloop)!=null)
				resp.setMessage("New UserLogin has been update successfully");
			else
				resp.setMessage("New UserLogin has been save successfully");
		}
		return resp;
	}
}
