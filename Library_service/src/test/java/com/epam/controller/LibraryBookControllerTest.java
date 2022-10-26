package com.epam.controller;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.client.BookClient;
import com.epam.client.UserClient;
import com.epam.model.BookDTO;
import com.epam.model.LibraryDTO;
import com.epam.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryBookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	BookClient bookClient;

	@MockBean
	UserClient userClient;

	BookDTO bookDto;
	List<BookDTO> bookList = new ArrayList<>();

	UserDTO userDto;
	List<UserDTO> userList = new ArrayList<>();

	LibraryDTO libraryDto;

	ObjectMapper objectMapper;

	@BeforeEach
	void bookSetup() {
		bookDto = new BookDTO(1, "History", "Gk Publication", "R.Guha", 200);

		bookList.add(bookDto);

		userDto = new UserDTO(1, "niks", "niks123@gmail.com", "Nikhil");
		userList.add(userDto);

		// libraryDto = new LibraryDto("niks", 1);
	}

	@Test
	void test_getBooks() throws Exception {
		when(bookClient.getBooks()).thenReturn(bookList);

		mockMvc.perform(get("/library/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void test_getBooks_fail() throws Exception {
		when(bookClient.getBooks()).thenThrow(FeignException.class);

		mockMvc.perform(get("/library/books").contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}

	@Test
	void test_getBook() throws Exception {
		when(bookClient.getBook("1")).thenReturn(bookDto);

		mockMvc.perform(get("/library/books/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void test_getBookFail() throws Exception {
		when(bookClient.getBook("1")).thenThrow(FeignException.class);

		mockMvc.perform(get("/library/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}

	@Test
	void test_addBook() throws Exception {
		objectMapper = new ObjectMapper();
		String str = objectMapper.writeValueAsString(bookDto);
		when(bookClient.addBook(bookDto)).thenReturn(bookDto);

		mockMvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).content(str))
				.andExpect(status().isOk());

	}

	@Test
	void test_addBook_fail() throws Exception {
		objectMapper = new ObjectMapper();
		String str = objectMapper.writeValueAsString(bookDto);
		when(bookClient.addBook(any(BookDTO.class))).thenThrow(FeignException.class);

		mockMvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).content(str))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}

	@Test
	void test_deleteBook() throws Exception {

		when(bookClient.deleteBook("1")).thenReturn("Book delete succesfully");

		mockMvc.perform(delete("/library/books/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string("Book delete succesfully"));

	}

	@Test
	void test_deleteBook_fail() throws Exception {

		when(bookClient.deleteBook("1")).thenThrow(FeignException.class);

		mockMvc.perform(delete("/library/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}
	
	@Test
	void test_updateBook() throws Exception {
		objectMapper = new ObjectMapper();
		bookDto.setId(1);
		String str = objectMapper.writeValueAsString(bookDto);
		when(bookClient.updateBook(bookDto)).thenReturn(bookDto);

		mockMvc.perform(put("/library/books").contentType(MediaType.APPLICATION_JSON).content(str))
				.andExpect(status().isOk());

	}

	@Test
	void test_updateBook_fail() throws Exception {
		objectMapper = new ObjectMapper();
		bookDto.setId(1);
		String str = objectMapper.writeValueAsString(bookDto);
		when(bookClient.updateBook(any(BookDTO.class))).thenThrow(FeignException.class);

		mockMvc.perform(put("/library/books").contentType(MediaType.APPLICATION_JSON).content(str)).andDo(print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));

	}


}
