package com.epam.customerservice.service;

import com.epam.customerservice.dto.CustomerCredentialDto;
import com.epam.customerservice.dto.CustomerDto;
import com.epam.customerservice.models.Customer;

public interface ICustomerService {
	
	public Customer register(CustomerDto customerDto);
	
	public Customer login(CustomerCredentialDto customerCredentialDto);
	
	public Customer findById(Integer id);

	public Customer deleteById(Integer id);
}
