package com.epam.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerCredentialDto {
	
	private String username;
	
	private String password;

}
