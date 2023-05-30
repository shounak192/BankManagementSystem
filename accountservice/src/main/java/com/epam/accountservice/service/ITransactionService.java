package com.epam.accountservice.service;

import java.util.List;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.models.Transaction;

public interface ITransactionService {

	public TransactionViewDto transaction(TransactionDto transactionDto);

	public List<Transaction> generateTransactionHistory(Integer accountId, Integer durationInMonths);

}
