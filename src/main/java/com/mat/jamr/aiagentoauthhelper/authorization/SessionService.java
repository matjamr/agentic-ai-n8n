package com.mat.jamr.aiagentoauthhelper.authorization;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();

    public String createSession(String bookingReference, String phoneNumber, String userId) {
        String sessionToken = UUID.randomUUID().toString();
        SessionInfo sessionInfo = new SessionInfo(bookingReference, phoneNumber, userId);
        sessions.put(sessionToken, sessionInfo);
        return sessionToken;
    }

    public SessionInfo getSession(String sessionToken) {
        return sessions.get(sessionToken);
    }

    public boolean isValidSession(String sessionToken) {
        return sessions.containsKey(sessionToken);
    }

    public void removeSession(String sessionToken) {
        sessions.remove(sessionToken);
    }

    public static class SessionInfo {
        private final String bookingReference;
        private final String phoneNumber;
        private final String userId;

        public SessionInfo(String bookingReference, String phoneNumber, String userId) {
            this.bookingReference = bookingReference;
            this.phoneNumber = phoneNumber;
            this.userId = userId;
        }

        public String getBookingReference() {
            return bookingReference;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getUserId() {
            return userId;
        }
    }
}

