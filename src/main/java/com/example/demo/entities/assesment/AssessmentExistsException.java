package com.example.demo.entities.assesment;

public class AssessmentExistsException extends RuntimeException{

	AssessmentExistsException(){
		super("Duplicate entry");
	}
}
