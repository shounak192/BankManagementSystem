package com.epam.accountservice.service.implementations;

import org.springframework.stereotype.Service;

import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.dto.TransactionViewDto;
import com.epam.accountservice.exceptions.AccountNotFoundException;
import com.epam.accountservice.models.Balance;
import com.epam.accountservice.models.Transaction;
import com.epam.accountservice.models.TransactionType;
import com.epam.accountservice.repository.IAccountRepository;
import com.epam.accountservice.repository.IBalanceRepository;
import com.epam.accountservice.repository.ITransactionRepository;
import com.epam.accountservice.service.ITransactionService;
import com.epam.accountservice.util.convertor.TransactionConvertor;
import com.epam.accountservice.util.convertor.TransactionViewConvertor;

import lombok.AllArgsConstructor;

@Service
public class TransactionServiceImpl implements ITransactionService {

	private ITransactionRepository transactionRepository;
	private IAccountRepository accountRepository;
	private IBalanceRepository balanceRepository;
	private TransactionViewConvertor transactionViewConvertor;
	private TransactionConvertor transactionConvertor;

	private TransactionServiceImpl(ITransactionRepository transactionRepository, IAccountRepository accountRepository,
			IBalanceRepository balanceRepository, TransactionViewConvertor transactionViewConvertor,
			TransactionConvertor transactionConvertor) {
		super();
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.balanceRepository = balanceRepository;
		this.transactionViewConvertor = transactionViewConvertor;
		this.transactionConvertor = transactionConvertor;
	}

	@Override
	public TransactionViewDto transaction(TransactionDto transactionDto) {

		Balance balance = balanceRepository.findByAccountId(transactionDto.getAccountId())
				.orElseThrow(() -> new AccountNotFoundException("Account not found"));

		if (transactionDto.getTransactionType() == TransactionType.DEBIT) {
			balance.setOpeningBalance(balance.getClosingBalance());
			balance.setClosingBalance(balance.getClosingBalance().subtract(transactionDto.getAmount()));
			
		} else if (transactionDto.getTransactionType() == TransactionType.CREDIT) {
			balance.setOpeningBalance(balance.getClosingBalance());
			balance.setClosingBalance(balance.getClosingBalance().add(transactionDto.getAmount()));
		}
		
		Transaction transaction = transactionConvertor.convert(transactionDto);
		transactionRepository.save(transaction);
		
		return transactionViewConvertor.convert(balance, transactionDto);
		
	}

}
