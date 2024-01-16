package com.paymentDashboard.dashboard.repository;
import com.paymentDashboard.dashboard.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Add custom queries if needed
    List<Booking> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
