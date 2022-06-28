package com.example.BudgetApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.BudgetApplication.entity.Profile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("admin")
public class ProfileIntegrationTest {

	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	public ProfileIntegrationTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	@Test
	void test_add_a_profile() throws JsonProcessingException, Exception {
		Profile profile = new Profile("ben", "chang");
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
									.post("/api/profile/add")
									.contentType(MediaType.APPLICATION_JSON_VALUE)
									.content(mapper.writeValueAsString(profile)))
									.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void test_get_all_profile() throws JsonProcessingException, Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/profile/all"))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void test_delete_a_profile_with_id_1() throws Exception {		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
									.delete("/api/profile/delete/1"))
									.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
}
