package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.BudgetApplication.entity.User;
import com.example.BudgetApplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private UserService mockUS;
	
	@Autowired
	public UserIntegrationTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	@Test
	void test_register_user() throws Exception{
		User user = new User("ben.chang", "password");
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
									.post("/api/register")
									.contentType(MediaType.APPLICATION_JSON_VALUE)
									.content(mapper.writeValueAsString(user)))
									.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
}
