package com.epam.accountservice.util;

import org.modelmapper.ModelMapper;

public class ModelMapperGenerator {

	static ModelMapper mapper;

	private ModelMapperGenerator() {
		super();
	}

	public static ModelMapper getMapper() {
		if (mapper.equals(null))
			mapper = new ModelMapper();
		return mapper;
	}

}
