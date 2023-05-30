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
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.exceptions.AccountNotFoundException;
import com.epam.accountservice.models.Account;
import com.epam.accountservice.models.AccountType;
import com.epam.accountservice.models.Balance;
import com.epam.accountservice.models.CurrencyUnit;
import com.epam.accountservice.models.Transaction;
import com.epam.accountservice.models.TransactionType;
import com.epam.accountservice.repository.IBalanceRepository;
import com.epam.accountservice.repository.ITransactionRepository;
import com.epam.accountservice.service.implementations.TransactionServiceImpl;
import com.epam.accountservice.util.convertor.TransactionConvertor;
import com.epam.accountservice.util.convertor.TransactionViewConvertor;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@Mock
	private ITransactionRepository transactionRepository;

	@Mock
	private IBalanceRepository balanceRepository;

	@Mock
	private TransactionViewConvertor transactionViewConvertor;

	@Mock
	private TransactionConvertor transactionConvertor;

	private Transaction transaction;

	private TransactionDto transactionDto;

	private TransactionViewDto transactionViewDto;

	private List<Transaction> listTransaction = new ArrayList<>();

	private List<Transaction> emptyListTransaction = new ArrayList<>();

	private Balance balance;

	private Account account;

	private Optional<Balance> optionalBalance;

	@BeforeEach
	void setup() {
		transaction = new Transaction(1, TransactionType.CREDIT, 1, BigDecimal.valueOf(0), LocalDate.now());
		transactionDto = new TransactionDto(1, TransactionType.CREDIT, BigDecimal.valueOf(0));
		transactionViewDto = new TransactionViewDto(1, 1, 1, TransactionType.CREDIT, BigDecimal.valueOf(0),
				BigDecimal.valueOf(0), LocalDate.now());
		listTransaction.add(transaction);
		account = new Account(1, 1, AccountType.SAVINGS, LocalDate.now(), CurrencyUnit.INR, balance);
		balance = new Balance(1, account, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
		optionalBalance = Optional.ofNullable(balance);

		when(transactionViewConvertor.convert(balance, transaction)).thenReturn(transactionViewDto);
		when(transactionConvertor.convert(transactionDto)).thenReturn(transaction);
	}

	@Test
	void transactionTest() throws JsonProcessingException, Exception {

		when(balanceRepository.findByAccountId(1)).thenReturn(optionalBalance);
		when(transactionRepository.save(transaction)).thenReturn(transaction);
		assertEquals(transactionViewDto, transactionService.transaction(transactionDto));

	}

	@Test
	void invalidTransactionTest() throws JsonProcessingException, Exception {

		when(balanceRepository.findByAccountId(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(AccountNotFoundException.class, () -> transactionService.transaction(transactionDto));

	}

	@Test
	void generateTransactionHistory() throws JsonProcessingException, Exception {

		when(transactionRepository.findAllByAccountId(1)).thenReturn(listTransaction);
		assertEquals(listTransaction, transactionService.generateTransactionHistory(1, 1));
	}

}
