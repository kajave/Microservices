package com.epam.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.epam.model.BookDTO;


@FeignClient(name = "book-service")
@LoadBalancerClient(name ="book-service" )
public interface BookClient {

	@GetMapping("/books")
	public List<BookDTO> getBooks();

	@GetMapping("/books/{id}")
	public BookDTO getBook(@PathVariable("id") String bookId);

	@PostMapping("/books")
	public BookDTO addBook(BookDTO bookDto);

	@PutMapping("/books")
	public BookDTO updateBook(BookDTO bookDto);

	
	@DeleteMapping("/books/{book_id}")
	public String deleteBook(@PathVariable("book_id") String bookId);

}
