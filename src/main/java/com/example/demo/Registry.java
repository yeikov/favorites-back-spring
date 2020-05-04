package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Entity
public class Registry {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//titulo | 
	private String title;
	
	//año prod 
	private LocalDateTime productionDate;
	
	//tipo prod
	@ApiModelProperty(notes = "Tipo de medio", name="media", required=true, value="book")
	private String media;
	
	//autor 
	private String autor;

	@OneToMany(mappedBy="registry")
	private Set<User_Registry> registrations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDateTime productionDate) {
		this.productionDate = productionDate;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}


}




