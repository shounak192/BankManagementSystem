package com.epam.customerservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_generator")
	@SequenceGenerator(name = "cust_generator", sequenceName = "cust_sequence", initialValue = 10, allocationSize = 2)
	private Integer id;

	private String name;

	private Integer age;

	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private IdentityType identityType;

	private String identityNumber;
	
	private String username;
	
	private String password;

}
