package com.collins.fileserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="users")
public class User {
	
	@Id
	private String username;
	
	@NotEmpty
	@Column
	private String displayName;
	
	
    @JsonIgnore
	private String password;


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	
	
	

}
