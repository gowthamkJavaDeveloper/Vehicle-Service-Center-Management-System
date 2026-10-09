package com.vsc.vehicleservicecenter.vehicle.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter @Setter public class VehicleResponse {
    private Long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private Integer manufactureYear;
    private Long customerId;
}
