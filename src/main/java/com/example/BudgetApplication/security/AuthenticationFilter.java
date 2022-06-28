package com.example.BudgetApplication.security;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.BudgetApplication.SpringApplicationContext;
import com.example.BudgetApplication.dto.request.UserLoginRequestModel;
import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * AuthenticationFilter class extends from UsernamePasswordAuthentication Filter. Used for filtering requests by the client.
 * 
 * @author Benjamin Chang
 *
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
	
	private final AuthenticationManager authenticationManager;

	/**
	 * AuthenticationFilter default constructor requires an AuthenticationManager to be passed in.
	 * @param authenticationManager
	 */
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Overriden method of attemptAuthentication which takes the login details from HTTP req. It returns an 'authenticated user token'
	 * if the login attempt has been successful. Else the token will be NULL.
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			UserLoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(),
					UserLoginRequestModel.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			System.out.println("Throwing the runtime exception");
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Overriden method of successfulAuthentication which sends a response back to the user with a token upon successful login.
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		String username = auth.getName();
		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();

		UserService us = (UserService) SpringApplicationContext.getBean("userService");
		User user = null;
		try {
			user = us.findByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		res.addHeader("userId", user.getUserId().toString());
	}

}