package com.epam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.exception.BookNotFoundException;
import com.epam.model.Book;
import com.epam.model.BookDTO;
import com.epam.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	ModelMapper mapper;

	@Autowired
	BookRepository bookRepository;

	public BookDTO add(BookDTO bookDTO) throws BookNotFoundException {

		Book book = mapper.map(bookDTO, Book.class);
		try {
			book = bookRepository.save(book);
		} catch (RuntimeException e) {
			throw new BookNotFoundException("book is not added succesfully");
		}

		return mapper.map(book, BookDTO.class);
	}

	
	public List<BookDTO> get() throws BookNotFoundException {
		List<Book> books;
		try {
			books = bookRepository.findAll();
		} catch (RuntimeException e) {
			throw new BookNotFoundException("Book is empty");
		}
		return books.stream().map(b -> mapper.map(b, BookDTO.class)).collect(Collectors.toList());
	}

	
	public BookDTO getById(int id) throws BookNotFoundException {
		Optional<Book> books;
		try {
			books = bookRepository.findById(id);
		} catch (RuntimeException e) {
			throw new BookNotFoundException("Book is empty");
		}
		Book book = books.orElseThrow(() -> new BookNotFoundException("book is not present"));
		return mapper.map(book, BookDTO.class);
	}
	
	
	public String deleteById(int id) throws BookNotFoundException {
		try {
			 bookRepository.deleteById(id);;
		} catch (RuntimeException e) {
			throw new BookNotFoundException("Book is empty");
		}
		return "Book deleted Succesfully";
	}
	
	
	public BookDTO update(BookDTO bookDTO) throws BookNotFoundException {

		Book book = mapper.map(bookDTO, Book.class);
		try {
			book = bookRepository.save(book);
		} catch (RuntimeException e) {
			throw new BookNotFoundException("book is not update succesfully");
		}

		return mapper.map(book, BookDTO.class);
	}
}
