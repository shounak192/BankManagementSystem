package com.epam.customerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(code = HttpStatus.CONFLICT)
@AllArgsConstructor
public class DuplicateCustomerException extends RuntimeException{
	
	private final String message;

}
