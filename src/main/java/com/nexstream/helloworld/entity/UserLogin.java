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
@Table(name="UserLogin")
public class UserLogin {

	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="loginId")
	private String loginId;
	
	@Column(name="loginPass")
	private String loginPass;
	
	@Column(name="authToken")
	private String authToken;
	
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

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
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
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
			try {
				Date dateformat = df.parse(String.valueOf(getTimeStamp()));
				inputTimeStamp = df.format(dateformat);
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

	
}