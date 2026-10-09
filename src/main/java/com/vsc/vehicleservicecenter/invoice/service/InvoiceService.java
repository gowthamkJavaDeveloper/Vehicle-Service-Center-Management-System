package com.vsc.vehicleservicecenter.invoice.service;

import com.vsc.vehicleservicecenter.common.exception.DuplicateResourceException;
import java.util.*;
import java.math.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.invoice.entity.Invoice;
import com.vsc.vehicleservicecenter.invoice.repository.InvoiceRepository;
import com.vsc.vehicleservicecenter.jobcard.repository.JobCardRepository;
import com.vsc.vehicleservicecenter.booking.repository.BookingRepository;
import com.vsc.vehicleservicecenter.servicecatalog.repository.ServiceCatalogRepository;
import com.vsc.vehicleservicecenter.stockmovement.repository.StockMovementRepository;
import com.vsc.vehicleservicecenter.sparepart.repository.SparePartRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class InvoiceService {
	private final InvoiceRepository repo;
	private final JobCardRepository jobs;
	private final BookingRepository bookings;
	private final ServiceCatalogRepository services;
	private final StockMovementRepository movements;
	private final SparePartRepository parts;

	public Invoice get(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + id));
	}

	@Transactional(readOnly = true)
	public List<Invoice> all() {
		return repo.findAll();
	}

	@Transactional
	public Invoice generate(Long jobCardId, BigDecimal taxPercent) {
		var job = jobs.findById(jobCardId).orElseThrow(() -> new ResourceNotFoundException("Job card not found"));
		if (repo.existsByJobCardId(jobCardId)) {
			throw new DuplicateResourceException("Invoice already exists for Job Card ID: " + jobCardId);
		}
		if (!"COMPLETED".equals(job.getStatus()))
			throw new IllegalArgumentException("Complete job card before invoicing");
		if (taxPercent == null || taxPercent.signum() < 0 || taxPercent.compareTo(new BigDecimal("100")) > 0)
			throw new IllegalArgumentException("Invalid tax percent");
		var booking = bookings.findById(job.getBookingId())
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
		var catalog = services.findById(booking.getServiceId())
				.orElseThrow(() -> new ResourceNotFoundException("Service not found"));
		BigDecimal subtotal = catalog.getBasePrice().add(job.getLaborCharge());

		for (var m : movements.findAll()) {
			if (jobCardId.equals(m.getJobCardId()) && "OUT".equals(m.getMovementType())) {

				BigDecimal unitPrice = m.getUnitPriceAtMovement();

				if (unitPrice == null) {
					var part = parts.findById(m.getPartId())
							.orElseThrow(() -> new ResourceNotFoundException("Part not found"));

					unitPrice = part.getUnitPrice();
				}

				subtotal = subtotal.add(unitPrice.multiply(BigDecimal.valueOf(m.getQuantity())));
			}
		}

		subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
		BigDecimal tax = subtotal.multiply(taxPercent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
		Invoice i = new Invoice();
		i.setJobCardId(jobCardId);
		i.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase());
		i.setSubtotal(subtotal);
		i.setTaxAmount(tax);
		i.setTotalAmount(subtotal.add(tax));
		return repo.save(i);
	}
}
