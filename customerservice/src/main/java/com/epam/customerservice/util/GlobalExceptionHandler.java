package com.epam.customerservice.util;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.customerservice.exceptions.CustomerNotFoundException;
import com.epam.customerservice.exceptions.DuplicateCustomerException;
import com.epam.customerservice.exceptions.InvalidCustomerException;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { CustomerNotFoundException.class, DuplicateCustomerException.class,
			InvalidCustomerException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorResponse> handleGlobalException(WebRequest request, Exception e) {

		ErrorResponse response = new ErrorResponse();
		response.setError(e.getMessage());
		response.setStatus(HttpStatus.BAD_REQUEST.name());
		response.setTimeStamp(LocalDate.now());
		response.setPath(request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponse {

	private String status;
	private String error;
	private LocalDate timeStamp;
	private String path;
}
