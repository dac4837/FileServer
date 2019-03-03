package com.collins.fileserver.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserDto {

	@NotNull
    @NotEmpty
    @Length(min=4, max=50)
    private String username;
     
    @NotNull
    @NotEmpty
    @Length(min=1, max=200)
    private String displayName;
     
    @NotNull
    @NotEmpty
    @Length(min=1, max=200)
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
