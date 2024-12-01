package com.favorites.back.entities.assessment;

import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.favorites.back.BackApplication;
import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.registry.RegistryNotFoundException;
import com.favorites.back.entities.registry.RegistryRepository;
import com.favorites.back.entities.viewer.Viewer;
import com.favorites.back.entities.viewer.ViewerNotFoundException;
import com.favorites.back.entities.viewer.ViewerRepository;

@RestController
@RequestMapping(path = BackApplication.backEndUrl + "/assessments")
public class AssessmentController {

	@Autowired
	private AssessmentRepository viewer_registryRepository;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private RegistryRepository registryRepository;

	@CrossOrigin
	@GetMapping("/{id}")
	public @ResponseBody Assessment one(@PathVariable Long id) {
		return viewer_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));

	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Assessment> save(@RequestBody AssessmentDto dto, UriComponentsBuilder ucb, Principal principal) {

		Viewer viewer = viewerRepository.findById(dto.getViewerId())
				.orElseThrow(() -> new ViewerNotFoundException(dto.getViewerId())); // .orElse(null);
		Registry reg = registryRepository.findById(dto.getRegistryId())
				.orElseThrow(() -> new RegistryNotFoundException(dto.getRegistryId()));
		int favo = dto.getFavorite();
		int recom = dto.getRecommend();
		String notes = dto.getNotes();

		Assessment newAssessment = new Assessment(viewer, reg, favo, recom, notes);

		try {

			Assessment _newAssessment = viewer_registryRepository.save(newAssessment);
			URI location = ucb.path("favorites/assessment/{id}").buildAndExpand(_newAssessment.getId()).toUri();
			return ResponseEntity.created(location).body(_newAssessment);

		} catch (Exception e) {
			// throw e;
			return ResponseEntity.badRequest().build();
		}

	}

	@CrossOrigin
	@PutMapping("/{id}")
	public @ResponseBody Assessment update(@PathVariable Long id, @RequestBody Assessment valoration) {

		Assessment ur = viewer_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));

		ur.setFavorite(valoration.getFavorite());
		ur.setRecommend(valoration.getRecommend());
		ur.setNotes(valoration.getNotes());

		return viewer_registryRepository.save(ur);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public @ResponseBody Long delete(@PathVariable Long id) {
		viewer_registryRepository.deleteById(id);

		return id;
	}

	@CrossOrigin
	@GetMapping("/viewer/{id}")
	public @ResponseBody Iterable<Assessment> allByViewer(@PathVariable Long id) {

		Viewer viewer = viewerRepository.findById(id).orElseThrow(() -> new ViewerNotFoundException(id));

		return viewer_registryRepository.findAllByViewer(viewer);

	}

	
	@CrossOrigin
	@GetMapping("registry/{id}")
	public @ResponseBody Iterable<Assessment> allByRegistry(@PathVariable Long id) {

		Registry registry = registryRepository.findById(id).orElseThrow(() -> new RegistryNotFoundException(id));

		return viewer_registryRepository.findAllByRegistry(registry);

	}

	@CrossOrigin
	@GetMapping("media/{media}")
	public @ResponseBody Iterable<Assessment> allByMedia(@PathVariable String media) {

		return viewer_registryRepository.findM(media);

	}

	@CrossOrigin
	@GetMapping("viewer/{id}/{media}")
	public @ResponseBody Iterable<Assessment> allViewerByMedia(@PathVariable Long id, @PathVariable String media) {

		return viewer_registryRepository.findUM(id, media);

	}


}
