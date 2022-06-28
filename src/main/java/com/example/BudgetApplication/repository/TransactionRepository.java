package com.example.BudgetApplication.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.BudgetApplication.entity.Transaction;

/**
 * Repository for the transaction entity.
 * 
 * @author Benjamin Chang
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>  {
	
	/**
	 * Custom query to find all transactions made by a Profile.
	 * 
	 * @param profileId
	 * @return all relevant transactions
	 */
	@Query("SELECT t FROM Transaction t "
			+ "WHERE t.profile.profileId = :profileId")
	public List<Transaction> findAllTransactionsByProfileId (
			@Param("profileId") Integer profileId);
	
	/**
	 * Custom query to find all income transactions made by a Profile.
	 * 
	 * @param profileId
	 * @return all relevant income transactions 
	 */
	@Query("SELECT t FROM Transaction t "
			+ "WHERE t.profile.profileId = :profileId "
			+ "AND t.type = 'income'")
	public List<Transaction> findAllIncomeByProfileId (
			@Param("profileId") Integer profileId);
	
	/**
	 * Custom query to find all expense transactions made by a Profile.
	 * 
	 * @param profileId
	 * @return all relevant expense transactions
	 */
	@Query("SELECT t FROM Transaction t "
			+ "WHERE t.profile.profileId = :profileId "
			+ "AND t.type = 'expense'")
	public List<Transaction> findAllExpenseByProfileId (
			@Param("profileId") Integer profileId);
	
}
