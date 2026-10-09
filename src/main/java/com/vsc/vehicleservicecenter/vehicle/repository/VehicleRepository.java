package com.vsc.vehicleservicecenter.vehicle.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vsc.vehicleservicecenter.vehicle.entity.Vehicle;
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {}
