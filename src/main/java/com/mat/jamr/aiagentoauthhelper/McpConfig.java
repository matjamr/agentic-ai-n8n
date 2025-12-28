package com.mat.jamr.aiagentoauthhelper;

import com.mat.jamr.aiagentoauthhelper.authorization.DiscoveryService;
import com.mat.jamr.aiagentoauthhelper.mcp.AuthorizationTool;
import com.mat.jamr.aiagentoauthhelper.mcp.EmptyStringTool;
import com.mat.jamr.aiagentoauthhelper.mcp.GetLogisticDetailsTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider emptyStringTools(EmptyStringTool emptyStringTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(emptyStringTool)
                .build();
    }

    @Bean
    public ToolCallbackProvider discoveryServiceTools(DiscoveryService discoveryService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(discoveryService)
                .build();
    }

    @Bean
    public ToolCallbackProvider getLogisticDetailsTools(GetLogisticDetailsTool getLogisticDetailsTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(getLogisticDetailsTool)
                .build();
    }

    @Bean
    public ToolCallbackProvider authorizationTools(AuthorizationTool authorizationTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(authorizationTool)
                .build();
    }
}

