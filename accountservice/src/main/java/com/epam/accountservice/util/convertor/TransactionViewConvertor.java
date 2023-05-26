package com.epam.accountservice.util.convertor;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.models.Balance;

public class TransactionViewConvertor {
	
	public TransactionViewDto convert(Balance balance, TransactionDto transactionDto) {
		
		return TransactionViewDto.builder().accountId(transactionDto.getAccountId()).amount(transactionDto.getAmount())
				.closingBalance(balance.getClosingBalance()).customerId(balance.getAccount().getCustomerId())
				.transactionType(transactionDto.getTransactionType()).build();
	}

}
