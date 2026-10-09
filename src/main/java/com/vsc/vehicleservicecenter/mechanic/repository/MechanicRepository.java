package com.vsc.vehicleservicecenter.mechanic.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vsc.vehicleservicecenter.mechanic.entity.Mechanic;
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {}
