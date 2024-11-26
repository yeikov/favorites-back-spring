package com.favorites.back.entities.viewer;

public class ViewerNotFoundException extends RuntimeException {
	
	public ViewerNotFoundException (Long id){
		super ("101_Could not find Viewer "+id);
	}
	
	ViewerNotFoundException (String name){
		super ("101_Could not find Viewer "+name);
	}

}
