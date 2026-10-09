package com.vsc.vehicleservicecenter.sparepart.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.sparepart.dto.*;
import com.vsc.vehicleservicecenter.sparepart.service.SparePartService;
@RestController @RequiredArgsConstructor @RequestMapping("/api/v1/spare-parts")
public class SparePartController {
    private final SparePartService service;
    @PostMapping public ResponseEntity<SparePartResponse> create(@Valid @RequestBody SparePartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
    @GetMapping("/{id}") public SparePartResponse get(@PathVariable Long id) { return service.get(id); }
    @GetMapping public List<SparePartResponse> all() { return service.all(); }
    @PutMapping("/{id}") public SparePartResponse update(@PathVariable Long id, @Valid @RequestBody SparePartRequest request) {
        return service.update(id,request);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
