package com.favorites.back.entities.assesment;

public class AssessmentExistsException extends RuntimeException{

	AssessmentExistsException(){
		super("Duplicate entry");
	}
}
