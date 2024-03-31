package com.bank.transaction.repository;

import com.bank.transaction.model.TransactionHistory;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionHistory, Long> {

}
