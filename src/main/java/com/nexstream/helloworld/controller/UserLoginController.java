package com.nexstream.helloworld.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexstream.helloworld.domains.BaseResp;
import com.nexstream.helloworld.domains.ErrorResp;
import com.nexstream.helloworld.entity.UserLogin;
import com.nexstream.helloworld.service.UserLoginService;

@RestController
public class UserLoginController {
	
	@Resource
	private UserLoginService userLoginService;
	
	//-----------database------------------
	@RequestMapping(value="/getUserLogins", method=RequestMethod.GET)
	@ResponseBody
	public List<UserLogin> geTests()throws Exception{
		return userLoginService.geTests();
	}
	
	@RequestMapping(value="/getUserLogin/{id}", method=RequestMethod.GET)
	@ResponseBody
	public UserLogin geTestById(@PathVariable Long id)throws Exception{
		return userLoginService.getTest(id);
	}
	
	@SuppressWarnings("deprecation")
	private Boolean validateType(String value, Object type){
		Boolean isValid = true;
		
		try{
			if (type instanceof Integer){
				Integer.parseInt(value);
				return isValid;
			}
			if(type instanceof Double){
				Double.parseDouble(value);
				return isValid;
			}
			if(type instanceof Date){
				Date.parse(value);
				return isValid;
			}
		}catch(Exception ex){
			isValid = false;
		}
		return isValid;
	}

	//@SuppressWarnings("deprecation")
	@RequestMapping(value="/saveNewUserLogin", method=RequestMethod.POST)
	@ResponseBody
	//public Object saveTest(@RequestBody Object testObj)throws Exception{
	public Object saveTest(@RequestBody UserLogin test)throws Exception{
	
		
		String userNameField = test.getUserName();
		//Object numberField = test.getNumber();
		//Double dotnumField = test.getDotnum();
		//Date dateField = test.getDate();
		
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode("500");			
		
		if (userNameField==null) 
			userNameField="";	
		
		if (userNameField.equalsIgnoreCase("")){
			errorResp.setMessage("User Name field is require!");
			return errorResp;
		}
		
		String inputLoginId = test.getLoginId();
		if (inputLoginId==null) 
			inputLoginId="";
		
		if (inputLoginId.equalsIgnoreCase("")){
			errorResp.setMessage("login id field is require!");
			return errorResp;
		}
		
		/*if (validateType(test.getInputNumber(), new Integer(0))){
			test.setNumber(Integer.parseInt(test.getInputNumber()));
		}else {
			errorResp.setMessage("Invalid number value specified!");
			return errorResp;
		}
*/		
		String inputloginPass = test.getLoginPass();
		if (inputloginPass == null)
			inputloginPass = "";
		
		if(inputloginPass.equalsIgnoreCase("")){
			errorResp.setMessage("login password field is require!");
			return errorResp;
		}
		
		/*if(validateType(inputDotnum, new Double(0)))
			test.setDotnum(Double.parseDouble(inputDotnum));
		else{
			errorResp.setMessage("Invalid dotnum value specified!");
			return errorResp;
		}*/
		
		String inputtimeStamp = test.getInputTimeStamp();
		if (inputtimeStamp == null)
			inputtimeStamp="";
		
		if(inputtimeStamp.equalsIgnoreCase("")){
			errorResp.setMessage("time stamp field is require!");	
			return errorResp;
		}
		
		if(validateType(inputtimeStamp, null)){
			Date date = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
			try{
				date = df.parse(inputtimeStamp);
				test.setTimeStamp(date);
			}catch(ParseException e){
				errorResp.setMessage("Invalid date value specified!(yyyy-MM-dd)");
				return errorResp;
			}
		}
		
		userLoginService.saveOrUpdate(test);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("New UserLogin has been added successfully");
		return resp;
	}
	
	@RequestMapping(value="/updateUserLogin/{id}", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveTest(@PathVariable Long id, @RequestBody UserLogin test)throws Exception{
		test.setId(id);
		userLoginService.saveOrUpdate(test);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("New UserLogin has been update successfully");
		return resp;
	}
	
	@RequestMapping(value="/saveOrUpdateUserLogin", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveOrUpdateTest(@RequestBody UserLogin test)throws Exception{
		userLoginService.saveOrUpdate(test);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		long checkid = test.getId();
				
		if(userLoginService.getTest(checkid)!=null)
			resp.setMessage("New UserLogin has been update successfully");
		else
			resp.setMessage("New UserLogin has been save successfully");
		return resp;
	}
	
	@RequestMapping(value="/deleteUserLogin", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp deleteTest(@RequestBody UserLogin test)throws Exception{
		Long id = test.getId();
		userLoginService.deleteTest(id);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("UserLogin has been delete successfully");
		return resp;
	}
	
	@RequestMapping(value="/deleteAllUserLogin", method=RequestMethod.GET)
	@ResponseBody
	public BaseResp deleteAllTests()throws Exception{
		userLoginService.deleteAllTests();
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("All UserLogin has been delete successfully");
		return resp;
	}
	
	@RequestMapping(value="/saveOrUpdateAllUserLogin", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveOrUpdateAllTests(@RequestBody List<UserLogin> tests)throws Exception{
		//System.out.println(tests);
		//testService.saveOrUpdateAllTests(tests);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		ListIterator<UserLogin> iter = tests.listIterator();
		while(iter.hasNext()){
			UserLogin testtemp = iter.next();
			Long checkidloop = testtemp.getId();
			if(userLoginService.getTest(checkidloop)!=null)
				resp.setMessage("New UserLogin has been update successfully");
			else
				resp.setMessage("New UserLogin has been save successfully");
		}
		
		return resp;
	}
}
