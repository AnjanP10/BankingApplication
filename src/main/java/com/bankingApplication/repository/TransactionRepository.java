package com.bankingApplication.repository;

import com.bankingApplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository< Transaction, String> {

}
