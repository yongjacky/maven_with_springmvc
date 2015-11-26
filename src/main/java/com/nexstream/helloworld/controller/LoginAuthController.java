package com.nexstream.helloworld.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexstream.helloworld.domains.BaseResp;
import com.nexstream.helloworld.domains.ErrorResp;
import com.nexstream.helloworld.domains.LoginAuth;
import com.nexstream.helloworld.entity.User;
import com.nexstream.helloworld.service.UserService;


@RestController
public class LoginAuthController {
	SecureRandom sr = new SecureRandom();
	ApplicationContext context = new ClassPathXmlApplicationContext("springModules.xml");
		
	@Resource
	private UserService userService;
	
		
	//-----------database------------------
	private String generatAndUpdateToken(User user) throws Exception{
		String generateToken = new BigInteger(130, sr).toString(32);
		user.setAuthToken(generateToken);
		Date date = new Date();
		user.setTimeStamp(date);
		userService.saveOrUpdate(user);
		return generateToken;
	}
	
	@RequestMapping(value="/messageDisplay", method=RequestMethod.POST)
	@ResponseBody
	public void messageDisplay()throws Exception{
		String message = context.getMessage("empty.field", new Object[] {"login id"}, Locale.US);
		System.out.println("message: "+message);
	}

	//check duplicate login
	@RequestMapping(value="/userLogin", method=RequestMethod.POST)
	@ResponseBody
	public Object userLogin(@RequestBody LoginAuth login)throws Exception{
	
		String userLoginId = login.getLoginId();
		String userPassField = login.getUserPass();
		boolean isLoginValid = false;
			
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode("500");
		
		//empty field check
		if (userLoginId==null) 
			userLoginId="";	
		
		if (userLoginId.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("empty.field", new Object[] {"login id"}, Locale.US));
			return errorResp;
		}
		
		if (userPassField==null) userPassField="";
		
		if (userPassField.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("empty.field", new Object[] {"password"}, Locale.US));
			return errorResp;
		}
		
		//encode user input password for comparision
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String userInputPassword = md5.encodePassword(userPassField, userLoginId);
		
		//query user from database by login id
		User userDb = userService.getUserByLoginId(userLoginId);
		User userDbTemp = null;
		if (userDb!=null) userDbTemp = userDb;
		else{
			errorResp.setMessage(context.getMessage("incorrect.field", new Object[] {"login id"}, Locale.US));
			return errorResp;
		}
		
		//compare input password with database password
		if(userInputPassword.equalsIgnoreCase(userDbTemp.getPassword())) isLoginValid = true;
		else{
			errorResp.setMessage(context.getMessage("incorrect.field", new Object[] {"password"}, Locale.US));
			return errorResp;
		}
		
		//generate token if password matches
		if(isLoginValid){
			String generatedToken = generatAndUpdateToken(userDbTemp);			
			login.setAuthenticationToken(generatedToken);
			
			resp.setMessage(context.getMessage("login.status", new Object[] {"successful"}, Locale.US));
			return login;
		}
		resp.setMessage(context.getMessage("login.status", new Object[] {"fail"}, Locale.US));
		return resp;
	}
	
	@RequestMapping(value="/userLogout", method=RequestMethod.POST)
	@ResponseBody
	public Object userLogout(@RequestBody LoginAuth logout)throws Exception{
		String userLoginId = logout.getLoginId();
		String databaseToken = null;
		String receiveToken = null;
		//obtain input token
		if(logout.getAuthenticationToken()!=null)
			receiveToken = logout.getAuthenticationToken();
		
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode("500");
		
		if (receiveToken==null) receiveToken="";
		if(receiveToken.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("token.status", new Object[] {"missing"}, Locale.US));
			return errorResp;
		}
		
		//query database for token by login id
		User userDb = userService.getUserByLoginId(userLoginId);
		if(userDb!=null) databaseToken = userDb.getAuthToken();
		
		//reset token after user logout
		if (receiveToken.equalsIgnoreCase(databaseToken)){
			generatAndUpdateToken(userDb);
						
			BaseResp resp = new BaseResp();
			resp.setCode("200");
			resp.setMessage(context.getMessage("logout.status", new Object[] {"success"}, Locale.US));
			return resp;
			}
		else{
			errorResp.setMessage(context.getMessage("token.status", new Object[] {"incorrect"}, Locale.US));
			return errorResp;
		}	
	}
	
	//change the get id from database to get token
	@RequestMapping(value="/userChangePassword", method=RequestMethod.POST)
	@ResponseBody
	public Object userChangePassword(@RequestBody LoginAuth changePass)throws Exception{
		String userLoginId = changePass.getLoginId();
		String databaseToken = null;
		String receiveToken = null;
		
		//get input token
		if(changePass.getAuthenticationToken()!=null)
			receiveToken = changePass.getAuthenticationToken();
		
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode("500");
		
		if (receiveToken==null) receiveToken="";
		if(receiveToken.equalsIgnoreCase("")){
			errorResp.setMessage(context.getMessage("token.status", new Object[] {"missing"}, Locale.US));
			return errorResp;
		}
		
		//query database for token by login id
		User userDb = userService.getUserByLoginId(userLoginId);
		if(userDb!=null) databaseToken = userDb.getAuthToken();
		
		//token validation
		if (receiveToken.equalsIgnoreCase(databaseToken)){
			String newPassword = changePass.getUserPass();
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();		
			userDb.setPassword(md5.encodePassword(newPassword, userLoginId));
			userService.saveOrUpdate(userDb);
						
			BaseResp resp = new BaseResp();
			resp.setCode("200");
			resp.setMessage("change password success");
			return resp;
			}
		else{
			errorResp.setMessage(context.getMessage("token.status", new Object[] {"incorrect"}, Locale.US));
			return errorResp;
		}	
	}
}
