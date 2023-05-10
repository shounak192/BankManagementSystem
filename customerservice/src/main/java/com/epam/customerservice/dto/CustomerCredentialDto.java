package com.epam.customerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerCredentialDto {

	@NotBlank(message="username cannot be blank")
	@Pattern(regexp = "^[a-zA-Z0-9]{3,10}$", message = "Username must be alphanumeric of length between 3 and 10")
	private String username;

	@NotBlank(message="password cannot be blank")
	private String password;

}
