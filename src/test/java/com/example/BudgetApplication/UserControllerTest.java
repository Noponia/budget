package com.example.BudgetApplication;

import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.BudgetApplication.controller.UserController;
import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	private UserController uC;
	
	@Mock
	UserService mockUS;
	
	@Mock
	HttpServletRequest mockReq;
	@Mock
	SecurityContextHolder mockSCH;
	
	@BeforeEach
	void setup() {
		uC = new UserController(mockUS);
	}
	
	@Test
	void test_register() {
		// Arrange
		User user = new User("ben", "password");
		
		// Act
		uC.register(user);
		
		// Assert
		verify(mockUS).addUser(user);
	}
}
