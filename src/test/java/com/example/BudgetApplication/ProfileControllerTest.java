package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.BudgetApplication.controller.ProfileController;
import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.service.ProfileService;
import com.example.BudgetApplication.service.UserService;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {
	
	private ProfileController pC;
	
	@Mock
	UserService mockUS;
	
	@Mock
	ProfileService mockPS;
	
	@Mock
	Authentication mockAuth;
	
	@BeforeEach
	void setup() {
		pC = new ProfileController(mockUS, mockPS);
	}
	
	@Test
	void test_add_profile() throws Exception {
		// Arrange
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");	
		when(mockAuth.getName()).thenReturn("ben");
		when(mockUS.findByUsername("ben")).thenReturn(user);
		
		// Act
		pC.addProfile(profile, mockAuth);
		
		// Assert
		verify(mockPS).addProfile(profile, user);
	}
	
	@Test
	void test_get_all_profiles() throws Exception {
		// Arrange
		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile("ben", "chang"));
		User user = new User("ben", "password");	
		when(mockAuth.getName()).thenReturn("ben");
		when(mockUS.findByUsername("ben")).thenReturn(user);
		when(mockPS.getProfiles(user)).thenReturn(profiles);
		
		// Act
		List<Profile> result = pC.getProfiles(mockAuth);
		
		// Assert
		assertEquals(result, profiles);
	}
	
	@Test
	void test_delete_profile() throws Exception {
		// Arrange
		Integer profileId = 1;
		
		// Act 
		pC.deleteProfile(1, mockAuth);
		
		// Assert
		verify(mockPS).deleteProfileById(profileId, mockAuth);
	}
}
