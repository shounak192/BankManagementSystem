package com.epam.accountservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.epam.accountservice.dto.AccountDto;
import com.epam.accountservice.dto.AccountViewDto;
import com.epam.accountservice.exceptions.AccountNotFoundException;
import com.epam.accountservice.models.Account;
import com.epam.accountservice.models.AccountType;
import com.epam.accountservice.models.Balance;
import com.epam.accountservice.models.CurrencyUnit;
import com.epam.accountservice.repository.IAccountRepository;
import com.epam.accountservice.service.implementations.AccountServiceImpl;
import com.epam.accountservice.util.convertor.AccountConvertor;
import com.epam.accountservice.util.convertor.AccountViewConvertor;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountServiceTest {

	@InjectMocks
	private AccountServiceImpl accountService;

	@Mock
	private IAccountRepository accountRepository;

	@Mock
	private AccountConvertor accountConvertor;

	@Mock
	private AccountViewConvertor accountViewConvertor;

	private AccountDto accountDto;

	private Account account;

	private AccountViewDto accountViewDto;

	private List<AccountViewDto> listAccountViewDto = new ArrayList<>();

	private List<Account> listAccount = new ArrayList<>();

	private BigDecimal closingBalance;

	private Balance balance;

	private Optional<Account> optionalAccount;

	@BeforeEach
	void setup() {
		balance = new Balance();
		balance.setClosingBalance(BigDecimal.valueOf(0));
		account = new Account(1, 1, AccountType.SAVINGS, LocalDate.now(), CurrencyUnit.INR, balance);
		accountDto = new AccountDto(AccountType.SAVINGS, CurrencyUnit.INR);
		accountViewDto = new AccountViewDto(1, 1, AccountType.SAVINGS, LocalDate.now(), CurrencyUnit.INR,
				BigDecimal.valueOf(0));
		listAccountViewDto.add(accountViewDto);
		listAccount.add(account);
		closingBalance = BigDecimal.valueOf(0);
		optionalAccount = Optional.ofNullable(account);
		when(accountViewConvertor.convert(account)).thenReturn(accountViewDto);
	}

	@Test
	void viewTest() throws JsonProcessingException, Exception {

		when(accountRepository.findById(1)).thenReturn(optionalAccount);
		assertEquals(accountViewDto, accountService.view(1));
	}

	@Test
	void invalidViewTest() throws JsonProcessingException, Exception {

		when(accountRepository.findById(0)).thenReturn(Optional.ofNullable(null));
		assertThrows(AccountNotFoundException.class, () -> accountService.view(1));
	}

	@Test
	void deleteTest() throws JsonProcessingException, Exception {

		when(accountRepository.findById(1)).thenReturn(optionalAccount);
		assertEquals(accountViewDto, accountService.delete(1));

	}

	@Test
	void invalidDeleteTest() throws JsonProcessingException, Exception {

		when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(AccountNotFoundException.class, () -> accountService.delete(1));
	}

	@Test
	void viewAllTest() throws JsonProcessingException, Exception {

		when(accountRepository.findAll()).thenReturn(listAccount);
		assertEquals(listAccountViewDto, accountService.viewAll());
	}

	@Test
	void viewBalanceTest() throws JsonProcessingException, Exception {

		when(accountRepository.findById(1)).thenReturn(optionalAccount);
		assertEquals(closingBalance, accountService.viewBalance(1));
	}

	@Test
	void invalidViewBalanceTest() throws JsonProcessingException, Exception {

		when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(AccountNotFoundException.class, () -> accountService.viewBalance(1));
	}

}
