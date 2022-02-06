package com.example.demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class HomeController {
	
	@CrossOrigin
	@GetMapping("/")
    public RedirectView redirectWithUsingRedirectView() {
        return new RedirectView(DemoApplication.backEndUrl);
    }
	
	@CrossOrigin
	@GetMapping(DemoApplication.backEndUrl)
	String home (){
		return "home";
	}
	

}
