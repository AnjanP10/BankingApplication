package com.bankingApplication.service.impl;

import com.bankingApplication.dto.BankResponse;
import com.bankingApplication.dto.CreditDebitRequest;
import com.bankingApplication.dto.EnquiryRequest;
import com.bankingApplication.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
}
