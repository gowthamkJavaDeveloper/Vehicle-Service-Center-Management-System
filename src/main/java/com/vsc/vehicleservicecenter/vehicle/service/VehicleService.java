package com.vsc.vehicleservicecenter.vehicle.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vsc.vehicleservicecenter.vehicle.entity.Vehicle;
import com.vsc.vehicleservicecenter.vehicle.dto.*;
import com.vsc.vehicleservicecenter.vehicle.repository.VehicleRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;
import com.vsc.vehicleservicecenter.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor public class VehicleService {
    private final VehicleRepository repository;
    private final CustomerRepository customerRepository;
    private Vehicle find(Long id) { return repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Vehicle not found: " + id)); }
    private VehicleResponse map(Vehicle entity) {
        VehicleResponse dto = new VehicleResponse(); dto.setId(entity.getId());
        dto.setRegistrationNumber(entity.getRegistrationNumber());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setManufactureYear(entity.getManufactureYear());
        dto.setCustomerId(entity.getCustomerId());
        return dto;
    }
    @Transactional public VehicleResponse create(VehicleRequest request) {
        if (!customerRepository.existsById(request.getCustomerId()))
            throw new ResourceNotFoundException("Customer not found: " + request.getCustomerId());
        Vehicle entity = new Vehicle();
        entity.setRegistrationNumber(request.getRegistrationNumber());
        entity.setBrand(request.getBrand());
        entity.setModel(request.getModel());
        entity.setManufactureYear(request.getManufactureYear());
        entity.setCustomerId(request.getCustomerId());
        return map(repository.save(entity));
    }
    @Transactional(readOnly=true) public VehicleResponse get(Long id) { return map(find(id)); }
    @Transactional(readOnly=true) public List<VehicleResponse> all() { return repository.findAll().stream().map(this::map).toList(); }
    @Transactional public VehicleResponse update(Long id, VehicleRequest request) {
        if (!customerRepository.existsById(request.getCustomerId()))
            throw new ResourceNotFoundException("Customer not found: " + request.getCustomerId());
        Vehicle entity = find(id);
        entity.setRegistrationNumber(request.getRegistrationNumber());
        entity.setBrand(request.getBrand());
        entity.setModel(request.getModel());
        entity.setManufactureYear(request.getManufactureYear());
        entity.setCustomerId(request.getCustomerId());
        return map(repository.save(entity));
    }
    @Transactional public void delete(Long id) { repository.delete(find(id)); }
}
