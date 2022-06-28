package com.example.BudgetApplication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BudgetApplication.controller.ProfileController;
import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.repository.UserRepository;

/**
 * Default constructor for User Service which autowires in the User Repostiory.

 * 
 * @author Benjamin Chang
 *
 */
@Service
public class UserService {
	
	private Logger logger = LoggerFactory.getLogger(ProfileController.class);
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;

	/**
	 * Default constructor for Transaction Service which autowires in the Transaction Repostiory and PasswordEncoder.
	 * 
	 * @param userRepo
	 * @param passwordEncoder
	 */
	@Autowired
	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * Method to add user to the database.
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		userRepo.save(user);
	}
	
	/**
	 * Method to find a user by its userId.
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User findUserById(Integer userId) throws Exception {
		User user =  userRepo.findById(userId).orElse(null);
		if(user != null) {
			return user;
		} else {
			logger.error("User ID: " + userId + " not found");
			throw new Exception("User ID: " + userId + " not found");
		}
	}
	
	/**
	 * Method to find a user by their username.
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User findByUsername(String username) throws Exception {
		User user = userRepo.findByUsername(username).orElse(null);
		if(user != null) {
			return user;
		} else {
			logger.error("Username: " + username + " not found");
			throw new Exception("Username: " + username + " not found");
		}
	}
	
}
