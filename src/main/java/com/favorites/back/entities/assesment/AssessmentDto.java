package com.favorites.back.entities.assesment;

import lombok.Data;

@Data
public class AssessmentDto {
	
	private Long userId;
	
	private Long registryId;
	
	private int favorite;
	
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
		return "User_RegistryDto [userId=" + userId + ", registryId=" + registryId + ", favorito=" + favorite
				+ ", recomendable=" + recommend + ", notes=" + notes + "]";
	}
	
	

}
