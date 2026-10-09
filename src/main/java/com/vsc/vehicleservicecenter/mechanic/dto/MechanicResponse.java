package com.vsc.vehicleservicecenter.mechanic.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter @Setter public class MechanicResponse {
    private Long id;
    private String fullName;
    private String specialization;
    private String phoneNumber;
}
