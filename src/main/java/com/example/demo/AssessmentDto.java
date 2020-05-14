package com.example.demo;

import lombok.Data;

@Data
public class AssessmentDto {
	
	private Long userId;
	
	private Long registryId;
	
	private int favourite;
	
	private int recommend;
	
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




	public int getFavourite() {
		return favourite;
	}



	public void setFavourite(int favourite) {
		this.favourite = favourite;
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
		return "User_RegistryDto [userId=" + userId + ", registryId=" + registryId + ", favorito=" + favourite
				+ ", recomendable=" + recommend + ", notes=" + notes + "]";
	}
	
	

}
