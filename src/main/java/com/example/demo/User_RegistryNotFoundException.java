package com.example.demo;

public class User_RegistryNotFoundException extends RuntimeException{
	
	User_RegistryNotFoundException (Long id){
		super ("Could not find User_Registry " + id);
	}
}
