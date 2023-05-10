package com.epam.customerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerCredentialDto {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]{3,10}$", message = "Username must be alphanumeric of length between 3 and 10")
	private String username;

	@NotBlank
	private String password;

}
