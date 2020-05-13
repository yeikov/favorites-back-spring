package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class User_RegistryModelAssembler implements RepresentationModelAssembler<User_Registry, EntityModel<User_Registry>> {

	@Override
	public EntityModel<User_Registry> toModel(User_Registry valoration) {
		
		return new EntityModel<> (valoration, 
				linkTo(methodOn(User_RegistryController.class).one(valoration.getId())).withSelfRel()
				//linkTo(methodOn(User_RegistryController.class).getAllUserRegistries()).withRel("valorations")
				);
	}

}
