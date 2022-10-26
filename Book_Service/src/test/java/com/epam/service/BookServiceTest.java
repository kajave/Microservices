package com.epam.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.exception.BookNotFoundException;
import com.epam.model.Book;
import com.epam.model.BookDTO;
import com.epam.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookServiceTest {

	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	BookService bookService;
	
	@Mock
	ModelMapper modelMapper;
	
	@Test
	void test_addBook_sucessfully() throws BookNotFoundException {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Dark");
		Book book = new Book();
		book.setName("Dark");
		when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
		when(bookRepository.save(book)).thenReturn(book);
		when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
		assertEquals(bookDTO, bookService.add(bookDTO));
		assertEquals("Dark", bookService.add(bookDTO).getName());
	}
	
	@Test
	void test_addBook_fail() throws BookNotFoundException {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Dark");
		Book book = new Book();
		book.setName("Dark");
		when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
		when(bookRepository.save(book)).thenThrow(RuntimeException.class);
		assertThrows(BookNotFoundException.class, ()->bookService.add(bookDTO));
	}
	
	@Test
	void test_getBooks() throws BookNotFoundException {
		List<Book> list = Arrays.asList(new Book());
		BookDTO bookDTO = new BookDTO();
		when(bookRepository.findAll()).thenReturn(list);
		
		list.stream().map(b->when(modelMapper.map(b, BookDTO.class)).thenReturn(bookDTO)).collect(Collectors.toList());
		assertEquals(1, bookService.get().size());
	}
	
	@Test
	void test_getBooks_exception() throws BookNotFoundException {
		when(bookRepository.findAll()).thenThrow(RuntimeException.class);
		assertThrows(BookNotFoundException.class, ()->bookService.get());
	}
	
	@Test
	void test_getById() throws BookNotFoundException {
		BookDTO bookDTO = new BookDTO();
		Optional<Book> bookOpt = Optional.empty();
		Book book = new Book();
		book.setName("Wings of Fire");
		when(bookRepository.findById(1)).thenThrow(RuntimeException.class);
		assertThrows(BookNotFoundException.class, ()->bookService.getById(1));
	}
	
	@Test
	void test_deleteById_succesful() throws BookNotFoundException {
		doNothing().when(bookRepository).deleteById(1);
		assertEquals("Book deleted Succesfully", bookService.deleteById(1));
	}
	
	@Test
	void test_deleteById_fail() throws BookNotFoundException {
		doThrow(RuntimeException.class).when(bookRepository).deleteById(1);
		assertThrows(BookNotFoundException.class, ()->bookService.deleteById(1));
	}

	@Test
	void test_update_succesful() throws BookNotFoundException {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Dark");
		Book book = new Book();
		book.setName("Dark");
		when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
		when(bookRepository.save(book)).thenReturn(book);
		when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
		assertEquals(bookDTO, bookService.update(bookDTO));
		assertEquals("Dark", bookService.update(bookDTO).getName());
	}
	
	@Test
	void test_update_fail() throws BookNotFoundException {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Dark");
		Book book = new Book();
		book.setName("Dark");
		when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
		when(bookRepository.save(book)).thenThrow(RuntimeException.class);
		assertThrows(BookNotFoundException.class, ()->bookService.update(bookDTO));
	}
}
