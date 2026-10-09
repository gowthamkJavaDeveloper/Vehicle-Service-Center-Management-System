
package com.vsc.vehicleservicecenter.customer.mapper;

import org.springframework.stereotype.Component;

import com.vsc.vehicleservicecenter.customer.dto.CustomerRequestDTO;
import com.vsc.vehicleservicecenter.customer.dto.CustomerResponseDTO;
import com.vsc.vehicleservicecenter.customer.entity.Customer;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO request) {

    	Customer customer = Customer.create();

        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());

        return customer;
    }

    public CustomerResponseDTO toResponse(Customer customer) {

        CustomerResponseDTO response = new CustomerResponseDTO();

        response.setId(customer.getId());
        response.setCustomerCode(customer.getCustomerCode());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAddress(customer.getAddress());
        response.setStatus(customer.getStatus());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }
}
