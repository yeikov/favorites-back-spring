package com.favorites.back.entities.assessment;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = BackApplication.backEndUrl + "/assessments")
public class AssessmentController {

	@Autowired
	private AssessmentRepository viewer_registryRepository;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private RegistryRepository registryRepository;

	
	@GetMapping("/{id}")
	public ResponseEntity <Assessment> one(@PathVariable Long id) {
		
		Optional<Assessment> assessmentOptional = viewer_registryRepository.findById(id);

		if (assessmentOptional.isPresent()) {
			return ResponseEntity.ok(assessmentOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	
	@PostMapping
	public ResponseEntity <Assessment> save(@RequestBody AssessmentDto dto, UriComponentsBuilder ucb) {

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
			URI locationOfNewAssessment = ucb.path("/favorites/assessments/{id}").buildAndExpand(_newAssessment.getId()).toUri();
			return ResponseEntity.created(locationOfNewAssessment).body(_newAssessment);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	
	@PutMapping("/{id}")
	ResponseEntity <Assessment> update(@PathVariable Long id, @RequestBody Assessment valoration) {

		Assessment _assessment = viewer_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));

		_assessment.setFavorite(valoration.getFavorite());
		_assessment.setRecommend(valoration.getRecommend());
		_assessment.setNotes(valoration.getNotes());

		try {

			Assessment newAssessment = viewer_registryRepository.save(_assessment);
			return ResponseEntity.ok(newAssessment);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity <Long> delete(@PathVariable Long id) throws Exception{

		Assessment deletedAssessment = viewer_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));

		try {
			viewer_registryRepository.deleteById(id);
			return ResponseEntity.ok().body(deletedAssessment.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
	}

	
	@GetMapping("/viewer/{id}")
	public ResponseEntity <List<Assessment>> allByViewer(@PathVariable Long id) {

		try {
			Viewer viewer = viewerRepository.findById(id).orElseThrow(() -> new ViewerNotFoundException(id));
			List<Assessment> assessmentsViewer = viewer_registryRepository.findAllByViewer(viewer);
			return ResponseEntity.ok(assessmentsViewer);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	
	@GetMapping("registry/{id}")
	public ResponseEntity <List<Assessment>> allByRegistry(@PathVariable Long id) {

		try {
			Registry registry = registryRepository.findById(id).orElseThrow(() -> new RegistryNotFoundException(id));
			List<Assessment> assessmentsViewer = viewer_registryRepository.findAllByRegistry(registry);
			return ResponseEntity.ok(assessmentsViewer);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	
	@GetMapping("media/{media}")
	public ResponseEntity <List<Assessment>> allByMedia(@PathVariable String media) {

		try {
			List<Assessment> assessmentsMedia = viewer_registryRepository.findM(media);
			return ResponseEntity.ok(assessmentsMedia);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	
	@GetMapping("viewer/{id}/{media}")
	public ResponseEntity <List<Assessment>> allViewerByMedia(@PathVariable Long id, @PathVariable String media) {

		try {
			List<Assessment> assessmentsViewerMedia = viewer_registryRepository.findUM(id, media);
			return ResponseEntity.ok(assessmentsViewerMedia);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

}
