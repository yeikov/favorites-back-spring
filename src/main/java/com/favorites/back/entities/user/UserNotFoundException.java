package com.favorites.back.entities.user;

public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException (Long id){
		super ("101_Could not find User "+id);
	}
	
	UserNotFoundException (String name){
		super ("101_Could not find User "+name);
	}

}
