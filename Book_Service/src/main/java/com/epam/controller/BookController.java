package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.exception.BookNotFoundException;
import com.epam.model.BookDTO;
import com.epam.service.BookService;


@RestController
@RequestMapping(value = "/books")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping
	public BookDTO add(@RequestBody BookDTO bookDTO) throws BookNotFoundException {
		return bookService.add(bookDTO);
	}
	
	@GetMapping
	public List<BookDTO> get() throws BookNotFoundException{
		return bookService.get();
	}
	
	@GetMapping("/{id}")
	public BookDTO getById(@PathVariable String id) throws BookNotFoundException {
		return bookService.getById(Integer.parseInt(id));
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) throws BookNotFoundException {
		return bookService.deleteById(Integer.parseInt(id));
	}
	
	@PutMapping
	public BookDTO update(@RequestBody BookDTO bookDTO) throws BookNotFoundException {
		return bookService.update(bookDTO);
	}

}
