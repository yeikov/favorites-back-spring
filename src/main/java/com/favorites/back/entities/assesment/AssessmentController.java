package com.favorites.back.entities.assesment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.favorites.back.BackApplication;
import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.registry.RegistryNotFoundException;
import com.favorites.back.entities.registry.RegistryRepository;
import com.favorites.back.entities.user.User;
import com.favorites.back.entities.user.UserNotFoundException;
import com.favorites.back.entities.user.UserRepository;

@Controller
@RequestMapping(path = BackApplication.backEndUrl + "/assessments")
public class AssessmentController {

	@Autowired
	private AssessmentRepository user_registryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegistryRepository registryRepository;

	@CrossOrigin
	@GetMapping("/{id}")
	public @ResponseBody Assessment one(@PathVariable Long id) {
		return user_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));

	}

	@CrossOrigin
	@PostMapping
	public @ResponseBody Assessment add(@RequestBody AssessmentDto dto) {

		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new UserNotFoundException(dto.getUserId())); // .orElse(null);
		Registry reg = registryRepository.findById(dto.getRegistryId())
				.orElseThrow(() -> new RegistryNotFoundException(dto.getRegistryId()));
		int favo = dto.getFavorite();
		int recom = dto.getRecommend();
		String notes = dto.getNotes();

		Assessment newValoration = new Assessment(user, reg, favo, recom, notes);

		try {

			return user_registryRepository.save(newValoration);

		} catch (Exception e) {
			// throw e;
			throw new AssessmentExistsException();
		}

	}

	@CrossOrigin
	@PutMapping("/{id}")
	public @ResponseBody Assessment update(@PathVariable Long id, @RequestBody Assessment valoration) {

		Assessment ur = user_registryRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException(id));

		ur.setFavorite(valoration.getFavorite());
		ur.setRecommend(valoration.getRecommend());
		ur.setNotes(valoration.getNotes());

		return user_registryRepository.save(ur);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public @ResponseBody Long delete(@PathVariable Long id) {
		user_registryRepository.deleteById(id);

		return id;
	}

	@CrossOrigin
	@GetMapping("/user/{id}")
	public @ResponseBody Iterable<Assessment> allByUser(@PathVariable Long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		return user_registryRepository.findAllByUser(user);

	}

	
	@CrossOrigin
	@GetMapping("registry/{id}")
	public @ResponseBody Iterable<Assessment> allByRegistry(@PathVariable Long id) {

		Registry registry = registryRepository.findById(id).orElseThrow(() -> new RegistryNotFoundException(id));

		return user_registryRepository.findAllByRegistry(registry);

	}

	@CrossOrigin
	@GetMapping("media/{media}")
	public @ResponseBody Iterable<Assessment> allByMedia(@PathVariable String media) {

		return user_registryRepository.findM(media);

	}

	@CrossOrigin
	@GetMapping("user/{id}/{media}")
	public @ResponseBody Iterable<Assessment> allUserByMedia(@PathVariable Long id, @PathVariable String media) {

		return user_registryRepository.findUM(id, media);

	}


}
