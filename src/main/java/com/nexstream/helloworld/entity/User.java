package com.nexstream.helloworld.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="User")
public class User {

	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="loginId")
	private String loginId;
	
	@JsonIgnore
	@Column(name="password")
	private String password;
	
	@Column(name="authenticationToken")
	private String authenticationToken;
	
	@JsonIgnore
	@Column(name="timeStamp")
	private Date timeStamp;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getPassword() {
		return password;
	}

	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAuthToken() {
		return authenticationToken;
	}

	public void setAuthToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	@JsonIgnore
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	@Transient
	private String inputTimeStamp;

	@Transient
	public String getInputTimeStamp() {
		if (inputTimeStamp==null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			try {
				//System.out.println("getTimeStamp: "+String.valueOf(getTimeStamp()));
				Date dateformat = df.parse(String.valueOf(getTimeStamp()));
				inputTimeStamp = df.format(dateformat);
				//System.out.println(inputTimeStamp);
			} catch (ParseException e) {
				System.out.println("date cannot be parse");
			}
			
		}
		return inputTimeStamp;
	}

	@Transient
	public void setInputTimeStamp(String inputTimeStamp) {
		this.inputTimeStamp = inputTimeStamp;
	}
	
	@Transient
	private String inputLoginPass;

	@Transient
	public String getInputLoginPass() {
		if(inputLoginPass==null){
			inputLoginPass = String.valueOf(getPassword());
		}
		return inputLoginPass;
	}

	@Transient
	public void setInputLoginPass(String inputLoginPass) {
		this.inputLoginPass = inputLoginPass;
	}
	
	
}