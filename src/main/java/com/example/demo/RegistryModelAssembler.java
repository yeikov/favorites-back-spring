package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RegistryModelAssembler implements RepresentationModelAssembler<Registry, EntityModel<Registry>>{

	@Override
	public EntityModel<Registry> toModel(Registry registry) {
		 
		return new EntityModel<Registry> (registry, 
				linkTo(methodOn(RegistryController.class).one(registry.getId())).withSelfRel(),
			    linkTo(methodOn(RegistryController.class).all()).withRel("registries"));
	}

}
