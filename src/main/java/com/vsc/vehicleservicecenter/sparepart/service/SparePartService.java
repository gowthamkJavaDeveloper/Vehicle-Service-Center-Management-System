package com.vsc.vehicleservicecenter.sparepart.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vsc.vehicleservicecenter.sparepart.entity.SparePart;
import com.vsc.vehicleservicecenter.sparepart.dto.*;
import com.vsc.vehicleservicecenter.sparepart.repository.SparePartRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor public class SparePartService {
    private final SparePartRepository repository;
    private SparePart find(Long id) { return repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("SparePart not found: " + id)); }
    private SparePartResponse map(SparePart entity) {
        SparePartResponse dto = new SparePartResponse(); dto.setId(entity.getId());
        dto.setSku(entity.getSku());
        dto.setName(entity.getName());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setReorderLevel(entity.getReorderLevel());
        return dto;
    }
    @Transactional public SparePartResponse create(SparePartRequest request) {
        SparePart entity = new SparePart();
        entity.setSku(request.getSku());
        entity.setName(request.getName());
        entity.setUnitPrice(request.getUnitPrice());
        entity.setStockQuantity(request.getStockQuantity());
        entity.setReorderLevel(request.getReorderLevel());
        return map(repository.save(entity));
    }
    @Transactional(readOnly=true) public SparePartResponse get(Long id) { return map(find(id)); }
    @Transactional(readOnly=true) public List<SparePartResponse> all() { return repository.findAll().stream().map(this::map).toList(); }
    @Transactional public SparePartResponse update(Long id, SparePartRequest request) {
        SparePart entity = find(id);
        entity.setSku(request.getSku());
        entity.setName(request.getName());
        entity.setUnitPrice(request.getUnitPrice());
        entity.setStockQuantity(request.getStockQuantity());
        entity.setReorderLevel(request.getReorderLevel());
        return map(repository.save(entity));
    }
    @Transactional public void delete(Long id) { repository.delete(find(id)); }
}
