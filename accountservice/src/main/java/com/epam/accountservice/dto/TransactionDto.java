package com.epam.accountservice.dto;

import java.math.BigDecimal;

import com.epam.accountservice.models.TransactionType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionDto {

	private Integer accountId;

	@Positive
	@Min(value = 0, message = "0:DEBIT, 1:CREDIT")
	@Max(value = 1, message = "0:DEBIT, 1:CREDIT")
	private TransactionType transactionType;

	private BigDecimal amount;
}
