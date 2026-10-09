package com.vsc.vehicleservicecenter.jobcard.entity;
 import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal;
import java.time.LocalDateTime;
 @Entity @Table(name="job_cards") @Getter @Setter @NoArgsConstructor
 public class JobCard { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
     @Column(name="booking_id", nullable=false) private Long bookingId;
    @Column(name="complaint", nullable=false) private String complaint;
    @Column(name="diagnosis") private String diagnosis;
    @Column(name="work_notes") private String workNotes;
    @Column(name="labor_charge") private BigDecimal laborCharge = BigDecimal.ZERO;
    @Column(name="status") private String status = "OPEN";
 }
