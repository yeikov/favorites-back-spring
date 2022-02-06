package com.example.demo.entities.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.example.demo.entities.assesment.Assessment;
import com.sun.istack.NotNull;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Column(unique=true)
	@NotNull
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
