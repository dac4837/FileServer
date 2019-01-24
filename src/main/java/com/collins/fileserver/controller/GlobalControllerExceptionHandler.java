package com.collins.fileserver.controller;

import java.sql.SQLException;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	
	@ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException e, RedirectAttributes redirectAttributes) {
		
		//http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
		throw new ClientWebException (e.getCause().getMessage());

    }
	
	@ExceptionHandler(JdbcSQLException.class)
    public String handleJdbcSQLException(JdbcSQLException e, RedirectAttributes redirectAttributes) {
		
		throw new ClientWebException ("Error saving. Already exists");

    }
	
	@ExceptionHandler(SQLException.class)
    public String handleSQLException(SQLException e, RedirectAttributes redirectAttributes) {
		
		throw new ClientWebException (e.getMessage());

    }
}
