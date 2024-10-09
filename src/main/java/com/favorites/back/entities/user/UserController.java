package com.favorites.back.entities.user;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.favorites.back.BackApplication;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;

@Controller
@RequestMapping(path = BackApplication.backEndUrl + "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserModelAssembler assembler;

	@CrossOrigin // (origins="http://localhost:4200")
	@GetMapping
	@ResponseBody
	CollectionModel<EntityModel<User>> all() {

		List<EntityModel<User>> users = userRepository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
	}

	@CrossOrigin
	@GetMapping("/{id}")
	@ResponseBody
	EntityModel<User> one(@PathVariable Long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		return assembler.toModel(user);

	}

	@CrossOrigin
	@PostMapping
	@ResponseBody
	ResponseEntity<?> add(@RequestBody User newUser) throws URISyntaxException {
		
		User doexists = userRepository.findByeMail(newUser.geteMail()).orElse(null);
		
		EntityModel<User> entityModel;
		if(doexists!=null) {
			throw new UserExistsException(newUser.geteMail());

		} else {
			entityModel = assembler.toModel(userRepository.save(newUser));
			return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		}
	
	}

	@CrossOrigin
	@PutMapping(path = "/{id}")
	@ResponseBody
	ResponseEntity<?> update(@PathVariable Long id, @RequestBody User newUser) {
		User updatedUser = userRepository.findById(id).map(user -> {
			user.setName(newUser.getName());
			user.setCity(newUser.getCity());
			user.seteMail(newUser.geteMail());
			return userRepository.save(user);
		}).orElseGet(() -> {
			newUser.setId(id);
			return userRepository.save(newUser);
		});

		EntityModel<User> entityModel = assembler.toModel(updatedUser);

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseBody
	ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		try {
			userRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			throw new Exception();
		}

	}
	
	@CrossOrigin
	@PostMapping("/email")
	@ResponseBody
	EntityModel<User> oneByEMail(@RequestBody User search) {

		User user = userRepository.findByeMail(search.geteMail()).orElseThrow(() -> new UserNotFoundException(search.geteMail()));

		return EntityModel.of(user, linkTo(methodOn(UserController.class).oneByEMail(user)).withSelfRel(),
				linkTo(methodOn(UserController.class).all()).withRel("users"));
	}
	
	@CrossOrigin
	@GetMapping("/recent/{criterio}")
	@ResponseBody
	CollectionModel <EntityModel<User>> recent(@PathVariable String criterio) {

		
		List<EntityModel<User>> users = userRepository.recent(criterio).stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).recent(criterio)).withSelfRel());
		
	}

}

