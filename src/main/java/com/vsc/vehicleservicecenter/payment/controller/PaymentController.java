package com.vsc.vehicleservicecenter.payment.controller;
import java.util.*;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.payment.service.PaymentService;import com.vsc.vehicleservicecenter.payment.dto.PaymentRequest;import com.vsc.vehicleservicecenter.payment.entity.Payment;
@RestController @RequestMapping("/api/v1/payments") @RequiredArgsConstructor public class PaymentController {
 private final PaymentService service;
 @PostMapping public ResponseEntity<Payment> create(@Valid @RequestBody PaymentRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @GetMapping public List<Payment> all(){return service.all();}
}
