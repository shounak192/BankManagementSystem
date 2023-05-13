package com.epam.customerservice.service.implementations;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.epam.customerservice.dto.CustomerCredentialDto;
import com.epam.customerservice.dto.CustomerDto;
import com.epam.customerservice.exceptions.CustomerNotFoundException;
import com.epam.customerservice.exceptions.DuplicateCustomerException;
import com.epam.customerservice.exceptions.InvalidCustomerException;
import com.epam.customerservice.models.Customer;
import com.epam.customerservice.repository.ICustomerRepository;
import com.epam.customerservice.service.ICustomerService;
import com.epam.customerservice.util.ObjectsValidator;
import com.epam.customerservice.util.convertor.CustomerConvertor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

	private ICustomerRepository customerRepository;
	private CustomerConvertor customerConvertor;
	private final ObjectsValidator<CustomerDto> customerValidator = new ObjectsValidator<>();
	private final ObjectsValidator<CustomerCredentialDto> customerCredentialValidator = new ObjectsValidator<>();

	@Override
	public Customer register(CustomerDto customerDto) {

		Set<ConstraintViolation<CustomerDto>> customerViolations = customerValidator.validate(customerDto);
		Set<ConstraintViolation<CustomerCredentialDto>> customerCredentialViolations = customerCredentialValidator
				.validate(customerDto.getCustomerCredentialDto());
		if (!customerViolations.isEmpty() || !customerCredentialViolations.isEmpty())
			throw new ConstraintViolationException(customerViolations);

		Optional<Customer> foundCustomer = customerRepository
				.findByUsername(customerDto.getCustomerCredentialDto().getUsername());
		if (foundCustomer.isPresent())
			throw new DuplicateCustomerException("Duplicate customer present");

		Customer customer = customerConvertor.convert(customerDto);
		return customerRepository.save(customer);
	}

	@Override
	public Customer login(CustomerCredentialDto customerCredentialDto) {

		Set<ConstraintViolation<CustomerCredentialDto>> violations = customerCredentialValidator
				.validate(customerCredentialDto);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

		Customer foundCustomer = customerRepository.findByUsername(customerCredentialDto.getUsername())
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		if (foundCustomer.getPassword().equals(customerCredentialDto.getPassword()))
			return foundCustomer;
		else
			throw new InvalidCustomerException("Invalid customer credentials");
	}

	@Override
	public Customer findById(Integer id) {

		return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
	}

	@Override
	public Customer deleteById(Integer id) {
		Customer foundCustomer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		customerRepository.deleteById(id);
		return foundCustomer;
	}

}
