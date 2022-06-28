package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.repository.ProfileRepository;
import com.example.BudgetApplication.repository.TransactionRepository;
import com.example.BudgetApplication.service.ProfileService;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
	
	private ProfileService pS;
	
	@Mock
	ProfileRepository mockPR;
	
	@Mock
	TransactionRepository mockTR;
	
	@Mock
	Authentication mockAuth;
	
	@BeforeEach
	void setup() {
		pS = new ProfileService(mockPR, mockTR);
	}
	
	@Test
	void test_add_profile() {
		// Arrange
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");
		
		// Act
		pS.addProfile(profile, user);
		
		// Assert
		verify(mockPR).save(profile);
	}
	
	@Test
	void test_get_profiles() {
		// Arrange
		List<Profile> foundProfiles = new ArrayList<>();
		User user = new User("ben", "password");
		Profile profile = new Profile("ben", "chang");
		foundProfiles.add(profile);
		when(mockPR.findAllByUser(user)).thenReturn(foundProfiles);
		
		// Act
		List<Profile> result = pS.getProfiles(user);
		
		// Assert
		assertEquals(foundProfiles, result);
	}
	
	@Test
	void test_find_profile_by_id() throws Exception {
		// Arrange 
		Integer profileId = 1;
		Profile profile = new Profile("ben", "chang");
		Optional<Profile> foundProfile = Optional.of(profile);
		when(mockPR.findById(profileId)).thenReturn(foundProfile);

		// Act
		Profile result = pS.findProfileById(profileId);
		
		// Assert
		assertEquals(profile, result);
	}
	
	@Test
	void test_find_profile_by_id_errors_when_no_profile_exists() throws Exception {
		// Arrange 
		Integer profileId = 1;
		Optional<Profile> foundProfile = Optional.ofNullable(null);
		when(mockPR.findById(profileId)).thenReturn(foundProfile);

		// Act
		Exception e =  assertThrows(Exception.class, () -> pS.findProfileById(profileId));
		
		// Assert
		assertEquals("Profile ID:1 not found", e.getMessage());
	}
	
	@Test
	void test_delete_profile_by_id_profile_not_found() {
		// Arrange
		Integer profileId = 1;
		when(mockPR.findById(profileId)).thenReturn(Optional.ofNullable(null));
		
		// Act
		Exception e =  assertThrows(Exception.class, () -> pS.deleteProfileById(profileId, mockAuth));
		
		// Assert
		assertEquals("Profile ID: 1 not found", e.getMessage());
	}
	
	@Test
	void test_delete_profile_by_id_profile_found() throws Exception {
		// Arrange
		Integer profileId = 1;
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");
		profile.setUser(user);
		Optional<Profile> foundProfile = Optional.of(profile);
		when(mockPR.findById(profileId)).thenReturn(foundProfile);
		when(mockAuth.getName()).thenReturn("ben");
		
		// Act
		pS.deleteProfileById(profileId, mockAuth);
		
		// Assert
		verify(mockPR).deleteById(profileId);
	}
	
	@Test
	void test_no_permission_to_delete_profile_by_id() throws Exception {
		// Arrange
		Integer profileId = 1;
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");
		profile.setUser(user);
		Optional<Profile> foundProfile = Optional.of(profile);
		when(mockPR.findById(profileId)).thenReturn(foundProfile);
		when(mockAuth.getName()).thenReturn("notben");
		
		// Act
		Exception e = assertThrows(Exception.class, () -> pS.deleteProfileById(profileId, mockAuth));
		
		// Assert
		assertEquals("Not permitted to delete profile", e.getMessage());
	}
}
