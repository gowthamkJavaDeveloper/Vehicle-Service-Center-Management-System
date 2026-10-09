package com.vsc.vehicleservicecenter.stockmovement.service;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.stockmovement.dto.StockMovementRequest;
import com.vsc.vehicleservicecenter.stockmovement.entity.StockMovement;
import com.vsc.vehicleservicecenter.stockmovement.repository.StockMovementRepository;
import com.vsc.vehicleservicecenter.sparepart.repository.SparePartRepository;
import com.vsc.vehicleservicecenter.jobcard.repository.JobCardRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class StockMovementService {
	private final StockMovementRepository repo;
	private final SparePartRepository parts;
	private final JobCardRepository jobs;

	@Transactional(readOnly = true)
	public List<StockMovement> all() {
		return repo.findAll();
	}

	@Transactional
	public StockMovement create(StockMovementRequest r) {
		if (r.getQuantity() <= 0)
			throw new IllegalArgumentException("Quantity must be positive");
		if (!List.of("IN", "OUT").contains(r.getMovementType()))
			throw new IllegalArgumentException("Movement type must be IN or OUT");
		if (r.getJobCardId() != null) {
			var job = jobs.findById(r.getJobCardId())
					.orElseThrow(() -> new ResourceNotFoundException("Job card not found"));

			if ("COMPLETED".equals(job.getStatus())) {
				throw new IllegalArgumentException("Cannot modify stock for a completed job card");
			}
		}
		if ("OUT".equals(r.getMovementType()) && r.getJobCardId() == null)
			throw new IllegalArgumentException("OUT requires a job card");
		var part = parts.findByIdForUpdate(r.getPartId())
				.orElseThrow(() -> new ResourceNotFoundException("Spare part not found"));
		int updated = part.getStockQuantity() + ("IN".equals(r.getMovementType()) ? r.getQuantity() : -r.getQuantity());
		if (updated < 0)
			throw new IllegalArgumentException("Insufficient stock");
		part.setStockQuantity(updated);
		parts.save(part);
		StockMovement m = new StockMovement();
		m.setPartId(r.getPartId());
		m.setUnitPriceAtMovement(part.getUnitPrice());
		m.setJobCardId(r.getJobCardId());
		m.setMovementType(r.getMovementType());
		m.setQuantity(r.getQuantity());
		m.setRemarks(r.getRemarks());
		return repo.save(m);
	}
}
