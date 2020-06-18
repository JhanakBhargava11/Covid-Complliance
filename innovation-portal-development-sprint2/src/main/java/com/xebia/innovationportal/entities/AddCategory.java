package com.xebia.innovationportal.entities;

import javax.persistence.Entity;
import javax.persistence.Id;


public class AddCategory {

	private Integer Categoryid;
	private String name;
	
	public Integer getCategoryid() {
		return Categoryid;
	}
	public void setCategoryid(Integer categoryid) {
		Categoryid = categoryid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
