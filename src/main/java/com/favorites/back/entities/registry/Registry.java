package com.favorites.back.entities.registry;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.favorites.back.CommonUtilities;
import com.favorites.back.entities.assesment.Assessment;

@Entity
public class Registry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private LocalDate productionDate;

	// @ApiModelProperty(notes = "Tipo de medio", name="media", required=true,
	// value="book")
	private String media;

	private String author;

	@OneToMany(mappedBy = "registry")
	private Set<Assessment> registrations;

	public Registry() {
	};

	public Registry(String title, String media, String author, String year) {

		this.title = title;
		this.media = media;
		this.author = author;
		try {
			this.productionDate = CommonUtilities.year2LocalDate(Integer.parseInt(year));

		} catch (NumberFormatException e) {
			System.out.println("Invalid String");
		}

	}

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

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getRegistrations() {
		return this.registrations == null ? 0 : this.registrations.size();
	}

}
