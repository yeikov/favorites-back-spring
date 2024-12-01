package com.favorites.back.entities.assessment;

public class AssessmentExistsException extends RuntimeException{

	AssessmentExistsException(){
		super("Duplicate entry");
	}
}
