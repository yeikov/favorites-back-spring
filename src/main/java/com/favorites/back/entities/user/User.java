package com.favorites.back.entities.user;

import java.time.LocalDate;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.favorites.back.entities.assesment.Assessment;


//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Column(unique=true)
	@Nonnull
	private String eMail;
	
	private String city;
	
	private LocalDate birdth;
	
	@OneToMany(mappedBy="user")
	private Set<Assessment> registrations;
	
	User() {};

	User(String name, String city) {
	    this.name = name;
	    this.city = city;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDate getBirdth() {
		return birdth;
	}

	public void setBirdth(LocalDate birdth) {
		this.birdth = birdth;
	}


}
