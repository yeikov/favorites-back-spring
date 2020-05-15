package com.example.demo;

public class AssessmentExistsException extends RuntimeException{

	AssessmentExistsException(){
		super("Duplicate entry");
	}
}
