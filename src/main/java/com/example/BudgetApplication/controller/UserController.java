package com.example.BudgetApplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.service.UserService;

/**
 * Rest Controller for users which supports API calls from 'http://localhost:3000'
 * API to be called using 'http://localhost:8088/budget/api/{mapping}
 * 
 * @author Benjamin Chang
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(ProfileController.class);
	private UserService userService;
	
	/**
	 * Constructor for transaction controller, Spring boot to autowire UserService dependencies.
	 * @param userService
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * API to allow users to register, called using:
	 * http://localhost:8088/budget/api/register
	 * 
	 * @param user
	 */
	@PostMapping("/register")
	public void register(@RequestBody User user) {
		userService.addUser(user);
		logger.info(user.getUsername() + " has registered succesfully");
	}
}
