package com.collins.fileserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
/*
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public void handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
		System.out.println("HIT Maxupload");
		throw new ClientWebException("Message: " + ex.getMessage() + " local: " + ex.getLocalizedMessage());
	}

	@ExceptionHandler(SizeLimitExceededException.class)
	public void handleSizeLimitExceeded(SizeLimitExceededException ex) {
		System.out.println("HIT size limit");
		throw new ClientWebException("Message: " + ex.getMessage() + " local: " + ex.getLocalizedMessage());
	}
	*/
	
	@ExceptionHandler(value = Exception.class)
	  public void
	  defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		System.out.println("Default exception handler");
		/*
	    // If the exception is annotated with @ResponseStatus rethrow it and let
	    // the framework handle it - like the OrderNotFoundException example
	    // at the start of this post.
	    // AnnotationUtils is a Spring Framework utility class.
	    if (AnnotationUtils.findAnnotation
	                (e.getClass(), ResponseStatus.class) != null)
	      throw e;
	    
	    throw new ClientWebException(e.getLocalizedMessage());

	 */
	  }
}
