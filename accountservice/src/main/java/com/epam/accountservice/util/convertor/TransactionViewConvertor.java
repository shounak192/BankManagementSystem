package com.epam.accountservice.util.convertor;

import org.springframework.stereotype.Component;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.models.Balance;

@Component
public class TransactionViewConvertor {

	public TransactionViewDto convert(Balance balance, TransactionDto transactionDto) {

		return TransactionViewDto.builder().accountId(transactionDto.getAccountId()).amount(transactionDto.getAmount())
				.closingBalance(balance.getClosingBalance()).customerId(balance.getAccount().getCustomerId())
				.transactionType(transactionDto.getTransactionType()).build();
	}

}
