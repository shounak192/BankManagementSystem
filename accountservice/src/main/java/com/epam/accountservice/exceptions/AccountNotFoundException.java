package com.epam.accountservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class AccountNotFoundException extends RuntimeException {

	private final String message;

}
