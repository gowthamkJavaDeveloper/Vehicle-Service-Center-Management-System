package com.vsc.vehicleservicecenter.stockmovement.controller;
import java.util.*;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.stockmovement.service.StockMovementService;import com.vsc.vehicleservicecenter.stockmovement.dto.StockMovementRequest;import com.vsc.vehicleservicecenter.stockmovement.entity.StockMovement;
@RestController @RequestMapping("/api/v1/stock-movements") @RequiredArgsConstructor public class StockMovementController {
 private final StockMovementService service;
 @PostMapping public ResponseEntity<StockMovement> create(@Valid @RequestBody StockMovementRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @GetMapping public List<StockMovement> all(){return service.all();}
}
