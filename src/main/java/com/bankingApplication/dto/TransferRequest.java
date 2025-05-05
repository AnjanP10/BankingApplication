package com.bankingApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TransferRequest {

    @Schema(
            name = "Source Account Number"
    )
    private String sourceAccountNumber;

    @Schema(
            name = "Destination Account Number"
    )
    private String destinationAccountNumber;

    @Schema(
            name = "Transfered Amount"
    )
    private BigDecimal amount;
}
