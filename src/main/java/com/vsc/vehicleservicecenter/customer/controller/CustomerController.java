
package com.vsc.vehicleservicecenter.customer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vsc.vehicleservicecenter.customer.dto.CustomerRequestDTO;
import com.vsc.vehicleservicecenter.customer.dto.CustomerResponseDTO;
import com.vsc.vehicleservicecenter.customer.service.CustomerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerRequestDTO request) {

        CustomerResponseDTO response =
                customerService.createCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @PathVariable @Positive Long id) {

        CustomerResponseDTO response =
                customerService.getCustomerById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {

        List<CustomerResponseDTO> customers =
                customerService.getAllCustomers();

        return ResponseEntity.ok(customers);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable @Positive Long id,
            @Valid @RequestBody CustomerRequestDTO request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CustomerResponseDTO> changeStatus(@PathVariable @Positive Long id,
            @RequestParam com.vsc.vehicleservicecenter.customer.enums.CustomerStatus status) {
        return ResponseEntity.ok(customerService.changeStatus(id, status));
    }
}
