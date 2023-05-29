package com.epam.accountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.accountservice.models.Account;
import com.epam.accountservice.models.Balance;

@Repository
public interface IBalanceRepository extends JpaRepository<Balance, Integer> {

	Optional<Balance> findByAccountId(Integer accountId);
}
