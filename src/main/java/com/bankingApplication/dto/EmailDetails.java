package com.bankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailDetails {

    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;

}
