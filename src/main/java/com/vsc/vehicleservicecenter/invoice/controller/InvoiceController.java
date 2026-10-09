package com.vsc.vehicleservicecenter.invoice.controller;
import java.util.*;import java.math.*;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.invoice.service.InvoiceService;import com.vsc.vehicleservicecenter.invoice.entity.Invoice;
@RestController @RequestMapping("/api/v1/invoices") @RequiredArgsConstructor public class InvoiceController {
 private final InvoiceService service;
 @PostMapping("/job-card/{jobCardId}") public ResponseEntity<Invoice> generate(@PathVariable Long jobCardId,@RequestParam(defaultValue="0") BigDecimal taxPercent){return ResponseEntity.status(201).body(service.generate(jobCardId,taxPercent));}
 @GetMapping public List<Invoice> all(){return service.all();}
 @GetMapping("/{id}") public Invoice get(@PathVariable Long id){return service.get(id);}
}
