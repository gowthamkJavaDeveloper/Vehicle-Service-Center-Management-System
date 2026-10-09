
package com.vsc.vehicleservicecenter.customer.entity;

import java.time.LocalDateTime;

import com.vsc.vehicleservicecenter.customer.enums.CustomerStatus;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "customer_code",
        nullable = false,
        unique = true,
        length = 30
    )
    private String customerCode;

    @Column(
        name = "full_name",
        nullable = false,
        length = 100
    )
    private String fullName;

    @Column(
        name = "email",
        nullable = false,
        unique = true,
        length = 150
    )
    private String email;

    @Column(
        name = "phone_number",
        nullable = false,
        unique = true,
        length = 15
    )
    private String phoneNumber;

    @Column(
        name = "address",
        length = 255
    )
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        length = 20
    )
    private CustomerStatus status = CustomerStatus.ACTIVE;

    @Column(
        name = "created_at",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
        name = "updated_at",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime updatedAt;

    public static Customer create() {
        return new Customer();
    }
}
