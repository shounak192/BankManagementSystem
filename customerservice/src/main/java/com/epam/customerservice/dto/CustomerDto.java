package com.epam.customerservice.dto;

import com.epam.customerservice.models.Gender;
import com.epam.customerservice.models.IdentityType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {

	@NotBlank
	private String name;

	@Positive
	@Max(value = 99, message = "Age must be valid")
	private Integer age;

	private Gender gender;

	@NotBlank(message = "Identity Type must be Pan/Aadhar/DL/Passport")
	private IdentityType identityType;

	@NotBlank
	private String identityNumber;

	private CustomerCredentialDto customerCredentialDto;

}
