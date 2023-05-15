package com.epam.customerservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.epam.customerservice.dto.CustomerCredentialDto;
import com.epam.customerservice.dto.CustomerDto;
import com.epam.customerservice.exceptions.CustomerNotFoundException;
import com.epam.customerservice.exceptions.DuplicateCustomerException;
import com.epam.customerservice.models.Customer;
import com.epam.customerservice.models.Gender;
import com.epam.customerservice.models.IdentityType;
import com.epam.customerservice.repository.ICustomerRepository;
import com.epam.customerservice.service.implementations.CustomerServiceImpl;
import com.epam.customerservice.util.convertor.CustomerConvertor;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Mock
	private ICustomerRepository customerRepository;

	@Mock
	private CustomerConvertor customerConvertor;

	private CustomerDto customerDto;

	private CustomerCredentialDto customerCredentialDto;

	private Customer customer;

	private Optional<Customer> optionalCustomer;

	@BeforeEach
	void setup() {
		customerCredentialDto = new CustomerCredentialDto("username", "password");
		customerDto = new CustomerDto("name", 10, Gender.MALE, IdentityType.PAN, "IDT123", customerCredentialDto);
		customer = new Customer(1, "name", 10, Gender.MALE, IdentityType.PAN, "IDT123", "username", "password");
		optionalCustomer = Optional.ofNullable(customer);
		when(customerConvertor.convert(customerDto)).thenReturn(customer);
	}

	@Test
	void registerTest() {

		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customer, customerService.register(customerDto));
	}

	@Test
	void InvalidRegisterTest() {

		when(customerRepository.findByUsername("username")).thenReturn(optionalCustomer);
		assertThrows(DuplicateCustomerException.class, () -> customerService.register(customerDto));
	}

	@Test
	void loginTest() {

		when(customerRepository.findByUsername("username")).thenReturn(optionalCustomer);
		assertEquals(customer, customerService.login(customerCredentialDto));
	}

	@Test
	void InvalidLoginTest() {
		when(customerRepository.findByUsername("username")).thenReturn(Optional.ofNullable(null));
		assertThrows(CustomerNotFoundException.class, () -> customerService.login(customerCredentialDto));
	}

	@Test
	void findByIdTest() {

		when(customerRepository.findById(1)).thenReturn(optionalCustomer);
		assertEquals(customer, customerService.findById(1));
	}

	@Test
	void InvalidFindByIdTest() {
		when(customerRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(CustomerNotFoundException.class, () -> customerService.findById(1));
	}

	@Test
	void deleteByIdTest() {
		when(customerRepository.findById(1)).thenReturn(optionalCustomer);
		assertEquals(customer, customerService.deleteById(1));
	}

	@Test
	void InvalidDeleteByIdTest() {
		when(customerRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(CustomerNotFoundException.class, () -> customerService.deleteById(1));
	}
}
