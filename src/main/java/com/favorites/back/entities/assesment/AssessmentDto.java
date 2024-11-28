package com.favorites.back.entities.assesment;

public class AssessmentDto {
	
	private Long viewerId;
	
	private Long registryId;
	
	private int favorite;
	
	private int recommend;
	
	private String notes;

	
	
	public Long getViewerId() {
		return viewerId;
	}



	public void setViewerId(Long viewerId) {
		this.viewerId = viewerId;
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
		return "Viewer_RegistryDto [viewerId=" + viewerId + ", registryId=" + registryId + ", favorito=" + favorite
				+ ", recomendable=" + recommend + ", notes=" + notes + "]";
	}
	
	

}
