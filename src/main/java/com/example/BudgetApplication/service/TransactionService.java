package com.example.BudgetApplication.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.BudgetApplication.entity.Expense;
import com.example.BudgetApplication.entity.Income;
import com.example.BudgetApplication.entity.Transaction;
import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.repository.ProfileRepository;
import com.example.BudgetApplication.repository.TransactionRepository;

/**
 * This class acts as the service layer for our Transaction model.
 * 
 * @author Benjamin Chang
 *
 */
@Service
public class TransactionService {
	
	private Logger logger = LoggerFactory.getLogger(TransactionService.class);
	private TransactionRepository transactionRepo;
	private ProfileRepository profileRepo;

	/**
	 * Default constructor for Transaction Service which autowires in the Transaction Repository.
	 * 
	 * @param transactionRepo
	 */
	@Autowired
	public TransactionService(TransactionRepository transactionRepo, ProfileRepository profileRepo) {
		this.transactionRepo = transactionRepo;
		this.profileRepo = profileRepo;
	}
	
	/**
	 * This method is used to add an income type transaction to a profile.
	 * 
	 * @param income
	 * @param profile
	 * @param authentication
	 * @throws Exception 
	 */
	public void addIncome(Income income, Profile profile, Authentication authentication) throws Exception {
		// Here we need to check if the user has rights to add income to a profile
		if(authentication.getName().equals(profile.getUser().getUsername())) {
			income.setProfile(profile);
			income.setType("Income");
			transactionRepo.save(income);
		} else {
			logger.error(authentication.getName() + " does not have access");
			throw new Exception("Not permitted to add transaction");
		}
		
	}
	
	/**
	 * This method is used to add an expense type transaction to a profile.
	 * 
	 * @param expense
	 * @param profile
	 * @param authentication
	 * @throws Exception 
	 */
	public void addExpense(Expense expense, Profile profile, Authentication authentication) throws Exception {
		// Here we need to check if the user has rights to add expense to a profile
		if(authentication.getName().equals(profile.getUser().getUsername())) {
			expense.setProfile(profile);
			expense.setType("Expense");
			transactionRepo.save(expense);
		} else {
			logger.error(authentication.getName() + " does not have access");
			throw new Exception("Not permitted to add transaction");
		}
		
	}
	
	/**
	 * This method is used to delete a transaction by using its transactionId.
	 * 
	 * @param transactionId
	 * @param authentication
	 * @throws Exception 
	 */
	public void deleteTransactionById(Integer transactionId, Authentication authentication) throws Exception {
		Transaction foundTransaction = transactionRepo.findById(transactionId).orElse(null);

		if(foundTransaction == null) {
			throw new Exception("Transaction not found");
		}

		// Here we need to check if user has rights to delete a transaction
		if(foundTransaction.getProfile().getUser().getUsername().equals(authentication.getName())) {
			transactionRepo.deleteById(transactionId);
		} else {
			logger.error(authentication.getName() + " does not have access");
			throw new Exception("Not permitted to delete transaction");
		}
	}
	
	/**
	 * This method is used to update a transaction
	 * 
	 * @param transaction
	 * @param authentication
	 * @throws Exception
	 */
	public void updateTransaction(Transaction transaction, Authentication authentication) throws Exception {
		Transaction foundTransaction = transactionRepo.findById(transaction.getTransactionId()).orElse(null);
		
		if(foundTransaction == null) {
			throw new Exception("Transaction not found");
		}
		
		// Here we need to check if the user has rights to update a transaction
		if(foundTransaction.getProfile().getUser().getUsername().equals(authentication.getName())) {
			foundTransaction.setAmount(transaction.getAmount());
			foundTransaction.setCategory(transaction.getCategory());
			foundTransaction.setDate(transaction.getDate());
			foundTransaction.setDescription(transaction.getDescription());
			transactionRepo.save(foundTransaction);
		} else {
			logger.error(authentication.getName() + " does not have access");
			throw new Exception("Not permitted to update transaction");
		}
	}
	
	/**
	 * This method is used to get a transaction by using its transactionId.
	 * 
	 * @param transactionId
	 * @param authentication
	 * @return
	 * @throws Exception
	 */
	public Transaction getTransactionById(Integer transactionId, Authentication authentication) throws Exception {
		Transaction transaction = transactionRepo.findById(transactionId).orElse(null);

		if(transaction == null) {
			logger.error("TransactionID: " + transactionId + " not found");
			throw new Exception("Transaction not found");
		}
		
		// Here we need to check if the user has rights to get the transaction		
		if(authentication.getName().equals(transaction.getProfile().getUser().getUsername())) {
			return transaction;
		} else {
			logger.error(authentication.getName() + " does not have access");
			throw new Exception("Not permitted to view transaction");
		}
		
	}
	
	/**
	 * This method is used to get all transactions underneath a profile.
	 * 
	 * @param profileId
	 * @param authentication
	 * @return
	 * @throws Exception 
	 */
	public List<Transaction> getAllTransactionsByProfileId(Integer profileId, Authentication authentication) throws Exception {
		Profile foundProfile = profileRepo.findById(profileId)
									.orElseThrow(() -> new Exception("Profile ID " + profileId + " not found"));
		
		// Here we need to check if the user has rights to get all transactions
		if(foundProfile.getUser().getUsername().equals(authentication.getName())) {
			return transactionRepo.findAllTransactionsByProfileId(profileId);
		} else {
			logger.error(authentication.getName() + " does not have access");
			throw new Exception("Not permitted to view transaction");
		}
	}
	
	/**
	 * This method is used to get all transactions underneath a user.
	 * 
	 * @param profiles
	 * @return
	 */
	public List<Transaction> getAllTransactions(List<Profile> profiles) {
		ArrayList<Transaction> foundTransactions = new ArrayList<>();
		
		for(Profile profile : profiles) {
			foundTransactions.addAll(transactionRepo.findAllTransactionsByProfileId(profile.getProfileId()));
		}
		
		return foundTransactions;
	}
	
	/**
	 * This method is used to get all income type transactions underneath a user.
	 * 
	 * @param profiles
	 * @return
	 */
	public List<Transaction> getAllIncome(List<Profile> profiles) {
		ArrayList<Transaction> foundTransactions = new ArrayList<>();

		for(Profile profile : profiles) {
			foundTransactions.addAll(transactionRepo.findAllIncomeByProfileId(profile.getProfileId()));
		}
		
		return foundTransactions;
	}
	
	/**
	 * This method is used to get all expense type transactions underneath a user.
	 * 
	 * @param profiles
	 * @return
	 */
	public List<Transaction> getAllExpense(List<Profile> profiles) {
		ArrayList<Transaction> foundTransactions = new ArrayList<>();
		
		for(Profile profile : profiles) {
			foundTransactions.addAll(transactionRepo.findAllExpenseByProfileId(profile.getProfileId()));
		}
		
		return foundTransactions;
	}
}
