package com.epam.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.accountservice.dto.AccountViewDto;
import com.epam.accountservice.models.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

	List<Account> findAllByCustomerId(Integer customerId);
}
