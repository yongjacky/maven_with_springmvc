package com.nexstream.helloworld.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.nexstream.helloworld.domains.Employee;
import com.nexstream.helloworld.domains.Employee.Children;
import com.nexstream.helloworld.domains.ErrorResp;
import com.nexstream.helloworld.entity.Test;
import com.nexstream.helloworld.service.TestService;

@RestController
public class RestSample {
	
	@Resource
	private TestService testService;

	@RequestMapping(value="/getEmployee", method=RequestMethod.GET)
	@ResponseBody
	public Employee getEmployee(){
		
		Employee employee = new Employee();
		employee.setEmpName("David Chong");
		employee.setEmpNo("001");
		return employee;
	}
	
	@RequestMapping(value="/getEmployees", method=RequestMethod.GET)
	@ResponseBody
	public List<Employee> getEmployees(){
		
		List<Employee> employees = new ArrayList<Employee>(); 
		for(int i = 0; i<3;i++)
		{
			Employee employee = new Employee();
			employee.setEmpNo(String.valueOf(i));
			employee.setEmpName("name ".concat(String.valueOf(i)));
			employees.add(employee);
		}
		
		return employees;
	}
	
	@RequestMapping(value="/getEmployeesObject", method=RequestMethod.GET)
	@ResponseBody
	public Employee[] getEmployeesObject(){
		
		Employee[] employeeArray = new Employee[3];
		for(int i = 0; i<3;i++)
		{
			employeeArray[i] = new Employee();
			employeeArray[i].setEmpName("David Chong");
			employeeArray[i].setEmpNo("00"+i+1);
		}
		return employeeArray;
	}
	
	@RequestMapping(value="/getEmployeesChild", method=RequestMethod.GET)
	@ResponseBody
	public List<Employee> getEmployeesChild(){
		
		List<Employee> employees = new ArrayList<Employee>();
		for(int i = 0; i<3;i++)
		{
			Employee employee = new Employee();
			employee.setEmpNo(String.valueOf(i));
			employee.setEmpName("name ".concat(String.valueOf(i)));
			List<Children> childrens = new ArrayList<Employee.Children>();
			for(int j = 0;j<3;j++){
				Children child = employee.new Children();
				child.setChildname("child ".concat(String.valueOf(j)));
				child.setAge(String.valueOf(j));
				childrens.add(child);
			}
			employee.setEmpChild(childrens);
			employees.add(employee);
		}
		
		return employees;
	}
	
	//-----------database------------------
	@RequestMapping(value="/getTests", method=RequestMethod.GET)
	@ResponseBody
	public List<Test> geTests()throws Exception{
		return testService.geTests();
	}
	
	@RequestMapping(value="/getTest/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Test geTestById(@PathVariable Long id)throws Exception{
		return testService.getTest(id);
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

	
	@RequestMapping(value="/saveNewTest", method=RequestMethod.POST)
	@ResponseBody
	public Object saveTest(@RequestBody Test test)throws Exception{
	
			
		String commentField = test.getComments();
		String inputDate = test.getInputDate();
		String inputNumber = test.getInputNumber();
		String inputDotnum = test.getInputDotnum();

		
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode("500");			
		
		if (commentField==null) commentField="";	
		
		if (commentField.equalsIgnoreCase("")){
			errorResp.setMessage("Comment field is require!");
			return errorResp;
		}
		
		if (inputNumber==null) inputNumber="";
		
		if (inputNumber.equalsIgnoreCase("")){
			errorResp.setMessage("number field is require!");
			return errorResp;
		}
		
		if (validateType(test.getInputNumber(), new Integer(0))){
			test.setNumber(Integer.parseInt(test.getInputNumber()));
		}else {
			errorResp.setMessage("Invalid number value specified!");
			return errorResp;
		}
		
		if (inputDotnum == null) inputDotnum = "";
		
		if(inputDotnum.equalsIgnoreCase("")){
			errorResp.setMessage("decimal number field is require!");
			return errorResp;
		}
		
		if(validateType(inputDotnum, new Double(0)))
			test.setDotnum(Double.parseDouble(inputDotnum));
		else{
			errorResp.setMessage("Invalid dotnum value specified!");
			return errorResp;
		}
		
		if (inputDate == null) inputDate="";
		
		if(inputDate.equalsIgnoreCase("")){
			errorResp.setMessage("date field is require!");	
			return errorResp;
		}
		
		if(validateType(inputDate, null)){
			Date date = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try{
				date = df.parse(inputDate);
				test.setDate(date);
			}catch(ParseException e){
				errorResp.setMessage("Invalid date value specified!(yyyy-MM-dd)");
				return errorResp;
			}
		}
		
		
		
		testService.saveOrUpdate(test);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("New Test has been added successfully");
		return resp;
	}
	
	@RequestMapping(value="/updateTest/{id}", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveTest(@PathVariable Long id, @RequestBody Test test)throws Exception{
		test.setId(id);
		testService.saveOrUpdate(test);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("New Test has been update successfully");
		return resp;
	}
	
	@RequestMapping(value="/saveOrUpdateTest", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveOrUpdateTest(@RequestBody Test test)throws Exception{
		testService.saveOrUpdate(test);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		long checkid = test.getId();
				
		if(testService.getTest(checkid)!=null)
			resp.setMessage("New Test has been update successfully");
		else
			resp.setMessage("New Test has been save successfully");
		return resp;
	}
	
	@RequestMapping(value="/deleteTest", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp deleteTest(@RequestBody Test test)throws Exception{
		Long id = test.getId();
		testService.deleteTest(id);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("Test has been delete successfully");
		return resp;
	}
	
	@RequestMapping(value="/deleteAllTests", method=RequestMethod.GET)
	@ResponseBody
	public BaseResp deleteAllTests()throws Exception{
		testService.deleteAllTests();
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		resp.setMessage("All Test has been delete successfully");
		return resp;
	}
	
	@RequestMapping(value="/saveOrUpdateAllTests", method=RequestMethod.POST)
	@ResponseBody
	public BaseResp saveOrUpdateAllTests(@RequestBody List<Test> tests)throws Exception{
		//System.out.println(tests);
		//testService.saveOrUpdateAllTests(tests);
		BaseResp resp = new BaseResp();
		resp.setCode("200");
		ListIterator<Test> iter = tests.listIterator();
		while(iter.hasNext()){
			Test testtemp = iter.next();
			Long checkidloop = testtemp.getId();
			if(testService.getTest(checkidloop)!=null)
				resp.setMessage("New Test has been update successfully");
			else
				resp.setMessage("New Test has been save successfully");
		}
		
		return resp;
	}
}
