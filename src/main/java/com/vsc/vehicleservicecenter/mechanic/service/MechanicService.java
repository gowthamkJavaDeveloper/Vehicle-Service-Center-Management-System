package com.vsc.vehicleservicecenter.mechanic.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vsc.vehicleservicecenter.mechanic.entity.Mechanic;
import com.vsc.vehicleservicecenter.mechanic.dto.*;
import com.vsc.vehicleservicecenter.mechanic.repository.MechanicRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor public class MechanicService {
    private final MechanicRepository repository;
    private Mechanic find(Long id) { return repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Mechanic not found: " + id)); }
    private MechanicResponse map(Mechanic entity) {
        MechanicResponse dto = new MechanicResponse(); dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setSpecialization(entity.getSpecialization());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }
    @Transactional public MechanicResponse create(MechanicRequest request) {
        Mechanic entity = new Mechanic();
        entity.setFullName(request.getFullName());
        entity.setSpecialization(request.getSpecialization());
        entity.setPhoneNumber(request.getPhoneNumber());
        return map(repository.save(entity));
    }
    @Transactional(readOnly=true) public MechanicResponse get(Long id) { return map(find(id)); }
    @Transactional(readOnly=true) public List<MechanicResponse> all() { return repository.findAll().stream().map(this::map).toList(); }
    @Transactional public MechanicResponse update(Long id, MechanicRequest request) {
        Mechanic entity = find(id);
        entity.setFullName(request.getFullName());
        entity.setSpecialization(request.getSpecialization());
        entity.setPhoneNumber(request.getPhoneNumber());
        return map(repository.save(entity));
    }
    @Transactional public void delete(Long id) { repository.delete(find(id)); }
}
