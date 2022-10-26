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

import com.epam.client.UserClient;
import com.epam.model.LibraryDTO;
import com.epam.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryUserControllerTest {

	@Autowired
	private MockMvc mockMvc;


	@MockBean
	UserClient userClient;

	

	UserDTO userDto;
	List<UserDTO> userList = new ArrayList<>();

	LibraryDTO libraryDto;

	ObjectMapper objectMapper;

	@BeforeEach
	void userSetup() {

		userDto = new UserDTO(1, "Ak", "aniket@gmail.com", "Aniket");
		userList.add(userDto);

		// libraryDto = new LibraryDto("niks", 1);
	}

	@Test
	void test_getUsers() throws Exception {
		when(userClient.getUsers()).thenReturn(userList);

		mockMvc.perform(get("/library/user").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void test_getUsers_fail() throws Exception {
		when(userClient.getUsers()).thenThrow(FeignException.class);

		mockMvc.perform(get("/library/user").contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}

	@Test
	void test_getUser() throws Exception {
		when(userClient.getUser("Ak")).thenReturn(userDto);

		mockMvc.perform(get("/library/user/Ak").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void test_getUserFail() throws Exception {
		when(userClient.getUser("Ak")).thenThrow(FeignException.class);

		mockMvc.perform(get("/library/user/Ak").contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}

	@Test
	void test_addUser() throws Exception {
		objectMapper = new ObjectMapper();
		String str = objectMapper.writeValueAsString(userDto);
		when(userClient.addUser(userDto)).thenReturn(userDto);

		mockMvc.perform(post("/library/user").contentType(MediaType.APPLICATION_JSON).content(str))
				.andExpect(status().isOk());

	}

	@Test
	void test_addUser_fail() throws Exception {
		objectMapper = new ObjectMapper();
		String str = objectMapper.writeValueAsString(userDto);
		when(userClient.addUser(any(UserDTO.class))).thenThrow(FeignException.class);

		mockMvc.perform(post("/library/user").contentType(MediaType.APPLICATION_JSON).content(str))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}

	@Test
	void test_deleteUser() throws Exception {

		when(userClient.delete("Ak")).thenReturn("User delete succesfully");

		mockMvc.perform(delete("/library/user/Ak").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string("User delete succesfully"));

	}

	@Test
	void test_deleteUser_fail() throws Exception {

		when(userClient.delete("Ak")).thenThrow(FeignException.class);

		mockMvc.perform(delete("/library/user/Ak").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FeignException));

	}
	
	@Test
	void test_updateUser() throws Exception {
		objectMapper = new ObjectMapper();
		userDto.setId(1);
		String str = objectMapper.writeValueAsString(userDto);
		when(userClient.update(userDto)).thenReturn(userDto);

		mockMvc.perform(put("/library/user").contentType(MediaType.APPLICATION_JSON).content(str))
				.andExpect(status().isOk());

	}

	@Test
	void test_updateUser_fail() throws Exception {
		objectMapper = new ObjectMapper();
		userDto.setId(1);
		String str = objectMapper.writeValueAsString(userDto);
		when(userClient.update(any(UserDTO.class))).thenThrow(FeignException.class);

		mockMvc.perform(put("/library/user").contentType(MediaType.APPLICATION_JSON).content(str)).andDo(print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));

	}


}
