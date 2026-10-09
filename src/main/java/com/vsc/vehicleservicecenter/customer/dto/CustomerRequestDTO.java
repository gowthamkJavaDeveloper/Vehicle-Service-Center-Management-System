
package com.vsc.vehicleservicecenter.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100,
          message = "Name must contain 3 to 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Enter a valid 10-digit Indian mobile number"
    )
    private String phoneNumber;

    @Size(max = 255,
          message = "Address cannot exceed 255 characters")
    private String address;
}
