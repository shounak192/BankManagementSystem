package com.epam.customerservice.util;

import java.util.Collections;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ObjectsValidator<T> {
	
	private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	private final Validator validator = validatorFactory.getValidator();
	
	public Set<ConstraintViolation<T>> validate(T objectToValidate) {
		Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
		if(!violations.isEmpty()) {
			return violations;
		}
		return Collections.emptySet();
	}

}
