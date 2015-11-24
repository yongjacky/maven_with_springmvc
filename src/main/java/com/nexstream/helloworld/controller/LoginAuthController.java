package com.nexstream.helloworld.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexstream.helloworld.domains.BaseResp;
import com.nexstream.helloworld.domains.ErrorResp;
import com.nexstream.helloworld.domains.loginAuth;
import com.nexstream.helloworld.entity.User;
import com.nexstream.helloworld.service.UserService;


@RestController
public class LoginAuthController {
	SecureRandom sr = new SecureRandom();
	
	@Resource
	private UserService userService;
	
	//-----------database------------------

	@RequestMapping(value="/loginAuth", method=RequestMethod.POST)
	@ResponseBody
	public Object userLogin(@RequestBody loginAuth login)throws Exception{
	
		
		String userNameField = login.getUserName();
		String userPassField = login.getUserPass();
		boolean isLoginValid = false;
		
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode("500");			
		
		if (userNameField==null) 
			userNameField="";	
		
		if (userNameField.equalsIgnoreCase("")){
			errorResp.setMessage("User Name field is require!");
			return errorResp;
		}
		
		
		if (userPassField==null) 
			userPassField="";
		
		if (userPassField.equalsIgnoreCase("")){
			errorResp.setMessage("login id field is require!");
			return errorResp;
		}
		
		System.out.println("userPassField="+userPassField);
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String userInputPassword = md5.encodePassword(userPassField, userNameField);
		
		List<User> userDb = userService.getUserPass(userNameField);
		
		User userDbTemp = null;
		
		if (userDb.size()>0){
			userDbTemp = userDb.get(0);
		}
		
		
		//System.out.println(md5.encodePassword(userPassField, userNameField));
		//System.out.println(userDbTemp.getPassword());
		
		if(userInputPassword.equalsIgnoreCase(userDbTemp.getPassword())){
			isLoginValid = true;
			/*BaseResp resp = new BaseResp();
			resp.setCode("200");
			resp.setMessage("login successful");
			return resp;*/
			}
		else
			System.out.println("tabuli");
		
		/*This works by choosing 130 bits from a cryptographically secure random bit generator, and 
		encoding them in base-32. 128 bits is considered to be cryptographically strong, but each digit in a base 32 
		number can encode 5 bits, so 128 is rounded up to the next multiple of 5. This encoding is compact and 
		efficient, with 5 random bits per character. Compare this to a random UUID, which only has 3.4 bits per 
		character in standard layout, and only 122 random bits in total.

		If you allow session identifiers to be easily guessable (too short, flawed random number generator, etc.), 
		attackers can hijack other's sessions. Note that SecureRandom objects are expensive to initialize, so you'll want 
		to keep one around and reuse it.
		*/
		
		if(isLoginValid){
			String generateToken = new BigInteger(130, sr).toString(32);
			System.out.println("generateToken: "+generateToken);
			userDbTemp.setAuthToken(generateToken);
			userService.saveOrUpdate(userDbTemp);
		}

		
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("run success");
		return resp;
	}
	
	@RequestMapping(value="/userLogout", method=RequestMethod.POST)
	@ResponseBody
	public Object userLogout(@RequestBody loginAuth logout)throws Exception{
		String userNameField = logout.getUserName();
		
		List<User> userDb = userService.getUserPass(userNameField);
		
		User userDbTemp = null;
		
		if (userDb.size()>0){
			userDbTemp = userDb.get(0);
		}
		
		String generateLogoutToken = new BigInteger(130, sr).toString(32);
		System.out.println("generateLogoutToken: "+generateLogoutToken);
		userDbTemp.setAuthToken(generateLogoutToken);
		userService.saveOrUpdate(userDbTemp);
		
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("logout success");
		return resp;
		
	}
	
}
