package com.favorites.back.entities.assesment;

public class AssessmentNotFoundException extends RuntimeException{
	
	AssessmentNotFoundException (Long id){
		super ("Could not find Assessment " + id);
	}
}
