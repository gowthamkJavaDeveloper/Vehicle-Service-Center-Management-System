package com.vsc.vehicleservicecenter.booking.service;
import java.util.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor; import com.vsc.vehicleservicecenter.booking.dto.BookingRequest;
import com.vsc.vehicleservicecenter.booking.entity.Booking; import com.vsc.vehicleservicecenter.booking.repository.BookingRepository;
import com.vsc.vehicleservicecenter.vehicle.repository.VehicleRepository; import com.vsc.vehicleservicecenter.servicecatalog.repository.ServiceCatalogRepository;
import com.vsc.vehicleservicecenter.mechanic.repository.MechanicRepository;
import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;
@Service @RequiredArgsConstructor public class BookingService {
 private final BookingRepository repo; private final VehicleRepository vehicles; private final ServiceCatalogRepository services; private final MechanicRepository mechanics;
 public Booking get(Long id) { return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking not found: "+id)); }
 @Transactional(readOnly=true) public List<Booking> all(){return repo.findAll();}
 @Transactional public Booking create(BookingRequest r){
  if(!vehicles.existsById(r.getVehicleId()))throw new ResourceNotFoundException("Vehicle not found");
  if(!services.existsById(r.getServiceId()))throw new ResourceNotFoundException("Service not found");
  if(r.getMechanicId()!=null&&!mechanics.existsById(r.getMechanicId()))throw new ResourceNotFoundException("Mechanic not found");
  if(r.getAppointmentAt().isBefore(java.time.LocalDateTime.now()))throw new IllegalArgumentException("Appointment must be in the future");
  Booking b=new Booking(); b.setVehicleId(r.getVehicleId());b.setServiceId(r.getServiceId());b.setMechanicId(r.getMechanicId());b.setAppointmentAt(r.getAppointmentAt());b.setNotes(r.getNotes());
  return repo.save(b);
 }
 @Transactional public Booking status(Long id,String next){ Booking b=get(id); String current=b.getStatus();
  boolean valid=switch(current){case "SCHEDULED" -> List.of("IN_PROGRESS","CANCELLED").contains(next);case "IN_PROGRESS" -> List.of("COMPLETED","CANCELLED").contains(next);default -> false;};
  if(!valid)throw new IllegalArgumentException("Invalid booking status transition: "+current+" -> "+next);
  b.setStatus(next);return repo.save(b);
 }
}
