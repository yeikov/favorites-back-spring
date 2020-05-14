package com.example.demo;

public class UserExistsException extends RuntimeException {

	UserExistsException(String email) {
		super("User " + email + " already exixts.");
		// TODO Auto-generated constructor stub
	}

	
}
