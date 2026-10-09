package com.vsc.vehicleservicecenter.payment.service;

import java.util.*;
import java.math.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.payment.dto.PaymentRequest;
import com.vsc.vehicleservicecenter.payment.entity.Payment;
import com.vsc.vehicleservicecenter.payment.repository.PaymentRepository;
import com.vsc.vehicleservicecenter.invoice.repository.InvoiceRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository repo;
	private final InvoiceRepository invoices;

	@Transactional(readOnly = true)
	public List<Payment> all() {
		return repo.findAll();
	}

	@Transactional
	public Payment create(PaymentRequest r) {
		if (r.getAmount().signum() <= 0)
			throw new IllegalArgumentException("Payment must be positive");
		if (!List.of("CASH", "CARD", "UPI", "BANK_TRANSFER").contains(r.getMethod()))
			throw new IllegalArgumentException("Unsupported payment method");
		var invoice = invoices.findByIdForUpdate(r.getInvoiceId())
				.orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
		BigDecimal paid = invoice.getPaidAmount().add(r.getAmount());
		if (paid.compareTo(invoice.getTotalAmount()) > 0)
			throw new IllegalArgumentException("Payment exceeds outstanding amount");
		invoice.setPaidAmount(paid);
		invoice.setStatus(paid.compareTo(invoice.getTotalAmount()) == 0 ? "PAID" : "PARTIALLY_PAID");
		invoices.save(invoice);
		Payment p = new Payment();
		p.setInvoiceId(r.getInvoiceId());
		p.setAmount(r.getAmount());
		p.setMethod(r.getMethod());
		p.setReferenceNumber(r.getReferenceNumber());
		return repo.save(p);
	}
}
