package com.epam.customerservice.dto;

import com.epam.customerservice.models.Gender;
import com.epam.customerservice.models.IdentityType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {

	@NotBlank
	
	private String name;

	@Positive
	@Max(value = 120, message = "Age must be valid")
	private Integer age;

	private Gender gender;

	private IdentityType identityType;
	
	@NotBlank
	private String identityNumber;

	@NotNull(message = "Credentials cannot be null")
	@Valid
	private CustomerCredentialDto customerCredentialDto;

}
