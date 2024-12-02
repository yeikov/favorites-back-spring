package com.favorites.back.entities.viewer;

import java.time.LocalDate;
import java.util.Set;

import com.favorites.back.entities.assessment.Assessment;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

 
@Entity
public class Viewer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Column(unique=true)
	@Nonnull
	private String eMail;
	
	private String city;
	
	private LocalDate birth;
	
	@OneToMany(mappedBy="viewer")
	private Set<Assessment> registrations;
	
	public Viewer() {};

	public Viewer(String name, String eMail) {
	    this.name = name;
	    this.eMail = eMail;
	}

	public Viewer(String name, String eMail, String city, LocalDate birth) {
	    this.name = name;
	    this.eMail = eMail;
	    this.city = city;
	    this.birth = birth;
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

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}
} 

