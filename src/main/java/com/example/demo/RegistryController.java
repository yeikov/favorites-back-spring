package com.example.demo;


import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



@Controller
@RequestMapping(path=DemoApplication.backEndUrl + "/registries")
public class RegistryController {

	@Autowired
	private RegistryRepository registryRepository;
	
	@Autowired
	private RegistryModelAssembler assembler;
	
	@CrossOrigin //(origins="http://localhost:4200")
	@GetMapping
	@ResponseBody 
	CollectionModel<EntityModel<Registry>> all() {
		
		List<EntityModel<Registry>> registries = registryRepository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		
		return new CollectionModel<>(registries, linkTo(methodOn(RegistryController.class).all()).withSelfRel());
	}
	
	@CrossOrigin 
	@GetMapping("/{id}")
	@ResponseBody 
	EntityModel<Registry> one(@PathVariable Long id) {
		
		Registry registry = registryRepository.findById(id).orElseThrow(()-> new RegistryNotFoundException(id));
		
		return assembler.toModel(registry);
	}
	

	@CrossOrigin
	@PostMapping
	@ResponseBody 
	ResponseEntity<?> add(@RequestBody Registry newRegistry) throws URISyntaxException {
		
		Registry sondaRegistro = existsRegistry(newRegistry.getTitle(), newRegistry.getMedia(), newRegistry.getAutor(), newRegistry.getProductionDate());

		EntityModel<Registry> entityModel;
		
		if (sondaRegistro == null) {
			entityModel = assembler.toModel(registryRepository.save(newRegistry));

		} else { 
			entityModel = null; 
			new RegistryNotFoundException(null);
		}
		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);		
		
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@ResponseBody 
	ResponseEntity<?> update(@PathVariable Long id, @RequestBody Registry newRegistry) {
		
		Registry updateRegistro = registryRepository.findById(id).map(registry -> {
			registry.setTitle(newRegistry.getTitle());	
			registry.setProductionDate(newRegistry.getProductionDate());
			registry.setAutor(newRegistry.getAutor());
			registry.setMedia(newRegistry.getMedia());
			return registryRepository.save(registry);
		}).orElseGet(() -> {
			newRegistry.setId(id);
			return registryRepository.save(newRegistry);
		});
		if (updateRegistro == null) {
			//entityModel = assembler.toModel(registryRepository.save(newRegistry));
			new RegistryNotFoundException(null);
		} 
		EntityModel<Registry> entityModel = assembler.toModel(registryRepository.save(newRegistry));
		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);		
		
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseBody
	ResponseEntity<?> deleteUser(@PathVariable Long id) {
		registryRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	Registry existsRegistry (
			String title, 
			String media, 
			String autor, 
			LocalDateTime year) 
	{
		Registry response = null;
		
		List <Registry> request = registryRepository.findAllByTitleAndMediaAndAutorAndProductionDate(title, media, autor, year);
		if (request.size()!=0) {
			response = request.get(0);
		}

		return response;
	}

}
