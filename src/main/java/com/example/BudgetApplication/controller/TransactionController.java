package com.example.BudgetApplication.controller;

import java.util.List;

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

import com.example.BudgetApplication.entity.Expense;
import com.example.BudgetApplication.entity.Income;
import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.entity.Transaction;
import com.example.BudgetApplication.service.ProfileService;
import com.example.BudgetApplication.service.TransactionService;
import com.example.BudgetApplication.service.UserService;

/**
 * Rest Controller for transactions which supports API calls from 'http://localhost:3000'
 * API to be called using 'http://localhost:8088/budget/api/{mapping}
 * 
 * @author Benjamin Chang
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api")
public class TransactionController {
	
	private TransactionService transactionService;
	private ProfileService profileService;
	private UserService userService;

	/**
	 * Constructor for transaction controller, Spring boot to autowire UserService, TransactionService and ProfileService dependencies.
	 * 
	 * @param transactionService
	 * @param profileService
	 */
	@Autowired
	public TransactionController(TransactionService transactionService, ProfileService profileService, UserService userService) {
		this.transactionService = transactionService;
		this.profileService = profileService;
		this.userService = userService;
	}
	
	/**
	 * API to allow users to find the Transaction using its Id, called using:
	 * http://localhost:8088/budget/api/transaction/{transactionId}
	 * 
	 * @param id
	 * param authentication
	 * @return Transaction object queried
	 * @throws Exception when ID not found in the Transaction table
	 */
	@GetMapping("/transaction/{transactionId}")
	public Transaction getTransactionById(@PathVariable Integer transactionId, Authentication authentication) throws Exception {
		return transactionService.getTransactionById(transactionId, authentication);
	}
	
	/**
	 * API to allow users to find all transactions for a single profile, called using:
	 * http://localhost:8088/budget/api/transaction/{transactionId}
	 * 
	 * @param profileId
	 * @param authentication
	 * @return all the transactions by profile queried
	 * @throws Exception 
	 */
	@GetMapping("/transaction/all/{profileId}")
	public List<Transaction> getAllTransactionsByProfileId(@PathVariable Integer profileId, Authentication authentication) throws Exception {
		return transactionService.getAllTransactionsByProfileId(profileId, authentication);
	}
	
	/**
	 * API to allow users to find all transactions for all profiles, called using:
	 * http://localhost:8088/budget/api/transaction/all
	 * 
	 * @param authentication
	 * @return all transactions under a single user
	 * @throws Exception
	 */
	@GetMapping("/transaction/all")
	public List<Transaction> getAllTransactions(Authentication authentication) throws Exception {
		List<Profile> foundProfiles = profileService.getProfiles(userService.findByUsername(authentication.getName()));		
		return transactionService.getAllTransactions(foundProfiles);
	}
	
	/**
	 * API to allow users to find all income transactions under a single user, called using:
	 * http://localhost:8088/budget/api/transaction/all-income
	 * 
	 * @param authentication
	 * @return all income transactions under a single user
	 * @throws Exception
	 */
	@GetMapping("/transaction/all-income")
	public List<Transaction> getAllIncome(Authentication authentication) throws Exception {
		List<Profile> foundProfiles = profileService.getProfiles(userService.findByUsername(authentication.getName()));	
		return transactionService.getAllIncome(foundProfiles);
	}
	
	
	/**
	 * API to allow users to find all expense transactions under a single user, called using:
	 * http://localhost:8088/budget/api/transaction/all-expense 
	 * 
	 * @param authentication
	 * @return all expense transactions under a single user
	 * @throws Exception
	 */
	@GetMapping("/transaction/all-expense")
	public List<Transaction> getAllExpense(Authentication authentication) throws Exception {
		List<Profile> foundProfiles = profileService.getProfiles(userService.findByUsername(authentication.getName()));	
		return transactionService.getAllExpense(foundProfiles);
	}
	
	/**
	 * Write to the Transaction table for an income submission by a Profile, called using:
	 * http://localhost:8088/budget/api/transaction/add-income/{profileId}
	 * 
	 * @param income
	 * @param profileId
	 * @param authentication
	 * @throws Exception when the User ID not found
	 */
	@PostMapping("/transaction/add-income/{profileId}")
	public void addIncome(@RequestBody Income income, @PathVariable Integer profileId, Authentication authentication) throws Exception {
		transactionService.addIncome(income, profileService.findProfileById(profileId), authentication);
	}
	
	/**
	 * Write to the Transaction table for an expense submission by a Profile, called using:
	 * http://localhost:8088/budget/api/transaction/add-expense/{profileId}
	 * 
	 * @param expense
	 * @param profileId
	 * @param authentication
	 * @throws Exception
	 */
	@PostMapping("/transaction/add-expense/{profileId}")
	public void addExpense(@RequestBody Expense expense, @PathVariable Integer profileId, Authentication authentication) throws Exception {
		transactionService.addExpense(expense, profileService.findProfileById(profileId), authentication);
	}
	
	/**
	 * Delete a Transaction by its ID value, called using:
	 * http://localhost:8088/budget/api/transaction/delete/{transactionId}
	 * 
	 * @param transactionId
	 * @param authentication
	 * @throws Exception 
	 */
	@DeleteMapping("/transaction/delete/{transactionId}")
	public void deleteTransactionById(@PathVariable Integer transactionId, Authentication authentication) throws Exception {
		transactionService.deleteTransactionById(transactionId, authentication);
	}
	
	/**
	 * Update a Transaction by its ID, called using:
	 * http://localhost:8088/budget/api/transaction/update/{transactionId}
	 * 
	 * @param transaction
	 * @param authentication
	 * @throws Exception 
	 */
	@PostMapping("/transaction/update/{transactionId}")
	public void updateTransaction(@RequestBody Transaction transaction, Authentication authentication) throws Exception {
		transactionService.updateTransaction(transaction, authentication);
	}
}
