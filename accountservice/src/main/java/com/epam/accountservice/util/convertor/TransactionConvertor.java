package com.epam.accountservice.util.convertor;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.models.Transaction;

@Component
public class TransactionConvertor {

	public Transaction convert(TransactionDto transactionDto) {
		return Transaction.builder().accountId(transactionDto.getAccountId())
				.transactionType(transactionDto.getTransactionType()).amount(transactionDto.getAmount())
				.transactionDate(LocalDate.now()).build();
	}

}
