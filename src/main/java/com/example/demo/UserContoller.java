package com.example.demo;

import com.example.demo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@Controller
@RequestMapping(path=DemoApplication.backOfficeUrl + "/user")
public class UserContoller {
	
	@Autowired
	private UserRepository userRepository;

	//curl localhost:8080/backoffice/user/add -d name=john -d city=barcelona
	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@RequestParam String name
	      , @RequestParam String city) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    User u = new User();
	    u.setName(name);
	    u.setCity(city);
	    
	    userRepository.save(u);
	    return "Saved";
	}
	
	//http://127.0.0.1:8080/backoffice/user/all
	@CrossOrigin //(origins="http://localhost:4200")
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	
	//http://127.0.0.1:8080/backoffice/user/id?num=1
	@CrossOrigin //(origins="http://localhost:4200")
	@GetMapping(path="/id")
	public @ResponseBody Optional<User> getOneUser(@RequestParam Long num) {
		// This returns a JSON or XML with the users
		return userRepository.findById(num);
	}

	//http://127.0.0.1:8080/backoffice/user/name?nom=john
	@CrossOrigin //(origins="http://localhost:4200")
	@GetMapping(path="/name")
	public @ResponseBody Optional<User> getOneUser(@RequestParam String nom) {
		// This returns a JSON or XML with the users
		return userRepository.findByName(nom);
	}

}