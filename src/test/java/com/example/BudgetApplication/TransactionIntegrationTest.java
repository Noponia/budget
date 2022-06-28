package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.BudgetApplication.entity.Expense;
import com.example.BudgetApplication.entity.Income;
import com.example.BudgetApplication.entity.Transaction;
import com.example.BudgetApplication.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("admin")
public class TransactionIntegrationTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	public TransactionIntegrationTest(MockMvc mockMvc, TransactionRepository tR) {
		this.mockMvc = mockMvc;
	}
	
	@Test
	void get_transaction_by_id() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/transaction/1"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void get_all_transactions_by_profile_id() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/transaction/all/1"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void test_get_all_transactions() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/transaction/all"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void test_get_all_income() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/transaction/all-income"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void test_get_all_expense() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/transaction/all-expense"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_add_income_to_profile_id_1() throws JsonProcessingException, Exception {
		Income income = new Income("Job", "FDM", 10, new Date(2016, 3, 3));
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
									.post("/api/transaction/add-income/1")
									.contentType(MediaType.APPLICATION_JSON_VALUE)
									.content(mapper.writeValueAsString(income)))
									.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_add_expense_to_profile_id_1() throws JsonProcessingException, Exception {
		Expense expense = new Expense("Groceries", "Chicken, Eggs", 30, new Date(2016, 3, 3));
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
									.post("/api/transaction/add-expense/1")
									.contentType(MediaType.APPLICATION_JSON_VALUE)
									.content(mapper.writeValueAsString(expense)))
									.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void test_update_transaction_with_id_1() throws JsonProcessingException, Exception {
		Transaction transaction = new Transaction("Groceries", "Chicken, Eggs", 30, new Date(2016, 3, 3));
		transaction.setTransactionId(1);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/transaction/update/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(transaction)))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void test_delete_transaction_with_id_2() throws Exception {
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/transaction/delete/2"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
}
