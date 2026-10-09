package com.vsc.vehicleservicecenter.stockmovement.repository;
 import org.springframework.data.jpa.repository.JpaRepository;
 import com.vsc.vehicleservicecenter.stockmovement.entity.StockMovement;
 public interface StockMovementRepository extends JpaRepository<StockMovement,Long> { }
