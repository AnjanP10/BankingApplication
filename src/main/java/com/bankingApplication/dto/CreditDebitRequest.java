package com.bankingApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDebitRequest {

    @Schema(
            name = "Credit/Debit Account Number"
    )
    private String accountNumber;

    @Schema(
            name = "Credit/Debit Amount"
    )
    private BigDecimal amount;
}
