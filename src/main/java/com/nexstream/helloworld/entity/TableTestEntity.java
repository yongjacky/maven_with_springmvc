package com.nexstream.helloworld.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TableTest")
public class TableTestEntity {

	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int ID;
	
	@Column(name="name")
	private String name;

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

	public String getComments() {
		return name;
	}

	public void setComments(String name) {
		this.name = name;
	}
	
	
}