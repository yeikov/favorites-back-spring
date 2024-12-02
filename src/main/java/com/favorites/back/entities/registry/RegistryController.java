package com.favorites.back.entities.registry;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.favorites.back.CommonUtilities;
import com.favorites.back.entities.viewer.Viewer;
import com.favorites.back.entities.viewer.ViewerNotFoundException;
import com.favorites.back.BackApplication;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = BackApplication.backEndUrl + "/registries")
// @CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@CrossOrigin(origins = "http://localhost:4200")
public class RegistryController {

	@Autowired
	private RegistryRepository registryRepository;


	@GetMapping
	private ResponseEntity<List<Registry>> findAll(Pageable pageable) {
		Page<Registry> page = registryRepository.findAll(
				PageRequest.of(pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.DESC, "title"))));

		return ResponseEntity.ok(page.getContent());

	}

	@GetMapping("/{id}")
	ResponseEntity<Registry> one(@PathVariable Long id) {

		Optional<Registry> registryOptional = registryRepository.findById(id);

		if (registryOptional.isPresent()) {
			return ResponseEntity.ok(registryOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	
	@SuppressWarnings("unchecked")
	@PostMapping("/find")
	ResponseEntity <List<Registry>> find(@RequestBody String title, Pageable pageable) {
		//return registryRepository.findAllByTitleIgnoreCaseContaining(title);
		Page<Registry> page = (Page<Registry>) registryRepository.findAllByTitleIgnoreCaseContaining(title,
				PageRequest.of(pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.DESC, "productionDate"))));

		return ResponseEntity.ok(page.getContent());
	}

	
	@PostMapping
	ResponseEntity<Registry> save (@RequestBody RegistryDto newRegistry, UriComponentsBuilder ucb) throws URISyntaxException {

		Registry sondaRegistro = existsRegistry(newRegistry.getTitle(), 
		newRegistry.getMedia(), newRegistry.getAuthor(),
				CommonUtilities.year2LocalDate(newRegistry.getYear()));

		if (sondaRegistro == null) {

			sondaRegistro = new Registry();
			sondaRegistro.setTitle(newRegistry.getTitle());
			sondaRegistro.setAuthor(newRegistry.getAuthor());
			sondaRegistro.setMedia(newRegistry.getMedia());
			sondaRegistro.setProductionDate(CommonUtilities.year2LocalDate(newRegistry.getYear()));

			try {
				
				Registry _newRegistry = registryRepository.save(sondaRegistro);
				URI locationOfNewRegistry = ucb.path("/favorites/registries/{id}").buildAndExpand(_newRegistry.getId()).toUri();
				
				return ResponseEntity.created(locationOfNewRegistry).body(_newRegistry);
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}

		} else {

			throw new RegistryExistsException(sondaRegistro);

		}

	}

	
	@PutMapping("/{id}")
	ResponseEntity<Registry> update(@PathVariable Long id, @RequestBody Registry newRegistry) {
	
		try {
			Registry _newRegistry = new Registry(
				newRegistry.getTitle(), 
				newRegistry.getMedia(),
				newRegistry.getAuthor(),
				newRegistry.getProductionDate().toString()
			);

			registryRepository.save(_newRegistry);
			return ResponseEntity.ok(_newRegistry);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	
	@DeleteMapping("/{id}")
	ResponseEntity<Registry> delete(@PathVariable Long id) throws Exception {
		Registry deletedRegistry = registryRepository.findById(id).orElseThrow(() -> new RegistryNotFoundException(id));

		try {
			registryRepository.deleteById(id);
			return ResponseEntity.ok(deletedRegistry);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	Registry existsRegistry(
			String title,
			String media,
			String author,
			LocalDate year) {
		Registry response = null;

		List<Registry> request = registryRepository.findAllByTitleAndMediaAndAuthorAndProductionDate(title, media,
				author, year);
		if (request.size() != 0) {
			response = request.get(0);
		}

		return response;
	}



	@GetMapping("/topFavorite/{media}")
	ResponseEntity
	<List<Registry>> findTopFavoritesByMedia(@PathVariable String media) {

		List<Registry> topFavorites =  registryRepository.findTopFavoriteByMedia(media);

		return ResponseEntity.ok(topFavorites);


	}

	
	@GetMapping("/topRecommend/{media}")
	ResponseEntity
	<List<Registry>> findTopRecommendByMedia(@PathVariable String media) {

		List<Registry> topRecommend = registryRepository.findTopRecommendByMedia(media);
		return ResponseEntity.ok(topRecommend);
		

	}

}
