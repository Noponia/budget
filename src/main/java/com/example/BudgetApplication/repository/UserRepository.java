package com.example.BudgetApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BudgetApplication.entity.User;

/**
 * Repository for the User entity.
 * 
 * @author Benjamin Chang
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * Nmaed query to find a user by their username.
	 * 
	 * @param username
	 * @return Optional<User> 
	 */
	Optional<User> findByUsername(String username);

}
