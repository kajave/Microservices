package com.epam.exception;

public class UserNotFoundException extends RuntimeException{

	String massage;

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public UserNotFoundException(String massage) {
		super();
		this.massage = massage;
	}
	
}
