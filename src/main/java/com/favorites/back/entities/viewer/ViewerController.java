package com.favorites.back.entities.viewer;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.favorites.back.BackApplication;
import com.favorites.back.Media;
import com.favorites.back.entities.assesment.AssessmentRepository;

@RestController
@RequestMapping(path = BackApplication.backEndUrl + "/viewers")
public class ViewerController {

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private AssessmentRepository assessmentRepository;

	@CrossOrigin // (origins="http://localhost:4200")
	@GetMapping
	public @ResponseBody Iterable<Viewer> all() {
		return viewerRepository.findAll();
	}

	@CrossOrigin
	@GetMapping(path = "/{id}")
	public @ResponseBody <Opional> Viewer one(@PathVariable Long id) {
		return viewerRepository.findById(id).orElseThrow(() -> new ViewerNotFoundException(id));

	}

	@CrossOrigin
	@PostMapping
	public @ResponseBody Viewer add(@RequestBody Viewer newViewer) throws Exception {

		Viewer doexists = viewerRepository.findByeMail(newViewer.geteMail()).orElse(null);

		if (doexists != null) {
			//throw new ViewerExistsException(newViewer.geteMail());
			newViewer.setId(-1L); // ...er.setId(-1L); workaround Todo: review exceptions
			return newViewer;
		} else {
			return viewerRepository.save(newViewer);
		}

	}

	@CrossOrigin
	@PutMapping(path = "/{id}")
	public @ResponseBody Viewer update(@PathVariable Long id, @RequestBody Viewer newViewer) {
		Viewer updatedViewer = viewerRepository.findById(id).map(viewer -> {
			viewer.setName(newViewer.getName());
			viewer.setCity(newViewer.getCity());
			viewer.seteMail(newViewer.geteMail());
			return viewerRepository.save(viewer);
		}).orElseGet(() -> {
			newViewer.setId(id);
			return viewerRepository.save(newViewer);
		});

		return updatedViewer;

	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseBody
	Viewer delete(@PathVariable Long id) throws Exception {
		Viewer deletedViewer = viewerRepository.findById(id).orElseThrow(() -> new ViewerNotFoundException(id));

		try {
			assessmentRepository.deleteAllInBatch(assessmentRepository.findAllByViewer(deletedViewer));
			viewerRepository.deleteById(id);
			return deletedViewer;
		} catch (Exception e) {
			throw e;
		}

	}

	@CrossOrigin
	@PostMapping("/email")
	@ResponseBody
	Viewer oneByEMail(@RequestBody Viewer search) {

		return viewerRepository.findByeMail(search.geteMail())
				.orElseThrow(() -> new ViewerNotFoundException(search.geteMail()));

	}

	@CrossOrigin
	@GetMapping("/recent/{media}")
	@ResponseBody
	Iterable<Viewer> recent(@PathVariable String media) {

		Media _media = Media.ALL;
		try {
			_media = Media.valueOf(media.toUpperCase());
		} catch (Exception e) {
			// TODO: handle exception
		}

		return viewerRepository.recent(_media);

	}

}
