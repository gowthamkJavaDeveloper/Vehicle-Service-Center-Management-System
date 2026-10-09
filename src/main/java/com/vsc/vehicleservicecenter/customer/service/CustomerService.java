package com.vsc.vehicleservicecenter.customer.service;
import java.util.List;
import com.vsc.vehicleservicecenter.customer.dto.*;
import com.vsc.vehicleservicecenter.customer.enums.CustomerStatus;
public interface CustomerService {
 CustomerResponseDTO createCustomer(CustomerRequestDTO request);
 CustomerResponseDTO getCustomerById(Long id);
 List<CustomerResponseDTO> getAllCustomers();
 CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request);
 CustomerResponseDTO changeStatus(Long id, CustomerStatus status);
}
