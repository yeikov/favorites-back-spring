package com.favorites.back.entities.registry;


public class RegistryExistsException extends RuntimeException {
	
	RegistryExistsException(){
		super("The registry already exists.");
	}
	
	RegistryExistsException(Registry registry){
		super("The "+registry.getMedia() +" named "+ registry.getTitle()+ " by " + registry.getAuthor()+ " dated in " + registry.getProductionDate().getYear() +" already exists.");
	}
	
	
}
