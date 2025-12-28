package com.mat.jamr.aiagentoauthhelper.authorization;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

    private final SessionService sessionService;
    private static final String SESSION_TOKEN_HEADER = "X-Session-Token";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    public SecurityService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Extracts the session token from HTTP headers.
     * Checks both X-Session-Token header and Authorization header (Bearer token format)
     * @return The session token if found, null otherwise
     */
    public String extractSessionTokenFromHeaders() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();
        
        // First, try X-Session-Token header
        String sessionToken = request.getHeader(SESSION_TOKEN_HEADER);
        if (sessionToken != null && !sessionToken.isBlank()) {
            return sessionToken.trim();
        }

        // Then, try Authorization header with Bearer prefix
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length()).trim();
        }

        return null;
    }

    /**
     * Validates a session token and checks if it authorizes access to a specific booking reference
     * @param sessionToken The session token to validate
     * @param bookingReference The booking reference to check access for
     * @return true if the token is valid and authorizes access to the booking reference
     */
    public boolean validateTokenForBooking(String sessionToken, String bookingReference) {
        if (sessionToken == null || sessionToken.isBlank()) {
            return false;
        }

        if (!sessionService.isValidSession(sessionToken)) {
            return false;
        }

        SessionService.SessionInfo sessionInfo = sessionService.getSession(sessionToken);
        if (sessionInfo == null) {
            return false;
        }

        // Check if the session's booking reference matches the requested booking reference
        return sessionInfo.getBookingReference().equals(bookingReference);
    }

    /**
     * Validates a session token
     * @param sessionToken The session token to validate
     * @return true if the token is valid
     */
    public boolean isValidToken(String sessionToken) {
        if (sessionToken == null || sessionToken.isBlank()) {
            return false;
        }
        return sessionService.isValidSession(sessionToken);
    }

    /**
     * Gets session info for a valid token
     * @param sessionToken The session token
     * @return SessionInfo if token is valid, null otherwise
     */
    public SessionService.SessionInfo getSessionInfo(String sessionToken) {
        if (!isValidToken(sessionToken)) {
            return null;
        }
        return sessionService.getSession(sessionToken);
    }
}

