package com.favorites.back.entities.registry;

public class RegistryNotFoundException extends RuntimeException {
	public RegistryNotFoundException (Long id){
		super ("Could not find Registry " + id);
	}

}
