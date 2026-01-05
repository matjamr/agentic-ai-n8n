package com.mat.jamr.aiagentoauthhelper.authorization.controller;

import com.mat.jamr.aiagentoauthhelper.authorization.SessionService;
import com.mat.jamr.aiagentoauthhelper.authorization.dto.AuthorizationRequest;
import com.mat.jamr.aiagentoauthhelper.authorization.dto.AuthorizationResponse;
import com.mat.jamr.aiagentoauthhelper.authorization.dto.Intent;
import com.mat.jamr.aiagentoauthhelper.booking.entity.Booking;
import com.mat.jamr.aiagentoauthhelper.booking.repository.BookingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<AuthorizationResponse> authorize(@Valid @RequestBody AuthorizationRequest request) {
        // Validate intent
        if (request.getIntent() != Intent.LOGISTIC_INFORMATION) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthorizationResponse(null, "Error: Unsupported intent. Only LOGISTIC_INFORMATION is supported."));
        }

        // Find booking by reference
        var bookingOpt = bookingRepository.findByBookingReference(request.getBookingReference());
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthorizationResponse(null, "Error: Booking not found with reference: " + request.getBookingReference()));
        }

        Booking booking = bookingOpt.get();
        String userPhoneNumber = booking.getUser().getPhoneNumber();

        // Validate phone number matches the booking's user
        if (!userPhoneNumber.equals(request.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthorizationResponse(null, "Error: Invalid phone number for this booking reference."));
        }

        // Create session token
        String sessionToken = sessionService.createSession(
                request.getBookingReference(),
                request.getPhoneNumber(),
                booking.getUser().getId()
        );

        return ResponseEntity.ok(new AuthorizationResponse(sessionToken, "Authorization successful"));
    }

    @GetMapping("/session/{sessionToken}")
    public ResponseEntity<SessionService.SessionInfo> getSession(@PathVariable String sessionToken) {
        SessionService.SessionInfo sessionInfo = sessionService.getSession(sessionToken);
        if (sessionInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessionInfo);
    }

    @DeleteMapping("/session/{sessionToken}")
    public ResponseEntity<Void> invalidateSession(@PathVariable String sessionToken) {
        if (sessionService.isValidSession(sessionToken)) {
            sessionService.removeSession(sessionToken);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



