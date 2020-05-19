package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@SpringBootApplication
public class DemoApplication extends WebSecurityConfigurerAdapter{
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	// @formatter:off
	        http
	            .authorizeRequests(a -> a
	                .antMatchers("/", "/backend/users", "/error", "/webjars/**").permitAll()
	                .anyRequest().authenticated()
	            )
	            .exceptionHandling(e -> e
	                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
	            )
	            .oauth2Login();
	        // @formatter:on
	    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	public static final String backEndUrl = "/backend";
	
	
	
}
