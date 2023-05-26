package com.epam.accountservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceViewDto {
	
	private Integer id;
	
	private Integer accountId;
	
	private BigDecimal closingBalance;

}
