package com.vsc.vehicleservicecenter.mechanic.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Getter @Setter public class MechanicRequest {
    @NotBlank @Size(max=100) private String fullName;
    @NotBlank @Size(max=100) private String specialization;
    @NotBlank @Pattern(regexp="^[6-9][0-9]{9}$") private String phoneNumber;
}
