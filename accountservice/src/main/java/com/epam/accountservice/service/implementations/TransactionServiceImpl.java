package com.epam.accountservice.service.implementations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.exceptions.AccountNotFoundException;
import com.epam.accountservice.exceptions.InsufficientBalanceException;
import com.epam.accountservice.exceptions.PPFMaturityException;
import com.epam.accountservice.models.Account;
import com.epam.accountservice.models.AccountType;
import com.epam.accountservice.models.Balance;
import com.epam.accountservice.models.Transaction;
import com.epam.accountservice.models.TransactionType;
import com.epam.accountservice.repository.IAccountRepository;
import com.epam.accountservice.repository.IBalanceRepository;
import com.epam.accountservice.repository.ITransactionRepository;
import com.epam.accountservice.service.ITransactionService;
import com.epam.accountservice.util.ObjectsValidator;
import com.epam.accountservice.util.convertor.TransactionConvertor;
import com.epam.accountservice.util.convertor.TransactionViewConvertor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Service
public class TransactionServiceImpl implements ITransactionService {

	private ITransactionRepository transactionRepository;
	private IBalanceRepository balanceRepository;
	private IAccountRepository accountRepository;
	private TransactionViewConvertor transactionViewConvertor;
	private TransactionConvertor transactionConvertor;
	private final ObjectsValidator<TransactionDto> transactionValidator = new ObjectsValidator<>();

	public TransactionServiceImpl(ITransactionRepository transactionRepository, IBalanceRepository balanceRepository,
			IAccountRepository accountRepository, TransactionViewConvertor transactionViewConvertor,
			TransactionConvertor transactionConvertor) {
		super();
		this.transactionRepository = transactionRepository;
		this.balanceRepository = balanceRepository;
		this.accountRepository = accountRepository;
		this.transactionViewConvertor = transactionViewConvertor;
		this.transactionConvertor = transactionConvertor;
	}

	@Override
	public TransactionViewDto transaction(TransactionDto transactionDto) {

		Set<ConstraintViolation<TransactionDto>> violations = transactionValidator.validate(transactionDto);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

		Balance balance = balanceRepository.findByAccountId(transactionDto.getAccountId())
				.orElseThrow(() -> new AccountNotFoundException("Account not found"));

		if (balance.getAccount().getAccountType() == AccountType.PPF)
			checkPPFValidity(balance.getAccount(), transactionDto.getTransactionType());

		if (transactionDto.getTransactionType() == TransactionType.DEBIT) {

			if (balance.getClosingBalance().compareTo(BigDecimal.ZERO) <= 0)
				throw new InsufficientBalanceException("Insufficient account balance");

			balance.setOpeningBalance(balance.getClosingBalance());
			balance.setClosingBalance(balance.getClosingBalance().subtract(transactionDto.getAmount()));

		} else if (transactionDto.getTransactionType() == TransactionType.CREDIT) {
			balance.setOpeningBalance(balance.getClosingBalance());
			balance.setClosingBalance(balance.getClosingBalance().add(transactionDto.getAmount()));
		}

		Transaction transaction = transactionConvertor.convert(transactionDto);
		Integer id = transactionRepository.save(transaction).getId();

		TransactionViewDto transactionViewDto = transactionViewConvertor.convert(balance, transactionDto);
		transactionViewDto.setId(id);
		return transactionViewDto;
	}

	public void checkPPFValidity(Account account, TransactionType transactionType) {

		Period period = Period.between(account.getDateCreated(), LocalDate.now());

		if (transactionType == TransactionType.DEBIT && period.getYears() < 15) {
			throw new PPFMaturityException("PPF Account not yet matured. No DEBIT.");
		} else if (transactionType == TransactionType.CREDIT && period.getYears() >= 15) {
			throw new PPFMaturityException("PPF account matured. No more CREDIT.");
		}
	}
}
