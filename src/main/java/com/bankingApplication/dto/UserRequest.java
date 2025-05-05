package com.bankingApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Schema(
            name = "User's Firstname"
    )
    private String firstName;

    @Schema(
            name = "User's Lastname"
    )
    private String lastName;

    @Schema(
            name = "User's Othername"
    )
    private String otherName;

    @Schema(
            name = "User's Gender"
    )
    private String gender;

    @Schema(
            name = "User's Address"
    )
    private String address;

    @Schema(
            name = "User's State of Origin"
    )
    private String stateOfOrigin;

    @Schema(
            name = "User's Phone Number"
    )
    private String phoneNumber;

    @Schema(
            name = "User's Email"
    )
    private String email;

    @Schema(
            name = "User's Alternative Phone Number"
    )
    private String alternativePhoneNumber;
}
