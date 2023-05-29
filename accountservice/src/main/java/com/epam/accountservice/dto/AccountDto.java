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

	private AccountType accountType;

	private CurrencyUnit unit;
}
