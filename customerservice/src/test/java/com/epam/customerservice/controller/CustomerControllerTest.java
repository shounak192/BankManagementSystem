package com.epam.customerservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.customerservice.dto.CustomerCredentialDto;
import com.epam.customerservice.dto.CustomerDto;
import com.epam.customerservice.models.Customer;
import com.epam.customerservice.models.Gender;
import com.epam.customerservice.models.IdentityType;
import com.epam.customerservice.service.implementations.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	@MockBean
	private CustomerServiceImpl customerService;
	
	private CustomerDto customerDto;
	
	private CustomerCredentialDto customerCredentialDto;
	
	private Customer customer;
	
	@Autowired
	private CustomerControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		super();
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}


	@BeforeEach
	void setup() {
		customerCredentialDto= new CustomerCredentialDto("username", "password");
		customerDto= new CustomerDto("name", 10, Gender.MALE, IdentityType.PAN, "IDT123", customerCredentialDto);
		customer= new Customer(1, "name", 10, Gender.MALE, IdentityType.PAN, "IDT123", "username", "password");
	}
	
	@Test
	void registerTest() throws JsonProcessingException, Exception {
		
		when(customerService.register(customerDto)).thenReturn(customer);
		mockMvc.perform(post("/customer/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerDto))).andExpect(status().isCreated());
	}
	
	@Test
	void loginTest() throws JsonProcessingException, Exception  {
		
		when(customerService.login(customerCredentialDto)).thenReturn(customer);
		mockMvc.perform(get("/customer/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerDto))).andExpect(status().isOk());
	}
	
	@Test
	void findByIdTest() throws Exception {
		
		when(customerService.findById(1)).thenReturn(customer);
		mockMvc.perform(get("/customer/{id}",1)).andExpect(status().isFound());
	}
	
	@Test
	void deleteByIdTest() throws Exception {
		
		when(customerService.register(customerDto)).thenReturn(customer);
		mockMvc.perform(delete("/customer/{id}",1)).andExpect(status().isOk());
	}

}
