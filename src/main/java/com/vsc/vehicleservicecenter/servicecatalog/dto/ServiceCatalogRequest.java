package com.vsc.vehicleservicecenter.servicecatalog.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Getter @Setter public class ServiceCatalogRequest {
    @NotBlank @Size(max=120) private String name;
    @Size(max=255) private String description;
    @NotNull @DecimalMin("0.00") private BigDecimal basePrice;
    @NotNull @Min(1) private Integer estimatedMinutes;
}
