package com.epam.accountservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.models.Transaction;
import com.epam.accountservice.service.ITransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	private ITransactionService transactionService;

	public TransactionController(ITransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity<TransactionViewDto> transaction(@RequestBody TransactionDto transactionDto) {

		return new ResponseEntity<>(transactionService.transaction(transactionDto), HttpStatus.OK);
	}

	@GetMapping("/history/{accountId}/{durationInMonths}")
	public ResponseEntity<List<Transaction>> generateTransactionHistory(@PathVariable Integer accountId,
			@PathVariable Integer durationInMonths) {

		return new ResponseEntity<>(transactionService.generateTransactionHistory(accountId, durationInMonths),
				HttpStatus.FOUND);
	}

}
