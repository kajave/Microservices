package com.epam.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.exception.UserNotFoundException;
import com.epam.model.UserDTO;
import com.epam.service.UserService;
import com.epam.xcontroller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	UserDTO userDTO;
	ObjectMapper objectMapper;

	@BeforeEach
	void set() {
		userDTO = new UserDTO();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testRegisterUser() throws Exception {
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		
		when(userService.add(userDTO)).thenReturn(userDTO);
		String json = objectMapper.writeValueAsString(userDTO);
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}

	@Test
	void testGetUsers() throws Exception {
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		List<UserDTO> list = new ArrayList<>();
		list.add(userDTO);
		when(userService.get()).thenReturn(list);
		mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testUsersNotGet() throws Exception {
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		when(userService.get()).thenThrow(new UserNotFoundException("User not found"));
		mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void testGetUserByName() throws Exception {
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		when(userService.getUserByName("Aniket")).thenReturn(userDTO);
		mockMvc.perform(get("/users/Aniket").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testUserNotByName() throws Exception {
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		when(userService.getUserByName("Aniket")).thenThrow(new UserNotFoundException("User not found"));
		mockMvc.perform(get("/users/Aniket").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteUserName() throws Exception {
		userDTO.setId(1);
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		when(userService.deleteById("Aniket")).thenReturn("Deleted succesfully");
		mockMvc.perform(delete("/users/Aniket").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string("Deleted succesfully"));
	}

	@Test
	void testUserNotDeletedByName() throws Exception {
		userDTO.setId(1);
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		when(userService.deleteById("Aniket")).thenThrow(new UserNotFoundException("user not found"));
		mockMvc.perform(delete("/users/Aniket").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUserUpdate() throws Exception {
		userDTO.setId(1);
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		when(userService.add(userDTO)).thenReturn(userDTO);
		String json = objectMapper.writeValueAsString(userDTO);
		when(userService.update(userDTO)).thenReturn(userDTO);
		mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
	}

}
