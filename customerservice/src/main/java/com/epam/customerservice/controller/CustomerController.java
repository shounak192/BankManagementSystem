package com.epam.customerservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customerservice.dto.CustomerDto;
import com.epam.customerservice.models.Customer;
import com.epam.customerservice.service.ICustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

	private ICustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<Customer> register(@RequestBody CustomerDto customerDto) {
		return new ResponseEntity<>(customerService.register(customerDto), HttpStatus.OK);
	}
	
	
}
