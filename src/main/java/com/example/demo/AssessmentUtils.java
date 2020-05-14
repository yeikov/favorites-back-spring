package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

public class AssessmentUtils {
	@Autowired
	private AssessmentRepository user_registryRepository;
	
	Iterable <Assessment> existsUserRegistry(User u, Registry r) {

		Iterable <Assessment> response = null;
		Iterable <Assessment> request =  user_registryRepository.findAllByUserAndRegistry(u, r);
		
		response = request;
		
		System.out.println("RegistryController.existUserRegistry resp"+ response);
		
		return response;
	}

}
