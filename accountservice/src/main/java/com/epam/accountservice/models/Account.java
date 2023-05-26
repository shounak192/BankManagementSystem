package com.epam.accountservice.models;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer customerId;

	@Enumerated(EnumType.ORDINAL)
	private AccountType accountType;

	private LocalDate dateCreated;

	@Enumerated(EnumType.ORDINAL)
	private CurrencyUnit unit;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.LAZY)
	private Balance balance;
}
