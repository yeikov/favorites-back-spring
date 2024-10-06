package com.favorites.back.entities.registry;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RegistryNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(RegistryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	Exception registryNotFoundHandler(RegistryNotFoundException ex) {
		return ex;
	}
}
