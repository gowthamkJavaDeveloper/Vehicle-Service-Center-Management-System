package com.vsc.vehicleservicecenter.auth.entity;
import jakarta.persistence.*;import lombok.*;
@Entity @Table(name="app_users") @Getter @Setter @NoArgsConstructor public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String username;
 @Column(name="password_hash",nullable=false) private String passwordHash;
 @Column(nullable=false) private String role="STAFF";
 @Column(nullable=false) private boolean enabled=true;
}
