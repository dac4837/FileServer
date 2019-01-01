package com.collins.fileserver.domain;

//import javax.persistence.Entity;

//@Entity
public class Page {
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirectory() {
		return name.toLowerCase();
	}
	
	

}
