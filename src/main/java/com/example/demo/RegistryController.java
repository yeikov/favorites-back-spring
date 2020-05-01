package com.example.demo;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path=DemoApplication.backOfficeUrl + "/registry")
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
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private User_RegistryRepository user_registryRepository;
	

	@GetMapping(path="/id")
	public @ResponseBody Optional<Registry> getOneRegistry(@RequestParam int num) {
		return registryRepository.findById(num);
	}
	
	//http://127.0.0.1:8080/backoffice/registry/all
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Registry> getAll() {
		return registryRepository.findAll();
	}
	
	//http://127.0.0.1:8080/backoffice/registry/user?id=1
	@GetMapping(path="/user")
	public @ResponseBody Iterable<Registry> getAllUserRegistries(@RequestParam Long id){
		
		User u = userRepository.findById(id).orElse(null);
		
		Iterable <User_Registry> userRegistros =  user_registryRepository.findAllByUser(u);
		
		List <Registry> registros = new ArrayList<Registry>();
		
		
  		userRegistros.forEach(reg -> {
			registros. add(reg.registry);
			
		});
		
		return registros;
		
	}
	
	//curl localhost:8080/backoffice/registry/add -d userId=1 -d title=hobitt -d autor=tolkien -d media=book -d year=1937 -d fav=1 -d rec=1
	@PostMapping(path= "/add")
	public @ResponseBody String addNewRegistry(
			@RequestParam Long userId, 
			@RequestParam String title, 
			@RequestParam String autor, 
			@RequestParam String media, 
			@RequestParam int year, 
			@RequestParam int fav, 
			@RequestParam int rec ) {
		
		User u = userRepository.findById(userId).orElse(null);
		
		Registry r = new Registry();
		r.title = title;
		r.autor = autor;
		r.media = media;
		r.productionDate=LocalDateTime.of(year, 1, 1, 0, 0);
		
		User_Registry ur = new User_Registry();
		LocalDateTime now = LocalDateTime.now();

		ur.user=u;
		ur.registry=r;
		ur.favorito=1;
		ur.favorito=fav;
		ur.recomendable =rec;
		ur.setRegisteredAt(now);

		registryRepository.save(r);
	
		user_registryRepository.save(ur);
		
		return title;
	}
	
	//curl localhost:8080/backoffice/registry/update -d id=1 -d userId=1 -d fav=1 -d rec=1
	// param registryId, usuarioId, favoritoIndex, recomendableIndex 
	@PostMapping(path= "/update")
	public @ResponseBody String updateNewRegistry(@RequestParam int id, @RequestParam int userId, @RequestParam int fav, @RequestParam int rec ) {
		
		User u = userRepository.findById(userId).orElse(null);
		
		Registry r = new Registry();
		
		User_Registry ur = new User_Registry();
		ur.user=u;
		ur.favorito=fav;
		ur.recomendable =rec;

		
		user_registryRepository.save(ur);
		return "updated";
	}

	//http://127.0.0.1:8080/backoffice/registry/exists?title=hobbit%20&media=book%20&autor=tolkien%20&year=1937
	@GetMapping(path="/exists")
	public @ResponseBody Boolean exists (
			@RequestParam String title, 
			@RequestParam String media, 
			@RequestParam String autor, 
			@RequestParam int year) 
	{
		
		Boolean response = true;
		LocalDateTime yearF = LocalDateTime.of(year, 1, 1, 0, 0);
		List <Registry> request = 
		registryRepository.findAllByTitleAndMediaAndAutorAndProductionDate(title, media, autor, yearF);
		if (request.size()==0) {
			response = false;
		}
		System.out.println(request);
		return response;
		
	}
	
	
	
	
	

}
