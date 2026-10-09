package com.vsc.vehicleservicecenter.servicecatalog.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @Entity @Table(name="service_catalog")
public class ServiceCatalog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="name", nullable=false)
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="base_price", nullable=false)
    private BigDecimal basePrice;
    @Column(name="estimated_minutes", nullable=false)
    private Integer estimatedMinutes;
}
