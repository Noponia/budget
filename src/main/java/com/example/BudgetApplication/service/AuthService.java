package com.example.BudgetApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.BudgetApplication.repository.UserRepository;

/**
 * This class implements UserDetailsService which is used to authenticate a login request.
 * 
 * @author Benjamin Chang
 *
 */
@Service
public class AuthService implements UserDetailsService {

	private UserRepository userRepository;

	/**
	 * Default constructor of the AuthService class which autowires in the UserRepository.
	 * 
	 * @param userRepository
	 */
	@Autowired
	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Overriden method of loadByUserName which searchs the UserRepository for the username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new AuthUserDetails(userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " does not exist")));
	}

}
