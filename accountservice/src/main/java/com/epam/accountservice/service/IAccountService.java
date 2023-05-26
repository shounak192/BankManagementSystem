package com.epam.accountservice.service;

import com.epam.accountservice.dto.AccountDto;
import com.epam.accountservice.dto.AccountViewDto;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {

	public AccountViewDto create(Integer customerId, AccountDto accountDto);

	public AccountViewDto view(Integer id);

	public AccountViewDto delete(Integer id);

	public List<AccountViewDto> viewAll();

	public List<AccountViewDto> viewAllByCustomer(Integer customerId);

	public BigDecimal viewBalance(Integer id);

}
