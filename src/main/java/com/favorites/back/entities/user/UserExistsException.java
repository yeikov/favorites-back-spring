package com.favorites.back.entities.user;

public class UserExistsException extends RuntimeException {

	UserExistsException(String email) {
		super("User " + email + " already exixts.");
	}

	
}
