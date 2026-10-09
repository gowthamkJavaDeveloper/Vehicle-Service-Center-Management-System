package com.vsc.vehicleservicecenter.jobcard.service;
import java.util.*;import java.math.*;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.jobcard.dto.JobCardRequest;import com.vsc.vehicleservicecenter.jobcard.entity.JobCard;import com.vsc.vehicleservicecenter.jobcard.repository.JobCardRepository;
import com.vsc.vehicleservicecenter.booking.repository.BookingRepository;import com.vsc.vehicleservicecenter.common.exception.ResourceNotFoundException;
@Service @RequiredArgsConstructor public class JobCardService {
 private final JobCardRepository repo;private final BookingRepository bookings;
 public JobCard get(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job card not found: "+id));}
 @Transactional(readOnly=true) public List<JobCard> all(){return repo.findAll();}
 @Transactional public JobCard create(JobCardRequest r){if(!bookings.existsById(r.getBookingId()))throw new ResourceNotFoundException("Booking not found");
 JobCard j=new JobCard();j.setBookingId(r.getBookingId());j.setComplaint(r.getComplaint());j.setDiagnosis(r.getDiagnosis());j.setWorkNotes(r.getWorkNotes());
 j.setLaborCharge(r.getLaborCharge()==null?BigDecimal.ZERO:r.getLaborCharge());if(j.getLaborCharge().signum()<0)throw new IllegalArgumentException("Negative labor charge");return repo.save(j);}
 @Transactional public JobCard update(Long id,JobCardRequest r){JobCard j=get(id);if(!"OPEN".equals(j.getStatus()))throw new IllegalArgumentException("Only OPEN job cards may be edited");
 j.setComplaint(r.getComplaint());j.setDiagnosis(r.getDiagnosis());j.setWorkNotes(r.getWorkNotes());if(r.getLaborCharge()!=null){if(r.getLaborCharge().signum()<0)throw new IllegalArgumentException("Negative labor charge");j.setLaborCharge(r.getLaborCharge());}return repo.save(j);}
 @Transactional public JobCard close(Long id){JobCard j=get(id);if(!"OPEN".equals(j.getStatus()))throw new IllegalArgumentException("Job card is already closed");j.setStatus("COMPLETED");return repo.save(j);}
}
