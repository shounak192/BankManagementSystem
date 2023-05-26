package com.epam.accountservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.service.ITransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	private ITransactionService transactionService;

	private TransactionController(ITransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}
	
	@PostMapping
	public ResponseEntity<TransactionViewDto> transaction(TransactionDto transactionDto) {

		return new ResponseEntity<>(transactionService.transaction(transactionDto), HttpStatus.OK);
	}

}
