package com.epam.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.epam.accountservice.models.AccountType;
import com.epam.accountservice.models.CurrencyUnit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountViewDto {
	
	private Integer id;
	
	private Integer customerId;
	
	private AccountType accountType;
	
	private LocalDate dateCreated;
	
	private CurrencyUnit unit;
	
	private BigDecimal closingBalance;
}
