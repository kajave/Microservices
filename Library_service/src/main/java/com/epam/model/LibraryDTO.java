package com.epam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

public class LibraryDTO {
	
	int id;	
	String username;
	int bookId;

	public LibraryDTO() {

	}
	
	public LibraryDTO(int id, String username, int bookId) {
		super();
		this.id = id;
		this.username = username;
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "Library [id=" + id + ", username=" + username + ", bookId=" + bookId + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	
	

}
