package com.example.demo;

import lombok.Data;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	private String city;
	
	private Date birdth;
	
	@ManyToMany
	@JoinTable(name="R_User_Registry", joinColumns = @JoinColumn(name = "Id_User", referencedColumnName= "id"), inverseJoinColumns = @JoinColumn(name = "id_Registry", referencedColumnName = "id"))
	private List<Registry> registries;
	
	User() {};

	User(String name, String city) {
	    this.name = name;
	    this.city = city;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getBirdth() {
		return birdth;
	}

	public void setBirdth(Date birdth) {
		this.birdth = birdth;
	}
	

}
