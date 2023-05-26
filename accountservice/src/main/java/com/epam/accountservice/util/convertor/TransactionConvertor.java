package com.epam.accountservice.util.convertor;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.models.Transaction;

public class TransactionConvertor {

	public Transaction convert(TransactionDto transactionDto) {
		return Transaction.builder().accountId(transactionDto.getAccountId())
				.transactionType(transactionDto.getTransactionType()).amount(transactionDto.getAmount()).build();
	}

}
