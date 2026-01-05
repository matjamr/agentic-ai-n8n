package com.mat.jamr.aiagentoauthhelper.authorization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthorizationRequest {
    @NotNull(message = "Intent is required")
    private Intent intent;

    private String bookingReference;
    private String phoneNumber;
}



