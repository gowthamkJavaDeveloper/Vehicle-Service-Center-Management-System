package com.vsc.vehicleservicecenter.payment.entity;
 import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal;
import java.time.LocalDateTime;
 @Entity @Table(name="payments") @Getter @Setter @NoArgsConstructor
 public class Payment { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
     @Column(name="invoice_id", nullable=false) private Long invoiceId;
    @Column(name="amount", nullable=false) private BigDecimal amount;
    @Column(name="method", nullable=false) private String method;
    @Column(name="reference_number") private String referenceNumber;
 }
