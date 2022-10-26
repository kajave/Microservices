package com.epam.service;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.exception.LibraryException;
import com.epam.model.Library;
import com.epam.model.LibraryDTO;
import com.epam.repository.LibraryRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LibraryServiceTest {

	@Mock
	LibraryRepository libraryRepository;

	@InjectMocks
	LibraryService libraryService;

	@Mock
	ModelMapper mapper;

	@Test
	void test_addAssociation_succesful() throws LibraryException {
		Library library = new Library("aniket", 1);
		LibraryDTO dto = new LibraryDTO();
		dto.setUsername("aniket");
		dto.setBookId(1);
		when(libraryRepository.save(any(Library.class))).thenReturn(library);
		 when(mapper.map(library, LibraryDTO.class)).thenReturn(dto);
		assertEquals(dto, libraryService.addBookToUser("aniket", 1));
	}
	
	@Test
	void test_addAssociation_fail() throws LibraryException {
		Library library = new Library("aniket", 1);
		LibraryDTO dto = new LibraryDTO();
		dto.setUsername("aniket");
		dto.setBookId(1);
		when(libraryRepository.save(library)).thenThrow(RuntimeException.class);
		assertThrows(LibraryException.class, () -> libraryService.addBookToUser("aniket", 1));
	}

	@Test
	void test_deleteAssociation_pass() throws LibraryException {
		Library library = new Library();
		when(libraryRepository.findByUsernameAndBookId(anyString(), anyInt())).thenReturn(library);
		when(libraryRepository.deleteByUsernameAndBookId(anyString(), anyInt())).thenReturn(1);
		assertEquals("Succesfully delete association", libraryService.delete("aniket", 1));
	}

	@Test
	void test_deleteAssociation_fail() throws LibraryException {
		Library library = new Library();
		when(libraryRepository.findByUsernameAndBookId(anyString(), anyInt())).thenReturn(null);
		assertThrows(LibraryException.class, () -> libraryService.delete("aniket", 1));

		when(libraryRepository.findByUsernameAndBookId(anyString(), anyInt())).thenReturn(library);
		when(libraryRepository.deleteByUsernameAndBookId(anyString(), anyInt())).thenThrow(RuntimeException.class);
		assertThrows(LibraryException.class, () -> libraryService.delete("aniket", 1));
	}
	
	@Test
	void test_listOfBookId_getSuccesfully() throws LibraryException {
		when(libraryRepository.findBookIdByUsername(anyString())).thenReturn(Arrays.asList(1,2));
		assertEquals(2, libraryService.listOfBookId("aniket").size());
	}
	
	@Test
	void test_listOfBookId_getException() throws LibraryException {
		when(libraryRepository.findBookIdByUsername(anyString())).thenReturn(Arrays.asList());
		assertThrows(LibraryException.class, ()->libraryService.listOfBookId("aniket"));
	}

}
