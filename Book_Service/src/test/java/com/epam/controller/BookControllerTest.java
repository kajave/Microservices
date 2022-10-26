package com.epam.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.exception.BookNotFoundException;
import com.epam.model.BookDTO;
import com.epam.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
class BookControllerTest {

	@MockBean
	BookService bookService;

	@InjectMocks
	BookController bookController;

	@Autowired
	MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	void test_addBook_succesfull() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");
		String json = mapper.writeValueAsString(bookDTO);
		when(bookService.add(bookDTO)).thenReturn(bookDTO);
		mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	void test_addBook_fail() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");
		String json = mapper.writeValueAsString(bookDTO);
		when(bookService.add(any(BookDTO.class))).thenThrow(BookNotFoundException.class);
		mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException));
	}

	@Test
	void test_getBook_succesfull() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");

		when(bookService.get()).thenReturn(Arrays.asList(new BookDTO()));
		mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void test_getBook_fail() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");

		when(bookService.get()).thenThrow(BookNotFoundException.class);
		mockMvc.perform(get("/books")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException));
	}

	@Test
	void test_getBookById_succesfull() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");

		when(bookService.getById(anyInt())).thenReturn(bookDTO);
		mockMvc.perform(get("/books/1")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void test_getBookById_fail() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");

		when(bookService.getById(anyInt())).thenThrow(BookNotFoundException.class);
		mockMvc.perform(get("/books/1")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException));
	}

	@Test
	void test_delete_succesfull() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");

		when(bookService.deleteById(anyInt())).thenReturn("delete succesfully");
		mockMvc.perform(delete("/books/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("delete succesfully"));
	}

	@Test
	void test_delete_fail() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");

		when(bookService.deleteById(anyInt())).thenThrow(BookNotFoundException.class);
		mockMvc.perform(delete("/books/1")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException));
	}
	
	@Test
	void test_updateBook_succesfull() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");
		String json = mapper.writeValueAsString(bookDTO);
		when(bookService.update(bookDTO)).thenReturn(bookDTO);
		mockMvc.perform(put("/books").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	void test_updateBook_fail() throws Exception {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName("Wings of fire");
		String json = mapper.writeValueAsString(bookDTO);
		when(bookService.update(any(BookDTO.class))).thenThrow(BookNotFoundException.class);
		mockMvc.perform(put("/books").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException));
	}

}
