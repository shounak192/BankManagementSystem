package com.epam.accountservice.dto;

import com.epam.accountservice.models.AccountType;
import com.epam.accountservice.models.CurrencyUnit;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {

	@Positive
	@Min(value = 0, message = "0:SAVINGS, 1:CURRENT, 2:PPF")
	@Max(value = 2, message = "0:SAVINGS, 1:CURRENT, 2:PPF")
	private AccountType accountType;

	@Positive
	@Min(value = 0, message = "0:SAVINGS, 1:CURRENT, 2:PPF")
	@Max(value = 2, message = "0:SAVINGS, 1:CURRENT, 2:PPF")
	private CurrencyUnit unit;
}
