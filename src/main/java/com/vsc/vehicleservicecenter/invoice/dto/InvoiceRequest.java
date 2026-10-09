package com.vsc.vehicleservicecenter.invoice.dto;
 import lombok.*; import jakarta.validation.constraints.*; import java.math.BigDecimal;
import java.time.LocalDateTime;
 @Getter @Setter public class InvoiceRequest {     @NotNull private Long jobCardId; }
