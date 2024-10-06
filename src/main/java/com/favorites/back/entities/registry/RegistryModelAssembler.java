package com.favorites.back.entities.registry;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.favorites.back.entities.assesment.AssessmentRepository;

@Component
public class RegistryModelAssembler implements RepresentationModelAssembler<Registry, EntityModel<Registry>>{

	@Autowired
	private AssessmentRepository assessmentRepository;
	
	@Override
	public EntityModel<Registry> toModel(Registry registry) {
		 
		EntityModel <Registry> registryModel = EntityModel.of(registry, 
				linkTo(methodOn(RegistryController.class).one(registry.getId())).withSelfRel(),
			    linkTo(methodOn(RegistryController.class).all()).withRel("registries"));
		
		//Conditional link
		if (assessmentRepository.findAllByRegistry(registry).isEmpty()) {
			registryModel.add(linkTo(methodOn(RegistryController.class).delete(registry.getId())).withRel("delete"));
		}
		
		return registryModel;
	}

}
