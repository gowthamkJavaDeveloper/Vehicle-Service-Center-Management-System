
package com.vsc.vehicleservicecenter.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vsc.vehicleservicecenter.invoice.entity.Invoice;

import jakarta.persistence.LockModeType;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    boolean existsByJobCardId(Long jobCardId);

    Optional<Invoice> findByJobCardId(Long jobCardId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Invoice i WHERE i.id = :id")
    Optional<Invoice> findByIdForUpdate(@Param("id") Long id);
}
