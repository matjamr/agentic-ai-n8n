package com.mat.jamr.aiagentoauthhelper.mcp;

import com.mat.jamr.aiagentoauthhelper.authorization.SecurityService;
import com.mat.jamr.aiagentoauthhelper.booking.entity.Booking;
import com.mat.jamr.aiagentoauthhelper.booking.repository.BookingRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetLogisticDetailsTool {

    private final BookingRepository bookingRepository;
    private final SecurityService securityService;

    public GetLogisticDetailsTool(BookingRepository bookingRepository, SecurityService securityService) {
        this.bookingRepository = bookingRepository;
        this.securityService = securityService;
    }

    @Tool(name = "get_logistic_details", description = "Retrieves logistic details for a booking by booking reference. Requires a valid session token in the X-Session-Token or Authorization header (Bearer token) obtained from the authorize tool.")
    public String getLogisticDetails(String bookingReference) {
        // Extract session token from headers
        String sessionToken = securityService.extractSessionTokenFromHeaders();
        
        // Validate session token
        if (sessionToken == null || !securityService.validateTokenForBooking(sessionToken, bookingReference)) {
            return "Error: Invalid or unauthorized session token. Please provide a valid session token in the X-Session-Token or Authorization header (Bearer token).";
        }

        Optional<Booking> booking = bookingRepository.findByBookingReference(bookingReference);
        
        if (booking.isPresent()) {
            Booking b = booking.get();
            return String.format(
                "Booking Reference: %s\nUser ID: %s\nDescription: %s\nCreated At: %s",
                b.getBookingReference(),
                b.getUser().getId(),
                b.getDescription() != null ? b.getDescription() : "No description",
                b.getCreatedAt()
            );
        } else {
            return "Booking not found with reference: " + bookingReference;
        }
    }
}

