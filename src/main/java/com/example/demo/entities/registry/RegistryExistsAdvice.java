package com.example.demo.entities.registry;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RegistryExistsAdvice {
	@ResponseBody
	@ExceptionHandler(RegistryExistsException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	Exception userNotFoundHandler(RegistryExistsException ex) {
	    return ex; //.getMessage();
	}
	
}
