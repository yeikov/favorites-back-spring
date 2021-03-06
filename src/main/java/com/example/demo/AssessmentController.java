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
@RequestMapping(path = DemoApplication.backEndUrl + "/assessments")
public class AssessmentController {

	@Autowired
	private AssessmentRepository user_registryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegistryRepository registryRepository;

	@Autowired
	private AssessmentModelAssembler assembler;

	@CrossOrigin
	@GetMapping("/{id}")
	@ResponseBody
	EntityModel<Assessment> one(@PathVariable Long id) {
		Assessment ur = user_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));
		return assembler.toModel(ur);
	}

	@CrossOrigin
	@PostMapping
	@ResponseBody
	ResponseEntity<?> add(@RequestBody AssessmentDto dto) {

		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new UserNotFoundException(dto.getUserId())); // .orElse(null);
		Registry reg = registryRepository.findById(dto.getRegistryId())
				.orElseThrow(() -> new RegistryNotFoundException(dto.getRegistryId()));
		int favo = dto.getFavorite();
		int recom = dto.getRecommend();
		String notes = dto.getNotes();

		Assessment newValoration = new Assessment(user, reg, favo, recom, notes);

		try {

			EntityModel<Assessment> entityModel = assembler.toModel(user_registryRepository.save(newValoration));

			return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
		} catch (Exception e) {
			// throw e;
			throw new AssessmentExistsException();
		}

	}

	@CrossOrigin
	@PutMapping("/{id}")
	@ResponseBody
	ResponseEntity<?> update(@PathVariable Long id, @RequestBody Assessment valoration) {

		Assessment ur = user_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));

		ur.setFavorite(valoration.getFavorite());
		ur.setRecommend(valoration.getRecommend());
		ur.setNotes(valoration.getNotes());

		EntityModel<Assessment> entityModel = assembler.toModel(user_registryRepository.save(ur));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	ResponseEntity<?> delete(@PathVariable Long id) {
		user_registryRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@GetMapping("/user/{id}")
	@ResponseBody
	CollectionModel<EntityModel<Assessment>> allByUser(@PathVariable Long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		List<EntityModel<Assessment>> assessments = user_registryRepository.findAllByUser(user).stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return new CollectionModel<>(assessments,
				linkTo(methodOn(AssessmentController.class).allByUser(id)).withSelfRel());

	}

	@CrossOrigin
	@GetMapping("media/{media}")
	@ResponseBody
	CollectionModel<EntityModel<Assessment>> allByMedia(@PathVariable String media) {

		List<EntityModel<Assessment>> assessments = user_registryRepository.findM(media).stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return new CollectionModel<>(assessments,
				linkTo(methodOn(AssessmentController.class).allByMedia(media)).withSelfRel());
	}
	
	@CrossOrigin
	@GetMapping("user/{id}/{media}")
	@ResponseBody
	CollectionModel<EntityModel<Assessment>> allUserByMedia(@PathVariable Long id,@PathVariable String media) {

		List<EntityModel<Assessment>> assessments = user_registryRepository.findUM(id, media).stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return new CollectionModel<>(assessments,
				linkTo(methodOn(AssessmentController.class).allUserByMedia(id, media)).withSelfRel());
	}
	@CrossOrigin
	@GetMapping("registry/{id}")
	@ResponseBody
	CollectionModel<EntityModel<Assessment>> allByRegistry(@PathVariable Long id) {
		
		Registry registry = registryRepository.findById(id).orElseThrow(() -> new RegistryNotFoundException(id));

		List<EntityModel<Assessment>> assessments = user_registryRepository.findAllByRegistry(registry).stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return new CollectionModel<>(assessments,
				linkTo(methodOn(AssessmentController.class).allByRegistry(id)).withSelfRel());
		
	}

}
