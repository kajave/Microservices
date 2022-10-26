package com.epam.xcontroller;

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

import com.epam.exception.UserNotFoundException;
import com.epam.model.UserDTO;
import com.epam.service.UserService;


@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public UserDTO add(@RequestBody UserDTO userDTO) throws UserNotFoundException {
		return userService.add(userDTO);
	}
	
	@GetMapping
	public List<UserDTO> get() throws UserNotFoundException{
		return userService.get();
	}
	
	@GetMapping("/{username}")
	public UserDTO getById(@PathVariable String username) throws UserNotFoundException {
		return userService.getUserByName(username);
	}
	
	@DeleteMapping("/{username}")
	public String delete(@PathVariable String username) throws UserNotFoundException {
		return userService.deleteById(username);
	}
	
	@PutMapping
	public UserDTO update(@RequestBody UserDTO userDTO) throws UserNotFoundException {
		return userService.update(userDTO);
	}

}
