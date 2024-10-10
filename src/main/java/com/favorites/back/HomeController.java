package com.favorites.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class HomeController {
	
	@CrossOrigin
	@GetMapping("/")
    public RedirectView redirectWithUsingRedirectView() {
        return new RedirectView(BackApplication.backEndUrl);
    }
	
	@CrossOrigin
	@GetMapping(BackApplication.backEndUrl)
	String home (){
		return "home";
	}
	

}
