package com.collins.fileserver.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
     
    private RoleDto role;
    
    public enum RoleDto {
    	ROLE_ADMIN, ROLE_READER, ROLE_WRITER, ROLE_LOGIN
    }
     
}
