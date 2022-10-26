package com.epam.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.exception.LibraryException;
import com.epam.model.BookDTO;
import com.epam.model.Library;
import com.epam.model.LibraryDTO;
import com.epam.model.UserDTO;
import com.epam.repository.LibraryRepository;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	ModelMapper mapper;

	public LibraryDTO addBookToUser(String username, int bookid) throws LibraryException {
		Library library = new Library(username, bookid);
		try {
			library = libraryRepository.save(library);
		} catch (RuntimeException e) {
			throw new LibraryException("not added association");
		}
		return mapper.map(library, LibraryDTO.class);
	}

//	@Transactional
//	public String delete(String username, int bookid) throws LibraryException {
//		Optional<Library> optional = Optional.ofNullable(libraryRepository.findByUsernameAndBookId(username, bookid));
//		optional.orElseThrow(() -> new LibraryException(username + " dont have an book whose ID is " + bookid));
//		try {
//			libraryRepository.deleteByUsernameAndBookId(username, bookid);
//		} catch (RuntimeException e) {
//			throw new LibraryException("not added association");
//		}
//		return "Succesfully delete association";
//	}

	@Transactional
	public String delete(String username, int bookid) throws LibraryException {
		if (libraryRepository.findByUsernameAndBookId(username, bookid) == null) {
			throw new LibraryException(username + " dont have an book whose ID is " + bookid);
		}
		try {
			libraryRepository.deleteByUsernameAndBookId(username, bookid);
		} catch (RuntimeException e) {
			throw new LibraryException("not added association");
		}
		return "Succesfully delete association";
	}

	public List<Integer> listOfBookId(String username) throws LibraryException {
		List<Integer> bookIds = libraryRepository.findBookIdByUsername(username);
		if (bookIds.isEmpty()) {
			throw new LibraryException("book is not associated for " + username);
		}
		return bookIds;
	}
}
