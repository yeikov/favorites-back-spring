package com.favorites.back.entities.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>>{

	@SuppressWarnings("null")
	@Override
	public EntityModel<User> toModel(User user) {

		return EntityModel.of(user,
				linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
			    linkTo(methodOn(UserController.class).all()).withRel("users"));
		
	}

}
