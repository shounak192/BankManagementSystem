package com.epam.accountservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.accountservice.dto.AccountDto;
import com.epam.accountservice.dto.AccountViewDto;
import com.epam.accountservice.models.AccountType;
import com.epam.accountservice.models.CurrencyUnit;
import com.epam.accountservice.service.implementations.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	private AccountDto accountDto;

	private AccountViewDto accountViewDto;

	private List<AccountViewDto> listAccountViewDto = new ArrayList<>();

	private BigDecimal closingBalance;

	@MockBean
	private AccountServiceImpl accountService;

	@Autowired
	public AccountControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		super();
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}

	@BeforeEach
	void setup() {
		accountDto = new AccountDto(AccountType.SAVINGS, CurrencyUnit.INR);
		accountViewDto = new AccountViewDto(1, 1, AccountType.SAVINGS, LocalDate.now(), CurrencyUnit.INR,
				BigDecimal.valueOf(0));
		listAccountViewDto.add(accountViewDto);
		closingBalance = BigDecimal.valueOf(0);
	}

	@Test
	void createTest() throws JsonProcessingException, Exception {

		when(accountService.create(1, accountDto)).thenReturn(accountViewDto);
		mockMvc.perform(post("/account/create/{customerId}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDto))).andExpect(status().isCreated());
	}

	@Test
	void viewTest() throws JsonProcessingException, Exception {

		when(accountService.view(1)).thenReturn(accountViewDto);
		mockMvc.perform(get("/account/{id}", 1)).andExpect(status().isFound());
	}

	@Test
	void deleteTest() throws JsonProcessingException, Exception {

		when(accountService.delete(1)).thenReturn(accountViewDto);
		mockMvc.perform(delete("/account/{id}", 1)).andExpect(status().isOk());
	}

	@Test
	void viewAllTest() throws JsonProcessingException, Exception {

		when(accountService.viewAll()).thenReturn(listAccountViewDto);
		mockMvc.perform(get("/account")).andExpect(status().isFound());
	}

	@Test
	void viewBalanceTest() throws JsonProcessingException, Exception {

		when(accountService.viewBalance(1)).thenReturn(closingBalance);
		mockMvc.perform(get("/account/balance/{id}", 1)).andExpect(status().isFound());
	}

	@Test
	void viewAllByCustomerTest() throws JsonProcessingException, Exception {

		when(accountService.viewAllByCustomer(1)).thenReturn(listAccountViewDto);
		mockMvc.perform(get("/account/viewaccounts/{customerId}", 1)).andExpect(status().isFound());
	}
}
