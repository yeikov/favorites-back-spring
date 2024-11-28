package com.favorites.back.entities.assesment;

import org.springframework.beans.factory.annotation.Autowired;

import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.viewer.Viewer;

public class AssessmentUtils {
	@Autowired
	private AssessmentRepository viewer_registryRepository;
	
	Iterable <Assessment> existsViewerRegistry(Viewer u, Registry r) {

		Iterable <Assessment> response = null;
		Iterable <Assessment> request =  viewer_registryRepository.findAllByViewerAndRegistry(u, r);
		
		response = request;
		
		System.out.println("RegistryController.existViewerRegistry resp"+ response);
		
		return response;
	}

}
