package com.mat.jamr.aiagentoauthhelper;

import com.mat.jamr.aiagentoauthhelper.booking.entity.Booking;
import com.mat.jamr.aiagentoauthhelper.booking.repository.BookingRepository;
import com.mat.jamr.aiagentoauthhelper.user.entity.User;
import com.mat.jamr.aiagentoauthhelper.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiAgentOauthHelperApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public static void main(String[] args) {
        SpringApplication.run(AiAgentOauthHelperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = userRepository.save(new User("jamr.mat", "jamr.mat@gmail.com", "+48123456789"));
        User user2 = userRepository.save(new User("modkil265", "modkil265@gmail.com", "+48987654321"));

        // Initialize sample bookings
        if (!bookingRepository.existsByBookingReference("BK001")) {
            bookingRepository.save(new Booking("BK001", user1, "Flight booking from Warsaw to London"));
        }
        if (!bookingRepository.existsByBookingReference("BK002")) {
            bookingRepository.save(new Booking("BK002", user1, "Hotel reservation in Paris"));
        }
        if (!bookingRepository.existsByBookingReference("BK003")) {
            bookingRepository.save(new Booking("BK003", user2, "Car rental in Berlin"));
        }
        if (!bookingRepository.existsByBookingReference("BK004")) {
            bookingRepository.save(new Booking("BK004", user2, "Train ticket from Munich to Vienna"));
        }
        if (!bookingRepository.existsByBookingReference("BK005")) {
            bookingRepository.save(new Booking("BK005", user1, "Concert tickets for Vienna Philharmonic"));
        }
    }
}
