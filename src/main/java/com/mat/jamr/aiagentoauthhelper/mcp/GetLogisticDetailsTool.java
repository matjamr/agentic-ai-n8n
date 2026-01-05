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

    @Tool(name = ToolMetadata.GET_LOGISTIC_DETAILS_TOOL_NAME, description = ToolMetadata.GET_LOGISTIC_DETAILS_TOOL_DESCRIPTION)
    public String getLogisticDetails(String sessionId, String bookingReference) {
        // Validate sessionId
        if (sessionId == null || sessionId.isBlank() || !securityService.validateTokenForBooking(sessionId, bookingReference)) {
            return "Error: Invalid or unauthorized sessionId. Please provide a valid sessionId obtained from the authorize_logistic_information tool.";
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

