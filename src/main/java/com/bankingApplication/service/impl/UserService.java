package com.bankingApplication.service.impl;

import com.bankingApplication.dto.BankResponse;
import com.bankingApplication.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
}
