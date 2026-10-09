package com.vsc.vehicleservicecenter.vehicle.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.vehicle.dto.*;
import com.vsc.vehicleservicecenter.vehicle.service.VehicleService;
@RestController @RequiredArgsConstructor @RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService service;
    @PostMapping public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
    @GetMapping("/{id}") public VehicleResponse get(@PathVariable Long id) { return service.get(id); }
    @GetMapping public List<VehicleResponse> all() { return service.all(); }
    @PutMapping("/{id}") public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return service.update(id,request);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
