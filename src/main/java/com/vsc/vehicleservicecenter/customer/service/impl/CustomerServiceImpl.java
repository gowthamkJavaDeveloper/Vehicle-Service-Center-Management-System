
package com.vsc.vehicleservicecenter.customer.service.impl;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vsc.vehicleservicecenter.customer.dto.CustomerRequestDTO;
import com.vsc.vehicleservicecenter.customer.dto.CustomerResponseDTO;
import com.vsc.vehicleservicecenter.customer.entity.Customer;
import com.vsc.vehicleservicecenter.customer.mapper.CustomerMapper;
import com.vsc.vehicleservicecenter.customer.repository.CustomerRepository;
import com.vsc.vehicleservicecenter.customer.service.CustomerService;
import com.vsc.vehicleservicecenter.common.exception.DuplicateResourceException;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {
    
	private final EntityManager entityManager;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper,
            EntityManager entityManager) {

        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public CustomerResponseDTO createCustomer(
            CustomerRequestDTO request) {

        String email = request.getEmail().trim().toLowerCase();
        String phone = request.getPhoneNumber().trim();

        if (customerRepository.existsByEmail(email)) {
        	throw new DuplicateResourceException(
        	        "Email already registered");
        }

        if (customerRepository.existsByPhoneNumber(phone)) {
        	throw new DuplicateResourceException(
        	        "Phone number already registered");
        }

        Customer customer = customerMapper.toEntity(request);

        customer.setEmail(email);
        customer.setPhoneNumber(phone);

        customer.setCustomerCode(
                "CUS-" + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase()
        );

        Customer savedCustomer = customerRepository.saveAndFlush(customer);

        entityManager.refresh(savedCustomer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {

    	Customer customer = customerRepository.findById(id)
    	        .orElseThrow(() ->
    	                new ResourceNotFoundException(
    	                        "Customer not found with ID: " + id));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Customer not found with ID: " + id));
        String email = request.getEmail().trim().toLowerCase();
        String phone = request.getPhoneNumber().trim();
        if (!email.equals(customer.getEmail()) && customerRepository.existsByEmail(email))
            throw new DuplicateResourceException("Email already registered");
        if (!phone.equals(customer.getPhoneNumber()) && customerRepository.existsByPhoneNumber(phone))
            throw new DuplicateResourceException("Phone number already registered");
        customer.setFullName(request.getFullName().trim());
        customer.setEmail(email);
        customer.setPhoneNumber(phone);
        customer.setAddress(request.getAddress());
        return customerMapper.toResponse(customerRepository.saveAndFlush(customer));
    }

    @Override
    @Transactional
    public CustomerResponseDTO changeStatus(Long id, com.vsc.vehicleservicecenter.customer.enums.CustomerStatus status) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Customer not found with ID: " + id));
        customer.setStatus(status);
        return customerMapper.toResponse(customerRepository.saveAndFlush(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }
}
