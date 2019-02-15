package com.collins.fileserver.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5889996913281892324L;
	
	public FileExistsException(String message) {
		super (message);
	}

}
