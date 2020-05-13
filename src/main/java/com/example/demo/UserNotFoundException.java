package com.example.demo;

public class UserNotFoundException extends RuntimeException {
	
	UserNotFoundException (Long id){
		super ("Could not find User "+id);
	}
	
	UserNotFoundException (String name){
		super ("Could not find User "+name);
	}

}
