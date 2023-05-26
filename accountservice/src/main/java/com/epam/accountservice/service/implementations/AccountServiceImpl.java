package com.epam.accountservice.service.implementations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.epam.accountservice.dto.AccountDto;
import com.epam.accountservice.dto.AccountViewDto;
import com.epam.accountservice.dto.Customer;
import com.epam.accountservice.dto.TransactionDto;
import com.epam.accountservice.exceptions.AccountNotFoundException;
import com.epam.accountservice.exceptions.CustomerNotFoundException;
import com.epam.accountservice.models.Account;
import com.epam.accountservice.models.Balance;
import com.epam.accountservice.repository.IAccountRepository;
import com.epam.accountservice.service.IAccountService;
import com.epam.accountservice.util.ObjectsValidator;
import com.epam.accountservice.util.convertor.AccountConvertor;
import com.epam.accountservice.util.convertor.AccountViewConvertor;
import com.epam.accountservice.util.convertor.BalanceConvertor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Service
public class AccountServiceImpl implements IAccountService {

	private BalanceConvertor balanceConvertor;
	private AccountConvertor accountConvertor;
	private AccountViewConvertor accountViewConvertor;
	private IAccountRepository accountRepository;
	private final ObjectsValidator<AccountDto> accountValidator = new ObjectsValidator<>();

	@Autowired
	@Qualifier("getCustomerServiceAppWebClient")
	private WebClient customerServiceAppWebClientBuilder;

	public AccountServiceImpl(BalanceConvertor balanceConvertor, AccountConvertor accountConvertor,
			AccountViewConvertor accountViewConvertor, IAccountRepository accountRepository) {
		super();
		this.balanceConvertor = balanceConvertor;
		this.accountConvertor = accountConvertor;
		this.accountViewConvertor = accountViewConvertor;
		this.accountRepository = accountRepository;
	}

	@Override
	public AccountViewDto create(Integer customerId, AccountDto accountDto) {

		Set<ConstraintViolation<AccountDto>> violations = accountValidator.validate(accountDto);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

		try {
			customerServiceAppWebClientBuilder.get().uri("/customer/{customerId}", customerId).retrieve()
					.bodyToMono(Customer.class).block();
		} catch (WebClientResponseException e) {
			throw new CustomerNotFoundException("Customer not found");
		}

		Balance balance = balanceConvertor.convert();
		Account account = accountConvertor.convert(customerId, accountDto);
		balance.setAccount(account);
		account.setBalance(balance);
		accountRepository.save(account);
		return accountViewConvertor.convert(account);
	}

	@Override
	public AccountViewDto view(Integer id) {

		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("Account not found"));
		return accountViewConvertor.convert(account);
	}

	@Override
	public AccountViewDto delete(Integer id) {

		Optional<Account> optionalAccount = Optional.ofNullable(
				accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found")));

		accountRepository.deleteById(id);
		return accountViewConvertor.convert(optionalAccount.get());

	}

	@Override
	public List<AccountViewDto> viewAll() {

		return accountRepository.findAll().stream().map((account) -> accountViewConvertor.convert(account)).toList();
	}

	@Override
	public BigDecimal viewBalance(Integer id) {

		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("Account Not found"));
		return account.getBalance().getClosingBalance();
	}

	@Override
	public List<AccountViewDto> viewAllByCustomer(Integer customerId) {

		try {
			customerServiceAppWebClientBuilder.get().uri("/customer/{customerId}", customerId).retrieve()
					.bodyToMono(Customer.class).block();
		} catch (WebClientResponseException e) {
			throw new CustomerNotFoundException("Customer not found");
		}

		return accountRepository.findAllByCustomerId(customerId).stream()
				.map((account) -> accountViewConvertor.convert(account)).toList();
	}

}
