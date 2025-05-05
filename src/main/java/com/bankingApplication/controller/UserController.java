package com.bankingApplication.controller;

import com.bankingApplication.dto.BankResponse;
import com.bankingApplication.dto.CreditDebitRequest;
import com.bankingApplication.dto.EnquiryRequest;
import com.bankingApplication.dto.TransferRequest;
import com.bankingApplication.dto.UserRequest;
import com.bankingApplication.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Account Management APIs")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(
            summary = "Create New User Account",
            description = "Creating a new user and assigning an account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @Operation(
            summary = "Name Enquiry",
            description = "Given an account number, checks the user's name"
    )
    @ApiResponse(
            responseCode = "202",
            description = "Http Status 202 CREATED"
    )
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request) {
        return userService.nameEnquiry(request);
    }

    @Operation(
            summary = "Balance Enquiry",
            description = "Given an account number, checks the user's balance"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 CREATED"
    )
    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
        return userService.balanceEnquiry(request);
    }

    @Operation(
            summary = "Balance Credited",
            description = "Balance credited in user's account"
    )
    @ApiResponse(
            responseCode = "203",
            description = "Http Status 203 CREATED"
    )
    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
        return userService.creditAccount(request);
    }

    @Operation(
            summary = "Balance Debited",
            description = "Balance debited from user's account"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 CREATED"
    )
    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
        return userService.debitAccount(request);
    }

    @Operation(
            summary = "Balance Transfer",
            description = "Balance transfer from source account to destination account"
    )
    @ApiResponse(
            responseCode = "205",
            description = "Http Status 205 CREATED"
    )
    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request) {
        return userService.transfer(request);
    }
}
