package com.paymentDashboard.dashboard.services;

// BookingService.java

import com.paymentDashboard.dashboard.domain.Booking;
import com.paymentDashboard.dashboard.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<String> getAvailableTimeSlots(LocalDateTime date) {
        // You need to customize this method based on your database schema and logic
        // Here, we assume you have a Booking entity with a field "dateTime"
        // and we're using BookingRepository to query the database

        // Example: Retrieve booked time slots for the given date
        List<Booking> bookedSlots = bookingRepository.findByDateTimeBetween(date, date.plusDays(1));

        // Example: Generate a list of all time slots for the day
        List<String> allTimeSlots = generateAllTimeSlots();

        // Example: Remove booked time slots from the list of all time slots
        allTimeSlots.removeAll(bookedSlots.stream()
                .map(booking -> booking.getDateTime().toLocalTime().toString())
                .toList());

        return allTimeSlots;
    }

    public void bookTimeSlot(Booking booking) {
        // Implement logic to save the booking to the database
        // You may want to customize this based on your requirements
        bookingRepository.save(booking);
    }

    private List<String> generateAllTimeSlots() {
        // Specify your time interval and format
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        int intervalMinutes = 15;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

        List<String> timeSlots = new ArrayList<>();

        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            timeSlots.add(currentTime.format(formatter));
            currentTime = currentTime.plusMinutes(intervalMinutes);
        }

        return timeSlots;
    }
}

