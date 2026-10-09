package com.vsc.vehicleservicecenter.payment.dto;
 import lombok.*; import jakarta.validation.constraints.*; import java.math.BigDecimal;
import java.time.LocalDateTime;
 @Getter @Setter public class PaymentRequest {     @NotNull private Long invoiceId;
    @NotNull private BigDecimal amount;
    @NotBlank private String method;
    private String referenceNumber; }
