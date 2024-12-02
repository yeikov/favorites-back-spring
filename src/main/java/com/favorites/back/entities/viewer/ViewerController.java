package com.favorites.back.entities.viewer;


import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.favorites.back.BackApplication;
import com.favorites.back.Media;
import com.favorites.back.entities.assessment.AssessmentRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = BackApplication.backEndUrl + "/viewers")
public class ViewerController {

	private final ViewerRepository viewerRepository;

	public ViewerController(ViewerRepository viewerRepository){
		this.viewerRepository = viewerRepository;
	}
	
	@Autowired
	private AssessmentRepository assessmentRepository;

	@GetMapping
	private ResponseEntity<List<Viewer>> findAll(Pageable pageable) {
		Page<Viewer> page = viewerRepository.findAll(
				PageRequest.of(
						pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.DESC, "id"))));
		return ResponseEntity.ok(page.getContent());
	}

	@CrossOrigin
	@GetMapping(path = "/{id}")
	private ResponseEntity<Viewer> one(@PathVariable Long id) {
		Optional<Viewer> viewerOptional = viewerRepository.findById(id);

		if (viewerOptional.isPresent()) {
			return ResponseEntity.ok(viewerOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin
	@PostMapping private ResponseEntity<Viewer> save(@RequestBody Viewer newViewer, UriComponentsBuilder ucb) throws Exception {

		try {
			Viewer _newViewer = viewerRepository.save(newViewer);
			URI locationOfNewViewer = ucb.path("/favorites/viewers/{id}").buildAndExpand(_newViewer.getId()).toUri();
			return ResponseEntity.created(locationOfNewViewer).body(_newViewer);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@CrossOrigin
	@PutMapping(path = "/{id}")
	public ResponseEntity<Viewer> update(@PathVariable Long id, @RequestBody Viewer newViewer) {

		try {
			Viewer _newViewer = new Viewer(newViewer.getName(), newViewer.geteMail(), newViewer.getCity(),
					newViewer.getBirth());
			viewerRepository.save(_newViewer);
			return ResponseEntity.ok(_newViewer);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	ResponseEntity<Viewer> delete(@PathVariable Long id) throws Exception {
		Viewer deletedViewer = viewerRepository.findById(id).orElseThrow(() -> new ViewerNotFoundException(id));

		try {
			assessmentRepository.deleteAllInBatch(assessmentRepository.findAllByViewer(deletedViewer));
			viewerRepository.deleteById(id);
			return ResponseEntity.ok(deletedViewer);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@CrossOrigin
	@PostMapping("/email")
	ResponseEntity<Viewer> oneByEMail(@RequestBody Viewer search) {

		try {
			Optional<Viewer> findViewer = viewerRepository.findByeMail(search.geteMail());
			return ResponseEntity.ok(findViewer.get());

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@CrossOrigin
	@GetMapping("/recent_notWorking/{media}")
	ResponseEntity<List<Viewer>> recent_notWorking(@PathVariable String media) {

		Media _media = Media.ALL;
		try {
			_media = Media.valueOf(media.toUpperCase());
		} catch (Exception e) {
			throw (e);
		}

		try {
			List<Viewer> _recent = viewerRepository.recent(_media);
			return ResponseEntity.ok(_recent);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@CrossOrigin
	@GetMapping("/recent/{media}")
	ResponseEntity<List<Viewer>> recent(@PathVariable String media, Pageable pageable) {
		Page<Viewer> page = viewerRepository.findAll(
				PageRequest.of(
						pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.DESC, "id"))));
		return ResponseEntity.ok(page.getContent());
	}

}
