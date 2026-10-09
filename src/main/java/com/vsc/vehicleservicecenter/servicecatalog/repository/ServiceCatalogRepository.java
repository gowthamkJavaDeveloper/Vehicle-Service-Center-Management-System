package com.vsc.vehicleservicecenter.servicecatalog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vsc.vehicleservicecenter.servicecatalog.entity.ServiceCatalog;
public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {}
