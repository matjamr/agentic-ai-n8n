package com.mat.jamr.aiagentoauthhelper.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScopeBasedAuthorization {
    private Intent intent;
    private String description;
}
