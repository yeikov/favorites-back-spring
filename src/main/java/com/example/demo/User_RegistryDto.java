package com.example.demo;

import lombok.Data;

@Data
public class User_RegistryDto {
	
	private Long userId;
	
	private Long registryId;
	
	private int favorito;
	
	private int recomendable;
	
	private String notes;

	
	
	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Long getRegistryId() {
		return registryId;
	}



	public void setRegistryId(Long registryId) {
		this.registryId = registryId;
	}



	public int getFavorito() {
		return favorito;
	}



	public void setFavorito(int favorito) {
		this.favorito = favorito;
	}



	public int getRecomendable() {
		return recomendable;
	}



	public void setRecomendable(int recomendable) {
		this.recomendable = recomendable;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}



	@Override
	public String toString() {
		return "User_RegistryDto [userId=" + userId + ", registryId=" + registryId + ", favorito=" + favorito
				+ ", recomendable=" + recomendable + ", notes=" + notes + "]";
	}
	
	

}
