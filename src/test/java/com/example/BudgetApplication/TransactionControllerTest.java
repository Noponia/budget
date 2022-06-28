package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.BudgetApplication.controller.TransactionController;
import com.example.BudgetApplication.entity.Expense;
import com.example.BudgetApplication.entity.Income;
import com.example.BudgetApplication.entity.Profile;
import com.example.BudgetApplication.entity.Transaction;
import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.service.ProfileService;
import com.example.BudgetApplication.service.TransactionService;
import com.example.BudgetApplication.service.UserService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
	
	private TransactionController tC;

	@Mock
	TransactionService mockTS;
	
	@Mock
	ProfileService mockPS;
	
	@Mock
	UserService mockUS;
	
	@Mock
	Transaction mockTransaction;
	
	@Mock
	Authentication mockAuth;
	
	@BeforeEach
	void setup() {
		tC = new TransactionController(mockTS, mockPS, mockUS);
	}
	
	@Test
	void test_get_transaction_by_its_id() throws Exception {
		// Arrange
		Integer transactionId = 1;
		when(mockTS.getTransactionById(transactionId, mockAuth)).thenReturn(mockTransaction);
		
		// Act
		Transaction foundTransaction = tC.getTransactionById(transactionId, mockAuth);
		
		//Assert
		assertEquals(mockTransaction, foundTransaction);
	}
	
	@Test
	void test_get_all_transactions_by_profileId() throws Exception {
		// Arrange
		Integer transactionId = 1;
		List<Transaction> foundTransactions = new ArrayList<>();
		when(mockTS.getAllTransactionsByProfileId(transactionId, mockAuth)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tC.getAllTransactionsByProfileId(transactionId, mockAuth);
		
		// Assert
		assertEquals(foundTransactions, result);
	}
	
	@Test
	void test_get_all_transactions_for_a_user() throws Exception {
		// Arrange
		List<Profile> foundProfiles = new ArrayList<>();
		List<Transaction> foundTransactions = new ArrayList<>();
		String username = "ben";
		User user = new User("ben.chang", "password");
		Profile profile = new Profile("ben", "chang");
		@SuppressWarnings("deprecation")
		Transaction transaction = new Transaction("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		foundTransactions.add(transaction);
		foundProfiles.add(profile);
		
		when(mockAuth.getName()).thenReturn(username);
		when(mockUS.findByUsername(username)).thenReturn(user);
		when(mockPS.getProfiles(user)).thenReturn(foundProfiles);
		when(mockTS.getAllTransactions(foundProfiles)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tC.getAllTransactions(mockAuth);
		
		// Assert
		assertEquals(foundTransactions, result);
	}
	
	@Test
	void test_get_all_income_for_a_user() throws Exception {
		// Arrange
		List<Profile> foundProfiles = new ArrayList<>();
		List<Transaction> foundTransactions = new ArrayList<>();
		String username = "ben";
		User user = new User("ben.chang", "password");
		Profile profile = new Profile("ben", "chang");
		@SuppressWarnings("deprecation")
		Income income = new Income("Job", "FDM", 500, new Date(2020, 12, 23));
		foundTransactions.add(income);
		foundProfiles.add(profile);
		
		when(mockAuth.getName()).thenReturn(username);
		when(mockUS.findByUsername(username)).thenReturn(user);
		when(mockPS.getProfiles(user)).thenReturn(foundProfiles);
		when(mockTS.getAllIncome(foundProfiles)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tC.getAllIncome(mockAuth);
		
		// Assert
		assertEquals(foundTransactions, result);
	}
	
	@Test
	void test_get_all_expense_for_a_user() throws Exception {
		// Arrange
		List<Profile> foundProfiles = new ArrayList<>();
		List<Transaction> foundTransactions = new ArrayList<>();
		String username = "ben";
		User user = new User("ben.chang", "password");
		Profile profile = new Profile("ben", "chang");
		@SuppressWarnings("deprecation")
		Expense expense = new Expense("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		foundTransactions.add(expense);
		foundProfiles.add(profile);
		
		when(mockAuth.getName()).thenReturn(username);
		when(mockUS.findByUsername(username)).thenReturn(user);
		when(mockPS.getProfiles(user)).thenReturn(foundProfiles);
		when(mockTS.getAllExpense(foundProfiles)).thenReturn(foundTransactions);
		
		// Act
		List<Transaction> result = tC.getAllExpense(mockAuth);
		
		// Assert
		assertEquals(foundTransactions, result);
	}
	
	@Test
	void test_add_income() throws Exception {
		// Arrange
		Integer profileId = 1;
		@SuppressWarnings("deprecation")
		Income income = new Income("Job", "FDM", 500, new Date(2020, 12, 23));
		Profile profile = new Profile("ben", "chang");
		when(mockPS.findProfileById(profileId)).thenReturn(profile);
		
		// Act
		tC.addIncome(income, 1, mockAuth);
		
		// Assert
		verify(mockTS).addIncome(income, profile, mockAuth);
	}
	
	@Test
	void test_add_expense() throws Exception {
		// Arrange
		Integer profileId = 1;
		@SuppressWarnings("deprecation")
		Expense expense = new Expense("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		Profile profile = new Profile("ben", "chang");
		when(mockPS.findProfileById(profileId)).thenReturn(profile);
		
		// Act
		tC.addExpense(expense, 1, mockAuth);
		
		// Assert
		verify(mockTS).addExpense(expense, profile, mockAuth);
	}
	
	
	@Test void test_delete_transaction_by_id() throws Exception {
		// Arrange
		Integer transactionId = 1;
		
		// Act
		tC.deleteTransactionById(transactionId, mockAuth);
		
		// Assert
		verify(mockTS).deleteTransactionById(transactionId, mockAuth);
	}

	@Test void test_update_transaction() throws Exception {
		// Arrange 
		@SuppressWarnings("deprecation")
		Transaction transaction = new Transaction("Clothes", "T-shirt", 50, new Date(2020, 12, 23));
		
		// Act
		tC.updateTransaction(transaction, mockAuth);
		
		// Assert
		verify(mockTS).updateTransaction(transaction, mockAuth);
	}
}
