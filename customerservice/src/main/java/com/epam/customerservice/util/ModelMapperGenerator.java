package com.epam.customerservice.util;

import org.modelmapper.ModelMapper;

public class ModelMapperGenerator {

	static ModelMapper mapper;

	private ModelMapperGenerator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ModelMapper getMapper() {
		if (mapper.equals(null))
			mapper = new ModelMapper();
		return mapper;
	}

}
