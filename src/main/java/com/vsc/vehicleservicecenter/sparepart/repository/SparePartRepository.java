
package com.vsc.vehicleservicecenter.sparepart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vsc.vehicleservicecenter.sparepart.entity.SparePart;

import jakarta.persistence.LockModeType;

public interface SparePartRepository extends JpaRepository<SparePart, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM SparePart p WHERE p.id = :id")
    Optional<SparePart> findByIdForUpdate(@Param("id") Long id);
}
