package com.nexstream.helloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.stereotype.Controller;

import com.nexstream.helloworld.domains.Employee;

@RestController
//public class RestController {

	//@RestController
	public class RestSample {

		@RequestMapping(value="/getEmployee", method=RequestMethod.GET)
		@ResponseBody
		public Employee getEmployee(){
			Employee employee = new Employee();
			employee.setEmpName("David Chong");
			employee.setEmpNo("001");
			
			return employee;
		}
	}
	
//}
