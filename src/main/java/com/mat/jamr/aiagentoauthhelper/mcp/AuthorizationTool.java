package com.mat.jamr.aiagentoauthhelper.mcp;

import com.mat.jamr.aiagentoauthhelper.authorization.SessionService;
import com.mat.jamr.aiagentoauthhelper.booking.entity.Booking;
import com.mat.jamr.aiagentoauthhelper.booking.repository.BookingRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationTool {

    private final BookingRepository bookingRepository;
    private final SessionService sessionService;

    public AuthorizationTool(BookingRepository bookingRepository, SessionService sessionService) {
        this.bookingRepository = bookingRepository;
        this.sessionService = sessionService;
    }

    @Tool(name = ToolMetadata.AUTHORIZE_LOGISTIC_INFORMATION_TOOL_NAME, description = ToolMetadata.AUTHORIZE_LOGISTIC_INFORMATION_TOOL_DESCRIPTION)
    public String authorizeLogisticInformation(String bookingReference, String phoneNumber) {
        // Find booking by reference
        Optional<Booking> bookingOpt = bookingRepository.findByBookingReference(bookingReference);
        if (bookingOpt.isEmpty()) {
            return "Error: Booking not found with reference: " + bookingReference;
        }

        Booking booking = bookingOpt.get();
        String userPhoneNumber = booking.getUser().getPhoneNumber();

        // Validate phone number matches the booking's user
        if (!userPhoneNumber.equals(phoneNumber)) {
            return "Error: Invalid phone number for this booking reference.";
        }

        // Create session token
        String sessionToken = sessionService.createSession(
                bookingReference,
                phoneNumber,
                booking.getUser().getId()
        );

        return sessionToken;
    }
}

