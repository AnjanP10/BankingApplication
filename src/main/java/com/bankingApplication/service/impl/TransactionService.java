package com.bankingApplication.service.impl;

import com.bankingApplication.dto.TransactionDTO;
import com.bankingApplication.entity.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

     void saveTransaction(TransactionDTO transactionDTO);
}
