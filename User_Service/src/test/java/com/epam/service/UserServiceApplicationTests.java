package com.epam.service;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.exception.UserNotFoundException;
import com.epam.model.User;
import com.epam.model.UserDTO;
import com.epam.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceApplicationTests {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	@Mock
	ModelMapper modelMapper;

	UserDTO userDTO;
	User user;

	@BeforeEach
	void set() {
		userDTO = new UserDTO();
		userDTO.setEmail("aniket@gmail.com");
		userDTO.setUsername("Aniket");
		user = new User();
		user.setEmail("aniket@gmail.com");
		user.setName("Aniket");
		
	}

	@Test
	void testAddUser() throws UserNotFoundException {

		when(modelMapper.map(userDTO, User.class)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
		assertEquals(userDTO.getUsername(), userService.add(userDTO).getUsername());
	}

	@Test
	void testUserNotAdded() throws UserNotFoundException {

		when(userRepository.save(user)).thenThrow(new UserNotFoundException("User can not be added"));
		assertThrows(UserNotFoundException.class, () -> userService.add(userDTO));
	}
	
	
	@Test
	void testGetUsers() throws UserNotFoundException
	{
		List<UserDTO> list=Arrays.asList(userDTO);
		List<User> users=Arrays.asList(user);
		when(userRepository.findAll()).thenReturn(users);
		when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
		assertEquals(list.size(),userService.get().size());
	}
	
	@Test
	void testUsersNotGet() throws UserNotFoundException
	{
		when(userRepository.findAll()).thenThrow(new UserNotFoundException("user is empty"));
		assertThrows(UserNotFoundException.class, () -> userService.get());
	}
	
	@Test
	void testGetUserByName() throws UserNotFoundException
	{
		when(userRepository.findByUsername("Ayush")).thenReturn(Optional.of(user));
		when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
		assertEquals(user.getEmail(),userService.getUserByName("Ayush").getEmail());
	}
	@Test
	void testUserNotGetByName() throws UserNotFoundException
	{
		when(userRepository.findByUsername("Ayush")).thenThrow(new UserNotFoundException("User is not present"));
		assertThrows(UserNotFoundException.class, () -> userService.getUserByName("Ayush"));
	}

	@Test
	void testUpdateUser()throws UserNotFoundException
	{
		userDTO.setUsername("ayush");
		when(modelMapper.map(userDTO, User.class)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
		assertEquals(userDTO.getUsername(), userService.update(userDTO).getUsername());
	}
	
	@Test
	void testDeleteUser()throws UserNotFoundException
	{
		when(userRepository.deleteByUsername("Ayush")).thenReturn(1);
		assertEquals("User deleted Succesfully", userService.deleteById("Ayush"));
	}
	
	@Test
	void testUserNotDeleted()
	{
		when(userRepository.deleteByUsername("Ayush")).thenThrow(new  UserNotFoundException("User not found"));
		assertThrows(UserNotFoundException.class, () -> userService.deleteById("Ayush"));
	}

}
