package com.collins.fileserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientWebException extends RuntimeException {

	private static final long serialVersionUID = 7574189120779752115L;

	public ClientWebException() {
		super();
	}
	
	public ClientWebException(String message) {
		super(message);
	}

}
