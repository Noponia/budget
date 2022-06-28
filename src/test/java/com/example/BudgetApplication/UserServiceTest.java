package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.repository.UserRepository;
import com.example.BudgetApplication.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	private UserService uS;
	
	@Mock
	UserRepository mockUR;
	
	@Mock
	PasswordEncoder mockPE;
	
	@BeforeEach
	void setup() {
		uS = new UserService(mockUR, mockPE);
	}
	
	@Test
	void test_add_user() {
		// Arrange
		User user = new User("ben", "password");

		// Act
		uS.addUser(user);
		
		// Assert
		verify(mockPE).encode("password");
		verify(mockUR).save(user);
	}
	
	@Test
	void test_find_user_by_id() throws Exception {
		// Arrange
		Integer userId = 1;
		Optional<User> user = Optional.ofNullable(new User("ben", "password"));

		when(mockUR.findById(userId)).thenReturn(user);
		
		// Act
		Optional<User> result = Optional.ofNullable(uS.findUserById(userId));
		
		// Assert
		assertEquals(user, result);
	}
	
	@Test
	void test_does_not_find_user_by_id() throws Exception {
		// Arrange
		Integer userId = 1;

		when(mockUR.findById(userId)).thenReturn(Optional.ofNullable(null));
		
		// Act
		Exception e = assertThrows(Exception.class, () -> uS.findUserById(userId));
		
		// Assert
		assertEquals("User ID: 1 not found", e.getMessage());
	}
	
	@Test
	void test_find_user_by_username() throws Exception {
		// Arrange
		String username = "ben";
		Optional<User> user = Optional.ofNullable(new User("ben", "password"));		
		
		when(mockUR.findByUsername(username)).thenReturn(user);
		
		// Act 
		Optional<User> result = Optional.ofNullable(uS.findByUsername(username));
		
		// Assert
		assertEquals(user, result);
	}
	
	@Test
	void test_does_not_find_user_by_username() throws Exception {
		// Arrange
		String username = "ben";
		
		when(mockUR.findByUsername(username)).thenReturn(Optional.ofNullable(null));
		
		// Act 
		Exception e = assertThrows(Exception.class, () -> uS.findByUsername(username));
		
		// Assert
		assertEquals("Username: " + username + " not found", e.getMessage());
	}
}
