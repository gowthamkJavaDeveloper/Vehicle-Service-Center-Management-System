package com.vsc.vehicleservicecenter.mechanic.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.mechanic.dto.*;
import com.vsc.vehicleservicecenter.mechanic.service.MechanicService;
@RestController @RequiredArgsConstructor @RequestMapping("/api/v1/mechanics")
public class MechanicController {
    private final MechanicService service;
    @PostMapping public ResponseEntity<MechanicResponse> create(@Valid @RequestBody MechanicRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
    @GetMapping("/{id}") public MechanicResponse get(@PathVariable Long id) { return service.get(id); }
    @GetMapping public List<MechanicResponse> all() { return service.all(); }
    @PutMapping("/{id}") public MechanicResponse update(@PathVariable Long id, @Valid @RequestBody MechanicRequest request) {
        return service.update(id,request);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
