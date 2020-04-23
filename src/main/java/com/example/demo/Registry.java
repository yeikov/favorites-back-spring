package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;


@Data
@Entity
public class Registry {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	//titulo | 
	String title;
	
	//año prod 
	Date productionDate;
	
	//tipo prod
	String media;
	
	//autor 
	String autor;
	
	//comentarios 
	String coment;
	
	//favorito
	int favourite;
	
	//recomendable 
	int recommendable;
	
	@ManyToMany(mappedBy="registries")
	private List<User> users;

	

}




