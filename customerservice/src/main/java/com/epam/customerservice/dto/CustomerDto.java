package com.epam.customerservice.dto;

import com.epam.customerservice.models.Gender;
import com.epam.customerservice.models.IdentityType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {

	private String name;

	private Integer age;

	private Gender gender;

	private IdentityType identityType;

	private String identityNumber;

	private CustomerCredentialDto customerCredentialDto;

}
