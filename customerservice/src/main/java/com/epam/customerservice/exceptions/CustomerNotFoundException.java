package com.epam.customerservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class CustomerNotFoundException extends RuntimeException {

	private final String message;
}
