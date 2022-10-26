package com.epam.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.epam.model.UserDTO;

@FeignClient(name = "user-service")
@LoadBalancerClient(name = "user-service")
public interface UserClient {

	@GetMapping("/users")
	public List<UserDTO> getUsers();

	@GetMapping("/users/{username}")
	public UserDTO getUser(@PathVariable("username") String userName);

	@PostMapping("/users")
	public UserDTO addUser(UserDTO userDto);

	@PutMapping("/users/")
	public UserDTO update(UserDTO userDto);
		
	@DeleteMapping("/users/{username}")
	public String delete(@PathVariable("username") String userName);



}
