package com.bank.transaction.repository;

import com.bank.transaction.model.AccountDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountDetails, Long> {
    List<AccountDetails> findByBankAccountNumber(Long bankAccountNumber);
}
