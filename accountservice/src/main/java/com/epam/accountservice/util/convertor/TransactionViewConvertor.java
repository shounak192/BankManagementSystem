package com.epam.accountservice.util.convertor;

import org.springframework.stereotype.Component;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.models.Balance;
import com.epam.accountservice.models.Transaction;

@Component
public class TransactionViewConvertor {

	public TransactionViewDto convert(Balance balance, Transaction transaction) {

		return TransactionViewDto.builder().accountId(transaction.getAccountId()).amount(transaction.getAmount())
				.closingBalance(balance.getClosingBalance()).customerId(balance.getAccount().getCustomerId())
				.transactionType(transaction.getTransactionType()).transactionDate(transaction.getTransactionDate())
				.build();
	}

}
