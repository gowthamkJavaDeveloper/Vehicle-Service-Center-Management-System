package com.vsc.vehicleservicecenter.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vsc.vehicleservicecenter.customer.entity.Customer;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}