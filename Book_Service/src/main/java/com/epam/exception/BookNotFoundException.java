package com.epam.exception;

public class BookNotFoundException extends Exception{

	String massage;

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public BookNotFoundException(String massage) {
		super();
		this.massage = massage;
	}
	
}
