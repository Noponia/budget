package com.example.BudgetApplication;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.entity.Expense;
import com.example.BudgetApplication.entity.Income;
import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.repository.TransactionRepository;
import com.example.BudgetApplication.repository.UserRepository;
import com.example.BudgetApplication.repository.ProfileRepository;

/**
 * DataLoader class which implements ApplicationRunner to generate sample data for testing purposes.
 * 
 * @author Benjamin Chang
 *
 */
@Component
public class DataLoader implements ApplicationRunner {
	
	private TransactionRepository transactionRepository;
	private ProfileRepository profileRepository;
	private UserRepository userRepository;
	
	/**
	 * Default constructor of the DataLoader class. Spring to autowire require repositories to save sample data to the database.
	 * 
	 * @param transactionRepository
	 * @param userRepository
	 * @param profileRepository
	 */
	@Autowired
	public DataLoader(
			TransactionRepository transactionRepository, 
			UserRepository userRepository, 
			ProfileRepository profileRepository) {
		this.transactionRepository = transactionRepository;
		this.profileRepository = profileRepository;
		this.userRepository = userRepository;
	}
	
	/**
	 * Overrided method of ApplicationRunner, used to save sample data.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Expense expense1 = new Expense("Clothes", "Shirt", 10, Date.valueOf("2020-03-03"));
		Expense expense2 = new Expense("Food", "Restaurant", 40, Date.valueOf("2020-04-03"));
		Expense expense3 = new Expense("Food", "Restaurant", 50, Date.valueOf("2020-05-03"));
		Expense expense4 = new Expense("Bill", "Water", 200, Date.valueOf("2020-06-03"));
		Expense expense5 = new Expense("Clothes", "Pants", 30, Date.valueOf("2020-07-03"));
		Expense expense6 = new Expense("Entertainment", "Netflix Subscription", 10, Date.valueOf("2020-08-03"));
		Expense expense7 = new Expense("Entertainment", "Spotify Subscription", 10, Date.valueOf("2020-09-03"));
		Expense expense8 = new Expense("Groceries", "Coles", 100, Date.valueOf("2020-10-03"));
		Expense expense9 = new Expense("Clothes", "Shirt", 40, Date.valueOf("2020-11-03"));
		Expense expense10 = new Expense("Entertainment", "Games", 50, Date.valueOf("2020-12-03"));
		Expense expense11 = new Expense("Bill", "Gas", 300, Date.valueOf("2021-01-03"));
		Income income1 = new Income("Job", "Main job", 2000, Date.valueOf("2021-02-03"));
		Income income2 = new Income("Gift", "Free money", 1000, Date.valueOf("2021-03-03"));
		Income income3 = new Income("Job", "Side job", 500, Date.valueOf("2021-04-03"));
		Income income4 = new Income("Job", "Main job", 1500, Date.valueOf("2021-05-03"));
		Income income5 = new Income("Job", "Main job", 2000, Date.valueOf("2021-06-03"));
		
		User user1 = new User("admin", "$2a$10$hcUpdZx6wonxuan3U99mP.cor4m9zNYlDYwpBYYfmvTtxEPgpHWAK");
		
		Profile profile1 = new Profile("Ben", "Chang");
		profile1.setUser(user1);
		Profile profile2 = new Profile("Bob", "Builder");
		profile2.setUser(user1);
		
		expense1.setProfile(profile1);
		expense2.setProfile(profile1);
		expense3.setProfile(profile2);
		expense4.setProfile(profile2);
		expense5.setProfile(profile1);
		expense6.setProfile(profile1);
		expense7.setProfile(profile1);
		expense8.setProfile(profile1);
		expense9.setProfile(profile1);
		expense10.setProfile(profile1);
		expense11.setProfile(profile1);

		income1.setProfile(profile1);
		income2.setProfile(profile1);
		income3.setProfile(profile1);
		income4.setProfile(profile1);
		income5.setProfile(profile1);

		
		userRepository.save(user1);
		
		profileRepository.save(profile1);
		profileRepository.save(profile2);

		transactionRepository.save(expense1);
		transactionRepository.save(expense2);
		transactionRepository.save(expense3);
		transactionRepository.save(expense4);
		transactionRepository.save(expense5);
		transactionRepository.save(expense6);
		transactionRepository.save(expense7);
		transactionRepository.save(expense8);
		transactionRepository.save(expense9);
		transactionRepository.save(expense10);
		transactionRepository.save(expense11);
		transactionRepository.save(income1);
		transactionRepository.save(income2);
		transactionRepository.save(income3);
		transactionRepository.save(income4);
		transactionRepository.save(income5);
	}
}
