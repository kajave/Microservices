package com.epam.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.epam.client.BookClient;
import com.epam.model.BookDTO;

@RestController
@RequestMapping("/library/books")
public class LibraryBookController {

	@Autowired
	private BookClient bookClient;


	@GetMapping
	public List<BookDTO> getBooks() {
		return bookClient.getBooks();	
	}
	
	@GetMapping("/{bookid}")
	public BookDTO getBookById(@PathVariable String bookid) {
		return bookClient.getBook(bookid);
		
	}
	
	@DeleteMapping("/{bookid}")
	public String deleteBook(@PathVariable String bookid) {
		return bookClient.deleteBook(bookid);
		
	}
	
	@PostMapping
	public BookDTO addBook(@RequestBody BookDTO bookDTO) {
		return bookClient.addBook(bookDTO);
		
	}
	
	@PutMapping
	public BookDTO updateBook(@RequestBody BookDTO bookDTO) {
		return bookClient.updateBook(bookDTO);
		
	}

}
