package com.mat.jamr.aiagentoauthhelper.authorization;

import com.mat.jamr.aiagentoauthhelper.authorization.dto.Intent;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DiscoveryService {

    private static final Map<Intent, String> SCOPES_BY_INTENT = Map.of(Intent.LOGISTIC_INFORMATION, "Mapping of intents to their required scopes.\n" +
            "     * For logistic information, valid booking reference and phone number are required.");

    @Tool(name = "discoverScopes", description = "Discover scopes from authorization code")
    public String discoverScopes(Intent intent) {
        return SCOPES_BY_INTENT.getOrDefault(intent, "Unsuported, please redirect to human agent");
    }
}
