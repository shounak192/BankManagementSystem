package com.epam.customerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class CustomerNotFoundException extends RuntimeException {

	private final String message;
}
