package com.favorites.back.entities.registry;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.favorites.back.CommonUtilities;
import com.favorites.back.BackApplication;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(path = BackApplication.backEndUrl + "/registries")
//@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@CrossOrigin(origins = "http://localhost:4200")
public class RegistryController {

	@Autowired
	private RegistryRepository registryRepository;

	@CrossOrigin // (origins="http://localhost:4200")
	@GetMapping
	@ResponseBody
	Iterable<Registry> all() {
		return registryRepository.findAll();
	}
	@GetMapping("/{id}")
	@ResponseBody
	Registry one(@PathVariable Long id) {

		return registryRepository.findById(id).orElseThrow(() -> new RegistryNotFoundException(id));
	}

	@CrossOrigin
	@PostMapping("/find")
	@ResponseBody
	Iterable<Registry> find(@RequestBody String title) {
		return registryRepository.findAllByTitleIgnoreCaseContaining(title);
	}

	@CrossOrigin
	@PostMapping
	@ResponseBody
	Registry add(@RequestBody RegistryDto newRegistry) throws URISyntaxException {

		Registry sondaRegistro = existsRegistry(newRegistry.getTitle(), newRegistry.getMedia(), newRegistry.getAuthor(),
				CommonUtilities.year2LocalDate(newRegistry.getYear()));

		if (sondaRegistro == null) {

			sondaRegistro = new Registry();
			sondaRegistro.setTitle(newRegistry.getTitle());
			sondaRegistro.setAuthor(newRegistry.getAuthor());
			sondaRegistro.setMedia(newRegistry.getMedia());
			sondaRegistro.setProductionDate(CommonUtilities.year2LocalDate(newRegistry.getYear()));

			return registryRepository.save(sondaRegistro);

		} else {

			throw new RegistryExistsException(sondaRegistro);

		}

	}

	@CrossOrigin
	@PutMapping("/{id}")
	@ResponseBody
	Registry update(@PathVariable Long id, @RequestBody Registry newRegistry) {

		Registry updateRegistro = registryRepository.findById(id).map(registry -> {
			registry.setTitle(newRegistry.getTitle());
			registry.setProductionDate(newRegistry.getProductionDate());
			registry.setAuthor(newRegistry.getAuthor());
			registry.setMedia(newRegistry.getMedia());
			return registryRepository.save(registry);
		}).orElseGet(() -> {
			newRegistry.setId(id);
			return registryRepository.save(newRegistry);
		});
		if (updateRegistro == null) {
			// entityModel = assembler.toModel(registryRepository.save(newRegistry));
			new RegistryNotFoundException(null);
		}
		return registryRepository.save(newRegistry);

	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseBody
	Long delete(@PathVariable Long id) {
		registryRepository.deleteById(id);
		return id;
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

	@CrossOrigin(origins = "http://localhost:4000")
	@GetMapping("/topFavorite/{media}")
	@ResponseBody
	Iterable<Registry> findTopFavoritesByMedia(@PathVariable String media) {

		return registryRepository.findTopFavoriteByMedia(media);

	}

	@CrossOrigin
	@GetMapping("/topRecommend/{media}")
	@ResponseBody
	Iterable<Registry> findTopRecommendByMedia(@PathVariable String media) {

		return registryRepository.findTopRecommendByMedia(media);

	}

}
