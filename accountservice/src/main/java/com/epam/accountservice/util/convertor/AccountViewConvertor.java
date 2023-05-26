package com.epam.accountservice.util.convertor;

import org.springframework.stereotype.Component;

import com.epam.accountservice.dto.AccountViewDto;
import com.epam.accountservice.models.Account;

@Component
public class AccountViewConvertor {

	public AccountViewDto convert(Account account) {

		return AccountViewDto.builder().id(account.getId()).customerId(account.getCustomerId())
				.accountType(account.getAccountType()).dateCreated(account.getDateCreated()).unit(account.getUnit())
				.closingBalance(account.getBalance().getClosingBalance()).build();
	}

}
