package com.nexstream.helloworld.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="Image")
public class Image {

	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@JsonIgnore
	@Column(name="filePath")
	private String filePath;
	
	@JsonIgnore
	@Column(name="fileName")
	private String fileName;
	
	@JsonIgnore
	@Column(name="loginId")
	private String loginId;
		
	@JsonIgnore
	@Column(name="timeStamp")
	private Date timeStamp;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	@JsonIgnore
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	@JsonIgnore
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getFilePath() {
		return filePath;
	}

	@JsonIgnore
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	@JsonIgnore
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
	
}