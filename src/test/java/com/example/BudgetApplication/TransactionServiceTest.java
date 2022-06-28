package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.BudgetApplication.entity.Expense;
import com.example.BudgetApplication.entity.Income;
import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.entity.Transaction;
import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.repository.ProfileRepository;
import com.example.BudgetApplication.repository.TransactionRepository;
import com.example.BudgetApplication.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	
	private TransactionService tS;

	@Mock
	TransactionRepository mockTR;
	
	@Mock
	ProfileRepository mockPR;	
	
	@Mock
	Authentication mockAuth;
	
	@BeforeEach
	void setup() {
		tS = new TransactionService(mockTR, mockPR);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_add_income_correct_user() throws Exception {
		// Arrange
		Income income = new Income("Job", "FDM", 500, new Date(2020, 12, 23));
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");
		profile.setUser(user);
		when(mockAuth.getName()).thenReturn("ben");
		
		// Act
		tS.addIncome(income, profile, mockAuth);
		
		//Assert
		verify(mockTR).save(income);
		assertEquals(income.getProfile(), profile);
		assertEquals(income.getType(), "Income");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_add_income_incorrect_user() throws Exception {
		// Arrange
		Income income = new Income("Job", "FDM", 500, new Date(2020, 12, 23));
		Profile profile = new Profile("ben", "chang");
		User user = new User("notben", "password");		// Here the username doesn't match with what's authenticated
		profile.setUser(user);
		when(mockAuth.getName()).thenReturn("ben");
		
		// Act
		Exception e = assertThrows(Exception.class, () -> tS.addIncome(income, profile, mockAuth));
		
		//Assert
		assertEquals("Not permitted to add transaction", e.getMessage());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_add_expense_correct_user() throws Exception {
		// Arrange
		Expense expense = new Expense("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");
		profile.setUser(user);
		when(mockAuth.getName()).thenReturn("ben");
		
		// Act
		tS.addExpense(expense, profile, mockAuth);
		
		//Assert
		verify(mockTR).save(expense);
		assertEquals(expense.getProfile(), profile);
		assertEquals(expense.getType(), "Expense");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_add_expense_incorrect_user() throws Exception {
		// Arrange
		Expense expense = new Expense("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		Profile profile = new Profile("ben", "chang");
		User user = new User("notben", "password");		// Here the username doesn't match with what's authenticated
		profile.setUser(user);
		when(mockAuth.getName()).thenReturn("ben");
		
		// Act
		Exception e = assertThrows(Exception.class, () -> tS.addExpense(expense, profile, mockAuth));
		
		//Assert
		assertEquals("Not permitted to add transaction", e.getMessage());
	}
	
	@Test
	void test_delete_transaction_by_id_no_transaction_found() throws Exception {
		// Arrange
		Integer transactionId = 1;
		when(mockTR.findById(transactionId)).thenReturn(Optional.ofNullable(null));
		
		// Act
		Exception e = assertThrows(Exception.class, () -> tS.deleteTransactionById(transactionId, mockAuth));
		
		// Assert
		assertEquals("Transaction not found", e.getMessage());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_delete_transaction_by_id_success() throws Exception {
		// Arrange
		Integer transactionId = 1;
		Optional<Transaction> transaction = Optional.ofNullable(new Transaction("Clothes", "T-shirt", 50, new Date(2020, 12, 23)));
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");
		profile.setUser(user);
		transaction.get().setProfile(profile);
		
		when(mockAuth.getName()).thenReturn("ben");
		when(mockTR.findById(transactionId)).thenReturn(transaction);
		
		// Act
		tS.deleteTransactionById(transactionId, mockAuth);
		
		// Assert
		verify(mockTR).deleteById(transactionId);
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	void get_transaction_by_id_success() throws Exception {
		// Arrange
		when(mockAuth.getName()).thenReturn("ben");
		Integer transactionId = 1;
		Optional<Transaction> transaction = Optional.ofNullable(new Transaction("Clothes", "T-shirt", 50, new Date(2020, 12, 23)));
		Profile profile = new Profile("ben", "chang");
		User user = new User("ben", "password");
		profile.setUser(user);
		transaction.get().setProfile(profile);
		when(mockTR.findById(transactionId)).thenReturn(transaction);
		
		// Act
		Transaction result = tS.getTransactionById(transactionId, mockAuth);
		
		// Assert
		assertEquals(transaction.get(), result);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_get_all_transactions_by_profile_id_sucess() throws Exception {
		// Arrange
		when(mockAuth.getName()).thenReturn("ben");
		Integer profileId = 1;
		
		Optional<Profile> foundProfile = Optional.ofNullable(new Profile("ben", "chang"));
		when(mockPR.findById(profileId)).thenReturn(foundProfile);
		
		List<Transaction> foundTransactions = new ArrayList<>();
		Transaction transaction = new Transaction("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		User user = new User("ben", "password");
		
		foundProfile.get().setUser(user);
		transaction.setProfile(foundProfile.get());
		foundTransactions.add(transaction);
		
		when(mockTR.findAllTransactionsByProfileId(profileId)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tS.getAllTransactionsByProfileId(profileId, mockAuth);
		
		// Assert
		assertEquals(foundTransactions, result);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_get_all_transactions() {
		// Arrange
		List<Transaction> foundTransactions = new ArrayList<>();
		Transaction transaction = new Expense("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		
		foundTransactions.add(transaction);
		
		List<Profile> profiles = new ArrayList<>();
		Profile profile = new Profile("ben", "chang");
		profile.setProfileId(1);
		profiles.add(profile);
		
		when(mockTR.findAllTransactionsByProfileId(1)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tS.getAllTransactions(profiles);
		
		// Assert
		assertEquals(foundTransactions, result);
		verify(mockTR).findAllTransactionsByProfileId(1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_get_all_income() {
		// Arrange
		List<Transaction> foundTransactions = new ArrayList<>();
		Income income = new Income("Job", "FDM", 500, new Date(2020, 12, 23));
		
		foundTransactions.add(income);
		
		List<Profile> profiles = new ArrayList<>();
		Profile profile = new Profile("ben", "chang");
		profile.setProfileId(1);
		profiles.add(profile);
		
		when(mockTR.findAllIncomeByProfileId(1)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tS.getAllIncome(profiles);
		
		// Assert
		assertEquals(foundTransactions, result);
		verify(mockTR).findAllIncomeByProfileId(1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_get_all_expense() {
		// Arrange
		List<Transaction> foundTransactions = new ArrayList<>();
		Transaction transaction = new Expense("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		
		foundTransactions.add(transaction);
		
		List<Profile> profiles = new ArrayList<>();
		Profile profile = new Profile("ben", "chang");
		profile.setProfileId(1);
		profiles.add(profile);
		
		when(mockTR.findAllExpenseByProfileId(1)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tS.getAllExpense(profiles);
		
		// Assert
		assertEquals(foundTransactions, result);
		verify(mockTR).findAllExpenseByProfileId(1);
	}
}
