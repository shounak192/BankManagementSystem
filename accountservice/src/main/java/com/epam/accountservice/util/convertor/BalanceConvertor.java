package com.epam.accountservice.util.convertor;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.epam.accountservice.models.Balance;

@Component
public class BalanceConvertor {

	public Balance convert() {
		return Balance.builder().openingBalance(new BigDecimal(0.0)).closingBalance(new BigDecimal(0.0)).build();
	}

}
