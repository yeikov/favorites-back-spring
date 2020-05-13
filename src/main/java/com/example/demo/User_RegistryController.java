package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Controller
@RequestMapping(path=DemoApplication.backEndUrl + "/assessments")
public class User_RegistryController {
	
	@Autowired
	private User_RegistryRepository user_registryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RegistryRepository registryRepository;
	
	@Autowired
	private User_RegistryModelAssembler assembler;
	
	
	@CrossOrigin
	@GetMapping("/{id}")
	@ResponseBody
	EntityModel<User_Registry> one (@PathVariable Long id){
		User_Registry ur = user_registryRepository.findById(id).orElseThrow(() -> new User_RegistryNotFoundException(id));
		return assembler.toModel(ur);
	}
	
	@CrossOrigin
	@PostMapping
	@ResponseBody 
	ResponseEntity <?>  add (@RequestBody User_RegistryDto dto ) {
	
		User user = userRepository.findById(dto.getUserId()).orElse(null);
		Registry reg = registryRepository.findById(dto.getRegistryId()).orElse(null);
		int favo = dto.getFavorito();
		int recom = dto.getRecomendable();
		String notes = dto.getNotes();
		
		User_Registry newValoration = new User_Registry(user, reg, favo, recom, notes);
		
		EntityModel<User_Registry> entityModel = assembler.toModel(user_registryRepository.save(newValoration));
		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@ResponseBody 
	User_Registry update(@PathVariable Long id, @RequestBody User_Registry valoration ) {
		System.out.println("Update fn");
		
		User_Registry ur = user_registryRepository.findById(id).orElseThrow(() -> new User_RegistryNotFoundException(id));
		
		ur.setFavorito(valoration.getFavorito());
		ur.setRecomendable(valoration.getRecomendable());
		ur.setNotes(valoration.getNotes());
		
		user_registryRepository.save(ur);
		
		return ur;
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody void delete (
			@PathVariable Long id
			) {
		user_registryRepository.deleteById(id);
	
	}
	
	@CrossOrigin
	@GetMapping("/user/{id}")
	@ResponseBody 
	CollectionModel <EntityModel<User_Registry>> allByUser(@PathVariable Long id){
		
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		
		List<EntityModel<User_Registry>> valorations =  user_registryRepository.findAllByUser(user).stream().map(assembler::toModel).collect(Collectors.toList());
		
		return new CollectionModel<>(valorations, linkTo(methodOn(User_RegistryController.class).allByUser(id)).withSelfRel());
		
	}
	
}
