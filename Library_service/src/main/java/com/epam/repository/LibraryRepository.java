package com.epam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.epam.model.Library;
import com.epam.model.LibraryDTO;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

	@Modifying
	@Query("delete from Library l where l.username=?1 and l.bookId=?2")
	int deleteByUsernameAndBookId(String username, int bookId);

	@Query("select bookId from Library l where l.username=?1")
	List<Integer> findBookIdByUsername(String username);

	Library findByUsername(String username);

	@Query("select l from Library l where l.username=?1 and l.bookId=?2")
	Library findByUsernameAndBookId(String username, int bookId);
}
