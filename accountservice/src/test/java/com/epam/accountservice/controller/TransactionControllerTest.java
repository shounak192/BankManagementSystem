package com.epam.accountservice.controller;

import static org.mockito.Mockito.when;
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

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.models.Transaction;
import com.epam.accountservice.models.TransactionType;
import com.epam.accountservice.service.implementations.TransactionServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	private Transaction transaction;

	private TransactionDto transactionDto;

	private TransactionViewDto transactionViewDto;

	private List<Transaction> listTransaction = new ArrayList<>();

	@MockBean
	private TransactionServiceImpl transactionService;

	@Autowired
	public TransactionControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		super();
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}

	@BeforeEach
	void setup() {
		transaction = new Transaction(1, TransactionType.CREDIT, 1, BigDecimal.valueOf(0), LocalDate.now());
		transactionDto = new TransactionDto(1, TransactionType.CREDIT, BigDecimal.valueOf(0));
		transactionViewDto = new TransactionViewDto(1, 1, 1, TransactionType.CREDIT, BigDecimal.valueOf(0),
				BigDecimal.valueOf(0), LocalDate.now());
		listTransaction.add(transaction);
	}

	@Test
	void transactionTest() throws JsonProcessingException, Exception {

		when(transactionService.transaction(transactionDto)).thenReturn(transactionViewDto);
		mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transactionDto))).andExpect(status().isOk());
	}

	@Test
	void generateTransactionHistory() throws JsonProcessingException, Exception {

		when(transactionService.generateTransactionHistory(1, 1)).thenReturn(listTransaction);
		mockMvc.perform(get("/transaction/history/{accountId}/{durationInMonths}", 1, 1))
				.andExpect(status().isFound());
	}

}
