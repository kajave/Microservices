package com.epam.exception;

public class LibraryException extends Exception{

	String massage;

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public LibraryException(String massage) {
		super();
		this.massage = massage;
	}
	
}
