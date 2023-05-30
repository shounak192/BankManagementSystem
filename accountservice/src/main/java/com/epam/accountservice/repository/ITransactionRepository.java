package com.epam.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.accountservice.models.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Integer>{

	List<Transaction> findAllByAccountId(Integer accountId);

	

}
