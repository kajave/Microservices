package com.epam.controller;

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

import com.epam.client.BookClient;
import com.epam.client.UserClient;
import com.epam.exception.LibraryException;
import com.epam.model.BookDTO;
import com.epam.model.LibraryDTO;
import com.epam.model.UserDTO;
import com.epam.service.LibraryService;

@RestController
@RequestMapping("/library/user")
public class LibraryUserController {

	@Autowired
	LibraryService libraryService;

	@Autowired
	UserClient userClient;

	@Autowired
	BookClient bookClient;

	@GetMapping
	public List<UserDTO> getUsers() {
		return userClient.getUsers();
	}

	@GetMapping("/{username}")
	public List<BookDTO> getUserByName(@PathVariable String username) throws LibraryException {
		List<Integer> bookIds = libraryService.listOfBookId(username);
		return bookIds.stream().map(id -> bookClient.getBook(Integer.toString(id))).toList();
	}

	@DeleteMapping("/{username}")
	public String deleteUser(@PathVariable String username) {
		return userClient.delete(username);
	}

	@PostMapping
	public UserDTO addUser(@RequestBody UserDTO userDTO) {
		return userClient.addUser(userDTO);
	}

	@PutMapping
	public UserDTO updateUser(@RequestBody UserDTO userDTO) {
		return userClient.update(userDTO);
	}

	@PostMapping("/{username}/books/{bookid}")
	public LibraryDTO addAssociation(@PathVariable String username, @PathVariable String bookid)
			throws NumberFormatException, LibraryException {
		userClient.getUser(username);
		bookClient.getBook(bookid);
		return libraryService.addBookToUser(username, Integer.parseInt(bookid));
	}

	@DeleteMapping("/{username}/books/{bookid}")
	public String deleteAssociation(@PathVariable String username, @PathVariable String bookid)
			throws NumberFormatException, LibraryException {
		return libraryService.delete(username, Integer.parseInt(bookid));
	}

}
