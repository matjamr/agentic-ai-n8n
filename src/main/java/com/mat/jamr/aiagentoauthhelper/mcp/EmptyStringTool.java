package com.mat.jamr.aiagentoauthhelper.mcp;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class EmptyStringTool {


    @Tool(name = "emptyString", description = "Returns an empty string")
    public String returnEmptyString() {
        return "";
    }
}


