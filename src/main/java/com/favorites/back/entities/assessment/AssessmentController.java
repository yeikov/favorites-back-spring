package com.favorites.back.entities.assessment;

import java.net.URI;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<Assessment> one(@PathVariable Long id) {

		Optional<Assessment> assessmentOptional = viewer_registryRepository.findById(id);

		if (assessmentOptional.isPresent()) {
			return ResponseEntity.ok(assessmentOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	private void updateRegistryStatistics(Registry registryInitial,
			int addOrSubstractMentionValue,
			int addOrSubstractFavoriteValue,
			int addOrSubstractRecommendValue) {
		Registry registryUpdated =  new Registry();
		registryUpdated.setId(registryInitial.getId());
		registryUpdated.setTitle(registryInitial.getTitle());
		registryUpdated.setAuthor(registryInitial.getAuthor());
		registryUpdated.setProductionDate(registryInitial.getProductionDate());
		registryUpdated.setMedia(registryInitial.getMedia());

		registryUpdated.setMentions(registryInitial.getMentions() + addOrSubstractMentionValue);
		registryUpdated
				.setFavoriteSum(
						((registryInitial.getFavoriteSum() * registryInitial.getMentions()
								+ addOrSubstractFavoriteValue))
								/ registryUpdated.getMentions());
		registryUpdated
				.setRecommendSum(
						((registryInitial.getRecommendSum() * registryInitial.getMentions()
								+ addOrSubstractRecommendValue))
								/ registryUpdated.getMentions());

		registryRepository.save(registryUpdated);

	}

	@PostMapping
	public ResponseEntity<Assessment> save(@RequestBody AssessmentDto dto, UriComponentsBuilder ucb) {

		Viewer viewer = viewerRepository.findById(dto.getViewerId())
				.orElseThrow(() -> new ViewerNotFoundException(dto.getViewerId())); // .orElse(null);
		Registry registry = registryRepository.findById(dto.getRegistryId())
				.orElseThrow(() -> new RegistryNotFoundException(dto.getRegistryId()));
		int favorite = dto.getFavorite();
		int recommend = dto.getRecommend();
		String notes = dto.getNotes();

		Assessment newAssessment = new Assessment(viewer, registry, favorite, recommend, notes);

		try {
			Assessment _newAssessment = viewer_registryRepository.save(newAssessment);
			URI locationOfNewAssessment = ucb.path("/favorites/assessments/{id}").buildAndExpand(_newAssessment.getId())
					.toUri();
			// STATISTICS STUF
			updateRegistryStatistics(registry, +1, favorite, recommend);
			return ResponseEntity.created(locationOfNewAssessment).body(_newAssessment);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@PutMapping("/{id}")
	ResponseEntity<Assessment> update(@PathVariable Long id, @RequestBody Assessment valoration) {

		Assessment _assessment = viewer_registryRepository.findById(id)
				.orElseThrow(() -> new AssessmentNotFoundException(id));
		Assessment memo_assessment = new Assessment();

		memo_assessment.setFavorite(_assessment.getFavorite());
		memo_assessment.setRecommend(_assessment.getRecommend());

		_assessment.setFavorite(valoration.getFavorite());
		_assessment.setRecommend(valoration.getRecommend());
		_assessment.setNotes(valoration.getNotes());

		try {
			Assessment newAssessment = viewer_registryRepository.save(_assessment);

			// STATISTICS STUF
			updateRegistryStatistics(_assessment.getRegistry(), 0,
					_assessment.getFavorite() - memo_assessment.getFavorite(),
					_assessment.getRecommend() - memo_assessment.getRecommend());
			return ResponseEntity.ok(newAssessment);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable Long id) throws Exception {

		Assessment assessmentToDelete = viewer_registryRepository.findById(id)
				.orElseThrow(() -> new AssessmentNotFoundException(id));

		try {
			viewer_registryRepository.deleteById(id);

			// STATISTICS STUF
			updateRegistryStatistics(assessmentToDelete.getRegistry(),
					-1,
					assessmentToDelete.getFavorite() * -1,
					assessmentToDelete.getRecommend() * -1);
			return ResponseEntity.ok().body(assessmentToDelete.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping("/viewer/{id}")
	public ResponseEntity<Page<Assessment>> allByViewer(@PathVariable Long id, Pageable pageable) {

		try {
			Viewer viewer = viewerRepository.findById(id).orElseThrow(() -> new ViewerNotFoundException(id));

			Page<Assessment> page = viewer_registryRepository.findAllByViewer(
					viewer,
					PageRequest.of(pageable.getPageNumber(),
							pageable.getPageSize(),
							pageable.getSortOr(Sort.by(Sort.Direction.DESC, "registeredAt"))));
			return ResponseEntity.ok(page);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping("registry/{id}")
	public ResponseEntity<Page<Assessment>> allByRegistry(@PathVariable Long id, Pageable pageable) {

		try {
			Registry registry = registryRepository.findById(id).orElseThrow(() -> new RegistryNotFoundException(id));

			Page<Assessment> page = viewer_registryRepository.findAllByRegistry(
					registry,
					PageRequest.of(pageable.getPageNumber(),
							pageable.getPageSize(),
							pageable.getSortOr(Sort.by(Sort.Direction.DESC, "registeredAt"))));
			return ResponseEntity.ok(page);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping("media/{media}")
	public ResponseEntity<List<Assessment>> allByMedia(@PathVariable String media) {

		try {
			List<Assessment> assessmentsMedia = viewer_registryRepository.findM(media);
			return ResponseEntity.ok(assessmentsMedia);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping("viewer/{id}/{media}")
	public ResponseEntity<List<Assessment>> allViewerByMedia(@PathVariable Long id, @PathVariable String media) {

		try {
			List<Assessment> assessmentsViewerMedia = viewer_registryRepository.findUM(id, media);
			return ResponseEntity.ok(assessmentsViewerMedia);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

}
