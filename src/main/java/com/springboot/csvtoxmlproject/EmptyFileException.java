package com.springboot.csvtoxmlproject;

public class EmptyFileException extends Exception {
	
	
	public EmptyFileException() {
		super("Uploaded file is empty");
	}
	
	public EmptyFileException(String message) {
		super(message);
	}
	

}
