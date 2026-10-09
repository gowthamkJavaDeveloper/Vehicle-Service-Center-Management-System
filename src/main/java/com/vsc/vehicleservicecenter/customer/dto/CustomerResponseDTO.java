
package com.vsc.vehicleservicecenter.customer.dto;

import java.time.LocalDateTime;

import com.vsc.vehicleservicecenter.customer.enums.CustomerStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {

    private Long id;

    private String customerCode;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private CustomerStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
