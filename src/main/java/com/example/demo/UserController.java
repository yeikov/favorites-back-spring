package com.example.demo;

import com.example.demo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.lang.model.type.ErrorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;


@Controller
@RequestMapping(path = DemoApplication.backOfficeUrl + "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserModelAssembler assembler;
	
	// http://127.0.0.1:8080/backoffice/users
	@CrossOrigin // (origins="http://localhost:4200")
	@GetMapping
	@ResponseBody CollectionModel<EntityModel<User>> all() {

		  List<EntityModel<User>> users = userRepository.findAll().stream()
		    .map(assembler::toModel)
		    .collect(Collectors.toList());

		  return new CollectionModel<>(users,
		    linkTo(methodOn(UserController.class).all()).withSelfRel());
		}

	// http://127.0.0.1:8080/backoffice/users/1
	@CrossOrigin // (origins="http://localhost:4200")
	@GetMapping("/{id}")
	@ResponseBody EntityModel <User> one(@PathVariable Long id) {
		
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));		
		
		return assembler.toModel(user);
				
	}

	// http://127.0.0.1:8080/backoffice/users/byName?name=john
	@CrossOrigin // (origins="http://localhost:4200")
	@GetMapping(path = "/byName")
	@ResponseBody EntityModel <User> byName(@RequestParam String name) {

		User user = userRepository.findByName(name).orElseThrow(() -> new UserNotFoundException(name));
		
		return new EntityModel<> (user,
				linkTo(methodOn(UserController.class).byName(name)).withSelfRel(),
			    linkTo(methodOn(UserController.class).all()).withRel("users"));
	}

	// curl -v -X POST localhost:8080/backoffice/users -H 'Content-Type:application/json' -d '{"name": "john", "city":"London", "eMail": "johnmail"}'
	@CrossOrigin
	@PostMapping 
	@ResponseBody ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {

		EntityModel <User> entityModel = assembler.toModel(userRepository.save(newUser));
		
		return ResponseEntity
			    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			    .body(entityModel);
	}

	//$ curl -v -X PUT localhost:8080/backoffice/user/3 -H 'Content-Type:application/json' -d '{"name": "johan", "city": "Bruselas", "eMail": "johanBruselas"}'
	@CrossOrigin
	@PutMapping(path = "/{id}") 
	@ResponseBody ResponseEntity<?> updateUser(
			@PathVariable Long id, 
			@RequestBody User newUser) {
		User updatedUser =  userRepository.findById(id).map(user -> {
			user.setName(newUser.getName());
			user.setCity(newUser.getCity());
			user.seteMail(newUser.geteMail());
			return userRepository.save(user);
		}).orElseGet(() -> {
			newUser.setId(id);
			return userRepository.save(newUser);
		});
		
		EntityModel <User> entityModel = assembler.toModel(updatedUser);
		
		return ResponseEntity
			    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			    .body(entityModel);
	}

	//$ curl -X DELETE localhost:8080/backoffice/user/1
	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseBody ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}