package com.favorites.back.entities.user;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import java.net.URISyntaxException;

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
import com.favorites.back.entities.assesment.AssessmentRepository;

@Controller
@RequestMapping(path = BackApplication.backEndUrl + "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AssessmentRepository assessmentRepository;

	@CrossOrigin // (origins="http://localhost:4200")
	@GetMapping
	public @ResponseBody Iterable<User> all() {
		return userRepository.findAll();
	}

	@CrossOrigin
	@GetMapping(path = "/{id}")
	public @ResponseBody <Opional> User one(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

	}

	@CrossOrigin
	@PostMapping
	public @ResponseBody User add(@RequestBody User newUser) throws URISyntaxException {

		User doexists = userRepository.findByeMail(newUser.geteMail()).orElse(null);

		if (doexists != null) {
			throw new UserExistsException(newUser.geteMail());
		} else {
			return userRepository.save(newUser);
		}

	}

	@CrossOrigin
	@PutMapping(path = "/{id}")
	public @ResponseBody User update(@PathVariable Long id, @RequestBody User newUser) {
		User updatedUser = userRepository.findById(id).map(user -> {
			user.setName(newUser.getName());
			user.setCity(newUser.getCity());
			user.seteMail(newUser.geteMail());
			return userRepository.save(user);
		}).orElseGet(() -> {
			newUser.setId(id);
			return userRepository.save(newUser);
		});

		return updatedUser;

	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseBody
	User delete(@PathVariable Long id) throws Exception {
		User deletedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		try {
			assessmentRepository.deleteAllInBatch(assessmentRepository.findAllByUser(deletedUser));
			userRepository.deleteById(id);
			return deletedUser;
		} catch (Exception e) {
			throw e;
		}

	}

	@CrossOrigin
	@PostMapping("/email")
	@ResponseBody
	User oneByEMail(@RequestBody User search) {

		return userRepository.findByeMail(search.geteMail())
				.orElseThrow(() -> new UserNotFoundException(search.geteMail()));

	}

	@CrossOrigin
	@GetMapping("/recent/{criterio}")
	@ResponseBody
	Iterable<User> recent(@PathVariable String criterio) {

		return userRepository.recent(criterio);

	}

}
