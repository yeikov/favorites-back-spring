package com.favorites.back.entities.assesment;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;



@Component
public class AssessmentModelAssembler implements RepresentationModelAssembler<Assessment, EntityModel<Assessment>> {

	@SuppressWarnings("null")
	@Override
	@NonNull
	public EntityModel<Assessment> toModel(Assessment assessment) {

		
		return EntityModel.of(assessment, 
			linkTo(methodOn(AssessmentController.class).one(assessment.getId())).withSelfRel(),
			linkTo(methodOn(AssessmentController.class).allByUser(assessment.getUser().getId())).withRel("assessments"));
		
		

	}

}
