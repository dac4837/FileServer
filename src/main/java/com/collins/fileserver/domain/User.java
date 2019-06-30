package com.collins.fileserver.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	@Column(unique = true, nullable = false)
	private String username;

	@NotEmpty
	@NotNull
	@Column
	private String displayName;

	@NotEmpty
	@NotNull
	@Column(length = 60)
	@JsonIgnore
	private String password;

	@ManyToOne
    @JoinColumn(name="role_id", nullable=false)
	private Role role;


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
