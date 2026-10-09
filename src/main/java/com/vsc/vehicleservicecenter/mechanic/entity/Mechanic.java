package com.vsc.vehicleservicecenter.mechanic.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @Entity @Table(name="mechanics")
public class Mechanic {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="full_name", nullable=false)
    private String fullName;
    @Column(name="specialization", nullable=false)
    private String specialization;
    @Column(name="phone_number", nullable=false)
    private String phoneNumber;
}
