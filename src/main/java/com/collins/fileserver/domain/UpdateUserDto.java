package com.collins.fileserver.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.collins.fileserver.domain.UserDto.RoleDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateUserDto {

    @NotNull
    @NotEmpty
    @Length(min=1, max=200)
    private String displayName;
     
    @Length(max=200)
    private String password;
     
    
    private RoleDto role;
   
    @NotNull
    @NotEmpty
    private String roleString;
    
    public void setRoleString(String roleString) {
    	this.roleString = roleString;
    	if (roleString == null) return;
    	if (roleString.equals("ROLE_LOGIN")) this.role = RoleDto.ROLE_LOGIN;
    	if (roleString.equals("ROLE_READER")) this.role = RoleDto.ROLE_READER;
    	if (roleString.equals("ROLE_WRITER")) this.role = RoleDto.ROLE_WRITER;
    	if (roleString.equals("ROLE_ADMIN")) this.role = RoleDto.ROLE_ADMIN;
    }
     
}
