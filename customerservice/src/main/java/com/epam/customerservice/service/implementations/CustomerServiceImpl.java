package com.epam.customerservice.service.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.epam.customerservice.dto.CustomerCredentialDto;
import com.epam.customerservice.dto.CustomerDto;
import com.epam.customerservice.exceptions.CustomerNotFoundException;
import com.epam.customerservice.exceptions.DuplicateCustomerException;
import com.epam.customerservice.models.Customer;
import com.epam.customerservice.repository.ICustomerRepository;
import com.epam.customerservice.service.ICustomerService;
import com.epam.customerservice.util.convertor.CustomerConvertor;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

	private ICustomerRepository customerRepository;
	private CustomerConvertor customerConvertor;

	@Override
	public Customer register(CustomerDto customerDto) {

		Optional<Customer> foundCustomer = customerRepository
				.findByUsername(customerDto.getCustomerCredentialDto().getUsername());
		if (foundCustomer.isPresent())
			throw new DuplicateCustomerException("Duplicate customer present");

		Customer customer = customerConvertor.convert(customerDto);
		return customerRepository.save(customer);
	}

	@Override
	public Customer login(CustomerCredentialDto customerCredentialDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findByUsername(String username) {

		return customerRepository.findByUsername(username)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
	}

}
