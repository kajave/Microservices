package com.epam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.exception.UserNotFoundException;
import com.epam.model.User;
import com.epam.model.UserDTO;
import com.epam.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	ModelMapper mapper;

	@Autowired
	UserRepository userRepository;

	public UserDTO add(UserDTO userDTO) throws UserNotFoundException {

		User user = mapper.map(userDTO, User.class);
		try {
			user = userRepository.save(user);
		} catch (RuntimeException e) {
			throw new UserNotFoundException("user is not added succesfully");
		}

		return mapper.map(user, UserDTO.class);
	}

	
	public List<UserDTO> get() throws UserNotFoundException {
		List<User> users;
		try {
			users = userRepository.findAll();
		} catch (RuntimeException e) {
			throw new UserNotFoundException("user is empty");
		}
		return users.stream().map(b -> mapper.map(b, UserDTO.class)).collect(Collectors.toList());
	}

	
	public UserDTO getUserByName(String username) throws UserNotFoundException {
		Optional<User> users;
		try {
			users = userRepository.findByUsername(username);
		} catch (RuntimeException e) {
			throw new UserNotFoundException("user is not present");
		}
		User user = users.orElseThrow(() -> new UserNotFoundException("user is not present"));
		return mapper.map(user, UserDTO.class);
	}
	
	@Transactional
	public String deleteById(String username) throws UserNotFoundException {
		try {
			 userRepository.deleteByUsername(username);
		} catch (RuntimeException e) {
			throw new UserNotFoundException("user is empty");
		}
		return "User deleted Succesfully";
	}
	
	
	public UserDTO update(UserDTO userDTO) throws UserNotFoundException {

		User user = mapper.map(userDTO, User.class);
		try {
			user = userRepository.save(user);
		} catch (RuntimeException e) {
			throw new UserNotFoundException("user is not update succesfully");
		}

		return mapper.map(user, UserDTO.class);
	}
}
