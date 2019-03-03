package com.collins.fileserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User {
	
	@Id
	private String username;
	
	@NotEmpty
	@NotNull
	@Column
	
	private String displayName;
	
	
	@NotEmpty
	@NotNull
	@Column
	private String role;
	
	
	@NotEmpty
	@NotNull
	@Column(length= 60)
    @JsonIgnore
	private String password;


	public String getUsername() {
		return username;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
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


	@Override
	public String toString() {
		return "User [username=" + username + ", displayName=" + displayName + ", role=" + role + "]";
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        if (!username.equals(user.username)) {
            return false;
        }
        return true;
    }
	

}
