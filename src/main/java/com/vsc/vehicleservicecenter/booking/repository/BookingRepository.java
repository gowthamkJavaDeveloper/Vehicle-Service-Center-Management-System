package com.vsc.vehicleservicecenter.booking.repository;
 import org.springframework.data.jpa.repository.JpaRepository;
 import com.vsc.vehicleservicecenter.booking.entity.Booking;
 public interface BookingRepository extends JpaRepository<Booking,Long> { }
