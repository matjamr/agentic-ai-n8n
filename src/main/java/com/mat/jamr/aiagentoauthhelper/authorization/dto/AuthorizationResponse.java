package com.mat.jamr.aiagentoauthhelper.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationResponse {
    private String sessionToken;
    private String message;
}

