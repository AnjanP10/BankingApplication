package com.bankingApplication.service.impl;

import com.bankingApplication.dto.AccountInfo;
import com.bankingApplication.dto.BankResponse;
import com.bankingApplication.dto.CreditDebitRequest;
import com.bankingApplication.dto.EmailDetails;
import com.bankingApplication.dto.EnquiryRequest;
import com.bankingApplication.dto.TransactionDTO;
import com.bankingApplication.dto.TransferRequest;
import com.bankingApplication.dto.UserRequest;
import com.bankingApplication.entity.Transaction;
import com.bankingApplication.entity.User;
import com.bankingApplication.repository.UserRepository;
import com.bankingApplication.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        // creating an account - saving a new user info into the db
        // check if user already has an account

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);
        //send email alerts
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("New Account Created")
                .messageBody("Congratulations !!! Your new account has been created. \n " +
                        "Account Name : " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " +savedUser.getOtherName() +
                        "\nAccount Number : " + savedUser.getAccountNumber())
                .attachment(null)
                .build();
        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
        .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        //check if the provided account number exists in db .
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {

        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExists) {
            return AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE;
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();

    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        //checking if the account exists
       boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
       if (!isAccountExists) {
           return BankResponse.builder()
                   .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                   .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                   .accountInfo(null)
                   .build();
       }
       User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
       userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
       userRepository.save(userToCredit);

       // Save Transaction
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();
        transactionService.saveTransaction(transactionDTO);

       return BankResponse.builder()
               .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
               .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
               .accountInfo(AccountInfo.builder()
                       .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                       .accountBalance(userToCredit.getAccountBalance())
                       .accountNumber(userToCredit.getAccountNumber())
                       .build())
               .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        // checks if the account exists or not
        // checks if the amount you intend to withdraw is not more than current account amount
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
    }
        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if(availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(userToDebit);

            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .transactionType("DEBIT")
                    .amount(request.getAmount())
                    .build();
            transactionService.saveTransaction(transactionDTO);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .build())
                    .build();
        }
    }
// Full Transfer Services , Debited , Credited
    @Override
    public BankResponse transfer(TransferRequest request) {
        // get the account to debit (check if it exists)
        // check if the amount i'm debitting is not more than current balance
        // debit the account
        // get the account to credit
        // credit the account
        boolean isDestinationAccountExists = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        if (!isDestinationAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        if(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
        String sourceUsername = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " " + sourceAccountUser.getOtherName();
        userRepository.save(sourceAccountUser);
        EmailDetails debitAlert = EmailDetails.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of " + request.getAmount() + "has been deducted from your account ! Your current balance is " + sourceAccountUser.getAccountBalance())
                .build();

        emailService.sendEmailAlert(debitAlert);

        User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
        //String recipientUsername = destinationAccountUser.getFirstName() + " " + destinationAccountUser.getLastName() + " " + destinationAccountUser.getOtherName();
        userRepository.save(destinationAccountUser);
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("The sum of Rs " + request.getAmount() + " has been sent to your account from " + sourceUsername + " . Your current balance is Rs " + destinationAccountUser.getAccountBalance())
                .build();

        emailService.sendEmailAlert(creditAlert);

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .accountNumber(destinationAccountUser.getAccountNumber())
                .transactionType("TRANSFER")
                .amount(request.getAmount())
                .build();
        transactionService.saveTransaction(transactionDTO);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }

}
