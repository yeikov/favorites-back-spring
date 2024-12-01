package com.favorites.back.entities.assessment;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.viewer.Viewer;

//import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames = {"viewer_id", "registry_id"})
})
public class Assessment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "viewer_id")
	private Viewer viewer;
	
	@ManyToOne
	@JoinColumn(name = "registry_id")
	private Registry registry;
	
	private LocalDateTime registeredAt;
	
	//@ApiModelProperty(notes = "Posición en la lista de favoritos", name="favorite", required=false, value="1")
	private int favorite;
	//@ApiModelProperty(notes = "Posición en la lista de recomendables", name="recommend", required=false, value="1")
	private int recommend;
	
	private String notes;

	public Assessment() {
		
	}
	
	public Assessment(Viewer viewer, Registry registry, int favorite, int recommend, String notes) {
		super();
		this.viewer = viewer;
		this.registry = registry;
		this.favorite = favorite;
		this.recommend = recommend;
		this.notes = notes;
		this.registeredAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Viewer getViewer() {
		return viewer;
	}

	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public LocalDateTime getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Viewer_Registry [id=" + id + ", viewer=" + viewer + ", registry=" + registry + ", registeredAt="
				+ registeredAt + ", favorito=" + favorite + ", recomendable=" + recommend + ", notes=" + notes + "]";
	}
	
	
}
