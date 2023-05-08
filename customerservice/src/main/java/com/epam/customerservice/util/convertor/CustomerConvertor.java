package com.epam.customerservice.util.convertor;

import org.springframework.stereotype.Component;

import com.epam.customerservice.dto.CustomerDto;
import com.epam.customerservice.models.Customer;

@Component
public class CustomerConvertor {

	public Customer convert(CustomerDto customerDto) {

		return Customer.builder().name(customerDto.getName()).age(customerDto.getAge()).gender(customerDto.getGender())
				.identityType(customerDto.getIdentityType()).identityNumber(customerDto.getIdentityNumber())
				.username(customerDto.getCustomerCredentialDto().getUsername())
				.password(customerDto.getCustomerCredentialDto().getPassword()).build();

	}

}
