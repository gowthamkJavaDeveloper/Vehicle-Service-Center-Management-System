package com.vsc.vehicleservicecenter.booking.entity;
 import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal;
import java.time.LocalDateTime;
 @Entity @Table(name="bookings") @Getter @Setter @NoArgsConstructor
 public class Booking { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
     @Column(name="vehicle_id", nullable=false) private Long vehicleId;
    @Column(name="service_id", nullable=false) private Long serviceId;
    @Column(name="mechanic_id") private Long mechanicId;
    @Column(name="appointment_at", nullable=false) private LocalDateTime appointmentAt;
    @Column(name="status") private String status = "SCHEDULED";
    @Column(name="notes") private String notes;
 }
