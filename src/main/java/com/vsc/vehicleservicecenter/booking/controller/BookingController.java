package com.vsc.vehicleservicecenter.booking.controller;
import java.util.*;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.booking.service.BookingService;import com.vsc.vehicleservicecenter.booking.dto.BookingRequest;import com.vsc.vehicleservicecenter.booking.entity.Booking;
@RestController @RequestMapping("/api/v1/bookings") @RequiredArgsConstructor public class BookingController {
 private final BookingService service;
 @PostMapping public ResponseEntity<Booking> create(@Valid @RequestBody BookingRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @GetMapping public List<Booking> all(){return service.all();}
 @GetMapping("/{id}") public Booking get(@PathVariable Long id){return service.get(id);}
 @PatchMapping("/{id}/status") public Booking status(@PathVariable Long id,@RequestParam String status){return service.status(id,status);}
}
