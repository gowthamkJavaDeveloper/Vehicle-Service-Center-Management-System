package com.vsc.vehicleservicecenter.booking.dto;
 import lombok.*; import jakarta.validation.constraints.*; import java.math.BigDecimal;
import java.time.LocalDateTime;
 @Getter @Setter public class BookingRequest {     @NotNull private Long vehicleId;
    @NotNull private Long serviceId;
    private Long mechanicId;
    @NotNull private LocalDateTime appointmentAt;
    private String notes; }
