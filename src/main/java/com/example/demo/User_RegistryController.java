package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path=DemoApplication.backOfficeUrl + "/user_registry")
public class User_RegistryController {
	
	@Autowired
	private User_RegistryRepository user_registryRepository;
	
	private User_RegistryUtils user_registryUtils;
	
	
	//curl localhost:8080/backoffice/user_registry/update -d userId=1 -d registry=1 -d fav=1 -d rec=1
	@PostMapping(path= "/update")
	public @ResponseBody User_Registry updateUserRegistry(@RequestParam User user, @RequestParam Registry registry, @RequestParam int fav, @RequestParam int rec ) {
		
		List <User_Registry> urIte = (List) user_registryUtils.existsUserRegistry(user, registry);
		
		if (urIte.size()==0) {
			return null;
		}
		
		User_Registry ur = urIte.get(0);
		
		ur.setFavorito(fav); 
		ur.setRecomendable(rec); 
		
		user_registryRepository.save(ur);
		
		return ur;
	}
	
	


}
