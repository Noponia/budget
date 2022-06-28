package com.example.BudgetApplication.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.repository.ProfileRepository;
import com.example.BudgetApplication.repository.TransactionRepository;

/**
 * This class acts as the service layer for our Profile model.
 * 
 * @author Benjamin Chang
 *
 */
@Service
public class ProfileService {
	
	private Logger logger = LoggerFactory.getLogger(ProfileService.class);
	private ProfileRepository profileRepo;
	private TransactionRepository transactionRepo;

	/**
	 * Default constructor for Profile Service which autowires in the Profile Repository.
	 * 
	 * @param profileRepo
	 */
	@Autowired
	public ProfileService(ProfileRepository profileRepo, TransactionRepository transactionRepo) {
		this.profileRepo = profileRepo;
		this.transactionRepo = transactionRepo;
	}
	
	/**
	 * Adds a profile to the user.
	 * 
	 * @param profile
	 * @param user
	 */
	public void addProfile(Profile profile, User user) {
		profile.setUser(user);
		profileRepo.save(profile);
	}
	
	/**
	 * Gets all profiles registered underneath a user.
	 * 
	 * @param user
	 * @return
	 */
	public List<Profile> getProfiles(User user) {
		return profileRepo.findAllByUser(user);
	}
	
	/**
	 * Finds the Profile object that matches the profile Id passed.
	 * 
	 * @param profileId
	 * @return
	 * @throws Exception
	 */
	public Profile findProfileById(Integer profileId) throws Exception {
		Profile foundProfile = profileRepo.findById(profileId).orElse(null);
		
		// Check if profile was found other wise throw a error
		if(foundProfile == null) {
			logger.error("Profile ID:" + profileId + " not found");
			throw new Exception("Profile ID:" + profileId + " not found");
		} else {
			return foundProfile;
		}
		
	}
	
	/**
	 * Deletes a Profile object that matches the profileId passed.
	 * 
	 * @param profileId
	 * @throws Exception 
	 */
	public void deleteProfileById(Integer profileId, Authentication authentication) throws Exception {
		Profile foundProfile = profileRepo.findById(profileId).orElse(null);
		
		// Check if the profile was found else throw a error
		if(foundProfile == null) {
			logger.error("Profile ID: " + profileId + " not found");
			throw new Exception("Profile ID: " + profileId + " not found");
		} 
	
		// Verify that the user deleting profile has rights to the profile, if no rights throw an error
		if(authentication.getName().equals(foundProfile.getUser().getUsername())) {
			// We find all the transactions that are under the Profile using the TransactionRepository and then delete them all in batch
			transactionRepo.deleteAll(transactionRepo.findAllTransactionsByProfileId(profileId));
			profileRepo.deleteById(profileId);
		} else {
			logger.error(authentication.getName() + " has no permission to delete profile");
			throw new Exception("Not permitted to delete profile");
		}
		
	}
	
}