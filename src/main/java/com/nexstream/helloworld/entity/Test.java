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
@Table(name="test")
public class Test {

	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="comments")
	private String comments;
	
	@JsonIgnore
	@Column(name="number")
	private Integer number;
	
	@JsonIgnore
	@Column(name="dotnum")
	private Double dotnum;
	
	@JsonIgnore
	@Column(name="date")
	private Date date;

	@JsonIgnore
	public Integer getNumber() {
		return number;
	}

	@JsonIgnore
	public void setNumber(Integer number) {
		this.number=number;
	}

	public Double getDotnum() {
		return dotnum;
	}

	@JsonIgnore
	public void setDotnum(Double dotnum) {
		this.dotnum = dotnum;
	}

	public Date getDate() {
		return date;
	}

	@JsonIgnore
	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Transient
	private String inputNumber;
	@Transient
	private String inputDotnum;
	@Transient
	private String inputDate;

	@Transient
	public String getInputNumber() {
		if (inputNumber==null) 
			inputNumber = String.valueOf(getNumber());
		return inputNumber;
	}

	@Transient
	public void setInputNumber(String inputNumber) {
		this.inputNumber = inputNumber;
	}
	
	@Transient
	public String getInputDotnum() {
		if (inputDotnum==null) 
			inputDotnum = String.valueOf(getDotnum());
		return inputDotnum;
	}
	
	@Transient
	public void setInputDotnum(String inputDotnum) {
		this.inputDotnum = inputDotnum;
	}

	@Transient
	public String getInputDate() {
		if (inputDate==null) {
			//Date date = getDate();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dateformat = df.parse(String.valueOf(getDate()));
				inputDate = df.format(dateformat);
			} catch (ParseException e) {
				//e.printStackTrace();
				System.out.println("date cannot be parse");
			}
			//inputDate = String.valueOf(getDate());
			
		}
		return inputDate;
	}

	@Transient
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
}