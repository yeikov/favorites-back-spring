package com.favorites.back.entities.viewer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ViewerExistsAdvice {
	
	@ResponseBody
	@ExceptionHandler(ViewerNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	Exception viewerNotFoundHandler(ViewerNotFoundException ex) {
	    return ex; //.getMessage();
	}
	
}
