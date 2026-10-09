package com.vsc.vehicleservicecenter.servicecatalog.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter @Setter public class ServiceCatalogResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private Integer estimatedMinutes;
}
