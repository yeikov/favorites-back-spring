package com.example.demo;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	private User_RegistryUtils user_registryUtils;
	
	@CrossOrigin //(origins="http://localhost:4200")
	@GetMapping(path="/id")
	public @ResponseBody Optional<Registry> getOneRegistry(@RequestParam int num) {
		return registryRepository.findById(num);
	}
	
	@CrossOrigin //(origins="http://localhost:4200")
	//http://127.0.0.1:8080/backoffice/registry/all
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Registry> getAll() {
		return registryRepository.findAll();
	}
	
	@CrossOrigin //(origins="http://localhost:4200")
	//http://127.0.0.1:8080/backoffice/registry/user?id=1
	@GetMapping(path="/user")
	public @ResponseBody Iterable<Registry> getAllUserRegistries(@RequestParam Long id){
		
		User u = userRepository.findById(id).orElse(null);
		
		Iterable <User_Registry> userRegistros =  user_registryRepository.findAllByUser(u);
		
		List <Registry> registros = new ArrayList<Registry>();
		
  		userRegistros.forEach(reg -> {
			registros.add(reg.getRegistry());
			
		});
		
		return registros;
		
	}
	
	//curl -X POST "http://localhost:8080/backoffice/registry/add?autor=David%20Simon&fav=1&media=serie&rec=1&title=The%20Wire&userId=1&year=2002" -H "accept: */*"
	//curl -X POST "http://localhost:8080/backoffice/registry/add?autor=tolkien&fav=1&media=book&rec=1&title=hobbit&userId=1&year=1937" -H "accept: */*"
	@CrossOrigin //(origins="http://localhost:4200")
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
		
		Registry sondaRegistro = existsRegistry(title, media, autor, year);
		
		LocalDateTime now = LocalDateTime.now();
		
		Iterable <User_Registry> sondaUr;
		
		User_Registry ur = new User_Registry();
		Registry r;
		
		//Si no existe el registro se guarda un nuevo
		if (sondaRegistro == null) {
			r = new Registry();
			r.setTitle(title);
			r.setAutor(autor);
			r.setMedia(media);
			r.setProductionDate(LocalDateTime.of(year, 1, 1, 0, 0));
			registryRepository.save(r);
			//ur.registry = r;
		} else { //Si ya existe el registro
			r = sondaRegistro;
			//comprueba si el usuario ya lo ha vinculado (si existe el user_registry)
			sondaUr = user_registryUtils.existsUserRegistry(u, r);
			if (sondaUr != null) {//si existe se actualizan los parametros de favorito y recomendable
				List<User_Registry> tal = (List) sondaUr;
				for (int i = 0; i < tal.size(); i++) { 
					if (i==0) {
					tal.get(i).setFavorito(fav); 
					tal.get(i).setRecomendable(rec); 
					tal.get(i).setRegisteredAt(now);
					user_registryRepository.save(tal.get(i));
					} else {
						user_registryRepository.delete(tal.get(i));
					}
				};
				
				System.out.println("actualiza un registro");
				return title;
			} 
			
			
		}
		
		ur.setRegistry(r);;

		ur.setUser(u);
		ur.setFavorito(fav); 
		ur.setRecomendable(rec); 
		ur.setRegisteredAt(now);

		user_registryRepository.save(ur);
		System.out.println("crea un registro");
		return title;
	}
	
	

	//http://127.0.0.1:8080/backoffice/registry/exists?title=hobbit%20&media=book%20&autor=tolkien%20&year=1937
	@CrossOrigin //(origins="http://localhost:4200")
	@GetMapping(path="/exists")
	public @ResponseBody Registry existsRegistry (
			@RequestParam String title, 
			@RequestParam String media, 
			@RequestParam String autor, 
			@RequestParam int year) 
	{
		
		Registry response = null;
		LocalDateTime yearF = LocalDateTime.of(year, 1, 1, 0, 0);
		List <Registry> request = 
		registryRepository.findAllByTitleAndMediaAndAutorAndProductionDate(title, media, autor, yearF);
		if (request.size()!=0) {
			response = request.get(0);
		}
		System.out.println(request);
		return response;
		
	}
	
	
	

}
