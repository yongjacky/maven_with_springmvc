package com.nexstream.helloworld.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import javax.annotation.Resource;
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
	
	@Resource
	private UserService userService;
	
		
	//-----------database------------------
	public String generatAndUpdateToken(User user) throws Exception{
		String generateToken = new BigInteger(130, sr).toString(32);
		user.setAuthToken(generateToken);
		Date date = new Date();
		user.setTimeStamp(date);
		userService.saveOrUpdate(user);
		return generateToken;
	}

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
			errorResp.setMessage("login id field is require!");
			return errorResp;
		}
		
		if (userPassField==null) userPassField="";
		
		if (userPassField.equalsIgnoreCase("")){
			errorResp.setMessage("login password field is require!");
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
			errorResp.setMessage("username is incorrect!");
			return errorResp;
		}
		
		//compare input password with database password
		if(userInputPassword.equalsIgnoreCase(userDbTemp.getPassword())) isLoginValid = true;
		else{
			errorResp.setMessage("password incorrect");
			return errorResp;
		}
		
		//generate token if password matches
		if(isLoginValid){
			String generatedToken = generatAndUpdateToken(userDbTemp);			
			login.setAuthenticationToken(generatedToken);
			
			resp.setMessage("login success");
			return login;
		}
		resp.setMessage("login not success");
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
			errorResp.setMessage("authentication token is missing");
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
			resp.setMessage("logout success");
			return resp;
			}
		else{
			errorResp.setMessage("authentication token is incorrect");
			return errorResp;
		}	
	}
	
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
			errorResp.setMessage("authentication token is missing");
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
			errorResp.setMessage("authentication token is incorrect");
			return errorResp;
		}	
	}
}
