package com.epam.accountservice.util.convertor;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.epam.accountservice.dto.AccountDto;
import com.epam.accountservice.models.Account;

@Component
public class AccountConvertor {

	public Account convert(Integer customerId, AccountDto accountDto) {

		return Account.builder().customerId(customerId).accountType(accountDto.getAccountType())
				.unit(accountDto.getUnit()).dateCreated(LocalDate.now()).build();
	}

}
