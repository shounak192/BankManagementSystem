 package com.epam.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.epam.accountservice.models.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionDto {

	private Integer accountId;

	private TransactionType transactionType;

	private BigDecimal amount;
	
}
