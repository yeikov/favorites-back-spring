package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

public class User_RegistryUtils {
	@Autowired
	private User_RegistryRepository user_registryRepository;
	
	Iterable <User_Registry> existsUserRegistry(User u, Registry r) {

		Iterable <User_Registry> response = null;
		Iterable <User_Registry> request =  user_registryRepository.findAllByUserAndRegistry(u, r);
		
		response = request;
		
		System.out.println("RegistryController.existUserRegistry resp"+ response);
		
		return response;
	}

}
