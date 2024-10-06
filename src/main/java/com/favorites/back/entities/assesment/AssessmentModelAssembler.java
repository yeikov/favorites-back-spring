package com.favorites.back.entities.assesment;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.hibernate.mapping.Array;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AssessmentModelAssembler implements RepresentationModelAssembler<Assessment, EntityModel<Assessment>> {

	@Override
	public EntityModel<Assessment> toModel(Assessment assessment) {

		
		return EntityModel.of(assessment, 
			linkTo(methodOn(AssessmentController.class).one(assessment.getId())).withSelfRel(),
			linkTo(methodOn(AssessmentController.class).allByUser(assessment.getUser().getId())).withRel("assessments"));
		
		

	}

}
