package com.nexstream.helloworld.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexstream.helloworld.domains.Employee;

@RestController
public class RestSample {

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
}
