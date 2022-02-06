package com.example.demo.entities.user;

public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException (Long id){
		super ("Could not find User "+id);
	}
	
	UserNotFoundException (String name){
		super ("Could not find User "+name);
	}

}
