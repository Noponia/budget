package com.example.BudgetApplication.dto.request;

/**
 * Class that contains a username and password requested by client for registeration.
 * 
 * @author Benjamin Chang
 *
 */
public class UserLoginRequestModel {
	
	private String username, password;

	public UserLoginRequestModel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public UserLoginRequestModel() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
