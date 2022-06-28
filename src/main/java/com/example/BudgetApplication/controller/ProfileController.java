package com.example.BudgetApplication.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.service.ProfileService;
import com.example.BudgetApplication.service.UserService;

/**
 * Rest Controller for profiles which supports API calls from 'http://localhost:3000'
 * API to be called using 'http://localhost:8088/budget/api/{mapping}
 * 
 * @author Benjamin Chang
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api")
public class ProfileController {
	private Logger logger = LoggerFactory.getLogger(ProfileController.class);
	private ProfileService profileService;		
	private UserService userService;			 
	
	/**
	 * Constructor for profile controller, Spring boot to autowire UserService and ProfileService dependencies.
	 * 
	 * @param userService
	 * @param profileService
	 */
	@Autowired
	public ProfileController(UserService userService, ProfileService profileService) {
		this.userService = userService;
		this.profileService = profileService;
	}
	
	/**
	 * API to allow users to add a profile, called using:
	 * http://localhost:8088/budget/api/profile/add
	 * 
	 * @param profile
	 * @param authentication
	 * @throws Exception
	 */
	@PostMapping("profile/add") 
	public void addProfile(@RequestBody Profile profile, Authentication authentication) throws Exception {
		profileService.addProfile(profile, userService.findByUsername(authentication.getName()));
		logger.info("ProfileID: " + profile.getProfileId() + " successfully added");
	}
	
	/**
	 * API to allow users to get all the profiles associated with them, called using:
	 * http://localhost:8088/budget/api/profile/all
	 * 
	 * @param authentication
	 * @return all profiles associated with the user
	 * @throws Exception
	 */
	@GetMapping("profile/all")
	public List<Profile> getProfiles(Authentication authentication) throws Exception {
		return profileService.getProfiles(userService.findByUsername(authentication.getName()));
	}
	
	/**
	 * API to allow users to delete a single profile called using:
	 * http://localhost:8088/budget/api/profile/delete/{profileId}
	 * 
	 * @param profileId
	 * @param authentication
	 * @throws Exception 
	 */
	@DeleteMapping("profile/delete/{profileId}")
	public void deleteProfile(@PathVariable Integer profileId, Authentication authentication) throws Exception {
		profileService.deleteProfileById(profileId, authentication);
		logger.info("ProfileID:" + profileId + " successfully deleted");
	}
}
