package com.example.demo;

public class AssessmentNotFoundException extends RuntimeException{
	
	AssessmentNotFoundException (Long id){
		super ("Could not find User_Registry " + id);
	}
}
