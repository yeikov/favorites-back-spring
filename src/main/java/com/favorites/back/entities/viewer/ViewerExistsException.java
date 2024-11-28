package com.favorites.back.entities.viewer;

public class ViewerExistsException extends RuntimeException {

	ViewerExistsException(String email) {
		super("Viewer " + email + " already exixts.");
	}

}
