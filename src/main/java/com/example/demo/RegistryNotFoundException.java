package com.example.demo;

public class RegistryNotFoundException extends RuntimeException {
	RegistryNotFoundException (Long id){
		super ("Could not find Registry " + id);
	}

}
