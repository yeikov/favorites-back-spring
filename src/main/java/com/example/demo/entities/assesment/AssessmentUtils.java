package com.example.demo.entities.assesment;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.registry.Registry;
import com.example.demo.entities.user.User;

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
