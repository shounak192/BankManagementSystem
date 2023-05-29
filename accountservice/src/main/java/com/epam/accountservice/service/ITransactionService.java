package com.epam.accountservice.service;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;

public interface ITransactionService {

	public TransactionViewDto transaction(TransactionDto transactionDto);
	
	
	
	

}
