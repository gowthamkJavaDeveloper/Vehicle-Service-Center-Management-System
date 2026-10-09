package com.vsc.vehicleservicecenter.servicecatalog.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vsc.vehicleservicecenter.servicecatalog.entity.ServiceCatalog;
import com.vsc.vehicleservicecenter.servicecatalog.dto.*;
import com.vsc.vehicleservicecenter.servicecatalog.repository.ServiceCatalogRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor public class ServiceCatalogService {
    private final ServiceCatalogRepository repository;
    private ServiceCatalog find(Long id) { return repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("ServiceCatalog not found: " + id)); }
    private ServiceCatalogResponse map(ServiceCatalog entity) {
        ServiceCatalogResponse dto = new ServiceCatalogResponse(); dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setBasePrice(entity.getBasePrice());
        dto.setEstimatedMinutes(entity.getEstimatedMinutes());
        return dto;
    }
    @Transactional public ServiceCatalogResponse create(ServiceCatalogRequest request) {
        ServiceCatalog entity = new ServiceCatalog();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setBasePrice(request.getBasePrice());
        entity.setEstimatedMinutes(request.getEstimatedMinutes());
        return map(repository.save(entity));
    }
    @Transactional(readOnly=true) public ServiceCatalogResponse get(Long id) { return map(find(id)); }
    @Transactional(readOnly=true) public List<ServiceCatalogResponse> all() { return repository.findAll().stream().map(this::map).toList(); }
    @Transactional public ServiceCatalogResponse update(Long id, ServiceCatalogRequest request) {
        ServiceCatalog entity = find(id);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setBasePrice(request.getBasePrice());
        entity.setEstimatedMinutes(request.getEstimatedMinutes());
        return map(repository.save(entity));
    }
    @Transactional public void delete(Long id) { repository.delete(find(id)); }
}
