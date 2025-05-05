package com.bankingApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailDetails {

    @Schema(
            name = "Email Recipient"
    )
    private String recipient;

    @Schema(
            name = "Email Subject"
    )
    private String subject;

    @Schema(
            name = "Email Message Body"
    )
    private String messageBody;

    @Schema(
            name = "Email Attachment"
    )
    private String attachment;

}
