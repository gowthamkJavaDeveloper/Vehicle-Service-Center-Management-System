package com.vsc.vehicleservicecenter.vehicle.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Getter @Setter public class VehicleRequest {
    @NotBlank @Size(max=20) private String registrationNumber;
    @NotBlank @Size(max=80) private String brand;
    @NotBlank @Size(max=80) private String model;
    @Min(1900) @Max(2100) private Integer manufactureYear;
    @NotNull private Long customerId;
}
