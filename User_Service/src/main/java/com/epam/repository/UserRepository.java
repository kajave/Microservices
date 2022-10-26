package com.epam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.epam.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
	
	@Modifying
	@Query("delete from User u where u.username=?1")
	int deleteByUsername(String username);
}
