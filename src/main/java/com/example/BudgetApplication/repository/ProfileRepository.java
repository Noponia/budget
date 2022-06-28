package com.example.BudgetApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.entity.User;

/**
 * Repository for the Profile entity.
 * 
 * @author Benjamin Chang
 *
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>  {
	
	/**
	 * Named query to find all profiles registered under a user.
	 * 
	 * @param user
	 * @return list of profiles that match the query
	 */
	public List<Profile> findAllByUser(User user);
}
