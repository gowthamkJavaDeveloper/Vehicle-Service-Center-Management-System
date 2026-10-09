package com.vsc.vehicleservicecenter.vehicle.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @Entity @Table(name="vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="registration_number", nullable=false)
    private String registrationNumber;
    @Column(name="brand", nullable=false)
    private String brand;
    @Column(name="model", nullable=false)
    private String model;
    @Column(name="manufacture_year")
    private Integer manufactureYear;
    @Column(name="customer_id", nullable=false)
    private Long customerId;
}
