package com.bankingApplication.service.impl;

import com.bankingApplication.dto.TransactionDTO;
import com.bankingApplication.entity.Transaction;
import com.bankingApplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDTO.getTransactionType())
                .accountNumber(transactionDTO.getAccountNumber())
                .amount(transactionDTO.getAmount())
                .status("SUCCESS")
                .build();
        transactionRepository.save(transaction);
        System.out.println("Transaction Saved Successfully ");
    }
}
