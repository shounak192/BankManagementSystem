package com.epam.accountservice.dto;

import java.math.BigDecimal;

import com.epam.accountservice.models.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionViewDto {

	private Integer id;

	private Integer customerId;

	private Integer accountId;

	private TransactionType transactionType;

	private BigDecimal amount;

	private BigDecimal closingBalance;

}
