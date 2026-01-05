package com.mat.jamr.aiagentoauthhelper.mcp;

/**
 * Centralized metadata for all MCP tools including names and descriptions.
 * This class provides a single source of truth for tool definitions.
 */
public final class ToolMetadata {

    private ToolMetadata() {
        // Utility class - prevent instantiation
    }

    // AuthorizationTool - Logistic Information
    public static final String AUTHORIZE_LOGISTIC_INFORMATION_TOOL_NAME = "authorize_logistic_information";
    public static final String AUTHORIZE_LOGISTIC_INFORMATION_TOOL_DESCRIPTION = 
        "Authorizes a user for logistic information access by validating booking reference and phone number. " +
        "Returns a session token upon successful authorization.";

    // GetLogisticDetailsTool
    public static final String GET_LOGISTIC_DETAILS_TOOL_NAME = "get_logistic_details";
    public static final String GET_LOGISTIC_DETAILS_TOOL_DESCRIPTION = 
        "Retrieves logistic details for a booking by booking reference. " +
        "Requires a valid sessionId obtained from the authorize_logistic_information tool.";

    // DiscoveryService
    public static final String DISCOVER_SCOPES_TOOL_NAME = "discoverScopes";
    public static final String DISCOVER_SCOPES_TOOL_DESCRIPTION = 
        "Discover scopes from authorization code";
}

