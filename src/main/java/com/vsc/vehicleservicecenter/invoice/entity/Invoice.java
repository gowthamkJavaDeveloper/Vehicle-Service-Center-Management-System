
package com.vsc.vehicleservicecenter.invoice.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_card_id", nullable = false, unique = true)
    private Long jobCardId;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(name = "status")
    private String status = "UNPAID";
}
