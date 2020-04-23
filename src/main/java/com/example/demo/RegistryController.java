package com.example.demo;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class RegistryController {
	
	ArrayList<String> media = new ArrayList<String>();
	
	RegistryController(){
		media.add("series");
	    media.add("book");
	    media.add("film");
	    media.add("album");
	}
	
	@Autowired
	private RegistryRepository registryRepository;
	
	@PostMapping(path= "/add")
	public @ResponseBody String addNewRegistry(@RequestParam String title, @RequestParam String media) {
		Registry r = new Registry();
		r.title = title;
		r.media = media;
		registryRepository.save(r);
		return title;
	}
	
	@GetMapping(path="/id")
	public @ResponseBody Optional<Registry> getOneRegistry(@RequestParam Integer num) {
		// This returns a JSON or XML with the users
		return registryRepository.findById(num);
	}
	

}
