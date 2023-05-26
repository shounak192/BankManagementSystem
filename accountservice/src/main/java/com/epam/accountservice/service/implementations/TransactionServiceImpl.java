package com.epam.accountservice.service.implementations;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.exceptions.AccountNotFoundException;
import com.epam.accountservice.exceptions.InsufficientBalanceException;
import com.epam.accountservice.models.Balance;
import com.epam.accountservice.models.Transaction;
import com.epam.accountservice.models.TransactionType;
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
	private TransactionViewConvertor transactionViewConvertor;
	private TransactionConvertor transactionConvertor;
	private final ObjectsValidator<TransactionDto> transactionValidator = new ObjectsValidator<>();

	private TransactionServiceImpl(ITransactionRepository transactionRepository, IBalanceRepository balanceRepository,
			TransactionViewConvertor transactionViewConvertor, TransactionConvertor transactionConvertor) {
		super();
		this.transactionRepository = transactionRepository;
		this.balanceRepository = balanceRepository;
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

}
