package com.vsc.vehicleservicecenter.jobcard.dto;
 import lombok.*; import jakarta.validation.constraints.*; import java.math.BigDecimal;
import java.time.LocalDateTime;
 @Getter @Setter public class JobCardRequest {     @NotNull private Long bookingId;
    @NotBlank private String complaint;
    private String diagnosis;
    private String workNotes;
    private BigDecimal laborCharge; }
