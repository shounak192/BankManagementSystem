package com.epam.customerservice.exceptions;

public class CustomerNotFoundException extends RuntimeException {

	private final String message;

	public CustomerNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
