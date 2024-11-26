package com.favorites.back.entities.viewer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ViewerNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(ViewerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	Exception viewerNotFoundHandler(ViewerNotFoundException ex) {
	    return ex; //.getMessage();
	}
	
}
