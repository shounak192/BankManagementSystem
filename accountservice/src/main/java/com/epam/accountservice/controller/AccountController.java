package com.epam.accountservice.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.epam.accountservice.dto.AccountDto;
import com.epam.accountservice.dto.AccountViewDto;
import com.epam.accountservice.dto.Customer;
import com.epam.accountservice.service.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private IAccountService accountService;

	private AccountController(IAccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@PostMapping("/create/{customerId}")
	public ResponseEntity<AccountViewDto> create(@PathVariable Integer customerId, @RequestBody AccountDto accountDto) {

		return new ResponseEntity<>(accountService.create(customerId, accountDto), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountViewDto> view(@PathVariable Integer id) {

		return new ResponseEntity<>(accountService.view(id), HttpStatus.FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AccountViewDto> delete(@PathVariable Integer id) {

		return new ResponseEntity<>(accountService.delete(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<AccountViewDto>> viewAll() {

		return new ResponseEntity<>(accountService.viewAll(), HttpStatus.FOUND);
	}

	@GetMapping("/balance/{id}")
	public ResponseEntity<BigDecimal> viewBalance(@PathVariable Integer id) {

		return new ResponseEntity<>(accountService.viewBalance(id), HttpStatus.FOUND);
	}

	@GetMapping("/viewaccounts/{customerId}")
	public ResponseEntity<List<AccountViewDto>> viewAllByCustomer(@PathVariable Integer customerId) {

		return new ResponseEntity<>(accountService.viewAllByCustomer(customerId), HttpStatus.FOUND);
	}

}
