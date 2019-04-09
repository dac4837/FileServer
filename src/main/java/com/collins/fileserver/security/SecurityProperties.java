package com.collins.fileserver.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
	
	private String defaultUser;
	private String defaultPassword;
	public String getDefaultUser() {
		return defaultUser;
	}
	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}
	public String getDefaultPassword() {
		return defaultPassword;
	}
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	
	

}
