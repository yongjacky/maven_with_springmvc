package com.nexstream.helloworld.domains;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String empNo;
	private String empName;
	private String dateOfBirth;
	private List<Children> empChild = new ArrayList<Children>();
	
	public List<Children> getEmpChild() {
		return empChild;
	}

	public void setEmpChild(List<Children> empChild) {
		this.empChild = empChild;
	}

	public String getEmpNo() {
		return empNo;
	}
	
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	
	public String getEmpName() {
		return empName;
	}
	
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	
	public class Children{
		private String childname;
		private String age;
		
		public String getChildname() {
			return childname;
		}
		
		public void setChildname(String childname) {
			this.childname = childname;
		}
		
		public String getAge() {
			return age;
		}
		
		public void setAge(String age) {
			this.age = age;
		}
		
	}

}


