package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.models.HttpMethod;

@SpringBootApplication
public class DemoApplication extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests(a -> a.antMatchers("/error", "/webjars/**"

		).permitAll()
				
				.mvcMatchers( 
						backEndUrl,
						backEndUrl + "/users/{id}", 
						backEndUrl + "/users",
						backEndUrl + "/registries/{id}", 
						backEndUrl + "/registries", 
						backEndUrl + "/assessments/{id}",
						backEndUrl + "/assessments"

				).permitAll()
				.anyRequest().authenticated()

		).exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
				.oauth2Login();
		// @formatter:on
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/"); 
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public static final String backEndUrl = "/backend";

}
