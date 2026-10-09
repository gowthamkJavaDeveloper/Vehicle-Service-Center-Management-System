package com.vsc.vehicleservicecenter.servicecatalog.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.servicecatalog.dto.*;
import com.vsc.vehicleservicecenter.servicecatalog.service.ServiceCatalogService;
@RestController @RequiredArgsConstructor @RequestMapping("/api/v1/service-catalog")
public class ServiceCatalogController {
    private final ServiceCatalogService service;
    @PostMapping public ResponseEntity<ServiceCatalogResponse> create(@Valid @RequestBody ServiceCatalogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
    @GetMapping("/{id}") public ServiceCatalogResponse get(@PathVariable Long id) { return service.get(id); }
    @GetMapping public List<ServiceCatalogResponse> all() { return service.all(); }
    @PutMapping("/{id}") public ServiceCatalogResponse update(@PathVariable Long id, @Valid @RequestBody ServiceCatalogRequest request) {
        return service.update(id,request);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
